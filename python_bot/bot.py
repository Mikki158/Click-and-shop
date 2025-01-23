from flask import Flask, request
from telegram import Update, Bot
from telegram.ext import Updater, CommandHandler, CallbackContext
import os

app = Flask(__name__)

# Укажите ваш токен бота
BOT_TOKEN = "7879270677:AAF_BdUvUylE9XXY8MpTOk8lpiWZL3cMy24"
bot = Bot(token=BOT_TOKEN)

@app.route('/bot/send_welcome', methods=['POST'])
def send_welcome():
    data = request.json
    chat_id = data.get('chat_id')
    message = data.get('message', 'Добро пожаловать!')

    print(f"Chat ID: {chat_id}, Message: {message}")

    if not chat_id:
        return {"error": "chat_id is required"}, 400

    try:
        bot.send_message(chat_id=chat_id, text=message)
        return {"status": "success"}, 200
    except Exception as e:
        return {"error": str(e)}, 500

def start(update: Update, context: CallbackContext):
    chat_id = update.effective_chat.id  # Получение chat_id
    user = update.effective_user       # Получение информации о пользователе

    # Сохранение chat_id на сервер (можно сделать HTTP-запрос к вашему бэкенду)
    print(f"Chat ID: {chat_id}, User: {user.first_name}")

    update.message.reply_text("chat_id sohranen")

def main():
    # Инициализируем Updater
    updater = Updater(BOT_TOKEN, use_context=True)

    # Получаем диспетчер для обработки команд
    dispatcher = updater.dispatcher

    # Добавляем обработчик команды /start
    dispatcher.add_handler(CommandHandler("start", start))

    # Запускаем polling
    updater.start_polling()

    # Ожидаем завершения работы
    updater.idle()

if __name__ == '__main__':
    app.run(host="0.0.0.0", port=5000)
    main()

