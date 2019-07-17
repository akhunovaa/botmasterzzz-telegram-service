package com.botmasterzzz.telegram.controller.telegram.gkh.account;

import com.botmasterzzz.bot.api.impl.methods.send.SendMessage;
import com.botmasterzzz.bot.api.impl.methods.update.EditMessageText;
import com.botmasterzzz.bot.api.impl.objects.Update;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.InlineKeyboardMarkup;
import com.botmasterzzz.telegram.config.annotations.BotController;
import com.botmasterzzz.telegram.config.annotations.BotRequestMapping;
import com.botmasterzzz.telegram.controller.telegram.gkh.GkhMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@BotController
public class GasGkhMessageController {

    private static final Logger logger = LoggerFactory.getLogger(EnergyGkhMessageController.class);

    @Autowired
    private GkhMessageService gkhMessageService;


    @BotRequestMapping(value = "gkh-\uD83D\uDCA8Газ")
    public SendMessage gas(Update update) {

        //todo переписать под газ
        InlineKeyboardMarkup inlineKeyboardMarkup = gkhMessageService.getInlineKeyboardForGasAccount();

        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText("<b>Здесь вы можете передать показания счетчика газа и получить переданные показания ранее</b>")
                .setReplyMarkup(inlineKeyboardMarkup);
    }

    @BotRequestMapping(value = "gkh-get_gas")
    public EditMessageText getGas(Update update) {

        //todo doAction() - отправка сообщения в кафку для получения переданных ранее показаний счетчика газа
        InlineKeyboardMarkup inlineKeyboardMarkup = gkhMessageService.getGetGasInlineKeyboardForAccount(); //
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<b>Передача показаний счетчика газа.</b>\n");
        stringBuilder.append("Показания переданы. Получить показания?");
        EditMessageText editMessageText = getEditMessage(stringBuilder.toString(), update);
        editMessageText.setReplyMarkup(inlineKeyboardMarkup);
        return editMessageText;
    }

    @BotRequestMapping(value = "gkh-send_gas")
    public EditMessageText sendGas(Update update) {

        //todo doAction() - отправка сообщения в кафку для отправки показаний счетчика газа
        InlineKeyboardMarkup inlineKeyboardMarkup = gkhMessageService.getSendGasInlineKeyboardForAccount(); //
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<b>Передача показаний счетчика газа.</b>\n");
        stringBuilder.append("Показания получены. 123,342 м3.");
        stringBuilder.append("Отправить другие показания?");
        EditMessageText editMessageText = getEditMessage(stringBuilder.toString(), update);
        editMessageText.setReplyMarkup(inlineKeyboardMarkup);
        return editMessageText;
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
