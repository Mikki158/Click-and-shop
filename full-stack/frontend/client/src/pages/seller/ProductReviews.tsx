import React, { useState } from "react";

type Review = {
  id: number;
  dateTime: string;
  productName: string;
  article: string;
  rating: number;
  buyerName: string;
  purchaseDate: string;
  comment: string;
};

const mockReviews: Review[] = [
  {
    id: 1,
    dateTime: "2025-06-01 12:00",
    productName: "Название товара",
    article: "123456",
    rating: 4,
    buyerName: "Иван Иванов",
    purchaseDate: "2025-05-20",
    comment: "Хороший товар, всё понравилось.",
  },
  {
    id: 2,
    dateTime: "2025-06-02 15:30",
    productName: "Название товара",
    article: "654321",
    rating: 5,
    buyerName: "Анна Петрова",
    purchaseDate: "2025-05-18",
    comment: "Отличное качество, быстрая доставка.",
  },
  // Добавь больше при необходимости
];

const ProductReviews = () => {
  const [search, setSearch] = useState("");
  const [perPage, setPerPage] = useState(5);
  const [page, setPage] = useState(1);

  const filtered = mockReviews.filter((r) =>
    r.article.includes(search)
  );

  const paginated = filtered.slice((page - 1) * perPage, page * perPage);

  const totalPages = Math.ceil(filtered.length / perPage);

  return (
    <div className="p-4 space-y-4 text-sm">
      <h2 className="font-medium">Отзывы</h2>

      {/* Фильтр */}
      <div className="flex items-center space-x-2">
        <input
          type="text"
          placeholder="Поиск по артикулу"
          value={search}
          onChange={(e) => setSearch(e.target.value)}
          className="border p-1"
        />
      </div>

      {/* Таблица */}
      <div className="overflow-x-auto">
        <table className="w-full text-left border-separate border-spacing-y-2">
          <thead>
            <tr>
              <th className="p-2">Дата и время</th>
              <th className="p-2">Товар</th>
              <th className="p-2">Кол-во звезд</th>
              <th className="p-2">Отзыв</th>
            </tr>
          </thead>
          <tbody>
            {paginated.map((r) => (
              <tr key={r.id} className="bg-gray-100">
                <td className="p-2 align-top">{r.dateTime}</td>
                <td className="p-2 align-top flex gap-2">
                  <div className="w-12 h-12 bg-gray-400 flex items-center justify-center text-white">
                    Фото
                  </div>
                  <div>
                    <div>{r.productName}</div>
                    <div className="text-xs text-gray-600">{r.article}</div>
                  </div>
                </td>
                <td className="p-2 align-top">
                  {Array.from({ length: 5 }, (_, i) => (
                    <span key={i}>
                      {i < r.rating ? "★" : "☆"}
                    </span>
                  ))}
                </td>
                <td className="p-2 align-top">
                  <div>{r.buyerName}</div>
                  <div className="text-xs text-gray-600">{r.purchaseDate}</div>
                  <div>{r.comment}</div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {/* Пагинация */}
      <div className="flex items-center justify-between">
        <div className="flex items-center space-x-2">
          <span>Показывать отзывов</span>
          <select
            value={perPage}
            onChange={(e) => {
              setPerPage(+e.target.value);
              setPage(1);
            }}
            className="border p-1"
          >
            {[5, 10, 15, 100].map((num) => (
              <option key={num} value={num}>
                {num}
              </option>
            ))}
          </select>
        </div>

        <div className="flex items-center gap-1">
          {Array.from({ length: totalPages }, (_, i) => (
            <button
              key={i}
              className={`px-2 py-1 border ${
                page === i + 1 ? "bg-gray-400" : "bg-white"
              }`}
              onClick={() => setPage(i + 1)}
            >
              {i + 1}
            </button>
          ))}
          {page < totalPages && (
            <button
              className="px-2 py-1 border bg-white"
              onClick={() => setPage(page + 1)}
            >
              &gt;
            </button>
          )}
        </div>
      </div>
    </div>
  );
};

export default ProductReviews;
