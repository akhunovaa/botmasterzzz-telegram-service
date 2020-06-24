package com.botmasterzzz.telegram.controller.telegram.tiktok;

import com.botmasterzzz.bot.api.impl.methods.send.SendMessage;
import com.botmasterzzz.bot.api.impl.objects.Update;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.InlineKeyboardMarkup;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.buttons.InlineKeyboardButton;
import com.botmasterzzz.telegram.config.annotations.BotController;
import com.botmasterzzz.telegram.config.annotations.BotRequestMapping;
import com.botmasterzzz.telegram.dto.CallBackData;
import com.botmasterzzz.telegram.dto.TopRatingUsersDTO;
import com.botmasterzzz.telegram.service.TelegramMediaService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@BotController
public class TikTokUserRatingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TikTokUserRatingController.class);

    @Autowired
    private TelegramMediaService telegramMediaService;

    @Autowired
    private Gson gson;

    @BotRequestMapping(value = "tiktok-\uD83D\uDC8EТОП")
    public SendMessage sendTOPActiveUsers(Update update) {
        Long chatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        String name = null != update.getMessage().getFrom().getUserName() ? update.getMessage().getFrom().getUserName() : update.getMessage().getFrom().getFirstName();

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = new ArrayList<>();

        InlineKeyboardButton rowLeftInlineButton = new InlineKeyboardButton();
        InlineKeyboardButton rowRightInlineButton = new InlineKeyboardButton();
//        CallBackData callBackDataForArrows = new CallBackData("part-nav-arrow");
        rowLeftInlineButton.setText("<");
        rowRightInlineButton.setText(">");

//        callBackDataForArrows.setOffset(offset > 0 ? offset - 1 : 0);
        rowLeftInlineButton.setCallbackData("<888");

//        callBackDataForArrows.setOffset(offset >= limit ? limit : offset + 1);
        rowRightInlineButton.setCallbackData(">999");

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
                .setText("<b>ТОП</b> 1️⃣0️⃣ <b> активных пользователей:</b>\uD83D\uDC8E:\n" +
                        "➖➖➖➖➖➖➖➖➖➖➖➖\n" +
                        "1️⃣  место за пользователем <a href=\"tg://user?id=" + telegramRatingStatisticEntityList.get(0).getTelegramUserId() + "\"><b>" + telegramUserFirst + "</b></a> \uD83E\uDD47 \n\n" +
                        "2️⃣  место за пользователем <a href=\"tg://user?id=" + telegramRatingStatisticEntityList.get(1).getTelegramUserId() + "\"><b>" + telegramUseSecond + "</b></a> \uD83E\uDD48 \n\n" +
                        "3️⃣  место за пользователем <a href=\"tg://user?id=" + telegramRatingStatisticEntityList.get(2).getTelegramUserId() + "\">" + telegramUserThird + "</a> \uD83E\uDD49 \n\n" +
                        "4️⃣  место за пользователем <a href=\"tg://user?id=" + telegramRatingStatisticEntityList.get(3).getTelegramUserId() + "\">" + telegramUserFourth + "</a> \uD83C\uDFC5 \n\n" +
                        "5️⃣  место за пользователем <a href=\"tg://user?id=" + telegramRatingStatisticEntityList.get(4).getTelegramUserId() + "\">" + telegramUserFifth + "</a> ❇️ \n\n" +
                        "6️⃣  место за пользователем <a href=\"tg://user?id=" + telegramRatingStatisticEntityList.get(5).getTelegramUserId() + "\">" + telegramUserSixth + "</a> ❇️ \n\n" +
                        "7️⃣  место за пользователем <a href=\"tg://user?id=" + telegramRatingStatisticEntityList.get(6).getTelegramUserId() + "\">" + telegramUserSeventh + "</a> ❇️ \n\n" +
                        "8️⃣  место за пользователем <a href=\"tg://user?id=" + telegramRatingStatisticEntityList.get(7).getTelegramUserId() + "\">" + telegramUserEith + "</a> ❇️ \n\n" +
                        "9️⃣  место за пользователем <a href=\"tg://user?id=" + telegramRatingStatisticEntityList.get(8).getTelegramUserId() + "\">" + telegramUserNinth + "</a> ❇️ \n\n" +
                        "\uD83D\uDD1F место за пользователе <a href=\"tg://user?id=" + telegramRatingStatisticEntityList.get(9).getTelegramUserId() + "\">" + telegramUserTenth + "</a> ❇️ \n" +
                        "➖➖➖➖➖➖➖➖➖➖➖➖\n")
                .setReplyMarkup(inlineKeyboardMarkup);
    }

}
