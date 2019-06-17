package com.botmasterzzz.telegram.controller.telegram;

import com.botmasterzzz.bot.api.impl.methods.send.SendMessage;
import com.botmasterzzz.bot.api.impl.objects.Update;
import com.botmasterzzz.bot.api.impl.objects.User;
import com.botmasterzzz.telegram.config.annotations.BotController;
import com.botmasterzzz.telegram.config.annotations.BotRequestMapping;
import com.botmasterzzz.telegram.config.container.BotInstanceContainer;
import com.botmasterzzz.telegram.config.context.UserContext;
import com.botmasterzzz.telegram.config.context.UserContextHolder;
import com.botmasterzzz.telegram.dto.ProjectCommandDTO;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@BotController
public class TelegramBotMessengerController {

    private static final Logger logger = LoggerFactory.getLogger(BotInstanceContainer.class);

    @Autowired
    private Gson gson;

    @BotRequestMapping(value = "text")
    public SendMessage textMessage(Update update) {
        UserContext userContext = UserContextHolder.currentContext();
        User user = userContext.getUser();
        ProjectCommandDTO projectCommandDTO = userContext.getProjectCommandDTO();
        String answer = projectCommandDTO.getAnswer();
        logger.info("User id {} sent message {} from command {} with a command name like {}", user.getId(), projectCommandDTO.getAnswer(), projectCommandDTO.getCommand(), projectCommandDTO.getCommandName());
        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText(answer);
    }

    @BotRequestMapping(value = "picture")
    public SendMessage pictureMessage(Update update) {
        UserContext userContext = UserContextHolder.currentContext();
        User user = userContext.getUser();
        ProjectCommandDTO projectCommandDTO = userContext.getProjectCommandDTO();
        String answer = projectCommandDTO.getAnswer();
        logger.info("User id {} sent message {} from command {} with a command name like {}", user.getId(), projectCommandDTO.getAnswer(), projectCommandDTO.getCommand(), projectCommandDTO.getCommandName());
        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText(answer);
    }

    @BotRequestMapping(value = "inner_button")
    public SendMessage innerButtonMessage(Update update) {
        UserContext userContext = UserContextHolder.currentContext();
        User user = userContext.getUser();
        ProjectCommandDTO projectCommandDTO = userContext.getProjectCommandDTO();
        String answer = projectCommandDTO.getAnswer();
        logger.info("User id {} sent message {} from command {} with a command name like {}", user.getId(), projectCommandDTO.getAnswer(), projectCommandDTO.getCommand(), projectCommandDTO.getCommandName());
        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText(answer);
    }

    @BotRequestMapping(value = "outter_button")
    public SendMessage outterButtonMessage(Update update) {
        UserContext userContext = UserContextHolder.currentContext();
        User user = userContext.getUser();
        ProjectCommandDTO projectCommandDTO = userContext.getProjectCommandDTO();
        String answer = projectCommandDTO.getAnswer();
        logger.info("User id {} sent message {} from command {} with a command name like {}", user.getId(), projectCommandDTO.getAnswer(), projectCommandDTO.getCommand(), projectCommandDTO.getCommandName());
        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText(answer);
    }


}
