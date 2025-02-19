import { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import { config } from '../../config'
import { BrandProps, ProductProps } from '../../type'
import { getData } from '../lib'
import Loading from '../ui/Loading'
import Container from '../ui/Container'
import _ from "lodash";
import PriceTag from '../ui/PriceTag'
import { MdOutlineStarOutline } from 'react-icons/md'
import AddToCartBtn from '../ui/AddToCartBtn'
import ProductCard from '../ui/ProductCard'
import apiClient from '../apiClient'

const Product = () => {

  const [productData, setProductData] = useState<ProductProps | null>(null);
  const [allProducts, setAllProducts] = useState<ProductProps[]>([]);
  const [loading, setLoading] = useState(false);
  const [imgUrl, setImgUrl] = useState("")
  const {id} = useParams();
  const [fullCategory, setFullCategory] = useState("")
<<<<<<< Updated upstream
=======
  const [brand, setBrand] = useState<BrandProps | null>(null);
>>>>>>> Stashed changes
  
    const endpoint = id 
    ? `${config.baseUrl}/api/product/${id}` 
    : `${config.baseUrl}/api/product`

    useEffect(() => {
      const fetchData = async() => {
        try {
          setLoading(true);
          const data = await getData(endpoint)
          if(id) {
            setProductData(data);
            setAllProducts([]);
          } else {
            setAllProducts(data);
            setProductData(null);            
          }

<<<<<<< Updated upstream
          console.log(data)

          document.title = data.name
=======
          console.log("DATA")
          console.log(data)

          document.title = data.name

          if(data.categoryId) {
            const categoryRepository = await apiClient.get(`/api/category/${data?.categoryId}`)
            setFullCategory(categoryRepository.data);
            console.log(categoryRepository.data)
          }

          if(data.brandId) {
            const brandRepository = await apiClient.get(`/api/seller/brand/${data?.brandId}`)
            setBrand(brandRepository.data);
            console.log(brandRepository.data)
          }
>>>>>>> Stashed changes
          
        } catch (error) {
          console.error('Error fetching data', error);          
        } finally {
          setLoading(false);
        } 
      };

      fetchData();

<<<<<<< Updated upstream
      apiClient.get("/api/category", {
        params: productData?.categoryId
      })
      .then((response) => {
        setFullCategory(response.data)
      })
      .catch((error) => {
        console.error("Ошибка при получени полной категории ", error);
      })      
=======
      // apiClient.get(`/api/category/${productData?.categoryId}`)
      // .then((response) => {
      //   setFullCategory(response.data)
      //   console.log(response)
      // })
      // .catch((error) => {
      //   console.error("Ошибка при получени полной категории ", error);
      // })      
>>>>>>> Stashed changes

    }, [id, endpoint]);

    useEffect(() => {
      if(productData) {
        setImgUrl(productData?.imagePaths[0])
      }
      
    },[productData])

  return (
    <div>
      {
        loading ? <Loading /> : <Container>
          {!!id && productData && _.isEmpty(allProducts) ? (
            <div className='grid grid-cols-1 md:grid-cols-2 gap-10'> 
              <div className='flex flex-start'>
                <div>
                  {productData?.imagePaths?.map((item, index) => (
                    <img 
                      src={item} 
                      alt="img" 
                      key={index} 
                      className={`w-24 cursor-pointer opacity-80 hover:opacity-100 duration-300 
                        ${imgUrl === item && 'border border-gray-500 rounded-sm opacity-100'}`}
                      onClick={() => setImgUrl(item)}
                    />
                  ))}
                </div>
                <div>
                  <img src={imgUrl} alt="mainImage" />
                </div>
              </div>
              <div className='flex flex-col gap-4'>
<<<<<<< Updated upstream
                <label>{productData?.categoryId}</label>
                <label>{productData?.brandId}</label>
=======
                <label>{fullCategory}</label>
                <label>{brand?.brandName}</label>
>>>>>>> Stashed changes
                <h2 className='text-3xl font-bold'>{productData?.name}</h2>
                <div className='flex items-center justify-between'>
                  <PriceTag 
                    regularPrice={productData?.regularPrice}
                    discountedPrice={productData?.discountedPrice}
                    className="text-xl"
                  />
                  <div className='flex items-center gap-1'>
                    <div className='text-base text-lightText flex items-center'>
                        <MdOutlineStarOutline />
                        <MdOutlineStarOutline />
                        <MdOutlineStarOutline />
                        <MdOutlineStarOutline />
                        <MdOutlineStarOutline />
                    </div>
                    {/* <p className='text-base font-semibold'>
                      {`(${productData?.reviews} оценок)`}
                    </p> */}
                    <p className='text-base font-semibold'>
                      {`(5 оценок)`}
                    </p>
                  </div>
                </div>
                <text>{productData.description}</text>
                <AddToCartBtn 
                  product={productData}
                  title='Buy now' 
                  className='bg-black/80 py-3 text-base text-gray-200 hover:scale-100 
                  hover:text-white duration-200'/>
              </div>
            </div>
          ) : (
            <div>
              <p className='text-4xl font-semibold mb-5 text-center'>
                Коллекция продуктов
              </p>
              <div className='grid grid-cols-2 md:grid-cols-3 lg:grid-cols-5 gap-5'>
                {allProducts?.map((item:ProductProps) => (
                  <ProductCard item={item} key={item?.id}/>
                ))}
              </div>
            </div>
          )}
        </Container>
      }
    </div>
  )
}

export default Product
