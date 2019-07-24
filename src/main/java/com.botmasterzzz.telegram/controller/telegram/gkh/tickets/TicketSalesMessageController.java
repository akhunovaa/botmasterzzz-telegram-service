package com.botmasterzzz.telegram.controller.telegram.gkh.tickets;

import com.botmasterzzz.bot.api.impl.methods.send.SendMessage;
import com.botmasterzzz.bot.api.impl.methods.update.EditMessageText;
import com.botmasterzzz.bot.api.impl.objects.Update;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.InlineKeyboardMarkup;
import com.botmasterzzz.telegram.config.annotations.BotController;
import com.botmasterzzz.telegram.config.annotations.BotRequestMapping;
import com.botmasterzzz.telegram.controller.telegram.gkh.GkhMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@BotController
public class TicketSalesMessageController {
    private static final Logger logger = LoggerFactory.getLogger(TicketSalesMessageController.class);

    @Autowired
    private GkhMessageService gkhMessageService;


    @BotRequestMapping(value = "gkh-Куплю/продам/сдам")
    public SendMessage sales(Update update) {

        //todo переписать под электричество
        InlineKeyboardMarkup inlineKeyboardMarkup = gkhMessageService.getInlineKeyboardForEnergyAccount();

        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText("<b>Объявления по покупке/продаже/аренде от жителей</b>")
                .setReplyMarkup(inlineKeyboardMarkup);
    }

    @BotRequestMapping(value = "gkh-get_sales")
    public EditMessageText getEnergy(Update update) {

        InlineKeyboardMarkup inlineKeyboardMarkup = gkhMessageService.getInlineKeyboardForSalesTickets();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<b>Передача показаний счетчика электроэнергии.</b>\n");
        stringBuilder.append("Список объявлений:");
        stringBuilder.append("Алексей - сдам гараж, +1-213-213");
        stringBuilder.append("Сергей - продам холодильник, , +1-321-321");
        stringBuilder.append("Оксана - сниму квартиру, +1-123-123");

        EditMessageText editMessageText = getEditMessage(stringBuilder.toString(), update);
        editMessageText.setReplyMarkup(inlineKeyboardMarkup);
        return editMessageText;
    }

    @BotRequestMapping(value = "gkh-send_sales")
    public EditMessageText sendGas(Update update) {

        InlineKeyboardMarkup inlineKeyboardMarkup = gkhMessageService.getInlineKeyboardForSalesTickets();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<b>Подать объявление.</b>\n");
        stringBuilder.append("ОБъявление зарегистрировано. Вернуться?");
        EditMessageText editMessageText = getEditMessage(stringBuilder.toString(), update);
        editMessageText.setReplyMarkup(inlineKeyboardMarkup);
        return editMessageText;
    }


    private EditMessageText getEditMessage(String text, Update update) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(update.getCallbackQuery().getMessage().getChatId());
        editMessageText.setText(text);
        editMessageText.enableHtml(Boolean.TRUE);
        editMessageText.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        return editMessageText;
    }
}
