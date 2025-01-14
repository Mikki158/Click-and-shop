import React, { useEffect } from 'react';

interface TelegramLoginProps {
  botUsername: string; // Имя бота, как указано в BotFather
  authUrl: string;     // URL для обработки авторизации
  size?: 'small' | 'medium' | 'large'; // Размер кнопки (необязательный)
  requestAccess?: 'write' | 'read';    // Запрос на доступ (необязательный)
}

const TelegramLogin: React.FC<TelegramLoginProps> = ({
  botUsername,
  authUrl,
  size = 'large',
  requestAccess = 'write',
}) => {
  useEffect(() => {
    const script = document.createElement('script');
    script.src = 'https://telegram.org/js/telegram-widget.js?19';
    script.async = true;
    script.setAttribute('data-telegram-login', botUsername);
    script.setAttribute('data-size', size);
    script.setAttribute('data-auth-url', authUrl);
    if (requestAccess) {
      script.setAttribute('data-request-access', requestAccess);
    }
    document.getElementById('telegram-login-container')?.appendChild(script);

    return () => {
      // Удаляем скрипт при размонтировании компонента
      const container = document.getElementById('telegram-login-container');
      if (container) {
        container.innerHTML = '';
      }
    };
  }, [botUsername, authUrl, size, requestAccess]);

  return <div id="telegram-login-container"></div>;
};

export default TelegramLogin;

