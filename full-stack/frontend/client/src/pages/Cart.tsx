import { useEffect, useState } from "react";
import Container from "../ui/Container"
import { store } from "../lib/store";
import { useNavigate, Link } from "react-router-dom";
import CartProduct from "../ui/CartProduct";
import CheckoutBtn from "../ui/CheckoutBtn";
import FormattedPrice from "../ui/FormattedPrice";
import apiClient from "../apiClient";
import { PickupPoint } from '../../type'

const Cart = () => {
  const [totalAmt, setTotalAmt] = useState({ regular: 0, discounted: 0 });
  const { cartProduct, fetchCart } = store();
  const [options, setOptions] = useState<PickupPoint[]>([]);

  const [selected, setSelected] = useState<string>("");
  const [selectedId, setSelectedId] = useState<number | "">("");

  const navigate = useNavigate();

  useEffect(() => {
    fetchCart(); // Загружаем данные корзины из backend при загрузке страницы
  }, [fetchCart]);

  useEffect(() => {
    const totals = cartProduct.reduce(
      (sum, product) => {
        sum.regular += product?.regularPrice * product?.quantity;
        sum.discounted += product?.discountedPrice * product?.quantity;
        return sum;
      },
      { regular: 0, discounted: 0 }
    );
    setTotalAmt(totals);
  }, [cartProduct]);

  useEffect(() => {
    const fetchOptions = async () => {
      try {
        const response = await apiClient.get("/api/pickup_point"); // URL замени на нужный
        console.log("RESPONSE")
        console.log(response.data)
        setOptions(response.data); // предполагается, что сервер возвращает массив строк
      } catch (error) {
        console.error("Ошибка при загрузке опций:", error);
      }
    };

    fetchOptions();
  }, []);

  const mackingOrder = async () => {

    const newData = cartProduct.map(({name, regularPrice, description, categoryId, brandId, imagePaths, rating, ratingCount, ...rest}) => rest);

    const data = {
      pickupPointId: selectedId,
      products: newData
    }

    console.log("Заказ", data)

    await apiClient.post('/api/order', data)
    .then((response) => {
      console.log("Заказ был успешно оформлен", response)
      apiClient.delete('/api/cart')
      navigate(`/order/${response.data.id}`)
    })
    .catch((error) => {
      console.error("Ошибка при создании заказа", error)
    })
  };

  return (
    <Container>
      {cartProduct.length > 0 ? (
        <>
        <h1 className="text-3xl font-bold tracking-tight text-gray-900 sm:text-4xl">
          Корзина
        </h1>
        <div className="mt-10 lg:grid lg:grid-cols-12 lg:items-start lg:gap-x-12 xl:gap-x-16">
          <section className="lg:col-span-7">
            <div className=" divide-y divide-gray-200 border-b border-t border-gray-200">
              {cartProduct.map((product) => (
                <CartProduct product={product} key={product?.id} />
              ))}
            </div>
          </section>
          <section className="mt-16 rounded-lg bg-gray-50 px-4 py-6 sm:p-6 lg:col-span-5 lg:mt-0 lg:p-8">
            <div className="w-full max-w-sm">
              <label  htmlFor="dropdown" className="text-lg font-medium text-gray-900">
                Выберите пункт выдачи:
              </label>
              <select
                className="border p-2 rounded"
                onChange={(e) => setSelectedId(Number(e.target.value))}
                value={selectedId ?? ''}
              >
                <option value="" disabled>Выберите вариант</option>
                {options.map((opt) => (
                  <option key={opt.id} value={opt.id}>
                    {opt.address}
                  </option>
                ))}
              </select>
            </div>
            <h2 className="text-lg font-medium text-gray-900">Краткое описание заказа</h2>
            <dl className="mt-6 space-y-4">
              <div className="flex items-center justify-between">
                <dt className="text-sm text-gray-600">Промежуточный итог</dt>
                <dd className="text-sm font-medium text-gray-900">
                  <FormattedPrice amount={totalAmt?.regular} />
                </dd>
              </div>
              <div className="flex items-center justify-between border-t border-gray-200 pt-4">
                <dt className="flex items-center text-sm text-gray-600">
                  <span>Доставка</span>
                </dt>
                <dd className="text-sm font-medium text-gray-900">
                  <FormattedPrice amount={25} />
                </dd>
              </div>
              <div className="flex items-center justify-between border-t border-gray-200 pt-4">
                <dt className="flex text-sm text-gray-600">
                  <span>Налог</span>
                </dt>
                <dd className="text-sm font-medium text-gray-900">
                  <FormattedPrice amount={15} />
                </dd>
              </div>
              <div className="flex items-center justify-between border-t border-gray-200 pt-4">
                <dt className="text-base font-medium text-gray-900">Общая скидка</dt>
                <dd className="text-base font-medium text-gray-500">
                  <FormattedPrice
                    amount={totalAmt?.regular - totalAmt?.discounted}
                  />
                </dd>
              </div>
              <div className="flex items-center justify-between border-t border-gray-200 pt-4">
                <dt className="text-base font-medium text-gray-900">Сумма заказа</dt>
                <dd className="text-lg font-bold text-gray-900">
                  <FormattedPrice
                    amount={totalAmt?.discounted + 25 + 15}
                  />
                </dd>
              </div>
            </dl>
            {
              selectedId ? (
                <button
                  onClick={mackingOrder}
                  type="submit"
                  className="w-full rounded-md border border-transparent bg-gray-800 px-4 py-3 text-base font-medium text-white shadow-sm hover:bg-black focus:outline-none focus:ring-2 focus:ring-skyText focus:ring-offset-2 focus:ring-offset-gray-50 duration-200">
                  Заказать
                </button>
              ) : (
                <button
                  className="w-full text-base text-white text-center rounded-md border border-transparent bg-gray-500 px-4 py-3 cursor-not-allowed">
                  Выберите пункт выдачи
                </button>
              )
            }
        
          </section>
        </div>
      </>
      ) : (
        <div className="bg-white h-96 flex flex-col gap-2 items-center justify-center py-5 rounded-lg border border-gray-200 drop-shadow-2xl">
          <h1 className="text-3xl font-bold tracking-tight text-gray-900 sm:text-4xl">
            Корзина
          </h1>
          <p className="text-lg max-w-[600px] text-center text-gray-600 tracking-wide leading-6">
            Ваша корзина пока пустая, можете перейти на главную страницу, чтобы это исправить.
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
  )
}

export default Cart
