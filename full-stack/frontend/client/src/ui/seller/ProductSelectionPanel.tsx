import React, { useState } from "react";
import { Dialog } from '@headlessui/react';

const ProductSelectionPanel = ({ isOpen, onClose }) => {
  const [products, setProducts] = useState([
    { id: 1, selected: false, quantity: 1 },
    { id: 2, selected: false, quantity: 1 },
    { id: 3, selected: false, quantity: 1 },
    { id: 4, selected: false, quantity: 1 },
  ]);

  const [perPage, setPerPage] = useState(10);
  const [page, setPage] = useState(1);

  const toggleSelect = (id: number) => {
    setProducts((prev) =>
      prev.map((p) =>
        p.id === id ? { ...p, selected: !p.selected } : p
      )
    );
  };

  const handleQuantityChange = (id: number, value: number) => {
    setProducts((prev) =>
      prev.map((p) => (p.id === id ? { ...p, quantity: value } : p))
    );
  };

  const handleApply = () => {
    const selected = products.filter((p) => p.selected);
    alert(`Добавлено ${selected.reduce((sum, p) => sum + p.quantity, 0)} товаров`);
  };

  const handleCancel = () => {
    onClose()
  };

  return (
    <Dialog open={isOpen} onClose={onClose} className="fixed inset-0 z-50 flex items-center justify-center p-4">
        <div className="p-4 bg-gray-300 rounded space-y-4 text-sm">
            <h2 className="font-medium">Добавьте товары</h2>

            <div className="overflow-x-auto">
                <table className="w-full text-left bg-gray-200">
                <thead>
                    <tr>
                    <th className="p-2"> </th>
                    <th className="p-2">Фото</th>
                    <th className="p-2">Предмет</th>
                    <th className="p-2">Количество</th>
                    <th className="p-2">Артикул</th>
                    <th className="p-2">Размер</th>
                    <th className="p-2">Цвет</th>
                    </tr>
                </thead>
                <tbody>
                    {products.map((item) => (
                    <tr key={item.id} className="border-t">
                        <td className="p-2">
                        <input
                            type="checkbox"
                            checked={item.selected}
                            onChange={() => toggleSelect(item.id)}
                        />
                        </td>
                        <td className="p-2">
                        <div className="w-12 h-12 bg-gray-400 flex items-center justify-center">
                            Фото
                        </div>
                        </td>
                        <td className="p-2">Название</td>
                        <td className="p-2">
                        <input
                            type="number"
                            min={1}
                            value={item.quantity}
                            onChange={(e) => handleQuantityChange(item.id, +e.target.value)}
                            className="w-20 p-1 border"
                        />
                        </td>
                        <td className="p-2">Артикул</td>
                        <td className="p-2">Размер</td>
                        <td className="p-2">Цвет</td>
                    </tr>
                    ))}
                </tbody>
                </table>
            </div>

            {/* Управление */}
            <div className="flex flex-wrap items-center justify-between gap-4">
                <div className="space-x-2">
                <button onClick={handleApply} className="bg-white px-4 py-1 border">
                    Применить
                </button>
                <button onClick={handleCancel} className="bg-white px-4 py-1 border">
                    Отменить
                </button>
                </div>

                <div className="text-sm flex-grow text-center">
                Добавлено {products.filter((p) => p.selected).reduce((sum, p) => sum + p.quantity, 0)} товаров
                </div>

                <div className="flex items-center space-x-2">
                <span>Показывать записей</span>
                <select
                    value={perPage}
                    onChange={(e) => setPerPage(+e.target.value)}
                    className="border p-1"
                >
                    {[10, 20, 25, 50, 100].map((num) => (
                    <option key={num} value={num}>
                        {num}
                    </option>
                    ))}
                </select>
                <input
                    type="number"
                    min={1}
                    value={page}
                    onChange={(e) => setPage(+e.target.value)}
                    className="border w-12 text-center"
                />
                </div>
            </div>
        </div>
    </Dialog>
  );
};

export default ProductSelectionPanel;
