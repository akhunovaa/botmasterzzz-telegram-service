package com.botmasterzzz.telegram.controller.telegram.gkh;

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
public class MainGkhMenuController {

    private static final Logger logger = LoggerFactory.getLogger(MainGkhMenuController.class);

    @BotRequestMapping(value = "gkh-/start")
    public SendMessage start(Update update) {
        String name = null != update.getMessage().getFrom().getUserName() ? update.getMessage().getFrom().getUserName() : update.getMessage().getFrom().getFirstName();
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRowLineOne = new KeyboardRow();
        KeyboardRow keyboardRowLineTwo = new KeyboardRow();
        KeyboardRow keyboardRowLineThree = new KeyboardRow();
        keyboardRowLineOne.add("\uD83D\uDDA5Главное меню");
        keyboardRowLineOne.add("\uD83D\uDD10Личный кабинет");
        keyboardRowLineTwo.add("\uD83D\uDEA5Шлагбаум");
        keyboardRowLineTwo.add("\uD83D\uDCD2Контакты");
        keyboardRowLineThree.add("❓Помощь");
        keyboardRowLineThree.add("\uD83D\uDCF0Новости");
        keyboardRows.add(keyboardRowLineOne);
        keyboardRows.add(keyboardRowLineTwo);
        keyboardRows.add(keyboardRowLineThree);
        keyboard.setKeyboard(keyboardRows);

        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText("<b>Вас приветствует ЖКХ №1!</b>\n" +
                        "Позвольте узнать, что Вас беспокоит, " + name + "? \n" +
                        "Выберите раздел: \uD83D\uDD3D")
                .setReplyMarkup(keyboard);
    }

    @BotRequestMapping(value = "gkh-\uD83D\uDDA5Главное меню")
    public SendMessage main(Update update) {
        String name = null != update.getMessage().getFrom().getUserName() ? update.getMessage().getFrom().getUserName() : update.getMessage().getFrom().getFirstName();
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRowLineOne = new KeyboardRow();
        KeyboardRow keyboardRowLineTwo = new KeyboardRow();
        KeyboardRow keyboardRowLineThree = new KeyboardRow();
        keyboardRowLineOne.add("\uD83D\uDDA5Главное меню");
        keyboardRowLineOne.add("\uD83D\uDD10Личный кабинет");
        keyboardRowLineTwo.add("\uD83D\uDEA5Шлагбаум");
        keyboardRowLineTwo.add("\uD83D\uDCD2Контакты");
        keyboardRowLineThree.add("❓Помощь");
        keyboardRowLineThree.add("\uD83D\uDCF0Новости");
        keyboardRows.add(keyboardRowLineOne);
        keyboardRows.add(keyboardRowLineTwo);
        keyboardRows.add(keyboardRowLineThree);
        keyboard.setKeyboard(keyboardRows);

        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText("<b>Вас приветствует ЖКХ №1!</b>\n" +
                        "Позвольте узнать, что Вас беспокоит, " + name + "? \n" +
                        "Выберите раздел: \uD83D\uDD3D")
                .setReplyMarkup(keyboard);
    }


}
