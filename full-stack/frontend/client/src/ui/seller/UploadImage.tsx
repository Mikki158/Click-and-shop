import React, { useState } from "react";
import axios from "axios";

const UploadImage = () => {
  const [selectedFiles, setSelectedFiles] = useState<File[]>([]);
  const [previews, setPreviews] = useState<string[]>([]);

  // Обработка выбора файлов
  const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const files = event.target.files;
    if (files) {
      const fileArray = Array.from(files); // Преобразуем FileList в массив
      setSelectedFiles((prevFiles) => [...prevFiles, ...fileArray]);

      // Создаём массив превью изображений
      const previewUrls = fileArray.map((file) => URL.createObjectURL(file));
      setPreviews((prevPreviews) => [...prevPreviews, ...previewUrls]);
    }
  };

  // Отправка файлов на backend
  const handleUpload = async () => {
    if (selectedFiles.length === 0) {
      alert("Пожалуйста, выберите файлы для загрузки.");
      return;
    }

    const formData = new FormData();
    selectedFiles.forEach((file, index) => {
      formData.append(`files[${index}]`, file); // Добавляем файлы в formData
    });

    console.log(formData)

    // try {
    //   const response = await axios.post("/api/upload", formData, {
    //     headers: {
    //       "Content-Type": "multipart/form-data",
    //     },
    //   });
    //   alert("Файлы успешно загружены!");
    //   console.log("Ответ сервера:", response.data);
    // } catch (error) {
    //   console.error("Ошибка при загрузке файлов:", error);
    //   alert("Ошибка при загрузке файлов.");
    // }
  };

  // Удаление выбранного файла
  const handleRemoveFile = (index: number) => {
    setSelectedFiles((prevFiles) => prevFiles.filter((_, i) => i !== index));
    setPreviews((prevPreviews) => prevPreviews.filter((_, i) => i !== index));
  };

  return (
    <div className="p-4">
      <label className="block mb-2 text-lg font-medium text-gray-700">
        Загрузить фотографии
      </label>
      <input
        type="file"
        accept="image/*"
        multiple
        onChange={handleFileChange}
        className="block w-full text-sm text-gray-500 file:mr-4 file:py-2 file:px-4 file:border-0 file:text-sm file:font-semibold file:bg-gray-100 file:text-gray-700 hover:file:bg-gray-200"
      />

      {/* Превью изображений */}
      {previews.length > 0 && (
        <div className="mt-4 grid grid-cols-3 gap-4">
          {previews.map((preview, index) => (
            <div key={index} className="relative">
              <img
                src={preview}
                alt={`Preview ${index + 1}`}
                className="w-full h-32 object-cover rounded border"
              />
              <button
                onClick={() => handleRemoveFile(index)}
                className="absolute top-1 right-1 bg-red-500 text-white rounded-full w-6 h-6 flex items-center justify-center text-sm"
              >
                ✕
              </button>
            </div>
          ))}
        </div>
      )}

      <button
        onClick={handleUpload}
        className="mt-4 px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
      >
        Загрузить
      </button>
    </div>
  );
};

export default UploadImage;
