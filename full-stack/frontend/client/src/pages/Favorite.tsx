import { ProductProps } from '../../type';
import ProductCard from '../ui/ProductCard';
import { useEffect, useState } from 'react';
import { Link } from "react-router-dom";
import { config } from '../../config';
import { getData } from '../lib';
import { store } from "../lib/store";
import Container from '../ui/Container';
import apiClient from '../apiClient';
import Title from '../ui/Title'
import FavoriteProduct from "../ui/FavoriteProduct";

interface ItemsProps {
    currentItems:ProductProps[]
}

//interface PaginationProps {}

const Items = ({currentItems}:ItemsProps) => {
    return (
        <div className='grid grid-cols-2 md:grid-cols-3 lg:grid-cols-5 gap-5'>
            {currentItems && currentItems?.map((item:ProductProps) => (
                <ProductCard key={item?.id} item={item}/>
            ))}
        </div>
    )
}

const Favorite = () => {

    const { favoriteProduct, fetchFavorite } = store();

    const [products, setProducts] = useState([]);
    
    useEffect(() => {
        fetchFavorite()
    }, [fetchFavorite])

    useEffect(() => {
        console.log("Избранные товары ", favoriteProduct)
    }, [favoriteProduct])

  return (
    <>
        <Container>
            {favoriteProduct.length > 0 ? (
                <div>
                    <div className="border-b border-b-gray-300 pb-6">
                        <h2 className="text-2xl font-bold tracking-tight text-gray-900 sm:text-3xl">
                        Сохраненные товары
                        </h2>
                    </div>
                    <div className="mt-6 flow-root px-4 sm:mt-10 sm:px-0">
                        <div className="-my-6 divide-y divide-gray-200 sm:-my-10">
                        {favoriteProduct?.map((product) => (
                            <FavoriteProduct key={product?._id} product={product} />
                        ))}
                        </div>
                    </div>
                </div>
            ) : (
                <div className="bg-white h-96 flex flex-col gap-2 items-center justify-center py-5 rounded-lg border border-gray-200 drop-shadow-2xl">
                    <h1 className="text-3xl font-bold tracking-tight text-gray-900 sm:text-4xl">
                        Сохраненные товары
                    </h1>
                    <p className="text-lg max-w-[600px] text-center text-gray-600 tracking-wide leading-6">
                        Вы не сохранили ни одного товара, можете перейти на главную страницу, чтобы это исправить.
                    </p>
                    <Link
                        to={"/product"}
                        className="bg-gray-800 text-gray-200 px-8 py-4 rounded-md hover:bg-black hover:text-white duration-200 uppercase text-sm font-semibold tracking-wide"
                    >
                        За покупками
                    </Link>
                </div>
            )}
            
        </Container>
    </>
  )
}

export default Favorite
