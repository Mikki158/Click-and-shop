import React, { useState } from "react";
import ProductSelectionPanel from "../../ui/seller/ProductSelectionPanel"

const CreateSupply = () => {

    const [isModalOpen, setIsModalOpen] = useState(false);

    const [items, setItems] = useState([]);

    const handleAddItem = () => {
        setIsModalOpen(true)
    };

    const handleDeleteItem = (id: number) => {
        setItems(items.filter((item) => item.id !== id));
    };

    return (
        <div className="p-6 space-y-6">
            {/* Фильтры */}
            <div className="flex flex-wrap gap-4 items-center">
                <select className="border p-2 min-w-[160px]">
                <option>Склад</option>
                </select>
                <input type="date" className="border p-2 min-w-[160px]" />
                <button
                onClick={handleAddItem}
                className="bg-gray-200 hover:bg-gray-300 px-4 py-2 rounded text-sm"
                >
                Добавить товар
                </button>
            </div>

            {/* Таблица товаров */}
            <div className="bg-gray-100 rounded p-4 overflow-x-auto">
                <table className="w-full text-sm">
                <thead>
                    <tr className="text-left text-gray-700">
                    <th className="p-2">Фото</th>
                    <th className="p-2">Предмет</th>
                    <th className="p-2">Количество</th>
                    <th className="p-2">Артикул</th>
                    <th className="p-2">Размер</th>
                    <th className="p-2">Цвет</th>
                    <th className="p-2">Удалить</th>
                    </tr>
                </thead>
                <tbody>
                    {items.map((item) => (
                    <tr key={item.id} className="border-t">
                        <td className="p-2">
                        <div className="w-12 h-12 bg-gray-300 flex items-center justify-center">
                            Фото
                        </div>
                        </td>
                        <td className="p-2">
                        <input
                            className="border p-1 w-full"
                            placeholder="Название"
                        />
                        </td>
                        <td className="p-2">
                        <input
                            className="border p-1 w-20"
                            type="number"
                            placeholder="Кол-во"
                        />
                        </td>
                        <td className="p-2">
                        <input
                            className="border p-1 w-full"
                            placeholder="Артикул"
                        />
                        </td>
                        <td className="p-2">
                        <input
                            className="border p-1 w-full"
                            placeholder="Размер"
                        />
                        </td>
                        <td className="p-2">
                        <input
                            className="border p-1 w-full"
                            placeholder="Цвет"
                        />
                        </td>
                        <td className="p-2">
                        <button
                            onClick={() => handleDeleteItem(item.id)}
                            className="text-red-500 hover:underline"
                        >
                            Удалить
                        </button>
                        </td>
                    </tr>
                    ))}
                </tbody>
                </table>
            </div>

            {/* Кнопка создания */}
            <div>
                <button className="bg-gray-200 hover:bg-gray-300 px-6 py-2 rounded">
                Создать поставку
                </button>
            </div>
            {
                isModalOpen && (
                    <ProductSelectionPanel
                    isOpen={isModalOpen}
                    onClose={() => setIsModalOpen(false)}/>
                )
            }
        </div>
    );
};

export default CreateSupply;
