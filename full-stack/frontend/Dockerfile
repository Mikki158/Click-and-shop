# Используем официальный Node.js образ
FROM node:18-alpine

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем package.json и pnpm-lock.yaml
COPY package.json pnpm-lock.yaml ./

# Устанавливаем pnpm
RUN npm install -g pnpm

# Устанавливаем зависимости
RUN pnpm install

# Копируем весь проект в контейнер
COPY . .

# Собираем приложение
RUN pnpm build

# Stage 2: Отдаем приложение через Nginx
FROM nginx:stable

# Копируем собранные файлы в контейнер Nginx
COPY --from=build /app/build /usr/share/nginx/html

# Открываем порты
EXPOSE 80
EXPOSE 443

# Конфиг Nginx будет в отдельном контейнере
CMD ["nginx", "-g", "daemon off;"]
