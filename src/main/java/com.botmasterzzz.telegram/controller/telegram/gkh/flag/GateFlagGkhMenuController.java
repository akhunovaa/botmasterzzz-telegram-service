package com.botmasterzzz.telegram.controller.telegram.gkh.flag;

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
public class GateFlagGkhMenuController {

    private static final Logger logger = LoggerFactory.getLogger(GateFlagGkhMenuController.class);

    @Autowired
    private GkhMessageService gkhMessageService;

    @BotRequestMapping(value = "gkh-\uD83D\uDEA5Шлагбаум")
    public SendMessage gate(Update update) {

        InlineKeyboardMarkup inlineKeyboardMarkup = gkhMessageService.getInlineKeyboardForGate();

        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText("<b>Управление въездом/выездом на территорию дома ЖКХ №1!</b>\n" +
                        "Здесь вы можете управлять шлагбаумом: \uD83D\uDD3D")
                .setReplyMarkup(inlineKeyboardMarkup);
    }

    @BotRequestMapping(value = "gkh-gate_open")
    public EditMessageText gateOpen(Update update) {

        //todo doAction() - отправка сообщения в кафку для открытия шлагбаума
        InlineKeyboardMarkup inlineKeyboardMarkup = gkhMessageService.getCloseInlineKeyboardForGate();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<b>Управление въездом/выездом на территорию дома ЖКХ №1!</b>\n");
        stringBuilder.append("Шлагбаум открыт. Не забудьте закрыть: \uD83D\uDD3D");
        EditMessageText editMessageText = getEditMessage(stringBuilder.toString(), update);
        editMessageText.setReplyMarkup(inlineKeyboardMarkup);
        return editMessageText;
    }

    @BotRequestMapping(value = "gkh-gate_close")
    public EditMessageText gateClose(Update update) {

        //todo doAction() - отправка сообщения в кафку для закрытия шлагбаума
        InlineKeyboardMarkup inlineKeyboardMarkup = gkhMessageService.getOpenInlineKeyboardForGate();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<b>Управление въездом/выездом на территорию дома ЖКХ №1!</b>\n");
        stringBuilder.append("Шлагбаум закрыт. \uD83D\uDD3D");
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
