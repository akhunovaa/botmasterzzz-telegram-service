package com.botmasterzzz.telegram.controller.telegram.tiktok;

import com.botmasterzzz.bot.api.impl.methods.send.SendMessage;
import com.botmasterzzz.bot.api.impl.methods.update.EditMessageText;
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
public class TikTokMediaCommentController {

    private static final Logger logger = LoggerFactory.getLogger(TikTokMediaCommentController.class);

    private final TelegramMediaService telegramMediaService;

    private final TelegramUserService telegramUserService;

    private final DatabaseService databaseService;

    private final Gson gson;

    public TikTokMediaCommentController(TelegramMediaService telegramMediaService, Gson gson, DatabaseService databaseService, TelegramUserService telegramUserService) {
        this.telegramMediaService = telegramMediaService;
        this.gson = gson;
        this.databaseService = databaseService;
        this.telegramUserService = telegramUserService;
    }

    @BotRequestMapping(value = "tiktok-comment")
    public SendMessage comment(Update update) {
        Long chatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();

        CallBackData callBackData = UserContextHolder.currentContext().getCallBackData();
        Long telegramUserId = callBackData.getUserId();
        Long fileId = callBackData.getFileId();
        int offset = 0;
        List<MediaCommentsDataEntity> mediaCommentsDataEntityList = databaseService.getCommentsForMedia(fileId, offset, 3);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();

        List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonSecondRow = new ArrayList<>();

        InlineKeyboardButton leftArrowButton = new InlineKeyboardButton();
        leftArrowButton.setText("◀️");
        CallBackData leftButtonData = new CallBackData("arrow", telegramUserId, fileId);
        leftButtonData.setOffset(offset);
        leftArrowButton.setCallbackData(gson.toJson(leftButtonData));

        InlineKeyboardButton rightArrowButton = new InlineKeyboardButton();
        rightArrowButton.setText("▶️️");
        CallBackData rightButtonData = new CallBackData("arrow", telegramUserId, fileId);
        int lim = mediaCommentsDataEntityList.size() < 3 ? offset : offset + 3;
        rightButtonData.setOffset(lim);
        rightArrowButton.setCallbackData(gson.toJson(rightButtonData));

        InlineKeyboardButton commentAddButton = new InlineKeyboardButton();
        commentAddButton.setText("\uD83D\uDCE8 Написать");
        commentAddButton.setCallbackData(gson.toJson(new CallBackData("comment-write", telegramUserId, fileId)));


        if (!mediaCommentsDataEntityList.isEmpty()) {
            inlineKeyboardButtonsFirstRow.add(leftArrowButton);
            inlineKeyboardButtonsFirstRow.add(rightArrowButton);
            inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);
        }
        inlineKeyboardButtonSecondRow.add(commentAddButton);
        inlineKeyboardButtons.add(inlineKeyboardButtonSecondRow);
        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<b>Обсуждения:</b>\n");
        stringBuilder.append("\n");
        for (MediaCommentsDataEntity mediaCommentsDataEntity : mediaCommentsDataEntityList) {
            TelegramBotUserEntity telegramBotUserEntity = mediaCommentsDataEntity.getTelegramBotUserEntity();
            String telegramUser = null != telegramBotUserEntity.getUsername() ? telegramBotUserEntity.getUsername() : telegramBotUserEntity.getFirstName();
            String commentData = mediaCommentsDataEntity.getData();
            String commentTimestamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(mediaCommentsDataEntity.getAudWhenCreate().getTime() + TimeUnit.HOURS.toMillis(3));
            stringBuilder.append("⌚<i>").append(commentTimestamp).append("</i>  ");
            stringBuilder.append("от пользователя <a href=\"tg://user?id=").append(telegramBotUserEntity.getTelegramId()).append("\">").append(telegramUser).append("</a>:\n");
            stringBuilder.append("➖➖➖➖➖➖➖➖➖➖➖➖\n");
            stringBuilder.append("<code>").append(commentData).append("</code>\n");
            stringBuilder.append("➖➖➖➖➖➖➖➖➖➖➖➖\n\n");
            logger.info("User {} comment {}", telegramBotUserEntity, commentData);
        }
        if (mediaCommentsDataEntityList.isEmpty()) {
            stringBuilder.append("<code>").append("Комментарии и обсуждения отсутствуют.  Вы будете первыми! Не стесняйтесь в выражениях \uD83D\uDE09").append("</code>\n\n<u>(ограничение в 250 символов)</u>");
        }
        logger.info("User id {} comment message requested", telegramUserId);

        return new SendMessage()
                .setChatId(chatId).enableHtml(true)
                .setReplyMarkup(inlineKeyboardMarkup)
                .setText(stringBuilder.toString());
    }

    @BotRequestMapping(value = "tiktok-arrow")
    public EditMessageText commentOffset(Update update) {
        Long chatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();

        CallBackData callBackData = UserContextHolder.currentContext().getCallBackData();
        Long telegramUserId = callBackData.getUserId();
        Long fileId = callBackData.getFileId();
        int offset = callBackData.getOffset();
        List<MediaCommentsDataEntity> mediaCommentsDataEntityList = databaseService.getCommentsForMedia(fileId, offset, 3);
        if(mediaCommentsDataEntityList.isEmpty()){
            offset -= 3;
            mediaCommentsDataEntityList = databaseService.getCommentsForMedia(fileId, offset, 3);
        }
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();

        List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonSecondRow = new ArrayList<>();

        InlineKeyboardButton leftArrowButton = new InlineKeyboardButton();
        leftArrowButton.setText("◀️");
        CallBackData leftButtonData = new CallBackData("arrow", telegramUserId, fileId);
        leftButtonData.setOffset(offset - 3);
        leftArrowButton.setCallbackData(gson.toJson(leftButtonData));

        InlineKeyboardButton rightArrowButton = new InlineKeyboardButton();
        rightArrowButton.setText("▶️️");
        CallBackData rightButtonData = new CallBackData("arrow", telegramUserId, fileId);
        int lim = mediaCommentsDataEntityList.size() < 3 ? offset : offset + 3;
        rightButtonData.setOffset(lim);
        rightArrowButton.setCallbackData(gson.toJson(rightButtonData));

        InlineKeyboardButton commentAddButton = new InlineKeyboardButton();
        commentAddButton.setText("\uD83D\uDCE8 Написать");
        commentAddButton.setCallbackData(gson.toJson(new CallBackData("comment-write", telegramUserId, fileId)));


        if (!mediaCommentsDataEntityList.isEmpty()) {
            inlineKeyboardButtonsFirstRow.add(leftArrowButton);
            inlineKeyboardButtonsFirstRow.add(rightArrowButton);
            inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);
        }
        inlineKeyboardButtonSecondRow.add(commentAddButton);
        inlineKeyboardButtons.add(inlineKeyboardButtonSecondRow);
        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<b>Обсуждения:</b>\n");
        stringBuilder.append("\n");

        for (MediaCommentsDataEntity mediaCommentsDataEntity : mediaCommentsDataEntityList) {
            TelegramBotUserEntity telegramBotUserEntity = mediaCommentsDataEntity.getTelegramBotUserEntity();
            String telegramUser = null != telegramBotUserEntity.getUsername() ? telegramBotUserEntity.getUsername() : telegramBotUserEntity.getFirstName();
            String commentData = mediaCommentsDataEntity.getData();
            String commentTimestamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(mediaCommentsDataEntity.getAudWhenCreate().getTime() + TimeUnit.HOURS.toMillis(3));
            stringBuilder.append("⌚<i>").append(commentTimestamp).append("</i>  ");
            stringBuilder.append("от пользователя <a href=\"tg://user?id=").append(telegramBotUserEntity.getTelegramId()).append("\">").append(telegramUser).append("</a>:\n");
            stringBuilder.append("➖➖➖➖➖➖➖➖➖➖➖➖\n");
            stringBuilder.append("<code>").append(commentData).append("</code>\n");
            stringBuilder.append("➖➖➖➖➖➖➖➖➖➖➖➖\n\n");
            logger.info("User {} comment {}", telegramBotUserEntity, commentData);
        }

        if (mediaCommentsDataEntityList.isEmpty()) {
            stringBuilder.append("<code>").append("Комментарии и обсуждения отсутствуют.  Вы будете первыми! Не стесняйтесь в выражениях \uD83D\uDE09").append("</code>\n\n<u>(ограничение в 250 символов)</u>");
        }
        logger.info("User id {} comment message requested", telegramUserId);

        return new EditMessageText()
                .setChatId(chatId).enableHtml(true)
                .setReplyMarkup(inlineKeyboardMarkup)
                .setMessageId(messageId)
                .setText(stringBuilder.toString());
    }

    @BotRequestMapping(value = "tiktok-comment-write")
    public SendMessage commentWrite(Update update) {
        String name = null != update.getCallbackQuery().getFrom().getUserName() ? update.getCallbackQuery().getFrom().getUserName() : update.getCallbackQuery().getFrom().getFirstName();
//        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
//        keyboard.setOneTimeKeyboard(true);
//        List<KeyboardRow> keyboardRows = new ArrayList<>();
//        KeyboardRow keyboardRowLineOne = new KeyboardRow();
//        keyboardRowLineOne.add("❌Отмена");
//        keyboardRows.add(keyboardRowLineOne);
//        keyboard.setKeyboard(keyboardRows);
        UserContextHolder.currentContext().setCommentRemain(true);
        return new SendMessage()
                .setChatId(update.getCallbackQuery().getMessage().getChatId()).enableHtml(true)
                .setText("<b>" + name + "</b>, отправьте свой комментарий:\n");
    }

    @BotRequestMapping(value = "tiktok-comment-upload")
    public List<SendMessage> uploadWait(Update update) {
        Long requestedUserId = Long.valueOf(update.getMessage().getFrom().getId());
        TelegramBotUserEntity requestedTelegramUser = telegramUserService.getTelegramUser(requestedUserId);
        List<SendMessage> mailingData = new ArrayList<>();

        String message = update.hasMessage() ? update.getMessage().getText() : update.getCallbackQuery().getData();
        message = message.replace("<", "&lt;").replace(">", "&gt;");
        CallBackData callBackData = UserContextHolder.currentContext().getCallBackData();
        Long telegramUserId = Long.valueOf(update.getMessage().getFrom().getId());
        Long fileId = callBackData.getFileId();
        if (message.length() < 250) {
            databaseService.mediaCommentAdd(message, fileId, telegramUserId);
        }
        TelegramUserMediaEntity telegramUserMediaEntity = telegramMediaService.telegramUserMediaGet(fileId);
        TelegramBotUserEntity telegramBotUserEntity = telegramUserMediaEntity.getTelegramBotUserEntity();
        UserContextHolder.currentContext().setCommentRemain(false);
        mailingData.add(comment(update));
        String name = null != telegramBotUserEntity.getUsername() ? telegramBotUserEntity.getUsername() : telegramBotUserEntity.getFirstName();

        String fileType = telegramUserMediaEntity.getFileType() == 1 ? " вашу фотографию" : " ваше видео";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<b>").append(name).append("</b>, ");
        stringBuilder.append(fileType).append("  прокомментировали. Для того чтобы посмотреть комментарий, необходимо перейти в раздел обсуждений нажав на кнопку \uD83C\uDF81<b>Мои медиа</b>.");

        if (!requestedUserId.equals(telegramBotUserEntity.getTelegramId())){
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(telegramUserMediaEntity.getTelegramBotUserEntity().getTelegramId());
            sendMessage.setText(stringBuilder.toString());
            sendMessage.enableHtml(true);
            sendMessage.setParseMode("HTML");
            mailingData.add(sendMessage);
        }
        logger.info("User id {} comment added {}",requestedTelegramUser, message);
        return mailingData;
    }


}
