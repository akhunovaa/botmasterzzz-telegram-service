package com.botmasterzzz.telegram.controller.telegram;

import com.botmasterzzz.bot.api.impl.methods.BotApiMethod;
import com.botmasterzzz.bot.api.impl.methods.send.SendMessage;
import com.botmasterzzz.bot.api.impl.methods.send.SendPhoto;
import com.botmasterzzz.bot.api.impl.methods.update.EditMessageText;
import com.botmasterzzz.bot.api.impl.objects.InputFile;
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
import com.botmasterzzz.telegram.util.HelperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@BotController
public class TelegramBotMessengerController {

    private static final Logger logger = LoggerFactory.getLogger(BotInstanceContainer.class);

    @BotRequestMapping(value = "text")
    public BotApiMethod textMessage(Update update) {
        Long chatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        UserContext userContext = UserContextHolder.currentContext();
        User user = userContext.getUser();
        ProjectCommandDTO projectCommandDTO = userContext.getProjectCommandDTO();
        String command = null != projectCommandDTO ? projectCommandDTO.getCommand() : "/неизвестная_команда";
        String commandName = null != projectCommandDTO ? projectCommandDTO.getCommandName() : "Неизвестная команда";
        String answer = null != projectCommandDTO ? projectCommandDTO.getAnswer() : "Неизвестная команда. Повторите попытку позднее";
        logger.info("User id {} sent message {} from command {} with a command name like {}", user.getId(), answer, command, commandName);
        if (update.hasMessage()){
            return new SendMessage()
                    .setChatId(chatId).enableHtml(true)
                    .setText(answer);
        }else {
            return new EditMessageText()
                    .setChatId(chatId)
                    .setMessageId(update.getCallbackQuery().getMessage().getMessageId())
                    .setText(answer);
        }

    }

    @BotRequestMapping(value = "picture")
    public SendPhoto pictureMessage(Update update) {
        Long chatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        UserContext userContext = UserContextHolder.currentContext();
        User user = userContext.getUser();
        ProjectCommandDTO projectCommandDTO = userContext.getProjectCommandDTO();
        String command = null != projectCommandDTO ? projectCommandDTO.getCommand() : "/неизвестная_команда";
        String commandName = null != projectCommandDTO ? projectCommandDTO.getCommandName() : "Неизвестная команда";
        String answer = null != projectCommandDTO ? projectCommandDTO.getAnswer() : "Неизвестная команда. Повторите попытку позднее";
        InlineKeyboardButton firstInlineButton = new InlineKeyboardButton();
        firstInlineButton.setText(commandName);
        firstInlineButton.setCallbackData(command);
        SendPhoto sendPhoto = new SendPhoto();
        String filePath;
        try {
            filePath = HelperUtil.saveImage(answer, "temporary");
            File file1 = new File(filePath);
            sendPhoto.setPhoto(new InputFile(file1, "image-one"));
        } catch (IOException e) {
            logger.error("User id {} sent photo message {} from command {} with a command name like {}", user.getId(), answer, command, commandName);
        }
        sendPhoto.setChatId(chatId);
        sendPhoto.disableNotification();
        logger.info("User id {} sent photo message {} from command {} with a command name like {}", user.getId(), answer, command, commandName);
        return sendPhoto;
    }

    @BotRequestMapping(value = "inner_button")
    public BotApiMethod innerButtonMessage(Update update) {
        Long chatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        UserContext userContext = UserContextHolder.currentContext();
        User user = userContext.getUser();
        ProjectCommandDTO projectCommandDTO = userContext.getProjectCommandDTO();
        String command = null != projectCommandDTO ? projectCommandDTO.getCommand() : "/неизвестная_команда";
        String commandName = null != projectCommandDTO ? projectCommandDTO.getCommandName() : "Неизвестная команда";
        String answer = null != projectCommandDTO ? projectCommandDTO.getAnswer() : "Неизвестная команда. Повторите попытку позднее";

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();

        List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = new ArrayList<>();

        InlineKeyboardButton firstInlineButton = new InlineKeyboardButton();
        firstInlineButton.setText(commandName);
        firstInlineButton.setCallbackData(commandName);
        inlineKeyboardButtonsFirstRow.add(firstInlineButton);

        inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);
        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);

        logger.info("User id {} sent inlined buttons message {} from command {} with a command name like {}", user.getId(), answer, command, commandName);
        if (update.hasMessage()){
            return new SendMessage()
                    .setChatId(chatId).enableHtml(true)
                    .setReplyMarkup(inlineKeyboardMarkup)
                    .setText(answer);
        }else {
            return new EditMessageText()
                    .setChatId(chatId)
                    .setReplyMarkup(inlineKeyboardMarkup)
                    .setMessageId(update.getCallbackQuery().getMessage().getMessageId())
                    .setText(answer);
        }
    }

    @BotRequestMapping(value = "outter_button")
    public SendMessage outterButtonMessage(Update update) {
        Long chatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        UserContext userContext = UserContextHolder.currentContext();
        User user = userContext.getUser();
        ProjectCommandDTO projectCommandDTO = userContext.getProjectCommandDTO();
        String command = null != projectCommandDTO ? projectCommandDTO.getCommand() : "/неизвестная_команда";
        String commandName = null != projectCommandDTO ? projectCommandDTO.getCommandName() : "Неизвестная команда";
        String answer = null != projectCommandDTO ? projectCommandDTO.getAnswer() : "Неизвестная команда. Повторите попытку позднее";

        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRowLineOne = new KeyboardRow();
        keyboardRowLineOne.add(commandName);

        keyboardRows.add(keyboardRowLineOne);
        keyboard.setKeyboard(keyboardRows);

        logger.info("User id {} sent reply keyboard buttons message {} from command {} with a command name like {}", user.getId(), answer, command, commandName);
        return new SendMessage()
                .setChatId(chatId).enableHtml(true)
                .setText(answer)
                .setReplyMarkup(keyboard);
    }


}
