package com.botmasterzzz.telegram.controller.telegram.getparts;

import com.botmasterzzz.bot.api.impl.methods.send.SendMessage;
import com.botmasterzzz.bot.api.impl.objects.Update;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.InlineKeyboardMarkup;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.ReplyKeyboardMarkup;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.buttons.KeyboardRow;
import com.botmasterzzz.telegram.config.annotations.BotController;
import com.botmasterzzz.telegram.config.annotations.BotRequestMapping;
import com.botmasterzzz.telegram.config.context.UserContextHolder;
import com.botmasterzzz.telegram.entity.GetPartsEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@BotController
public class MainGetPartsMenuController {

    private static final Logger logger = LoggerFactory.getLogger(MainGetPartsMenuController.class);

    @Autowired
    private GetPartsMessageService getPartsMessageService;

    @BotRequestMapping(value = "getparts-/start")
    public SendMessage start(Update update) {
        String name = null != update.getMessage().getFrom().getUserName() ? update.getMessage().getFrom().getUserName() : update.getMessage().getFrom().getFirstName();
        ReplyKeyboardMarkup keyboard = getMainPageKeyboard();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<b>Добро пожаловать!</b>\n");
        stringBuilder.append("Приветствуем Вас у себя в онлайн магазине Get Parts, ").append(name).append("! \n");
        stringBuilder.append("\n");
        stringBuilder.append("Выберите раздел: \uD83D\uDD3D");
        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText(stringBuilder.toString())
                .setReplyMarkup(keyboard);
    }

    @BotRequestMapping(value = "getparts-\uD83D\uDDA5Главное меню")
    public SendMessage main(Update update) {
        String name = null != update.getMessage().getFrom().getUserName() ? update.getMessage().getFrom().getUserName() : update.getMessage().getFrom().getFirstName();
        ReplyKeyboardMarkup keyboard = getMainPageKeyboard();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<b>Добро пожаловать!</b>\n");
        stringBuilder.append("Приветствуем Вас у себя в онлайн магазине Get Parts, ").append(name).append("! \n");
        stringBuilder.append("\n");
        stringBuilder.append("Выберите раздел: \uD83D\uDD3D");
        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText(stringBuilder.toString())
                .setReplyMarkup(keyboard);
    }

    @BotRequestMapping(value = "getparts-\uD83D\uDCC4Новости")
    public SendMessage news(Update update) {
        ReplyKeyboardMarkup keyboard = getMainPageKeyboard();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<b>15 июля 2019г. Запуск бота!</b>\n");
        stringBuilder.append("<b>Запуск бота!</b>\n");
        stringBuilder.append("Наш портал запущен. Добро пожаловать! \n");
        stringBuilder.append("\n");
        stringBuilder.append("Выберите раздел: \uD83D\uDD3D");
        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText(stringBuilder.toString())
                .setReplyMarkup(keyboard);
    }

    @BotRequestMapping(value = "getparts-\uD83D\uDCE8Отзывы")
    public SendMessage feedback(Update update) {
        ReplyKeyboardMarkup keyboard = getMainPageKeyboard();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<b>тюхлов а.</b>\n");
        stringBuilder.append("<b>Комментарий:</b> Всё понравилось купил что надо.\n");
        stringBuilder.append("\n");

        stringBuilder.append("<b>Евгений Б.</b>\n");
        stringBuilder.append("<b>Достоинства:</b> Всё быстро и даже в 21:00 оформили в работу без денег! Оплатил ночью в 22:00. В 15:00 на след день забрал!\n");
        stringBuilder.append("<b>Комментарий:</b> Всё супер. Рекомендую всем.\n");
        stringBuilder.append("\n");

        stringBuilder.append("<b>Зина Д.</b>\n");
        stringBuilder.append("<b>Достоинства:</b> Очень внимательное отношение к выбору нужных запчастей. Грамотность менеджеров.\n");
        stringBuilder.append("<b>Комментарий:</b> Всё супер. Рекомендую всем.\n");
        stringBuilder.append("\n");
        stringBuilder.append("Выберите раздел: \uD83D\uDD3D");
        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText(stringBuilder.toString())
                .setReplyMarkup(keyboard);
    }

    @BotRequestMapping(value = "getparts-\uD83D\uDCD2Контакты")
    public SendMessage contacts(Update update) {
        ReplyKeyboardMarkup keyboard = getMainPageKeyboard();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\uD83C\uDFE2<b>Адрес:</b>\n");
        stringBuilder.append("г. Москва пос. Битца ул. Нагорная С1\n");
        stringBuilder.append("\n");
        stringBuilder.append("☎️<b>Номер телефона:</b>\n");
        stringBuilder.append("+7(903)177-59-46\n");
        stringBuilder.append("\n");
        stringBuilder.append("\uD83D\uDC8E<b>Телеграмм:</b>\n");
        stringBuilder.append("@getpartsrobot\n");
        stringBuilder.append("\n");
        stringBuilder.append("<b>Разработан при помощи:</b>\n");
        stringBuilder.append("https://botmasterzzz.com\n");
        stringBuilder.append("\n");
        stringBuilder.append("Выберите раздел: \uD83D\uDD3D");
        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText(stringBuilder.toString())
                .setReplyMarkup(keyboard);
    }

    @BotRequestMapping(value = "getparts-❓Помощь")
    public SendMessage help(Update update) {
        ReplyKeyboardMarkup keyboard = getMainPageKeyboard();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\uD83D\uDDA5<b>Главное меню</b>\n");
        stringBuilder.append("Нажмите на эту кнопку, для того чтобы попасть на главную страницу нашего портала.\n");
        stringBuilder.append("\n");
        stringBuilder.append("\uD83D\uDCC4<b>Новости</b>\n");
        stringBuilder.append("Нажмите на эту кнопку, для того чтобы почитать наши последние новости.\n");
        stringBuilder.append("\n");
        stringBuilder.append("\uD83D\uDCE8<b>Отзывы</b>\n");
        stringBuilder.append("Нажмите на эту кнопку, чтобы почитать отзывы наших покупателей.\n");
        stringBuilder.append("\n");
        stringBuilder.append("\uD83D\uDCD2<b>Контакты</b>\n");
        stringBuilder.append("Нажмите на эту кнопку, для получения списка наших контактных данных.\n");
        stringBuilder.append("\n");
        stringBuilder.append("❓<b>Помощь</b>\n");
        stringBuilder.append("Раздел помощи.\n");
        stringBuilder.append("\n");
        stringBuilder.append("\uD83D\uDCC2<b>Каталог</b>\n");
        stringBuilder.append("Для перехода в каталог представленных товаров.\n");
        stringBuilder.append("\n");
        stringBuilder.append("Выберите раздел: \uD83D\uDD3D");
        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText(stringBuilder.toString())
                .setReplyMarkup(keyboard);
    }

    @BotRequestMapping(value = "getparts-\uD83D\uDCC2Каталог")
    public SendMessage catalog(Update update) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\uD83D\uDCC2<b>Каталог</b>\n");
        stringBuilder.append("Выберите раздел:\n");
        InlineKeyboardMarkup inlineKeyboardMarkup = getPartsMessageService.getInlineKeyboardForCatalog();

        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText(stringBuilder.toString())
                .setReplyMarkup(inlineKeyboardMarkup);
    }

    @BotRequestMapping(value = "getparts-ПОИСК")
    public SendMessage search(Update update) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\uD83D\uDCC2<b>Поиск по каталогу</b>\n");
        stringBuilder.append("Введите запрос на поиск:\n");
        UserContextHolder.currentContext().setRemain(true);
        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText(stringBuilder.toString());
    }

    @BotRequestMapping(value = "getparts-SECRET-FIND")
    public SendMessage searchInfo(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId()).enableHtml(true);
        StringBuilder stringBuilder = new StringBuilder();
        String message = update.getMessage().getText();
        GetPartsEntity getPartsEntity = getPartsMessageService.searchPart(message);
        InlineKeyboardMarkup inlineKeyboardMarkup;
        if (null != getPartsEntity){
            stringBuilder.append("\uD83D\uDCC2<b>Поиск по каталогу</b>\n");
            stringBuilder.append("Наименование: ").append(getPartsEntity.getName()).append("\n");
            stringBuilder.append("Брэнд: ").append(getPartsEntity.getBrandName()).append("\n");
            stringBuilder.append("Артикул: ").append(getPartsEntity.getArticle()).append("\n");
            stringBuilder.append("Категория: ").append(getPartsEntity.getGetPartsDetailsEntity().getCatName()).append("\n");
            stringBuilder.append("Описание: ").append(getPartsEntity.getGetPartsDetailsEntity().getDescription()).append("\n");
            stringBuilder.append("Цвет: ").append(getPartsEntity.getGetPartsDetailsEntity().getColour()).append("\n");
            stringBuilder.append("Материал: ").append(getPartsEntity.getGetPartsDetailsEntity().getMaterial()).append("\n");
            stringBuilder.append("Высота в метрах: ").append(getPartsEntity.getGetPartsDetailsEntity().getHeight()).append("\n");
            stringBuilder.append("Длина в метрах: ").append(getPartsEntity.getGetPartsDetailsEntity().getLength()).append("\n");
            stringBuilder.append("Вес: ").append(getPartsEntity.getGetPartsDetailsEntity().getWeight()).append("\n");
            stringBuilder.append("Ширина в метрах: ").append(getPartsEntity.getGetPartsDetailsEntity().getWidth()).append("\n");
            UserContextHolder.currentContext().setPartId(getPartsEntity.getId());
            inlineKeyboardMarkup = getPartsMessageService.getPartsPhotoButton();
            sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        }else {
            stringBuilder.append("Ничего не найдено. Поробуйте снова!");
        }
        sendMessage.setText(stringBuilder.toString());
        UserContextHolder.currentContext().setRemain(false);
        return sendMessage;


    }

    private ReplyKeyboardMarkup getMainPageKeyboard(){
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRowLineOne = new KeyboardRow();
        KeyboardRow keyboardRowLineTwo = new KeyboardRow();
        KeyboardRow keyboardRowLineThree = new KeyboardRow();
        KeyboardRow keyboardRowLineFour = new KeyboardRow();
        keyboardRowLineOne.add("\uD83D\uDDA5Главное меню");
        keyboardRowLineOne.add("\uD83D\uDCC4Новости");
        keyboardRowLineTwo.add("\uD83D\uDCE8Отзывы");
        keyboardRowLineTwo.add("\uD83D\uDCD2Контакты");
        keyboardRowLineThree.add("❓Помощь");
        keyboardRowLineThree.add("\uD83D\uDCC2Каталог");
        keyboardRowLineFour.add("ПОИСК");
        keyboardRows.add(keyboardRowLineOne);
        keyboardRows.add(keyboardRowLineTwo);
        keyboardRows.add(keyboardRowLineThree);
        keyboardRows.add(keyboardRowLineFour);
        keyboard.setKeyboard(keyboardRows);
        return keyboard;
    }
}
