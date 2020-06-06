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
        keyboardRowLineOne.add("\uD83D\uDDA5Главное меню");
        keyboardRowLineTwo.add("\uD83D\uDCD2Контакты");
        keyboardRowLineThree.add("❓Помощь");
        keyboardRowLineThree.add("\uD83C\uDFACЗагрузить фото/видео");
        keyboardRows.add(keyboardRowLineOne);
        keyboardRows.add(keyboardRowLineTwo);
        keyboardRows.add(keyboardRowLineThree);
        keyboard.setKeyboard(keyboardRows);

        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText("<bvЗдесь вы можете выложить видео и оценивать видео других. С нами всегда интересно\uD83E\uDD29, " + name + ".\n" +
                        "Выберите раздел: \uD83D\uDD3D")
                .setReplyMarkup(keyboard);
    }

    @BotRequestMapping(value = "tiktok-\uD83D\uDDA5Главное меню")
    public SendMessage main(Update update) {
        String name = null != update.getMessage().getFrom().getUserName() ? update.getMessage().getFrom().getUserName() : update.getMessage().getFrom().getFirstName();
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRowLineOne = new KeyboardRow();
        KeyboardRow keyboardRowLineTwo = new KeyboardRow();
        KeyboardRow keyboardRowLineThree = new KeyboardRow();
        keyboardRowLineOne.add("\uD83D\uDDA5Главное меню");
        keyboardRowLineTwo.add("\uD83D\uDCD2Контакты");
        keyboardRowLineThree.add("❓Помощь");
        keyboardRowLineThree.add("\uD83C\uDFACЗагрузить фото/видео");
        keyboardRows.add(keyboardRowLineOne);
        keyboardRows.add(keyboardRowLineTwo);
        keyboardRows.add(keyboardRowLineThree);
        keyboard.setKeyboard(keyboardRows);

        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText("<bvЗдесь вы можете выложить видео и оценивать видео других. С нами всегда интересно\uD83E\uDD29, " + name + ".\n" +
                        "Выберите раздел: \uD83D\uDD3D")
                .setReplyMarkup(keyboard);
    }

    @BotRequestMapping(value = "tiktok-❓Помощь")
    public SendMessage help(Update update) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\uD83D\uDDA5<b>Главное меню</b>\n");
        stringBuilder.append("Нажмите на эту кнопку, для того чтобы попасть на главную страницу нашего портала.\n");
        stringBuilder.append("\n");
        stringBuilder.append("\uD83D\uDCD2<b>Контакты</b>\n");
        stringBuilder.append("Нажмите на эту кнопку, для получения списка наших контактных данных.\n");
        stringBuilder.append("\n");
        stringBuilder.append("❓<b>Помощь</b>\n");
        stringBuilder.append("Раздел помощи.\n");
        stringBuilder.append("\n");
        stringBuilder.append("\uD83C\uDFACЗагрузить фото/видео\n");
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
