package com.botmasterzzz.telegram.controller.telegram.portfolio;

import com.botmasterzzz.bot.api.impl.methods.send.SendMessage;
import com.botmasterzzz.bot.api.impl.objects.Message;
import com.botmasterzzz.bot.api.impl.objects.Update;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.ReplyKeyboardMarkup;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.buttons.KeyboardRow;
import com.botmasterzzz.telegram.config.annotations.BotController;
import com.botmasterzzz.telegram.config.annotations.BotRequestMapping;
import com.botmasterzzz.telegram.config.context.UserContextHolder;
import com.botmasterzzz.telegram.controller.telegram.tiktok.MainTikTokMenuController;
import com.botmasterzzz.telegram.entity.TelegramBotUserEntity;
import com.botmasterzzz.telegram.service.TelegramMediaService;
import com.botmasterzzz.telegram.service.TelegramUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

@BotController
public class PortfolioMediaFileUploaderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainTikTokMenuController.class);

    @Autowired
    private TelegramMediaService telegramMediaService;

    @Autowired
    private TelegramUserService telegramUserService;

    @Value("${video.file.upload.path}")
    private String path;

    @BotRequestMapping(value = {"portfolio-\uD83C\uDFACЗагрузить", "portfolio-\uD83C\uDFACUpload", "portfolio-\uD83C\uDFAC下載"})
    public List<SendMessage> uploadPhoto(Update update) {
        String name = null != update.getMessage().getFrom().getUserName() ? update.getMessage().getFrom().getUserName() : update.getMessage().getFrom().getFirstName();
        Long requestedUserId = Long.valueOf(update.getMessage().getFrom().getId());
        List<SendMessage> mailingData = new ArrayList<>();
        TelegramBotUserEntity requestedTelegramUser = telegramUserService.getTelegramUser(requestedUserId);
        boolean isRequestedUserAdmin = requestedTelegramUser.isAdmin();

        if (!isRequestedUserAdmin) {
            SendMessage restrictedWarningMessage = new SendMessage();
            restrictedWarningMessage.setChatId(update.getMessage().getChatId());
            restrictedWarningMessage.setText("⚠️ Недостаточно прав");
            restrictedWarningMessage.enableHtml(true);
            LOGGER.info("User id {} restricted to MAKE A MAILING", requestedTelegramUser);
            mailingData.add(restrictedWarningMessage);
            return mailingData;
        }

        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRowLineOne = new KeyboardRow();
        keyboardRowLineOne.add("❌Отмена");
        keyboardRows.add(keyboardRowLineOne);
        keyboard.setKeyboard(keyboardRows);
        UserContextHolder.currentContext().setRemain(true);
        mailingData.add(new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText("<b>" + name + "</b>, отправьте боту видео или фото:\n")
                .setReplyMarkup(keyboard));
        return mailingData;
    }

    @BotRequestMapping(value = "portfolio-/upload")
    public List<SendMessage> uploadVideoFromCommand(Update update) {
        String name = null != update.getMessage().getFrom().getUserName() ? update.getMessage().getFrom().getUserName() : update.getMessage().getFrom().getFirstName();
        Long requestedUserId = Long.valueOf(update.getMessage().getFrom().getId());
        List<SendMessage> mailingData = new ArrayList<>();
        TelegramBotUserEntity requestedTelegramUser = telegramUserService.getTelegramUser(requestedUserId);
        boolean isRequestedUserAdmin = requestedTelegramUser.isAdmin();

        if (!isRequestedUserAdmin) {
            SendMessage restrictedWarningMessage = new SendMessage();
            restrictedWarningMessage.setChatId(update.getMessage().getChatId());
            restrictedWarningMessage.setText("⚠️ Недостаточно прав");
            restrictedWarningMessage.enableHtml(true);
            LOGGER.info("User id {} restricted to MAKE A MAILING", requestedTelegramUser);
            mailingData.add(restrictedWarningMessage);
            return mailingData;
        }
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRowLineOne = new KeyboardRow();
        keyboardRowLineOne.add("❌Отмена");
        keyboardRows.add(keyboardRowLineOne);
        keyboard.setKeyboard(keyboardRows);
        UserContextHolder.currentContext().setRemain(true);
        mailingData.add(new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText("<b>" + name + "</b>, отправьте боту видео или фото:\n")
                .setReplyMarkup(keyboard));
        return mailingData;
    }

    @BotRequestMapping(value = "portfolio-❌Отмена")
    public SendMessage uploadCancel(Update update) {
        String name = null != update.getMessage().getFrom().getUserName() ? update.getMessage().getFrom().getUserName() : update.getMessage().getFrom().getFirstName();
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
        UserContextHolder.currentContext().setRemain(false);
        UserContextHolder.currentContext().setAnon(false);
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

    @BotRequestMapping(value = "portfolio-media-upload-error")
    public SendMessage uploadError(Update update) {
        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText("❗️<b>Ошибка</b>\n Пожалуйста, попробуйте заново. Принимаются только медиа файлы: фото\n");
    }

    @BotRequestMapping(value = "portfolio-media-upload")
    public SendMessage uploadWait(Update update) {
        String name = null != update.getMessage().getFrom().getUserName() ? update.getMessage().getFrom().getUserName() : update.getMessage().getFrom().getFirstName();
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
        boolean isAnon = UserContextHolder.currentContext().isAnon();
        Message message = update.getMessage();
        Long telegramUserId = Long.valueOf(update.getMessage().getFrom().getId());
        String caption = message.getCaption();
        if (message.hasPhoto()) {
            telegramMediaService.portfolioMediaAdd(message.getPhoto(), telegramUserId, isAnon, caption);
        }
        UserContextHolder.currentContext().setRemain(false);
        UserContextHolder.currentContext().setAnon(false);
        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText("<b>" + name + "</b>, мы получили и загрузили Ваш медиа файл.\n")
                .setReplyMarkup(keyboard);
    }
}
