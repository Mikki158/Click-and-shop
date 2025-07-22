import { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import { config } from '../../config'
import { BrandProps, ProductProps } from '../../type'
import { getData } from '../lib'
import Loading from '../ui/Loading'
import Container from '../ui/Container'
import _ from "lodash";
import PriceTag from '../ui/PriceTag'
import { MdOutlineStar, MdOutlineStarBorder } from "react-icons/md";
import { FaStar } from "react-icons/fa";
import AddToCartBtn from '../ui/AddToCartBtn'
import ProductCard from '../ui/ProductCard'
import apiClient from '../apiClient'
import ImageModal from '../ui/ImageModal'

const Product = () => {

  type Review = {
    userId: number;
    rating: number;
    reviewText: string;
    imagePaths: string[];
  };

  const [productData, setProductData] = useState<ProductProps | null>(null);
  const [allProducts, setAllProducts] = useState<ProductProps[]>([]);
  const [loading, setLoading] = useState(false);
  const [imgUrl, setImgUrl] = useState("")
  const {id} = useParams();
  const [fullCategory, setFullCategory] = useState("")
  const [brand, setBrand] = useState<BrandProps | null>(null);
  const [quantity, setQuantity] = useState("");
  const totalStars = 5;
  const [reviews, setReview] = useState<Review[]>(null);
  const [modalOpen, setModalOpen] = useState(false);
  const [modalImages, setModalImages] = useState<string[]>([]);
  const [modalIndex, setModalIndex] = useState(0);

  const endpoint = id 
  ? `/api/product/${id}` 
  : `/api/product`

  useEffect(() => {
    const fetchData = async() => {
      try {
        setLoading(true);
        const response = await apiClient.get(endpoint)

        console.log("DATA", response.data)

        const data = response.data

        if(id) {
          setProductData(data);
          setAllProducts([]);
        } else {
          setAllProducts(data);
          setProductData(null);            
        }

        document.title = data.name

        if(data.categoryId) {
          const categoryRepository = await apiClient.get(`/api/category/${data?.categoryId}`)
          setFullCategory(categoryRepository.data);
          console.log(categoryRepository.data)
        }

        if(data.brandId) {
          const brandRepository = await apiClient.get(`/api/seller/brand/${data?.brandId}`)
          setBrand(brandRepository.data);
          console.log("Brands", brandRepository.data)
        }

        const quantityRepository = await apiClient.get(`api/supply/product/${data?.id}`);
        setQuantity(quantityRepository.data)

        const reviewRepository = await apiClient.get(`/api/product/${id}/review`)
        setReview(reviewRepository.data)
        console.log("Review", reviewRepository);
        
      } catch (error) {
        console.error('Error fetching data', error);          
      } finally {
        setLoading(false);
      } 
    };

    fetchData();

    // apiClient.get(`/api/category/${productData?.categoryId}`)
    // .then((response) => {
    //   setFullCategory(response.data)
    //   console.log(response)
    // })
    // .catch((error) => {
    //   console.error("Ошибка при получени полной категории ", error);
    // })      

  }, [id, endpoint]);

  useEffect(() => {
    if(productData) {
      setImgUrl(productData?.imagePaths[0])
    }
    
  },[productData])

  function pluralizeReview(count: number): string {
    const mod10 = count % 10;
    const mod100 = count % 100;

    if (mod10 === 1 && mod100 !== 11) return `${count} оценка`;
    if (mod10 >= 2 && mod10 <= 4 && (mod100 < 10 || mod100 >= 20)) return `${count} оценки`;
    return `${count} оценок`;
  }

  const renderStars = (rating: number) => {
    return (
      <div className="flex text-yellow-400">
        {Array.from({ length: 5 }, (_, index) => (
          <FaStar key={index} className={index < rating ? "" : "text-gray-300"} />
        ))}
      </div>
    );
  };

  const openModal = (index: number) => {
    setModalIndex(index);
    setModalOpen(true);
  };

  const handleImageClick = (images: string[], index: number) => {
    setModalImages(images);
    setModalIndex(index);
    setModalOpen(true);
    console.log("ModalImages", images)
    console.log("ModalIndex", index)
  };

  const onClose = () => {
    setModalOpen(false);
  };

  function formatDate(dateStr: string): string {
    const date = new Date(dateStr);
    const formatted = date.toISOString().slice(0, 16).replace('T', ' ');
    return formatted;
  };


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
                <label>{fullCategory}</label>
                <label>{brand?.brandName}</label>
                <h2 className='text-3xl font-bold'>{productData?.name}</h2>
                <div className='flex items-center justify-between'>
                  <PriceTag 
                    regularPrice={productData?.regularPrice}
                    discountedPrice={productData?.discountedPrice}
                    className="text-xl"
                  />
                  <div className='flex items-center gap-1'>
                    <div className='text-base text-lightText flex items-center'>
                        {/* <MdOutlineStarOutline />
                        <MdOutlineStarOutline />
                        <MdOutlineStarOutline />
                        <MdOutlineStarOutline />
                        <MdOutlineStarOutline /> */}
                      
                    </div>
                    <div className="flex items-center text-yellow-500">
                      {renderStars(productData.rating)}
                    </div>
                    {/* <p className='text-base font-semibold'>
                      {`(${productData?.reviews} оценок)`}
                    </p> */}
                    <p className='text-base font-semibold'>
                      {`(${pluralizeReview(productData.ratingCount)})`}
                    </p>
                  </div>
                </div>
                <text>{productData.description}</text>
                <AddToCartBtn 
                  product={productData}
                  title='Купить сейчас' 
                  className='bg-black/80 py-3 text-base text-gray-200 hover:scale-100 
                  hover:text-white duration-200'/>
                В наличии {quantity} штук
              </div>
              <div className="space-y-6 mt-8">
                {reviews.map((review, index) => (
                  <div
                    key={index}
                    className="border rounded-xl p-4 shadow-sm bg-white"
                  >
                    <div className="flex items-center justify-between mb-2">
                      <div className='flex items-center gap-2 mb-2'>
                        <img 
                          src={review.photoUrl} 
                          alt="User Avatar"
                          className='w-12 h-12 rounded-full border border-gray-300' 
                        />
                        <div>
                          <p className="text-sm text-gray-700 font-medium">Пользователь #{review.username}</p>
                          <p className="text-gray-500 text-xs">{formatDate(review.created)}</p>
                        </div>
                      </div>
                      {renderStars(review.rating)}
                    </div>
                    <p className="text-gray-800 mb-2">{review.reviewText}</p>
                    <div className="flex flex-wrap gap-2">
                      {Array.isArray(review.imagePaths) && review.imagePaths.map((url, i) => (
                        <img
                          key={i}
                          src={url}
                          alt={`review-img-${i}`}
                          className="w-20 h-30 object-cover cursor-pointer rounded"
                          onClick={() => handleImageClick(review.imagePaths, i)}
                        />
                      ))}
                    </div>
                    {modalOpen && review && (
                      <div className="fixed inset-0 bg-black/70 z-50 flex items-center justify-center">
                        <div className="relative bg-white rounded-md shadow-lg max-w-6xl w-full h-[80vh] flex overflow-hidden">

                          {/* Кнопка закрытия */}
                          <button
                            className="absolute top-4 right-4 text-gray-600 text-2xl z-10"
                            onClick={onClose}
                          >
                            ✖
                          </button>

                          {/* Фото + навигация */}
                          <div className="w-1/2 bg-black flex items-center justify-center relative">
                            <button
                              onClick={() =>
                                setModalIndex((prev) => (prev > 0 ? prev - 1 : modalImages.length - 1))
                              }
                              className="absolute left-4 text-white text-3xl z-10"
                            >
                              ‹
                            </button>
                            <img
                              src={modalImages[modalIndex]}
                              alt="modal"
                              className="max-h-[75vh] object-contain rounded"
                            />
                            <button
                              onClick={() =>
                                setModalIndex((prev) => (prev < modalImages.length - 1 ? prev + 1 : 0))
                              }
                              className="absolute right-4 text-white text-3xl z-10"
                            >
                              ›
                            </button>
                          </div>

                          {/* Текст отзыва */}
                          <div className="w-1/2 p-6 overflow-y-auto text-black text-sm">
                            <div className="flex items-center gap-2 mb-2">
                              <img 
                                src={review.photoUrl} 
                                alt="User Avatar"
                                className='w-12 h-12 rounded-full border border-gray-300' 
                              />
                              <div>
                                <p className="font-semibold">{review.username || 'Пользователь'}</p>
                                <p className="text-gray-500 text-xs">{formatDate(review.created)}</p>
                              </div>
                              {renderStars(review.rating)}
                            </div>

                            {/* Звёзды */}
                            {/* <div className="flex items-center gap-1 text-yellow-500 mb-4">
                              {renderStars(review.rating)}
                            </div> */}
                            <div>
                              <p className="font-bold">Комментарий:</p>
                              <p>{review.reviewText}</p>
                            </div>
                          </div>
                        </div>
                      </div>
                    )}
                  </div>
                ))}
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
          {productData?.reviews && productData.reviews.length > 0 && (
            <div className="mt-10">
              <h3 className="text-2xl font-semibold mb-4">Отзывы</h3>
              <div className="space-y-4">
                {productData.reviews.map((review, index) => (
                  <div key={index} className="border p-4 rounded shadow-sm">
                    <div className="flex justify-between items-center mb-1">
                      <p className="font-semibold">{review.user}</p>
                      <div className="flex gap-1 text-yellow-500">
                        {Array.from({ length: 5 }).map((_, i) => (
                          <span key={i}>
                            {i < review.rating ? "★" : "☆"}
                          </span>
                        ))}
                      </div>
                    </div>
                    <p className="text-sm text-gray-700">{review.comment}</p>
                  </div>
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
