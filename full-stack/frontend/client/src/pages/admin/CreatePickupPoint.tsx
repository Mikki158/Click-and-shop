import { useState, useEffect } from 'react'
import { useNavigate } from "react-router-dom"
import apiClient from "../../apiClient";
import { IoMdClose } from 'react-icons/io';
import { Dialog } from '@headlessui/react';

const CreatePickupPoint = ({ isOpen, onClose }) => {

    const [address, setAddress] = useState("");
    const [phoneNumber, setPnoneNimber] = useState<number>();

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        if (!address || !phoneNumber) {
            alert("Пожалуйста, заполните все поля");
            return;
        }

        const data = {
            "address": address,
            "phoneNumber": phoneNumber
            }
        apiClient.post("/api/pickup_point", data)
        .then((response) => {
            console.log(response)
        })
        .catch((error) => {
            console.error("Ошибка при создании пункта выдачи" + error);
        })
    };

    return (
        <Dialog open={isOpen} onClose={onClose} className="fixed inset-0 z-50 flex items-center justify-center p-4">
            <div className="fixed inset-0 bg-black opacity-40" />
            <div className="relative z-50 max-w-md w-full bg-white rounded-xl shadow-xl p-6 space-y-4">
                <div className="flex justify-between items-start">
                    <h1 className="text-2xl font-bold mb-6">Создание пункта выдачи</h1>
                    <button onClick={onClose}>
                        <IoMdClose size={22} className="text-gray-500 hover:text-black" />
                    </button>
                </div>
                

                <form onSubmit={handleSubmit} className="space-y-4">
                <div>
                    <label className="block font-medium">Адрес пункта выдачи</label>
                    <input
                    type="text"
                    className="border w-full px-4 py-2 rounded"
                    value={address}
                    onChange={(e) => setAddress(e.target.value)}
                    placeholder="Введите адрес"
                    required
                    />
                </div>

                <div>
                    <label className="block font-medium">Контактный номер телефона</label>
                    <input
                    type="text"
                    className="border w-full px-4 py-2 rounded"
                    value={phoneNumber}
                    onChange={(e) => setPnoneNimber(e.target.value)}
                    placeholder="Введите номер телефона"
                    required
                    />
                </div>

                <button
                    type="submit"
                    className="bg-blue-600 text-white px-6 py-2 rounded hover:bg-blue-700"
                >
                    Создать пункт выдачи
                </button>
                </form>
            </div>
        </Dialog>
    );
}

export default CreatePickupPoint;