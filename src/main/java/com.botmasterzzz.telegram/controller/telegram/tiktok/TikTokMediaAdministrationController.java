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
import com.botmasterzzz.telegram.dto.OwnerStatisticDTO;
import com.botmasterzzz.telegram.entity.TelegramBotUserEntity;
import com.botmasterzzz.telegram.entity.TelegramUserMediaEntity;
import com.botmasterzzz.telegram.service.DatabaseService;
import com.botmasterzzz.telegram.service.TelegramBotStatisticService;
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
    private TelegramBotStatisticService telegramBotStatisticService;

    @Autowired
    private TelegramUserService telegramUserService;

    @Autowired
    private DatabaseService databaseService;

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
        restoreInlineButton.setText("?????? ????????????????????????");
        restoreInlineButton.setCallbackData(gson.toJson(new CallBackData("restore", telegramUserId, fileId)));
        inlineKeyboardButtonsFirstRow.add(restoreInlineButton);
        logger.info("User id {} requested to delete a media resource: {}", requestedUserId, telegramUserMediaEntity);
//        }else {
//            InlineKeyboardButton restoreInlineButton = new InlineKeyboardButton();
//            restoreInlineButton.setText("?????? ???????????????????????? ????????");
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
        restoreInlineButton.setText("??? ??????????????");
        restoreInlineButton.setCallbackData(gson.toJson(new CallBackData("delete", telegramUserId, fileId)));
        inlineKeyboardButtonsFirstRow.add(restoreInlineButton);
        logger.info("User id {} requested to restore a media resource: {}", requestedUserId, telegramUserMediaEntity);
//        }else {
//            InlineKeyboardButton restoreInlineButton = new InlineKeyboardButton();
//            restoreInlineButton.setText("?????? ???????????????????????? ????????");
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
    public List<SendMessage> mailingSend(Update update) {
        Long requestedUserId = Long.valueOf(update.getMessage().getFrom().getId());
        TelegramBotUserEntity requestedTelegramUser = telegramUserService.getTelegramUser(requestedUserId);
        boolean isRequestedUserAdmin = requestedTelegramUser.isAdmin();
        List<SendMessage> mailingData = new ArrayList<>();
        if (!isRequestedUserAdmin) {
            SendMessage restrictedWarningMessage = new SendMessage();
            restrictedWarningMessage.setChatId(update.getMessage().getChatId());
            restrictedWarningMessage.setText("?????? ???????????????????????? ????????");
            restrictedWarningMessage.enableHtml(true);
            logger.info("User id {} restricted to MAKE A MAILING", requestedTelegramUser);
            mailingData.add(restrictedWarningMessage);
            return mailingData;
        }
        logger.info("User id {} requested to MAKE a MAILING", requestedTelegramUser);
        List<TelegramBotUserEntity> telegramBotUserEntityList = telegramUserService.getTelegramUserList();
        for (TelegramBotUserEntity telegramBotUserEntity : telegramBotUserEntityList) {
            Long telegramUserId = telegramBotUserEntity.getTelegramId();
            List<OwnerStatisticDTO> ownerStatisticDTOList = telegramMediaService.getUsersActivityStatistic(telegramUserId);
            Long countOfLoggedToUser = databaseService.getCountOfLoggedToUser(telegramUserId);
            Long countOfLoggedToUsersMedia = databaseService.getUsersCountOfMediaLog(telegramUserId);

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\uD83D\uDD25<b>????????????, ?? ?????? ???????????????? ??????????????!</b>\n");
            stringBuilder.append("?????? ?????? ???? ?????????????????? ????????????????????.\n");
            stringBuilder.append("\n");
            stringBuilder.append("?????? ???????????????? ?? ???????? ?????????????????? ????????????????????:\n");
            stringBuilder.append("-?????? ?????????????????? ????????????, ?????????????????? ?????????? ???????????? ?????? <b>??????????</b>, ?????? ?????????????? ???? ?????????????? ?????????? ?????????????? ???? ?????????????????? ???????? ?? ?????? ???????? ???????????????????? ?????????? ???????????? ?????????? ??????????????????\n");
            stringBuilder.append("\n");
            stringBuilder.append("<b>???????? ???????????????????? ???? ????????????/??????????????????</b>\uD83E\uDDFE \n");
            stringBuilder.append("???????? ?????????? ????????????????????:\n");
            stringBuilder.append("????????????????????????????????????\n");
            if (ownerStatisticDTOList.isEmpty()) {
                stringBuilder.append("<b>???????????????????? ???? ?????? ??????????????????????</b>\n");
            }
            for (OwnerStatisticDTO ownerStatisticDTO : ownerStatisticDTOList) {
                stringBuilder.append("<b>")
                        .append(ownerStatisticDTO.getTouchType()).append("</b>: ")
                        .append(ownerStatisticDTO.getCountOfTouch()).append("\n");
            }
            stringBuilder.append("????????????????????????????????????\n");
            ownerStatisticDTOList = telegramMediaService.getSelfActivityStatistic(telegramUserId);
            stringBuilder.append("???? ?????? ?????????? ?????? ??????????????????:\n");
            stringBuilder.append("????????????????????????????????????\n");
            if (ownerStatisticDTOList.isEmpty()) {
                stringBuilder.append("<b>???????????????????? ???? ?????? ??????????????????????</b>\n");
            }
            for (OwnerStatisticDTO ownerStatisticDTO : ownerStatisticDTOList) {
                stringBuilder.append("<b>")
                        .append(ownerStatisticDTO.getTouchType()).append("</b>: ")
                        .append(ownerStatisticDTO.getCountOfTouch()).append("\n");
            }
            stringBuilder.append("????????????????????????????????????\n");
            stringBuilder.append("???????? ?????????????????????? <b>").append(countOfLoggedToUser).append("</b> ????????????\n");
            stringBuilder.append("???????? ?????????? ?????????????????????? <b>").append(countOfLoggedToUsersMedia).append("</b> ??????\n");

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(telegramUserId);
            sendMessage.setText(stringBuilder.toString());
            sendMessage.enableHtml(true);
            sendMessage.setParseMode("HTML");

            mailingData.add(sendMessage);
            logger.info("Message {} to user {} MAILING sent", stringBuilder.toString(), telegramBotUserEntity);
            telegramBotStatisticService.telegramStatisticAdd(stringBuilder.toString(),telegramUserId);
        }


        return mailingData;
    }

}
