package com.botmasterzzz.telegram.controller.telegram.gkh;

import com.botmasterzzz.bot.api.impl.methods.send.SendMessage;
import com.botmasterzzz.bot.api.impl.methods.update.EditMessageText;
import com.botmasterzzz.bot.api.impl.objects.Update;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.ReplyKeyboardMarkup;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.buttons.KeyboardRow;
import com.botmasterzzz.telegram.config.annotations.BotController;
import com.botmasterzzz.telegram.config.annotations.BotRequestMapping;
import com.botmasterzzz.telegram.service.GkhMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@BotController
public class AccountGkhMenuController {

    private static final Logger logger = LoggerFactory.getLogger(AccountGkhMenuController.class);

    @Autowired
    private GkhMessageService gkhMessageService;

    @BotRequestMapping(value = "gkh-\uD83D\uDD10Личный кабинет")
    public SendMessage gate(Update update) {

        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRowLineOne = new KeyboardRow();
        KeyboardRow keyboardRowLineTwo = new KeyboardRow();
        KeyboardRow keyboardRowLineThree = new KeyboardRow();
        keyboardRowLineOne.add("\uD83D\uDDA5Главное меню");
        keyboardRowLineOne.add("⚡️Электроэнергия");
        keyboardRowLineTwo.add("\uD83D\uDEA7Заявки");
        keyboardRowLineTwo.add("\uD83D\uDCA8Газ");
        keyboardRowLineThree.add("\uD83D\uDCDDОбъявления");
        keyboardRowLineThree.add("\uD83D\uDCA7ГВС/ХВС");
        keyboardRows.add(keyboardRowLineOne);
        keyboardRows.add(keyboardRowLineTwo);
        keyboardRows.add(keyboardRowLineThree);
        keyboard.setKeyboard(keyboardRows);

        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText("<b>Вас приветствует ЖКХ №1!</b>\n" +
                        "Здесь Вы можете управлять личным кабинетом. \n" +
                        "Выберите раздел: \uD83D\uDD3D")
                .setReplyMarkup(keyboard);
    }

    private EditMessageText getEditMessage(String text, Update update) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(update.getCallbackQuery().getMessage().getChatId());
        editMessageText.setText(text);
        editMessageText.enableHtml(Boolean.TRUE);
        editMessageText.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        return editMessageText;
    }

}
