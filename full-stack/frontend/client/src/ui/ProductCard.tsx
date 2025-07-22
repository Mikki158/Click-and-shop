import { ProductProps } from '../../type'
import { MdOutlineStarOutline, MdOutlineStar } from "react-icons/md";
import { FaComment } from "react-icons/fa";
import AddToCartBtn from './AddToCartBtn';
import ProductCardSideNav from './ProductCardSideNav';
import { useNavigate } from 'react-router-dom';
import { useEffect, useState } from 'react';
import apiClient from '../apiClient';

interface Props {
    item: ProductProps;
}

const ProductCard = ({item}: Props) => {

    //const [category, setCategory] = useState("")
    const navigation = useNavigate()

    const [brand, setBrand] = useState("")

    const handleProduct = () => {
        navigation(`/product/${item?.id}`)
    }

    useEffect(() => {
        const fetchData = async() => {
            try {
                if(item?.categoryId) {
                    const categoryReposytory = await apiClient.get(`/api/category/${item?.categoryId}`)
                    const brand = await apiClient.get(`/api/seller/brand/${item?.brandId}`)
                    setBrand(brand.data.brandName)
                }
            } catch(error) {
                console.error('Error fetching data', error);
            }
        }

        fetchData()
    })

    function pluralizeReview(count: number): string {
        const mod10 = count % 10;
        const mod100 = count % 100;

        if (mod10 === 1 && mod100 !== 11) return `${count} отзыв`;
        if (mod10 >= 2 && mod10 <= 4 && (mod100 < 10 || mod100 >= 20)) return `${count} отзыва`;
        return `${count} отзывов`;
    }

  return (
    <div className='border border-gray-200 rounded-lg p-1 overflow-hidden hover:border-black duration-200 cursor-pointer'>
        <div className='w-full h-60 relative p-2 group'>
            <img 
                onClick={handleProduct}
                src={item?.imagePaths[0]} 
                alt='productImage' 
                className='w-full h-full rounded-md object-cover duration-300'
            />
            <ProductCardSideNav product={item}/>
        </div>
        <div className='flex flex-col gap-2 px-2 pb-2'>
            {/* <h3 className='text-xs uppercase font-semibold text-lightText'>{category}</h3> */}
            <h2 className='text-lg font-bold line-clamp-2 truncate w-full' onClick={handleProduct}>{item?.name}</h2>
            <h2>{item?.brand}</h2>
            <div className='text-base text-lightText flex items-center'>
                <MdOutlineStar /> 
                <span className="ml-1">{item.rating > 0 ? item.rating : 0}</span> 
                <FaComment className="ml-2"/> 
                <span className="ml-1">{pluralizeReview(item.ratingCount)}</span>
                
            </div>
            <AddToCartBtn
                product={item}
            />
        </div>
    </div>
  )
}

export default ProductCard