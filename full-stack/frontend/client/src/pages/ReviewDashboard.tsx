import { useEffect, useState } from 'react';
import apiClient from "../apiClient";
import { FaStar } from "react-icons/fa";
import Container from '../ui/Container';
import { ProductProps, ReviewProps } from '../../type';
import StarRating from '../ui/StarRating';
import ReviewModal from '../ui/ReviewModal';
import { Link } from 'react-router-dom';
// import { MoreVertical } from "lucide-react";

interface ReviewDetails extends ReviewProps{
    productImage: string,
    productName: string
}

const ReviewDashboard = () => {

    const [products, setProducts] = useState<ProductProps[]>([]);
    const [productIds, setProductIds] = useState<number[]>([]);

    const [rating, setRating] = useState<number>(0);
    const [selectedRating, setSelectedRating] = useState<number | null>(null);

    const [isModalOpen, setIsModalOpen] = useState(false);
    const [selectedProduct, setSelectedProduct] = useState(null);

    const [menuOpen, setMenuOpen] = useState(false);

    const [reviewDetails, setReviewDetails] = useState<ReviewDetails[]>([])

    const [photoUrl, setPhotoUrl] = useState("")
    const [username, setUsername] = useState('')

    const handleRatingChange = (rating, product) => {
        setSelectedRating(rating)
        setSelectedProduct(product);
        setIsModalOpen(true);
    };

    const [selectedTab, setSelectedTab] = useState<'waiting' | 'created'>('waiting');

    const [reviews, setReviews] = useState<ReviewProps[]>([])

    useEffect(() => {
        const fetchProducts = async () => {
            try {
                console.log("Отзывы2", productIds)
                const responses = await Promise.all(
                    productIds.map((id) => apiClient.get(`/api/product/${id}`))
                );
                const productsData = responses.map((res) => res.data);
                console.log("Товары для новых отзывов", productsData)
                setProducts(productsData);
            } catch (error) {
                console.error('Ошибка при загрузке товаров:', error);
                return [];
            }
        };

        fetchProducts();

    }, [productIds])

    useEffect(() => {
        const fetchProduct = async () => {
            try {
                await apiClient.get('/api/product/notReviewProducts')
                    .then((res) => {
                        console.log("Отзывы1", res.data)
                        setProductIds(res.data)
                    })
                    .catch((err) => {
                        console.error('Error fetching data ', err);
                    })

                //setProductIds(productsRepository.data)
                
                

            } catch (err) {
                console.error("Ошибка при загрузке отзывов:", err);
            }
        } 

        fetchProduct()
    }, [])

    useEffect(() => {
        const fetchReview = async () => {
            const response = await apiClient.get('/api/product/review')

            setReviews(response.data)

            const reviews = response.data;

            console.log("REVIEWS", reviews)
            
            const newReviewDetails = await Promise.all(
                reviews.map(async (review: {id: string}) => {
                    const productResponse = await apiClient.get(`/api/product/${review.productId}`);

                    console.log("Товар для отзыва", productResponse)

                    return {
                        ...review,
                        productImage: productResponse.data.imagePaths[0],
                        productName: productResponse.data.name
                    };
                })
            )

            setReviewDetails(newReviewDetails)
            console.log("newReviewDetails", newReviewDetails)
        }

        const fetchUserInfo = async () => {
            const response = await apiClient.get('/api/auth/userInfo')
            .then((response) => {
                setPhotoUrl(response.data.photoUrl)
                setUsername(response.data.username)
            })
            .catch((error) => {
                console.error("Ошибка при получении фотографии пользователя ", error)
            })
        }

        fetchUserInfo()
        fetchReview()
    }, [])

    function formatDate(dateStr: string): string {
        const date = new Date(dateStr);
        const formatted = date.toISOString().slice(0, 16).replace('T', ' ');
        return formatted;
    };

  return (
    <>
        <Container>
            <div className="flex min-h-screen p-4">
                {/* Левая колонка */}
                <div className="w-1/4 bg-white rounded-xl shadow p-6 mr-6">
                    <div className="flex flex-col items-center">
                    <img 
                    src={photoUrl} 
                    alt="User Avatar" 
                    className="w-24 h-24 rounded-full border border-gray-300"
                    />
                    <p className="font-semibold text-lg">{username}</p>
                    <div className="mt-4 flex w-full justify-around">
                        <div className="text-center">
                            <p className="text-xl font-bold">{reviews.length}</p>
                            <p className="text-sm text-gray-500">Отзывов написано</p>
                        </div>
                        
                    </div>
                    </div>
                </div>

                {/* Правая колонка */}
                <div className="w-3/4">
                    <div className="flex gap-4 mb-4">
                    <button 
                        onClick={() => setSelectedTab('waiting')}
                        className={`font-semibold text-lg border-b-2 ${
                            selectedTab === 'waiting' ? 'text-black border-black' : 'text-gray-400 border-transparent'
                        }`}>
                            Ждут отзыва
                    </button>
                    <button 
                        onClick={() => setSelectedTab('created')}
                        className={`font-semibold text-lg border-b-2 ${
                            selectedTab === 'created' ? 'text-black border-black' : 'text-gray-400 border-transparent'
                        }`}>
                        Отзывы
                    </button>
                    </div>

                    {
                        selectedTab.includes("waiting") ? (
                            <div className="bg-white p-4 rounded-xl shadow grid grid-cols-1 md:grid-cols-2 gap-6">
                                {products.map((product) => (
                                    <div key={product.id} className="flex gap-4">
                                        <img
                                            src={product.imagePaths[0]}
                                            alt=""
                                            className="w-20 h-20 object-cover rounded-md"
                                        />
                                        <div>
                                            <p className="font-medium leading-tight">{product.name}</p>
                                            <p className="text-sm text-gray-600 mb-1 leading-tight">
                                            {product.description}
                                            </p>
                                            <p className="text-sm text-gray-400 mb-1">
                                            {/* Доставлен {product.delivered} */}
                                            </p>
                                            <div className="flex gap-1">
                                                <StarRating onRate={(rating) => handleRatingChange(rating, product)} />
                                            </div>
                                        </div>
                                    </div>
                                ))}
                            </div>
                        ) : (
                            <div className="bg-white p-4 rounded-xl shadow grid grid-cols-1 md:grid-cols-1 gap-6">
                                {reviewDetails.map((review) => (
                                    <div className="flex items-start justify-between p-4 bg-white rounded-xl shadow-sm">
                                        {/* Левая часть */}
                                        <Link to={`/product/${review.productId}`}>
                                            <div className="flex items-start space-x-3">
                                                {/* Фото товара */}
                                                <img
                                                src={review.productImage}
                                                alt={review.productTitle}
                                                className="w-12 h-12 rounded-md object-cover"
                                                />

                                                {/* Информация о товаре и отзыве */}
                                                <div className="flex flex-col space-y-1">
                                                    {/* Статус */}
                                                    <span className="text-xs text-white bg-orange-500 rounded px-1.5 py-0.5 w-fit font-semibold">
                                                        {review.status}
                                                    </span>

                                                    {/* Название */}
                                                    <span className="font-semibold text-sm">{review.productName}</span>

                                                    {/* Доп. информация */}
                                                    <div className="flex items-center space-x-2 text-xs ">
                                                        <span className="  py-0.5 rounded-full font-medium">
                                                            {review.reviewText}
                                                        </span>
                                                    </div>
                                                </div>
                                            </div>
                                        </Link>

                                        {/* Правая часть */}
                                        <div className="flex flex-col items-end justify-between h-full ml-4">
                                            {/* Рейтинг */}
                                            <div className="flex items-center space-x-1 text-orange-400 text-sm">
                                                {Array.from({ length: 5 }, (_, index) => (
                                                    <FaStar key={index} className={index < review.rating ? "" : "text-gray-300"} />
                                                ))}
                                            </div>
                                            {
                                                review.updated != null ? (
                                                    <span className="text-xs text-gray-500 mt-1">{formatDate(review.updated)}</span>
                                                ) : (
                                                    <span className="text-xs text-gray-500 mt-1">{formatDate(review.created)}</span>
                                                )
                                            }
                                            {/* Время */}
                                            {/* <span className="text-xs text-gray-500 mt-1">{formatDate(review.created)}</span> */}

                                            {/* Три точки */}
                                            <button className="text-gray-400 hover:text-black mt-2">
                                                {/* <MoreVertical size={18} /> */}
                                                ::
                                            </button>
                                        </div>
                                    </div>
                                ))}
                            </div>
                        )
                    }
                </div>
            </div>
            {isModalOpen && selectedProduct && (
                <ReviewModal
                isOpen={isModalOpen}
                onClose={() => setIsModalOpen(false)}
                product={selectedProduct}
                fromrating={selectedRating}
                />
            )}
            
        </Container>
    </>
  );
};

export default ReviewDashboard;
