import telebot
import requests

from dotenv import load_dotenv
import os

load_dotenv()

bot = telebot.TeleBot(os.getenv("BOT_TOKEN"));

@bot.message_handler(content_types=['text'])
def get_text_messages(message):
    if message.text == "Привет":
        bot.send_message(message.from_user.id, "Привет, чем я могу тебе помочь?")
    elif message.text == "/help":
        bot.send_message(message.from_user.id, "Напиши привет")
    elif message.text == "/link":
        url = "http://localhost:8080/api/createAuthCode"
        payload = {"username":message.from_user.username, "role":"admin"}

        response = requests.get(url, payload, headers={"API_Key": os.getenv("API_KEY")},)
        response_json = response.json()
        print(response_json)
        bot.send_message(message.from_user.id, "```Код для авторизации:```", parse_mode="MarkdownV2")
        bot.send_message(message.from_user.id, response_json["auth_code"])
    else:
        bot.send_message(message.from_user.id, "Я тебя не понимаю. Напиши /help.")

bot.polling(none_stop=True, interval=0)