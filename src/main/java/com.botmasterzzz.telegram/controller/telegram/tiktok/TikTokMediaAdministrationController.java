package com.botmasterzzz.telegram.controller.telegram.tiktok;

import com.botmasterzzz.bot.api.impl.methods.send.SendMessage;
import com.botmasterzzz.bot.api.impl.methods.update.EditMessageReplyMarkup;
import com.botmasterzzz.bot.api.impl.objects.Update;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.InlineKeyboardMarkup;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.buttons.InlineKeyboardButton;
import com.botmasterzzz.telegram.config.annotations.BotController;
import com.botmasterzzz.telegram.config.annotations.BotRequestMapping;
import com.botmasterzzz.telegram.config.context.UserContextHolder;
import com.botmasterzzz.telegram.dto.CallBackData;
import com.botmasterzzz.telegram.entity.TelegramBotUserEntity;
import com.botmasterzzz.telegram.entity.TelegramUserMediaEntity;
import com.botmasterzzz.telegram.service.TelegramMediaService;
import com.botmasterzzz.telegram.service.TelegramUserService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@BotController
public class TikTokMediaAdministrationController {

    private static final Logger logger = LoggerFactory.getLogger(TikTokMediaAdministrationController.class);

    @Autowired
    private TelegramMediaService telegramMediaService;

    @Autowired
    private TelegramUserService telegramUserService;

    @Autowired
    private Gson gson;

    @BotRequestMapping(value = "tiktok-delete")
    public EditMessageReplyMarkup mediaDelete(Update update) {
        Long chatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        Long requestedUserId = Long.valueOf(update.getCallbackQuery().getFrom().getId());

        TelegramBotUserEntity requestedTelegramUser = telegramUserService.getTelegramUser(requestedUserId);
        boolean isRequestedUserAdmin = requestedTelegramUser.isAdmin();
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();

        CallBackData callBackData = UserContextHolder.currentContext().getCallBackData();
        Long telegramUserId = Long.valueOf(update.getCallbackQuery().getFrom().getId());
        Long fileId = callBackData.getFileId();

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = new ArrayList<>();

        EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
        editMessageReplyMarkup.setChatId(chatId);

        TelegramUserMediaEntity telegramUserMediaEntity;
//        if (isRequestedUserAdmin){
            telegramUserMediaEntity = telegramMediaService.telegramUserMediaGet(fileId);
            telegramUserMediaEntity.setDeleted(true);
            telegramMediaService.telegramUserMediaUpdate(telegramUserMediaEntity);
            InlineKeyboardButton restoreInlineButton = new InlineKeyboardButton();
            restoreInlineButton.setText("⚠️ Восстановить");
            restoreInlineButton.setCallbackData(gson.toJson(new CallBackData("restore", telegramUserId, fileId)));
            inlineKeyboardButtonsFirstRow.add(restoreInlineButton);
            logger.info("User id {} requested to delete a media resource: {}", requestedUserId, telegramUserMediaEntity);
//        }else {
//            InlineKeyboardButton restoreInlineButton = new InlineKeyboardButton();
//            restoreInlineButton.setText("⚠️ Недостаточно прав");
//            restoreInlineButton.setCallbackData(gson.toJson(new CallBackData("restricted", telegramUserId, fileId)));
//            inlineKeyboardButtonsFirstRow.add(restoreInlineButton);
//            logger.info("User id {} restricted to delete a media resource {}", requestedUserId, messageId);
//        }
        editMessageReplyMarkup.setMessageId(messageId);
        inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);
        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);
        editMessageReplyMarkup.setReplyMarkup(inlineKeyboardMarkup);
        return editMessageReplyMarkup;
    }

    @BotRequestMapping(value = "tiktok-restore")
    public EditMessageReplyMarkup mediaRestore(Update update) {
        Long chatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        Long requestedUserId = Long.valueOf(update.getCallbackQuery().getFrom().getId());

        TelegramBotUserEntity requestedTelegramUser = telegramUserService.getTelegramUser(requestedUserId);
        boolean isRequestedUserAdmin = requestedTelegramUser.isAdmin();
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();

        CallBackData callBackData = UserContextHolder.currentContext().getCallBackData();
        Long telegramUserId = Long.valueOf(update.getCallbackQuery().getFrom().getId());
        Long fileId = callBackData.getFileId();

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = new ArrayList<>();

        EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
        editMessageReplyMarkup.setChatId(chatId);

        TelegramUserMediaEntity telegramUserMediaEntity;
//        if (isRequestedUserAdmin){
            telegramUserMediaEntity = telegramMediaService.telegramUserMediaGet(fileId);
            telegramUserMediaEntity.setDeleted(false);
            telegramMediaService.telegramUserMediaUpdate(telegramUserMediaEntity);
            InlineKeyboardButton restoreInlineButton = new InlineKeyboardButton();
            restoreInlineButton.setText("❌ Удалить");
            restoreInlineButton.setCallbackData(gson.toJson(new CallBackData("delete", telegramUserId, fileId)));
            inlineKeyboardButtonsFirstRow.add(restoreInlineButton);
            logger.info("User id {} requested to restore a media resource: {}", requestedUserId, telegramUserMediaEntity);
//        }else {
//            InlineKeyboardButton restoreInlineButton = new InlineKeyboardButton();
//            restoreInlineButton.setText("⚠️ Недостаточно прав");
//            restoreInlineButton.setCallbackData(gson.toJson(new CallBackData("restricted", telegramUserId, fileId)));
//            inlineKeyboardButtonsFirstRow.add(restoreInlineButton);
//            logger.info("User id {} restricted to restore a media resource {}", requestedUserId, messageId);
//        }
        editMessageReplyMarkup.setMessageId(messageId);
        inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);
        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);
        editMessageReplyMarkup.setReplyMarkup(inlineKeyboardMarkup);
        return editMessageReplyMarkup;
    }

    @BotRequestMapping(value = "tiktok-/rassylka")
    public List<SendMessage> help(Update update) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("1️⃣");

        SendMessage sendMessage1 = new SendMessage();
        sendMessage1.setChatId(update.getMessage().getChatId());
        sendMessage1.setText(stringBuilder.toString());
        sendMessage1.enableHtml(true);

        SendMessage sendMessage2 = new SendMessage();
        stringBuilder.append("1️⃣1️⃣");
        sendMessage2.setChatId(update.getMessage().getChatId());
        sendMessage2.setText(stringBuilder.toString());
        sendMessage2.enableHtml(true);

        SendMessage sendMessage3 = new SendMessage();
        stringBuilder.append("1️⃣1️⃣1️⃣");
        sendMessage3.setChatId(update.getMessage().getChatId());
        sendMessage3.setText(stringBuilder.toString());
        sendMessage3.enableHtml(true);

        List<SendMessage> testData = new ArrayList<>();
        testData.add(sendMessage1);
        testData.add(sendMessage2);
        testData.add(sendMessage3);
        return testData;
    }

}
