FROM eclipse-temurin:23-jdk-alpine

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем jar-файл приложения
COPY target/cart-0.0.1-SNAPSHOT.jar app.jar

# Указываем порт, на котором работает приложение
EXPOSE 8084

# Указываем команду запуска приложения
ENTRYPOINT ["java", "-jar", "app.jar"]