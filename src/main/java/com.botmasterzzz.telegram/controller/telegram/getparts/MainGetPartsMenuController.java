package com.botmasterzzz.telegram.controller.telegram.getparts;

import com.botmasterzzz.bot.api.impl.methods.send.SendMessage;
import com.botmasterzzz.bot.api.impl.objects.Update;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.ReplyKeyboardMarkup;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.buttons.KeyboardRow;
import com.botmasterzzz.telegram.config.annotations.BotController;
import com.botmasterzzz.telegram.config.annotations.BotRequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@BotController
public class MainGetPartsMenuController {

    private static final Logger logger = LoggerFactory.getLogger(MainGetPartsMenuController.class);

    @BotRequestMapping(value = "getparts-/start")
    public SendMessage start(Update update) {
        String name = null != update.getMessage().getFrom().getUserName() ? update.getMessage().getFrom().getUserName() : update.getMessage().getFrom().getFirstName();
        ReplyKeyboardMarkup keyboard = getMainPageKeyboard();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<b>Добро пожаловать!</b>\n");
        stringBuilder.append("Приветствуем Вас у себя в онлайн магазине Get Parts, ").append(name).append("! \n");
        stringBuilder.append("\n");
        stringBuilder.append("Выберите раздел: \uD83D\uDD3D");
        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText(stringBuilder.toString())
                .setReplyMarkup(keyboard);
    }

    @BotRequestMapping(value = "getparts-\uD83D\uDDA5Главное меню")
    public SendMessage main(Update update) {
        String name = null != update.getMessage().getFrom().getUserName() ? update.getMessage().getFrom().getUserName() : update.getMessage().getFrom().getFirstName();
        ReplyKeyboardMarkup keyboard = getMainPageKeyboard();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<b>Добро пожаловать!</b>\n");
        stringBuilder.append("Приветствуем Вас у себя в онлайн магазине Get Parts, ").append(name).append("! \n");
        stringBuilder.append("\n");
        stringBuilder.append("Выберите раздел: \uD83D\uDD3D");
        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText(stringBuilder.toString())
                .setReplyMarkup(keyboard);
    }

    @BotRequestMapping(value = "getparts-\uD83D\uDCC4Новости")
    public SendMessage news(Update update) {
        ReplyKeyboardMarkup keyboard = getMainPageKeyboard();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<b>15 июля 2019г. Запуск бота!</b>\n");
        stringBuilder.append("<b>Запуск бота!</b>\n");
        stringBuilder.append("Наш портал запущен. Добро пожаловать! \n");
        stringBuilder.append("\n");
        stringBuilder.append("Выберите раздел: \uD83D\uDD3D");
        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText(stringBuilder.toString())
                .setReplyMarkup(keyboard);
    }

    @BotRequestMapping(value = "getparts-\uD83D\uDCE8Отзывы")
    public SendMessage feedback(Update update) {
        ReplyKeyboardMarkup keyboard = getMainPageKeyboard();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<b>тюхлов а.</b>\n");
        stringBuilder.append("<b>Комментарий:</b> Всё понравилось купил что надо.\n");
        stringBuilder.append("\n");

        stringBuilder.append("<b>Евгений Б.</b>\n");
        stringBuilder.append("<b>Достоинства:</b> Всё быстро и даже в 21:00 оформили в работу без денег! Оплатил ночью в 22:00. В 15:00 на след день забрал!\n");
        stringBuilder.append("<b>Комментарий:</b> Всё супер. Рекомендую всем.\n");
        stringBuilder.append("\n");

        stringBuilder.append("<b>Зина Д.</b>\n");
        stringBuilder.append("<b>Достоинства:</b> Очень внимательное отношение к выбору нужных запчастей. Грамотность менеджеров.\n");
        stringBuilder.append("<b>Комментарий:</b> Всё супер. Рекомендую всем.\n");
        stringBuilder.append("\n");
        stringBuilder.append("Выберите раздел: \uD83D\uDD3D");
        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText(stringBuilder.toString())
                .setReplyMarkup(keyboard);
    }

    @BotRequestMapping(value = "getparts-\uD83D\uDCD2Контакты")
    public SendMessage contacts(Update update) {
        ReplyKeyboardMarkup keyboard = getMainPageKeyboard();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<b>Адрес:</b>\n");
        stringBuilder.append("<b>г. Москва ул. Обычная д.3\n");
        stringBuilder.append("\n");
        stringBuilder.append("<b>Номер телефона:</b>\n");
        stringBuilder.append("+7(915)000-00-00\n");
        stringBuilder.append("\n");
        stringBuilder.append("<b>Телеграмм:</b>\n");
        stringBuilder.append("@getpartsrobot\n");
        stringBuilder.append("\n");
        stringBuilder.append("<b>Разработан при помощи:</b>\n");
        stringBuilder.append("https://botmasterzzz.com\n");
        stringBuilder.append("\n");
        stringBuilder.append("Выберите раздел: \uD83D\uDD3D");
        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText(stringBuilder.toString())
                .setReplyMarkup(keyboard);
    }
    @BotRequestMapping(value = "getparts-❓Помощь")
    public SendMessage help(Update update) {
        ReplyKeyboardMarkup keyboard = getMainPageKeyboard();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\uD83D\uDDA5<b>Главное меню</b>\n");
        stringBuilder.append("<b>Нажмите на эту кнопку, для того чтобы попасть на главную страницу нашего портала.\n");
        stringBuilder.append("\uD83D\uDCC4<b>Новости</b>\n");
        stringBuilder.append("Нажмите на эту кнопку, для того чтобы почитать наши последние новости.\n");
        stringBuilder.append("\uD83D\uDCE8<b>Отзывы</b>\n");
        stringBuilder.append("Нажмите на эту кнопку, чтобы почитать отзывы наших покупателей.\n");
        stringBuilder.append("\uD83D\uDCD2<b>Контакты</b>\n");
        stringBuilder.append("Нажмите на эту кнопку, для получения списка наших контактных данных.\n");
        stringBuilder.append("❓<b>Помощь</b>\n");
        stringBuilder.append("Раздел помощи.\n");
        stringBuilder.append("\uD83D\uDCC2<b>Каталог</b>\n");
        stringBuilder.append("Для перехода в каталог представленных товаров.\n");
        stringBuilder.append("\n");
        stringBuilder.append("Выберите раздел: \uD83D\uDD3D");
        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText(stringBuilder.toString())
                .setReplyMarkup(keyboard);
    }

    private ReplyKeyboardMarkup getMainPageKeyboard(){
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRowLineOne = new KeyboardRow();
        KeyboardRow keyboardRowLineTwo = new KeyboardRow();
        KeyboardRow keyboardRowLineThree = new KeyboardRow();
        keyboardRowLineOne.add("\uD83D\uDDA5Главное меню");
        keyboardRowLineOne.add("\uD83D\uDCC4Новости");
        keyboardRowLineTwo.add("\uD83D\uDCE8Отзывы");
        keyboardRowLineTwo.add("\uD83D\uDCD2Контакты");
        keyboardRowLineThree.add("❓Помощь");
        keyboardRowLineThree.add("\uD83D\uDCC2Каталог");
        keyboardRows.add(keyboardRowLineOne);
        keyboardRows.add(keyboardRowLineTwo);
        keyboardRows.add(keyboardRowLineThree);
        keyboard.setKeyboard(keyboardRows);
        return keyboard;
    }
}
