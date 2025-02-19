import axios from "axios";
import dayjs from "dayjs";

const onGetForceToken = async () => {
    try {
        let refreshToken = document.cookie
            .split('; ')
            .find((row) => row.startsWith('refreshToken='))
            ?.split('=')[1];

<<<<<<< Updated upstream
=======
        console.log("REFRESH " + refreshToken)

>>>>>>> Stashed changes
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
    }
}

const apiClient = axios.create({
    baseURL: 'https://click-and-shop.ru',
    headers: {
        'Content-Type': 'application/json',
    },
});

apiClient.interceptors.request.use(async (config) => {

    console.log("Проверка токена");

    const expireAt = sessionStorage.getItem('nbf');

    if (dayjs(expireAt).diff(dayjs()) < 1) {
        console.log("Проверка не пройдена")
        await onGetForceToken();
        console.log("Токен обновился");
    }
   
    const accessToken = sessionStorage.getItem('accessToken');
    console.log("Добавление заголовка", accessToken)
    config.headers.Authorization = `Bearer ${accessToken}`;

    return config;
},
(error) => {
    return Promise.reject(error);
}
);

export default apiClient;

