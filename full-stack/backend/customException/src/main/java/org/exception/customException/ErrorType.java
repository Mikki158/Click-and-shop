package org.exception.customException;

public enum ErrorType {
    DEFAULT,                        // Дефолтная
    SYSTEM,                         // Системная ошибка
    SERVICE,                        // Бизнес-логика
    REPOSITORY,                     // Ошибка при взаимодействии с БД
    COMPONENT,                      // Ошибка компонента
    CONTROLLER,                     // Ошибка в контроллере
    VIEW,                           // Ошибка представления
    AUTHORIZATION,                  // Ошибка авторизации
    INPUT_REQUEST,                  // Ошибка в данных(неполный запрос)
    INPUT_REQUEST_VALIDATION,       // Ошибка валидации
    OUTPUT_RESPONSE,                // Ошибка в формировании ответа
    OUTPUT_RESPONSE_VALIDATION,     // Ошибка валидации ответа
    MAPPING,                        // Ошибка преобразования данных
    INTEGRATION_SYNC,               // Ошибка синхронной интеграции с внешними сервисами
    INTEGRATION_ASYNC,              // Ошибка асинхронной интеграции
    VALIDATION,                     // Общая ошибка валидации
    EXTERNAL_SYSTEM;                // Ошибка связанная с внешними сервисами
}