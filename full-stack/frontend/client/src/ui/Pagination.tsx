'use client'
import React, { useEffect, useState } from 'react'
import { getData } from '../lib';
import { config } from '../../config';
import { ProductProps } from '../../type';
import ProductCard from './ProductCard';
import ReactPaginate from "react-paginate";

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

    const itemsPerPage = 15;   
    const [itemOffset, setItemOffset] = useState(0);
  const [itemStart, setItemStart] = useState(1);
  const endOffset = itemOffset + itemsPerPage;
  // console.log(`Loading items from ${itemOffset} to ${endOffset}`);
  const currentItems = products.slice(itemOffset, endOffset);
  const pageCount = Math.ceil(products.length / itemsPerPage);

  const handlePageClick = (event: any) => {
    const newOffset = (event.selected * itemsPerPage) % products.length;
    const newStart = newOffset + 1;
    // console.log(
    //   `User requested page number ${event.selected}, which is offset ${newOffset}`
    // );
    setItemOffset(newOffset);
    setItemStart(newStart);
  };

  return (
    <>
        <Items currentItems={currentItems} />
        <div className='flex flex-col md:flex-row justify-center md:justify-between items-center'>
            <ReactPaginate 
                nextlabel = "" 
                onPageChange={handlePageClick} 
                pageRangeDisplayed={3} 
                marginPagesDisplayed={2} 
                pageCount={pageCount}
                previousLabel=""
                pageLinkClassName="w-9 h-9 border[1px] border-lightColor hover:border-gray-500 duration-300 flex justify-center
                items-center"
                pageClassName="mr-6"
                containerClassName="flex text-base font-semibold py-10"
                activeClassName="bg-black text-white"
            />
            <p>Products from {itemStart} to {Math.min(endOffset, products?.length)}{" "} of {products?.length}</p>
        </div>
    </>
  )
}

export default Pagination