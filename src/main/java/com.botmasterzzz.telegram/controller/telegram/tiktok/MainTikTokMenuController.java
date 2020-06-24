package com.botmasterzzz.telegram.controller.telegram.tiktok;

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
public class MainTikTokMenuController {

    private static final Logger logger = LoggerFactory.getLogger(MainTikTokMenuController.class);

    @BotRequestMapping(value = "tiktok-/start")
    public SendMessage start(Update update) {
        String name = null != update.getMessage().getFrom().getUserName() ? update.getMessage().getFrom().getUserName() : update.getMessage().getFrom().getFirstName();
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRowLineOne = new KeyboardRow();
        KeyboardRow keyboardRowLineTwo = new KeyboardRow();
        KeyboardRow keyboardRowLineThree = new KeyboardRow();
        KeyboardRow keyboardRowLineFourth = new KeyboardRow();
        keyboardRowLineOne.add("\uD83D\uDCF2Видео");
        keyboardRowLineOne.add("\uD83D\uDCF2Фото");
        keyboardRowLineTwo.add("\uD83C\uDFACЗагрузить видео/фото");
        keyboardRowLineTwo.add("\uD83C\uDFACЗагрузить видео/фото(анонимно)");
        keyboardRowLineThree.add("\uD83D\uDC8EТОП");
//        keyboardRowLineThree.add("\uD83C\uDF81Мои медиа");
        keyboardRowLineFourth.add("\uD83D\uDCD2Контакты");
        keyboardRows.add(keyboardRowLineOne);
        keyboardRows.add(keyboardRowLineTwo);
        keyboardRows.add(keyboardRowLineThree);
        keyboardRows.add(keyboardRowLineFourth);
        keyboard.setKeyboard(keyboardRows);

        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText("<b>Здесь вы можете выложить \uD83D\uDCF9 фото/видео и оценивать \uD83D\uDCF8 фото/видео от других пользователей. С нами всегда интересно</b>\uD83E\uDD29, " + name + ".\n" +
                        "\n" +
                        "❗️/start для перехода на главное меню\n" +
                        "\uD83D\uDCF9 /video для получения случайного видео от бота\n" +
                        "\uD83D\uDCF8 /photo для получения случайного фото/картинки от бота\n" +
                        "\uD83D\uDCE4 /upload для чтобы загрузить видео/фото\n" +
                        "\n" +
                        "или \n" +
                        "<b>Выберите раздел ниже:</b> \uD83D\uDD3D")
                .setReplyMarkup(keyboard);
    }

    @BotRequestMapping(value = "tiktok-❓Помощь")
    public SendMessage help(Update update) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\uD83D\uDCF2<b>Новое видео/фото</b>\n");
        stringBuilder.append("Нажмите на эту кнопку, для того чтобы запросить новое случайное видео/фото случайного пользователя, загрузившего медиа файл.\n");
        stringBuilder.append("\n");
        stringBuilder.append("\uD83D\uDCD2<b>Контакты</b>\n");
        stringBuilder.append("Нажмите на эту кнопку, для получения списка наших контактных данных.\n");
        stringBuilder.append("\n");
        stringBuilder.append("❓<b>Помощь</b>\n");
        stringBuilder.append("Раздел помощи.\n");
        stringBuilder.append("\n");
        stringBuilder.append("\uD83C\uDFACЗагрузить фото\n");
        stringBuilder.append("Здесь вы можете загрузить видео или фото.\n");
        stringBuilder.append("\n");
        stringBuilder.append("Выберите раздел: \uD83D\uDD3D");
        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText(stringBuilder.toString());
    }

    @BotRequestMapping(value = "tiktok-\uD83D\uDCD2Контакты")
    public SendMessage contacts(Update update) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<b>Адрес:</b>\n");
        stringBuilder.append("г. Москва Варшавское шоссе д.2\n");
        stringBuilder.append("\n");
        stringBuilder.append("<b>Номер телефона:</b>\n");
        stringBuilder.append("+7(926)545-14-52\n");
        stringBuilder.append("\n");
        stringBuilder.append("<b>Телеграмм:</b>\n");
        stringBuilder.append("@leon4uk\n");
        stringBuilder.append("\n");
        stringBuilder.append("<b>Разработан при помощи:</b>\n");
        stringBuilder.append("https://botmasterzzz.com\n");
        stringBuilder.append("\n");
        stringBuilder.append("Выберите раздел: \uD83D\uDD3D");
        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText(stringBuilder.toString());
    }
}
