import { ProductProps } from '../../type'
import { MdOutlineStarOutline } from "react-icons/md";
import AddToCartBtn from './AddToCartBtn';
import ProductCardSideNav from './ProductCardSideNav';
import { useNavigate } from 'react-router-dom';

interface Props {
    item: ProductProps;
}

const ProductCard = ({item}: Props) => {
    const navigation = useNavigate()

    const handleProduct = () => {
        navigation(`/product/${item?.id}`)
    }

  return (
    <div className='border border-gray-200 rounded-lg p-1 overflow-hidden hover:border-black duration-200 cursor-pointer'>
        <div className='w-full h-60 relative p-2 group'>
            <img 
                onClick={handleProduct}
                src={item?.imagePaths[0]} 
                alt='productImage' 
                className='w-full h-full rounded-md object-cover group-hover:scale-100 duration-300'
            />
            <ProductCardSideNav />
        </div>
        <div className='flex flex-col gap-2 px-2 pb-2'>
            <h3 className='text-xs uppercase font-semibold text-lightText'>{item?.categoryId}</h3>
            <h2 className='text-lg font-bold line-clamp-2' onClick={handleProduct}>{item?.name}</h2>
            <div className='text-base text-lightText flex items-center'>
                <MdOutlineStarOutline />
                <MdOutlineStarOutline />
                <MdOutlineStarOutline />
                <MdOutlineStarOutline />
                <MdOutlineStarOutline />
            </div>
            <AddToCartBtn/>
        </div>
    </div>
  )
}

export default ProductCard