package com.botmasterzzz.telegram.controller.telegram.tiktok;

import com.botmasterzzz.bot.api.impl.methods.send.SendMessage;
import com.botmasterzzz.bot.api.impl.objects.Update;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.ReplyKeyboardMarkup;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.buttons.KeyboardRow;
import com.botmasterzzz.telegram.config.annotations.BotController;
import com.botmasterzzz.telegram.config.annotations.BotRequestMapping;
import com.botmasterzzz.telegram.entity.TelegramBotUserEntity;
import com.botmasterzzz.telegram.service.TelegramUserService;
import com.botmasterzzz.telegram.service.YoutubeMediaGrabberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@BotController
public class MainTikTokMenuController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainTikTokMenuController.class);

    @Autowired
    private YoutubeMediaGrabberService youtubeMediaGrabberService;

    @Autowired
    private TelegramUserService telegramUserService;

    @BotRequestMapping(value = "tiktok-/start")
    public SendMessage start(Update update) {
        String name = null != update.getMessage().getFrom().getUserName() ? update.getMessage().getFrom().getUserName() : update.getMessage().getFrom().getFirstName();
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRowLineTop = new KeyboardRow();
        KeyboardRow keyboardRowLineOne = new KeyboardRow();
        KeyboardRow keyboardRowLineTwo = new KeyboardRow();
        KeyboardRow keyboardRowLineThree = new KeyboardRow();
        KeyboardRow keyboardRowLineFourth = new KeyboardRow();
        keyboardRowLineTop.add("\uD83C\uDD95Новое за сегодня");
        keyboardRowLineTop.add("\uD83C\uDF88Новое за вчера");
        keyboardRowLineOne.add("\uD83D\uDCF2Видео");
        keyboardRowLineOne.add("\uD83D\uDCF2Фото");
        keyboardRowLineTwo.add("\uD83C\uDFACЗагрузить");
        keyboardRowLineTwo.add("\uD83C\uDFACЗагрузить (анонимно)");
        keyboardRowLineThree.add("\uD83E\uDD1DМои друзья");
        keyboardRowLineThree.add("\uD83C\uDF81Мои медиа");
        keyboardRowLineFourth.add("\uD83D\uDC8EТОП");
        keyboardRowLineFourth.add("ℹ️Информация");
        keyboardRows.add(keyboardRowLineTop);
        keyboardRows.add(keyboardRowLineOne);
        keyboardRows.add(keyboardRowLineTwo);
        keyboardRows.add(keyboardRowLineThree);
        keyboardRows.add(keyboardRowLineFourth);
        keyboard.setKeyboard(keyboardRows);
        keyboard.setOneTimeKeyboard(Boolean.TRUE);
        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText("<b>Здесь вы можете выложить любое свое \uD83D\uDCF9 фото или видео, а также оценить и других пользователей. С нами всегда интересно</b>\uD83E\uDD29, " + name + ".\n" +
                        "\n" +
                        "\uD83D\uDD25 <b>Новости</b> \uD83D\uDD25 \n" + BotMessageHelperUtil.getNewsMessage() +
                        "\n" +
                        "❗️/start для перехода на главное меню\n" +
                        "\uD83D\uDCF9 /video для получения случайного видео от бота\n" +
                        "\uD83D\uDCF8 /photo для получения случайного фото/картинки от бота\n" +
                        "\uD83D\uDCE4 /upload для чтобы загрузить видео/фото\n" +
                        "\n")
                .setReplyMarkup(keyboard);
    }

    @BotRequestMapping(value = {"tiktok-❓Помощь", "tiktok-❓Help", "tiktok-❓救命"})
    public SendMessage help(Update update) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\uD83D\uDCF2<b>Новое видео/фото</b>\n");
        stringBuilder.append("Нажмите на эту кнопку, для того чтобы запросить новое случайное видео/фото случайного пользователя, загрузившего медиа файл.\n");
        stringBuilder.append("\n");
        stringBuilder.append("\uD83D\uDCD2<b>Контакты</b>\n");
        stringBuilder.append("Нажмите на эту кнопку, для получения списка наших контактных данных.\n");
        stringBuilder.append("\n");
        stringBuilder.append("❓<b>Помощь</b>\n");
        stringBuilder.append("Раздел помощи.\n");
        stringBuilder.append("\n");
        stringBuilder.append("\uD83C\uDFACЗагрузить фото\n");
        stringBuilder.append("Здесь вы можете загрузить видео или фото.\n");
        stringBuilder.append("\n");
        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText(stringBuilder.toString());
    }

    @BotRequestMapping(value = {"tiktok-ℹ️Информация", "tiktok-ℹ️Info", "tiktok-ℹ️信息"})
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

    @BotRequestMapping(value = {"tiktok-\uD83E\uDD1DМои друзья", "tiktok-\uD83E\uDD1DMy friends", "tiktok-\uD83E\uDD1D我的朋友"})
    public SendMessage friends(Update update) {
        Long requestedUserId = Long.valueOf(update.getMessage().getFrom().getId());
        TelegramBotUserEntity requestedTelegramUser = telegramUserService.getTelegramUser(requestedUserId);
        TelegramBotUserEntity referralUser = null;
        if(null != requestedTelegramUser.getReferralId()){
            referralUser = telegramUserService.getTelegramUser(requestedTelegramUser.getReferralId());
        }
        String referralName;
        if (null != referralUser){
            referralName = null != referralUser.getUsername() ? referralUser.getUsername(): referralUser.getFirstName();
        }else {
            referralName = "Незнакомец";
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\uD83D\uDCA3 <b>Партнёрская акция</b>\uD83D\uDCA3 \n");

        stringBuilder.append("\n");
        stringBuilder.append("➖➖➖➖➖➖➖➖➖➖\n");
        if (null != referralUser){
            stringBuilder.append("\uD83D\uDC64 Вас привел: ")
                    .append("<a href=\"tg://user?id=")
                    .append(referralUser.getTelegramId())
                    .append("\"><b>").append(referralName).append("</b></a> \n");
        }else {
            stringBuilder.append("\uD83D\uDC64 Вас привел: ").append(referralName).append("\n");
        }
        stringBuilder.append("➖➖➖➖➖➖➖➖➖➖\n");
        stringBuilder.append("\uD83D\uDD17 <b>Ваша партнёрская ссылка:</b>\n");
        stringBuilder.append("https://t.me/tiktiktokrobot?start=").append(requestedUserId).append("\n");
        stringBuilder.append("\n");
        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText(stringBuilder.toString()).enableHtml(true).setParseMode("HTML");
    }

    @BotRequestMapping(value = "tiktok-youtube")
    public SendMessage youtube(Update update) {
        StringBuilder stringBuilder = new StringBuilder();
        youtubeMediaGrabberService.downloadVideo("LbY3DdzV0rA");
        stringBuilder.append("<b>Адрес:</b>\n");
        stringBuilder.append("г. Москва Варшавское шоссе\n");
        stringBuilder.append("\n");
        stringBuilder.append("<b>Телеграмм:</b>\n");
        stringBuilder.append("@leon4uk\n");
        stringBuilder.append("\n");
        stringBuilder.append("<b>Разработан при помощи:</b>\n");
        stringBuilder.append("https://botmasterzzz.com\n");
        stringBuilder.append("\n");
        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText(stringBuilder.toString());
    }
}
