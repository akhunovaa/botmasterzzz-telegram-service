package com.botmasterzzz.telegram.controller.telegram.antiparkon;

public class BotMessageHelperUtil {

    protected static String getNewsMessage(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("➖➖➖➖➖➖➖➖➖➖➖➖\n");
        stringBuilder.append("<b>10.08.2020</b>\n");
        stringBuilder.append(" - Запустили нашего бота. Помощь водителям авто. Поможем если вы хотите связаться с владельцем авто, при ДТП или если приглянулся водитель \uD83D\uDE09\n");
        stringBuilder.append("➖➖➖➖➖➖➖➖➖➖➖➖\n");
        return stringBuilder.toString();
    }
}
