package com.botmasterzzz.telegram.controller.telegram;

import com.botmasterzzz.bot.api.impl.methods.send.SendMessage;
import com.botmasterzzz.bot.api.impl.objects.Update;
import com.botmasterzzz.telegram.config.annotations.BotController;
import com.botmasterzzz.telegram.config.annotations.BotRequestMapping;
import com.botmasterzzz.telegram.entity.TelegramBotUserEntity;
import com.botmasterzzz.telegram.service.TelegramUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@BotController
public class UnknownCommandsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UnknownCommandsController.class);

    private final TelegramUserService telegramUserService;

    public UnknownCommandsController(TelegramUserService telegramUserService) {
        this.telegramUserService = telegramUserService;
    }

    @BotRequestMapping(value = "command-unknown-error")
    public SendMessage error(Update update) {
        Long requestedUserId = Long.valueOf(null == update.getCallbackQuery() ? update.getMessage().getFrom().getId() : update.getCallbackQuery().getFrom().getId());
        Long chatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        TelegramBotUserEntity requestedTelegramUser = telegramUserService.getTelegramUser(requestedUserId);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("❗️Неизвестная команда \n\nДля того чтобы вернуться на <b>главное меню</b>, воспользуйтесь командой /start\n");
        stringBuilder.append("\n");
        LOGGER.info("User {} sent an unknown command", requestedTelegramUser);
        return new SendMessage()
                .setChatId(chatId).enableHtml(true)
                .setText(stringBuilder.toString());
    }
}
