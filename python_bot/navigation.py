import telebot
from pyexpat.errors import messages
from telebot import types
from telebot.types import InlineKeyboardMarkup, InlineKeyboardButton
from flask import Flask, request, jsonify
import requests
import time
import threading

BOT_TOKEN = '7879270677:AAF_BdUvUylE9XXY8MpTOk8lpiWZL3cMy24'
BASE_URL = 'https://click-and-shop.ru/api'

bot = telebot.TeleBot(BOT_TOKEN, parse_mode='HTML')
app = Flask(__name__)

secretKey = "12345qwerty"

headers = {
    "Content-Type": "application/json",
    "X-Client-Type": "bot",
    "Authorization": f"BotApiKey {secretKey}"
}

user_states = {}  # user_id -> "ожидаем_фото"
star_states = {}
product_states = {}

# Главный хендлер
@bot.message_handler(commands=['start'])
def send_welcome(message):
    bot.send_message(message.chat.id, "👋 Привет! Это официальный телегам бот маркетплейса Click-and-shop!", reply_markup=main_menu())

# Основное меню
def main_menu():
    markup = types.InlineKeyboardMarkup()

    # Кнопки
    btn_catalog = types.InlineKeyboardButton("📦 Заказы", callback_data='menu_orders')
    btn_profile = types.InlineKeyboardButton("👤 Мой профиль", callback_data='menu_profile')
    btn_support = types.InlineKeyboardButton("⚙ Настройки", callback_data='menu_settings')
    btn_reviews = types.InlineKeyboardButton("✍️ Отзывы", callback_data='menu_expection_reviews')
    btn_channel = types.InlineKeyboardButton("🌐 Сайт", url='https://click-and-shop.ru/')
    btn_faq = types.InlineKeyboardButton("❓ Частые вопросы", callback_data='menu_faq')

    # Расположение по рядам

    markup.add(btn_catalog)
    markup.row(btn_profile, btn_support)
    markup.row(btn_reviews, btn_faq)
    markup.add(btn_channel)
    return markup

# Подменю "Заказы"
def order_menu(orders):
    markup = types.InlineKeyboardMarkup()

    for order in orders:
        markup.add(types.InlineKeyboardButton(f"📦 Заказ №{order.get("id")}" , callback_data=f'order_item_{order.get("id")}'))

    markup.add(types.InlineKeyboardButton("✅ Завершенные заказы", callback_data='menu_complete_orders'))
    markup.add(types.InlineKeyboardButton("🔙 Назад", callback_data='back_to_main'))
    return markup

def complete_order_menu(completeOrders):
    markup = types.InlineKeyboardMarkup()

    for completeOrder in completeOrders:
        markup.add(types.InlineKeyboardButton(f"📦 Заказ №{completeOrder.get("id")}" , callback_data=f'complete_order_item_{completeOrder.get("id")}'))

    markup.add(types.InlineKeyboardButton("🔙 Назад", callback_data='back_to_main'))
    return markup

# Подменю "Профиль"
def profile_menu():
    markup = types.InlineKeyboardMarkup()
    markup.add(types.InlineKeyboardButton("🔙 Назад", callback_data='back_to_main'))
    return markup

def expection_review_menu(productsInfo):
    markup = types.InlineKeyboardMarkup()

    for product in productsInfo:
        markup.add(types.InlineKeyboardButton(f"{product.get("name")}",
                                              callback_data=f'new_review_item_{product.get("id")}'))

    markup.add(types.InlineKeyboardButton("📃 Опубликованные отзывы", callback_data='menu_review'))
    markup.add(types.InlineKeyboardButton("🔙 Назад", callback_data='back_to_main'))
    return markup

def review_menu(reviews):
    markup = types.InlineKeyboardMarkup()

    for review in reviews:
        response = requests.get(f"{BASE_URL}/product/{review.get("productId")}", headers=headers)

        markup.add(types.InlineKeyboardButton(f"{response.json().get("name")}",
                                              callback_data=f'review_item_{review.get("id")}'))

    markup.add(types.InlineKeyboardButton("🔙 Назад", callback_data='back_to_menu_review'))
    return markup



@bot.callback_query_handler(func=lambda call: call.data.startswith('order_item_'))
def handle_order_item(call):
    # Получаем order_id из callback_data
    order_id = call.data.split('_')[-1]


    # Здесь можно получить сам заказ из БД, например:
    response = requests.get(f"{BASE_URL}/order/{order_id}", headers=headers)
    order = response.json()
    response = requests.get(f"{BASE_URL}/order/{order_id}/products", headers=headers)
    products = response.json()
    response = requests.get(f"{BASE_URL}/pickup_point/{order.get("pickupPointId")}", headers=headers)
    pickupPoint = response.json()

    # Ответ
    text = (f"📦 Заказ №{order.get("id")}\n"
            f"📆 От {order.get("created")}\n"
            f"🚚 Доставка в пункт выдачи: {pickupPoint.get("address")}\n"
            f"ℹ Статус: {order.get("status")}\n"
            "Товары:\n"
            "----------------------\n")

    for product in products:
        response = requests.get(f"{BASE_URL}/product/{product.get("id")}", headers=headers)
        productInfo = response.json()
        text += (f"{productInfo.get("name")} \n"
                 f"Кол-во: {product.get("quantity")} \n"
                 f"Цена: {product.get("discountedPrice")} ₽/шт\n"
                 f"----------------------\n")

    text += f"Сумма заказа: {order.get("totalPrice")} руб."

    markup = types.InlineKeyboardMarkup()
    markup.add(types.InlineKeyboardButton("🔙 Назад", callback_data='back_to_orders'))

    bot.edit_message_text(
        chat_id=call.message.chat.id,
        message_id=call.message.message_id,
        text=text,
        reply_markup=markup
    )

    bot.answer_callback_query(call.id)


@bot.callback_query_handler(func=lambda call: call.data.startswith('complete_order_item_'))
def handle_complete_order_item(call):
    # Получаем order_id из callback_data
    order_id = call.data.split('_')[-1]


    # Здесь можно получить сам заказ из БД, например:
    response = requests.get(f"{BASE_URL}/completeOrder/{order_id}", headers=headers)
    order = response.json()
    response = requests.get(f"{BASE_URL}/completeOrder/{order_id}/products", headers=headers)
    products = response.json()
    response = requests.get(f"{BASE_URL}/pickup_point/{order.get("pickupPointId")}", headers=headers)
    pickupPoint = response.json()

    # Ответ
    text = (f"📦 Заказ №{order.get("id")}\n"
            f"📆 От {order.get("created")}\n"
            f"🚚 Доставка в пункт выдачи: {pickupPoint.get("address")}\n"
            f"ℹ Статус: {order.get("status")}\n"
            "Товары:\n"
            "----------------------\n")

    for product in products:
        response = requests.get(f"{BASE_URL}/product/{product.get("id")}", headers=headers)
        productInfo = response.json()
        text += (f"{productInfo.get("name")} \n"
                 f"Кол-во: {product.get("quantity")} \n"
                 f"Цена: {productInfo.get("discountedPrice")} ₽/шт\n"
                 f"----------------------\n")

    text += f"Сумма заказа: {order.get("totalPrice")} руб."

    markup = types.InlineKeyboardMarkup()
    markup.add(types.InlineKeyboardButton("🔙 Назад", callback_data='back_to_complete_orders'))

    bot.edit_message_text(
        chat_id=call.message.chat.id,
        message_id=call.message.message_id,
        text=text,
        reply_markup=markup
    )

    bot.answer_callback_query(call.id)

@bot.callback_query_handler(func=lambda call: call.data.startswith('new_review_item_'))
def handle_new_review_item(call):
    # Получаем order_id из callback_data
    product_id = call.data.split('_')[-1]
    response = requests.get(f"{BASE_URL}/product/{product_id}", verify=False, headers=headers)
    product = response.json()
    markup = types.InlineKeyboardMarkup()

    media = product.get("imagePaths")[0]
    print(media)

    text = (f"{product.get("name")}\n"
            f"{product.get("description")}\n"
            f"{product.get("brandId")}\n\n"
            f"---------------------------\n"
            f"Выберите количество звезд, которые хотите поставить этому товару")

    btn_link = types.InlineKeyboardButton("🔗 Ссылка на товар", url=f'https://click-and-shop.ru/product/{product.get("id")}')
    btn_star_1 = types.InlineKeyboardButton("1 ⭐", callback_data='new_review_1')
    btn_star_2 = types.InlineKeyboardButton("2 ⭐", callback_data='new_review_2')
    btn_star_3 = types.InlineKeyboardButton("3 ⭐", callback_data='new_review_3')
    btn_star_4 = types.InlineKeyboardButton("4 ⭐", callback_data='new_review_4')
    btn_star_5 = types.InlineKeyboardButton("5 ⭐", callback_data='new_review_5')
    markup.add(types.InlineKeyboardButton("🔙 Назад", callback_data='back_to_menu_review'))

    user_id = call.from_user.id
    product_states[user_id] = product_id


    markup.add(btn_link)
    markup.add(btn_star_1, btn_star_2, btn_star_3, btn_star_4, btn_star_5)

    bot.delete_message(chat_id=call.message.chat.id, message_id=call.message.message_id)

    bot.send_photo(
        chat_id=call.message.chat.id,
        photo=media,
        caption=text,
        parse_mode='Markdown',
        reply_markup=markup
    )

    bot.answer_callback_query(call.id)

@bot.callback_query_handler(func=lambda call: call.data.startswith('new_review_'))
def handle_new_review(call):
    starCount = call.data.split('_')[-1]
    markup = types.InlineKeyboardMarkup()

    bot.delete_message(chat_id=call.message.chat.id, message_id=call.message.message_id)

    markup.add(types.InlineKeyboardButton("❌ Отмена", callback_data='back_to_menu_review'))

    bot.send_message(call.message.chat.id, "Напишите свой отзыв, а так же можете прикрепить к нему фото, а затем отправьте",
                     reply_markup=markup)

    user_id = call.from_user.id
    print(user_id)
    user_states[user_id] = 'awaiting_photo'
    star_states[user_id] = starCount

@bot.message_handler(content_types=['photo'])
def handle_photo_with_caption(message):
    user_id = message.from_user.id
    if user_states.get(user_id) == 'awaiting_photo':
        caption = message.caption
        photo = message.photo[-1]
        file_info = bot.get_file(photo.file_id)
        file_path = file_info.file_path
        downloaded_file = bot.download_file(file_path)
        product_id = product_states.get(user_id)
        rating = star_states.get(user_id)

        print(caption)
        print(product_id)
        print(rating)

        response = requests.post(f'{BASE_URL}/product/createReview',
                                 headers=headers,
                                 json={"productId": product_id,
                                    "rating": rating,
                                    "reviewText": caption})

        print(response.json())

        if response.ok:
            reviewId = response.json().get("id")
            filename = f"{photo.file_id}.jpg"
            with open(filename, 'wb') as f:
                f.write(downloaded_file)

            # Отправляем POST-запрос с фото и подписью
            with open(filename, 'rb') as photo_file:
                response = requests.post(
                    f'{BASE_URL}/files/addReviewImage',  # замените на ваш URL
                    files={'photo': photo_file},
                    data={'reviewId': reviewId}
                )

            # Проверка ответа сервера
            if response.ok:
                bot.send_message(message.chat.id, "✅ Фото отправлено на сервер.")
            else:
                bot.send_message(message.chat.id, f"❌ Ошибка отправки: {response.status_code}")

        # Очистка состояния
        user_states.pop(user_id, None)
        star_states.pop(user_id, None)
        product_states.pop(user_id, None)

        bot.send_message(message.chat.id, f"✅ Получено фото с подписью: {caption}")
    else:
        bot.send_message(message.chat.id, "❗ Сначала нажмите на кнопку, чтобы загрузить фото.")


@bot.callback_query_handler(func=lambda call: call.data.startswith('review_item_'))
def handle_review(call):
    # Получаем review_id из callback_data
    review_id = call.data.split('_')[-1]

    response = requests.get(f"{BASE_URL}/product/review/{review_id}", verify=False, headers=headers)
    review = response.json()
    print(response.json())

    response = requests.get(f"{BASE_URL}/product/{review.get("productId")}", verify=False, headers=headers)
    product = response.json()

    text_rating = ""

    for i in range (0, review.get("rating")):
        text_rating += "⭐"

    text = (f"{product.get("name")}\n"
            f"Текст отзыва: {review.get("reviewText")}\n"
            f"Рейтинг: {text_rating}\n"
            f"Статус: {review.get("status")}\n")

    markup = types.InlineKeyboardMarkup()
    markup.add(types.InlineKeyboardButton("🗑 Удалить", callback_data='delete_review'))
    markup.add(types.InlineKeyboardButton("🔙 Назад", callback_data='back_to_list_review'))

    bot.edit_message_text(
        chat_id=call.message.chat.id,
        message_id=call.message.message_id,
        text=text,
        reply_markup=markup
    )

    bot.answer_callback_query(call.id)


@bot.callback_query_handler(func=lambda call: True)
def callback_handler(call):
    headers["X-Telegram-User-Id"] = str(call.from_user.id)

    if call.data == 'menu_orders' or call.data == 'back_to_orders':

        response = requests.get(f"{BASE_URL}/order", headers=headers)

        orders = response.json()

        bot.edit_message_text(
            chat_id=call.message.chat.id,
            message_id=call.message.message_id,
            text="📖 Ваши заказы:",
            reply_markup=order_menu(orders)
        )

    elif call.data == 'menu_complete_orders' or call.data == 'back_to_complete_orders':

        response = requests.get(f"{BASE_URL}/completeOrder", headers=headers)

        completeOrders = response.json()

        bot.edit_message_text(
            chat_id=call.message.chat.id,
            message_id=call.message.message_id,
            text="📖 Ваши завершенные заказы:",
            reply_markup=complete_order_menu(completeOrders)
        )

    elif call.data == 'menu_profile':

        response = requests.get(f"{BASE_URL}/auth/userInfo", verify=False, headers=headers)

        profile = response.json()

        text = ("👤 Ваш профиль:\n\n"
                f"🆔 User ID: {profile.get("userId")}\n"
                f"🐶 Username: {profile.get("username")}\n"
                f"🧑 Имя: {profile.get("firstName")}\n"
                f"🛡️ Роли: {', '.join(profile.get("role"))}")

        bot.edit_message_text(
            chat_id=call.message.chat.id,
            message_id=call.message.message_id,
            text=text,
            reply_markup=profile_menu()
        )

    elif call.data == 'menu_expection_reviews' or call.data == 'back_to_menu_review':

        user_id = call.from_user.id
        user_states.pop(user_id, None)
        star_states.pop(user_id, None)
        product_states.pop(user_id, None)

        response = requests.get(f"{BASE_URL}/product/notReviewProducts", verify=False, headers=headers)

        products = response.json()

        productsInfo = []

        for product in products:
            response = requests.get(f"{BASE_URL}/product/{product}", verify=False, headers=headers)
            print(response.json())
            productsInfo.append(response.json())

        bot.edit_message_text(
            chat_id=call.message.chat.id,
            message_id=call.message.message_id,
            text="⏳ Ожидают отзывов:",
            reply_markup=expection_review_menu(productsInfo)
        )

    elif call.data == 'menu_review' or call.data == 'back_to_list_review':

        response = requests.get(f"{BASE_URL}/product/review", verify=False, headers=headers)

        reviews = response.json()

        print(reviews)

        bot.edit_message_text(
            chat_id=call.message.chat.id,
            message_id=call.message.message_id,
            text="📃 Ваши отзывы:",
            reply_markup=review_menu(reviews)
        )


    elif call.data == 'menu_faq':

        markup = types.InlineKeyboardMarkup()

        markup.add(types.InlineKeyboardButton("🔙 Назад", callback_data='back_to_main'))

        text = ("❓ Часто задаваемые вопросы:\n\n"
                "1. Где посмотреть мои заказы?\n"
                "В разделе «📦 Заказы» отображаются все ваши текущие и завершённые заказы.\n\n"
                "2. Как оставить отзыв о товаре\n"
                "Перейдите в раздел «✍️ Отзывы» и выберите нужный товар, чтобы оставить отзыв.\n\n"
                "3. Где найти сайт маркетплейса?\n"
                "Вы можете перейти на наш сайт по кнопке «🌐 Сайт» в главном меню или перейти по ссылке: click-and-shop.ru")

        bot.edit_message_text(
            chat_id=call.message.chat.id,
            message_id=call.message.message_id,
            text=text,
            reply_markup=markup
        )


    elif call.data == 'back_to_main':
        bot.edit_message_text(
            chat_id=call.message.chat.id,
            message_id=call.message.message_id,
            text="👋 Главное меню",
            reply_markup=main_menu()
        )

    elif call.data == 'catalog_item_1':
        markup = types.InlineKeyboardMarkup()
        markup.add(types.InlineKeyboardButton("🔙 Назад", callback_data='menu_catalog'))
        bot.edit_message_text(
            chat_id=call.message.chat.id,
            message_id=call.message.message_id,
            text="🛍 Товар 1:\nОписание товара...",
            reply_markup=markup
        )

    bot.answer_callback_query(call.id)

# Запуск Flask и бота
if __name__ == "__main__":
    from threading import Thread

    # Поток для телеграм-бота
    def run_bot():
        bot.infinity_polling()

    # Поток для Flask
    Thread(target=run_bot).start()
    app.run(host="0.0.0.0", port=5000)