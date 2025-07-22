import { useState } from 'react';
import { MdOutlineStar } from 'react-icons/md';

const StarRating = ({ onRate }: { onRate: (rating: number) => void }) => {
  const [hoverIndex, setHoverIndex] = useState<number | null>(null);
  const [selectedRating, setSelectedRating] = useState<number>(0);

  return (
    <div className="flex space-x-1">
      {Array.from({ length: 5 }, (_, index) => (
        <MdOutlineStar
          key={index}
          size={30}
          className={`
            cursor-pointer 
            ${hoverIndex !== null 
              ? index <= hoverIndex 
                ? 'text-yellow-400' 
                : 'text-gray-300'
              : index < selectedRating 
                ? 'text-yellow-400' 
                : 'text-gray-300'}
          `}
          onMouseEnter={() => setHoverIndex(index)}
          onMouseLeave={() => setHoverIndex(null)}
          onClick={() => {
            //setSelectedRating(index + 1);
            onRate(index + 1);
          }}
        />
      ))}
    </div>
  );
};

export default StarRating;
