package com.botmasterzzz.telegram.controller.telegram.gkh;

import com.botmasterzzz.bot.api.impl.methods.send.SendMessage;
import com.botmasterzzz.bot.api.impl.objects.Update;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.InlineKeyboardMarkup;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.ReplyKeyboardMarkup;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.buttons.KeyboardRow;
import com.botmasterzzz.telegram.config.annotations.BotController;
import com.botmasterzzz.telegram.config.annotations.BotRequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@BotController
public class GateFlagGkhMenuController {

    private static final Logger logger = LoggerFactory.getLogger(GateFlagGkhMenuController.class);

    private GkhMessageService gkhMessageService;

    @BotRequestMapping(value = "gkh-\uD83D\uDEA5Шлагбаум")
    public SendMessage gate(Update update) {
        String name = null != update.getMessage().getFrom().getUserName() ? update.getMessage().getFrom().getUserName() : update.getMessage().getFrom().getFirstName();

        InlineKeyboardMarkup inlineKeyboardMarkup = gkhMessageService.getInlineKeyboardForGate();

        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText("<b>Управление въездом/выездом на территорию дома ЖКХ №1!</b>\n" +
                        "Здесь вы можете управлять шлагбаумом: \uD83D\uDD3D")
                .setReplyMarkup(inlineKeyboardMarkup);
    }

}
