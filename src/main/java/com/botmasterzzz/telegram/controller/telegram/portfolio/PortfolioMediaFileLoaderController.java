package com.botmasterzzz.telegram.controller.telegram.portfolio;

import com.botmasterzzz.bot.api.impl.methods.PartialBotApiMethod;
import com.botmasterzzz.bot.api.impl.methods.send.SendMessage;
import com.botmasterzzz.bot.api.impl.methods.send.SendPhoto;
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
public class PortfolioMediaFileLoaderController {

    private static final Logger logger = LoggerFactory.getLogger(PortfolioMediaFileLoaderController.class);

    private final TelegramMediaService telegramMediaService;

    private final DatabaseService databaseService;

    private final TelegramUserService telegramUserService;

    private final Gson gson;

    public PortfolioMediaFileLoaderController(TelegramMediaService telegramMediaService, DatabaseService databaseService, TelegramUserService telegramUserService, Gson gson) {
        this.telegramMediaService = telegramMediaService;
        this.databaseService = databaseService;
        this.telegramUserService = telegramUserService;
        this.gson = gson;
    }

    @BotRequestMapping(value = {"portfolio-\uD83D\uDCF2Фото", "portfolio-\uD83D\uDCF2Photo", "portfolio-\uD83D\uDCF2照片"})
    public PartialBotApiMethod sendRandomlyPhoto(Update update) {
        Long chatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        Long requestedUserId = Long.valueOf(update.getMessage().getFrom().getId());
        TelegramBotUserEntity requestedTelegramUser = telegramUserService.getTelegramUser(requestedUserId);
        boolean isRequestedUserAdmin = requestedTelegramUser.isAdmin();
        List<TelegramUserMediaEntity> telegramUserMediaEntityList = telegramMediaService.portfolioMediaList(1);
        if (telegramUserMediaEntityList.isEmpty()){
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("❗Данные отсутствуют \n");
            stringBuilder.append("\n");
            logger.info("User {} sent an unknown command", requestedTelegramUser);
            return new SendMessage()
                    .setChatId(update.getMessage().getChatId()).enableHtml(true)
                    .setText(stringBuilder.toString());
        }
        int old = (int) UserContextHolder.currentContext().getPartId() >= telegramUserMediaEntityList.size() ? telegramUserMediaEntityList.size() - 1 : (int) UserContextHolder.currentContext().getPartId();
        int choosen = old <= 0 ? telegramUserMediaEntityList.size() : old;
        int lastOne = choosen - 1;
        UserContextHolder.currentContext().setPartId(lastOne);
        TelegramUserMediaEntity telegramUserMediaEntity = telegramUserMediaEntityList.get(lastOne);
        boolean isAnon = telegramUserMediaEntity.isAnon();

        TelegramBotUserEntity telegramBotUserEntity = telegramUserMediaEntity.getTelegramBotUserEntity();
        String telegramUser = null != telegramBotUserEntity.getUsername() ? telegramBotUserEntity.getUsername() : telegramBotUserEntity.getFirstName();
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();

        List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsCommentRow = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsDeleteRow = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsProfileDataRow = new ArrayList<>();

        Long telegramUserId = telegramBotUserEntity.getTelegramId();
        Long fileId = telegramUserMediaEntity.getId();

        String logNote = "Chat ID: " + chatId + " User: " + requestedTelegramUser.getUsername();
        databaseService.telegramMediaLogAdd(logNote, fileId, requestedUserId);
        logger.info("Log added: {} User ID: {} Media file ID: {}", logNote, fileId, telegramUserId);

        long hearCount = telegramMediaService.countUserTouch(fileId, "HEART");
        long likeCount = telegramMediaService.countUserTouch(fileId, "LIKE");
        long dislikeCount = telegramMediaService.countUserTouch(fileId, "DISLIKE");
        long countOfMediaLogged = databaseService.getCountOfLoggedToMedia(fileId);
        long discussCount = databaseService.getCountOfDiscuss(fileId);

        InlineKeyboardButton heartInlineButton = new InlineKeyboardButton();
        heartInlineButton.setText("❤️ " + hearCount);
        CallBackData heartCallBackData = new CallBackData("heart", telegramUserId, fileId);
        heartInlineButton.setCallbackData(gson.toJson(heartCallBackData));

        InlineKeyboardButton likeInlineButton = new InlineKeyboardButton();
        likeInlineButton.setText("\uD83D\uDC4D " + likeCount);
        CallBackData likeCallBackData = new CallBackData("like", telegramUserId, fileId);
        likeInlineButton.setCallbackData(gson.toJson(likeCallBackData));

        InlineKeyboardButton dislikeInlineButton = new InlineKeyboardButton();
        dislikeInlineButton.setText("\uD83D\uDC4E️️ " + dislikeCount);
        CallBackData dislikeCallBackData = new CallBackData("dislike", telegramUserId, fileId);
        dislikeInlineButton.setCallbackData(gson.toJson(dislikeCallBackData));

        inlineKeyboardButtonsFirstRow.add(heartInlineButton);
        inlineKeyboardButtonsFirstRow.add(likeInlineButton);
        inlineKeyboardButtonsFirstRow.add(dislikeInlineButton);

        inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);

        String pageFrom = "photo";
        UserContextHolder.currentContext().setFromPageData(pageFrom);
        UserContextHolder.currentContext().setOffs(choosen);
        inlineKeyboardButtons.add(BotMessageHelperUtil.arrowButtonsBuild(pageFrom, choosen, telegramUserId, fileId));

        InlineKeyboardButton commentInlineButton = new InlineKeyboardButton();
        commentInlineButton.setText("\uD83D\uDCDD Обсудить (\uD83D\uDCE8" + discussCount + ")");
        commentInlineButton.setCallbackData(gson.toJson(new CallBackData("comment", telegramUserId, fileId)));
        inlineKeyboardButtonsCommentRow.add(commentInlineButton);
        inlineKeyboardButtons.add(inlineKeyboardButtonsCommentRow);

        if (!isAnon) {
            InlineKeyboardButton profileInfoInlineButton = new InlineKeyboardButton();
            profileInfoInlineButton.setText("\uD83D\uDCF2 Посты пользователя");
            profileInfoInlineButton.setCallbackData(gson.toJson(new CallBackData("more", telegramUserId, fileId)));
            inlineKeyboardButtonsProfileDataRow.add(profileInfoInlineButton);
            inlineKeyboardButtons.add(inlineKeyboardButtonsProfileDataRow);
        }

        if (isRequestedUserAdmin) {
            InlineKeyboardButton deleteInlineButton = new InlineKeyboardButton();
            deleteInlineButton.setText("❌ Удалить");
            deleteInlineButton.setCallbackData(gson.toJson(new CallBackData("delete", telegramUserId, fileId)));
            inlineKeyboardButtonsDeleteRow.add(deleteInlineButton);
            inlineKeyboardButtons.add(inlineKeyboardButtonsDeleteRow);
        }

        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);

        String fileIdx = telegramUserMediaEntity.getFileId();
        String caption = null != telegramUserMediaEntity.getMessage() ? telegramUserMediaEntity.getMessage() : "&lt;описание отсутствует&gt;";
        String commentTimestamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(telegramUserMediaEntity.getAudWhenCreate().getTime() + TimeUnit.HOURS.toMillis(3));
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(fileIdx);
        if (!isAnon) {
            sendPhoto.setCaption(caption + "\n\n\uD83D\uDC41 " + countOfMediaLogged + "\nФотография от пользователя <a href=\"tg://user?id=" + telegramBotUserEntity.getTelegramId() + "\">" + telegramUser + "</a> ⌚<i>" + commentTimestamp + "</i>");
        } else {
            sendPhoto.setCaption(caption + "\n\n\uD83D\uDC41 " + countOfMediaLogged + "\nanonymous ⌚<i>" + commentTimestamp + "</i>");
        }
        sendPhoto.setReplyMarkup(inlineKeyboardMarkup);
        sendPhoto.setParseMode("HTML");
        sendPhoto.disableNotification();
        logger.info("User: {}", requestedTelegramUser);
        logger.info("User id {} sent media message {} ", telegramUserMediaEntity.getTelegramBotUserEntity().getId(), telegramUserMediaEntity);
        return sendPhoto;
    }

    @BotRequestMapping(value = "portfolio-/photo")
    public PartialBotApiMethod sendRandomlyMediaFromCommand(Update update) {
        Long chatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        List<TelegramUserMediaEntity> telegramUserMediaEntityList = telegramMediaService.portfolioMediaList(1);
        Long requestedUserId = Long.valueOf(update.getMessage().getFrom().getId());
        TelegramBotUserEntity requestedTelegramUser = telegramUserService.getTelegramUser(requestedUserId);
        boolean isRequestedUserAdmin = requestedTelegramUser.isAdmin();
        if (telegramUserMediaEntityList.isEmpty()){
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("❗Данные отсутствуют \n");
            stringBuilder.append("\n");
            logger.info("User {} sent an unknown command", requestedTelegramUser);
            return new SendMessage()
                    .setChatId(update.getMessage().getChatId()).enableHtml(true)
                    .setText(stringBuilder.toString());
        }
        int old = (int) UserContextHolder.currentContext().getPartId() >= telegramUserMediaEntityList.size() ? telegramUserMediaEntityList.size() - 1 : (int) UserContextHolder.currentContext().getPartId();
        int choosen = old <= 0 ? telegramUserMediaEntityList.size() : old;
        int lastOne = choosen - 1;
        UserContextHolder.currentContext().setPartId(lastOne);
        TelegramUserMediaEntity telegramUserMediaEntity = telegramUserMediaEntityList.get(lastOne);
        boolean isAnon = telegramUserMediaEntity.isAnon();

        TelegramBotUserEntity telegramBotUserEntity = telegramUserMediaEntity.getTelegramBotUserEntity();
        String telegramUser = null != telegramBotUserEntity.getUsername() ? telegramBotUserEntity.getUsername() : telegramBotUserEntity.getFirstName();
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();

        List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsCommentRow = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsDeleteRow = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsProfileDataRow = new ArrayList<>();

        Long telegramUserId = telegramBotUserEntity.getTelegramId();
        Long fileId = telegramUserMediaEntity.getId();

        String logNote = "Chat ID: " + chatId + " User: " + requestedTelegramUser.getUsername();
        databaseService.telegramMediaLogAdd(logNote, fileId, requestedUserId);
        logger.info("Log added: {} User ID: {} Media file ID: {}", logNote, fileId, telegramUserId);

        long hearCount = telegramMediaService.countUserTouch(fileId, "HEART");
        long likeCount = telegramMediaService.countUserTouch(fileId, "LIKE");
        long dislikeCount = telegramMediaService.countUserTouch(fileId, "DISLIKE");
        long countOfMediaLogged = databaseService.getCountOfLoggedToMedia(fileId);
        long discussCount = databaseService.getCountOfDiscuss(fileId);

        InlineKeyboardButton heartInlineButton = new InlineKeyboardButton();
        heartInlineButton.setText("❤️ " + hearCount);
        CallBackData heartCallBackData = new CallBackData("heart", telegramUserId, fileId);
        heartInlineButton.setCallbackData(gson.toJson(heartCallBackData));

        InlineKeyboardButton likeInlineButton = new InlineKeyboardButton();
        likeInlineButton.setText("\uD83D\uDC4D " + likeCount);
        CallBackData likeCallBackData = new CallBackData("like", telegramUserId, fileId);
        likeInlineButton.setCallbackData(gson.toJson(likeCallBackData));

        InlineKeyboardButton dislikeInlineButton = new InlineKeyboardButton();
        dislikeInlineButton.setText("\uD83D\uDC4E️️ " + dislikeCount);
        CallBackData dislikeCallBackData = new CallBackData("dislike", telegramUserId, fileId);
        dislikeInlineButton.setCallbackData(gson.toJson(dislikeCallBackData));

        inlineKeyboardButtonsFirstRow.add(heartInlineButton);
        inlineKeyboardButtonsFirstRow.add(likeInlineButton);
        inlineKeyboardButtonsFirstRow.add(dislikeInlineButton);
        inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);

        String pageFrom = "photo";
        UserContextHolder.currentContext().setFromPageData(pageFrom);
        UserContextHolder.currentContext().setOffs(choosen);
        inlineKeyboardButtons.add(BotMessageHelperUtil.arrowButtonsBuild(pageFrom, choosen, telegramUserId, fileId));

        InlineKeyboardButton commentInlineButton = new InlineKeyboardButton();
        commentInlineButton.setText("\uD83D\uDCDD Обсудить (\uD83D\uDCE8" + discussCount + ")");
        commentInlineButton.setCallbackData(gson.toJson(new CallBackData("comment", telegramUserId, fileId)));
        inlineKeyboardButtonsCommentRow.add(commentInlineButton);
        inlineKeyboardButtons.add(inlineKeyboardButtonsCommentRow);

        if (!isAnon) {
            InlineKeyboardButton profileInfoInlineButton = new InlineKeyboardButton();
            profileInfoInlineButton.setText("\uD83D\uDCF2 Посты пользователя");
            profileInfoInlineButton.setCallbackData(gson.toJson(new CallBackData("more", telegramUserId, fileId)));
            inlineKeyboardButtonsProfileDataRow.add(profileInfoInlineButton);
            inlineKeyboardButtons.add(inlineKeyboardButtonsProfileDataRow);
        }
        if (isRequestedUserAdmin) {
            InlineKeyboardButton deleteInlineButton = new InlineKeyboardButton();
            deleteInlineButton.setText("❌ Удалить");
            deleteInlineButton.setCallbackData(gson.toJson(new CallBackData("delete", telegramUserId, fileId)));
            inlineKeyboardButtonsDeleteRow.add(deleteInlineButton);
            inlineKeyboardButtons.add(inlineKeyboardButtonsDeleteRow);
        }

        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);

        String fileIdx = telegramUserMediaEntity.getFileId();
        String caption = null != telegramUserMediaEntity.getMessage() ? telegramUserMediaEntity.getMessage() : "&lt;описание отсутствует&gt;";
        String commentTimestamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(telegramUserMediaEntity.getAudWhenCreate().getTime() + TimeUnit.HOURS.toMillis(3));
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(fileIdx);
        if (!isAnon) {
            sendPhoto.setCaption(caption + "\n\n\uD83D\uDC41 " + countOfMediaLogged + "\nФотография от пользователя <a href=\"tg://user?id=" + telegramBotUserEntity.getTelegramId() + "\">" + telegramUser + "</a> ⌚<i>" + commentTimestamp + "</i>");
        } else {
            sendPhoto.setCaption(caption + "\n\n\uD83D\uDC41 " + countOfMediaLogged + "\nanonymous ⌚<i>" + commentTimestamp + "</i>");
        }
        sendPhoto.setReplyMarkup(inlineKeyboardMarkup);
        sendPhoto.setParseMode("HTML");
        sendPhoto.disableNotification();
        logger.info("User: {}", requestedTelegramUser);
        logger.info("User id {} sent media message {} ", telegramUserMediaEntity.getTelegramBotUserEntity().getId(), telegramUserMediaEntity);
        return sendPhoto;
    }


    @BotRequestMapping(value = "portfolio-arf")
    public SendPhoto sendRandomlyMediaFromArrows(Update update) {
        Long chatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        Long requestedUserId = Long.valueOf(update.getCallbackQuery().getFrom().getId());
        TelegramBotUserEntity requestedTelegramUser = telegramUserService.getTelegramUser(requestedUserId);
        boolean isRequestedUserAdmin = requestedTelegramUser.isAdmin();
        List<TelegramUserMediaEntity> telegramUserMediaEntityList = telegramMediaService.portfolioMediaList(1);
        CallBackData callBackData = UserContextHolder.currentContext().getCallBackData();
        int offset = null != callBackData.getOffset() ? callBackData.getOffset() : callBackData.getO();
        int limit = null != callBackData.getLimit() ? callBackData.getLimit() : callBackData.getL();


        int old = offset >= limit ? limit - 1 : offset;
        int choosen = old <= 0 ? telegramUserMediaEntityList.size() : old;
        int lastOne = choosen - 1;
        UserContextHolder.currentContext().setPartId(lastOne);
        TelegramUserMediaEntity telegramUserMediaEntity = telegramUserMediaEntityList.get(lastOne);
        boolean isAnon = telegramUserMediaEntity.isAnon();

        TelegramBotUserEntity telegramBotUserEntity = telegramUserMediaEntity.getTelegramBotUserEntity();
        String telegramUser = null != telegramBotUserEntity.getUsername() ? telegramBotUserEntity.getUsername() : telegramBotUserEntity.getFirstName();
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();

        List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsCommentRow = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsDeleteRow = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsProfileDataRow = new ArrayList<>();

        Long telegramUserId = telegramBotUserEntity.getTelegramId();
        Long fileId = telegramUserMediaEntity.getId();

        String logNote = "Chat ID: " + chatId + " User: " + requestedTelegramUser.getUsername();
        databaseService.telegramMediaLogAdd(logNote, fileId, requestedUserId);
        logger.info("Log added: {} User ID: {} Media file ID: {}", logNote, fileId, telegramUserId);

        long hearCount = telegramMediaService.countUserTouch(fileId, "HEART");
        long likeCount = telegramMediaService.countUserTouch(fileId, "LIKE");
        long dislikeCount = telegramMediaService.countUserTouch(fileId, "DISLIKE");
        long countOfMediaLogged = databaseService.getCountOfLoggedToMedia(fileId);
        long discussCount = databaseService.getCountOfDiscuss(fileId);

        InlineKeyboardButton heartInlineButton = new InlineKeyboardButton();
        heartInlineButton.setText("❤️ " + hearCount);
        CallBackData heartCallBackData = new CallBackData("heart", telegramUserId, fileId);
        heartInlineButton.setCallbackData(gson.toJson(heartCallBackData));

        InlineKeyboardButton likeInlineButton = new InlineKeyboardButton();
        likeInlineButton.setText("\uD83D\uDC4D " + likeCount);
        CallBackData likeCallBackData = new CallBackData("like", telegramUserId, fileId);
        likeInlineButton.setCallbackData(gson.toJson(likeCallBackData));

        InlineKeyboardButton dislikeInlineButton = new InlineKeyboardButton();
        dislikeInlineButton.setText("\uD83D\uDC4E️️ " + dislikeCount);
        CallBackData dislikeCallBackData = new CallBackData("dislike", telegramUserId, fileId);
        dislikeInlineButton.setCallbackData(gson.toJson(dislikeCallBackData));

        inlineKeyboardButtonsFirstRow.add(heartInlineButton);
        inlineKeyboardButtonsFirstRow.add(likeInlineButton);
        inlineKeyboardButtonsFirstRow.add(dislikeInlineButton);

        inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);

        String pageFrom = "photo";
        UserContextHolder.currentContext().setFromPageData(pageFrom);
        UserContextHolder.currentContext().setOffs(choosen);
        inlineKeyboardButtons.add(BotMessageHelperUtil.arrowButtonsBuild(pageFrom, choosen, telegramUserId, fileId));

        InlineKeyboardButton commentInlineButton = new InlineKeyboardButton();
        commentInlineButton.setText("\uD83D\uDCDD Обсудить (\uD83D\uDCE8" + discussCount + ")");
        commentInlineButton.setCallbackData(gson.toJson(new CallBackData("comment", telegramUserId, fileId)));
        inlineKeyboardButtonsCommentRow.add(commentInlineButton);
        inlineKeyboardButtons.add(inlineKeyboardButtonsCommentRow);

        if (!isAnon) {
            InlineKeyboardButton profileInfoInlineButton = new InlineKeyboardButton();
            profileInfoInlineButton.setText("\uD83D\uDCF2 Посты пользователя");
            profileInfoInlineButton.setCallbackData(gson.toJson(new CallBackData("more", telegramUserId, fileId)));
            inlineKeyboardButtonsProfileDataRow.add(profileInfoInlineButton);
            inlineKeyboardButtons.add(inlineKeyboardButtonsProfileDataRow);
        }

        if (isRequestedUserAdmin) {
            InlineKeyboardButton deleteInlineButton = new InlineKeyboardButton();
            deleteInlineButton.setText("❌ Удалить");
            deleteInlineButton.setCallbackData(gson.toJson(new CallBackData("delete", telegramUserId, fileId)));
            inlineKeyboardButtonsDeleteRow.add(deleteInlineButton);
            inlineKeyboardButtons.add(inlineKeyboardButtonsDeleteRow);
        }

        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);

        String fileIdx = telegramUserMediaEntity.getFileId();
        String caption = null != telegramUserMediaEntity.getMessage() ? telegramUserMediaEntity.getMessage() : "&lt;описание отсутствует&gt;";
        String commentTimestamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(telegramUserMediaEntity.getAudWhenCreate().getTime() + TimeUnit.HOURS.toMillis(3));
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(fileIdx);
        if (!isAnon) {
            sendPhoto.setCaption(caption + "\n\n\uD83D\uDC41 " + countOfMediaLogged + "\nФотография от пользователя <a href=\"tg://user?id=" + telegramBotUserEntity.getTelegramId() + "\">" + telegramUser + "</a> ⌚<i>" + commentTimestamp + "</i>");
        } else {
            sendPhoto.setCaption(caption + "\n\n\uD83D\uDC41 " + countOfMediaLogged + "\nanonymous ⌚<i>" + commentTimestamp + "</i>");
        }
        sendPhoto.setReplyMarkup(inlineKeyboardMarkup);
        sendPhoto.setParseMode("HTML");
        sendPhoto.disableNotification();
        logger.info("User: {}", requestedTelegramUser);
        logger.info("User id {} sent media message {} ", telegramUserMediaEntity.getTelegramBotUserEntity().getId(), telegramUserMediaEntity);
        return sendPhoto;
    }
}
