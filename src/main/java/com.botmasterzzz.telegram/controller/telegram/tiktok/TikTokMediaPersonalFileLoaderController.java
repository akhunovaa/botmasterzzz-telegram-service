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
import com.botmasterzzz.telegram.entity.MediaCommentsDataEntity;
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
public class TikTokMediaPersonalFileLoaderController {

    private static final Logger logger = LoggerFactory.getLogger(TikTokMediaPersonalFileLoaderController.class);

    private final TelegramMediaService telegramMediaService;

    private final DatabaseService databaseService;

    private final TelegramUserService telegramUserService;

    private final Gson gson;

    public TikTokMediaPersonalFileLoaderController(TelegramMediaService telegramMediaService, DatabaseService databaseService, TelegramUserService telegramUserService, Gson gson) {
        this.telegramMediaService = telegramMediaService;
        this.databaseService = databaseService;
        this.telegramUserService = telegramUserService;
        this.gson = gson;
    }

    @BotRequestMapping(value = "tiktok-\uD83C\uDF81Мои медиа")
    public PartialBotApiMethod getPersonalMedia(Update update) {
        Long chatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        Long requestedUserId = Long.valueOf(update.getMessage().getFrom().getId());
        TelegramBotUserEntity requestedTelegramUser = telegramUserService.getTelegramUser(requestedUserId);
        String name = null != requestedTelegramUser.getUsername() ? requestedTelegramUser.getUsername() : requestedTelegramUser.getFirstName();

        List<TelegramUserMediaEntity> telegramUserMediaEntityList = telegramMediaService.telegramUserPersonalMediaList(requestedTelegramUser);
        TelegramUserMediaEntity telegramUserMediaEntity;

        if (!telegramUserMediaEntityList.isEmpty()) {
            telegramUserMediaEntity = telegramUserMediaEntityList.get(0);
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();

            List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = new ArrayList<>();
            List<InlineKeyboardButton> inlineKeyboardButtonsCommentRow = new ArrayList<>();
            List<InlineKeyboardButton> inlineKeyboardButtonsDeleteRow = new ArrayList<>();

            Long telegramUserId = requestedTelegramUser.getTelegramId();
            Long fileId = telegramUserMediaEntity.getId();
            long hearCount = telegramMediaService.countUserTouch(fileId, "HEART");
            long likeCount = telegramMediaService.countUserTouch(fileId, "LIKE");
            long dislikeCount = telegramMediaService.countUserTouch(fileId, "DISLIKE");
            long countOfMediaLogged = databaseService.getCountOfLoggedToMedia(fileId);
            long countOfMediaDistinctLogged = databaseService.getCountOfDistinctLoggedToMedia(fileId);
            long discussCount = databaseService.getCountOfDiscuss(fileId);
            int offset = 0;

            InlineKeyboardButton leftArrowButton = new InlineKeyboardButton();
            leftArrowButton.setText("◀️");
            CallBackData leftButtonData = new CallBackData("next", telegramUserId, fileId);
            leftButtonData.setOffset(telegramUserMediaEntityList.size() - 1);
            leftArrowButton.setCallbackData(gson.toJson(leftButtonData));

            InlineKeyboardButton rightArrowButton = new InlineKeyboardButton();
            rightArrowButton.setText("▶️️");
            CallBackData rightButtonData = new CallBackData("next", telegramUserId, fileId);
            int lim = telegramUserMediaEntityList.size() < 1 ? offset : offset + 1;
            rightButtonData.setOffset(lim);
            rightArrowButton.setCallbackData(gson.toJson(rightButtonData));
            inlineKeyboardButtonsFirstRow.add(leftArrowButton);
            inlineKeyboardButtonsFirstRow.add(rightArrowButton);
            inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);

            if (discussCount > 0){
                InlineKeyboardButton commentInlineButton = new InlineKeyboardButton();
                commentInlineButton.setText("\uD83D\uDCDD Посмотреть обсуждения");
                commentInlineButton.setCallbackData(gson.toJson(new CallBackData("comment", telegramUserId, fileId)));
                inlineKeyboardButtonsCommentRow.add(commentInlineButton);
                inlineKeyboardButtons.add(inlineKeyboardButtonsCommentRow);
            }

            InlineKeyboardButton deleteInlineButton = new InlineKeyboardButton();
            deleteInlineButton.setText("❌ Удалить");
            deleteInlineButton.setCallbackData(gson.toJson(new CallBackData("delete", telegramUserId, fileId)));
            inlineKeyboardButtonsDeleteRow.add(deleteInlineButton);
            inlineKeyboardButtons.add(inlineKeyboardButtonsDeleteRow);
            String mediaTimestamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(telegramUserMediaEntity.getAudWhenCreate().getTime() + TimeUnit.HOURS.toMillis(3));
            String mediaCaption = null != telegramUserMediaEntity.getMessage() ? telegramUserMediaEntity.getMessage() : "&lt;отсутствует&gt;";
            StringBuilder captionBuilder = new StringBuilder();
            captionBuilder.append("<b>Описание</b>\n");
            captionBuilder.append(mediaCaption).append("\n");
            captionBuilder.append("\n");
            captionBuilder.append("<b>Статистика</b> \n");
            captionBuilder.append("❤️  ").append(hearCount).append(" ");
            captionBuilder.append("\uD83D\uDC4D️  ").append(likeCount).append(" ");
            captionBuilder.append("\uD83D\uDC4E️  ").append(dislikeCount).append("\n");
            captionBuilder.append("\n");
            captionBuilder.append("<b>Кол-во просмотров</b> \uD83D\uDC41\n");
            captionBuilder.append("Уникальных - ").append(countOfMediaDistinctLogged).append("\n");
            captionBuilder.append("Всего - ").append(countOfMediaLogged).append("\n");
            captionBuilder.append("\n");
            captionBuilder.append("<b>Время</b> ⌚\n");
            captionBuilder.append("Загружено в ").append(mediaTimestamp).append("\n");
            captionBuilder.append("\n");
            captionBuilder.append("<b>Обсуждения</b> \uD83D\uDCDD\n");
            captionBuilder.append("Прокомментировали ").append(discussCount).append(" раз(а)").append("\n");
            captionBuilder.append("\n");

            inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);
            String fileIdx = telegramUserMediaEntity.getFileId();
            if (telegramUserMediaEntity.getFileType() == 1) {
                SendPhoto sendPhoto = new SendPhoto();
                sendPhoto.setChatId(chatId);
                sendPhoto.setPhoto(fileIdx);
                sendPhoto.setCaption(captionBuilder.toString());
                sendPhoto.setReplyMarkup(inlineKeyboardMarkup);
                sendPhoto.setParseMode("HTML");
                logger.info("User: {}", requestedTelegramUser);
                logger.info("User id {} sent media message {} ", telegramUserMediaEntity.getTelegramBotUserEntity().getId(), telegramUserMediaEntity);
                return sendPhoto;
            } else if (telegramUserMediaEntity.getHeight() > 0 && telegramUserMediaEntity.getWidth() > 0) {
                SendVideo sendVideo = new SendVideo();
                sendVideo.setChatId(chatId);
                sendVideo.setVideo(fileIdx);
                sendVideo.setCaption(captionBuilder.toString());
                sendVideo.setReplyMarkup(inlineKeyboardMarkup);
                sendVideo.setParseMode("HTML");
                logger.info("User id {} sent media message {} ", telegramUserMediaEntity.getTelegramBotUserEntity().getId(), telegramUserMediaEntity);
                return sendVideo;
            } else {
                SendDocument sendDocument = new SendDocument();
                sendDocument.setChatId(chatId);
                sendDocument.setDocument(fileIdx);
                sendDocument.setCaption(captionBuilder.toString());
                sendDocument.setReplyMarkup(inlineKeyboardMarkup);
                sendDocument.setParseMode("HTML");
                logger.info("User id {} sent media message {} ", telegramUserMediaEntity.getTelegramBotUserEntity().getId(), telegramUserMediaEntity);
                return sendDocument;
            }
        } else {
            return new SendMessage()
                    .setChatId(update.getCallbackQuery().getMessage().getChatId()).enableHtml(true)
                    .setText("<b>" + name + "</b>, у Вас нет загруженных медиа-файлов.\n");
        }
    }

    @BotRequestMapping(value = "tiktok-next")
    public PartialBotApiMethod getPersonalMediaNext(Update update) {
        Long chatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        Long requestedUserId = Long.valueOf(update.getCallbackQuery().getFrom().getId());
        TelegramBotUserEntity requestedTelegramUser = telegramUserService.getTelegramUser(requestedUserId);
        String name = null != requestedTelegramUser.getUsername() ? requestedTelegramUser.getUsername() : requestedTelegramUser.getFirstName();
        CallBackData callBackData = UserContextHolder.currentContext().getCallBackData();
        int offset = callBackData.getOffset();
        List<TelegramUserMediaEntity> telegramUserMediaEntityList = telegramMediaService.telegramUserPersonalMediaList(requestedTelegramUser);
        if(telegramUserMediaEntityList.isEmpty()){
            offset -= 1;
        }
        offset = offset >= telegramUserMediaEntityList.size() ? telegramUserMediaEntityList.size() - 1 : offset;
        TelegramUserMediaEntity telegramUserMediaEntity;

        if (!telegramUserMediaEntityList.isEmpty()) {
            telegramUserMediaEntity = telegramUserMediaEntityList.get(offset);
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();

            List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = new ArrayList<>();
            List<InlineKeyboardButton> inlineKeyboardButtonsCommentRow = new ArrayList<>();
            List<InlineKeyboardButton> inlineKeyboardButtonsDeleteRow = new ArrayList<>();

            Long telegramUserId = requestedTelegramUser.getTelegramId();
            Long fileId = telegramUserMediaEntity.getId();
            long hearCount = telegramMediaService.countUserTouch(fileId, "HEART");
            long likeCount = telegramMediaService.countUserTouch(fileId, "LIKE");
            long dislikeCount = telegramMediaService.countUserTouch(fileId, "DISLIKE");
            long countOfMediaLogged = databaseService.getCountOfLoggedToMedia(fileId);
            long countOfMediaDistinctLogged = databaseService.getCountOfDistinctLoggedToMedia(fileId);
            long discussCount = databaseService.getCountOfDiscuss(fileId);


            InlineKeyboardButton leftArrowButton = new InlineKeyboardButton();
            leftArrowButton.setText("◀️");
            CallBackData leftButtonData = new CallBackData("next", telegramUserId, fileId);
            int lim = offset <= 0 ? telegramUserMediaEntityList.size() - 1 : offset - 1;
            leftButtonData.setOffset(lim);
            leftArrowButton.setCallbackData(gson.toJson(leftButtonData));

            InlineKeyboardButton rightArrowButton = new InlineKeyboardButton();
            rightArrowButton.setText("▶️️");
            CallBackData rightButtonData = new CallBackData("next", telegramUserId, fileId);
            lim = offset >= telegramUserMediaEntityList.size() - 1 ? 0 : offset + 1;
            rightButtonData.setOffset(lim);
            rightArrowButton.setCallbackData(gson.toJson(rightButtonData));
            inlineKeyboardButtonsFirstRow.add(leftArrowButton);
            inlineKeyboardButtonsFirstRow.add(rightArrowButton);
            inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);

            if (discussCount > 0){
                InlineKeyboardButton commentInlineButton = new InlineKeyboardButton();
                commentInlineButton.setText("\uD83D\uDCDD Посмотреть обсуждения");
                commentInlineButton.setCallbackData(gson.toJson(new CallBackData("comment", telegramUserId, fileId)));
                inlineKeyboardButtonsCommentRow.add(commentInlineButton);
                inlineKeyboardButtons.add(inlineKeyboardButtonsCommentRow);
            }

            InlineKeyboardButton deleteInlineButton = new InlineKeyboardButton();
            deleteInlineButton.setText("❌ Удалить");
            deleteInlineButton.setCallbackData(gson.toJson(new CallBackData("delete", telegramUserId, fileId)));
            inlineKeyboardButtonsDeleteRow.add(deleteInlineButton);
            inlineKeyboardButtons.add(inlineKeyboardButtonsDeleteRow);
            String mediaTimestamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(telegramUserMediaEntity.getAudWhenCreate().getTime() + TimeUnit.HOURS.toMillis(3));
            String mediaCaption = null != telegramUserMediaEntity.getMessage() ? telegramUserMediaEntity.getMessage() : "&lt;отсутствует&gt;";
            StringBuilder captionBuilder = new StringBuilder();
            captionBuilder.append("<b>Описание</b>\n");
            captionBuilder.append(mediaCaption).append("\n");
            captionBuilder.append("\n");
            captionBuilder.append("<b>Статистика</b> \n");
            captionBuilder.append("❤️  ").append(hearCount).append(" ");
            captionBuilder.append("\uD83D\uDC4D️  ").append(likeCount).append(" ");
            captionBuilder.append("\uD83D\uDC4E️  ").append(dislikeCount).append("\n");
            captionBuilder.append("\n");
            captionBuilder.append("<b>Кол-во просмотров</b> \uD83D\uDC41\n");
            captionBuilder.append("Уникальных - ").append(countOfMediaDistinctLogged).append("\n");
            captionBuilder.append("Всего - ").append(countOfMediaLogged).append("\n");
            captionBuilder.append("\n");
            captionBuilder.append("<b>Время</b> ⌚\n");
            captionBuilder.append("Загружено в ").append(mediaTimestamp).append("\n");
            captionBuilder.append("\n");
            captionBuilder.append("<b>Обсуждения</b> \uD83D\uDCDD\n");
            captionBuilder.append("Прокомментировали ").append(discussCount).append(" раз(а)").append("\n");
            captionBuilder.append("\n");

            inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);
            String fileIdx = telegramUserMediaEntity.getFileId();
            if (telegramUserMediaEntity.getFileType() == 1) {
                SendPhoto sendPhoto = new SendPhoto();
                sendPhoto.setChatId(chatId);
                sendPhoto.setPhoto(fileIdx);
                sendPhoto.setCaption(captionBuilder.toString());
                sendPhoto.setReplyMarkup(inlineKeyboardMarkup);
                sendPhoto.setParseMode("HTML");
                sendPhoto.disableNotification();
                logger.info("User: {}", requestedTelegramUser);
                logger.info("User id {} sent media message {} ", telegramUserMediaEntity.getTelegramBotUserEntity().getId(), telegramUserMediaEntity);
                return sendPhoto;
            } else if (telegramUserMediaEntity.getHeight() > 0 && telegramUserMediaEntity.getWidth() > 0) {
                SendVideo sendVideo = new SendVideo();
                sendVideo.setChatId(chatId);
                sendVideo.setVideo(fileIdx);
                sendVideo.setCaption(captionBuilder.toString());
                sendVideo.setReplyMarkup(inlineKeyboardMarkup);
                sendVideo.setParseMode("HTML");
                sendVideo.disableNotification();
                logger.info("User id {} sent media message {} ", telegramUserMediaEntity.getTelegramBotUserEntity().getId(), telegramUserMediaEntity);
                return sendVideo;
            } else {
                SendDocument sendDocument = new SendDocument();
                sendDocument.setChatId(chatId);
                sendDocument.setDocument(fileIdx);
                sendDocument.setCaption(captionBuilder.toString());
                sendDocument.setReplyMarkup(inlineKeyboardMarkup);
                sendDocument.setParseMode("HTML");
                sendDocument.disableNotification();
                logger.info("User id {} sent media message {} ", telegramUserMediaEntity.getTelegramBotUserEntity().getId(), telegramUserMediaEntity);
                return sendDocument;
            }
        } else {
            return new SendMessage()
                    .setChatId(update.getCallbackQuery().getMessage().getChatId()).enableHtml(true)
                    .setText("<b>" + name + "</b>, у Вас нет загруженных медиа-файлов.\n");
        }
    }
}
