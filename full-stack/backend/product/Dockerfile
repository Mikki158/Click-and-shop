# Используем базовый образ с JDK
FROM eclipse-temurin:23-jdk-alpine

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем jar-файл приложения
COPY target/product-0.0.1-SNAPSHOT.jar app.jar

# Указываем порт, на котором работает приложение
EXPOSE 8081

# Указываем команду запуска приложения
ENTRYPOINT ["java", "-jar", "app.jar"]

