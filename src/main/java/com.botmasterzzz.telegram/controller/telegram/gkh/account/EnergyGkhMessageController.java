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
public class EnergyGkhMessageController {

    private static final Logger logger = LoggerFactory.getLogger(EnergyGkhMessageController.class);

    @Autowired
    private GkhMessageService gkhMessageService;


    @BotRequestMapping(value = "gkh-⚡️Электроэнергия")
    public SendMessage gas(Update update) {

        //todo переписать под электричество
        InlineKeyboardMarkup inlineKeyboardMarkup = gkhMessageService.getInlineKeyboardForEnergyAccount();

        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText("<b>Здесь вы можете передать показания счетчика электроэнергии и получить переданные показания ранее</b>")
                .setReplyMarkup(inlineKeyboardMarkup);
    }

    @BotRequestMapping(value = "gkh-get_energy")
    public EditMessageText getEnergy(Update update) {

        //todo doAction() - отправка сообщения в кафку для получения переданных ранее показаний счетчика электроэнергии
        InlineKeyboardMarkup inlineKeyboardMarkup = gkhMessageService.getSendEnergyInlineKeyboardForAccount();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<b>Передача показаний счетчика электроэнергии.</b>\n");
        stringBuilder.append("Показания получены. 123,342 квт/ч.");
        stringBuilder.append("Отправить другие показания?");
        EditMessageText editMessageText = getEditMessage(stringBuilder.toString(), update);
        editMessageText.setReplyMarkup(inlineKeyboardMarkup);
        return editMessageText;
    }

    @BotRequestMapping(value = "gkh-send_energy")
    public EditMessageText sendEnergy(Update update) {

        //todo doAction() - отправка сообщения в кафку для отправки показаний счетчика электроэнергии
        InlineKeyboardMarkup inlineKeyboardMarkup = gkhMessageService.getGetEnergyInlineKeyboardForAccount();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<b>Передача показаний счетчика электроэнергии.</b>\n");
        stringBuilder.append("Показания переданы. Получить показания?");
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
