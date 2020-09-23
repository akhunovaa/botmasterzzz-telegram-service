package com.botmasterzzz.telegram.controller.telegram.reklam;

import com.botmasterzzz.bot.api.impl.methods.send.SendMessage;
import com.botmasterzzz.bot.api.impl.methods.update.EditMessageText;
import com.botmasterzzz.bot.api.impl.objects.Update;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.InlineKeyboardMarkup;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.ReplyKeyboardMarkup;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.buttons.InlineKeyboardButton;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.buttons.KeyboardRow;
import com.botmasterzzz.telegram.config.annotations.BotController;
import com.botmasterzzz.telegram.config.annotations.BotRequestMapping;
import com.botmasterzzz.telegram.config.context.UserContextHolder;
import com.botmasterzzz.telegram.dto.CallBackData;
import com.botmasterzzz.telegram.entity.LotsEntity;
import com.botmasterzzz.telegram.service.ReklamMessageService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@BotController
public class AccountReklamMenuController {

    private static final Logger logger = LoggerFactory.getLogger(AccountReklamMenuController.class);


    @Autowired
    private ReklamMessageService reklamMessageService;

    @Autowired
    private Gson gson;

    @BotRequestMapping(value = "reklam-\uD83D\uDD10Личный кабинет")
    public SendMessage account(Update update) {
        String name = null != update.getMessage().getFrom().getUserName() ? update.getMessage().getFrom().getUserName() : update.getMessage().getFrom().getFirstName();
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRowLineOne = new KeyboardRow();
        KeyboardRow keyboardRowLineTwo = new KeyboardRow();
        KeyboardRow keyboardRowLineThree = new KeyboardRow();
        keyboardRowLineOne.add("\uD83D\uDDA5Главное меню");
        keyboardRowLineOne.add("Баланс");
        keyboardRowLineTwo.add("Хочу заработать");
        keyboardRowLineThree.add("Хочу подписчиков");
        keyboardRows.add(keyboardRowLineOne);
        keyboardRows.add(keyboardRowLineTwo);
        keyboardRows.add(keyboardRowLineThree);
        keyboard.setKeyboard(keyboardRows);


        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText("<b>Главное меню</b>\n" +
                        "Выберите раздел: \uD83D\uDD3D")
                .setReplyMarkup(keyboard);
    }

    @BotRequestMapping(value = "reklam-Баланс")
    public SendMessage balacc(Update update) {
        InlineKeyboardMarkup inlineKeyboardMarkup = reklamMessageService.getInlineKeyboardForBalAcc();
        int currentId = update.getMessage().getFrom().getId();
//        CallBackData callBackData = UserContextHolder.currentContext().getCallBackData();
//        Long telegramUserId = callBackData.getUserId();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<b>Здесь отображается ваш баланс</b>\n");
        stringBuilder.append("\n");

        try {
            stringBuilder.append(reklamMessageService.getAccountTotal(currentId));
            stringBuilder.append("currentId="+currentId+ "\ns_");
//            stringBuilder.append("telegramuserid="+telegramUserId);
//            stringBuilder.append(reklamMessageService.getAccountTotalByUserId(telegramUserId));
            stringBuilder.append("_отработка_");
        } catch (Exception e)
        {
            logger.debug("fail при попытке чтения из pim_account");
        }
        finally {
            stringBuilder.append("лалала ёпта (манимейкер десижн)");
        }
        stringBuilder.append(" р.");

        stringBuilder.append("\n");
        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText(stringBuilder.toString());
    }

    @BotRequestMapping(value = "reklam-Хочу заработать")
    public SendMessage wantMoney(Update update) {
        InlineKeyboardMarkup inlineKeyboardMarkup = reklamMessageService.getInlineKeyboardForWantMoney();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<b>Здесь вы можете выбрать заявку на привлечение подписчиков</b>\n");
        stringBuilder.append("\n");

        stringBuilder.append("\n");
        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText(stringBuilder.toString())
                .setReplyMarkup(inlineKeyboardMarkup);
    }

    @BotRequestMapping(value = "reklam-Хочу подписчиков")
    public SendMessage wantSubs(Update update) {
        InlineKeyboardMarkup inlineKeyboardMarkup = reklamMessageService.getInlineKeyboardForWantSubs();

//        UserContextHolder.currentContext().setNameAwait(true);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<b>Здесь вы можете оставить заявку на привлечение подписчиков</b>\n");

        stringBuilder.append("\n");

        stringBuilder.append("\n");
        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText(stringBuilder.toString())
                .setReplyMarkup(inlineKeyboardMarkup);
    }

    @BotRequestMapping(value = "reklam-lots_list")
    public SendMessage comment(Update update) {
        Long chatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();

        CallBackData callBackData = UserContextHolder.currentContext().getCallBackData();
        Long telegramUserId = callBackData.getUserId();
        Long fileId = callBackData.getFileId();
        int offset = 0;
        List<LotsEntity> lotsEntityList = reklamMessageService.getLotsForCustomer(offset, 1);
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
        int lim = lotsEntityList.size() < 1 ? offset : offset + 1;
        rightButtonData.setOffset(lim);
        rightArrowButton.setCallbackData(gson.toJson(rightButtonData));

        InlineKeyboardButton commentAddButton = new InlineKeyboardButton();
        commentAddButton.setText("\uD83D\uDCE8 Написать");
        commentAddButton.setCallbackData(gson.toJson(new CallBackData("lots-reserve", telegramUserId, fileId)));


        if (!lotsEntityList.isEmpty()) {
            inlineKeyboardButtonsFirstRow.add(leftArrowButton);
            inlineKeyboardButtonsFirstRow.add(rightArrowButton);
            inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);
        }
        inlineKeyboardButtonSecondRow.add(commentAddButton);
        inlineKeyboardButtons.add(inlineKeyboardButtonSecondRow);
        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<b>За:</b>\n");
        stringBuilder.append("\n");
        for (LotsEntity lot : lotsEntityList) {
//            TelegramBotUserEntity telegramBotUserEntity = lot.getTelegramBotUserEntity();
//            String telegramUser = null != telegramBotUserEntity.getUsername() ? telegramBotUserEntity.getUsername() : telegramBotUserEntity.getFirstName();
//            String commentData = //mediaCommentsDataEntity.getData();
//            String commentTimestamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(mediaCommentsDataEntity.getAudWhenCreate().getTime() + TimeUnit.HOURS.toMillis(3));
//            stringBuilder.append("⌚<i>").append(commentTimestamp).append("</i>  ");
//            stringBuilder.append("от пользователя <a href=\"tg://user?id=").append(telegramBotUserEntity.getTelegramId()).append("\">").append(telegramUser).append("</a>:\n");
            stringBuilder.append("➖➖➖➖➖➖➖➖➖➖➖➖\n");
            stringBuilder.append("<code> Заявка #")
                    .append(lot.getId())
                    .append(" \nна привлечение ")
                    .append(lot.getQuantity())
                    .append(" пользователей \nза ")
                    .append(lot.getCost())
                    .append(" рублей")
                    .append("</code>\n");
            stringBuilder.append("➖➖➖➖➖➖➖➖➖➖➖➖\n\n");
//            logger.info("User {} comment {}", telegramBotUserEntity, commentData);
        }
        if (lotsEntityList.isEmpty()) {
            stringBuilder.append("Заявки отсутствуют. Подождите немного или создайте собственную заявку!");
        }
        logger.info("User id {} comment message requested", telegramUserId);

        return new SendMessage()
                .setChatId(chatId).enableHtml(true)
                .setReplyMarkup(inlineKeyboardMarkup)
                .setText(stringBuilder.toString());
    }

    @BotRequestMapping(value = "reklam-arrow")
    public EditMessageText commentOffset(Update update) {
        Long chatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();

        CallBackData callBackData = UserContextHolder.currentContext().getCallBackData();
        Long telegramUserId = callBackData.getUserId();
        Long fileId = callBackData.getFileId();
        int offset = callBackData.getOffset();
        List<LotsEntity> lots = reklamMessageService.getLotsForCustomer(offset, 1);
        if(lots.isEmpty()){
            offset -= 1;
            lots = reklamMessageService.getLotsForCustomer(offset, 1);
        }
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();

        List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonSecondRow = new ArrayList<>();

        InlineKeyboardButton leftArrowButton = new InlineKeyboardButton();
        leftArrowButton.setText("◀️");
        CallBackData leftButtonData = new CallBackData("arrow", telegramUserId, fileId);
        leftButtonData.setOffset(offset - 1);
        leftArrowButton.setCallbackData(gson.toJson(leftButtonData));

        InlineKeyboardButton rightArrowButton = new InlineKeyboardButton();
        rightArrowButton.setText("▶️️");
        CallBackData rightButtonData = new CallBackData("arrow", telegramUserId, fileId);
        int lim = lots.size() < 1 ? offset : offset + 1;
        rightButtonData.setOffset(lim);
        rightArrowButton.setCallbackData(gson.toJson(rightButtonData));

        InlineKeyboardButton commentAddButton = new InlineKeyboardButton();
        commentAddButton.setText("\uD83D\uDCE8 Выбрать заявку");
        commentAddButton.setCallbackData(gson.toJson(new CallBackData("lots-reserve", telegramUserId, fileId)));


        if (!lots.isEmpty()) {
            inlineKeyboardButtonsFirstRow.add(leftArrowButton);
            inlineKeyboardButtonsFirstRow.add(rightArrowButton);
            inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);
        }
        inlineKeyboardButtonSecondRow.add(commentAddButton);
        inlineKeyboardButtons.add(inlineKeyboardButtonSecondRow);
        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<b>Список заявок:</b>\n");
        stringBuilder.append("\n");

        for (LotsEntity lot : lots) {
//            TelegramBotUserEntity telegramBotUserEntity = mediaCommentsDataEntity.getTelegramBotUserEntity();
//            String telegramUser = null != telegramBotUserEntity.getUsername() ? telegramBotUserEntity.getUsername() : telegramBotUserEntity.getFirstName();
//            String commentData = mediaCommentsDataEntity.getData();
//            String commentTimestamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(mediaCommentsDataEntity.getAudWhenCreate().getTime() + TimeUnit.HOURS.toMillis(3));
//            stringBuilder.append("⌚<i>").append(commentTimestamp).append("</i>  ");
//            stringBuilder.append("от пользователя <a href=\"tg://user?id=").append(telegramBotUserEntity.getTelegramId()).append("\">").append(telegramUser).append("</a>:\n");
            stringBuilder.append("➖➖➖➖➖➖➖➖➖➖➖➖\n");
            stringBuilder.append("<code> Заявка #")
                    .append(lot.getId())
                    .append(" на привлечение ")
                    .append(lot.getQuantity())
                    .append(" пользователей за ")
                    .append(lot.getCost())
                    .append(" рублей")
                    .append("</code>\n");
            stringBuilder.append("➖➖➖➖➖➖➖➖➖➖➖➖\n\n");
//            logger.info("User {} comment {}", commentData);
        }

        if (lots.isEmpty()) {
            stringBuilder.append("<code>").append("На данный момент доступные заявки отсутствуют. Вернитесь чуть позже или создайте свою заявку");
        }
        logger.info("User id {} comment message requested", telegramUserId);

        return new EditMessageText()
                .setChatId(chatId).enableHtml(true)
                .setReplyMarkup(inlineKeyboardMarkup)
                .setMessageId(messageId)
                .setText(stringBuilder.toString());
    }

    private EditMessageText getEditMessage(String text, Update update) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(update.getCallbackQuery().getMessage().getChatId());
        editMessageText.setText(text);
        editMessageText.enableHtml(Boolean.TRUE);
        editMessageText.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        return editMessageText;
    }
}
