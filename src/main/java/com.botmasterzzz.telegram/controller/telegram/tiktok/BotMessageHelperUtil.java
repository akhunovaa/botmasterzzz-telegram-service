package com.botmasterzzz.telegram.controller.telegram.tiktok;

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
        return stringBuilder.toString();
    }
}
