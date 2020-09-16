package com.botmasterzzz.telegram.controller.telegram.reklam;

import com.botmasterzzz.bot.api.impl.methods.send.SendMessage;
import com.botmasterzzz.bot.api.impl.objects.Update;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.ReplyKeyboardMarkup;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.buttons.KeyboardRow;
import com.botmasterzzz.telegram.config.annotations.BotController;
import com.botmasterzzz.telegram.config.annotations.BotRequestMapping;
import com.botmasterzzz.telegram.service.ReklamMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@BotController
public class AccountReklamMenuController {

    private static final Logger logger = LoggerFactory.getLogger(AccountReklamMenuController.class);


    @Autowired
    ReklamMessageService reklamMessageService;

//Users/admin/IdeaProjects/botmasterzzz-telegram/src/main/java/com.botmasterzzz.telegram/controller/telegram
//Users/admin/IdeaProjects/botmasterzzz-telegram/src/main/java/com/botmasterzzz/telegram/controller/telegram/reklam


    @BotRequestMapping(value = "reklam-\uD83D\uDD10Личный кабинет")
    public SendMessage main(Update update) {
        String name = null != update.getMessage().getFrom().getUserName() ? update.getMessage().getFrom().getUserName() : update.getMessage().getFrom().getFirstName();
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRowLineOne = new KeyboardRow();
        KeyboardRow keyboardRowLineTwo = new KeyboardRow();
        KeyboardRow keyboardRowLineThree = new KeyboardRow();
        keyboardRowLineOne.add("\uD83D\uDDA5Главное меню");
        keyboardRowLineOne.add("Список заявок");
        keyboardRowLineTwo.add("Хочу заработать");
//        keyboardRowLineTwo.add("Контакты");
        keyboardRowLineThree.add("Хочу подписчиков");
//        keyboardRowLineThree.add("\uD83D\uDCF0Новости");
        keyboardRows.add(keyboardRowLineOne);
        keyboardRows.add(keyboardRowLineTwo);
//        keyboardRows.add(keyboardRowLineThree);
        keyboard.setKeyboard(keyboardRows);


        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText("<b>Главное меню</b>\n" +
//                        "Позвольте узнать, что Вас беспокоит, " + name + "? \n" +
                        "Выберите раздел: \uD83D\uDD3D")
                .setReplyMarkup(keyboard);
    }

    /*

//    @BotRequestMapping(value = "gkh-❓Помощь")
    public SendMessage help(Update update) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\uD83D\uDDA5<b>Главное меню</b>\n");
        stringBuilder.append("Нажмите на эту кнопку, для того чтобы попасть на главную страницу нашего портала.\n");
        stringBuilder.append("\n");
        stringBuilder.append("\uD83D\uDD10<b>Личный кабинет</b>\n");
        stringBuilder.append("Нажмите на эту кнопку, для того чтобы попасть в личный кабинет для передачи показаний счетчиков.\n");
        stringBuilder.append("\n");
        stringBuilder.append("\uD83D\uDEA5<b>Шлагбаум</b>\n");
        stringBuilder.append("Для управления шлагбаумом необходимо перейти по данной кнопке.\n");
        stringBuilder.append("\n");
        stringBuilder.append("\uD83D\uDCD2<b>Контакты</b>\n");
        stringBuilder.append("Нажмите на эту кнопку, для получения списка наших контактных данных.\n");
        stringBuilder.append("\n");
        stringBuilder.append("❓<b>Помощь</b>\n");
        stringBuilder.append("Раздел помощи.\n");
        stringBuilder.append("\n");
        stringBuilder.append("\uD83D\uDCF0<b>Новости</b>\n");
        stringBuilder.append("Здесь вы можете узнать последние новости и быть в курсе последних событий.\n");
        stringBuilder.append("\n");
        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText(stringBuilder.toString());
    }

    @BotRequestMapping(value = "gkh-\uD83D\uDCD2Контакты")
    public SendMessage contacts(Update update) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<b>Адрес:</b>\n");
        stringBuilder.append("г. Москва ул. Обычная д.3\n");
        stringBuilder.append("\n");
        stringBuilder.append("<b>Номер телефона:</b>\n");
        stringBuilder.append("+7(915)000-00-00\n");
        stringBuilder.append("\n");
        stringBuilder.append("<b>Телеграмм:</b>\n");
        stringBuilder.append("@zhkhrobot\n");
        stringBuilder.append("\n");
        stringBuilder.append("<b>Разработан при помощи:</b>\n");
        stringBuilder.append("https://botmasterzzz.com\n");
        stringBuilder.append("\n");
        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText(stringBuilder.toString());
    }

    @BotRequestMapping(value = "gkh-\uD83D\uDCF0Новости")
    public SendMessage news(Update update) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<b>15 июля 2019г. Запуск бота!</b>\n");
        stringBuilder.append("<b>Запуск бота!</b>\n");
        stringBuilder.append("Наш портал запущен. Добро пожаловать! \n");
        stringBuilder.append("\n");
        stringBuilder.append("<b>25 июля 2019г. Шлагбаум бота!</b>\n");
        stringBuilder.append("<b>Шлагбаум бота!</b>\n");
        stringBuilder.append("Введена возможность управления шлагбаумом через бота! \n");
        stringBuilder.append("\n");
        stringBuilder.append("<b>30 июля 2019г. Показания счетчиков!</b>\n");
        stringBuilder.append("<b>Показания счетчиков!</b>\n");
        stringBuilder.append("Добавили возможность передачи показаний для счетчиков ГВС/ХВС, электроэнергии и показаний по газу! \n");
        stringBuilder.append("\n");
        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText(stringBuilder.toString());
    }

    */
}
