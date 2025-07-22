import React, { useState } from 'react';

const ImageModal = ({ images, onClose, initialIndex = 0 }: { images: string[], onClose: () => void, initialIndex?: number }) => {
  const [currentIndex, setCurrentIndex] = useState(initialIndex);

  const prevImage = () => {
    setCurrentIndex((prev) => (prev === 0 ? images.length - 1 : prev - 1));
  };

  const nextImage = () => {
    setCurrentIndex((prev) => (prev === images.length - 1 ? 0 : prev + 1));
  };

  return (
    <div className="fixed inset-0 bg-black/70 z-50 flex items-center justify-center">
      <button className="absolute top-4 right-4 text-white text-2xl" onClick={onClose}>✖</button>

      <div className="flex items-center gap-4">
        <button onClick={prevImage} className="text-white text-3xl">‹</button>
        <img src={images[currentIndex]} alt="modal" className="max-w-[80vw] max-h-[80vh] rounded-md" />
        <button onClick={nextImage} className="text-white text-3xl">›</button>
      </div>
    </div>
  );
};

export default ImageModal;