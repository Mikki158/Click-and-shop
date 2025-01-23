from aiogram import Bot, Dispatcher, types
from aiogram.types import InlineKeyboardButton, InlineKeyboardMarkup
from aiogram.utils import executor
from aiohttp import web
import asyncio

# Telegram bot token
BOT_TOKEN = "7879270677:AAF_BdUvUylE9XXY8MpTOk8lpiWZL3cMy24"

# Initialize bot and dispatcher
bot = Bot(token=BOT_TOKEN)
dp = Dispatcher(bot)

# Inline button for /start command
start_keyboard = InlineKeyboardMarkup().add(
    InlineKeyboardButton("Welcome", callback_data="welcome")
)

# Handle /start command
@dp.message_handler(commands=["start"])
async def handle_start(message: types.Message):
    await message.reply("Welcome! Use the button below:", reply_markup=start_keyboard)

# Handle button click
@dp.callback_query_handler(lambda c: c.data == "welcome")
async def handle_welcome_callback(callback_query: types.CallbackQuery):
    await bot.answer_callback_query(callback_query.id)
    await bot.send_message(callback_query.from_user.id, "Thank you for clicking the button!")

# Webhook handler for POST requests
async def handle_post(request):
    try:
        data = await request.json()
        chat_id = data.get("id")
        message = data.get("message")

        if not chat_id or not message:
            return web.json_response({"error": "Missing 'id' or 'message'"}, status=400)

        await bot.send_message(chat_id, message)
        return web.json_response({"status": "success"})

    except Exception as e:
        return web.json_response({"error": str(e)}, status=500)

# Initialize web application
app = web.Application()
app.router.add_post("/bot/send_welcome", handle_post)

if __name__ == "__main__":
    # Run the webhook server and the bot
    loop = asyncio.get_event_loop()

    # Start aiohttp server for handling POST requests
    web_task = loop.create_task(web._run_app(app, host="0.0.0.0", port=5000))

    # Start polling for Telegram updates
    executor_task = loop.create_task(executor.start_polling(dp, skip_updates=True))

    # Run both tasks concurrently
    loop.run_until_complete(asyncio.gather(web_task, executor_task))

