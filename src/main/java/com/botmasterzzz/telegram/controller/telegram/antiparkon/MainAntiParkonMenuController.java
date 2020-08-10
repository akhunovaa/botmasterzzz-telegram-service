package com.botmasterzzz.telegram.controller.telegram.antiparkon;

import com.botmasterzzz.bot.api.impl.methods.send.SendMessage;
import com.botmasterzzz.bot.api.impl.objects.Update;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.ReplyKeyboardMarkup;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.buttons.KeyboardRow;
import com.botmasterzzz.telegram.config.annotations.BotController;
import com.botmasterzzz.telegram.config.annotations.BotRequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@BotController
public class MainAntiParkonMenuController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainAntiParkonMenuController.class);

    @BotRequestMapping(value = "parkon-/start")
    public SendMessage start(Update update) {
        String name = null != update.getMessage().getFrom().getUserName() ? update.getMessage().getFrom().getUserName() : update.getMessage().getFrom().getFirstName();
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRowLineTop = new KeyboardRow();
        KeyboardRow keyboardRowLineOne = new KeyboardRow();
        KeyboardRow keyboardRowLineTwo = new KeyboardRow();
//        KeyboardRow keyboardRowLineThree = new KeyboardRow();
        KeyboardRow keyboardRowLineFourth = new KeyboardRow();
        keyboardRowLineTop.add("\uD83D\uDCF2 Главное меню");
        keyboardRowLineOne.add("\uD83D\uDD0E Поиск");
        keyboardRowLineTwo.add("\uD83D\uDCE8 Отзывы");
        keyboardRowLineTwo.add("❓Помощь");
        keyboardRowLineFourth.add("\uD83D\uDCD2 Контакты");
        keyboardRows.add(keyboardRowLineTop);
        keyboardRows.add(keyboardRowLineOne);
        keyboardRows.add(keyboardRowLineTwo);
//        keyboardRows.add(keyboardRowLineThree);
        keyboardRows.add(keyboardRowLineFourth);
        keyboard.setKeyboard(keyboardRows);

        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText("<b>" + name + "</b>, добро пожаловать.\n\n" +
                        "\uD83D\uDD25 <b>Новости</b> \uD83D\uDD25 \n" + BotMessageHelperUtil.getNewsMessage() +
                        "\n" +
                        "❗️/start для перехода на главное меню\n" +
                        "\uD83D\uDD0E Поиск для поиска необходимого номера по базе данных\n" +
                        "\uD83D\uDC8E Отзывы по нашему боту\n" +
                        "❓Помощь для получения справки\n" +
                        "\uD83D\uDCD2Контакты для обратной связи\n" +
                        "\n")
                .setReplyMarkup(keyboard);
    }

    @BotRequestMapping(value = "parkon-\uD83D\uDCF2 Главное меню")
    public SendMessage main(Update update) {
        String name = null != update.getMessage().getFrom().getUserName() ? update.getMessage().getFrom().getUserName() : update.getMessage().getFrom().getFirstName();
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRowLineTop = new KeyboardRow();
        KeyboardRow keyboardRowLineOne = new KeyboardRow();
        KeyboardRow keyboardRowLineTwo = new KeyboardRow();
//        KeyboardRow keyboardRowLineThree = new KeyboardRow();
        KeyboardRow keyboardRowLineFourth = new KeyboardRow();
        keyboardRowLineTop.add("\uD83D\uDCF2 Главное меню");
        keyboardRowLineOne.add("\uD83D\uDD0E Поиск");
        keyboardRowLineTwo.add("\uD83D\uDCE8 Отзывы");
        keyboardRowLineTwo.add("❓Помощь");
        keyboardRowLineFourth.add("\uD83D\uDCD2 Контакты");
        keyboardRows.add(keyboardRowLineTop);
        keyboardRows.add(keyboardRowLineOne);
        keyboardRows.add(keyboardRowLineTwo);
//        keyboardRows.add(keyboardRowLineThree);
        keyboardRows.add(keyboardRowLineFourth);
        keyboard.setKeyboard(keyboardRows);

        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText("<b>" + name + "</b>, добро пожаловать.\n\n" +
                        "\uD83D\uDD25 <b>Новости</b> \uD83D\uDD25 \n" + BotMessageHelperUtil.getNewsMessage() +
                        "\n" +
                        "❗️/start для перехода на главное меню\n" +
                        "\uD83D\uDD0E Поиск для поиска необходимого номера по базе данных\n" +
                        "\uD83D\uDC8E Отзывы для отзывов по нашему боту\n" +
                        "❓Помощь для получения справки\n" +
                        "\uD83D\uDCD2 Контакты для обратной связи\n" +
                        "\n")
                .setReplyMarkup(keyboard);
    }

    @BotRequestMapping(value = "parkon-❓Помощь")
    public SendMessage help(Update update) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\uD83D\uDCF2<b> Главное меню</b>\n");
        stringBuilder.append("Нажмите на эту кнопку, для перехода на главное меню бота.\n");
        stringBuilder.append("\n");
        stringBuilder.append("\uD83D\uDD0E<b>Поиск</b>\n");
        stringBuilder.append("Нажмите на эту кнопку, чтобы найти необходимый для вас автомобиль по номеру телефона\n");
        stringBuilder.append("\n");
        stringBuilder.append("\uD83D\uDC8E <b>Отзывы</b>\n");
        stringBuilder.append("Раздел с отзывами от реальных пользователей.\n");
        stringBuilder.append("\n");
        stringBuilder.append("\uD83D\uDCD2<b>Контакты</b>\n");
        stringBuilder.append("Для связи с нами используйте эту кнопку.\n");
        stringBuilder.append("\n");
        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText(stringBuilder.toString());
    }


    @BotRequestMapping(value = "parkon-\uD83D\uDCE8 Отзывы")
    public SendMessage feedback(Update update) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<b>тюхлов а.</b>\n");
        stringBuilder.append("<b>Комментарий:</b> Всё понравилось и нашел что надо.\n");
        stringBuilder.append("\n");

        stringBuilder.append("<b>Евгений Б.</b>\n");
        stringBuilder.append("<b>Достоинства:</b> Всё быстро и главное бесплатно!\n");
        stringBuilder.append("<b>Комментарий:</b> Всё супер. Рекомендую всем.\n");
        stringBuilder.append("\n");

        stringBuilder.append("<b>Зина Д.</b>\n");
        stringBuilder.append("<b>Достоинства:</b> Очень внимательное отношусь к парковке амтомобиля и данный бот мне очень помог.\n");
        stringBuilder.append("\n");
        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText(stringBuilder.toString());
    }

    @BotRequestMapping(value = "parkon-\uD83D\uDCD2 Контакты")
    public SendMessage contacts(Update update) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<b>Адрес:</b>\n");
        stringBuilder.append("г. Москва, Варшавское шоссе д.2\n");
        stringBuilder.append("\n");
        stringBuilder.append("<b>Номер телефона:</b>\n");
        stringBuilder.append("+7(926)545-14-52\n");
        stringBuilder.append("\n");
        stringBuilder.append("<b>Телеграмм:</b>\n");
        stringBuilder.append("@leon4uk\n");
        stringBuilder.append("\n");
        stringBuilder.append("<b>Разработан при помощи:</b>\n");
        stringBuilder.append("http://botmasterzzz.ru\n");
        stringBuilder.append("https://botmasterzzz.com\n");
        stringBuilder.append("\n");
        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText(stringBuilder.toString());
    }
}
