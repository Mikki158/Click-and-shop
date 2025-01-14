import { useEffect, useState } from 'react';
import Container from '../ui/Container';
import axios from 'axios';

const Profile = () => {

  const [first_name, setFirstName] = useState('')
  const [username, setUsername] = useState('')
  const [photoUrl, setPhotoUrl] = useState('')
  const [role, setrole] = useState('')

  useEffect(() => {

    axios
      .get('https://click-and-shop.ru/api/auth/userInfo',
        {
          headers: {
            "Authorization": "Bearer " + sessionStorage.getItem('accessToken')
          }
        }
      )
      .then((response) => {
          setFirstName(response.data.firstName)
          setUsername(response.data.username)
          setPhotoUrl(response.data.photoUrl)
          setrole(response.data.role)
      })
  })

  return (
    <Container>
      <div className="flex flex-col md:flex-row p-6 min-h-screen">
        {/* Левая панель */}
        <div className="bg-gray-200 p-4 w-full md:w-1/4 flex flex-col items-center md:items-start">
          <div className="flex items-center mb-6">
            <img 
              src={photoUrl} 
              alt="User Avatar" 
              className="w-24 h-24 rounded-full border border-gray-300"
            />
            <div className="ml-4">
            <h2 className="text-lg font-semibold">first: {first_name}</h2>
          </div>
          <div className="flex justify-center">

            </div>
            
          </div>
            <div className="ml-4">
              <h2 className="text-lg font-semibold">Username: {username}</h2>
            </div>
            <div className="ml-4">
              <h2 className="text-lg font-semibold">Role: {role}</h2>
            </div>
          {/* Финансы */}
          <div className="mb-4 w-full">
            <h3 className="text-sm font-semibold text-gray-600 mb-2">Финансы</h3>
            <div className="bg-white p-2 rounded shadow-sm mb-2 cursor-pointer hover:bg-gray-100">
              Способы оплаты
            </div>
            <div className="bg-white p-2 rounded shadow-sm cursor-pointer hover:bg-gray-100">
              Баланс аккаунта
            </div>
          </div>

          {/* Управление */}
          <div className="w-full">
            <h3 className="text-sm font-semibold text-gray-600 mb-2">Управление</h3>
            <div className="bg-white p-2 rounded shadow-sm mb-2 cursor-pointer hover:bg-gray-100">
              Настройки
            </div>
            <div className="bg-white p-2 rounded shadow-sm cursor-pointer hover:bg-gray-100">
              Ваши устройства
            </div>
          </div>
        </div>

        {/* Правая панель */}
        {/* <div className="flex-1 p-4 grid grid-cols-1 md:grid-cols-3 gap-4">
              <div className="bg-gray-200 p-6 flex items-center justify-center rounded shadow-sm">
                Избранное
              </div>
              <div className="bg-gray-200 p-6 flex items-center justify-center rounded shadow-sm">
                Покупки
              </div>
              <div className="bg-gray-200 p-6 flex items-center justify-center rounded shadow-sm">
                Ждут оценки
              </div>
            </div> */}
      </div>
    </Container>
  );
}

export default Profile

