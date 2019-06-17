package com.botmasterzzz.telegram.controller.telegram;

import com.botmasterzzz.bot.api.impl.methods.send.SendMessage;
import com.botmasterzzz.bot.api.impl.methods.send.SendPhoto;
import com.botmasterzzz.bot.api.impl.objects.Update;
import com.botmasterzzz.bot.api.impl.objects.User;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.InlineKeyboardMarkup;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.ReplyKeyboardMarkup;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.buttons.InlineKeyboardButton;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.buttons.KeyboardRow;
import com.botmasterzzz.telegram.config.annotations.BotController;
import com.botmasterzzz.telegram.config.annotations.BotRequestMapping;
import com.botmasterzzz.telegram.config.container.BotInstanceContainer;
import com.botmasterzzz.telegram.config.context.UserContext;
import com.botmasterzzz.telegram.config.context.UserContextHolder;
import com.botmasterzzz.telegram.dto.ProjectCommandDTO;
import com.botmasterzzz.telegram.service.ResourceStreamService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@BotController
public class TelegramBotMessengerController {

    private static final Logger logger = LoggerFactory.getLogger(BotInstanceContainer.class);

    @Autowired
    private Gson gson;

    @Autowired
    private ResourceStreamService resourceStreamService;

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
    public SendPhoto pictureMessage(Update update) {
        UserContext userContext = UserContextHolder.currentContext();
        User user = userContext.getUser();
        ProjectCommandDTO projectCommandDTO = userContext.getProjectCommandDTO();
        String command = projectCommandDTO.getCommand();
        String commandName = projectCommandDTO.getCommandName();
        String answer = projectCommandDTO.getAnswer();
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();

        List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = new ArrayList<>();

        InlineKeyboardButton firstInlineButton = new InlineKeyboardButton();
        firstInlineButton.setText(commandName);
        firstInlineButton.setCallbackData(command);
        inlineKeyboardButtonsFirstRow.add(firstInlineButton);

        inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);
        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);

        InputStream image = resourceStreamService.getImageFromUrl(answer);
        logger.info("User id {} sent photo message {} from command {} with a command name like {}", user.getId(), projectCommandDTO.getAnswer(), projectCommandDTO.getCommand(), projectCommandDTO.getCommandName());
        return new SendPhoto()
                .setChatId(update.getMessage().getChatId())
                .setCaption(commandName)
                .setReplyMarkup(inlineKeyboardMarkup)
                .setPhoto(commandName, image);
    }

    @BotRequestMapping(value = "inner_button")
    public SendMessage innerButtonMessage(Update update) {
        UserContext userContext = UserContextHolder.currentContext();
        User user = userContext.getUser();
        ProjectCommandDTO projectCommandDTO = userContext.getProjectCommandDTO();
        String answer = projectCommandDTO.getAnswer();
        String command = projectCommandDTO.getCommand();
        String commandName = projectCommandDTO.getCommandName();

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();

        List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = new ArrayList<>();

        InlineKeyboardButton firstInlineButton = new InlineKeyboardButton();
        firstInlineButton.setText(commandName);
        firstInlineButton.setCallbackData(command);
        inlineKeyboardButtonsFirstRow.add(firstInlineButton);

        inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);
        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);

        logger.info("User id {} sent inlined buttons message {} from command {} with a command name like {}", user.getId(), projectCommandDTO.getAnswer(), projectCommandDTO.getCommand(), projectCommandDTO.getCommandName());
        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setReplyMarkup(inlineKeyboardMarkup)
                .setText(answer);
    }

    @BotRequestMapping(value = "outter_button")
    public SendMessage outterButtonMessage(Update update) {
        UserContext userContext = UserContextHolder.currentContext();
        User user = userContext.getUser();
        ProjectCommandDTO projectCommandDTO = userContext.getProjectCommandDTO();
        String answer = projectCommandDTO.getAnswer();
        String command = projectCommandDTO.getCommand();
        String commandName = projectCommandDTO.getCommandName();

        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRowLineOne = new KeyboardRow();
        keyboardRowLineOne.add(commandName);

        keyboardRows.add(keyboardRowLineOne);
        keyboard.setKeyboard(keyboardRows);

        logger.info("User id {} sent reply keyboard buttons message {} from command {} with a command name like {}", user.getId(), projectCommandDTO.getAnswer(), projectCommandDTO.getCommand(), projectCommandDTO.getCommandName());
        return new SendMessage()
                .setChatId(update.getMessage().getChatId()).enableHtml(true)
                .setText(answer)
                .setReplyMarkup(keyboard);
    }


}
