'use client'
import { useEffect, useState } from 'react'
import { getData } from '../lib';
import { config } from '../../config';
import { ProductProps } from '../../type';
import ProductCard from './ProductCard';

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
    
    useEffect(() => {
        const fetchData = async() => {
            const endpoint = `${config?.baseUrl}/api/product`;

            try {
                const data = await getData(endpoint);
                setProducts(data);
                console.log(data);                
                
            } catch (error) {
                console.error('Error fetching data ', error);
            }

        };

        fetchData();
    }, [])

    //const itemsPerPage = 15;   
  //const endOffset = itemOffset + itemsPerPage;
  // console.log(`Loading items from ${itemOffset} to ${endOffset}`);
  //const currentItems = products.slice(itemOffset, endOffset);

  return (
    <>
        <Items currentItems={products} />
    </>
  )
}

export default Pagination
