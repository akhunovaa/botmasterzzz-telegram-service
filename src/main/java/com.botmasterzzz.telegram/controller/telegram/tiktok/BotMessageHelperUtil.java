package com.botmasterzzz.telegram.controller.telegram.tiktok;

import com.botmasterzzz.bot.api.impl.objects.replykeyboard.buttons.InlineKeyboardButton;
import com.botmasterzzz.telegram.dto.CallBackData;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class BotMessageHelperUtil {

    protected static String getNewsMessage(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("➖➖➖➖➖➖➖➖➖➖➖➖\n");
        stringBuilder.append("<b>05.08.2020</b>\n");
        stringBuilder.append(" - В меню бота была добавлена кнопка  <b>\uD83C\uDF88Новое за вчера</b>");
        stringBuilder.append(" благодаря которой можно пролистать и увидеть новинки предыдущего дня.\n");
        stringBuilder.append(" - Добавили новую кнопку <b>\uD83C\uDF81Мои медиа</b> с помощью которой сможете просмотреть все загруженные Вами медиа файлы, где помимо этого Вам будет предоставлена и вся статистика.\n");
        stringBuilder.append(" - Внедрили новую кнопку <b>\uD83D\uDCF2Посты пользователя</b>, нажав на которую можно перейти ко всем постам автора.\n");
        stringBuilder.append("➖➖➖➖➖➖➖➖➖➖➖➖\n");
        stringBuilder.append("<b>12.08.2020</b>\n");
        stringBuilder.append(" - При просмотре постов, появилась новая кнопка ➡️ <b>Далее</b>, при нажатии на которую можно перейти на следующий пост и при этом клавиатура снизу теперь будет прятаться.\n");
        stringBuilder.append("➖➖➖➖➖➖➖➖➖➖➖➖\n");
        return stringBuilder.toString();
    }

    protected static List<InlineKeyboardButton> arrowButtonsBuild(String path, int limitLeft, long telegramUserId, long fileId){
        String pathToCallback;
        switch (path){
            case "newForToday":
                pathToCallback = "art";
                break;
            case "newForYesterday":
                pathToCallback = "art";
                break;
            case "photo":
                pathToCallback = "arf";
                break;
            case "video":
                pathToCallback = "arv";
                break;
            default:
                pathToCallback = "art";
        }
        List<InlineKeyboardButton> inlineKeyboardButtonsArrowsRow = new ArrayList<>();
        Gson gson = new Gson();
        InlineKeyboardButton leftArrowButton = new InlineKeyboardButton();
        leftArrowButton.setText("️➡️ Далее");
        CallBackData leftButtonData = new CallBackData(pathToCallback, telegramUserId, fileId);
        leftButtonData.setO(limitLeft);
        leftButtonData.setL(limitLeft);
        leftArrowButton.setCallbackData(gson.toJson(leftButtonData));

//        InlineKeyboardButton rightArrowButton = new InlineKeyboardButton();
//        rightArrowButton.setText("▶️️");
//        CallBackData rightButtonData = new CallBackData(path, telegramUserId, fileId);
//        rightButtonData.setO(limitRight);
//        rightButtonData.setL(limitLeft);
//        rightArrowButton.setCallbackData(gson.toJson(rightButtonData));
        inlineKeyboardButtonsArrowsRow.add(leftArrowButton);
        //inlineKeyboardButtonsArrowsRow.add(rightArrowButton);
        return inlineKeyboardButtonsArrowsRow;
    }
}
