package com.botmasterzzz.telegram.controller.telegram.tiktok;

import com.botmasterzzz.bot.api.impl.methods.PartialBotApiMethod;
import com.botmasterzzz.bot.api.impl.methods.send.SendDocument;
import com.botmasterzzz.bot.api.impl.methods.send.SendMessage;
import com.botmasterzzz.bot.api.impl.methods.send.SendPhoto;
import com.botmasterzzz.bot.api.impl.methods.send.SendVideo;
import com.botmasterzzz.bot.api.impl.objects.Update;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.InlineKeyboardMarkup;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.buttons.InlineKeyboardButton;
import com.botmasterzzz.telegram.config.annotations.BotController;
import com.botmasterzzz.telegram.config.annotations.BotRequestMapping;
import com.botmasterzzz.telegram.config.context.UserContextHolder;
import com.botmasterzzz.telegram.dto.CallBackData;
import com.botmasterzzz.telegram.entity.TelegramBotUserEntity;
import com.botmasterzzz.telegram.entity.TelegramUserMediaEntity;
import com.botmasterzzz.telegram.service.DatabaseService;
import com.botmasterzzz.telegram.service.TelegramMediaService;
import com.botmasterzzz.telegram.service.TelegramUserService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@BotController
public class TikTokMediaProfileFileLoaderController {

    private static final Logger logger = LoggerFactory.getLogger(TikTokMediaProfileFileLoaderController.class);

    private final TelegramMediaService telegramMediaService;

    private final DatabaseService databaseService;

    private final TelegramUserService telegramUserService;

    private final Gson gson;

    public TikTokMediaProfileFileLoaderController(TelegramMediaService telegramMediaService, DatabaseService databaseService, TelegramUserService telegramUserService, Gson gson) {
        this.telegramMediaService = telegramMediaService;
        this.databaseService = databaseService;
        this.telegramUserService = telegramUserService;
        this.gson = gson;
    }

    @BotRequestMapping(value = "tiktok-more")
    public PartialBotApiMethod getProfileMedia(Update update) {
        Long chatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        Long requestedUserId = Long.valueOf(update.getCallbackQuery().getFrom().getId());
        CallBackData callBackData = UserContextHolder.currentContext().getCallBackData();
        Long mediaForUser = callBackData.getUserId();
        UserContextHolder.currentContext().setForUserId(mediaForUser);
        TelegramBotUserEntity requestedTelegramUser = telegramUserService.getTelegramUser(requestedUserId);
        boolean isRequestedUserAdmin = requestedTelegramUser.isAdmin();

        TelegramBotUserEntity mediaForTelegramUser = telegramUserService.getTelegramUser(mediaForUser);
        String telegramUser = null != mediaForTelegramUser.getUsername() ? mediaForTelegramUser.getUsername() : mediaForTelegramUser.getFirstName();

        String name = null != requestedTelegramUser.getUsername() ? requestedTelegramUser.getUsername() : requestedTelegramUser.getFirstName();

        List<TelegramUserMediaEntity> telegramUserMediaEntityList = telegramMediaService.telegramUserPersonalMediaList(mediaForTelegramUser);
        TelegramUserMediaEntity telegramUserMediaEntity;
        int idx = 0;
        if (!telegramUserMediaEntityList.isEmpty()) {
            telegramUserMediaEntity = telegramUserMediaEntityList.get(idx);
            boolean isAnon = telegramUserMediaEntity.isAnon();
            while (isAnon){
                idx++;
                telegramUserMediaEntity = telegramUserMediaEntityList.get(idx);
                isAnon = telegramUserMediaEntity.isAnon();
            }
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();

            List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = new ArrayList<>();
            List<InlineKeyboardButton> inlineKeyboardButtonsArrowsRow = new ArrayList<>();
            List<InlineKeyboardButton> inlineKeyboardButtonsCommentRow = new ArrayList<>();
            List<InlineKeyboardButton> inlineKeyboardButtonsDeleteRow = new ArrayList<>();

            Long telegramUserId = requestedTelegramUser.getTelegramId();
            Long mediaForUserId = mediaForTelegramUser.getTelegramId();

            Long fileId = telegramUserMediaEntity.getId();
            long hearCount = telegramMediaService.countUserTouch(fileId, "HEART");
            long likeCount = telegramMediaService.countUserTouch(fileId, "LIKE");
            long dislikeCount = telegramMediaService.countUserTouch(fileId, "DISLIKE");
            long countOfMediaLogged = databaseService.getCountOfLoggedToMedia(fileId);
            //long countOfMediaDistinctLogged = databaseService.getCountOfDistinctLoggedToMedia(fileId);
            long discussCount = databaseService.getCountOfDiscuss(fileId);
            int offset = idx;

            InlineKeyboardButton heartInlineButton = new InlineKeyboardButton();
            heartInlineButton.setText("❤️ " + hearCount);
            CallBackData heartCallBackData = new CallBackData("heart", telegramUserId, fileId);
            heartCallBackData.setO(offset);
            heartCallBackData.setL(telegramUserMediaEntityList.size() < 1 ? offset : offset + 1);
            heartInlineButton.setCallbackData(gson.toJson(heartCallBackData));

            InlineKeyboardButton likeInlineButton = new InlineKeyboardButton();
            likeInlineButton.setText("\uD83D\uDC4D " + likeCount);
            CallBackData likeCallBackData = new CallBackData("like", telegramUserId, fileId);
            likeCallBackData.setO(offset);
            likeCallBackData.setL(telegramUserMediaEntityList.size() < 1 ? offset : offset + 1);
            likeInlineButton.setCallbackData(gson.toJson(likeCallBackData));

            InlineKeyboardButton dislikeInlineButton = new InlineKeyboardButton();
            dislikeInlineButton.setText("\uD83D\uDC4E️️ " + dislikeCount);
            CallBackData dislikeCallBackData = new CallBackData("dislike", telegramUserId, fileId);
            dislikeCallBackData.setO(offset);
            dislikeCallBackData.setL(telegramUserMediaEntityList.size() < 1 ? offset : offset + 1);
            dislikeInlineButton.setCallbackData(gson.toJson(dislikeCallBackData));

            inlineKeyboardButtonsFirstRow.add(heartInlineButton);
            inlineKeyboardButtonsFirstRow.add(likeInlineButton);
            inlineKeyboardButtonsFirstRow.add(dislikeInlineButton);
            inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);

            InlineKeyboardButton leftArrowButton = new InlineKeyboardButton();
            leftArrowButton.setText("◀️");
            CallBackData leftButtonData = new CallBackData("prfl", mediaForUserId, fileId);
            leftButtonData.setO(telegramUserMediaEntityList.size() - 1);
            leftArrowButton.setCallbackData(gson.toJson(leftButtonData));

            InlineKeyboardButton rightArrowButton = new InlineKeyboardButton();
            rightArrowButton.setText("▶️️");
            CallBackData rightButtonData = new CallBackData("prfl", mediaForUserId, fileId);
            int lim = telegramUserMediaEntityList.size() < 1 ? offset : offset + 1;
            rightButtonData.setO(lim);
            rightArrowButton.setCallbackData(gson.toJson(rightButtonData));
            inlineKeyboardButtonsArrowsRow.add(leftArrowButton);
            inlineKeyboardButtonsArrowsRow.add(rightArrowButton);
            inlineKeyboardButtons.add(inlineKeyboardButtonsArrowsRow);

            InlineKeyboardButton commentInlineButton = new InlineKeyboardButton();
            commentInlineButton.setText("\uD83D\uDCDD Обсудить (\uD83D\uDCE8" + discussCount + ")") ;
            commentInlineButton.setCallbackData(gson.toJson(new CallBackData("comment", telegramUserId, fileId)));
            inlineKeyboardButtonsCommentRow.add(commentInlineButton);
            inlineKeyboardButtons.add(inlineKeyboardButtonsCommentRow);

            if (isRequestedUserAdmin){
                InlineKeyboardButton deleteInlineButton = new InlineKeyboardButton();
                deleteInlineButton.setText("❌ Удалить");
                deleteInlineButton.setCallbackData(gson.toJson(new CallBackData("delete", telegramUserId, fileId)));
                inlineKeyboardButtonsDeleteRow.add(deleteInlineButton);
                inlineKeyboardButtons.add(inlineKeyboardButtonsDeleteRow);
            }

            String mediaTimestamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(telegramUserMediaEntity.getAudWhenCreate().getTime() + TimeUnit.HOURS.toMillis(3));
            String mediaCaption = null != telegramUserMediaEntity.getMessage() ? telegramUserMediaEntity.getMessage() : "&lt;описание отсутствует&gt;";

            inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);
            String fileIdx = telegramUserMediaEntity.getFileId();
            if (telegramUserMediaEntity.getFileType() == 1) {
                SendPhoto sendPhoto = new SendPhoto();
                sendPhoto.setChatId(chatId);
                sendPhoto.setPhoto(fileIdx);
                sendPhoto.setCaption(mediaCaption + "\n\n\uD83D\uDC41 " + countOfMediaLogged + "\nФотография от пользователя <a href=\"tg://user?id=" + mediaForTelegramUser.getTelegramId() + "\">" + telegramUser + "</a> ⌚<i>" + mediaTimestamp + "</i>");
                sendPhoto.setReplyMarkup(inlineKeyboardMarkup);
                sendPhoto.setParseMode("HTML");
                logger.info("User: {}", requestedTelegramUser);
                logger.info("User id {} sent media message {} ", telegramUserMediaEntity.getTelegramBotUserEntity().getId(), telegramUserMediaEntity);
                return sendPhoto;
            } else if (telegramUserMediaEntity.getHeight() > 0 && telegramUserMediaEntity.getWidth() > 0) {
                SendVideo sendVideo = new SendVideo();
                sendVideo.setChatId(chatId);
                sendVideo.setVideo(fileIdx);
                sendVideo.setCaption(mediaCaption + "\n\n\uD83D\uDC41 " + countOfMediaLogged + "\nВидео от пользователя <a href=\"tg://user?id=" + mediaForTelegramUser.getTelegramId() + "\">" + telegramUser + "</a> ⌚<i>" + mediaTimestamp + "</i>");
                sendVideo.setReplyMarkup(inlineKeyboardMarkup);
                sendVideo.setParseMode("HTML");
                logger.info("User id {} sent media message {} ", telegramUserMediaEntity.getTelegramBotUserEntity().getId(), telegramUserMediaEntity);
                return sendVideo;
            } else {
                SendDocument sendDocument = new SendDocument();
                sendDocument.setChatId(chatId);
                sendDocument.setDocument(fileIdx);
                sendDocument.setCaption(mediaCaption + "\n\n\uD83D\uDC41 " + countOfMediaLogged + "\nВидео от пользователя <a href=\"tg://user?id=" + mediaForTelegramUser.getTelegramId() + "\">" + telegramUser + "</a> ⌚<i>" + mediaTimestamp + "</i>");
                sendDocument.setReplyMarkup(inlineKeyboardMarkup);
                sendDocument.setParseMode("HTML");
                logger.info("User id {} sent media message {} ", telegramUserMediaEntity.getTelegramBotUserEntity().getId(), telegramUserMediaEntity);
                return sendDocument;
            }
        } else {
            return new SendMessage()
                    .setChatId(update.getCallbackQuery().getMessage().getChatId()).enableHtml(true)
                    .setText("<b>" + name + "</b>, у пользователя нет загруженных медиа-файлов.\n");
        }
    }

    @BotRequestMapping(value = "tiktok-prfl")
    public PartialBotApiMethod getPersonalMediaNext(Update update) {
        Long chatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        Long requestedUserId = Long.valueOf(update.getCallbackQuery().getFrom().getId());
        CallBackData callBackData = UserContextHolder.currentContext().getCallBackData();
        Long mediaForUser =  UserContextHolder.currentContext().getForUserId();
        int offset = null != callBackData.getOffset() ? callBackData.getOffset() : callBackData.getO();

        TelegramBotUserEntity requestedTelegramUser = telegramUserService.getTelegramUser(requestedUserId);
        boolean isRequestedUserAdmin = requestedTelegramUser.isAdmin();

        TelegramBotUserEntity mediaForTelegramUser = telegramUserService.getTelegramUser(mediaForUser);
        String telegramUser = null != mediaForTelegramUser.getUsername() ? mediaForTelegramUser.getUsername() : mediaForTelegramUser.getFirstName();

        String name = null != requestedTelegramUser.getUsername() ? requestedTelegramUser.getUsername() : requestedTelegramUser.getFirstName();

        List<TelegramUserMediaEntity> telegramUserMediaEntityList = telegramMediaService.telegramUserPersonalMediaList(mediaForTelegramUser);
        TelegramUserMediaEntity telegramUserMediaEntity;
        if(telegramUserMediaEntityList.isEmpty()){
            offset -= 1;
        }
        offset = offset >= telegramUserMediaEntityList.size() ? telegramUserMediaEntityList.size() - 1 : offset;

        if (!telegramUserMediaEntityList.isEmpty()) {
            telegramUserMediaEntity = telegramUserMediaEntityList.get(offset);
            boolean isAnon = telegramUserMediaEntity.isAnon();
            while (isAnon){
                offset++;
                telegramUserMediaEntity = telegramUserMediaEntityList.get(offset);
                isAnon = telegramUserMediaEntity.isAnon();
            }
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();

            List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = new ArrayList<>();
            List<InlineKeyboardButton> inlineKeyboardButtonsArrowsRow = new ArrayList<>();
            List<InlineKeyboardButton> inlineKeyboardButtonsCommentRow = new ArrayList<>();
            List<InlineKeyboardButton> inlineKeyboardButtonsDeleteRow = new ArrayList<>();

            Long telegramUserId = requestedTelegramUser.getTelegramId();
            Long mediaForTelegramUserId = mediaForTelegramUser.getTelegramId();
            Long fileId = telegramUserMediaEntity.getId();
            long hearCount = telegramMediaService.countUserTouch(fileId, "HEART");
            long likeCount = telegramMediaService.countUserTouch(fileId, "LIKE");
            long dislikeCount = telegramMediaService.countUserTouch(fileId, "DISLIKE");
            long countOfMediaLogged = databaseService.getCountOfLoggedToMedia(fileId);
            //long countOfMediaDistinctLogged = databaseService.getCountOfDistinctLoggedToMedia(fileId);
            long discussCount = databaseService.getCountOfDiscuss(fileId);

            InlineKeyboardButton heartInlineButton = new InlineKeyboardButton();
            heartInlineButton.setText("❤️ " + hearCount);
            CallBackData heartCallBackData = new CallBackData("heart", telegramUserId, fileId);
            heartCallBackData.setO(offset);
            heartCallBackData.setL(offset <= 0 ? telegramUserMediaEntityList.size() - 1 : offset - 1);
            heartInlineButton.setCallbackData(gson.toJson(heartCallBackData));

            InlineKeyboardButton likeInlineButton = new InlineKeyboardButton();
            likeInlineButton.setText("\uD83D\uDC4D " + likeCount);
            CallBackData likeCallBackData = new CallBackData("like", telegramUserId, fileId);
            likeCallBackData.setO(offset);
            likeCallBackData.setL(offset <= 0 ? telegramUserMediaEntityList.size() - 1 : offset - 1);
            likeInlineButton.setCallbackData(gson.toJson(likeCallBackData));

            InlineKeyboardButton dislikeInlineButton = new InlineKeyboardButton();
            dislikeInlineButton.setText("\uD83D\uDC4E️️ " + dislikeCount);
            CallBackData dislikeCallBackData = new CallBackData("dislike", telegramUserId, fileId);
            dislikeCallBackData.setO(offset);
            dislikeCallBackData.setL(offset <= 0 ? telegramUserMediaEntityList.size() - 1 : offset - 1);
            dislikeInlineButton.setCallbackData(gson.toJson(dislikeCallBackData));

            inlineKeyboardButtonsFirstRow.add(heartInlineButton);
            inlineKeyboardButtonsFirstRow.add(likeInlineButton);
            inlineKeyboardButtonsFirstRow.add(dislikeInlineButton);
            inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);

            InlineKeyboardButton leftArrowButton = new InlineKeyboardButton();
            leftArrowButton.setText("◀️");
            CallBackData leftButtonData = new CallBackData("prfl", mediaForTelegramUserId, fileId);
            int lim = offset <= 0 ? telegramUserMediaEntityList.size() - 1 : offset - 1;
            leftButtonData.setO(lim);
            leftButtonData.setL(lim);
            leftArrowButton.setCallbackData(gson.toJson(leftButtonData));

            InlineKeyboardButton rightArrowButton = new InlineKeyboardButton();
            rightArrowButton.setText("▶️️");
            CallBackData rightButtonData = new CallBackData("prfl", mediaForTelegramUserId, fileId);
            lim = offset >= telegramUserMediaEntityList.size() - 1 ? 0 : offset + 1;
            rightButtonData.setO(lim);
            rightArrowButton.setCallbackData(gson.toJson(rightButtonData));
            inlineKeyboardButtonsArrowsRow.add(leftArrowButton);
            inlineKeyboardButtonsArrowsRow.add(rightArrowButton);
            inlineKeyboardButtons.add(inlineKeyboardButtonsArrowsRow);

            InlineKeyboardButton commentInlineButton = new InlineKeyboardButton();
            commentInlineButton.setText("\uD83D\uDCDD Обсудить (\uD83D\uDCE8" + discussCount + ")") ;
            commentInlineButton.setCallbackData(gson.toJson(new CallBackData("comment", telegramUserId, fileId)));
            inlineKeyboardButtonsCommentRow.add(commentInlineButton);
            inlineKeyboardButtons.add(inlineKeyboardButtonsCommentRow);

            if (isRequestedUserAdmin){
                InlineKeyboardButton deleteInlineButton = new InlineKeyboardButton();
                deleteInlineButton.setText("❌ Удалить");
                deleteInlineButton.setCallbackData(gson.toJson(new CallBackData("delete", telegramUserId, fileId)));
                inlineKeyboardButtonsDeleteRow.add(deleteInlineButton);
                inlineKeyboardButtons.add(inlineKeyboardButtonsDeleteRow);
            }

            String mediaTimestamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(telegramUserMediaEntity.getAudWhenCreate().getTime() + TimeUnit.HOURS.toMillis(3));
            String mediaCaption = null != telegramUserMediaEntity.getMessage() ? telegramUserMediaEntity.getMessage() : "&lt;описание отсутствует&gt;";

            inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);
            String fileIdx = telegramUserMediaEntity.getFileId();
            if (telegramUserMediaEntity.getFileType() == 1) {
                SendPhoto sendPhoto = new SendPhoto();
                sendPhoto.setChatId(chatId);
                sendPhoto.setPhoto(fileIdx);
                sendPhoto.setCaption(mediaCaption + "\n\n\uD83D\uDC41 " + countOfMediaLogged + "\nФотография от пользователя <a href=\"tg://user?id=" + mediaForTelegramUser.getTelegramId() + "\">" + telegramUser + "</a> ⌚<i>" + mediaTimestamp + "</i>");
                sendPhoto.setReplyMarkup(inlineKeyboardMarkup);
                sendPhoto.setParseMode("HTML");
                logger.info("User: {}", requestedTelegramUser);
                logger.info("User id {} sent media message {} ", telegramUserMediaEntity.getTelegramBotUserEntity().getId(), telegramUserMediaEntity);
                return sendPhoto;
            } else if (telegramUserMediaEntity.getHeight() > 0 && telegramUserMediaEntity.getWidth() > 0) {
                SendVideo sendVideo = new SendVideo();
                sendVideo.setChatId(chatId);
                sendVideo.setVideo(fileIdx);
                sendVideo.setCaption(mediaCaption + "\n\n\uD83D\uDC41 " + countOfMediaLogged + "\nВидео от пользователя <a href=\"tg://user?id=" + mediaForTelegramUser.getTelegramId() + "\">" + telegramUser + "</a> ⌚<i>" + mediaTimestamp + "</i>");
                sendVideo.setReplyMarkup(inlineKeyboardMarkup);
                sendVideo.setParseMode("HTML");
                logger.info("User id {} sent media message {} ", telegramUserMediaEntity.getTelegramBotUserEntity().getId(), telegramUserMediaEntity);
                return sendVideo;
            } else {
                SendDocument sendDocument = new SendDocument();
                sendDocument.setChatId(chatId);
                sendDocument.setDocument(fileIdx);
                sendDocument.setCaption(mediaCaption + "\n\n\uD83D\uDC41 " + countOfMediaLogged + "\nВидео от пользователя <a href=\"tg://user?id=" + mediaForTelegramUser.getTelegramId() + "\">" + telegramUser + "</a> ⌚<i>" + mediaTimestamp + "</i>");
                sendDocument.setReplyMarkup(inlineKeyboardMarkup);
                sendDocument.setParseMode("HTML");
                logger.info("User id {} sent media message {} ", telegramUserMediaEntity.getTelegramBotUserEntity().getId(), telegramUserMediaEntity);
                return sendDocument;
            }
        } else {
            return new SendMessage()
                    .setChatId(update.getCallbackQuery().getMessage().getChatId()).enableHtml(true)
                    .setText("<b>" + name + "</b>, у пользователя нет загруженных медиа-файлов.\n");
        }
    }
}
