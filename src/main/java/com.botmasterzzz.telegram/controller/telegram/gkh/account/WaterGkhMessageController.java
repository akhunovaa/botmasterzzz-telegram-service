package com.botmasterzzz.telegram.controller.telegram.gkh.account;

import com.botmasterzzz.bot.api.impl.methods.send.SendMessage;
import com.botmasterzzz.bot.api.impl.methods.update.EditMessageText;
import com.botmasterzzz.bot.api.impl.objects.Update;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.InlineKeyboardMarkup;
import com.botmasterzzz.telegram.config.annotations.BotController;
import com.botmasterzzz.telegram.config.annotations.BotRequestMapping;
import com.botmasterzzz.telegram.service.GkhMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@BotController
public class WaterGkhMessageController {

    private static final Logger logger = LoggerFactory.getLogger(WaterGkhMessageController.class);

    @Autowired
    private GkhMessageService gkhMessageService;


    @BotRequestMapping(value = "gkh-\uD83D\uDCA7ГВС/ХВС")
    public SendMessage gas(Update update) {

        //todo переписать под газ
        InlineKeyboardMarkup inlineKeyboardMarkup = gkhMessageService.getInlineKeyboardForWaterAccount();

        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText("<b>Здесь вы можете передать показания счетчика ГВС/ХВС и получить переданные показания ранее</b>")
                .setReplyMarkup(inlineKeyboardMarkup);
    }

    @BotRequestMapping(value = "gkh-get_hot_water")
    public EditMessageText getHWater(Update update) {

        //todo doAction() - отправка сообщения в кафку для получения переданных ранее показаний счетчика газа
        InlineKeyboardMarkup inlineKeyboardMarkup = gkhMessageService.getInlineKeyboardForWaterAccount(); //
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<b>Получение показаний счетчика ГВС.</b>\n");
        stringBuilder.append("Показания получены. 2,1 м3.");
        EditMessageText editMessageText = getEditMessage(stringBuilder.toString(), update);
        editMessageText.setReplyMarkup(inlineKeyboardMarkup);
        return editMessageText;
    }

    @BotRequestMapping(value = "gkh-send_hot_water")
    public EditMessageText sendHWater(Update update) {

        //todo doAction() - отправка сообщения в кафку для отправки показаний счетчика газа
        InlineKeyboardMarkup inlineKeyboardMarkup = gkhMessageService.getInlineKeyboardForWaterAccount(); //
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<b>Передача показаний счетчика ГВС.</b>\n");
        stringBuilder.append("Показания переданы.");
        EditMessageText editMessageText = getEditMessage(stringBuilder.toString(), update);
        editMessageText.setReplyMarkup(inlineKeyboardMarkup);
        return editMessageText;
    }

    @BotRequestMapping(value = "gkh-get_cold_water")
    public EditMessageText getCWater(Update update) {

        //todo doAction() - отправка сообщения в кафку для отправки показаний счетчика газа
        InlineKeyboardMarkup inlineKeyboardMarkup = gkhMessageService.getInlineKeyboardForWaterAccount(); //
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<b>Получение показаний счетчика ХВС.</b>\n");
        stringBuilder.append("Показания получены. 19,22 м3.");
        EditMessageText editMessageText = getEditMessage(stringBuilder.toString(), update);
        editMessageText.setReplyMarkup(inlineKeyboardMarkup);
        return editMessageText;
    }

    @BotRequestMapping(value = "gkh-send_cold_water")
    public EditMessageText sendCWater(Update update) {

        //todo doAction() - отправка сообщения в кафку для отправки показаний счетчика газа
        InlineKeyboardMarkup inlineKeyboardMarkup = gkhMessageService.getInlineKeyboardForWaterAccount(); //
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<b>Передача показаний счетчика ХВС.</b>\n");
        stringBuilder.append("Показания переданы.");
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
