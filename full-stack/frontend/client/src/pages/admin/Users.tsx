import { useEffect, useState } from "react";
import apiClient from "../../apiClient";

interface User {
    userId: string,
    username: string,
    firstName: string;
    photoUrl: string,
    role: string;
}

interface Request {
    id: string,
    userId: number,
    username: string,
    firstName: string,
    brand: string,
    comment: string
}

const Users = () => {
    const [users, setUsers] = useState<User[]>([]);
    const [requests, setRequests] = useState<Request[]>([]);

    const [selectedTab, setSelectedTab] =  useState<'users' | 'requests'>('users');

    useEffect(() => {
        apiClient.get("/api/auth/users")
        .then((response) => {
            setUsers(response.data)
            console.log(response.data)
        })
        .catch((error) => {
            console.error("Ошибка получения пользователей, ", error)
        })

        apiClient.get('/api/seller/requestSeller')
        .then((response) => {
            console.log("Requests ", response.data)
            setRequests(response.data)
        })
        .catch((error) => {
            console.error("Ошибка при получении заявок на продавца ", error)
        })
    }, []);

    const approveRequests = (id: string) => {
        const formData = new FormData();
        formData.append('requestId', id);

        apiClient.post('/api/seller/approveRequest', formData)
        .then((response) => {
            console.log("Заявка одобрена")
        })
        .catch((error) => {
            console.error("Ошибка при одобрении заявки ", error)
        })
    }

    const rejectRequests = (id: string) => {
        const formData = new FormData();
        formData.append('requestId', id);

        apiClient.post('/api/seller/rejectRequest', formData)
        .then((response) => {
            console.log("Заявка одобрена")
        })
        .catch((error) => {
            console.error("Ошибка при одобрении заявки ", error)
        })
    }

    return (
        <div className="p-8">
            <div className="flex gap-8">
                <button 
                    onClick={() => setSelectedTab("users")}
                    className={`text-2xl font-bold mb-4 ${
                    selectedTab == 'users' ? 'text-black border-black' : 'text-gray-400 border-transparent'
                }`}>Список пользователей</button>
                <button 
                    onClick={() => setSelectedTab("requests")}
                    className={`text-2xl font-bold mb-4 ${
                    selectedTab == 'requests' ? 'text-black border-black' : 'text-gray-400 border-transparent'
                }`}>Заявки пользователей</button>
            </div>

            {
                selectedTab.includes("users") ? (
                    <table className="min-w-full bg-white border border-gray-200 rounded-lg">
                        <thead>
                        <tr className="bg-gray-100 text-left text-sm font-semibold text-gray-700">
                            <th className="p-3">user id</th>
                            <th className="p-3">username</th>
                            <th className="p-3">first name</th>
                            <th className="p-3">photo url</th>
                            <th className="p-3">Роль</th>
                        </tr>
                        </thead>
                        <tbody>
                        {users.map((user) => (
                            <tr key={user.userId} className="border-t text-sm">
                            <td className="p-3">{user.userId}</td>
                            <td className="p-3">{user.username}</td>
                            <td className="p-3">{user.firstName}</td>
                            <td className="p-3">{user.photoUrl}</td>
                            <td className="p-3">{user.role}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                ) : (
                    <table className="min-w-full bg-white border border-gray-200 rounded-lg">
                        <thead>
                        <tr className="bg-gray-100 text-left text-sm font-semibold text-gray-700">
                            <th className="p-3">id заявки</th>
                            <th className="p-3">user id</th>
                            <th className="p-3">username</th>
                            <th className="p-3">first name</th>
                            <th className="p-3">название магазина</th>
                            <th className="p-3">комментарий</th>
                            <th className="p-3 flex justify-center space-x-2">действие</th>
                        </tr>
                        </thead>
                        <tbody>
                        {requests.map((request) => (
                        <tr key={request.id} className="border-t text-sm">
                            <td className="p-3">{request.id}</td>
                            <td className="p-3">{request.userId}</td>
                            <td className="p-3">{request.username}</td>
                            <td className="p-3">{request.firstName}</td>
                            <td className="p-3">{request.brand}</td>
                            <td className="p-3">{request.comment}</td>
                            <td className="p-3">
                                <div className="flex justify-center space-x-2">
                                    <button
                                        className="text-blue-500 hover:underline"
                                        onClick={approveRequests(request.id)}>
                                        Одобрить
                                    </button>
                                    <button
                                        className="text-red-500 hover:underline"
                                        onClick={rejectRequests(request.id)}>
                                        Отклонить
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

export default Users;