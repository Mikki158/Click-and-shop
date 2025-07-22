import Container from '../ui/Container';
import { useEffect, useState } from 'react';
import apiClient from "../apiClient";
import { Link } from "react-router-dom";
import { store } from "../lib/store";

type OrderDto = {
  id: number;
  userId: number;
  status: string;
  totalPrice: number;
  pickupPointId: number;
  created: string; // ISO строка, например: "2023-03-21T10:00:00"
};

type OrderProductDto = {
  id: number;
  productId: number;
  quantity: number;
  orderId: number;
};

type ProductDto = {
  id: number;
  imagePaths: string[];
};

const formatDate = (iso: string) =>
  new Date(iso).toLocaleDateString("ru-RU", {
    day: "numeric",
    month: "long",
    year: "numeric",
  });

const Orders = () => {

  const [orders, setOrders] = useState<OrderDto[]>([]);
  const [productsMap, setProductsMap] = useState<Record<number, ProductDto[]>>({});

  const [completeOrders, setCompleteOrders] = useState<OrderDto[]>([]);
  const [completeProductsMap, setCompleteProductsMap] = useState<Record<number, ProductDto[]>>({});

  const [selectedTab, setSelectedTab] = useState<'active' | 'completed'>('active');

  //const { orders, fetchOrder } = store();

  // useEffect(() => {
  //   fetchOrder();
  // }, [fetchOrder]);

  useEffect(() => {
    const fetchOrders = async () => {
      try {
        const orderRes = await apiClient.get<OrderDto[]>('/api/order');
        console.log("Заказы: ", orderRes)
        
        const productMap: Record<number, ProductDto[]> = {};

        for (const order of orderRes.data) {
          console.log("Заказ ", order)
          const orderProductsRes = await apiClient.get<OrderProductDto[]>(`/api/order/${order.id}/products`);

          console.log("Товар", orderProductsRes)
          
          const productInfos: ProductDto[] = [];

          for (const op of orderProductsRes.data) {
            const productRes = await apiClient.get<ProductDto>(`/api/files/product/${op.id}`);
            console.log("Изображения", productRes)
            productInfos.push(productRes.data);
          }

          productMap[order.id] = productInfos;
        }
        setOrders(orderRes.data);
        setProductsMap(productMap);
      } catch (err) {
        console.error("Ошибка при загрузке заказов или товаров:", err);
      }
    };

    const fetchCompleteOrders = async () => {
      try {
        const orderRes = await apiClient.get<OrderDto[]>('/api/completeOrder');
        console.log("Завершенные заказы: ", orderRes)
        setCompleteOrders(orderRes.data);

        const productMap: Record<number, ProductDto[]> = {};

        for (const order of orderRes.data) {
          const orderProductsRes = await apiClient.get<OrderProductDto[]>(`/api/completeOrder/${order.id}/products`);
          
          const productInfos: ProductDto[] = [];

          for (const op of orderProductsRes.data) {
            const productRes = await apiClient.get<ProductDto>(`/api/files/product/${op.productId}`);
            console.log("Изображения", productRes)
            productInfos.push(productRes.data);
          }

          productMap[order.id] = productInfos;
        }

        setCompleteProductsMap(productMap);

      } catch (err) {
        console.error("Ошибка при загрузке заказов или товаров:", err);
      }
    };
    
    fetchCompleteOrders();
    fetchOrders();
  }, []);

  const filteredOrders = orders.filter(order =>
    selectedTab === 'active'
      ? order.status !== 'COMPLETED' && order.status !== 'CANCELLED'
      : order.status === 'COMPLETED'
  );

  return (
    <>
      <Container>
        {orders.length > 0 || completeOrders.length > 0 ? (
          <>
            <h1 className="text-2xl font-bold mb-4">Заказы</h1>
            <div className="flex gap-4 mb-6">
              <button 
                  onClick={() => setSelectedTab('active')}
                  className={`font-semibold text-lg border-b-2 ${
                      selectedTab === 'active' ? 'text-black border-black' : 'text-gray-400 border-transparent'
                  }`}>
                      Активные заказы
              </button>
              <button 
                  onClick={() => setSelectedTab('completed')}
                  className={`font-semibold text-lg border-b-2 ${
                      selectedTab === 'completed' ? 'text-black border-black' : 'text-gray-400 border-transparent'
                  }`}>
                  Завершенные заказы
              </button>
            </div>
            {
              selectedTab.includes("active") ? (
                <div>
                  {orders.map((order) => (
                    <Link to={`/order/${order.id}`} className="block hover:bg-gray-50 transition rounded-lg">
                      <div key={order.id} className="bg-white rounded-lg shadow p-4 mb-6">
                        <div className="flex justify-between items-center bg-gray-100 p-3 rounded">
                          <div>
                            <p className="text-lg font-semibold">Заказ от {formatDate(order.created)}</p>
                            <a className="text-blue-600 hover:underline text-sm">#{order.id.toString().padStart(12, '0')}</a>
                          </div>
                          <div className="text-right">
                            <p className="text-sm text-gray-500">оплачено</p>
                            <p className="text-xl font-bold">{order.totalPrice.toLocaleString('ru-RU')} ₽</p>
                          </div>
                        </div>

                        <div className="mt-4 flex items-center justify-between">
                          <div>
                            <p className="text-sm">Доставка в пункт выдачи</p>
                            <span className="inline-block bg-gray-300 text-gray-800 px-2 py-1 rounded text-xs font-medium mt-1">
                              {order.status}
                            </span>
                            <p className="text-sm mt-1">Дата доставки: 27 марта 2023</p>
                          </div>

                          <div className="flex gap-2">
                            {(productsMap[order.id] || []).map((product) => (
                              <img
                                key={product.id}
                                src={product?.[0] || '/fallback.jpg'}
                                alt="product"
                                className="w-16 h-16 object-cover rounded"
                              />
                            ))}
                          </div>
                        </div>
                      </div>
                    </Link>
                  ))}
                </div>
              ) : (
                <div>
                  {completeOrders.map((completeOrders) => (
                    <Link to={`/completeOrder/${completeOrders.id}`} className="block hover:bg-gray-50 transition rounded-lg">
                      <div key={completeOrders.id} className="bg-white rounded-lg shadow p-4 mb-6">
                        <div className="flex justify-between items-center bg-gray-100 p-3 rounded">
                          <div>
                            <p className="text-lg font-semibold">Заказ от {formatDate(completeOrders.created)}</p>
                            <a href="#" className="text-blue-600 hover:underline text-sm">#{completeOrders.id.toString().padStart(12, '0')}</a>
                          </div>
                          <div className="text-right">
                            <p className="text-sm text-gray-500">оплачено</p>
                            <p className="text-xl font-bold">{completeOrders.totalPrice.toLocaleString('ru-RU')} ₽</p>
                          </div>
                        </div>

                        <div className="mt-4 flex items-center justify-between">
                          <div>
                            <p className="text-sm">Доставка в пункт выдачи</p>
                            <span className="inline-block bg-gray-300 text-gray-800 px-2 py-1 rounded text-xs font-medium mt-1">
                              {completeOrders.status}
                            </span>
                            <p className="text-sm mt-1">Дата доставки: 27 марта 2023</p>
                          </div>

                          <div className="flex gap-2">
                            {(productsMap[completeOrders.id] || []).map((product) => (
                              <img
                                key={product.id}
                                src={product?.[0] || '/fallback.jpg'}
                                alt="product"
                                className="w-16 h-16 object-cover rounded"
                              />
                            ))}
                          </div>
                        </div>
                      </div>
                    </Link>
                  ))}
                </div>
              )
            }
          </>
        ) : (
          <div className="bg-white h-96 flex flex-col gap-2 items-center justify-center py-5 rounded-lg border border-gray-200 drop-shadow-2xl">
            <h1 className="text-3xl font-bold tracking-tight text-gray-900 sm:text-4xl">
              Заказы
            </h1>
            <p className="text-lg max-w-[600px] text-center text-gray-600 tracking-wide leading-6">
              У вас пока нет заказов, можете перейти на главную страницу, чтобы это исправить.
            </p>
            <Link
              to={"/product"}
              className="bg-gray-800 text-gray-200 px-8 py-4 rounded-md hover:bg-black hover:text-white duration-200 uppercase text-sm font-semibold tracking-wide"
            >
              За покупками
            </Link>
          </div>
        )}
        
      </Container>
    </>
  )
}

export default Orders
