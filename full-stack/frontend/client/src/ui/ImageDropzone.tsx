import React, { useRef, useState } from 'react';

interface ImageDropzoneProps {
  onImagesChange: (images: File[]) => void;
}

const ImageDropzone: React.FC<ImageDropzoneProps> = ({ onImagesChange }) => {
  const [files, setFiles] = useState<File[]>([]);
  const [previews, setPreviews] = useState<string[]>([]);
  const fileInputRef = useRef<HTMLInputElement>(null);

  const updateImages = (newFiles: File[]) => {
    setFiles(newFiles);
    onImagesChange(newFiles);
    const readers = newFiles.map((file) => {
      return new Promise<string>((resolve) => {
        const reader = new FileReader();
        reader.onload = () => resolve(reader.result as string);
        reader.readAsDataURL(file);
      });
    });

    Promise.all(readers).then(setPreviews);
  };

  const handleDrop = (e: React.DragEvent<HTMLDivElement>) => {
    e.preventDefault();
    const droppedFiles = Array.from(e.dataTransfer.files).filter((file) =>
      file.type.startsWith('image/')
    );
    updateImages([...files, ...droppedFiles]);
  };

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files) {
      const selectedFiles = Array.from(e.target.files).filter((file) =>
        file.type.startsWith('image/')
      );
      updateImages([...files, ...selectedFiles]);
    }
  };

  const handleRemove = (index: number) => {
    const newFiles = files.filter((_, i) => i !== index);
    updateImages(newFiles);
  };

  const handleClick = () => {
    fileInputRef.current?.click();
  };

  return (
    <>
      <div
        onClick={handleClick}
        onDrop={handleDrop}
        onDragOver={(e) => e.preventDefault()}
        className="w-full min-h-[160px] p-4 border-2 border-dashed rounded-lg cursor-pointer flex flex-wrap gap-4 items-center justify-start"
      >
        {previews.length === 0 && (
          <p className="text-gray-500">Кликните или перетащите изображения сюда</p>
        )}
        {previews.map((src, index) => (
          <div key={index} className="relative group">
            <img
              src={src}
              alt={`preview-${index}`}
              className="w-24 h-24 object-cover rounded-md"
            />
            <button
              onClick={(e) => {
                e.stopPropagation();
                handleRemove(index);
              }}
              className="absolute top-0 right-0 text-white bg-black/60 hover:bg-black/80 rounded-full w-6 h-6 flex items-center justify-center text-xs hidden group-hover:flex"
            >
              ✕
            </button>
          </div>
        ))}
      </div>

      <input
        type="file"
        accept="image/*"
        multiple
        ref={fileInputRef}
        onChange={handleFileChange}
        className="hidden"
      />
    </>
  );
};

export default ImageDropzone;
