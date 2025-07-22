import { useEffect } from "react";
import { useSearchParams } from "react-router-dom"
import axios from 'axios'
import { authStore } from "../lib/auth";
import apiClient from '../apiClient';

const Login = () => {

  const [searchParams] = useSearchParams();  

  useEffect(() => {

    let last_name = searchParams.get('last_name')
    let photo_url = searchParams.get('photo_url')

    if (last_name == null)
      last_name = "null";

    if (photo_url == null)
      photo_url = "null"

    const params = {
      id: searchParams.get('id'),
      first_name: searchParams.get('first_name'),
      last_name: decodeURIComponent(last_name),
      username: searchParams.get('username'),
      photo_url: decodeURIComponent(photo_url),
      auth_date: searchParams.get('auth_date'),
      hash: searchParams.get('hash'),
    };

    console.log(params);

    const fetchData = async() => {
      try {
        const response = await apiClient.get('https://click-and-shop.ru/api/auth/TG_auth', {params})

        const { accessToken, refreshToken, nbf } = response.data;

        sessionStorage.setItem('nbf', nbf);

        sessionStorage.setItem('accessToken', accessToken);
        document.cookie = `refreshToken=${refreshToken}; Secure`

        console.log('Авторизация прошла успешно, токены сохранены', accessToken, refreshToken)

        const userInfo = await apiClient.get('/api/auth/userInfo')
        console.log("Роли", userInfo.data.role)

        localStorage.setItem("roles", userInfo.data.role);

        const timer = setTimeout(() => {
          window.location.href = "/";
        }, 2000);

        return () => clearTimeout(timer);
      } catch(error) {
        console.error('Ошибка авторизации', error)
      }
    }

    fetchData()

  }, [searchParams])

  return (
    <div className="flex flex-col items-center justify-center h-screen bg-gray-100">
      <div className="bg-white shadow-md rounded-lg p-6 max-w-md w-full">
        <h1 className="text-2xl font-bold text-center mb-4">Добро пожаловать!</h1>
        <p className="text-center text-gray-600">Авторизация прошла успешно</p>
        <div className="mt-6 space-y-4">
          <p>
            <span className="font-semibold">Имя:</span> {searchParams.get('first_name')}
          </p>
          <p>
            <span className="font-semibold">Пользователь:</span> @{searchParams.get('username')}
          </p>
          <p>
            <span className="font-semibold">ID:</span> {searchParams.get('id')}
          </p>
          {searchParams.get('photo_url') && (
            <div className="flex justify-center">
              <img 
                src={searchParams.get('photo_url')!} 
                alt="User Avatar" 
                className="w-24 h-24 rounded-full border border-gray-300"
              />
            </div>
          )}
        </div>
      </div>
    </div>
  );
}

export default Login;
