package com.botmasterzzz.telegram.controller.telegram.tiktok;

import com.botmasterzzz.bot.api.impl.methods.send.SendMessage;
import com.botmasterzzz.bot.api.impl.objects.Message;
import com.botmasterzzz.bot.api.impl.objects.Update;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.ReplyKeyboardMarkup;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.buttons.KeyboardRow;
import com.botmasterzzz.telegram.config.annotations.BotController;
import com.botmasterzzz.telegram.config.annotations.BotRequestMapping;
import com.botmasterzzz.telegram.config.context.UserContextHolder;
import com.botmasterzzz.telegram.service.TelegramMediaService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@BotController
public class TikTokMediaFileUploaderController {

    @Autowired
    private TelegramMediaService telegramMediaService;

    @BotRequestMapping(value = "tiktok-\uD83C\uDFACЗагрузить видео/фото")
    public SendMessage uploadPhoto(Update update) {
        String name = null != update.getMessage().getFrom().getUserName() ? update.getMessage().getFrom().getUserName() : update.getMessage().getFrom().getFirstName();
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRowLineOne = new KeyboardRow();
        keyboardRowLineOne.add("❌Отмена");
        keyboardRows.add(keyboardRowLineOne);
        keyboard.setKeyboard(keyboardRows);
        UserContextHolder.currentContext().setRemain(true);
        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText("<b>" + name + "</b>, отправьте боту видео или фото:\n")
                .setReplyMarkup(keyboard);
    }

    @BotRequestMapping(value = "tiktok-\uD83C\uDFACЗагрузить видео/фото(анонимно)")
    public SendMessage uploadPhotoAnonymous(Update update) {
        String name = null != update.getMessage().getFrom().getUserName() ? update.getMessage().getFrom().getUserName() : update.getMessage().getFrom().getFirstName();
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRowLineOne = new KeyboardRow();
        keyboardRowLineOne.add("❌Отмена");
        keyboardRows.add(keyboardRowLineOne);
        keyboard.setKeyboard(keyboardRows);
        UserContextHolder.currentContext().setRemain(true);
        UserContextHolder.currentContext().setAnon(true);
        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText("<b>" + name + "</b>, отправьте боту видео или фото:\n")
                .setReplyMarkup(keyboard);
    }

    @BotRequestMapping(value = "tiktok-/upload")
    public SendMessage uploadVideoFromCommand(Update update) {
        String name = null != update.getMessage().getFrom().getUserName() ? update.getMessage().getFrom().getUserName() : update.getMessage().getFrom().getFirstName();
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRowLineOne = new KeyboardRow();
        keyboardRowLineOne.add("❌Отмена");
        keyboardRows.add(keyboardRowLineOne);
        keyboard.setKeyboard(keyboardRows);
        UserContextHolder.currentContext().setRemain(true);
        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText("<b>" + name + "</b>, отправьте боту видео или фото:\n")
                .setReplyMarkup(keyboard);
    }

    @BotRequestMapping(value = "tiktok-❌Отмена")
    public SendMessage uploadCancel(Update update) {
        String name = null != update.getMessage().getFrom().getUserName() ? update.getMessage().getFrom().getUserName() : update.getMessage().getFrom().getFirstName();
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRowLineOne = new KeyboardRow();
        KeyboardRow keyboardRowLineTwo = new KeyboardRow();
        KeyboardRow keyboardRowLineThree = new KeyboardRow();
        KeyboardRow keyboardRowLineFourth = new KeyboardRow();
        keyboardRowLineOne.add("\uD83D\uDCF2Видео");
        keyboardRowLineOne.add("\uD83D\uDCF2Фото");
        keyboardRowLineTwo.add("\uD83C\uDFACЗагрузить видео/фото");
        keyboardRowLineTwo.add("\uD83C\uDFACЗагрузить видео/фото(анонимно)");
        keyboardRowLineThree.add("\uD83D\uDC8EРейтинг");
        keyboardRowLineThree.add("\uD83C\uDF81Мои загрузки");
        keyboardRowLineFourth.add("\uD83D\uDCD2Контакты");
        keyboardRows.add(keyboardRowLineOne);
        keyboardRows.add(keyboardRowLineTwo);
        keyboardRows.add(keyboardRowLineThree);
        keyboardRows.add(keyboardRowLineFourth);
        keyboard.setKeyboard(keyboardRows);
        UserContextHolder.currentContext().setRemain(false);
        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText("<b>Здесь вы можете выложить \uD83D\uDCF9 фото/видео и оценивать \uD83D\uDCF8 фото/видео от других пользователей. С нами всегда интересно</b>\uD83E\uDD29, " + name + ".\n" +
                        "\n" +
                        "❗️/start для перехода на главное меню\n" +
                        "\uD83D\uDCF9 /video для получения случайного видео от бота\n" +
                        "\uD83D\uDCF8 /photo для получения случайного фото/картинки от бота\n" +
                        "\uD83D\uDCE4 /upload для чтобы загрузить видео/фото\n" +
                        "\n" +
                        "или \n" +
                        "<b>Выберите раздел ниже:</b> \uD83D\uDD3D")
                .setReplyMarkup(keyboard);
    }

    @BotRequestMapping(value = "tiktok-media-upload-error")
    public SendMessage uploadError(Update update) {
        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText("❗️<b>Ошибка</b>\n Пожалуйста, попробуйте заново. Принимаются только медиа файлы: фото, видео\n");
    }

    @BotRequestMapping(value = "tiktok-media-upload")
    public SendMessage uploadWait(Update update) {
        String name = null != update.getMessage().getFrom().getUserName() ? update.getMessage().getFrom().getUserName() : update.getMessage().getFrom().getFirstName();
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRowLineOne = new KeyboardRow();
        KeyboardRow keyboardRowLineTwo = new KeyboardRow();
        KeyboardRow keyboardRowLineThree = new KeyboardRow();
        KeyboardRow keyboardRowLineFourth = new KeyboardRow();
        keyboardRowLineOne.add("\uD83D\uDCF2Видео");
        keyboardRowLineOne.add("\uD83D\uDCF2Фото");
        keyboardRowLineTwo.add("\uD83C\uDFACЗагрузить видео/фото");
        keyboardRowLineTwo.add("\uD83C\uDFACЗагрузить видео/фото(анонимно)");
        keyboardRowLineThree.add("\uD83D\uDC8EРейтинг");
        keyboardRowLineThree.add("\uD83C\uDF81Мои загрузки");
        keyboardRowLineFourth.add("\uD83D\uDCD2Контакты");
        keyboardRows.add(keyboardRowLineOne);
        keyboardRows.add(keyboardRowLineTwo);
        keyboardRows.add(keyboardRowLineThree);
        keyboardRows.add(keyboardRowLineFourth);
        keyboard.setKeyboard(keyboardRows);
        boolean isAnon = UserContextHolder.currentContext().isAnon();
        Message message = update.getMessage();
        Long telegramUserId = Long.valueOf(update.getMessage().getFrom().getId());
        String caption = message.getCaption();
        if (message.hasPhoto()){
            telegramMediaService.telegramUserMediaAdd(message.getPhoto(), telegramUserId, isAnon, caption);
        }else if(message.hasVideo()){
            telegramMediaService.telegramUserMediaAdd(message.getVideo(), telegramUserId, isAnon, caption);
        }else if(message.hasDocument()){
            telegramMediaService.telegramUserMediaAdd(message.getDocument(), telegramUserId, isAnon, caption);
        }
        UserContextHolder.currentContext().setRemain(false);
        UserContextHolder.currentContext().setAnon(false);
        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText("<b>" + name + "</b>, мы получили и загрузили Ваш медиа файл.\n")
                .setReplyMarkup(keyboard);
    }
}
