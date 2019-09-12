package com.botmasterzzz.telegram.controller.telegram.getparts;

import com.botmasterzzz.bot.api.impl.methods.send.SendPhoto;
import com.botmasterzzz.bot.api.impl.methods.update.EditMessageText;
import com.botmasterzzz.bot.api.impl.objects.InputFile;
import com.botmasterzzz.bot.api.impl.objects.Update;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.InlineKeyboardMarkup;
import com.botmasterzzz.telegram.config.annotations.BotController;
import com.botmasterzzz.telegram.config.annotations.BotRequestMapping;
import com.botmasterzzz.telegram.config.context.UserContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

@BotController
public class InnerCatalogGetPartsMenuController {

    private static final Logger logger = LoggerFactory.getLogger(InnerCatalogGetPartsMenuController.class);

    @Autowired
    private GetPartsMessageService getPartsMessageService;

    @BotRequestMapping(value = "getparts-kamaz")
    public EditMessageText kamaz(Update update) {
        InlineKeyboardMarkup inlineKeyboardMarkup = getPartsMessageService.getInlineKeyboardForKamaz();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("⚙️<b>Раздел</b>\n");
        stringBuilder.append("Выберите наименование раздела:");
        EditMessageText editMessageText = getEditMessage(stringBuilder.toString(), update);
        editMessageText.setReplyMarkup(inlineKeyboardMarkup);
        return editMessageText;
    }

    @BotRequestMapping(value = "getparts-movelex")
    public EditMessageText movelex(Update update) {
        InlineKeyboardMarkup inlineKeyboardMarkup = getPartsMessageService.getInlineKeyboardForMovelex();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("⚙️<b>Раздел</b>\n");
        stringBuilder.append("Выберите наименование раздела:");
        EditMessageText editMessageText = getEditMessage(stringBuilder.toString(), update);
        editMessageText.setReplyMarkup(inlineKeyboardMarkup);
        return editMessageText;
    }

    @BotRequestMapping(value = "getparts-nefaz")
    public EditMessageText nefaz(Update update) {
        InlineKeyboardMarkup inlineKeyboardMarkup = getPartsMessageService.getInlineKeyboardForNefaz();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("⚙️<b>Раздел</b>\n");
        stringBuilder.append("Выберите наименование раздела:");
        EditMessageText editMessageText = getEditMessage(stringBuilder.toString(), update);
        editMessageText.setReplyMarkup(inlineKeyboardMarkup);
        return editMessageText;
    }

    @BotRequestMapping(value = "getparts-belzan")
    public EditMessageText belzan(Update update) {
        InlineKeyboardMarkup inlineKeyboardMarkup = getPartsMessageService.getInlineKeyboardForBelzan();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("⚙️<b>Раздел</b>\n");
        stringBuilder.append("Выберите наименование раздела:");
        EditMessageText editMessageText = getEditMessage(stringBuilder.toString(), update);
        editMessageText.setReplyMarkup(inlineKeyboardMarkup);
        return editMessageText;

    }

    @BotRequestMapping(value = "getparts-mercedes")
    public EditMessageText mercedes(Update update) {
        InlineKeyboardMarkup inlineKeyboardMarkup = getPartsMessageService.getInlineKeyboardForMercedes();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("⚙️<b>Раздел</b>\n");
        stringBuilder.append("Выберите наименование раздела:");
        EditMessageText editMessageText = getEditMessage(stringBuilder.toString(), update);
        editMessageText.setReplyMarkup(inlineKeyboardMarkup);
        return editMessageText;
    }

    @BotRequestMapping(value = "getparts-photo-part")
    public SendPhoto photo(Update update) {
        long partId = UserContextHolder.currentContext().getPartId();
        String fileName = "/home/repository/get_parts/images/" + partId + "/1-image.jpg";
        SendPhoto sendPhoto = new SendPhoto();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("⚙️<b>Фото №1</b>\n");
        File file = new File(fileName);
        sendPhoto.setPhoto(new InputFile(file, "image-one"));
        sendPhoto.setCaption(stringBuilder.toString());
        sendPhoto.setChatId(update.getMessage().getChatId());
        return sendPhoto;
    }

    @BotRequestMapping(value = "getparts-return-to-catalog")
    public EditMessageText catalog(Update update) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\uD83D\uDCC2<b>Каталог</b>\n");
        stringBuilder.append("Выберите раздел:\n");
        InlineKeyboardMarkup inlineKeyboardMarkup = getPartsMessageService.getInlineKeyboardForCatalog();
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
