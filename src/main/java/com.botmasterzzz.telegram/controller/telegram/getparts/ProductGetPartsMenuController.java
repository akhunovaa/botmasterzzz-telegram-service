package com.botmasterzzz.telegram.controller.telegram.getparts;

import com.botmasterzzz.bot.api.impl.methods.update.EditMessageText;
import com.botmasterzzz.bot.api.impl.objects.Update;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.InlineKeyboardMarkup;
import com.botmasterzzz.telegram.config.annotations.BotController;
import com.botmasterzzz.telegram.config.annotations.BotRequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@BotController
public class ProductGetPartsMenuController {

    private static final Logger logger = LoggerFactory.getLogger(ProductGetPartsMenuController.class);

    @Autowired
    private GetPartsMessageService getPartsMessageService;

    @BotRequestMapping(value = "getparts-kamaz-tormoza")
    public EditMessageText kamazTormoza(Update update) {
        InlineKeyboardMarkup inlineKeyboardMarkup = getPartsMessageService.getInlineKeyboardForKamaz(0);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\uD83D\uDD27<b>Наименование</b>\n");
        stringBuilder.append("Барабан тормозной КАМАЗ (ОАО КАМАЗ)");
        stringBuilder.append("\n");
        stringBuilder.append("\uD83D\uDD27<b>Наименование</b>\n");
        stringBuilder.append("Барабан тормозной КАМАЗ (ОАО КАМАЗ)");
        stringBuilder.append("\n");
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
