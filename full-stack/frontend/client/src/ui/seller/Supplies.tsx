import { useNavigate } from "react-router-dom";

const Supplies = () => {
    const supplies = [
    { id: "12345", date: "01.06.2025", totalItems: 12, warehouse: "Склад 1", status: "В пути" },
    { id: "67890", date: "30.05.2025", totalItems: 8, warehouse: "Склад 2", status: "Доставлено" },
  ];

  const navigate = useNavigate();



  return (
    <div className="p-6">
      {/* Заголовок и кнопка */}
      <div className="flex justify-between items-start mb-8">
        <h1 className="text-2xl font-semibold">Поставки</h1>
        <button
            onClick={() => navigate("/seller/createSupply")}
            className="bg-gray-200 hover:bg-gray-300 text-black font-medium py-2 px-4 rounded">
          Новая поставка
        </button>
      </div>

      {/* Подзаголовок */}
      <h2 className="text-xl font-medium mb-4">Все поставки</h2>

      {/* Таблица */}
      <div className="overflow-x-auto">
        <table className="min-w-full table-auto border-t border-gray-300">
          <thead className="text-left text-sm text-gray-600">
            <tr>
              <th className="py-2 pr-4">Номер поставки</th>
              <th className="py-2 pr-4">Дата создание</th>
              <th className="py-2 pr-4">Всего товаров</th>
              <th className="py-2 pr-4">Склад</th>
              <th className="py-2 pr-4">Статус</th>
            </tr>
          </thead>
          <tbody className="text-sm text-black">
            {supplies.map((supply, index) => (
              <tr key={index} className="border-t border-gray-200">
                <td className="py-2 pr-4">{supply.id}</td>
                <td className="py-2 pr-4">{supply.date}</td>
                <td className="py-2 pr-4">{supply.totalItems}</td>
                <td className="py-2 pr-4">{supply.warehouse}</td>
                <td className="py-2 pr-4">{supply.status}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default Supplies;