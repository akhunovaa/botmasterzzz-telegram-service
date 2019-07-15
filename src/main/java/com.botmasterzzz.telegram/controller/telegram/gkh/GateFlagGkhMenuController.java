package com.botmasterzzz.telegram.controller.telegram.gkh;

import com.botmasterzzz.bot.api.impl.methods.send.SendMessage;
import com.botmasterzzz.bot.api.impl.objects.Update;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.InlineKeyboardMarkup;
import com.botmasterzzz.telegram.config.annotations.BotController;
import com.botmasterzzz.telegram.config.annotations.BotRequestMapping;
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
    public SendMessage gateOpen(Update update) {

        InlineKeyboardMarkup inlineKeyboardMarkup = gkhMessageService.getCloseInlineKeyboardForGate();

        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText("<b>Управление въездом/выездом на территорию дома ЖКХ №1!</b>\n" +
                        "Шлагбаум открыт. Не забудьте закрыть: \uD83D\uDD3D")
                .setReplyMarkup(inlineKeyboardMarkup);
    }

    @BotRequestMapping(value = "gkh-gate_close")
    public SendMessage gateClose(Update update) {

        InlineKeyboardMarkup inlineKeyboardMarkup = gkhMessageService.getOpenInlineKeyboardForGate();

        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText("<b>Управление въездом/выездом на территорию дома ЖКХ №1!</b>\n" +
                        "Шлагбаум закрыт. \uD83D\uDD3D")
                .setReplyMarkup(inlineKeyboardMarkup);
    }

}
