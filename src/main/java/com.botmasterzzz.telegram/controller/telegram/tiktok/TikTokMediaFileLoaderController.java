package com.botmasterzzz.telegram.controller.telegram.tiktok;

import com.botmasterzzz.bot.api.impl.methods.PartialBotApiMethod;
import com.botmasterzzz.bot.api.impl.methods.send.SendDocument;
import com.botmasterzzz.bot.api.impl.methods.send.SendPhoto;
import com.botmasterzzz.bot.api.impl.methods.send.SendVideo;
import com.botmasterzzz.bot.api.impl.objects.Update;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.InlineKeyboardMarkup;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.buttons.InlineKeyboardButton;
import com.botmasterzzz.telegram.config.annotations.BotController;
import com.botmasterzzz.telegram.config.annotations.BotRequestMapping;
import com.botmasterzzz.telegram.dto.CallBackData;
import com.botmasterzzz.telegram.entity.TelegramBotUserEntity;
import com.botmasterzzz.telegram.entity.TelegramUserMediaEntity;
import com.botmasterzzz.telegram.service.TelegramMediaService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@BotController
public class TikTokMediaFileLoaderController {

    private static final Logger logger = LoggerFactory.getLogger(TikTokMediaFileLoaderController.class);

    @Autowired
    private TelegramMediaService telegramMediaService;

    @Autowired
    private Gson gson;

    @BotRequestMapping(value = "tiktok-\uD83D\uDCF2Фото")
    public SendPhoto sendRandomlyMedia(Update update) {
        Long chatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        List<TelegramUserMediaEntity> telegramUserMediaEntityList = telegramMediaService.telegramUserMediaList(1);
        Random rnd = new Random();
        int randomlyChoosenIndex = rnd.nextInt(telegramUserMediaEntityList.size());
        TelegramUserMediaEntity telegramUserMediaEntity = telegramUserMediaEntityList.get(randomlyChoosenIndex);

        TelegramBotUserEntity telegramBotUserEntity = telegramUserMediaEntity.getTelegramBotUserEntity();
        String telegramUser = null != telegramBotUserEntity.getUsername() ? telegramBotUserEntity.getUsername() : telegramBotUserEntity.getFirstName();
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();

        List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = new ArrayList<>();

        Long telegramUserId = telegramBotUserEntity.getTelegramId();
        Long fileId = telegramUserMediaEntity.getId();
        long hearCount = telegramMediaService.countUserTouch(fileId, "HEART");
        long likeCount = telegramMediaService.countUserTouch(fileId, "LIKE");
        long dislikeCount = telegramMediaService.countUserTouch(fileId, "DISLIKE");

        InlineKeyboardButton heartInlineButton = new InlineKeyboardButton();
        heartInlineButton.setText("❤️ ️️" + hearCount);
        heartInlineButton.setCallbackData(gson.toJson(new CallBackData("heart", telegramUserId, fileId)));

        InlineKeyboardButton likeInlineButton = new InlineKeyboardButton();
        likeInlineButton.setText("\uD83D\uDC4D " + likeCount);
        likeInlineButton.setCallbackData(gson.toJson(new CallBackData("like", telegramUserId, fileId)));

        InlineKeyboardButton dislikeInlineButton = new InlineKeyboardButton();
        dislikeInlineButton.setText("\uD83D\uDC4E️️ " + dislikeCount);
        dislikeInlineButton.setCallbackData(gson.toJson(new CallBackData("dislike", telegramUserId, fileId)));

        inlineKeyboardButtonsFirstRow.add(heartInlineButton);
        inlineKeyboardButtonsFirstRow.add(likeInlineButton);
        inlineKeyboardButtonsFirstRow.add(dislikeInlineButton);
        inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);
        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);


        String fileIdx = telegramUserMediaEntity.getFileId();

        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(fileIdx);
        sendPhoto.setCaption("Фотография от пользователя <a href=\"tg://user?id=" + telegramBotUserEntity.getTelegramId() + "\">" + telegramUser + "</a>");
        sendPhoto.setReplyMarkup(inlineKeyboardMarkup);
        sendPhoto.setParseMode("HTML");
        sendPhoto.disableNotification();
        logger.info("User id {} sent media message {} ", telegramUserMediaEntity.getTelegramBotUserEntity().getId(), telegramUserMediaEntity);
        return sendPhoto;
    }

    @BotRequestMapping(value = "tiktok-\uD83D\uDCF2Видео")
    public PartialBotApiMethod sendRandomlyVideo(Update update) {
        Long chatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();


        List<TelegramUserMediaEntity> telegramUserMediaEntityList = telegramMediaService.telegramUserMediaList(2);
        Random rnd = new Random();
        int randomlyChoosenIndex = rnd.nextInt(telegramUserMediaEntityList.size());
        TelegramUserMediaEntity telegramUserMediaEntity = telegramUserMediaEntityList.get(randomlyChoosenIndex);

        TelegramBotUserEntity telegramBotUserEntity = telegramUserMediaEntity.getTelegramBotUserEntity();
        String telegramUser = null != telegramBotUserEntity.getUsername() ? telegramBotUserEntity.getUsername() : telegramBotUserEntity.getFirstName();
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();

        List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = new ArrayList<>();

        Long telegramUserId = telegramBotUserEntity.getTelegramId();
        Long fileId = telegramUserMediaEntity.getId();

        long hearCount = telegramMediaService.countUserTouch(fileId, "HEART");
        long likeCount = telegramMediaService.countUserTouch(fileId, "LIKE");
        long dislikeCount = telegramMediaService.countUserTouch(fileId, "DISLIKE");

        InlineKeyboardButton heartInlineButton = new InlineKeyboardButton();
        heartInlineButton.setText("❤️ ️️" + hearCount);
        heartInlineButton.setCallbackData(gson.toJson(new CallBackData("heart", telegramUserId, fileId)));

        InlineKeyboardButton likeInlineButton = new InlineKeyboardButton();
        likeInlineButton.setText("\uD83D\uDC4D " + likeCount);
        likeInlineButton.setCallbackData(gson.toJson(new CallBackData("like", telegramUserId, fileId)));

        InlineKeyboardButton dislikeInlineButton = new InlineKeyboardButton();
        dislikeInlineButton.setText("\uD83D\uDC4E️️ " + dislikeCount);
        dislikeInlineButton.setCallbackData(gson.toJson(new CallBackData("dislike", telegramUserId, fileId)));

        inlineKeyboardButtonsFirstRow.add(heartInlineButton);
        inlineKeyboardButtonsFirstRow.add(likeInlineButton);
        inlineKeyboardButtonsFirstRow.add(dislikeInlineButton);
        inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);
        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);


        String fileIdx = telegramUserMediaEntity.getFileId();
        if (telegramUserMediaEntity.getHeight() > 0 && telegramUserMediaEntity.getWidth() > 0) {
            SendVideo sendVideo = new SendVideo();
            sendVideo.setChatId(chatId);
            sendVideo.setVideo(fileIdx);
            sendVideo.setCaption("Видео от пользователя <a href=\"tg://user?id=" + telegramBotUserEntity.getTelegramId() + "\">" + telegramUser + "</a>");
            sendVideo.setReplyMarkup(inlineKeyboardMarkup);
            sendVideo.setParseMode("HTML");
            sendVideo.disableNotification();
            logger.info("User id {} sent media message {} ", telegramUserMediaEntity.getTelegramBotUserEntity().getId(), telegramUserMediaEntity);
            return sendVideo;
        }else {
            SendDocument sendDocument = new SendDocument();
            sendDocument.setChatId(chatId);
            sendDocument.setDocument(fileIdx);
            sendDocument.setCaption("Видео от пользователя <a href=\"tg://user?id=" + telegramBotUserEntity.getTelegramId() + "\">" + telegramUser + "</a>");
            sendDocument.setReplyMarkup(inlineKeyboardMarkup);
            sendDocument.setParseMode("HTML");
            sendDocument.disableNotification();
            logger.info("User id {} sent media message {} ", telegramUserMediaEntity.getTelegramBotUserEntity().getId(), telegramUserMediaEntity);
            return sendDocument;
        }
    }
}
