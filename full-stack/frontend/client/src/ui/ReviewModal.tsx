import { useRef, useState, useEffect } from 'react';
import { Dialog } from '@headlessui/react';
import { MdOutlineStar } from 'react-icons/md';
import { IoMdClose } from 'react-icons/io';
import ImageDropzone from './ImageDropzone';
import apiClient from '../apiClient';

const ReviewModal = ({ isOpen, onClose, product, fromrating }) => {
  const [rating, setRating] = useState(fromrating);
  const [hoverIndex, setHoverIndex] = useState(null);
  const [advantages, setAdvantages] = useState('');
  const [disadvantages, setDisadvantages] = useState('');
  const [comment, setComment] = useState('');

  const textareaRef = useRef<HTMLTextAreaElement>(null);
  const [value, setValue] = useState("");

  const handleRatingClick = (index) => setRating(index + 1);

  const [images, setImages] = useState<File[]>([]);

  const handleSubmit = () => {

    const formData = new FormData();
    images.forEach((file) => {
      formData.append('files', file);
    });

    const data = {
      "productId": product.id,
      "rating": rating,
      "reviewText": value
    }    

    apiClient.post('/api/product/createReview', data)
    .then((response) => {
      console.log(response)

      formData.append('reviewId', response.data.id);

      apiClient.post("/api/files/addReviewImage", formData, {
          headers: {
              "Content-Type": "multipart/form-data",
          },
      })
      .then((response) => {
          //alert("Файл успешно загружен!");
          console.log("Ответ сервера:", response.data);
          onClose();
      })
      .catch((error) => {
          console.error("Ошибка при загрузке файлов:", error);
          //alert("Ошибка при загрузке файлов.");
      });

      onClose();

    })
    .catch((error) => {
      console.error("Ошибка при отпраке отзыва ", error)

      

    })

    console.log("Отправка отзыва", data)

    

    // Теперь можно отправить formData на сервер
    console.log(images);
  };

  useEffect(() => {
    const textarea = textareaRef.current;
    if (textarea) {
      textarea.style.height = "auto"; // сброс
      textarea.style.height = `${textarea.scrollHeight}px`; // установка новой высоты
    }
  }, [value]);

  return (
    <Dialog open={isOpen} onClose={onClose} className="fixed inset-0 z-50 flex items-center justify-center p-4">
      <div className="fixed inset-0 bg-black opacity-40" />

      <div className="relative z-50 max-w-md w-full bg-white rounded-xl shadow-xl p-6 space-y-4">
        <div className="flex justify-between items-start">
          <div className="flex items-center space-x-2">
            <img src={product.imagePaths[0]} alt={product.name} className="w-10 h-10 object-cover rounded" />
            <span className="font-medium text-sm">{product.name}</span>
          </div>
          <button onClick={onClose}>
            <IoMdClose size={22} className="text-gray-500 hover:text-black" />
          </button>
        </div>

        <div className="flex space-x-1">
          {Array.from({ length: 5 }).map((_, index) => (
            <MdOutlineStar
              key={index}
              size={28}
              className={`cursor-pointer transition-colors ${
                hoverIndex !== null ? (index <= hoverIndex ? 'text-yellow-400' : 'text-gray-300') : index < rating ? 'text-yellow-400' : 'text-gray-300'
              }`}
              onMouseEnter={() => setHoverIndex(index)}
              onMouseLeave={() => setHoverIndex(null)}
              onClick={() => handleRatingClick(index)}
            />
          ))}
        </div>

        <div className="space-y-2">
         <textarea
            maxLength={300}
            ref={textareaRef}
            value={value}
            onChange={(e) => setValue(e.target.value)}
            className="w-full p-2 border rounded resize-none overflow-hidden"
            rows={1}
            placeholder="Введите отзыв..."
          />
          <div className="text-sm text-gray-500 text-right mt-1">
            {value.length} / 300
          </div>
        </div>

        <div className="space-y-4">
          <div>
            <p className="text-sm font-medium mb-1">Загрузите до 5 фото</p>
            <ImageDropzone onImagesChange={setImages}/>
          </div>
        </div>

        <button onClick={handleSubmit} className="w-full bg-gray-500 text-white py-2 rounded-lg hover:bg-gray-300 transition">
          Отправить отзыв
        </button>
      </div>
    </Dialog>
  );
};

export default ReviewModal;
