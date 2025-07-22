import { loadStripe } from "@stripe/stripe-js";
import { ProductProps } from "../../type";
import { store } from "../lib/store";
import { config } from "../../config";
import { useEffect } from 'react';
import apiClient from "../apiClient";

const CheckoutBtn = ({ products }: { products: ProductProps[] }, {pickupPoint}: {pickupPoint: number}) => {
  const { currentUser, getUserInfo } = store();
  const publishableKey = "";
  const stripePromise = loadStripe(publishableKey);

  

  const handleCheckout = async () => {

    const newData = products.map(({name, regularPrice, description, categoryId, brandId, imagePaths, rating, ratingCount, ...rest}) => rest);

    const data = {
      pickupPointId: pickupPoint,
      products: newData
    }

    console.log("Заказ", data)

    // await apiClient.post('/api/order', data)
    // .then((response) => {
    //   console.log("Заказ был успешно оформлен", response)
    // })
    // .catch((error) => {
    //   console.error("Ошибка при создании заказа", error)
    // })
  };

  useEffect(() => {
    getUserInfo()
  }, [getUserInfo])

  return (
    <div className="mt-6">
      {currentUser ? (
        <button
          onClick={handleCheckout}
          type="submit"
          className="w-full rounded-md border border-transparent bg-gray-800 px-4 py-3 text-base font-medium text-white shadow-sm hover:bg-black focus:outline-none focus:ring-2 focus:ring-skyText focus:ring-offset-2 focus:ring-offset-gray-50 duration-200"
        >
          Заказать
        </button>
      ) : (
        <button className="w-full text-base text-white text-center rounded-md border border-transparent bg-gray-500 px-4 py-3 cursor-not-allowed">
          Проверка
        </button>
      )}
    </div>
  );
};

export default CheckoutBtn;