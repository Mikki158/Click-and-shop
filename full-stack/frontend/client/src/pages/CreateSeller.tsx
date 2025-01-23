import { useState } from "react";
import apiClient from "../apiClient";

const CreateSeller = () => {

    const [brandName, setBrandName] = useState<string>("");
    const [comment, setComment] = useState<string>("");

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
    
        // Здесь можно обработать данные, например, отправить их на сервер
        console.log("Brand Name:", brandName);
        console.log("Comment:", comment);

        apiClient
          .post("/api/seller/createRequestSeller", {
            "brand": brandName,
            "comment": comment}
          )
          .then((response) => {
            console.log(response.data)
          })
          .catch((error) => {
            console.error('Ошибка отправки заявки', error)
          })
    
        // Очистка полей формы после отправки
        setBrandName("");
        setComment("");
      };

    return (
    <div className="flex justify-center items-center min-h-screen bg-gray-100">
      <form
        onSubmit={handleSubmit}
        className="bg-white p-6 rounded-lg shadow-lg w-full max-w-md"
      >
        <h1 className="text-2xl font-semibold mb-4">Подать заявку</h1>

        {/* Поле для ввода названия бренда */}
        <div className="mb-4">
          <label
            htmlFor="brandName"
            className="block text-gray-700 font-medium mb-1"
          >
            Наименование бренда
          </label>
          <input
            type="text"
            id="brandName"
            value={brandName}
            onChange={(e) => setBrandName(e.target.value)}
            placeholder="например, LosTomatos"
            className="w-full p-2 border border-gray-300 rounded focus:outline-none focus:ring focus:ring-gray-200"
            required
          />
        </div>

        {/* Поле для комментария */}
        <div className="mb-4">
          <label
            htmlFor="comment"
            className="block text-gray-700 font-medium mb-1"
          >
            Комментарий
          </label>
          <textarea
            id="comment"
            value={comment}
            onChange={(e) => setComment(e.target.value)}
            placeholder="Введите ваш комментарий"
            className="w-full p-2 border border-gray-300 rounded h-32 focus:outline-none focus:ring focus:ring-gray-200"
            required
          />
        </div>

        {/* Кнопка отправки */}
        <button
          type="submit"
          className="w-full bg-gray-800 text-white py-2 px-4 rounded hover:bg-gray-900 transition"
        >
          Подать заявку
        </button>
      </form>
    </div>
  )
}

export default CreateSeller