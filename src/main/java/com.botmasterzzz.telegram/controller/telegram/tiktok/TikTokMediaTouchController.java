package com.botmasterzzz.telegram.controller.telegram.tiktok;

import com.botmasterzzz.bot.api.impl.methods.update.EditMessageReplyMarkup;
import com.botmasterzzz.bot.api.impl.objects.Update;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.InlineKeyboardMarkup;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.buttons.InlineKeyboardButton;
import com.botmasterzzz.telegram.config.annotations.BotController;
import com.botmasterzzz.telegram.config.annotations.BotRequestMapping;
import com.botmasterzzz.telegram.config.context.UserContextHolder;
import com.botmasterzzz.telegram.dto.CallBackData;
import com.botmasterzzz.telegram.entity.TelegramBotUserEntity;
import com.botmasterzzz.telegram.entity.TelegramMediaStatisticEntity;
import com.botmasterzzz.telegram.entity.TelegramUserMediaEntity;
import com.botmasterzzz.telegram.service.DatabaseService;
import com.botmasterzzz.telegram.service.TelegramMediaService;
import com.botmasterzzz.telegram.service.TelegramUserService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@BotController
public class TikTokMediaTouchController {

    private static final Logger logger = LoggerFactory.getLogger(TikTokMediaTouchController.class);

    private final TelegramMediaService telegramMediaService;

    private final DatabaseService databaseService;

    private final TelegramUserService telegramUserService;

    private final Gson gson;

    public TikTokMediaTouchController(TelegramMediaService telegramMediaService, DatabaseService databaseService, Gson gson, TelegramUserService telegramUserService) {
        this.telegramMediaService = telegramMediaService;
        this.databaseService = databaseService;
        this.gson = gson;
        this.telegramUserService = telegramUserService;
    }

    @BotRequestMapping(value = "tiktok-heart")
    public EditMessageReplyMarkup heartTouch(Update update) {
        Long chatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        Long requestedUserId = Long.valueOf(update.getCallbackQuery().getFrom().getId());

        TelegramBotUserEntity requestedTelegramUser = telegramUserService.getTelegramUser(requestedUserId);
        boolean isRequestedUserAdmin = requestedTelegramUser.isAdmin();

        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        String restricted = "";
        CallBackData callBackData = UserContextHolder.currentContext().getCallBackData();
        boolean isFromProfile = null != callBackData.getL();
        Integer offset = callBackData.getO();
        Integer limit = callBackData.getL();
        Long telegramUserId = Long.valueOf(update.getCallbackQuery().getFrom().getId());
        Long fileId = callBackData.getFileId();
        Optional<TelegramMediaStatisticEntity> telegramMediaStatisticOptionalEntity = telegramMediaService.findHeartUserTouch(telegramUserId, fileId);
        if (telegramMediaStatisticOptionalEntity.isPresent()) {
            logger.info("Presents {} ", telegramMediaStatisticOptionalEntity.get());
            restricted = " \uD83D\uDEAB";
        } else {
            telegramMediaService.telegramUserMediaTouchAdd(telegramUserId, fileId, "HEART");
            logger.info("Touch added telegramUserId: {} fileId: {} TouchType: {}", telegramUserId, fileId, "HEART");
        }

        TelegramUserMediaEntity telegramUserMediaEntity = telegramMediaService.telegramUserMediaGet(fileId);
        boolean isAnon = telegramUserMediaEntity.isAnon();

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();

        List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsCommentRow = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsDeleteRow = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsProfileDataRow = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsArrowsRow = new ArrayList<>();

        long hearCount = telegramMediaService.countUserTouch(fileId, "HEART");
        long likeCount = telegramMediaService.countUserTouch(fileId, "LIKE");
        long dislikeCount = telegramMediaService.countUserTouch(fileId, "DISLIKE");
        long discussCount = databaseService.getCommentsForMedia(fileId, 0, 10000).size();

        InlineKeyboardButton heartInlineButton = new InlineKeyboardButton();
        heartInlineButton.setText("❤️  " + hearCount + (restricted.equals("") ? "" : restricted));
        CallBackData heartCallBackData = new CallBackData("heart", telegramUserId, fileId);
        if (isFromProfile){
            heartCallBackData.setO(offset);
            heartCallBackData.setL(limit);
        }
        heartInlineButton.setCallbackData(gson.toJson(heartCallBackData));

        InlineKeyboardButton likeInlineButton = new InlineKeyboardButton();
        likeInlineButton.setText("\uD83D\uDC4D " + likeCount);
        CallBackData likeCallBackData = new CallBackData("like", telegramUserId, fileId);
        if (isFromProfile){
            likeCallBackData.setO(offset);
            likeCallBackData.setL(limit);
        }
        likeInlineButton.setCallbackData(gson.toJson(likeCallBackData));

        InlineKeyboardButton dislikeInlineButton = new InlineKeyboardButton();
        dislikeInlineButton.setText("\uD83D\uDC4E️️ " + dislikeCount);
        CallBackData dislikeCallBackData = new CallBackData("dislike", telegramUserId, fileId);
        if (isFromProfile){
            dislikeCallBackData.setO(offset);
            dislikeCallBackData.setL(limit);
        }
        dislikeInlineButton.setCallbackData(gson.toJson(dislikeCallBackData));

        inlineKeyboardButtonsFirstRow.add(heartInlineButton);
        inlineKeyboardButtonsFirstRow.add(likeInlineButton);
        inlineKeyboardButtonsFirstRow.add(dislikeInlineButton);
        inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);

        if (isFromProfile){
            Long mediaForUser = UserContextHolder.currentContext().getForUserId();
            InlineKeyboardButton leftArrowButton = new InlineKeyboardButton();
            leftArrowButton.setText("◀️");
            CallBackData leftButtonData = new CallBackData("prfl", mediaForUser, fileId);
            leftButtonData.setOffset(offset);
            leftArrowButton.setCallbackData(gson.toJson(leftButtonData));

            InlineKeyboardButton rightArrowButton = new InlineKeyboardButton();
            rightArrowButton.setText("▶️️");
            CallBackData rightButtonData = new CallBackData("prfl", mediaForUser, fileId);
            rightButtonData.setOffset(limit);
            rightArrowButton.setCallbackData(gson.toJson(rightButtonData));

            inlineKeyboardButtonsArrowsRow.add(leftArrowButton);
            inlineKeyboardButtonsArrowsRow.add(rightArrowButton);
            inlineKeyboardButtons.add(inlineKeyboardButtonsArrowsRow);
        }

        InlineKeyboardButton commentInlineButton = new InlineKeyboardButton();
        commentInlineButton.setText("\uD83D\uDCDD Обсудить (\uD83D\uDCE8" + discussCount + ")") ;
        commentInlineButton.setCallbackData(gson.toJson(new CallBackData("comment", telegramUserId, fileId)));
        inlineKeyboardButtonsCommentRow.add(commentInlineButton);
        inlineKeyboardButtons.add(inlineKeyboardButtonsCommentRow);

        if (!isAnon && !isFromProfile){
            InlineKeyboardButton profileInfoInlineButton = new InlineKeyboardButton();
            profileInfoInlineButton.setText("\uD83D\uDCF2 Посты пользователя") ;
            profileInfoInlineButton.setCallbackData(gson.toJson(new CallBackData("more", telegramUserId, fileId)));
            inlineKeyboardButtonsProfileDataRow.add(profileInfoInlineButton);
            inlineKeyboardButtons.add(inlineKeyboardButtonsProfileDataRow);
        }

        if (isRequestedUserAdmin){
            InlineKeyboardButton deleteInlineButton = new InlineKeyboardButton();
            deleteInlineButton.setText("❌ Удалить");
            deleteInlineButton.setCallbackData(gson.toJson(new CallBackData("delete", telegramUserId, fileId)));
            inlineKeyboardButtonsDeleteRow.add(deleteInlineButton);
            inlineKeyboardButtons.add(inlineKeyboardButtonsDeleteRow);
        }

        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);

        EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
        editMessageReplyMarkup.setChatId(chatId);
        editMessageReplyMarkup.setMessageId(messageId);
        editMessageReplyMarkup.setReplyMarkup(inlineKeyboardMarkup);
        logger.info("User id {} heart updated to media message {} ", telegramUserMediaEntity.getTelegramBotUserEntity().getId(), telegramUserMediaEntity);
        return editMessageReplyMarkup;
    }

    @BotRequestMapping(value = "tiktok-like")
    public EditMessageReplyMarkup likeTouch(Update update) {
        Long chatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        Long requestedUserId = Long.valueOf(update.getCallbackQuery().getFrom().getId());
        TelegramBotUserEntity requestedTelegramUser = telegramUserService.getTelegramUser(requestedUserId);
        boolean isRequestedUserAdmin = requestedTelegramUser.isAdmin();

        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        String restricted = "";
        CallBackData callBackData = UserContextHolder.currentContext().getCallBackData();
        boolean isFromProfile = null != callBackData.getL();
        Integer offset = callBackData.getO();
        Integer limit = callBackData.getL();

        Long telegramUserId = Long.valueOf(update.getCallbackQuery().getFrom().getId());
        Long fileId = callBackData.getFileId();
        Optional<TelegramMediaStatisticEntity> telegramMediaStatisticOptionalEntity = telegramMediaService.findLikeUserTouch(telegramUserId, fileId);
        if (telegramMediaStatisticOptionalEntity.isPresent()) {
            logger.info("Presents {} ", telegramMediaStatisticOptionalEntity.get());
            restricted = " \uD83D\uDEAB";
        } else {
            telegramMediaService.telegramUserMediaTouchAdd(telegramUserId, fileId, "LIKE");
            logger.info("Touch added telegramUserId: {} fileId: {} TouchType: {}", telegramUserId, fileId, "LIKE");
        }

        TelegramUserMediaEntity telegramUserMediaEntity = telegramMediaService.telegramUserMediaGet(fileId);
        boolean isAnon = telegramUserMediaEntity.isAnon();

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();

        List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsCommentRow = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsDeleteRow = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsProfileDataRow = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsArrowsRow = new ArrayList<>();

        long hearCount = telegramMediaService.countUserTouch(fileId, "HEART");
        long likeCount = telegramMediaService.countUserTouch(fileId, "LIKE");
        long dislikeCount = telegramMediaService.countUserTouch(fileId, "DISLIKE");
        long discussCount = databaseService.getCommentsForMedia(fileId, 0, 10000).size();

        InlineKeyboardButton heartInlineButton = new InlineKeyboardButton();
        heartInlineButton.setText("❤️ " + hearCount);
        CallBackData heartCallBackData = new CallBackData("heart", telegramUserId, fileId);
        if (isFromProfile){
            heartCallBackData.setO(offset);
            heartCallBackData.setL(limit);
        }
        heartInlineButton.setCallbackData(gson.toJson(heartCallBackData));

        InlineKeyboardButton likeInlineButton = new InlineKeyboardButton();
        likeInlineButton.setText("\uD83D\uDC4D " + likeCount + (restricted.equals("") ? "" : restricted));
        CallBackData likeCallBackData = new CallBackData("like", telegramUserId, fileId);
        if (isFromProfile){
            likeCallBackData.setO(offset);
            likeCallBackData.setL(limit);
        }
        likeInlineButton.setCallbackData(gson.toJson(likeCallBackData));

        InlineKeyboardButton dislikeInlineButton = new InlineKeyboardButton();
        dislikeInlineButton.setText("\uD83D\uDC4E️️ " + dislikeCount);
        CallBackData dislikeCallBackData = new CallBackData("dislike", telegramUserId, fileId);
        if (isFromProfile){
            dislikeCallBackData.setO(offset);
            dislikeCallBackData.setL(limit);
        }
        dislikeInlineButton.setCallbackData(gson.toJson(dislikeCallBackData));

        inlineKeyboardButtonsFirstRow.add(heartInlineButton);
        inlineKeyboardButtonsFirstRow.add(likeInlineButton);
        inlineKeyboardButtonsFirstRow.add(dislikeInlineButton);
        inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);
        if (isFromProfile){
            Long mediaForUser = UserContextHolder.currentContext().getForUserId();
            InlineKeyboardButton leftArrowButton = new InlineKeyboardButton();
            leftArrowButton.setText("◀️");
            CallBackData leftButtonData = new CallBackData("prfl", mediaForUser, fileId);
            leftButtonData.setOffset(offset);
            leftArrowButton.setCallbackData(gson.toJson(leftButtonData));

            InlineKeyboardButton rightArrowButton = new InlineKeyboardButton();
            rightArrowButton.setText("▶️️");
            CallBackData rightButtonData = new CallBackData("prfl", mediaForUser, fileId);
            rightButtonData.setOffset(limit);
            rightArrowButton.setCallbackData(gson.toJson(rightButtonData));

            inlineKeyboardButtonsArrowsRow.add(leftArrowButton);
            inlineKeyboardButtonsArrowsRow.add(rightArrowButton);
            inlineKeyboardButtons.add(inlineKeyboardButtonsArrowsRow);
        }
        InlineKeyboardButton commentInlineButton = new InlineKeyboardButton();
        commentInlineButton.setText("\uD83D\uDCDD Обсудить (\uD83D\uDCE8" + discussCount + ")") ;
        commentInlineButton.setCallbackData(gson.toJson(new CallBackData("comment", telegramUserId, fileId)));
        inlineKeyboardButtonsCommentRow.add(commentInlineButton);
        inlineKeyboardButtons.add(inlineKeyboardButtonsCommentRow);

        if (!isAnon && !isFromProfile){
            InlineKeyboardButton profileInfoInlineButton = new InlineKeyboardButton();
            profileInfoInlineButton.setText("\uD83D\uDCF2 Посты пользователя") ;
            profileInfoInlineButton.setCallbackData(gson.toJson(new CallBackData("more", telegramUserId, fileId)));
            inlineKeyboardButtonsProfileDataRow.add(profileInfoInlineButton);
            inlineKeyboardButtons.add(inlineKeyboardButtonsProfileDataRow);
        }

        if (isRequestedUserAdmin){
            InlineKeyboardButton deleteInlineButton = new InlineKeyboardButton();
            deleteInlineButton.setText("❌ Удалить");
            deleteInlineButton.setCallbackData(gson.toJson(new CallBackData("delete", telegramUserId, fileId)));
            inlineKeyboardButtonsDeleteRow.add(deleteInlineButton);
            inlineKeyboardButtons.add(inlineKeyboardButtonsDeleteRow);
        }

        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);

        EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
        editMessageReplyMarkup.setChatId(chatId);
        editMessageReplyMarkup.setMessageId(messageId);
        editMessageReplyMarkup.setReplyMarkup(inlineKeyboardMarkup);
        logger.info("User id {} heart updated to media message {} ", telegramUserMediaEntity.getTelegramBotUserEntity().getId(), telegramUserMediaEntity);
        return editMessageReplyMarkup;
    }

    @BotRequestMapping(value = "tiktok-dislike")
    public EditMessageReplyMarkup dislikeTouch(Update update) {
        Long chatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        Long requestedUserId = Long.valueOf(update.getCallbackQuery().getFrom().getId());
        TelegramBotUserEntity requestedTelegramUser = telegramUserService.getTelegramUser(requestedUserId);
        boolean isRequestedUserAdmin = requestedTelegramUser.isAdmin();

        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        String restricted = "";
        CallBackData callBackData = UserContextHolder.currentContext().getCallBackData();
        boolean isFromProfile = null != callBackData.getL();
        Integer offset = callBackData.getO();
        Integer limit = callBackData.getL();

        Long telegramUserId = Long.valueOf(update.getCallbackQuery().getFrom().getId());
        Long fileId = callBackData.getFileId();
        Optional<TelegramMediaStatisticEntity> telegramMediaStatisticOptionalEntity = telegramMediaService.findDislikeUserTouch(telegramUserId, fileId);
        if (telegramMediaStatisticOptionalEntity.isPresent()) {
            logger.info("Presents {} ", telegramMediaStatisticOptionalEntity.get());
            restricted = " \uD83D\uDEAB";
        } else {
            telegramMediaService.telegramUserMediaTouchAdd(telegramUserId, fileId, "DISLIKE");
            logger.info("Touch added telegramUserId: {} fileId: {} TouchType: {}", telegramUserId, fileId, "DISLIKE");
        }

        TelegramUserMediaEntity telegramUserMediaEntity = telegramMediaService.telegramUserMediaGet(fileId);
        boolean isAnon = telegramUserMediaEntity.isAnon();

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();

        List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsCommentRow = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsDeleteRow = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsProfileDataRow = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsArrowsRow = new ArrayList<>();

        long hearCount = telegramMediaService.countUserTouch(fileId, "HEART");
        long likeCount = telegramMediaService.countUserTouch(fileId, "LIKE");
        long dislikeCount = telegramMediaService.countUserTouch(fileId, "DISLIKE");
        long discussCount = databaseService.getCommentsForMedia(fileId, 0, 10000).size();

        InlineKeyboardButton heartInlineButton = new InlineKeyboardButton();
        heartInlineButton.setText("❤️ " + hearCount);
        CallBackData heartCallBackData = new CallBackData("heart", telegramUserId, fileId);
        if (isFromProfile){
            heartCallBackData.setO(offset);
            heartCallBackData.setL(limit);
        }
        heartInlineButton.setCallbackData(gson.toJson(heartCallBackData));

        InlineKeyboardButton likeInlineButton = new InlineKeyboardButton();
        likeInlineButton.setText("\uD83D\uDC4D " + likeCount);
        CallBackData likeCallBackData = new CallBackData("like", telegramUserId, fileId);
        if (isFromProfile){
            likeCallBackData.setO(offset);
            likeCallBackData.setL(limit);
        }
        likeInlineButton.setCallbackData(gson.toJson(likeCallBackData));

        InlineKeyboardButton dislikeInlineButton = new InlineKeyboardButton();
        dislikeInlineButton.setText("\uD83D\uDC4E " + dislikeCount + (restricted.equals("") ? "" : restricted));
        CallBackData dislikeCallBackData = new CallBackData("dislike", telegramUserId, fileId);
        if (isFromProfile){
            dislikeCallBackData.setO(offset);
            dislikeCallBackData.setL(limit);
        }
        dislikeInlineButton.setCallbackData(gson.toJson(dislikeCallBackData));

        inlineKeyboardButtonsFirstRow.add(heartInlineButton);
        inlineKeyboardButtonsFirstRow.add(likeInlineButton);
        inlineKeyboardButtonsFirstRow.add(dislikeInlineButton);
        inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);

        if (isFromProfile){
            Long mediaForUser = UserContextHolder.currentContext().getForUserId();
            InlineKeyboardButton leftArrowButton = new InlineKeyboardButton();
            leftArrowButton.setText("◀️");
            CallBackData leftButtonData = new CallBackData("prfl", mediaForUser, fileId);
            leftButtonData.setOffset(offset);
            leftArrowButton.setCallbackData(gson.toJson(leftButtonData));

            InlineKeyboardButton rightArrowButton = new InlineKeyboardButton();
            rightArrowButton.setText("▶️️");
            CallBackData rightButtonData = new CallBackData("prfl", mediaForUser, fileId);
            rightButtonData.setOffset(limit);
            rightArrowButton.setCallbackData(gson.toJson(rightButtonData));

            inlineKeyboardButtonsArrowsRow.add(leftArrowButton);
            inlineKeyboardButtonsArrowsRow.add(rightArrowButton);
            inlineKeyboardButtons.add(inlineKeyboardButtonsArrowsRow);
        }

        InlineKeyboardButton commentInlineButton = new InlineKeyboardButton();
        commentInlineButton.setText("\uD83D\uDCDD Обсудить (\uD83D\uDCE8" + discussCount + ")") ;
        commentInlineButton.setCallbackData(gson.toJson(new CallBackData("comment", telegramUserId, fileId)));
        inlineKeyboardButtonsCommentRow.add(commentInlineButton);
        inlineKeyboardButtons.add(inlineKeyboardButtonsCommentRow);

        if (!isAnon && !isFromProfile){
            InlineKeyboardButton profileInfoInlineButton = new InlineKeyboardButton();
            profileInfoInlineButton.setText("\uD83D\uDCF2 Посты пользователя") ;
            profileInfoInlineButton.setCallbackData(gson.toJson(new CallBackData("more", telegramUserId, fileId)));
            inlineKeyboardButtonsProfileDataRow.add(profileInfoInlineButton);
            inlineKeyboardButtons.add(inlineKeyboardButtonsProfileDataRow);
        }

        if (isRequestedUserAdmin){
            InlineKeyboardButton deleteInlineButton = new InlineKeyboardButton();
            deleteInlineButton.setText("❌ Удалить");
            deleteInlineButton.setCallbackData(gson.toJson(new CallBackData("delete", telegramUserId, fileId)));
            inlineKeyboardButtonsDeleteRow.add(deleteInlineButton);
            inlineKeyboardButtons.add(inlineKeyboardButtonsDeleteRow);
        }

        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);

        EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
        editMessageReplyMarkup.setChatId(chatId);
        editMessageReplyMarkup.setMessageId(messageId);
        editMessageReplyMarkup.setReplyMarkup(inlineKeyboardMarkup);
        logger.info("User id {} heart updated to media message {} ", telegramUserMediaEntity.getTelegramBotUserEntity().getId(), telegramUserMediaEntity);
        return editMessageReplyMarkup;
    }


}
