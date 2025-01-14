import axios from "axios";
import { PropsWithChildren, useEffect } from "react";


const TOKEN_REFRESH_INTERVAL = 2 * 60 * 1000;

const AuthProvider: React.FC<PropsWithChildren> = ({ children }) => {
    useEffect(() => {
        const refreshAccessToken = async () => {
            try {
                const refreshToken = document.cookie
                    .split('; ')
                    .find((row) => row.startsWith('refreshToken='))
                    ?.split('=')[1];

                if (!refreshToken) {
                    console.error('Refresh token отсутствует.');
                    return;
                }

                const response = await axios.post('https://click-and-shop.ru/api/auth/update_tokens', {refreshToken,});

                const { accessToken, newRefreshToken } = response.data

                sessionStorage.setItem('accessToken', accessToken);
                document.cookie = `refreshToken=${newRefreshToken}; HttpOnly; Secure`
                console.log('Access token успешно обновлён.', accessToken, refreshToken)
            } catch (error) {
                console.error('Ошибка обновления access token:', error);
            }
        };

        const intervalId = setInterval(refreshAccessToken, TOKEN_REFRESH_INTERVAL);

        return () => clearInterval(intervalId);
    }, []);

    return <>{children}</>
};

export default AuthProvider;
