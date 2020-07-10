package com.botmasterzzz.telegram.controller.telegram.tiktok;

import com.botmasterzzz.bot.api.impl.methods.send.SendMessage;
import com.botmasterzzz.bot.api.impl.methods.update.EditMessageText;
import com.botmasterzzz.bot.api.impl.objects.Update;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.InlineKeyboardMarkup;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.buttons.InlineKeyboardButton;
import com.botmasterzzz.telegram.config.annotations.BotController;
import com.botmasterzzz.telegram.config.annotations.BotRequestMapping;
import com.botmasterzzz.telegram.dto.CallBackData;
import com.botmasterzzz.telegram.dto.OwnerStatisticDTO;
import com.botmasterzzz.telegram.dto.TopRatingUsersDTO;
import com.botmasterzzz.telegram.entity.TelegramBotUserEntity;
import com.botmasterzzz.telegram.service.DatabaseService;
import com.botmasterzzz.telegram.service.TelegramMediaService;
import com.botmasterzzz.telegram.service.TelegramUserService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@BotController
public class TikTokUserRatingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TikTokUserRatingController.class);

    private final TelegramMediaService telegramMediaService;

    private final DatabaseService databaseService;

    private final TelegramUserService telegramUserService;

    private final Gson gson;

    @Autowired
    public TikTokUserRatingController(TelegramMediaService telegramMediaService, TelegramUserService telegramUserService, Gson gson, DatabaseService databaseService) {
        this.telegramMediaService = telegramMediaService;
        this.telegramUserService = telegramUserService;
        this.gson = gson;
        this.databaseService = databaseService;
    }

    @BotRequestMapping(value = "tiktok-\uD83D\uDC8EТОП")
    public SendMessage sendTOPActiveUsers(Update update) {
        Long chatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        String name = null != update.getMessage().getFrom().getUserName() ? update.getMessage().getFrom().getUserName() : update.getMessage().getFrom().getFirstName();

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = new ArrayList<>();

        InlineKeyboardButton rowLeftInlineButton = new InlineKeyboardButton();
        InlineKeyboardButton rowRightInlineButton = new InlineKeyboardButton();
        CallBackData callBackDataForArrows = new CallBackData("owner-statistic");
        rowLeftInlineButton.setText("\uD83E\uDDFE Моя статистика");
        CallBackData callBackData = new CallBackData("mass-top-statistic");
        rowRightInlineButton.setText("\uD83E\uDDFE Общая статистика");

        rowLeftInlineButton.setCallbackData(gson.toJson(callBackDataForArrows));
        rowRightInlineButton.setCallbackData(gson.toJson(callBackData));

        inlineKeyboardButtonsFirstRow.add(rowLeftInlineButton);
        inlineKeyboardButtonsFirstRow.add(rowRightInlineButton);
        inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);
        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);
        List<TopRatingUsersDTO> telegramRatingStatisticEntityList = telegramMediaService.topActiveUsersGet();
        LOGGER.info("TOP active users requested: {}", name);
        String telegramUserFirst = null != telegramRatingStatisticEntityList.get(0).getTelegramUserName() ? telegramRatingStatisticEntityList.get(0).getTelegramUserName() : telegramRatingStatisticEntityList.get(0).getTelegramFirstName();
        String telegramUseSecond = null != telegramRatingStatisticEntityList.get(1).getTelegramUserName() ? telegramRatingStatisticEntityList.get(1).getTelegramUserName() : telegramRatingStatisticEntityList.get(1).getTelegramFirstName();
        String telegramUserThird = null != telegramRatingStatisticEntityList.get(2).getTelegramUserName() ? telegramRatingStatisticEntityList.get(2).getTelegramUserName() : telegramRatingStatisticEntityList.get(2).getTelegramFirstName();
        String telegramUserFourth = null != telegramRatingStatisticEntityList.get(3).getTelegramUserName() ? telegramRatingStatisticEntityList.get(3).getTelegramUserName() : telegramRatingStatisticEntityList.get(3).getTelegramFirstName();
        String telegramUserFifth = null != telegramRatingStatisticEntityList.get(4).getTelegramUserName() ? telegramRatingStatisticEntityList.get(4).getTelegramUserName() : telegramRatingStatisticEntityList.get(4).getTelegramFirstName();
        String telegramUserSixth = null != telegramRatingStatisticEntityList.get(5).getTelegramUserName() ? telegramRatingStatisticEntityList.get(5).getTelegramUserName() : telegramRatingStatisticEntityList.get(5).getTelegramFirstName();
        String telegramUserSeventh = null != telegramRatingStatisticEntityList.get(6).getTelegramUserName() ? telegramRatingStatisticEntityList.get(6).getTelegramUserName() : telegramRatingStatisticEntityList.get(6).getTelegramFirstName();
        String telegramUserEith = null != telegramRatingStatisticEntityList.get(7).getTelegramUserName() ? telegramRatingStatisticEntityList.get(7).getTelegramUserName() : telegramRatingStatisticEntityList.get(7).getTelegramFirstName();
        String telegramUserNinth = null != telegramRatingStatisticEntityList.get(8).getTelegramUserName() ? telegramRatingStatisticEntityList.get(8).getTelegramUserName() : telegramRatingStatisticEntityList.get(8).getTelegramFirstName();
        String telegramUserTenth = null != telegramRatingStatisticEntityList.get(9).getTelegramUserName() ? telegramRatingStatisticEntityList.get(9).getTelegramUserName() : telegramRatingStatisticEntityList.get(9).getTelegramFirstName();

        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true).setParseMode("HTML")
                .setText("<b>ТОП</b> 1️⃣0️⃣ <b> активных пользователей</b>\uD83D\uDC8E:\n" +
                        "➖➖➖➖➖➖➖➖➖➖➖➖\n" +
                        "1️⃣ место <a href=\"tg://user?id=" + telegramRatingStatisticEntityList.get(0).getTelegramUserId() + "\"><b>" + telegramUserFirst + "</b></a> \uD83E\uDD47 \n\n" +
                        "2️⃣ место <a href=\"tg://user?id=" + telegramRatingStatisticEntityList.get(1).getTelegramUserId() + "\"><b>" + telegramUseSecond + "</b></a> \uD83E\uDD48 \n\n" +
                        "3️⃣ место <a href=\"tg://user?id=" + telegramRatingStatisticEntityList.get(2).getTelegramUserId() + "\">" + telegramUserThird + "</a> \uD83E\uDD49 \n\n" +
                        "4️⃣ место <a href=\"tg://user?id=" + telegramRatingStatisticEntityList.get(3).getTelegramUserId() + "\">" + telegramUserFourth + "</a> \uD83C\uDFC5 \n\n" +
                        "5️⃣ место <a href=\"tg://user?id=" + telegramRatingStatisticEntityList.get(4).getTelegramUserId() + "\">" + telegramUserFifth + "</a> ❇️ \n\n" +
                        "6️⃣ место <a href=\"tg://user?id=" + telegramRatingStatisticEntityList.get(5).getTelegramUserId() + "\">" + telegramUserSixth + "</a> ❇️ \n\n" +
                        "7️⃣ место <a href=\"tg://user?id=" + telegramRatingStatisticEntityList.get(6).getTelegramUserId() + "\">" + telegramUserSeventh + "</a> ❇️ \n\n" +
                        "8️⃣ место <a href=\"tg://user?id=" + telegramRatingStatisticEntityList.get(7).getTelegramUserId() + "\">" + telegramUserEith + "</a> ❇️ \n\n" +
                        "9️⃣ место <a href=\"tg://user?id=" + telegramRatingStatisticEntityList.get(8).getTelegramUserId() + "\">" + telegramUserNinth + "</a> ❇️ \n\n" +
                        "\uD83D\uDD1F место <a href=\"tg://user?id=" + telegramRatingStatisticEntityList.get(9).getTelegramUserId() + "\">" + telegramUserTenth + "</a> ❇️ \n" +
                        "➖➖➖➖➖➖➖➖➖➖➖➖\n")
                .setReplyMarkup(inlineKeyboardMarkup);
    }

    @BotRequestMapping(value = "tiktok-top-statistic")
    public EditMessageText sendTOPActiveUsersFromCommand(Update update) {
        Long chatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        String name = null != update.getCallbackQuery().getFrom().getUserName() ? update.getCallbackQuery().getFrom().getUserName() : update.getCallbackQuery().getFrom().getFirstName();

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = new ArrayList<>();

        InlineKeyboardButton rowLeftInlineButton = new InlineKeyboardButton();
        InlineKeyboardButton rowRightInlineButton = new InlineKeyboardButton();
        CallBackData callBackDataForArrows = new CallBackData("owner-statistic");
        rowLeftInlineButton.setText("\uD83E\uDDFE Моя статистика");
//        rowRightInlineButton.setText(">");

//        callBackDataForArrows.setOffset(offset > 0 ? offset - 1 : 0);
        rowLeftInlineButton.setCallbackData(gson.toJson(callBackDataForArrows));

//        callBackDataForArrows.setOffset(offset >= limit ? limit : offset + 1);
        rowRightInlineButton.setCallbackData("mass-statistic");

        inlineKeyboardButtonsFirstRow.add(rowLeftInlineButton);
//        inlineKeyboardButtonsFirstRow.add(rowRightInlineButton);
        inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);
        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);
        List<TopRatingUsersDTO> telegramRatingStatisticEntityList = telegramMediaService.topActiveUsersGet();
        LOGGER.info("TOP active users requested: {}", name);
        String telegramUserFirst = null != telegramRatingStatisticEntityList.get(0).getTelegramUserName() ? telegramRatingStatisticEntityList.get(0).getTelegramUserName() : telegramRatingStatisticEntityList.get(0).getTelegramFirstName();
        String telegramUseSecond = null != telegramRatingStatisticEntityList.get(1).getTelegramUserName() ? telegramRatingStatisticEntityList.get(1).getTelegramUserName() : telegramRatingStatisticEntityList.get(1).getTelegramFirstName();
        String telegramUserThird = null != telegramRatingStatisticEntityList.get(2).getTelegramUserName() ? telegramRatingStatisticEntityList.get(2).getTelegramUserName() : telegramRatingStatisticEntityList.get(2).getTelegramFirstName();
        String telegramUserFourth = null != telegramRatingStatisticEntityList.get(3).getTelegramUserName() ? telegramRatingStatisticEntityList.get(3).getTelegramUserName() : telegramRatingStatisticEntityList.get(3).getTelegramFirstName();
        String telegramUserFifth = null != telegramRatingStatisticEntityList.get(4).getTelegramUserName() ? telegramRatingStatisticEntityList.get(4).getTelegramUserName() : telegramRatingStatisticEntityList.get(4).getTelegramFirstName();
        String telegramUserSixth = null != telegramRatingStatisticEntityList.get(5).getTelegramUserName() ? telegramRatingStatisticEntityList.get(5).getTelegramUserName() : telegramRatingStatisticEntityList.get(5).getTelegramFirstName();
        String telegramUserSeventh = null != telegramRatingStatisticEntityList.get(6).getTelegramUserName() ? telegramRatingStatisticEntityList.get(6).getTelegramUserName() : telegramRatingStatisticEntityList.get(6).getTelegramFirstName();
        String telegramUserEith = null != telegramRatingStatisticEntityList.get(7).getTelegramUserName() ? telegramRatingStatisticEntityList.get(7).getTelegramUserName() : telegramRatingStatisticEntityList.get(7).getTelegramFirstName();
        String telegramUserNinth = null != telegramRatingStatisticEntityList.get(8).getTelegramUserName() ? telegramRatingStatisticEntityList.get(8).getTelegramUserName() : telegramRatingStatisticEntityList.get(8).getTelegramFirstName();
        String telegramUserTenth = null != telegramRatingStatisticEntityList.get(9).getTelegramUserName() ? telegramRatingStatisticEntityList.get(9).getTelegramUserName() : telegramRatingStatisticEntityList.get(9).getTelegramFirstName();

        return new EditMessageText()
                .setChatId(update.getCallbackQuery().getMessage().getChatId()).enableHtml(true).setParseMode("HTML")
                .setText("<b>ТОП</b> 1️⃣0️⃣ <b> активных пользователей</b>\uD83D\uDC8E:\n" +
                        "➖➖➖➖➖➖➖➖➖➖➖➖\n" +
                        "1️⃣ место <a href=\"tg://user?id=" + telegramRatingStatisticEntityList.get(0).getTelegramUserId() + "\"><b>" + telegramUserFirst + "</b></a> \uD83E\uDD47 \n\n" +
                        "2️⃣ место <a href=\"tg://user?id=" + telegramRatingStatisticEntityList.get(1).getTelegramUserId() + "\"><b>" + telegramUseSecond + "</b></a> \uD83E\uDD48 \n\n" +
                        "3️⃣ место <a href=\"tg://user?id=" + telegramRatingStatisticEntityList.get(2).getTelegramUserId() + "\">" + telegramUserThird + "</a> \uD83E\uDD49 \n\n" +
                        "4️⃣ место <a href=\"tg://user?id=" + telegramRatingStatisticEntityList.get(3).getTelegramUserId() + "\">" + telegramUserFourth + "</a> \uD83C\uDFC5 \n\n" +
                        "5️⃣ место <a href=\"tg://user?id=" + telegramRatingStatisticEntityList.get(4).getTelegramUserId() + "\">" + telegramUserFifth + "</a> ❇️ \n\n" +
                        "6️⃣ место <a href=\"tg://user?id=" + telegramRatingStatisticEntityList.get(5).getTelegramUserId() + "\">" + telegramUserSixth + "</a> ❇️ \n\n" +
                        "7️⃣ место <a href=\"tg://user?id=" + telegramRatingStatisticEntityList.get(6).getTelegramUserId() + "\">" + telegramUserSeventh + "</a> ❇️ \n\n" +
                        "8️⃣ место <a href=\"tg://user?id=" + telegramRatingStatisticEntityList.get(7).getTelegramUserId() + "\">" + telegramUserEith + "</a> ❇️ \n\n" +
                        "9️⃣ место <a href=\"tg://user?id=" + telegramRatingStatisticEntityList.get(8).getTelegramUserId() + "\">" + telegramUserNinth + "</a> ❇️ \n\n" +
                        "\uD83D\uDD1F место <a href=\"tg://user?id=" + telegramRatingStatisticEntityList.get(9).getTelegramUserId() + "\">" + telegramUserTenth + "</a> ❇️ \n" +
                        "➖➖➖➖➖➖➖➖➖➖➖➖\n")
                .setReplyMarkup(inlineKeyboardMarkup).setMessageId(update.getCallbackQuery().getMessage().getMessageId());
    }

    @BotRequestMapping(value = "tiktok-mass-top-statistic")
    public EditMessageText sendTOPFromCommand(Update update) {
        Long chatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        String name = null != update.getCallbackQuery().getFrom().getUserName() ? update.getCallbackQuery().getFrom().getUserName() : update.getCallbackQuery().getFrom().getFirstName();

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = new ArrayList<>();

        InlineKeyboardButton rowLeftInlineButton = new InlineKeyboardButton();
        InlineKeyboardButton rowRightInlineButton = new InlineKeyboardButton();
        CallBackData callBackDataForArrows = new CallBackData("owner-statistic");
        rowLeftInlineButton.setText("\uD83E\uDDFE Моя статистика");
//        rowRightInlineButton.setText(">");

//        callBackDataForArrows.setOffset(offset > 0 ? offset - 1 : 0);
        rowLeftInlineButton.setCallbackData(gson.toJson(callBackDataForArrows));

//        callBackDataForArrows.setOffset(offset >= limit ? limit : offset + 1);
        //rowRightInlineButton.setCallbackData("mass-statistic");

        inlineKeyboardButtonsFirstRow.add(rowLeftInlineButton);
//        inlineKeyboardButtonsFirstRow.add(rowRightInlineButton);
        inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);
        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);
        List<TopRatingUsersDTO> telegramRatingStatisticEntityList = telegramMediaService.topUsersGet();
        LOGGER.info("TOP users requested: {}", name);
        String telegramUserFirst = null != telegramRatingStatisticEntityList.get(0).getTelegramUserName() ? telegramRatingStatisticEntityList.get(0).getTelegramUserName() : telegramRatingStatisticEntityList.get(0).getTelegramFirstName();
        String telegramUseSecond = null != telegramRatingStatisticEntityList.get(1).getTelegramUserName() ? telegramRatingStatisticEntityList.get(1).getTelegramUserName() : telegramRatingStatisticEntityList.get(1).getTelegramFirstName();
        String telegramUserThird = null != telegramRatingStatisticEntityList.get(2).getTelegramUserName() ? telegramRatingStatisticEntityList.get(2).getTelegramUserName() : telegramRatingStatisticEntityList.get(2).getTelegramFirstName();
        String telegramUserFourth = null != telegramRatingStatisticEntityList.get(3).getTelegramUserName() ? telegramRatingStatisticEntityList.get(3).getTelegramUserName() : telegramRatingStatisticEntityList.get(3).getTelegramFirstName();
        String telegramUserFifth = null != telegramRatingStatisticEntityList.get(4).getTelegramUserName() ? telegramRatingStatisticEntityList.get(4).getTelegramUserName() : telegramRatingStatisticEntityList.get(4).getTelegramFirstName();
        String telegramUserSixth = null != telegramRatingStatisticEntityList.get(5).getTelegramUserName() ? telegramRatingStatisticEntityList.get(5).getTelegramUserName() : telegramRatingStatisticEntityList.get(5).getTelegramFirstName();
        String telegramUserSeventh = null != telegramRatingStatisticEntityList.get(6).getTelegramUserName() ? telegramRatingStatisticEntityList.get(6).getTelegramUserName() : telegramRatingStatisticEntityList.get(6).getTelegramFirstName();
        String telegramUserEith = null != telegramRatingStatisticEntityList.get(7).getTelegramUserName() ? telegramRatingStatisticEntityList.get(7).getTelegramUserName() : telegramRatingStatisticEntityList.get(7).getTelegramFirstName();
        String telegramUserNinth = null != telegramRatingStatisticEntityList.get(8).getTelegramUserName() ? telegramRatingStatisticEntityList.get(8).getTelegramUserName() : telegramRatingStatisticEntityList.get(8).getTelegramFirstName();
        String telegramUserTenth = null != telegramRatingStatisticEntityList.get(9).getTelegramUserName() ? telegramRatingStatisticEntityList.get(9).getTelegramUserName() : telegramRatingStatisticEntityList.get(9).getTelegramFirstName();

        return new EditMessageText()
                .setChatId(update.getCallbackQuery().getMessage().getChatId()).enableHtml(true).setParseMode("HTML")
                .setText("<b>ТОП</b> 1️⃣0️⃣ <b> пользователей по полученным лайкам и симпатиям </b>\uD83D\uDC8E:\n" +
                        "➖➖➖➖➖➖➖➖➖➖➖➖\n" +
                        "1️⃣ место <a href=\"tg://user?id=" + telegramRatingStatisticEntityList.get(0).getTelegramUserId() + "\"><b>" + telegramUserFirst + "</b></a> \uD83E\uDD47 \n\n" +
                        "2️⃣ место <a href=\"tg://user?id=" + telegramRatingStatisticEntityList.get(1).getTelegramUserId() + "\"><b>" + telegramUseSecond + "</b></a> \uD83E\uDD48 \n\n" +
                        "3️⃣ место <a href=\"tg://user?id=" + telegramRatingStatisticEntityList.get(2).getTelegramUserId() + "\">" + telegramUserThird + "</a> \uD83E\uDD49 \n\n" +
                        "4️⃣ место <a href=\"tg://user?id=" + telegramRatingStatisticEntityList.get(3).getTelegramUserId() + "\">" + telegramUserFourth + "</a> \uD83C\uDFC5 \n\n" +
                        "5️⃣ место <a href=\"tg://user?id=" + telegramRatingStatisticEntityList.get(4).getTelegramUserId() + "\">" + telegramUserFifth + "</a> ❇️ \n\n" +
                        "6️⃣ место <a href=\"tg://user?id=" + telegramRatingStatisticEntityList.get(5).getTelegramUserId() + "\">" + telegramUserSixth + "</a> ❇️ \n\n" +
                        "7️⃣ место <a href=\"tg://user?id=" + telegramRatingStatisticEntityList.get(6).getTelegramUserId() + "\">" + telegramUserSeventh + "</a> ❇️ \n\n" +
                        "8️⃣ место <a href=\"tg://user?id=" + telegramRatingStatisticEntityList.get(7).getTelegramUserId() + "\">" + telegramUserEith + "</a> ❇️ \n\n" +
                        "9️⃣ место <a href=\"tg://user?id=" + telegramRatingStatisticEntityList.get(8).getTelegramUserId() + "\">" + telegramUserNinth + "</a> ❇️ \n\n" +
                        "\uD83D\uDD1F место <a href=\"tg://user?id=" + telegramRatingStatisticEntityList.get(9).getTelegramUserId() + "\">" + telegramUserTenth + "</a> ❇️ \n" +
                        "➖➖➖➖➖➖➖➖➖➖➖➖\n")
                .setReplyMarkup(inlineKeyboardMarkup).setMessageId(update.getCallbackQuery().getMessage().getMessageId());
    }

    @BotRequestMapping(value = "tiktok-owner-statistic")
    public EditMessageText getOwnerStatisticData(Update update) {
        Long chatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        Long requestedUserId = Long.valueOf(update.getCallbackQuery().getFrom().getId());

        TelegramBotUserEntity requestedTelegramUser = telegramUserService.getTelegramUser(requestedUserId);
        boolean isRequestedUserAdmin = requestedTelegramUser.isAdmin();
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();


        List<OwnerStatisticDTO> ownerStatisticDTOList = telegramMediaService.getUsersActivityStatistic(requestedUserId);
        Long countOfLoggedToUser = databaseService.getCountOfLoggedToUser(requestedUserId);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = new ArrayList<>();
        CallBackData callBackDataForArrows = new CallBackData("top-statistic");
        InlineKeyboardButton rowRightInlineButton = new InlineKeyboardButton();
        InlineKeyboardButton rowInlineButton = new InlineKeyboardButton();
        rowRightInlineButton.setText("ТОП самых активных\uD83D\uDC8E");
        rowRightInlineButton.setCallbackData(gson.toJson(callBackDataForArrows));

        CallBackData callBackData = new CallBackData("mass-top-statistic");
        rowInlineButton.setText("\uD83E\uDDFE Общая статистика");
        rowInlineButton.setCallbackData(gson.toJson(callBackData));

        inlineKeyboardButtonsFirstRow.add(rowRightInlineButton);
        inlineKeyboardButtonsFirstRow.add(rowInlineButton);
        inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);
        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);
        StringBuilder responseTextBuilder = new StringBuilder();
        responseTextBuilder.append("<b>Моя статистика по лайкам/дизлайкам</b>\uD83E\uDDFE:\n");
        responseTextBuilder.append("\n");
        responseTextBuilder.append("Мои:\n");
        responseTextBuilder.append("➖➖➖➖➖➖➖➖➖➖➖➖\n");
        if (ownerStatisticDTOList.isEmpty()) {
            responseTextBuilder.append("<b>Статистика отсутствует</b>\n");
        }
        for (OwnerStatisticDTO ownerStatisticDTO : ownerStatisticDTOList) {
            responseTextBuilder.append("<b>")
                    .append(ownerStatisticDTO.getTouchType()).append("</b>: ")
                    .append(ownerStatisticDTO.getCountOfTouch()).append("\n");
        }

        responseTextBuilder.append("➖➖➖➖➖➖➖➖➖➖➖➖\n\n");
        ownerStatisticDTOList = telegramMediaService.getSelfActivityStatistic(requestedUserId);
        responseTextBuilder.append("Мне:\n");
        responseTextBuilder.append("➖➖➖➖➖➖➖➖➖➖➖➖\n");
        if (ownerStatisticDTOList.isEmpty()) {
            responseTextBuilder.append("<b>Статистика отсутствует</b>\n");
        }
        for (OwnerStatisticDTO ownerStatisticDTO : ownerStatisticDTOList) {
            responseTextBuilder.append("<b>")
                    .append(ownerStatisticDTO.getTouchType()).append("</b>: ")
                    .append(ownerStatisticDTO.getCountOfTouch()).append("\n");
        }
        responseTextBuilder.append("➖➖➖➖➖➖➖➖➖➖➖➖\n");
        responseTextBuilder.append("Кол-во просмотренных записей \uD83D\uDC41: ").append(countOfLoggedToUser);
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(update.getCallbackQuery().getMessage().getChatId());
        editMessageText.setText(responseTextBuilder.toString());
        editMessageText.enableHtml(true);
        editMessageText.setParseMode("HTML");
        editMessageText.setReplyMarkup(inlineKeyboardMarkup);
        editMessageText.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        return editMessageText;
    }

}
