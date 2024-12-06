import logging

import requests

from dotenv import load_dotenv
import os

from io import text_encoding

from telegram import Update
from telegram.ext import filters, MessageHandler, ApplicationBuilder, ContextTypes, CommandHandler
from telegram import InlineQueryResultArticle, InputTextMessageContent
from telegram.ext import InlineQueryHandler

from uuid import uuid4

logging.basicConfig(
    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s',
    level=logging.INFO
)


async def start(update: Update, context: ContextTypes.DEFAULT_TYPE):
    await context.bot.send_message(chat_id=update.effective_chat.id, text="Привет! Я официальный бот маркетплейса ClickAndShop")

async def echo(update: Update, context: ContextTypes.DEFAULT_TYPE):
    await context.bot.send_message(chat_id=update.effective_chat.id, text=update.message.text)

async def caps(update: Update, context: ContextTypes.DEFAULT_TYPE):
    text_caps = ' '.join(context.args).upper()
    await context.bot.send_message(chat_id=update.effective_chat.id, text=text_caps)

async def inline_caps(update: Update, context: ContextTypes.DEFAULT_TYPE):
    query = update.inline_query.query
    if not query:
        return
    results = []
    results.append(
        InlineQueryResultArticle(
            id=str(uuid4()),
            title='Caps',
            input_message_content=InputTextMessageContent(query.upper())
        )
    )
    await context.bot.answer_inline_query(update.inline_query.id, results)

async def unknown(update: Update, context: ContextTypes.DEFAULT_TYPE):
    await context.bot.send_message(chat_id=update.effective_chat.id, text="Sorry, I didn't understand that command.")

async def link(update: Update, context:ContextTypes.DEFAULT_TYPE):
    url = "http://localhost:8080/api/createAuthCode"
    payload = {"username": update.effective_user.username, "role": "admin"}

    response = requests.get(url, payload, headers={"API_Key": "sicret_api_key"}, )
    response_json = response.json()
    print(response_json)

    await context.bot.send_message(chat_id=update.effective_chat.id, text="Код для авторизации:")
    await context.bot.send_message(chat_id=update.effective_chat.id, text=response_json["auth_code"])

async def create(update: Update, context: ContextTypes.DEFAULT_TYPE):
    await context.bot.send_message(chat_id=update.effective_chat.id, text="Вы успешно зарегистрировались")

async def profile(update: Update, context:ContextTypes.DEFAULT_TYPE):
    await context.bot.send_message(chat_id=update.effective_chat.id, text="Ваш профиль")

async def help(update: Update, context:ContextTypes.DEFAULT_TYPE):
    await context.bot.send_message(chat_id=update.effective_chat.id, text="Все доступные команды")


if __name__ == '__main__':
    application = ApplicationBuilder().token('7879270677:AAF_BdUvUylE9XXY8MpTOk8lpiWZL3cMy24').build()

    # Тестовые команды
    echo_handler = MessageHandler(filters.TEXT & (~filters.COMMAND), echo)
    caps_handler = CommandHandler('caps', caps)
    inline_caps_handler = InlineQueryHandler(inline_caps)

    application.add_handler(echo_handler)
    application.add_handler(caps_handler)
    application.add_handler(inline_caps_handler)


    # Релизные команды
    application.add_handler(CommandHandler('start', start))
    application.add_handler(CommandHandler('link', link))
    application.add_handler(CommandHandler('create', create))
    application.add_handler(CommandHandler('profile', profile))
    application.add_handler(CommandHandler('help', help))

    #Неизвестные команды
    application.add_handler(MessageHandler(filters.COMMAND, unknown))

    application.run_polling()