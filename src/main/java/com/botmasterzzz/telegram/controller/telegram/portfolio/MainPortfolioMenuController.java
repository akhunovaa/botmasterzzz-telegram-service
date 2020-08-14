package com.botmasterzzz.telegram.controller.telegram.portfolio;

import com.botmasterzzz.bot.api.impl.methods.send.SendMessage;
import com.botmasterzzz.bot.api.impl.objects.Update;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.ReplyKeyboardMarkup;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.buttons.KeyboardRow;
import com.botmasterzzz.telegram.config.annotations.BotController;
import com.botmasterzzz.telegram.config.annotations.BotRequestMapping;
import com.botmasterzzz.telegram.entity.TelegramBotUserEntity;
import com.botmasterzzz.telegram.service.TelegramUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@BotController
public class MainPortfolioMenuController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainPortfolioMenuController.class);

    @Autowired
    private TelegramUserService telegramUserService;

    @BotRequestMapping(value = "portfolio-/start")
    public SendMessage start(Update update) {
        String name = null != update.getMessage().getFrom().getUserName() ? update.getMessage().getFrom().getUserName() : update.getMessage().getFrom().getFirstName();
        Long chatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        Long requestedUserId = Long.valueOf(update.getMessage().getFrom().getId());

        TelegramBotUserEntity requestedTelegramUser = telegramUserService.getTelegramUser(requestedUserId);
        boolean isRequestedUserAdmin = requestedTelegramUser.isAdmin();

        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRowLineOne = new KeyboardRow();
        KeyboardRow keyboardRowLineTwo = new KeyboardRow();
        KeyboardRow keyboardRowLineThree = new KeyboardRow();
        KeyboardRow keyboardRowLineFourth = new KeyboardRow();
        keyboardRowLineOne.add("\uD83D\uDCF2Фото");
        if (isRequestedUserAdmin){
            keyboardRowLineTwo.add("\uD83C\uDFACЗагрузить");
            keyboardRowLineThree.add("\uD83C\uDF81Мои медиа");
        }
        keyboardRowLineFourth.add("ℹ️Информация");
        keyboardRows.add(keyboardRowLineOne);
        if (isRequestedUserAdmin){
            keyboardRows.add(keyboardRowLineTwo);
            keyboardRows.add(keyboardRowLineThree);
        }
        keyboardRows.add(keyboardRowLineFourth);
        keyboard.setKeyboard(keyboardRows);
        keyboard.setOneTimeKeyboard(Boolean.TRUE);
        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText("<b>Добро пожаловать</b>\uD83E\uDD29, " + name + ".\n" +
                        "\n" +
                        "\uD83D\uDD25 <b>Новости</b> \uD83D\uDD25 \n" + BotMessageHelperUtil.getNewsMessage() +
                        "\n" +
                        "❗️/start для перехода на главное меню\n" +
                        "\uD83D\uDCF8 /photo для получения случайного фото/картинки от бота\n" +
                        "\uD83D\uDCE4 /upload для чтобы загрузить фото\n" +
                        "\n")
                .setReplyMarkup(keyboard);
    }

    @BotRequestMapping(value = {"portfolio-ℹ️Информация", "portfolio-ℹ️Info", "portfolio-ℹ️信息"})
    public SendMessage contacts(Update update) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<b>Адрес:</b>\n");
        stringBuilder.append("г. Москва, Варшавское шоссе\n");
        stringBuilder.append("\n");
        stringBuilder.append("<b>Телеграмм:</b>\n");
        stringBuilder.append("@leon4uk\n");
        stringBuilder.append("\n");
        stringBuilder.append("<b>Разработан при помощи:</b>\n");
        stringBuilder.append("http://botmasterzzz.ru\n");
        stringBuilder.append("https://botmasterzzz.com\n");
        stringBuilder.append("\n");
        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText(stringBuilder.toString());
    }
}
