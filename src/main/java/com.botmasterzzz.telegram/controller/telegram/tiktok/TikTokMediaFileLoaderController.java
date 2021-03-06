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
public class TikTokMediaFileLoaderController {

    private static final Logger logger = LoggerFactory.getLogger(TikTokMediaFileLoaderController.class);

    private final TelegramMediaService telegramMediaService;

    private final DatabaseService databaseService;

    private final TelegramUserService telegramUserService;

    private final Gson gson;

    public TikTokMediaFileLoaderController(TelegramMediaService telegramMediaService, DatabaseService databaseService, TelegramUserService telegramUserService, Gson gson) {
        this.telegramMediaService = telegramMediaService;
        this.databaseService = databaseService;
        this.telegramUserService = telegramUserService;
        this.gson = gson;
    }

    @BotRequestMapping(value = {"tiktok-\uD83C\uDD95?????????? ???? ??????????????", "tiktok-\uD83C\uDD95New For Today", "tiktok-\uD83C\uDD95??????????????????"})
    public PartialBotApiMethod sendRandomlyMediaForToday(Update update) {
        Long chatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        Long requestedUserId = Long.valueOf(update.getMessage().getFrom().getId());
        TelegramBotUserEntity requestedTelegramUser = telegramUserService.getTelegramUser(requestedUserId);
        boolean isRequestedUserAdmin = requestedTelegramUser.isAdmin();
        List<TelegramUserMediaEntity> telegramUserMediaEntityList = telegramMediaService.telegramUserMediaListForToday();
        if (telegramUserMediaEntityList.isEmpty()){
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("??????????????? ?????????????????????? \n\n?????????????????? ???????? ???????????????????? ?????? ?????????? ?????????? ?? ???? ???????????? ??????????????!\n");
            stringBuilder.append("\n");
            logger.info("User {} sent an unknown command", requestedTelegramUser);
            return new SendMessage()
                    .setChatId(update.getMessage().getChatId()).enableHtml(true)
                    .setText(stringBuilder.toString());
        }
        int old = (int) UserContextHolder.currentContext().getPartId() >= telegramUserMediaEntityList.size() ? telegramUserMediaEntityList.size() - 1 : (int) UserContextHolder.currentContext().getPartId();
//        Collections.shuffle(telegramUserMediaEntityList);
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
        heartInlineButton.setText("?????? " + hearCount);
        CallBackData heartCallBackData = new CallBackData("heart", telegramUserId, fileId);
        heartInlineButton.setCallbackData(gson.toJson(heartCallBackData));

        InlineKeyboardButton likeInlineButton = new InlineKeyboardButton();
        likeInlineButton.setText("\uD83D\uDC4D " + likeCount);
        CallBackData likeCallBackData = new CallBackData("like", telegramUserId, fileId);
        likeInlineButton.setCallbackData(gson.toJson(likeCallBackData));

        InlineKeyboardButton dislikeInlineButton = new InlineKeyboardButton();
        dislikeInlineButton.setText("\uD83D\uDC4E?????? " + dislikeCount);
        CallBackData dislikeCallBackData = new CallBackData("dislike", telegramUserId, fileId);
        dislikeInlineButton.setCallbackData(gson.toJson(dislikeCallBackData));

        inlineKeyboardButtonsFirstRow.add(heartInlineButton);
        inlineKeyboardButtonsFirstRow.add(likeInlineButton);
        inlineKeyboardButtonsFirstRow.add(dislikeInlineButton);

        inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);
        String pageFrom = "newForToday";
        UserContextHolder.currentContext().setFromPageData(pageFrom);
        UserContextHolder.currentContext().setOffs(choosen);
        inlineKeyboardButtons.add(BotMessageHelperUtil.arrowButtonsBuild(pageFrom, choosen, telegramUserId, fileId));

        InlineKeyboardButton commentInlineButton = new InlineKeyboardButton();
        commentInlineButton.setText("\uD83D\uDCDD ???????????????? (\uD83D\uDCE8" + discussCount + ")") ;
        commentInlineButton.setCallbackData(gson.toJson(new CallBackData("comment", telegramUserId, fileId)));
        inlineKeyboardButtonsCommentRow.add(commentInlineButton);
        inlineKeyboardButtons.add(inlineKeyboardButtonsCommentRow);

        if (!isAnon){
            InlineKeyboardButton profileInfoInlineButton = new InlineKeyboardButton();
            profileInfoInlineButton.setText("\uD83D\uDCF2 ?????????? ????????????????????????") ;
            profileInfoInlineButton.setCallbackData(gson.toJson(new CallBackData("more", telegramUserId, fileId)));
            inlineKeyboardButtonsProfileDataRow.add(profileInfoInlineButton);
            inlineKeyboardButtons.add(inlineKeyboardButtonsProfileDataRow);
        }

        if (isRequestedUserAdmin){
            InlineKeyboardButton deleteInlineButton = new InlineKeyboardButton();
            deleteInlineButton.setText("??? ??????????????");
            deleteInlineButton.setCallbackData(gson.toJson(new CallBackData("delete", telegramUserId, fileId)));
            inlineKeyboardButtonsDeleteRow.add(deleteInlineButton);
            inlineKeyboardButtons.add(inlineKeyboardButtonsDeleteRow);
        }

        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);

        String fileIdx = telegramUserMediaEntity.getFileId();
        String caption = null != telegramUserMediaEntity.getMessage() ? telegramUserMediaEntity.getMessage() : "&lt;???????????????? ??????????????????????&gt;";
        String commentTimestamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(telegramUserMediaEntity.getAudWhenCreate().getTime() + TimeUnit.HOURS.toMillis(3));
        if (telegramUserMediaEntity.getFileType() == 1){
            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setChatId(chatId);
            sendPhoto.setPhoto(fileIdx);
            if (!isAnon){
                sendPhoto.setCaption(caption + "\n\n\uD83D\uDC41 " + countOfMediaLogged + "\n???????????????????? ???? ???????????????????????? <a href=\"tg://user?id=" + telegramBotUserEntity.getTelegramId() + "\">" + telegramUser + "</a> ???<i>" + commentTimestamp + "</i>");
            }else {
                sendPhoto.setCaption(caption + "\n\n\uD83D\uDC41 " + countOfMediaLogged + "\nanonymous ???<i>" + commentTimestamp + "</i>");
            }
            sendPhoto.setReplyMarkup(inlineKeyboardMarkup);
            sendPhoto.setParseMode("HTML");
            sendPhoto.disableNotification();
            logger.info("User: {}", requestedTelegramUser);
            logger.info("User id {} sent media message {} ", telegramUserMediaEntity.getTelegramBotUserEntity().getId(), telegramUserMediaEntity);
            return sendPhoto;
        }else if(telegramUserMediaEntity.getHeight() > 0 && telegramUserMediaEntity.getWidth() > 0){
            SendVideo sendVideo = new SendVideo();
            sendVideo.setChatId(chatId);
            sendVideo.setVideo(fileIdx);
            if (!isAnon){
                sendVideo.setCaption(caption + "\n\n\uD83D\uDC41 " + countOfMediaLogged + "\n?????????? ???? ???????????????????????? <a href=\"tg://user?id=" + telegramBotUserEntity.getTelegramId() + "\">" + telegramUser + "</a> ???<i>" + commentTimestamp + "</i>");
            }else {
                sendVideo.setCaption(caption + "\n\n\uD83D\uDC41 " + countOfMediaLogged + "\nanonymous");
            }
            sendVideo.setReplyMarkup(inlineKeyboardMarkup);
            sendVideo.setParseMode("HTML");
            sendVideo.disableNotification();
            logger.info("User id {} sent media message {} ", telegramUserMediaEntity.getTelegramBotUserEntity().getId(), telegramUserMediaEntity);
            return sendVideo;
        }else {
            SendDocument sendDocument = new SendDocument();
            sendDocument.setChatId(chatId);
            sendDocument.setDocument(fileIdx);
            if (!isAnon){
                sendDocument.setCaption("\n\n\uD83D\uDC41 " + countOfMediaLogged + "\n?????????? ???? ???????????????????????? <a href=\"tg://user?id=" + telegramBotUserEntity.getTelegramId() + "\">" + telegramUser + "</a> ???<i>" + commentTimestamp + "</i>");
            }else {
                sendDocument.setCaption("\n\n\uD83D\uDC41 " + countOfMediaLogged + "\nanonymous ???<i>" + commentTimestamp + "</i>");
            }
            sendDocument.setReplyMarkup(inlineKeyboardMarkup);
            sendDocument.setParseMode("HTML");
            sendDocument.disableNotification();
            logger.info("User id {} sent media message {} ", telegramUserMediaEntity.getTelegramBotUserEntity().getId(), telegramUserMediaEntity);
            return sendDocument;
        }
    }

    @BotRequestMapping(value = "tiktok-art")
    public PartialBotApiMethod sendRandomlyMediaForTodayFromArrows(Update update) {
        Long chatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        Long requestedUserId = Long.valueOf(update.getCallbackQuery().getFrom().getId());
        TelegramBotUserEntity requestedTelegramUser = telegramUserService.getTelegramUser(requestedUserId);
        boolean isRequestedUserAdmin = requestedTelegramUser.isAdmin();
        List<TelegramUserMediaEntity> telegramUserMediaEntityList = telegramMediaService.telegramUserMediaListForToday();

        CallBackData callBackData = UserContextHolder.currentContext().getCallBackData();
        Long mediaForUser =  UserContextHolder.currentContext().getForUserId();
        int offset = null != callBackData.getOffset() ? callBackData.getOffset() : callBackData.getO();
        int limit = null != callBackData.getLimit() ? callBackData.getLimit() : callBackData.getL();


        int old = offset >= limit ? limit - 1 : offset;
//
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
        heartInlineButton.setText("?????? " + hearCount);
        CallBackData heartCallBackData = new CallBackData("heart", telegramUserId, fileId);
        heartInlineButton.setCallbackData(gson.toJson(heartCallBackData));

        InlineKeyboardButton likeInlineButton = new InlineKeyboardButton();
        likeInlineButton.setText("\uD83D\uDC4D " + likeCount);
        CallBackData likeCallBackData = new CallBackData("like", telegramUserId, fileId);
        likeInlineButton.setCallbackData(gson.toJson(likeCallBackData));

        InlineKeyboardButton dislikeInlineButton = new InlineKeyboardButton();
        dislikeInlineButton.setText("\uD83D\uDC4E?????? " + dislikeCount);
        CallBackData dislikeCallBackData = new CallBackData("dislike", telegramUserId, fileId);
        dislikeInlineButton.setCallbackData(gson.toJson(dislikeCallBackData));

        inlineKeyboardButtonsFirstRow.add(heartInlineButton);
        inlineKeyboardButtonsFirstRow.add(likeInlineButton);
        inlineKeyboardButtonsFirstRow.add(dislikeInlineButton);

        inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);

        String pageFrom = "newForToday";
        UserContextHolder.currentContext().setFromPageData(pageFrom);
        UserContextHolder.currentContext().setOffs(choosen);
        inlineKeyboardButtons.add(BotMessageHelperUtil.arrowButtonsBuild(pageFrom, choosen, telegramUserId, fileId));

        InlineKeyboardButton commentInlineButton = new InlineKeyboardButton();
        commentInlineButton.setText("\uD83D\uDCDD ???????????????? (\uD83D\uDCE8" + discussCount + ")") ;
        commentInlineButton.setCallbackData(gson.toJson(new CallBackData("comment", telegramUserId, fileId)));
        inlineKeyboardButtonsCommentRow.add(commentInlineButton);
        inlineKeyboardButtons.add(inlineKeyboardButtonsCommentRow);

        if (!isAnon){
            InlineKeyboardButton profileInfoInlineButton = new InlineKeyboardButton();
            profileInfoInlineButton.setText("\uD83D\uDCF2 ?????????? ????????????????????????") ;
            profileInfoInlineButton.setCallbackData(gson.toJson(new CallBackData("more", telegramUserId, fileId)));
            inlineKeyboardButtonsProfileDataRow.add(profileInfoInlineButton);
            inlineKeyboardButtons.add(inlineKeyboardButtonsProfileDataRow);
        }

        if (isRequestedUserAdmin){
            InlineKeyboardButton deleteInlineButton = new InlineKeyboardButton();
            deleteInlineButton.setText("??? ??????????????");
            deleteInlineButton.setCallbackData(gson.toJson(new CallBackData("delete", telegramUserId, fileId)));
            inlineKeyboardButtonsDeleteRow.add(deleteInlineButton);
            inlineKeyboardButtons.add(inlineKeyboardButtonsDeleteRow);
        }

        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);

        String fileIdx = telegramUserMediaEntity.getFileId();
        String caption = null != telegramUserMediaEntity.getMessage() ? telegramUserMediaEntity.getMessage() : "&lt;???????????????? ??????????????????????&gt;";
        String commentTimestamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(telegramUserMediaEntity.getAudWhenCreate().getTime() + TimeUnit.HOURS.toMillis(3));
        if (telegramUserMediaEntity.getFileType() == 1){
            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setChatId(chatId);
            sendPhoto.setPhoto(fileIdx);
            if (!isAnon){
                sendPhoto.setCaption(caption + "\n\n\uD83D\uDC41 " + countOfMediaLogged + "\n???????????????????? ???? ???????????????????????? <a href=\"tg://user?id=" + telegramBotUserEntity.getTelegramId() + "\">" + telegramUser + "</a> ???<i>" + commentTimestamp + "</i>");
            }else {
                sendPhoto.setCaption(caption + "\n\n\uD83D\uDC41 " + countOfMediaLogged + "\nanonymous ???<i>" + commentTimestamp + "</i>");
            }
            sendPhoto.setReplyMarkup(inlineKeyboardMarkup);
            sendPhoto.setParseMode("HTML");
            sendPhoto.disableNotification();
            logger.info("User: {}", requestedTelegramUser);
            logger.info("User id {} sent media message {} ", telegramUserMediaEntity.getTelegramBotUserEntity().getId(), telegramUserMediaEntity);
            return sendPhoto;
        }else if(telegramUserMediaEntity.getHeight() > 0 && telegramUserMediaEntity.getWidth() > 0){
            SendVideo sendVideo = new SendVideo();
            sendVideo.setChatId(chatId);
            sendVideo.setVideo(fileIdx);
            if (!isAnon){
                sendVideo.setCaption(caption + "\n\n\uD83D\uDC41 " + countOfMediaLogged + "\n?????????? ???? ???????????????????????? <a href=\"tg://user?id=" + telegramBotUserEntity.getTelegramId() + "\">" + telegramUser + "</a> ???<i>" + commentTimestamp + "</i>");
            }else {
                sendVideo.setCaption(caption + "\n\n\uD83D\uDC41 " + countOfMediaLogged + "\nanonymous");
            }
            sendVideo.setReplyMarkup(inlineKeyboardMarkup);
            sendVideo.setParseMode("HTML");
            sendVideo.disableNotification();
            logger.info("User id {} sent media message {} ", telegramUserMediaEntity.getTelegramBotUserEntity().getId(), telegramUserMediaEntity);
            return sendVideo;
        }else {
            SendDocument sendDocument = new SendDocument();
            sendDocument.setChatId(chatId);
            sendDocument.setDocument(fileIdx);
            if (!isAnon){
                sendDocument.setCaption("\n\n\uD83D\uDC41 " + countOfMediaLogged + "\n?????????? ???? ???????????????????????? <a href=\"tg://user?id=" + telegramBotUserEntity.getTelegramId() + "\">" + telegramUser + "</a> ???<i>" + commentTimestamp + "</i>");
            }else {
                sendDocument.setCaption("\n\n\uD83D\uDC41 " + countOfMediaLogged + "\nanonymous ???<i>" + commentTimestamp + "</i>");
            }
            sendDocument.setReplyMarkup(inlineKeyboardMarkup);
            sendDocument.setParseMode("HTML");
            sendDocument.disableNotification();
            logger.info("User id {} sent media message {} ", telegramUserMediaEntity.getTelegramBotUserEntity().getId(), telegramUserMediaEntity);
            return sendDocument;
        }
    }

    @BotRequestMapping(value = {"tiktok-\uD83C\uDF88?????????? ???? ??????????", "tiktok-\uD83C\uDF88New For Yesterday", "tiktok-\uD83C\uDF88?????????"})
    public PartialBotApiMethod sendRandomlyMediaForYesterday(Update update) {
        Long chatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        Long requestedUserId = Long.valueOf(update.getMessage().getFrom().getId());
        TelegramBotUserEntity requestedTelegramUser = telegramUserService.getTelegramUser(requestedUserId);
        boolean isRequestedUserAdmin = requestedTelegramUser.isAdmin();
        List<TelegramUserMediaEntity> telegramUserMediaEntityList = telegramMediaService.telegramUserMediaListForYesterday();
        if (telegramUserMediaEntityList.isEmpty()){
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("??????????????? ?????????????????????? \n\n?????????????????? ???????? ???????????????????? ?????? ?????????? ?????????? ?? ???? ???????????? ??????????????!\n");
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
        heartInlineButton.setText("?????? " + hearCount);
        CallBackData heartCallBackData = new CallBackData("heart", telegramUserId, fileId);
        heartInlineButton.setCallbackData(gson.toJson(heartCallBackData));

        InlineKeyboardButton likeInlineButton = new InlineKeyboardButton();
        likeInlineButton.setText("\uD83D\uDC4D " + likeCount);
        CallBackData likeCallBackData = new CallBackData("like", telegramUserId, fileId);
        likeInlineButton.setCallbackData(gson.toJson(likeCallBackData));

        InlineKeyboardButton dislikeInlineButton = new InlineKeyboardButton();
        dislikeInlineButton.setText("\uD83D\uDC4E?????? " + dislikeCount);
        CallBackData dislikeCallBackData = new CallBackData("dislike", telegramUserId, fileId);
        dislikeInlineButton.setCallbackData(gson.toJson(dislikeCallBackData));

        inlineKeyboardButtonsFirstRow.add(heartInlineButton);
        inlineKeyboardButtonsFirstRow.add(likeInlineButton);
        inlineKeyboardButtonsFirstRow.add(dislikeInlineButton);

        inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);

        String pageFrom = "newForYesterday";
        UserContextHolder.currentContext().setFromPageData(pageFrom);
        UserContextHolder.currentContext().setOffs(choosen);
        inlineKeyboardButtons.add(BotMessageHelperUtil.arrowButtonsBuild(pageFrom, choosen, telegramUserId, fileId));

        InlineKeyboardButton commentInlineButton = new InlineKeyboardButton();
        commentInlineButton.setText("\uD83D\uDCDD ???????????????? (\uD83D\uDCE8" + discussCount + ")") ;
        commentInlineButton.setCallbackData(gson.toJson(new CallBackData("comment", telegramUserId, fileId)));
        inlineKeyboardButtonsCommentRow.add(commentInlineButton);
        inlineKeyboardButtons.add(inlineKeyboardButtonsCommentRow);

        if (!isAnon){
            InlineKeyboardButton profileInfoInlineButton = new InlineKeyboardButton();
            profileInfoInlineButton.setText("\uD83D\uDCF2 ?????????? ????????????????????????") ;
            profileInfoInlineButton.setCallbackData(gson.toJson(new CallBackData("more", telegramUserId, fileId)));
            inlineKeyboardButtonsProfileDataRow.add(profileInfoInlineButton);
            inlineKeyboardButtons.add(inlineKeyboardButtonsProfileDataRow);
        }
        if (isRequestedUserAdmin){
            InlineKeyboardButton deleteInlineButton = new InlineKeyboardButton();
            deleteInlineButton.setText("??? ??????????????");
            deleteInlineButton.setCallbackData(gson.toJson(new CallBackData("delete", telegramUserId, fileId)));
            inlineKeyboardButtonsDeleteRow.add(deleteInlineButton);
            inlineKeyboardButtons.add(inlineKeyboardButtonsDeleteRow);
        }

        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);

        String fileIdx = telegramUserMediaEntity.getFileId();
        String caption = null != telegramUserMediaEntity.getMessage() ? telegramUserMediaEntity.getMessage() : "&lt;???????????????? ??????????????????????&gt;";
        String commentTimestamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(telegramUserMediaEntity.getAudWhenCreate().getTime() + TimeUnit.HOURS.toMillis(3));
        if (telegramUserMediaEntity.getFileType() == 1){
            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setChatId(chatId);
            sendPhoto.setPhoto(fileIdx);
            if (!isAnon){
                sendPhoto.setCaption(caption + "\n\n\uD83D\uDC41 " + countOfMediaLogged + "\n???????????????????? ???? ???????????????????????? <a href=\"tg://user?id=" + telegramBotUserEntity.getTelegramId() + "\">" + telegramUser + "</a> ???<i>" + commentTimestamp + "</i>");
            }else {
                sendPhoto.setCaption(caption + "\n\n\uD83D\uDC41 " + countOfMediaLogged + "\nanonymous ???<i>" + commentTimestamp + "</i>");
            }
            sendPhoto.setReplyMarkup(inlineKeyboardMarkup);
            sendPhoto.setParseMode("HTML");
            sendPhoto.disableNotification();
            logger.info("User: {}", requestedTelegramUser);
            logger.info("User id {} sent media message {} ", telegramUserMediaEntity.getTelegramBotUserEntity().getId(), telegramUserMediaEntity);
            return sendPhoto;
        }else if(telegramUserMediaEntity.getHeight() > 0 && telegramUserMediaEntity.getWidth() > 0){
            SendVideo sendVideo = new SendVideo();
            sendVideo.setChatId(chatId);
            sendVideo.setVideo(fileIdx);
            if (!isAnon){
                sendVideo.setCaption(caption + "\n\n\uD83D\uDC41 " + countOfMediaLogged + "\n?????????? ???? ???????????????????????? <a href=\"tg://user?id=" + telegramBotUserEntity.getTelegramId() + "\">" + telegramUser + "</a> ???<i>" + commentTimestamp + "</i>");
            }else {
                sendVideo.setCaption(caption + "\n\n\uD83D\uDC41 " + countOfMediaLogged + "\nanonymous");
            }
            sendVideo.setReplyMarkup(inlineKeyboardMarkup);
            sendVideo.setParseMode("HTML");
            sendVideo.disableNotification();
            logger.info("User id {} sent media message {} ", telegramUserMediaEntity.getTelegramBotUserEntity().getId(), telegramUserMediaEntity);
            return sendVideo;
        }else {
            SendDocument sendDocument = new SendDocument();
            sendDocument.setChatId(chatId);
            sendDocument.setDocument(fileIdx);
            if (!isAnon){
                sendDocument.setCaption("\n\n\uD83D\uDC41 " + countOfMediaLogged + "\n?????????? ???? ???????????????????????? <a href=\"tg://user?id=" + telegramBotUserEntity.getTelegramId() + "\">" + telegramUser + "</a> ???<i>" + commentTimestamp + "</i>");
            }else {
                sendDocument.setCaption("\n\n\uD83D\uDC41 " + countOfMediaLogged + "\nanonymous ???<i>" + commentTimestamp + "</i>");
            }
            sendDocument.setReplyMarkup(inlineKeyboardMarkup);
            sendDocument.setParseMode("HTML");
            sendDocument.disableNotification();
            logger.info("User id {} sent media message {} ", telegramUserMediaEntity.getTelegramBotUserEntity().getId(), telegramUserMediaEntity);
            return sendDocument;
        }
    }

    @BotRequestMapping(value = "tiktok-ary")
    public PartialBotApiMethod sendRandomlyMediaForYesterdayFromArrow(Update update) {
        Long chatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        Long requestedUserId = Long.valueOf(update.getCallbackQuery().getFrom().getId());
        TelegramBotUserEntity requestedTelegramUser = telegramUserService.getTelegramUser(requestedUserId);
        boolean isRequestedUserAdmin = requestedTelegramUser.isAdmin();
        List<TelegramUserMediaEntity> telegramUserMediaEntityList = telegramMediaService.telegramUserMediaListForYesterday();
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
        heartInlineButton.setText("?????? " + hearCount);
        CallBackData heartCallBackData = new CallBackData("heart", telegramUserId, fileId);
        heartInlineButton.setCallbackData(gson.toJson(heartCallBackData));

        InlineKeyboardButton likeInlineButton = new InlineKeyboardButton();
        likeInlineButton.setText("\uD83D\uDC4D " + likeCount);
        CallBackData likeCallBackData = new CallBackData("like", telegramUserId, fileId);
        likeInlineButton.setCallbackData(gson.toJson(likeCallBackData));

        InlineKeyboardButton dislikeInlineButton = new InlineKeyboardButton();
        dislikeInlineButton.setText("\uD83D\uDC4E?????? " + dislikeCount);
        CallBackData dislikeCallBackData = new CallBackData("dislike", telegramUserId, fileId);
        dislikeInlineButton.setCallbackData(gson.toJson(dislikeCallBackData));

        inlineKeyboardButtonsFirstRow.add(heartInlineButton);
        inlineKeyboardButtonsFirstRow.add(likeInlineButton);
        inlineKeyboardButtonsFirstRow.add(dislikeInlineButton);

        inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);

        String pageFrom = "newForYesterday";
        UserContextHolder.currentContext().setFromPageData(pageFrom);
        UserContextHolder.currentContext().setOffs(choosen);
        inlineKeyboardButtons.add(BotMessageHelperUtil.arrowButtonsBuild(pageFrom, choosen, telegramUserId, fileId));

        InlineKeyboardButton commentInlineButton = new InlineKeyboardButton();
        commentInlineButton.setText("\uD83D\uDCDD ???????????????? (\uD83D\uDCE8" + discussCount + ")") ;
        commentInlineButton.setCallbackData(gson.toJson(new CallBackData("comment", telegramUserId, fileId)));
        inlineKeyboardButtonsCommentRow.add(commentInlineButton);
        inlineKeyboardButtons.add(inlineKeyboardButtonsCommentRow);

        if (!isAnon){
            InlineKeyboardButton profileInfoInlineButton = new InlineKeyboardButton();
            profileInfoInlineButton.setText("\uD83D\uDCF2 ?????????? ????????????????????????") ;
            profileInfoInlineButton.setCallbackData(gson.toJson(new CallBackData("more", telegramUserId, fileId)));
            inlineKeyboardButtonsProfileDataRow.add(profileInfoInlineButton);
            inlineKeyboardButtons.add(inlineKeyboardButtonsProfileDataRow);
        }
        if (isRequestedUserAdmin){
            InlineKeyboardButton deleteInlineButton = new InlineKeyboardButton();
            deleteInlineButton.setText("??? ??????????????");
            deleteInlineButton.setCallbackData(gson.toJson(new CallBackData("delete", telegramUserId, fileId)));
            inlineKeyboardButtonsDeleteRow.add(deleteInlineButton);
            inlineKeyboardButtons.add(inlineKeyboardButtonsDeleteRow);
        }

        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);

        String fileIdx = telegramUserMediaEntity.getFileId();
        String caption = null != telegramUserMediaEntity.getMessage() ? telegramUserMediaEntity.getMessage() : "&lt;???????????????? ??????????????????????&gt;";
        String commentTimestamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(telegramUserMediaEntity.getAudWhenCreate().getTime() + TimeUnit.HOURS.toMillis(3));
        if (telegramUserMediaEntity.getFileType() == 1){
            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setChatId(chatId);
            sendPhoto.setPhoto(fileIdx);
            if (!isAnon){
                sendPhoto.setCaption(caption + "\n\n\uD83D\uDC41 " + countOfMediaLogged + "\n???????????????????? ???? ???????????????????????? <a href=\"tg://user?id=" + telegramBotUserEntity.getTelegramId() + "\">" + telegramUser + "</a> ???<i>" + commentTimestamp + "</i>");
            }else {
                sendPhoto.setCaption(caption + "\n\n\uD83D\uDC41 " + countOfMediaLogged + "\nanonymous ???<i>" + commentTimestamp + "</i>");
            }
            sendPhoto.setReplyMarkup(inlineKeyboardMarkup);
            sendPhoto.setParseMode("HTML");
            sendPhoto.disableNotification();
            logger.info("User: {}", requestedTelegramUser);
            logger.info("User id {} sent media message {} ", telegramUserMediaEntity.getTelegramBotUserEntity().getId(), telegramUserMediaEntity);
            return sendPhoto;
        }else if(telegramUserMediaEntity.getHeight() > 0 && telegramUserMediaEntity.getWidth() > 0){
            SendVideo sendVideo = new SendVideo();
            sendVideo.setChatId(chatId);
            sendVideo.setVideo(fileIdx);
            if (!isAnon){
                sendVideo.setCaption(caption + "\n\n\uD83D\uDC41 " + countOfMediaLogged + "\n?????????? ???? ???????????????????????? <a href=\"tg://user?id=" + telegramBotUserEntity.getTelegramId() + "\">" + telegramUser + "</a> ???<i>" + commentTimestamp + "</i>");
            }else {
                sendVideo.setCaption(caption + "\n\n\uD83D\uDC41 " + countOfMediaLogged + "\nanonymous");
            }
            sendVideo.setReplyMarkup(inlineKeyboardMarkup);
            sendVideo.setParseMode("HTML");
            sendVideo.disableNotification();
            logger.info("User id {} sent media message {} ", telegramUserMediaEntity.getTelegramBotUserEntity().getId(), telegramUserMediaEntity);
            return sendVideo;
        }else {
            SendDocument sendDocument = new SendDocument();
            sendDocument.setChatId(chatId);
            sendDocument.setDocument(fileIdx);
            if (!isAnon){
                sendDocument.setCaption("\n\n\uD83D\uDC41 " + countOfMediaLogged + "\n?????????? ???? ???????????????????????? <a href=\"tg://user?id=" + telegramBotUserEntity.getTelegramId() + "\">" + telegramUser + "</a> ???<i>" + commentTimestamp + "</i>");
            }else {
                sendDocument.setCaption("\n\n\uD83D\uDC41 " + countOfMediaLogged + "\nanonymous ???<i>" + commentTimestamp + "</i>");
            }
            sendDocument.setReplyMarkup(inlineKeyboardMarkup);
            sendDocument.setParseMode("HTML");
            sendDocument.disableNotification();
            logger.info("User id {} sent media message {} ", telegramUserMediaEntity.getTelegramBotUserEntity().getId(), telegramUserMediaEntity);
            return sendDocument;
        }
    }

    @BotRequestMapping(value = {"tiktok-\uD83D\uDCF2????????", "tiktok-\uD83D\uDCF2Photo", "tiktok-\uD83D\uDCF2??????"})
    public SendPhoto sendRandomlyMedia(Update update) {
        Long chatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        Long requestedUserId = Long.valueOf(update.getMessage().getFrom().getId());
        TelegramBotUserEntity requestedTelegramUser = telegramUserService.getTelegramUser(requestedUserId);
        boolean isRequestedUserAdmin = requestedTelegramUser.isAdmin();
        List<TelegramUserMediaEntity> telegramUserMediaEntityList = telegramMediaService.telegramUserMediaList(1);

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
        heartInlineButton.setText("?????? " + hearCount);
        CallBackData heartCallBackData = new CallBackData("heart", telegramUserId, fileId);
        heartInlineButton.setCallbackData(gson.toJson(heartCallBackData));

        InlineKeyboardButton likeInlineButton = new InlineKeyboardButton();
        likeInlineButton.setText("\uD83D\uDC4D " + likeCount);
        CallBackData likeCallBackData = new CallBackData("like", telegramUserId, fileId);
        likeInlineButton.setCallbackData(gson.toJson(likeCallBackData));

        InlineKeyboardButton dislikeInlineButton = new InlineKeyboardButton();
        dislikeInlineButton.setText("\uD83D\uDC4E?????? " + dislikeCount);
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
        commentInlineButton.setText("\uD83D\uDCDD ???????????????? (\uD83D\uDCE8" + discussCount + ")") ;
        commentInlineButton.setCallbackData(gson.toJson(new CallBackData("comment", telegramUserId, fileId)));
        inlineKeyboardButtonsCommentRow.add(commentInlineButton);
        inlineKeyboardButtons.add(inlineKeyboardButtonsCommentRow);

        if (!isAnon){
            InlineKeyboardButton profileInfoInlineButton = new InlineKeyboardButton();
            profileInfoInlineButton.setText("\uD83D\uDCF2 ?????????? ????????????????????????") ;
            profileInfoInlineButton.setCallbackData(gson.toJson(new CallBackData("more", telegramUserId, fileId)));
            inlineKeyboardButtonsProfileDataRow.add(profileInfoInlineButton);
            inlineKeyboardButtons.add(inlineKeyboardButtonsProfileDataRow);
        }

        if (isRequestedUserAdmin){
            InlineKeyboardButton deleteInlineButton = new InlineKeyboardButton();
            deleteInlineButton.setText("??? ??????????????");
            deleteInlineButton.setCallbackData(gson.toJson(new CallBackData("delete", telegramUserId, fileId)));
            inlineKeyboardButtonsDeleteRow.add(deleteInlineButton);
            inlineKeyboardButtons.add(inlineKeyboardButtonsDeleteRow);
        }

        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);

        String fileIdx = telegramUserMediaEntity.getFileId();
        String caption = null != telegramUserMediaEntity.getMessage() ? telegramUserMediaEntity.getMessage() : "&lt;???????????????? ??????????????????????&gt;";
        String commentTimestamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(telegramUserMediaEntity.getAudWhenCreate().getTime() + TimeUnit.HOURS.toMillis(3));
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(fileIdx);
        if (!isAnon){
            sendPhoto.setCaption(caption + "\n\n\uD83D\uDC41 " + countOfMediaLogged + "\n???????????????????? ???? ???????????????????????? <a href=\"tg://user?id=" + telegramBotUserEntity.getTelegramId() + "\">" + telegramUser + "</a> ???<i>" + commentTimestamp + "</i>");
        }else {
            sendPhoto.setCaption(caption + "\n\n\uD83D\uDC41 " + countOfMediaLogged + "\nanonymous ???<i>" + commentTimestamp + "</i>");
        }
        sendPhoto.setReplyMarkup(inlineKeyboardMarkup);
        sendPhoto.setParseMode("HTML");
        sendPhoto.disableNotification();
        logger.info("User: {}", requestedTelegramUser);
        logger.info("User id {} sent media message {} ", telegramUserMediaEntity.getTelegramBotUserEntity().getId(), telegramUserMediaEntity);
        return sendPhoto;
    }

    @BotRequestMapping(value = "tiktok-/photo")
    public SendPhoto sendRandomlyMediaFromCommand(Update update) {
        Long chatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        List<TelegramUserMediaEntity> telegramUserMediaEntityList = telegramMediaService.telegramUserMediaList(1);
        Long requestedUserId = Long.valueOf(update.getMessage().getFrom().getId());
        TelegramBotUserEntity requestedTelegramUser = telegramUserService.getTelegramUser(requestedUserId);
        boolean isRequestedUserAdmin = requestedTelegramUser.isAdmin();

        int old = (int) UserContextHolder.currentContext().getPartId() >= telegramUserMediaEntityList.size() ? telegramUserMediaEntityList.size() - 1 : (int) UserContextHolder.currentContext().getPartId();
//        Collections.shuffle(telegramUserMediaEntityList);
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
        heartInlineButton.setText("?????? " + hearCount);
        CallBackData heartCallBackData = new CallBackData("heart", telegramUserId, fileId);
        heartInlineButton.setCallbackData(gson.toJson(heartCallBackData));

        InlineKeyboardButton likeInlineButton = new InlineKeyboardButton();
        likeInlineButton.setText("\uD83D\uDC4D " + likeCount);
        CallBackData likeCallBackData = new CallBackData("like", telegramUserId, fileId);
        likeInlineButton.setCallbackData(gson.toJson(likeCallBackData));

        InlineKeyboardButton dislikeInlineButton = new InlineKeyboardButton();
        dislikeInlineButton.setText("\uD83D\uDC4E?????? " + dislikeCount);
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
        commentInlineButton.setText("\uD83D\uDCDD ???????????????? (\uD83D\uDCE8" + discussCount + ")") ;
        commentInlineButton.setCallbackData(gson.toJson(new CallBackData("comment", telegramUserId, fileId)));
        inlineKeyboardButtonsCommentRow.add(commentInlineButton);
        inlineKeyboardButtons.add(inlineKeyboardButtonsCommentRow);

        if (!isAnon){
            InlineKeyboardButton profileInfoInlineButton = new InlineKeyboardButton();
            profileInfoInlineButton.setText("\uD83D\uDCF2 ?????????? ????????????????????????") ;
            profileInfoInlineButton.setCallbackData(gson.toJson(new CallBackData("more", telegramUserId, fileId)));
            inlineKeyboardButtonsProfileDataRow.add(profileInfoInlineButton);
            inlineKeyboardButtons.add(inlineKeyboardButtonsProfileDataRow);
        }
        if (isRequestedUserAdmin){
            InlineKeyboardButton deleteInlineButton = new InlineKeyboardButton();
            deleteInlineButton.setText("??? ??????????????");
            deleteInlineButton.setCallbackData(gson.toJson(new CallBackData("delete", telegramUserId, fileId)));
            inlineKeyboardButtonsDeleteRow.add(deleteInlineButton);
            inlineKeyboardButtons.add(inlineKeyboardButtonsDeleteRow);
        }

        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);

        String fileIdx = telegramUserMediaEntity.getFileId();
        String caption = null != telegramUserMediaEntity.getMessage() ? telegramUserMediaEntity.getMessage() : "&lt;???????????????? ??????????????????????&gt;";
        String commentTimestamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(telegramUserMediaEntity.getAudWhenCreate().getTime() + TimeUnit.HOURS.toMillis(3));
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(fileIdx);
        if (!isAnon){
            sendPhoto.setCaption(caption + "\n\n\uD83D\uDC41 " + countOfMediaLogged + "\n???????????????????? ???? ???????????????????????? <a href=\"tg://user?id=" + telegramBotUserEntity.getTelegramId() + "\">" + telegramUser + "</a> ???<i>" + commentTimestamp + "</i>");
        }else {
            sendPhoto.setCaption(caption + "\n\n\uD83D\uDC41 " + countOfMediaLogged + "\nanonymous ???<i>" + commentTimestamp + "</i>");
        }
        sendPhoto.setReplyMarkup(inlineKeyboardMarkup);
        sendPhoto.setParseMode("HTML");
        sendPhoto.disableNotification();
        logger.info("User: {}", requestedTelegramUser);
        logger.info("User id {} sent media message {} ", telegramUserMediaEntity.getTelegramBotUserEntity().getId(), telegramUserMediaEntity);
        return sendPhoto;
    }


    @BotRequestMapping(value = "tiktok-arf")
    public SendPhoto sendRandomlyMediaFromArrows(Update update) {
        Long chatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        Long requestedUserId = Long.valueOf(update.getCallbackQuery().getFrom().getId());
        TelegramBotUserEntity requestedTelegramUser = telegramUserService.getTelegramUser(requestedUserId);
        boolean isRequestedUserAdmin = requestedTelegramUser.isAdmin();
        List<TelegramUserMediaEntity> telegramUserMediaEntityList = telegramMediaService.telegramUserMediaList(1);
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
        heartInlineButton.setText("?????? " + hearCount);
        CallBackData heartCallBackData = new CallBackData("heart", telegramUserId, fileId);
        heartInlineButton.setCallbackData(gson.toJson(heartCallBackData));

        InlineKeyboardButton likeInlineButton = new InlineKeyboardButton();
        likeInlineButton.setText("\uD83D\uDC4D " + likeCount);
        CallBackData likeCallBackData = new CallBackData("like", telegramUserId, fileId);
        likeInlineButton.setCallbackData(gson.toJson(likeCallBackData));

        InlineKeyboardButton dislikeInlineButton = new InlineKeyboardButton();
        dislikeInlineButton.setText("\uD83D\uDC4E?????? " + dislikeCount);
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
        commentInlineButton.setText("\uD83D\uDCDD ???????????????? (\uD83D\uDCE8" + discussCount + ")") ;
        commentInlineButton.setCallbackData(gson.toJson(new CallBackData("comment", telegramUserId, fileId)));
        inlineKeyboardButtonsCommentRow.add(commentInlineButton);
        inlineKeyboardButtons.add(inlineKeyboardButtonsCommentRow);

        if (!isAnon){
            InlineKeyboardButton profileInfoInlineButton = new InlineKeyboardButton();
            profileInfoInlineButton.setText("\uD83D\uDCF2 ?????????? ????????????????????????") ;
            profileInfoInlineButton.setCallbackData(gson.toJson(new CallBackData("more", telegramUserId, fileId)));
            inlineKeyboardButtonsProfileDataRow.add(profileInfoInlineButton);
            inlineKeyboardButtons.add(inlineKeyboardButtonsProfileDataRow);
        }

        if (isRequestedUserAdmin){
            InlineKeyboardButton deleteInlineButton = new InlineKeyboardButton();
            deleteInlineButton.setText("??? ??????????????");
            deleteInlineButton.setCallbackData(gson.toJson(new CallBackData("delete", telegramUserId, fileId)));
            inlineKeyboardButtonsDeleteRow.add(deleteInlineButton);
            inlineKeyboardButtons.add(inlineKeyboardButtonsDeleteRow);
        }

        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);

        String fileIdx = telegramUserMediaEntity.getFileId();
        String caption = null != telegramUserMediaEntity.getMessage() ? telegramUserMediaEntity.getMessage() : "&lt;???????????????? ??????????????????????&gt;";
        String commentTimestamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(telegramUserMediaEntity.getAudWhenCreate().getTime() + TimeUnit.HOURS.toMillis(3));
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(fileIdx);
        if (!isAnon){
            sendPhoto.setCaption(caption + "\n\n\uD83D\uDC41 " + countOfMediaLogged + "\n???????????????????? ???? ???????????????????????? <a href=\"tg://user?id=" + telegramBotUserEntity.getTelegramId() + "\">" + telegramUser + "</a> ???<i>" + commentTimestamp + "</i>");
        }else {
            sendPhoto.setCaption(caption + "\n\n\uD83D\uDC41 " + countOfMediaLogged + "\nanonymous ???<i>" + commentTimestamp + "</i>");
        }
        sendPhoto.setReplyMarkup(inlineKeyboardMarkup);
        sendPhoto.setParseMode("HTML");
        sendPhoto.disableNotification();
        logger.info("User: {}", requestedTelegramUser);
        logger.info("User id {} sent media message {} ", telegramUserMediaEntity.getTelegramBotUserEntity().getId(), telegramUserMediaEntity);
        return sendPhoto;
    }

    @BotRequestMapping(value = {"tiktok-\uD83D\uDCF2??????????", "tiktok-\uD83D\uDCF2Video", "tiktok-\uD83D\uDCF2??????"})
    public PartialBotApiMethod sendRandomlyVideo(Update update) {
        Long chatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        Long requestedUserId = Long.valueOf(update.getMessage().getFrom().getId());
        TelegramBotUserEntity requestedTelegramUser = telegramUserService.getTelegramUser(requestedUserId);
        boolean isRequestedUserAdmin = requestedTelegramUser.isAdmin();

        List<TelegramUserMediaEntity> telegramUserMediaEntityList = telegramMediaService.telegramUserMediaList(2);

//        Collections.shuffle(telegramUserMediaEntityList);

        int old = (int) UserContextHolder.currentContext().getPartId() >= telegramUserMediaEntityList.size() ? telegramUserMediaEntityList.size() - 1 : (int) UserContextHolder.currentContext().getPartId();
//        Collections.shuffle(telegramUserMediaEntityList);
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
        heartInlineButton.setText("????????? " + hearCount);
        heartInlineButton.setCallbackData(gson.toJson(new CallBackData("heart", telegramUserId, fileId)));

        InlineKeyboardButton likeInlineButton = new InlineKeyboardButton();
        likeInlineButton.setText("\uD83D\uDC4D " + likeCount);
        likeInlineButton.setCallbackData(gson.toJson(new CallBackData("like", telegramUserId, fileId)));

        InlineKeyboardButton dislikeInlineButton = new InlineKeyboardButton();
        dislikeInlineButton.setText("\uD83D\uDC4E?????? " + dislikeCount);
        dislikeInlineButton.setCallbackData(gson.toJson(new CallBackData("dislike", telegramUserId, fileId)));

        inlineKeyboardButtonsFirstRow.add(heartInlineButton);
        inlineKeyboardButtonsFirstRow.add(likeInlineButton);
        inlineKeyboardButtonsFirstRow.add(dislikeInlineButton);
        inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);

        String pageFrom = "video";
        UserContextHolder.currentContext().setFromPageData(pageFrom);
        UserContextHolder.currentContext().setOffs(choosen);
        inlineKeyboardButtons.add(BotMessageHelperUtil.arrowButtonsBuild(pageFrom, choosen, telegramUserId, fileId));

        InlineKeyboardButton commentInlineButton = new InlineKeyboardButton();
        commentInlineButton.setText("\uD83D\uDCDD ???????????????? (\uD83D\uDCE8" + discussCount + ")") ;
        commentInlineButton.setCallbackData(gson.toJson(new CallBackData("comment", telegramUserId, fileId)));
        inlineKeyboardButtonsCommentRow.add(commentInlineButton);
        inlineKeyboardButtons.add(inlineKeyboardButtonsCommentRow);

        if (!isAnon){
            InlineKeyboardButton profileInfoInlineButton = new InlineKeyboardButton();
            profileInfoInlineButton.setText("\uD83D\uDCF2 ?????????? ????????????????????????") ;
            profileInfoInlineButton.setCallbackData(gson.toJson(new CallBackData("more", telegramUserId, fileId)));
            inlineKeyboardButtonsProfileDataRow.add(profileInfoInlineButton);
            inlineKeyboardButtons.add(inlineKeyboardButtonsProfileDataRow);
        }

        if (isRequestedUserAdmin){
            InlineKeyboardButton deleteInlineButton = new InlineKeyboardButton();
            deleteInlineButton.setText("??? ??????????????");
            deleteInlineButton.setCallbackData(gson.toJson(new CallBackData("delete", telegramUserId, fileId)));
            inlineKeyboardButtonsDeleteRow.add(deleteInlineButton);
            inlineKeyboardButtons.add(inlineKeyboardButtonsDeleteRow);
        }

        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);
        logger.info("User: {}", requestedTelegramUser);

        String caption = null != telegramUserMediaEntity.getMessage() ? telegramUserMediaEntity.getMessage() : "&lt;???????????????? ??????????????????????&gt;";
        String commentTimestamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(telegramUserMediaEntity.getAudWhenCreate().getTime() + TimeUnit.HOURS.toMillis(3));
        String fileIdx = telegramUserMediaEntity.getFileId();
        if (telegramUserMediaEntity.getHeight() > 0 && telegramUserMediaEntity.getWidth() > 0) {
            SendVideo sendVideo = new SendVideo();
            sendVideo.setChatId(chatId);
            sendVideo.setVideo(fileIdx);
            if (!isAnon){
                sendVideo.setCaption(caption + "\n\n\uD83D\uDC41 " + countOfMediaLogged + "\n?????????? ???? ???????????????????????? <a href=\"tg://user?id=" + telegramBotUserEntity.getTelegramId() + "\">" + telegramUser + "</a> ???<i>" + commentTimestamp + "</i>");
            }else {
                sendVideo.setCaption(caption + "\n\n\uD83D\uDC41 " + countOfMediaLogged + "\nanonymous ???<i>" + commentTimestamp + "</i>");
            }
            sendVideo.setReplyMarkup(inlineKeyboardMarkup);
            sendVideo.setParseMode("HTML");
            sendVideo.disableNotification();
            logger.info("User id {} sent media message {} ", telegramUserMediaEntity.getTelegramBotUserEntity().getId(), telegramUserMediaEntity);
            return sendVideo;
        }else {
            SendDocument sendDocument = new SendDocument();
            sendDocument.setChatId(chatId);
            sendDocument.setDocument(fileIdx);
            if (!isAnon){
                sendDocument.setCaption("\n\n\uD83D\uDC41 " + countOfMediaLogged + "\n?????????? ???? ???????????????????????? <a href=\"tg://user?id=" + telegramBotUserEntity.getTelegramId() + "\">" + telegramUser + "</a> ???<i>" + commentTimestamp + "</i>");
            }else {
                sendDocument.setCaption("\n\n\uD83D\uDC41 " + countOfMediaLogged + "\nanonymous ???<i>" + commentTimestamp + "</i>");
            }
            sendDocument.setReplyMarkup(inlineKeyboardMarkup);
            sendDocument.setParseMode("HTML");
            sendDocument.disableNotification();
            logger.info("User id {} sent media message {} ", telegramUserMediaEntity.getTelegramBotUserEntity().getId(), telegramUserMediaEntity);
            return sendDocument;
        }
    }

    @BotRequestMapping(value = "tiktok-/video")
    public PartialBotApiMethod sendRandomlyVideoFromCommand(Update update) {
        Long chatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        Long requestedUserId = Long.valueOf(update.getMessage().getFrom().getId());
        TelegramBotUserEntity requestedTelegramUser = telegramUserService.getTelegramUser(requestedUserId);
        boolean isRequestedUserAdmin = requestedTelegramUser.isAdmin();

        List<TelegramUserMediaEntity> telegramUserMediaEntityList = telegramMediaService.telegramUserMediaList(2);

//        Collections.shuffle(telegramUserMediaEntityList);

        int old = (int) UserContextHolder.currentContext().getPartId() >= telegramUserMediaEntityList.size() ? telegramUserMediaEntityList.size() - 1 : (int) UserContextHolder.currentContext().getPartId();
//        Collections.shuffle(telegramUserMediaEntityList);
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
        heartInlineButton.setText("????????? " + hearCount);
        heartInlineButton.setCallbackData(gson.toJson(new CallBackData("heart", telegramUserId, fileId)));

        InlineKeyboardButton likeInlineButton = new InlineKeyboardButton();
        likeInlineButton.setText("\uD83D\uDC4D " + likeCount);
        likeInlineButton.setCallbackData(gson.toJson(new CallBackData("like", telegramUserId, fileId)));

        InlineKeyboardButton dislikeInlineButton = new InlineKeyboardButton();
        dislikeInlineButton.setText("\uD83D\uDC4E?????? " + dislikeCount);
        dislikeInlineButton.setCallbackData(gson.toJson(new CallBackData("dislike", telegramUserId, fileId)));

        inlineKeyboardButtonsFirstRow.add(heartInlineButton);
        inlineKeyboardButtonsFirstRow.add(likeInlineButton);
        inlineKeyboardButtonsFirstRow.add(dislikeInlineButton);
        inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);

        String pageFrom = "video";
        UserContextHolder.currentContext().setFromPageData(pageFrom);
        UserContextHolder.currentContext().setOffs(choosen);
        inlineKeyboardButtons.add(BotMessageHelperUtil.arrowButtonsBuild(pageFrom, choosen, telegramUserId, fileId));

        InlineKeyboardButton commentInlineButton = new InlineKeyboardButton();
        commentInlineButton.setText("\uD83D\uDCDD ???????????????? (\uD83D\uDCE8" + discussCount + ")") ;
        commentInlineButton.setCallbackData(gson.toJson(new CallBackData("comment", telegramUserId, fileId)));
        inlineKeyboardButtonsCommentRow.add(commentInlineButton);
        inlineKeyboardButtons.add(inlineKeyboardButtonsCommentRow);

        if (!isAnon){
            InlineKeyboardButton profileInfoInlineButton = new InlineKeyboardButton();
            profileInfoInlineButton.setText("\uD83D\uDCF2 ?????????? ????????????????????????") ;
            profileInfoInlineButton.setCallbackData(gson.toJson(new CallBackData("more", telegramUserId, fileId)));
            inlineKeyboardButtonsProfileDataRow.add(profileInfoInlineButton);
            inlineKeyboardButtons.add(inlineKeyboardButtonsProfileDataRow);
        }

        if (isRequestedUserAdmin){
            InlineKeyboardButton deleteInlineButton = new InlineKeyboardButton();
            deleteInlineButton.setText("??? ??????????????");
            deleteInlineButton.setCallbackData(gson.toJson(new CallBackData("delete", telegramUserId, fileId)));
            inlineKeyboardButtonsDeleteRow.add(deleteInlineButton);
            inlineKeyboardButtons.add(inlineKeyboardButtonsDeleteRow);
        }

        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);

        String caption = null != telegramUserMediaEntity.getMessage() ? telegramUserMediaEntity.getMessage() : "&lt;???????????????? ??????????????????????&gt;";
        String commentTimestamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(telegramUserMediaEntity.getAudWhenCreate().getTime() + TimeUnit.HOURS.toMillis(3));
        String fileIdx = telegramUserMediaEntity.getFileId();
        logger.info("User: {}", requestedTelegramUser);
        if (telegramUserMediaEntity.getHeight() > 0 && telegramUserMediaEntity.getWidth() > 0) {
            SendVideo sendVideo = new SendVideo();
            sendVideo.setChatId(chatId);
            sendVideo.setVideo(fileIdx);
            if (!isAnon){
                sendVideo.setCaption(caption + "\n\n\uD83D\uDC41 " + countOfMediaLogged + "\n?????????? ???? ???????????????????????? <a href=\"tg://user?id=" + telegramBotUserEntity.getTelegramId() + "\">" + telegramUser + "</a> ???<i>\" + commentTimestamp + \"</i>\"");
            }else {
                sendVideo.setCaption(caption + "\n\n\uD83D\uDC41 " + countOfMediaLogged + "\nanonymous ???<i>" + commentTimestamp + "</i>");
            }
            sendVideo.setReplyMarkup(inlineKeyboardMarkup);
            sendVideo.setParseMode("HTML");
            sendVideo.disableNotification();
            logger.info("User id {} sent media message {} ", telegramUserMediaEntity.getTelegramBotUserEntity().getId(), telegramUserMediaEntity);
            return sendVideo;
        }else {
            SendDocument sendDocument = new SendDocument();
            sendDocument.setChatId(chatId);
            sendDocument.setDocument(fileIdx);
            if (!isAnon){
                sendDocument.setCaption("\n\n\uD83D\uDC41 " + countOfMediaLogged + "\n?????????? ???? ???????????????????????? <a href=\"tg://user?id=" + telegramBotUserEntity.getTelegramId() + "\">" + telegramUser + "</a> ???<i>" + commentTimestamp + "</i>");
            }else {
                sendDocument.setCaption("\n\n\uD83D\uDC41 " + countOfMediaLogged + "\nanonymous ???<i>" + commentTimestamp + "</i>");
            }
            sendDocument.setReplyMarkup(inlineKeyboardMarkup);
            sendDocument.setParseMode("HTML");
            sendDocument.disableNotification();
            logger.info("User id {} sent media message {} ", telegramUserMediaEntity.getTelegramBotUserEntity().getId(), telegramUserMediaEntity);
            return sendDocument;
        }
    }


    @BotRequestMapping(value = "tiktok-arv")
    public PartialBotApiMethod sendRandomlyVideoFromArrows(Update update) {
        Long chatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        Long requestedUserId = Long.valueOf(update.getCallbackQuery().getFrom().getId());
        TelegramBotUserEntity requestedTelegramUser = telegramUserService.getTelegramUser(requestedUserId);
        boolean isRequestedUserAdmin = requestedTelegramUser.isAdmin();

        List<TelegramUserMediaEntity> telegramUserMediaEntityList = telegramMediaService.telegramUserMediaList(2);
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
        heartInlineButton.setText("????????? " + hearCount);
        heartInlineButton.setCallbackData(gson.toJson(new CallBackData("heart", telegramUserId, fileId)));

        InlineKeyboardButton likeInlineButton = new InlineKeyboardButton();
        likeInlineButton.setText("\uD83D\uDC4D " + likeCount);
        likeInlineButton.setCallbackData(gson.toJson(new CallBackData("like", telegramUserId, fileId)));

        InlineKeyboardButton dislikeInlineButton = new InlineKeyboardButton();
        dislikeInlineButton.setText("\uD83D\uDC4E?????? " + dislikeCount);
        dislikeInlineButton.setCallbackData(gson.toJson(new CallBackData("dislike", telegramUserId, fileId)));

        inlineKeyboardButtonsFirstRow.add(heartInlineButton);
        inlineKeyboardButtonsFirstRow.add(likeInlineButton);
        inlineKeyboardButtonsFirstRow.add(dislikeInlineButton);
        inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);

        String pageFrom = "video";
        UserContextHolder.currentContext().setFromPageData(pageFrom);
        UserContextHolder.currentContext().setOffs(choosen);
        inlineKeyboardButtons.add(BotMessageHelperUtil.arrowButtonsBuild(pageFrom, choosen, telegramUserId, fileId));

        InlineKeyboardButton commentInlineButton = new InlineKeyboardButton();
        commentInlineButton.setText("\uD83D\uDCDD ???????????????? (\uD83D\uDCE8" + discussCount + ")") ;
        commentInlineButton.setCallbackData(gson.toJson(new CallBackData("comment", telegramUserId, fileId)));
        inlineKeyboardButtonsCommentRow.add(commentInlineButton);
        inlineKeyboardButtons.add(inlineKeyboardButtonsCommentRow);

        if (!isAnon){
            InlineKeyboardButton profileInfoInlineButton = new InlineKeyboardButton();
            profileInfoInlineButton.setText("\uD83D\uDCF2 ?????????? ????????????????????????") ;
            profileInfoInlineButton.setCallbackData(gson.toJson(new CallBackData("more", telegramUserId, fileId)));
            inlineKeyboardButtonsProfileDataRow.add(profileInfoInlineButton);
            inlineKeyboardButtons.add(inlineKeyboardButtonsProfileDataRow);
        }

        if (isRequestedUserAdmin){
            InlineKeyboardButton deleteInlineButton = new InlineKeyboardButton();
            deleteInlineButton.setText("??? ??????????????");
            deleteInlineButton.setCallbackData(gson.toJson(new CallBackData("delete", telegramUserId, fileId)));
            inlineKeyboardButtonsDeleteRow.add(deleteInlineButton);
            inlineKeyboardButtons.add(inlineKeyboardButtonsDeleteRow);
        }

        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);
        logger.info("User: {}", requestedTelegramUser);

        String caption = null != telegramUserMediaEntity.getMessage() ? telegramUserMediaEntity.getMessage() : "&lt;???????????????? ??????????????????????&gt;";
        String commentTimestamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(telegramUserMediaEntity.getAudWhenCreate().getTime() + TimeUnit.HOURS.toMillis(3));
        String fileIdx = telegramUserMediaEntity.getFileId();
        if (telegramUserMediaEntity.getHeight() > 0 && telegramUserMediaEntity.getWidth() > 0) {
            SendVideo sendVideo = new SendVideo();
            sendVideo.setChatId(chatId);
            sendVideo.setVideo(fileIdx);
            if (!isAnon){
                sendVideo.setCaption(caption + "\n\n\uD83D\uDC41 " + countOfMediaLogged + "\n?????????? ???? ???????????????????????? <a href=\"tg://user?id=" + telegramBotUserEntity.getTelegramId() + "\">" + telegramUser + "</a> ???<i>" + commentTimestamp + "</i>");
            }else {
                sendVideo.setCaption(caption + "\n\n\uD83D\uDC41 " + countOfMediaLogged + "\nanonymous ???<i>" + commentTimestamp + "</i>");
            }
            sendVideo.setReplyMarkup(inlineKeyboardMarkup);
            sendVideo.setParseMode("HTML");
            sendVideo.disableNotification();
            logger.info("User id {} sent media message {} ", telegramUserMediaEntity.getTelegramBotUserEntity().getId(), telegramUserMediaEntity);
            return sendVideo;
        }else {
            SendDocument sendDocument = new SendDocument();
            sendDocument.setChatId(chatId);
            sendDocument.setDocument(fileIdx);
            if (!isAnon){
                sendDocument.setCaption("\n\n\uD83D\uDC41 " + countOfMediaLogged + "\n?????????? ???? ???????????????????????? <a href=\"tg://user?id=" + telegramBotUserEntity.getTelegramId() + "\">" + telegramUser + "</a> ???<i>" + commentTimestamp + "</i>");
            }else {
                sendDocument.setCaption("\n\n\uD83D\uDC41 " + countOfMediaLogged + "\nanonymous ???<i>" + commentTimestamp + "</i>");
            }
            sendDocument.setReplyMarkup(inlineKeyboardMarkup);
            sendDocument.setParseMode("HTML");
            sendDocument.disableNotification();
            logger.info("User id {} sent media message {} ", telegramUserMediaEntity.getTelegramBotUserEntity().getId(), telegramUserMediaEntity);
            return sendDocument;
        }
    }
}
