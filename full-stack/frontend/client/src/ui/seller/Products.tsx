import { useNavigate } from "react-router-dom"
import { useState, useEffect } from 'react'
import apiClient from "../../apiClient";
import { toast } from 'react-toastify'
import 'react-toastify/dist/ReactToastify.css'

const Products = () => {

  const [products, setProducts] = useState<any[]>([]); // Состояние для хранения списка товаров

  const navigate = useNavigate()

  const newProduct = () => {
    navigate('/seller/newProduct')
  }

  const handleButtonEdit = (id: number) => {
    console.log("Редактировать товар с id: ", id);

    navigate(`/seller/editProduct/${id}`)
  }

  const handleButtonDelete = async (id: number) => {
    console.log("Удалить товар с id: ", id)

    try {
      const response = await apiClient.delete(`/api/product/${id}`)
      toast.success('Товар был успешно удален!', {
        position: "top-right",
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: false,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
        theme: "light",
        });

      console.log(response.data)
      
      const response2 = await apiClient.get("/api/product/seller"); // Замените URL на ваш API
      console.log(response2)
      setProducts(response2.data); // Предполагается, что ответ содержит массив товаров
      
    } catch (error) {
      toast.error('Произошла ошибка при удалении товара', {
        position: "top-right",
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: false,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
        theme: "light",
        });
      console.error("Произошла ошибка при удалении товара: ", error)
    }
  }

  useEffect(() => {
    const fetchProducts = async () => {
      try {
        const response = await apiClient.get("/api/product/seller"); // Замените URL на ваш API
        console.log(response)
        setProducts(response.data); // Предполагается, что ответ содержит массив товаров
      } catch (error) {
        console.error("Ошибка при загрузке товаров:", error);
      } finally {

      }
    };

    fetchProducts();
  }, []);

  function pluralizeReview(count: number): string {
    const mod10 = count % 10;
    const mod100 = count % 100;

    if (mod10 === 1 && mod100 !== 11) return `${count} оценка`;
    if (mod10 >= 2 && mod10 <= 4 && (mod100 < 10 || mod100 >= 20)) return `${count} оценки`;
    return `${count} оценок`;
  }

  return (
    <div className="p-4 bg-gray-100 min-h-screen">
      {/* Кнопка "Добавить" */}
      <div className="flex justify-between items-center mb-4">
        <button 
          onClick={newProduct}
          className="bg-gray-300 px-4 py-2 rounded-md hover:bg-gray-400">
          Добавить
        </button>
        <input
          type="text"
          placeholder="Поиск"
          className="border border-gray-300 rounded-md px-4 py-2 w-1/3"
        />
      </div>

      {/* Таблица товаров */}
      <div className="border-t border-gray-300">
        <div className="grid grid-cols-9 items-center text-gray-600 text-sm font-semibold py-2 border-b">
          <div className="col-span-1"></div>
          <div className="col-span-1">Фото</div>
          <div className="col-span-2">Название</div>
          <div className="col-span-1">Отзывы</div>
          <div className="col-span-1">Остаток</div>
          <div className="col-span-1">Изменено</div>
          <div className="col-span-1">Статус</div>
          <div className="col-span-1 text-center">Действия</div>
        </div>

        {/* Пример строки товара */}
        {products.map((product, index) => (
          <div
            key={product.id || index}
            className="grid grid-cols-9 items-center text-sm py-3 border-b"
          >
            <input type="checkbox" className="col-span-1" />
            <div className="col-span-1 flex justify-start">
              {/* Вывод изображения продукта */}
              <div
                className="w-10 h-10 bg-gray-300 rounded-md"
                style={{
                  backgroundImage: `url(${product.imagePaths[0] || ""})`,
                  backgroundSize: "cover",
                  backgroundPosition: "center",
                }}
              ></div>
            </div>
            <div className="col-span-2">
              {/* Название и артикул товара */}
              <p className="font-semibold">{product.name}</p>
              <p className="text-gray-500">{product.sku || "Артикул не указан"}</p>
            </div>
            <div className="col-span-1">
              {/* Оценка товара */}
              <p className="text-gray-500">
                {product.rating || 0}, {pluralizeReview(product.ratingCount) || 0}
              </p>
            </div>
            <div className="col-span-1">
              {/* Остаток товара */}
              <p className="text-gray-500">{product.stock || 0} шт.</p>
            </div>
            <div className="col-span-1">
              {/* Дата изменения */}
              <p className="text-gray-500">{new Date(product.updated).toLocaleDateString()}</p>
            </div>
            <div className="col-span-1">
              {/* Статус товара */}
              <p className="text-gray-500">{product.status || "Неизвестный статус"}</p>
            </div>
            <div className="col-span-1 flex justify-center space-x-2">
              {/* Кнопки управления */}
              <button 
                className="text-blue-500 hover:underline"
                onClick={() => handleButtonEdit(product.id)}
              >Редактировать</button>

              <button 
                className="text-red-500 hover:underline"
                onClick={() => handleButtonDelete(product.id)}
              >Удалить</button>
            </div>
          </div>
        ))}
      </div>
    </div>
  )
}

export default Products