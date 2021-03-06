package com.botmasterzzz.telegram.controller.telegram;

import com.botmasterzzz.bot.api.impl.methods.BotApiMethod;
import com.botmasterzzz.bot.api.impl.methods.send.SendMessage;
import com.botmasterzzz.bot.api.impl.methods.send.SendPhoto;
import com.botmasterzzz.bot.api.impl.methods.send.SendVideo;
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
import com.botmasterzzz.telegram.entity.TelegramAttributesDataEntity;
import com.botmasterzzz.telegram.service.DatabaseService;
import com.botmasterzzz.telegram.util.HelperUtil;
import com.botmasterzzz.telegram.util.HttpDownloadUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@BotController
public class TelegramBotMessengerController {

    private static final Logger logger = LoggerFactory.getLogger(BotInstanceContainer.class);

    @Autowired
    private DatabaseService databaseService;

    @BotRequestMapping(value = "text")
    public BotApiMethod textMessage(Update update) {
        Long chatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        UserContext userContext = UserContextHolder.currentContext();
        User user = userContext.getUser();
        ProjectCommandDTO projectCommandDTO = userContext.getProjectCommandDTO();
        String command = null != projectCommandDTO ? projectCommandDTO.getCommand() : "/??????????????????????_??????????????";
        String commandName = null != projectCommandDTO ? projectCommandDTO.getCommandName() : "?????????????????????? ??????????????";
        String answer = null != projectCommandDTO ? projectCommandDTO.getAnswer() : "";
        logger.info("User id {} sent message {} from command {} with a command name like {}", user.getId(), answer, command, commandName);
//        if (update.hasMessage()){
        return new SendMessage()
                .setChatId(chatId).enableHtml(true)
                .setText(answer);
//        }else {
//            return new EditMessageText()
//                    .setChatId(chatId)
//                    .setMessageId(update.getCallbackQuery().getMessage().getMessageId())
//                    .setText(answer);
//        }

    }

    @BotRequestMapping(value = "attribute_save")
    public BotApiMethod attributeSave(Update update) {
        Long chatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        String message = update.hasMessage() ? update.getMessage().getText() : update.getCallbackQuery().getMessage().getText();
        UserContext userContext = UserContextHolder.currentContext();
        User user = userContext.getUser();
        ProjectCommandDTO projectCommandDTO = userContext.getProjectCommandDTO();
        String command = null != projectCommandDTO ? projectCommandDTO.getCommand() : "/??????????????????????_??????????????";
        String commandName = null != projectCommandDTO ? projectCommandDTO.getCommandName() : "?????????????????????? ??????????????";
        String answer = null != projectCommandDTO ? projectCommandDTO.getAnswer() : "";
        boolean await =  UserContextHolder.currentContext().isRemain();
        long instanceId =  UserContextHolder.currentContext().getInstanceId();
        if (await){
            String attributeName = UserContextHolder.currentContext().getAttributeName();
            databaseService.telegramAttributeAdd(attributeName, message, instanceId, Long.valueOf(user.getId()));
            UserContextHolder.currentContext().setRemain(false);
        }else {
            UserContextHolder.currentContext().setRemain(true);
            UserContextHolder.currentContext().setAttributeName(answer);
        }
        logger.info("User id {} sent message {} from command {} with a command name like {}", user.getId(), answer, command, commandName);
        return new SendMessage()
                .setChatId(chatId).enableHtml(true)
                .setText(answer);
    }

    @BotRequestMapping(value = "attribute_get")
    public BotApiMethod attributeGet(Update update) {
        Long chatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        String message = update.hasMessage() ? update.getMessage().getText() : update.getCallbackQuery().getMessage().getText();
        UserContext userContext = UserContextHolder.currentContext();
        User user = userContext.getUser();
        ProjectCommandDTO projectCommandDTO = userContext.getProjectCommandDTO();
        String command = null != projectCommandDTO ? projectCommandDTO.getCommand() : "/??????????????????????_??????????????";
        String commandName = null != projectCommandDTO ? projectCommandDTO.getCommandName() : "?????????????????????? ??????????????";
        //String answer = null != projectCommandDTO ? projectCommandDTO.getAnswer() : "";
        long instanceId =  UserContextHolder.currentContext().getInstanceId();
        String attributeName = UserContextHolder.currentContext().getAttributeName();
        TelegramAttributesDataEntity telegramAttributesDataEntity = databaseService.telegramAttributeGet(Long.valueOf(user.getId()), instanceId, attributeName);
        UserContextHolder.currentContext().setAttributeName(null);
        boolean await =  UserContextHolder.currentContext().isRemain();
        logger.info("User id {} sent message {} from command {} with a command name like {}", user.getId(), telegramAttributesDataEntity.getValue(), command, commandName);
        return new SendMessage()
                .setChatId(chatId).enableHtml(true)
                .setText(telegramAttributesDataEntity.getValue());
    }

    @BotRequestMapping(value = "random_text")
    public BotApiMethod randomTextMessage(Update update) {
        Long chatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        UserContext userContext = UserContextHolder.currentContext();
        User user = userContext.getUser();
        ProjectCommandDTO projectCommandDTO = userContext.getProjectCommandDTO();
        String command = null != projectCommandDTO ? projectCommandDTO.getCommand() : "/??????????????????????_??????????????";
        String commandName = null != projectCommandDTO ? projectCommandDTO.getCommandName() : "?????????????????????? ??????????????";
        String answer = null != projectCommandDTO ? projectCommandDTO.getAnswer() : "";
        String[] choosenAnswer = answer.trim().split("%");

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();

        List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = new ArrayList<>();

        InlineKeyboardButton firstInlineButton = new InlineKeyboardButton();
        firstInlineButton.setText(commandName);
        firstInlineButton.setCallbackData(commandName);
        inlineKeyboardButtonsFirstRow.add(firstInlineButton);

        inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);
        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);

        Random rnd = new Random();
        int n = rnd.nextInt(choosenAnswer.length);
        String messageToSend = choosenAnswer[n];
        logger.info("User id {} sent random message {} from command {} with a command name like {} and message {}", user.getId(), answer, command, commandName, choosenAnswer[n]);
        if (update.hasMessage()) {
            return new SendMessage()
                    .setChatId(chatId).enableHtml(true)
                    .setText(messageToSend).setReplyMarkup(inlineKeyboardMarkup);
        } else {
            return new EditMessageText()
                    .setChatId(chatId)
                    .setMessageId(update.getCallbackQuery().getMessage().getMessageId())
                    .setText(messageToSend).setReplyMarkup(inlineKeyboardMarkup);
        }

    }

    @BotRequestMapping(value = "picture")
    public SendPhoto pictureMessage(Update update) {
        Long chatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        UserContext userContext = UserContextHolder.currentContext();
        User user = userContext.getUser();
        ProjectCommandDTO projectCommandDTO = userContext.getProjectCommandDTO();
        String command = null != projectCommandDTO ? projectCommandDTO.getCommand() : "/??????????????????????_??????????????";
        String commandName = null != projectCommandDTO ? projectCommandDTO.getCommandName() : "?????????????????????? ??????????????";
        String answer = null != projectCommandDTO ? projectCommandDTO.getAnswer() : "";
        InlineKeyboardButton firstInlineButton = new InlineKeyboardButton();
        firstInlineButton.setText(commandName);
        firstInlineButton.setCallbackData(command);
        SendPhoto sendPhoto = new SendPhoto();
        String filePath = System.getProperty("java.io.tmpdir") + "/" + answer.hashCode();
        try {
            File existdFile = new File(filePath);
            if (existdFile.exists()) {
                sendPhoto.setPhotoInputFile(new InputFile(existdFile, String.valueOf(answer.hashCode())));
                logger.info("SENT EXISTS file User id {} sent random photo message {} from command {} with a command name like {} choosen {}", user.getId(), answer, command, commandName, filePath);
            } else {
                filePath = HelperUtil.saveImage(answer, filePath);
                existdFile = new File(filePath);
                sendPhoto.setPhotoInputFile(new InputFile(existdFile, String.valueOf(answer.hashCode())));
            }
        } catch (IOException e) {
            logger.error("User id {} sent photo message {} from command {} with a command name like {}", user.getId(), answer, command, commandName);
        }
        sendPhoto.setChatId(chatId);
        sendPhoto.disableNotification();
        logger.info("User id {} sent photo message {} from command {} with a command name like {}", user.getId(), answer, command, commandName);
        return sendPhoto;
    }

    @BotRequestMapping(value = "random_picture")
    public SendPhoto randomPictureMessage(Update update) {

        Long chatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        UserContext userContext = UserContextHolder.currentContext();
        User user = userContext.getUser();
        ProjectCommandDTO projectCommandDTO = userContext.getProjectCommandDTO();
        String command = null != projectCommandDTO ? projectCommandDTO.getCommand() : "/??????????????????????_??????????????";
        String commandName = null != projectCommandDTO ? projectCommandDTO.getCommandName() : "?????????????????????? ??????????????";
        String answer = null != projectCommandDTO ? projectCommandDTO.getAnswer() : "";

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();

        List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = new ArrayList<>();

        InlineKeyboardButton firstInlineButton = new InlineKeyboardButton();
        firstInlineButton.setText(commandName);
        firstInlineButton.setCallbackData(commandName);
        inlineKeyboardButtonsFirstRow.add(firstInlineButton);

        inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);
        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);

        SendPhoto sendPhoto = new SendPhoto();
        String[] choosenPicture = answer.trim().split("%");
        Random rnd = new Random();
        int n = rnd.nextInt(choosenPicture.length);
        String pictureToSend = choosenPicture[n];
        try {
            String filePath = System.getProperty("java.io.tmpdir") + "/" + pictureToSend.hashCode();
            File existdFile = new File(filePath);
            if (existdFile.exists()) {
                sendPhoto.setPhotoInputFile(new InputFile(existdFile, "image-one"));
                logger.info("SENT EXISTS file User id {} sent random photo message {} from command {} with a command name like {} choosen {}", user.getId(), answer, command, commandName, pictureToSend);
            } else {
                pictureToSend = HelperUtil.saveImage(pictureToSend, String.valueOf(pictureToSend.hashCode()));
                existdFile = new File(pictureToSend);
                sendPhoto.setPhotoInputFile(new InputFile(existdFile, "image-one"));
            }
        } catch (IOException e) {
            logger.error("User id {} sent random photo message {} from command {} with a command name like {} choosen {}", user.getId(), answer, command, commandName, pictureToSend, e);
        }
        sendPhoto.setChatId(chatId);
        sendPhoto.setReplyMarkup(inlineKeyboardMarkup);
        logger.info("User id {} sent random photo message {} from command {} with a command name like {} choosen {}", user.getId(), answer, command, commandName, pictureToSend);
        return sendPhoto;
    }

    @BotRequestMapping(value = "video")
    public SendVideo videoMessage(Update update) {
        Long chatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        UserContext userContext = UserContextHolder.currentContext();
        User user = userContext.getUser();
        ProjectCommandDTO projectCommandDTO = userContext.getProjectCommandDTO();
        String command = null != projectCommandDTO ? projectCommandDTO.getCommand() : "/??????????????????????_??????????????";
        String commandName = null != projectCommandDTO ? projectCommandDTO.getCommandName() : "?????????????????????? ??????????????";
        String answer = null != projectCommandDTO ? projectCommandDTO.getAnswer() : "";
        InlineKeyboardButton firstInlineButton = new InlineKeyboardButton();
        firstInlineButton.setText(commandName);
        firstInlineButton.setCallbackData(command);

        SendVideo sendVideo = new SendVideo();
        String videoTemporaryName = String.valueOf(answer.hashCode());
        String filePath = System.getProperty("java.io.tmpdir") + "/" + videoTemporaryName;
        try {
            File existsFile = new File(filePath);
            if (existsFile.exists()) {
                sendVideo.setVideoInputFile(new InputFile(existsFile, videoTemporaryName));
                logger.info("SENT EXISTS file User id {} sent video message {} from command {} with a command name like {} choosen {}", user.getId(), answer, command, commandName, filePath);
            } else {
                HttpDownloadUtility.downloadFile(answer, videoTemporaryName);
                existsFile = new File(filePath);
                sendVideo.setVideoInputFile(new InputFile(existsFile, videoTemporaryName));
            }
        } catch (IOException e) {
            logger.error("User id {} sent video message {} from command {} with a command name like {}", user.getId(), answer, command, commandName);
        }
        sendVideo.setChatId(chatId);
        sendVideo.disableNotification();
        logger.info("User id {} sent video message {} from command {} with a command name like {}", user.getId(), answer, command, commandName);
        return sendVideo;
    }

    @BotRequestMapping(value = "random_video")
    public SendVideo randomVideoMessage(Update update) {

        Long chatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        UserContext userContext = UserContextHolder.currentContext();
        User user = userContext.getUser();
        ProjectCommandDTO projectCommandDTO = userContext.getProjectCommandDTO();
        String command = null != projectCommandDTO ? projectCommandDTO.getCommand() : "/??????????????????????_??????????????";
        String commandName = null != projectCommandDTO ? projectCommandDTO.getCommandName() : "?????????????????????? ??????????????";
        String answer = null != projectCommandDTO ? projectCommandDTO.getAnswer() : "";

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();

        List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = new ArrayList<>();

        InlineKeyboardButton firstInlineButton = new InlineKeyboardButton();
        firstInlineButton.setText(commandName);
        firstInlineButton.setCallbackData(commandName);
        inlineKeyboardButtonsFirstRow.add(firstInlineButton);

        inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);
        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);

        SendVideo sendVideo = new SendVideo();
        String[] choosenVideo = answer.trim().split("%");
        Random rnd = new Random();
        int n = rnd.nextInt(choosenVideo.length);
        String videoToSend = choosenVideo[n];
        String videoTemporaryName = String.valueOf(videoToSend.hashCode());
        try {
            String filePath = System.getProperty("java.io.tmpdir") + "/" + videoTemporaryName;
            File existsFile = new File(filePath);
            if (existsFile.exists()) {
                sendVideo.setVideoInputFile(new InputFile(existsFile, videoTemporaryName));
                logger.info("SENT EXISTS file User id {} sent random video message {} from command {} with a command name like {} choosen {}", user.getId(), answer, command, commandName, videoToSend);
            } else {
                HttpDownloadUtility.downloadFile(videoToSend, videoTemporaryName);
                existsFile = new File(filePath);
                sendVideo.setVideoInputFile(new InputFile(existsFile, videoTemporaryName));
            }
        } catch (IOException e) {
            logger.error("User id {} sent random video message {} from command {} with a command name like {} choosen {}", user.getId(), answer, command, commandName, videoToSend, e);
        }
        sendVideo.setChatId(chatId);
        sendVideo.setReplyMarkup(inlineKeyboardMarkup);
        logger.info("User id {} sent random video message {} from command {} with a command name like {} choosen {}", user.getId(), answer, command, commandName, videoToSend);
        return sendVideo;
    }

    @BotRequestMapping(value = "inner_button")
    public BotApiMethod innerButtonMessage(Update update) {
        Long chatId = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        UserContext userContext = UserContextHolder.currentContext();
        User user = userContext.getUser();
        ProjectCommandDTO projectCommandDTO = userContext.getProjectCommandDTO();
        String command = null != projectCommandDTO ? projectCommandDTO.getCommand() : "/??????????????????????_??????????????";
        String commandName = null != projectCommandDTO ? projectCommandDTO.getCommandName() : "?????????????????????? ??????????????";
        String answer = null != projectCommandDTO ? projectCommandDTO.getAnswer() : "";
        String[] commandsForButton = commandName.trim().split("%");
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();
        for (String iCommandName : commandsForButton) {
            List<InlineKeyboardButton> inlineKeyboardButtonsRow = new ArrayList<>();
            String[] innerCommandsForButton = iCommandName.trim().split("\\$");
            for (String sInnerCommandsForButton : innerCommandsForButton) {
                InlineKeyboardButton firstInlineButton = new InlineKeyboardButton();
                firstInlineButton.setText(sInnerCommandsForButton);
                firstInlineButton.setCallbackData(sInnerCommandsForButton);
                if (inlineKeyboardButtonsRow.size() <= 6) {
                    inlineKeyboardButtonsRow.add(firstInlineButton);
                }
            }
            if (inlineKeyboardButtons.size() <= 6) {
                inlineKeyboardButtons.add(inlineKeyboardButtonsRow);
            }
        }
        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);
        logger.info("User id {} sent inlined buttons message {} from command {} with a command name like {}", user.getId(), answer, command, commandName);
        if (update.hasMessage()) {
            return new SendMessage()
                    .setChatId(chatId).enableHtml(true)
                    .setReplyMarkup(inlineKeyboardMarkup)
                    .setText(answer);
        } else {
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
        String command = null != projectCommandDTO ? projectCommandDTO.getCommand() : "/??????????????????????_??????????????";
        String commandName = null != projectCommandDTO ? projectCommandDTO.getCommandName() : "?????????????????????? ??????????????";
        String answer = null != projectCommandDTO ? projectCommandDTO.getAnswer() : "";
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setOneTimeKeyboard(false);
        String[] commandsForButton = commandName.trim().split("%");
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        keyboard.setOneTimeKeyboard(false);
        for (String iCommandName : commandsForButton) {
            KeyboardRow keyboardRowLine = new KeyboardRow();
            String[] innerCommandsForButton = iCommandName.trim().split("\\$");
            for (String xInnerCommandsForButton : innerCommandsForButton) {
                if (keyboardRowLine.size() <= 6) {
                    keyboardRowLine.add(xInnerCommandsForButton);
                }
            }
            if (keyboardRows.size() <= 6) {
                keyboardRows.add(keyboardRowLine);
            }
        }
        keyboard.setKeyboard(keyboardRows);
        logger.info("User id {} sent reply keyboard buttons message {} from command {} with a command name like {}", user.getId(), answer, command, commandName);
        return new SendMessage()
                .setChatId(chatId).enableHtml(true)
                .setText(answer)
                .setReplyMarkup(keyboard);
    }


}
