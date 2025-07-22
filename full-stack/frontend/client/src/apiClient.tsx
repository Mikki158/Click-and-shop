import axios from "axios";
import dayjs from "dayjs";
import { authStore } from "./lib/auth";

const onGetForceToken = async () => {
    try {
        let refreshToken = document.cookie
            .split('; ')
            .find((row) => row.startsWith('refreshToken='))
            ?.split('=')[1];

        console.log("REFRESH " + refreshToken)

        if (!refreshToken) {
            console.error('Refresh token отсутствует.');
            return;
        }

        const response = await axios.post('https://click-and-shop.ru/api/auth/update_tokens', {refreshToken,});

        const { accessToken, nbf } = response.data

        const newRefreshToken = response.data.refreshToken;

        console.log(newRefreshToken);

        sessionStorage.setItem('nbf', nbf);

        sessionStorage.setItem('accessToken', accessToken);
        document.cookie = `refreshToken=${newRefreshToken}; Secure`
        console.log('Access token успешно обновлён.', accessToken, newRefreshToken)
    } catch (error) {
        console.error('Ошибка обновления access token:', error);
        authStore.getState().setIsAuthenticated(false);
    }
}

const apiClient = axios.create({
    baseURL: 'https://click-and-shop.ru',
    headers: {
        'Content-Type': 'application/json',
        'X-Client-Type': 'frontend',
        
    },
});

apiClient.interceptors.request.use(async (config) => {

    //const {setIsAuthenticated} = useAuthStore();

    console.log("Проверка токена");

    const expireAt = sessionStorage.getItem('nbf');

    console.log("Время", expireAt);

    if (dayjs(expireAt).diff(dayjs()) < 1 || expireAt == null) {
        console.log("Проверка не пройдена")
        await onGetForceToken();
        console.log("Токен обновился");
        
    }
   
    const accessToken = sessionStorage.getItem('accessToken');
    console.log("Добавление заголовка", accessToken)
    if (accessToken != null) {
        config.headers.Authorization = `Bearer ${accessToken}`;
    }

    //setIsAuthenticated(true);

    return config;
},
(error) => {
    return Promise.reject(error);
}
);

export default apiClient;

