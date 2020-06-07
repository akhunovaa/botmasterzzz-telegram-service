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

    @BotRequestMapping(value = "tiktok-\uD83C\uDFACЗагрузить фото")
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

    @BotRequestMapping(value = "tiktok-\uD83C\uDFACЗагрузить видео")
    public SendMessage uploadVideo(Update update) {
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
        KeyboardRow keyboardRowLineThree = new KeyboardRow();
        KeyboardRow keyboardRowLineFourth = new KeyboardRow();
        keyboardRowLineOne.add("\uD83D\uDCF2Видео");
        keyboardRowLineOne.add("\uD83D\uDCF2Фото");
        keyboardRowLineThree.add("\uD83C\uDFACЗагрузить видео");
        keyboardRowLineThree.add("\uD83C\uDFACЗагрузить фото");
        keyboardRowLineFourth.add("\uD83D\uDCD2Контакты");
        keyboardRows.add(keyboardRowLineOne);
        keyboardRows.add(keyboardRowLineThree);
        keyboardRows.add(keyboardRowLineFourth);
        keyboard.setKeyboard(keyboardRows);
        UserContextHolder.currentContext().setRemain(false);
        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText("<b>Здесь вы можете выложить видео и оценивать видео других. С нами всегда интересно</b>\uD83E\uDD29, " + name + ".\n" +
                        "Выберите раздел: \uD83D\uDD3D")
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
        KeyboardRow keyboardRowLineThree = new KeyboardRow();
        KeyboardRow keyboardRowLineFourth = new KeyboardRow();
        keyboardRowLineOne.add("\uD83D\uDCF2Видео");
        keyboardRowLineOne.add("\uD83D\uDCF2Фото");
        keyboardRowLineThree.add("\uD83C\uDFACЗагрузить видео");
        keyboardRowLineThree.add("\uD83C\uDFACЗагрузить фото");
        keyboardRowLineFourth.add("\uD83D\uDCD2Контакты");
        keyboardRows.add(keyboardRowLineOne);
        keyboardRows.add(keyboardRowLineThree);
        keyboardRows.add(keyboardRowLineFourth);
        keyboard.setKeyboard(keyboardRows);

        Message message = update.getMessage();
        Long telegramUserId = Long.valueOf(update.getMessage().getFrom().getId());
        if (message.hasPhoto()){
            telegramMediaService.telegramUserMediaAdd(message.getPhoto(), telegramUserId);
        }else if(message.hasVideo()){
            telegramMediaService.telegramUserMediaAdd(message.getVideo(), telegramUserId);
        }else if(message.hasDocument()){
            telegramMediaService.telegramUserMediaAdd(message.getDocument(), telegramUserId);
        }
        UserContextHolder.currentContext().setRemain(false);
        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText("<b>" + name + "</b>, мы получили и загрузили Ваш медиа файл.\n")
                .setReplyMarkup(keyboard);
    }
}
