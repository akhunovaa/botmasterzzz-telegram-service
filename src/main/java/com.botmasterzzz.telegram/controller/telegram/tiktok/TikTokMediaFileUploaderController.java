package com.botmasterzzz.telegram.controller.telegram.tiktok;

import com.botmasterzzz.bot.api.impl.methods.send.SendMessage;
import com.botmasterzzz.bot.api.impl.methods.send.SendVideo;
import com.botmasterzzz.bot.api.impl.objects.Message;
import com.botmasterzzz.bot.api.impl.objects.Update;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.ReplyKeyboardMarkup;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.buttons.KeyboardRow;
import com.botmasterzzz.telegram.config.annotations.BotController;
import com.botmasterzzz.telegram.config.annotations.BotRequestMapping;
import com.botmasterzzz.telegram.config.context.UserContextHolder;
import com.botmasterzzz.telegram.dto.ReceivedMediaFile;
import com.botmasterzzz.telegram.service.TelegramMediaService;
import com.botmasterzzz.telegram.service.YoutubeMediaGrabberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@BotController
public class TikTokMediaFileUploaderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainTikTokMenuController.class);

    @Autowired
    private TelegramMediaService telegramMediaService;

    @Autowired
    private YoutubeMediaGrabberService youtubeMediaGrabberService;

    @Value("${video.file.upload.path}")
    private String path;

    @BotRequestMapping(value = "tiktok-\uD83C\uDFACЗагрузить")
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
                .setText("<b>" + name + "</b>, отправьте боту видео или фото\n(пример для YouTube ссылок <code>https://youtube.com/watch?v=icZotxynzJI\nhttps://youtu.be/MqNHFkhA1y8</code>):\n")
                .setReplyMarkup(keyboard);
    }

    @BotRequestMapping(value = "tiktok-\uD83C\uDFACЗагрузить видео/фото")
    public SendMessage uploadPhotoOld(Update update) {
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
        keyboardRowLineOne.add("\uD83D\uDCF2Видео");
        keyboardRowLineOne.add("\uD83D\uDCF2Фото");
        keyboardRowLineTwo.add("\uD83C\uDFACЗагрузить");
        keyboardRowLineTwo.add("\uD83C\uDFACЗагрузить (анонимно)");
        keyboardRowLineThree.add("\uD83D\uDC8EТОП");
//        keyboardRowLineThree.add("\uD83C\uDF81Мои медиа");
        keyboardRowLineFourth.add("\uD83D\uDCD2Контакты");
        keyboardRows.add(keyboardRowLineTop);
        keyboardRows.add(keyboardRowLineOne);
        keyboardRows.add(keyboardRowLineTwo);
        keyboardRows.add(keyboardRowLineThree);
        keyboardRows.add(keyboardRowLineFourth);
        keyboard.setKeyboard(keyboardRows);

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

    @BotRequestMapping(value = "tiktok-\uD83C\uDFACЗагрузить фото")
    public SendMessage uploadPhotoBackup(Update update) {
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
        keyboardRowLineOne.add("\uD83D\uDCF2Видео");
        keyboardRowLineOne.add("\uD83D\uDCF2Фото");
        keyboardRowLineTwo.add("\uD83C\uDFACЗагрузить");
        keyboardRowLineTwo.add("\uD83C\uDFACЗагрузить (анонимно)");
        keyboardRowLineThree.add("\uD83D\uDC8EТОП");
//        keyboardRowLineThree.add("\uD83C\uDF81Мои медиа");
        keyboardRowLineFourth.add("\uD83D\uDCD2Контакты");
        keyboardRows.add(keyboardRowLineTop);
        keyboardRows.add(keyboardRowLineOne);
        keyboardRows.add(keyboardRowLineTwo);
        keyboardRows.add(keyboardRowLineThree);
        keyboardRows.add(keyboardRowLineFourth);
        keyboard.setKeyboard(keyboardRows);

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

    @BotRequestMapping(value = "tiktok-\uD83C\uDFACЗагрузить видео")
    public SendMessage uploadVideo(Update update) {
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
        keyboardRowLineOne.add("\uD83D\uDCF2Видео");
        keyboardRowLineOne.add("\uD83D\uDCF2Фото");
        keyboardRowLineTwo.add("\uD83C\uDFACЗагрузить");
        keyboardRowLineTwo.add("\uD83C\uDFACЗагрузить (анонимно)");
        keyboardRowLineThree.add("\uD83D\uDC8EТОП");
//        keyboardRowLineThree.add("\uD83C\uDF81Мои медиа");
        keyboardRowLineFourth.add("\uD83D\uDCD2Контакты");
        keyboardRows.add(keyboardRowLineTop);
        keyboardRows.add(keyboardRowLineOne);
        keyboardRows.add(keyboardRowLineTwo);
        keyboardRows.add(keyboardRowLineThree);
        keyboardRows.add(keyboardRowLineFourth);
        keyboard.setKeyboard(keyboardRows);

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

    @BotRequestMapping(value = "tiktok-\uD83C\uDFACЗагрузить (анонимно)")
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
                .setText("<b>" + name + "</b>, отправьте боту видео или фото\n(пример для YouTube ссылок <code>https://youtube.com/watch?v=icZotxynzJI\nhttps://youtu.be/MqNHFkhA1y8</code>):\n")
                .setReplyMarkup(keyboard);
    }

    @BotRequestMapping(value = "tiktok-\uD83C\uDFACЗагрузить видео/фото(анонимно)")
    public SendMessage uploadPhotoAnonymousBackupTwo(Update update) {
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
        keyboardRowLineOne.add("\uD83D\uDCF2Видео");
        keyboardRowLineOne.add("\uD83D\uDCF2Фото");
        keyboardRowLineTwo.add("\uD83C\uDFACЗагрузить");
        keyboardRowLineTwo.add("\uD83C\uDFACЗагрузить (анонимно)");
        keyboardRowLineThree.add("\uD83D\uDC8EТОП");
//        keyboardRowLineThree.add("\uD83C\uDF81Мои медиа");
        keyboardRowLineFourth.add("\uD83D\uDCD2Контакты");
        keyboardRows.add(keyboardRowLineTop);
        keyboardRows.add(keyboardRowLineOne);
        keyboardRows.add(keyboardRowLineTwo);
        keyboardRows.add(keyboardRowLineThree);
        keyboardRows.add(keyboardRowLineFourth);
        keyboard.setKeyboard(keyboardRows);

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

    @BotRequestMapping(value = "tiktok-\uD83C\uDFACЗагрузить фото(анонимно)")
    public SendMessage uploadPhotoAnonymousBackup(Update update) {
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
                .setText("<b>" + name + "</b>, отправьте боту видео или фото\n(пример для YouTube ссылок <code>https://youtube.com/watch?v=icZotxynzJI\nhttps://youtu.be/MqNHFkhA1y8</code>):\n")
                .setReplyMarkup(keyboard);
    }

    @BotRequestMapping(value = "tiktok-\uD83C\uDFACЗагрузить видео(анонимно)")
    public SendMessage uploadVideoAnonymousBackup(Update update) {
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
                .setText("<b>" + name + "</b>, отправьте боту видео или фото\n(пример для YouTube ссылок <code>https://youtube.com/watch?v=icZotxynzJI\nhttps://youtu.be/MqNHFkhA1y8</code>):\n")
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
                .setText("<b>" + name + "</b>, отправьте боту видео или фото\n(пример для YouTube ссылок <code>https://youtube.com/watch?v=icZotxynzJI\nhttps://youtu.be/MqNHFkhA1y8</code>):\n")
                .setReplyMarkup(keyboard);
    }

    @BotRequestMapping(value = "tiktok-❌Отмена")
    public SendMessage uploadCancel(Update update) {
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
        keyboardRowLineOne.add("\uD83D\uDCF2Видео");
        keyboardRowLineOne.add("\uD83D\uDCF2Фото");
        keyboardRowLineTwo.add("\uD83C\uDFACЗагрузить");
        keyboardRowLineTwo.add("\uD83C\uDFACЗагрузить (анонимно)");
        keyboardRowLineThree.add("\uD83D\uDC8EТОП");
//        keyboardRowLineThree.add("\uD83C\uDF81Мои видео/фото");
        keyboardRowLineFourth.add("\uD83D\uDCD2Контакты");
        keyboardRows.add(keyboardRowLineTop);
        keyboardRows.add(keyboardRowLineOne);
        keyboardRows.add(keyboardRowLineTwo);
        keyboardRows.add(keyboardRowLineThree);
        keyboardRows.add(keyboardRowLineFourth);
        keyboard.setKeyboard(keyboardRows);
        UserContextHolder.currentContext().setRemain(false);
        UserContextHolder.currentContext().setAnon(false);
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
        KeyboardRow keyboardRowLineTop = new KeyboardRow();
        KeyboardRow keyboardRowLineOne = new KeyboardRow();
        KeyboardRow keyboardRowLineTwo = new KeyboardRow();
        KeyboardRow keyboardRowLineThree = new KeyboardRow();
        KeyboardRow keyboardRowLineFourth = new KeyboardRow();
        keyboardRowLineTop.add("\uD83C\uDD95Новое за сегодня");
        keyboardRowLineOne.add("\uD83D\uDCF2Видео");
        keyboardRowLineOne.add("\uD83D\uDCF2Фото");
        keyboardRowLineTwo.add("\uD83C\uDFACЗагрузить");
        keyboardRowLineTwo.add("\uD83C\uDFACЗагрузить (анонимно)");
        keyboardRowLineThree.add("\uD83D\uDC8EТОП");
//        keyboardRowLineThree.add("\uD83C\uDF81Мои видео/фото");
        keyboardRowLineFourth.add("\uD83D\uDCD2Контакты");
        keyboardRows.add(keyboardRowLineTop);
        keyboardRows.add(keyboardRowLineOne);
        keyboardRows.add(keyboardRowLineTwo);
        keyboardRows.add(keyboardRowLineThree);
        keyboardRows.add(keyboardRowLineFourth);
        keyboard.setKeyboard(keyboardRows);
        boolean isAnon = UserContextHolder.currentContext().isAnon();
        Message message = update.getMessage();
        Long telegramUserId = Long.valueOf(update.getMessage().getFrom().getId());
        String caption = message.getCaption();
        if (message.hasPhoto()) {
            telegramMediaService.telegramUserMediaAdd(message.getPhoto(), telegramUserId, isAnon, caption);
        } else if (message.hasVideo()) {
            telegramMediaService.telegramUserMediaAdd(message.getVideo(), telegramUserId, isAnon, caption);
        } else if (message.hasDocument()) {
            telegramMediaService.telegramUserMediaAdd(message.getDocument(), telegramUserId, isAnon, caption);
        }
        UserContextHolder.currentContext().setRemain(false);
        UserContextHolder.currentContext().setAnon(false);
        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText("<b>" + name + "</b>, мы получили и загрузили Ваш медиа файл.\n")
                .setReplyMarkup(keyboard);
    }

    @BotRequestMapping(value = "tiktok-media-upload-link")
    public SendVideo uploadWaitFromLink(Update update) {
        String name = null != update.getMessage().getFrom().getUserName() ? update.getMessage().getFrom().getUserName() : update.getMessage().getFrom().getFirstName();
        Long chatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        String text = update.hasMessage() ? update.getMessage().getText() : update.getCallbackQuery().getMessage().getText();
        String videoId;
        if (text.contains("youtu.be")){
            videoId = text.substring(text.indexOf("youtu.be/") + 9);
        }else {
            videoId = text.substring(text.indexOf("v=") + 2);
        }
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRowLineTop = new KeyboardRow();
        KeyboardRow keyboardRowLineOne = new KeyboardRow();
        KeyboardRow keyboardRowLineTwo = new KeyboardRow();
        KeyboardRow keyboardRowLineThree = new KeyboardRow();
        KeyboardRow keyboardRowLineFourth = new KeyboardRow();
        keyboardRowLineTop.add("\uD83C\uDD95Новое за сегодня");
        keyboardRowLineOne.add("\uD83D\uDCF2Видео");
        keyboardRowLineOne.add("\uD83D\uDCF2Фото");
        keyboardRowLineTwo.add("\uD83C\uDFACЗагрузить");
        keyboardRowLineTwo.add("\uD83C\uDFACЗагрузить (анонимно)");
        keyboardRowLineThree.add("\uD83D\uDC8EТОП");
        keyboardRowLineFourth.add("\uD83D\uDCD2Контакты");
        keyboardRows.add(keyboardRowLineTop);
        keyboardRows.add(keyboardRowLineOne);
        keyboardRows.add(keyboardRowLineTwo);
        keyboardRows.add(keyboardRowLineThree);
        keyboardRows.add(keyboardRowLineFourth);
        keyboard.setKeyboard(keyboardRows);
        boolean isAnon = UserContextHolder.currentContext().isAnon();
        Long telegramUserId = Long.valueOf(update.getMessage().getFrom().getId());
        ReceivedMediaFile receivedMediaFile = youtubeMediaGrabberService.downloadVideo(videoId);
        String caption = "<b>" + receivedMediaFile.getTitle() + "</b>\n" + receivedMediaFile.getDescription();
        String filePath = path + "/" + videoId + ".mp4";
        telegramMediaService.telegramUserMediaAdd(filePath, telegramUserId, isAnon, caption);
        LOGGER.info("User id {} sent video message {}", chatId, videoId);
        UserContextHolder.currentContext().setRemain(false);
        UserContextHolder.currentContext().setAnon(false);
        return new SendVideo()
                .setVideo(receivedMediaFile.getFile().getAbsolutePath())
                .setChatId(update.getMessage().getChatId())
                .setParseMode("HTML")
                .setCaption("<b>" + name + "</b>, мы получили и загрузили Ваш медиа файл.\n" + caption)
                .setReplyMarkup(keyboard);
    }
}
