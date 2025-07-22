import { useState, useEffect } from 'react'
import { useNavigate } from "react-router-dom"
import apiClient from "../../apiClient";
import CreateWarehouse from '../../ui/admin/CreateWarehouse';

const Warehouse = () => {
  
  const [warehouses, setWarehouses] = useState<any[]>([]); // Состояние для хранения списка товаров
  const navigate = useNavigate()

  const [isModalOpen, setIsModalOpen] = useState(false);

  const newWarehouse = () => {
    setIsModalOpen(true)
  }

  useEffect(() => {
      apiClient.get('/api/warehouse')
      .then((response) => {
        console.log("Warehouse", response)
        setWarehouses(response.data)
      })
      .catch((error) => {
        console.error("Ошибка при получении складов ", error)
      })
  }, [])
  

  return (
    <div className="p-4 bg-gray-100 min-h-screen">
      <div className="flex justify-between items-start mb-8">
        <h1 className="text-2xl font-semibold">Склады</h1>
      </div>
      
      {/* Кнопка "Добавить" */}
      <div className="flex justify-between items-center mb-4">
        <button 
          onClick={newWarehouse}
          className="bg-gray-300 px-4 py-2 rounded-md hover:bg-gray-400">
          Добавить
        </button>
      </div>

      {/* Таблица товаров */}
      <div className="border-t border-gray-300">
        <div className="grid grid-cols-9 items-center text-gray-600 text-sm font-semibold py-2 border-b">
          <div className="col-span-1">id склада</div>
          <div className="col-span-2">Адрес</div>
          <div className="col-span-1">Вместимость</div>
          <div className="col-span-1 text-center">Действия</div>
        </div>

        {/* Пример строки товара */}
        {warehouses.map((warehouse, index) => (
          <div
            key={warehouse.id || index}
            className="grid grid-cols-9 items-center text-sm py-3 border-b"
          >
            
            <div className="col-span-1">
              {/* Название и артикул товара */}
              <p className="font-semibold">{warehouse.id}</p>
            </div>
            <div className="col-span-2">
              {/* Остаток товара */}
              <p className="text-gray-500">{warehouse.address}</p>
            </div>
            <div className="col-span-1">
              {/* Дата изменения */}
              <p className="text-gray-500">{warehouse.capacity}</p>
            </div>
            <div className="col-span-1 flex justify-center space-x-2">
              {/* Кнопки управления */}
              <button 
                className="text-blue-500 hover:underline"
                //onClick={() => handleButtonEdit(product.id)}
              >Редактировать</button>

              <button 
                className="text-red-500 hover:underline"
                //onClick={() => handleButtonDelete(product.id)}
              >Удалить</button>
            </div>
          </div>
        ))}
      </div>

      {isModalOpen && (
        <CreateWarehouse 
        isOpen={isModalOpen}
        onClose={() => setIsModalOpen(false)}/>
      )}
    </div>
  )
};

export default Warehouse;
