import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import apiClient from "../apiClient";
import Container from "../ui/Container";

export default function OrderDetails() {
  const { id } = useParams();
  const [order, setOrder] = useState(null);
  const [count, setCount] = useState(0)
  const [products, setProducts] = useState<any[]>([]);
  const [pickupPoint, setPickupPoint] = useState("")

  useEffect(() => {
    const fetchOrder = async () => {
      const { data } = await apiClient.get(`/api/order/${id}`);
      setOrder(data);

      const productsResp = await apiClient.get(`/api/order/${id}/products`);
      //setProducts(productsResp.data);
      console.log("ProductResp ", productsResp)

      const productInfos = [];
      const productMap: Record<number, []> = {};

      for (const product of productsResp.data ) {
        const productinfo = await apiClient.get(`/api/product/${product.id}`);
        productinfo.data.quantity = product.quantity
        productInfos.push(productinfo.data);
      }

      

      setProducts(productInfos);
      console.log("productInfos ", productInfos)

      const pickupResp = await apiClient.get(`/api/pickup_point/${data.pickupPointId}`);
      setPickupPoint(pickupResp.data.address)
    };
    fetchOrder();
  }, [id]);

  if (!order) return <p>Загрузка...</p>;

  const formatPrice = (price: number) =>
    price.toLocaleString("ru-RU") + " ₽";

  return (
    <Container>
      <h2 className="text-2xl font-bold mb-2">
        Заказ от {new Date(order.created).toLocaleDateString("ru-RU")}
      </h2>

      <div className="text-gray-600 mb-6">
        Доставка в пункт выдачи: {pickupPoint}
      </div>

      <div className="bg-white p-4 rounded-lg shadow-md mb-6">
        <p className="text-sm font-medium text-gray-600 mb-2">Статус заказа</p>
        <div className="inline-block px-3 py-1 bg-gray-300 rounded-full text-sm font-semibold">
          {order.status}
        </div>
      </div>

      {/* Две колонки: товары слева, стоимость справа */}
      <div className="flex flex-col lg:flex-row gap-8 items-start">
        {/* Левая колонка - товары */}
        <div className="flex-1 w-full bg-white p-4 rounded-lg shadow-md " >
          <h3 className="text-lg font-semibold mb-2">Товары</h3>
          <div className="space-y-4 ">
            {products.map((prod) => (
              <Link to={`/product/${prod.id}`} key={prod.id}>
                <div className="flex gap-4 border-b pb-4">
                  <img src={prod.imagePaths[0]} alt="" className="w-20 h-20 object-cover" />
                  <div className="flex-1">
                    <p className="font-semibold">{prod.name}</p>
                    <p className="text-sm text-gray-500">Количество: {prod.quantity}</p>
                  </div>
                  <div className="text-right">
                    <p className="font-semibold">{formatPrice(prod.discountedPrice)}</p>
                  </div>
                </div>
              </Link>
            ))}
          </div>
        </div>

        {/* Правая колонка - стоимость */}
        <div className="bg-white p-4 rounded-lg shadow-md w-full lg:w-1/3 min-w-[500px]">
          <h3 className="text-lg font-semibold mb-2">Ваш заказ</h3>
          <div className="flex justify-between border-b py-2">
            <span>Товары</span>
            <span>{formatPrice(order.totalPrice)}</span>
          </div>
          <div className="flex justify-between border-b py-2">
            <span>Доставка</span>
            <span>Без доплат</span>
          </div>
          <div className="flex justify-between font-bold py-2">
            <span>Оплачено</span>
            <span>{formatPrice(order.totalPrice)}</span>
          </div>
        </div>
      </div>
    </Container>

  );
}
