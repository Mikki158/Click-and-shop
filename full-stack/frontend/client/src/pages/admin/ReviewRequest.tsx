import { useEffect, useState } from "react";
import apiClient from "../../apiClient";

interface Review {
    id: string,
    imagePaths: string[],
    productId: string,
    rating: number,
    reviewText: string,
    status: string,
    userId: number,
    username: string
}

const ReviewRequest = () => {
    const [checkingReviews, setCheckingReviews] = useState<Review[]>([]);
    const [approvedReviews, setApproverReviews] = useState<Review[]>([]);

    const [selectedTab, setSelectedTab] =  useState<'checking' | 'approved'>('checking');

    useEffect(() => {
        apiClient.get("/api/product/checkingReview")
        .then((response) => {
            setCheckingReviews(response.data)
            console.log("Checking review ", response.data)
        })
        .catch((error) => {
            console.error("Ошибка получения пользователей, ", error)
        })

        apiClient.get('/api/product/approvedReview')
        .then((response) => {
            console.log("Approved review ", response.data)
            setApproverReviews(response.data)
        })
        .catch((error) => {
            console.error("Ошибка при получении заявок на продавца ", error)
        })
    }, []);

    const approveRequests = async (id: string) => {

        const data = {
            "reviewId": id,
            "status": 2
        }

        try {
            await apiClient.post('/api/product/review', data)

            apiClient.get("/api/product/checkingReview")
            .then((response) => {
                setCheckingReviews(response.data)
                console.log("Checking review ", response.data)
            })
            .catch((error) => {
                console.error("Ошибка получения пользователей, ", error)
            })

            console.log("Заявка одобрена")
        } catch (error) {
            console.error("Ошибка при одобрении заявки ", error)
        }

    
    }

    const rejectRequests = async (id: string) => {
        
        try {
            await apiClient.delete(`/api/product/review/${id}`)

            apiClient.get("/api/product/checkingReview")
            .then((response) => {
                setCheckingReviews(response.data)
                console.log("Checking review ", response.data)
            })
            .catch((error) => {
                console.error("Ошибка получения пользователей, ", error)
            })

        } catch (error) {
            console.error("Ошибка при Удалении заявки ", error)
        }
    }

    return (
        <div className="p-8">
            <div className="flex gap-8">
                <button 
                    onClick={() => setSelectedTab("checking")}
                    className={`text-2xl font-bold mb-4 ${
                    selectedTab == 'checking' ? 'text-black border-black' : 'text-gray-400 border-transparent'
                }`}>Модерация отзывов</button>
                <button 
                    onClick={() => setSelectedTab("approved")}
                    className={`text-2xl font-bold mb-4 ${
                    selectedTab == 'approved' ? 'text-black border-black' : 'text-gray-400 border-transparent'
                }`}>Одобренные отзывы</button>
            </div>

            {
                selectedTab.includes("checking") ? (
                    <table className="min-w-full bg-white border border-gray-200 rounded-lg">
                        <thead>
                        <tr className="bg-gray-100 text-left text-sm font-semibold text-gray-700">
                            <th className="p-3">review id</th>
                            <th className="p-3">userId</th>
                            <th className="p-3">product id</th>
                            <th className="p-3">review text</th>
                            <th className="p-3">rating</th>
                            <th className="p-3">status</th>
                            <th className="p-3">photo utl</th>
                            <th className="p-3 flex justify-center space-x-2">действие</th>
                        </tr>
                        </thead>
                        <tbody>
                        {checkingReviews.map((review) => (
                        <tr key={review.id} className="border-t text-sm">
                            <td className="p-3">{review.id}</td>
                            <td className="p-3">{review.userId}</td>
                            <td className="p-3">{review.productId}</td>
                            <td className="p-3">{review.reviewText}</td>
                            <td className="p-3">{review.rating}</td>
                            <td className="p-3">{review.status}</td>
                            <td className="p-3 space-x-2">
                                {review.imagePaths.map((path, index) => (
                                    <img
                                    key={index}
                                    src={path}
                                    alt={`Изображение ${index + 1}`}
                                    className="w-16 h-16 object-cover inline-block rounded"
                                    />
                                ))}
                            </td>
                            <td className="p-3">
                                <div className="flex justify-center space-x-2">
                                    <button
                                        className="text-blue-500 hover:underline"
                                        onClick={() => approveRequests(review.id)}>
                                        Одобрить
                                    </button>
                                    <button
                                        className="text-red-500 hover:underline"
                                        onClick={() => rejectRequests(review.id)}>
                                        Отклонить
                                    </button>
                                </div>
                            </td>
                        </tr>
                        ))}
                        </tbody>
                    </table>
                ) : (
                    <table className="min-w-full bg-white border border-gray-200 rounded-lg">
                        <thead>
                        <tr className="bg-gray-100 text-left text-sm font-semibold text-gray-700">
                            <th className="p-3">review id</th>
                            <th className="p-3">userId</th>
                            <th className="p-3">product id</th>
                            <th className="p-3">review text</th>
                            <th className="p-3">rating</th>
                            <th className="p-3">status</th>
                            <th className="p-3">photo utl</th>
                            <th className="p-3 flex justify-center space-x-2">действие</th>
                        </tr>
                        </thead>
                        <tbody>
                        {approvedReviews.map((review) => (
                        <tr key={review.id} className="border-t text-sm">
                            <td className="p-3">{review.id}</td>
                            <td className="p-3">{review.userId}</td>
                            <td className="p-3">{review.productId}</td>
                            <td className="p-3">{review.reviewText}</td>
                            <td className="p-3">{review.rating}</td>
                            <td className="p-3">{review.status}</td>
                            <td className="p-3 space-x-2">
                                {review.imagePaths.map((path, index) => (
                                    <img
                                    key={index}
                                    src={path}
                                    alt={`Изображение ${index + 1}`}
                                    className="w-16 h-16 object-cover inline-block rounded"
                                    />
                                ))}
                            </td>
                            <td className="p-3">
                                <div className="flex justify-center space-x-2">
                                    <button
                                        className="text-red-500 hover:underline">
                                        Удалить
                                    </button>
                                </div>
                            </td>
                        </tr>
                        ))}
                        </tbody>
                    </table>
                )
            }
            
            
        </div>        
    )
}

export default ReviewRequest;