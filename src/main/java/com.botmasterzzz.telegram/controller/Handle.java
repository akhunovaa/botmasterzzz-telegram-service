package com.botmasterzzz.telegram.controller;

import com.botmasterzzz.bot.api.impl.methods.BotApiMethod;
import com.botmasterzzz.bot.api.impl.objects.Update;
import com.botmasterzzz.bot.exceptions.TelegramApiException;
import com.botmasterzzz.telegram.config.Telegram;
import com.botmasterzzz.telegram.config.container.BotApiMethodContainer;
import com.botmasterzzz.telegram.config.context.UserContextHolder;
import com.botmasterzzz.telegram.dto.CallBackData;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Handle {

    private Gson gson;

    private Telegram telegram;

    private static final Logger logger = LoggerFactory.getLogger(BotApiMethodController.class);

    private static BotApiMethodContainer container = BotApiMethodContainer.getInstanse();

    public BotApiMethodController getHandle(Update update) {
        String queryData;
        CallBackData callBackData;
        BotApiMethodController controller = null;
        if (update.hasMessage() && update.getMessage().hasText()) {
            queryData = update.getMessage().getText();
            controller = container.getControllerMap().get(queryData.split(" ")[0].trim());
            if (null == container.getControllerMap().get(queryData))
                controller = container.getControllerMap().get(queryData.substring(2));
            UserContextHolder.currentContext().setCallBackData(new CallBackData());
        } else if (update.hasCallbackQuery()) {
            callBackData = gson.fromJson(update.getCallbackQuery().getData(), CallBackData.class);
            controller = container.getControllerMap().get(callBackData.getPath());
            UserContextHolder.currentContext().setCallBackData(callBackData);
            logger.info("Пользователь: " + update.getCallbackQuery().getFrom().getId() + " " + update.getCallbackQuery().getFrom().getFirstName() + " " + update.getCallbackQuery().getFrom().getLastName() + " " + update.getCallbackQuery().getFrom().getUserName() + " Сообщение: " + update.getCallbackQuery().getData());
        }
        return controller;
    }


    public void handleMessage(Update update) {
        BotApiMethodController methodController = getHandle(update);
        try {
            this.dispatch(methodController, update);
        } catch (Exception e) {
            logger.error("ERROR TelegramApiException", e);
        }
    }

    @SuppressWarnings("unchecked")
    private void dispatch(BotApiMethodController controller, Update update) {
        if (null == controller)
            return;
        try {
            for (BotApiMethod botApiMethod : controller.process(update)) {
                telegram.execute(botApiMethod);
            }
        } catch (TelegramApiException e) {
            logger.error("ERROR TelegramApiException", e);
        }
    }
}
