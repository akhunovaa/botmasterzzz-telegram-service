package com.botmasterzzz.telegram.controller.telegram.reklam;

import com.botmasterzzz.bot.api.impl.methods.send.SendMessage;
import com.botmasterzzz.bot.api.impl.methods.update.EditMessageText;
import com.botmasterzzz.bot.api.impl.objects.Update;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.ReplyKeyboardMarkup;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.buttons.KeyboardRow;
import com.botmasterzzz.telegram.config.annotations.BotController;
import com.botmasterzzz.telegram.config.annotations.BotRequestMapping;
import com.botmasterzzz.telegram.config.context.UserContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@BotController
public class MainReklamMenuController {


    private static final Logger logger = LoggerFactory.getLogger(MainReklamMenuController.class);

    @BotRequestMapping(value = "reklam-/start")
    public SendMessage start(Update update) {
        String name = null != update.getMessage().getFrom().getUserName() ? update.getMessage().getFrom().getUserName() : update.getMessage().getFrom().getFirstName();
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRowLineOne = new KeyboardRow();
        KeyboardRow keyboardRowLineTwo = new KeyboardRow();
        keyboardRowLineOne.add("\uD83D\uDDA5Главное меню");
        keyboardRowLineOne.add("\uD83D\uDD10Личный кабинет");
        keyboardRowLineTwo.add("\uD83D\uDCF0Новости");
        keyboardRowLineTwo.add("\uD83D\uDCD2Контакты");
        keyboardRows.add(keyboardRowLineOne);
        keyboardRows.add(keyboardRowLineTwo);
        keyboard.setKeyboard(keyboardRows);

        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText("Добро пожаловать! \n" +
                        "Здесь вы можете разместить предложение по привлечению трафика или же выбрать из существующих\n" +
                        "\n" +
                        "Выберите раздел \uD83D\uDD3D")
                .setReplyMarkup(keyboard);
    }

    @BotRequestMapping(value = "reklam-\uD83D\uDDA5Главное меню")
    public SendMessage main(Update update) {
        String name = null != update.getMessage().getFrom().getUserName() ? update.getMessage().getFrom().getUserName() : update.getMessage().getFrom().getFirstName();
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRowLineOne = new KeyboardRow();
        KeyboardRow keyboardRowLineTwo = new KeyboardRow();
        keyboardRowLineOne.add("\uD83D\uDDA5Главное меню");
        keyboardRowLineOne.add("\uD83D\uDD10Личный кабинет");
        keyboardRowLineTwo.add("\uD83D\uDCF0Новости");
        keyboardRowLineTwo.add("\uD83D\uDCD2Контакты");
        keyboardRows.add(keyboardRowLineOne);
        keyboardRows.add(keyboardRowLineTwo);
        keyboard.setKeyboard(keyboardRows);


        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText("<b>Главное меню</b>\n" +
                        "Выберите раздел: \uD83D\uDD3D")
                .setReplyMarkup(keyboard);
    }



    @BotRequestMapping(value = "reklam-\uD83D\uDCD2Контакты")
    public SendMessage contacts(Update update) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<b>Бот разработан командой BMZzz</b>\n");
        stringBuilder.append("https://botmasterzzz.com/\n");
        stringBuilder.append("\n");
        stringBuilder.append("<b>По вопросам обращаться к</b>\n");
        stringBuilder.append("@balsakas\n");
        stringBuilder.append("\n");
        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText(stringBuilder.toString());
    }

    @BotRequestMapping(value = "reklam-\uD83D\uDCF0Новости")
    public SendMessage news(Update update) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<b>18 сентярбря 2020г. Запуск бота!</b>\n");
        stringBuilder.append("<b>Запуск бота!</b>\n");
        stringBuilder.append("Наш портал запущен. Добро пожаловать! \n");
        stringBuilder.append("\n");
        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText(stringBuilder.toString());
    }

}
