const HeaderAdmin = () => {
  return (
    <div className="bg-gray-100">
      <nav className="bg-gray-300 p-4 flex justify-between items-center">
        <div className="flex gap-8">
          <a href='/admin/warehouse' className="text-gray-800 hover:text-gray-600">
            Склады
          </a>
          <a href='/admin/pickupPoints' className="text-gray-800 hover:text-gray-600">
            Пункты выдачи
          </a>
          <a href='/admin/users' className="text-gray-800 hover:text-gray-600">
            Пользователи
          </a>
          {/* <a href='/admin/products' className="text-gray-800 hover:text-gray-600">
            Товары
          </a> */}
          <a href='/admin/reviews' className="text-gray-800 hover:text-gray-600">
            Отзывы
          </a>
        </div>
        <div className="text-gray-800 font-semibold">
          <a href="/" className="text-gray-800 hover:text-gray-600">
            На страницу магазина
          </a>
        </div>
      </nav>
    </div>
  )
}

export default HeaderAdmin