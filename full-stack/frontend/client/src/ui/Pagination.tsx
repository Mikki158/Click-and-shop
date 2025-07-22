'use client'
import { useEffect, useState } from 'react'
import { getData } from '../lib';
import { config } from '../../config';
import { ProductProps } from '../../type';
import ProductCard from './ProductCard';
import apiClient from '../apiClient';
import Loading from './Loading'

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

const Pagination = () => {

    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(false);
    
    useEffect(() => {
        const fetchData = async() => {
            try {
                setLoading(true);
                const response = await apiClient.get("/api/product")
                
                const productsResponse = response.data

                const productDetails = await Promise.all(
                    productsResponse.map(async (product: { id:string }) => {
                        const categoryReposytory = await apiClient.get(`/api/category/${product?.categoryId}`)
                        const brandResponse = await apiClient.get(`/api/seller/brand/${product?.brandId}`)

                        return {
                            ...product,
                            brand: brandResponse.data.brandName
                        }
                    })
                )
                
                setProducts(productDetails);
                console.log(productDetails)
            } catch (error) {
                console.error("Ошибка при получении товаров: ", error)
            } finally {
                setLoading(false);
            }

        };

        fetchData();
    }, [])

    //const itemsPerPage = 15;   
  //const endOffset = itemOffset + itemsPerPage;
  // console.log(`Loading items from ${itemOffset} to ${endOffset}`);
  //const currentItems = products.slice(itemOffset, endOffset);

  return (
    <div>
        {
            loading ? <Loading/> : <Items currentItems={products} />
        }
    </div>
  )
}

export default Pagination
