package com.botmasterzzz.telegram.controller.telegram;

import com.botmasterzzz.bot.api.impl.methods.BotApiMethod;
import com.botmasterzzz.bot.api.impl.methods.send.SendMessage;
import com.botmasterzzz.bot.api.impl.methods.update.EditMessageText;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    public BotApiMethod pictureMessage(Update update) {
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
        firstInlineButton.setCallbackData(command);
        inlineKeyboardButtonsFirstRow.add(firstInlineButton);

        inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);
        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);

        logger.info("User id {} sent photo message {} from command {} with a command name like {}", user.getId(), answer, command, commandName);
        return new SendMessage()
                .setChatId(chatId).enableHtml(true)
                .setText(answer);
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
        List<ProjectCommandDTO> projectCommandDTOList = UserContextHolder.currentContext().getProjectCommandDTOList();
        ProjectCommandDTO parentProjectCommandDTO = new ProjectCommandDTO();
        ProjectCommandDTO secondProjectCommandDTO = new ProjectCommandDTO();
        ProjectCommandDTO thirdProjectCommandDTO = new ProjectCommandDTO();
        ProjectCommandDTO fourthProjectCommandDTO = new ProjectCommandDTO();
        ProjectCommandDTO fifthProjectCommandDTO = new ProjectCommandDTO();

        for (ProjectCommandDTO commandDTO : projectCommandDTOList) {
            if (null != projectCommandDTO && null != commandDTO.getParent() && commandDTO.getParent().getId() == projectCommandDTO.getId()){
                if (null == commandDTO.getParent() && commandDTO.getCommandType().getId() == 2){
                    parentProjectCommandDTO = commandDTO;
                    for (ProjectCommandDTO commandDTOChild : projectCommandDTOList) {
                        if (null != commandDTOChild.getParent() && commandDTOChild.getParent().getId() == projectCommandDTO.getId()){
                            secondProjectCommandDTO = commandDTOChild;
                            for (ProjectCommandDTO commandDTOChildSecond : projectCommandDTOList) {
                                if (null != commandDTOChildSecond.getParent() && commandDTOChild.getId() == commandDTOChildSecond.getParent().getId()){
                                    thirdProjectCommandDTO = commandDTOChild;
                                    for (ProjectCommandDTO commandDTOChildThird : projectCommandDTOList) {
                                        if (null != commandDTOChildThird.getParent() && null != commandDTOChildThird.getParent().getParent() && commandDTOChild.getId() == commandDTOChildThird.getParent().getParent().getId()){
                                            fourthProjectCommandDTO = commandDTOChild;
                                            for (ProjectCommandDTO commandDTOChildFourth : projectCommandDTOList) {
                                                if (null != commandDTOChildFourth.getParent().getParent().getParent() && commandDTOChild.getId() == commandDTOChildFourth.getParent().getParent().getParent().getId()){
                                                    fifthProjectCommandDTO = commandDTOChild;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }else if (null == commandDTO.getParent().getParent() && commandDTO.getCommandType().getId() == 2){
                    secondProjectCommandDTO = commandDTO;
                    parentProjectCommandDTO = commandDTO.getParent();
                    for (ProjectCommandDTO commandDTOChild : projectCommandDTOList) {
                        if (null != commandDTOChild.getParent() && commandDTOChild.getParent().getId() == projectCommandDTO.getId() && commandDTOChild.getCommandType().getId() == 2){
                            thirdProjectCommandDTO = commandDTOChild;
                        }
                    }
                }else if (null == commandDTO.getParent().getParent().getParent() && commandDTO.getCommandType().getId() == 2){
                    secondProjectCommandDTO = commandDTO;
                    thirdProjectCommandDTO = commandDTO.getParent();
                    parentProjectCommandDTO = commandDTO.getParent().getParent();
                    for (ProjectCommandDTO commandDTOChild : projectCommandDTOList) {
                        if (null != commandDTOChild.getParent() && commandDTOChild.getParent().getId() == projectCommandDTO.getId() && commandDTOChild.getCommandType().getId() == 2){
                            fourthProjectCommandDTO = commandDTOChild;
                        }
                    }
                }else if (null == commandDTO.getParent().getParent().getParent().getParent() && commandDTO.getCommandType().getId() == 2){
                    secondProjectCommandDTO = commandDTO;
                    thirdProjectCommandDTO = commandDTO.getParent();
                    fourthProjectCommandDTO = commandDTO.getParent().getParent();
                    parentProjectCommandDTO = commandDTO.getParent().getParent().getParent();
                    for (ProjectCommandDTO commandDTOChild : projectCommandDTOList) {
                        if (null != commandDTOChild.getParent() && commandDTOChild.getParent().getId() == projectCommandDTO.getId()){
                            fifthProjectCommandDTO = commandDTOChild;
                        }
                    }
                }else if (null == commandDTO.getParent().getParent().getParent().getParent().getParent() && commandDTO.getCommandType().getId() == 2){
                    secondProjectCommandDTO = commandDTO;
                    thirdProjectCommandDTO = commandDTO.getParent();
                    fourthProjectCommandDTO = commandDTO.getParent().getParent();
                    fifthProjectCommandDTO = commandDTO.getParent().getParent().getParent();
                    parentProjectCommandDTO = commandDTO.getParent().getParent().getParent().getParent();
                }
            }else if (null != projectCommandDTO && null != projectCommandDTO.getParent() && commandDTO.getCommandType().getId() == 2){
                for (ProjectCommandDTO commandDTOChild : projectCommandDTOList) {
                    if (null != commandDTOChild.getParent() && commandDTOChild.getParent().getId() == projectCommandDTO.getId()){
                        secondProjectCommandDTO = commandDTOChild;
                        for (ProjectCommandDTO commandDTOChildSecond : projectCommandDTOList) {
                            if (null != commandDTOChildSecond.getParent() && commandDTOChild.getId() == commandDTOChildSecond.getParent().getId()){
                                thirdProjectCommandDTO = commandDTOChild;
                                for (ProjectCommandDTO commandDTOChildThird : projectCommandDTOList) {
                                    if (null != commandDTOChildThird.getParent().getParent() && commandDTOChild.getId() == commandDTOChildThird.getParent().getParent().getId()){
                                        fourthProjectCommandDTO = commandDTOChild;
                                        for (ProjectCommandDTO commandDTOChildFourth : projectCommandDTOList) {
                                            if (null != commandDTOChildFourth.getParent().getParent().getParent() && commandDTOChild.getId() == commandDTOChildFourth.getParent().getParent().getParent().getId()){
                                                fifthProjectCommandDTO = commandDTOChild;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();

        List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsSecondRow = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsThirdRow = new ArrayList<>();

        InlineKeyboardButton firstInlineButton = new InlineKeyboardButton();
        firstInlineButton.setText(commandName);
        firstInlineButton.setCallbackData(commandName);
        inlineKeyboardButtonsFirstRow.add(firstInlineButton);

        if(null != parentProjectCommandDTO.getCommand() && parentProjectCommandDTO.getId() != projectCommandDTO.getId()){
            InlineKeyboardButton inlineButton = new InlineKeyboardButton();
            inlineButton.setText(parentProjectCommandDTO.getCommandName());
            inlineButton.setCallbackData(parentProjectCommandDTO.getCommandName());
            inlineKeyboardButtonsFirstRow.add(inlineButton);
        }
        if(null != secondProjectCommandDTO.getCommand() && secondProjectCommandDTO.getId() != parentProjectCommandDTO.getId()){
            InlineKeyboardButton inlineButton = new InlineKeyboardButton();
            inlineButton.setText(secondProjectCommandDTO.getCommandName());
            inlineButton.setCallbackData(secondProjectCommandDTO.getCommandName());
            inlineKeyboardButtonsSecondRow.add(inlineButton);
        }
        if(null != thirdProjectCommandDTO.getCommand() && thirdProjectCommandDTO.getId() != secondProjectCommandDTO.getId()){
            InlineKeyboardButton inlineButton = new InlineKeyboardButton();
            inlineButton.setText(thirdProjectCommandDTO.getCommandName());
            inlineButton.setCallbackData(thirdProjectCommandDTO.getCommandName());
            inlineKeyboardButtonsSecondRow.add(inlineButton);
        }
        if(null != fourthProjectCommandDTO.getCommand() && fourthProjectCommandDTO.getId() != thirdProjectCommandDTO.getId()){
            InlineKeyboardButton inlineButton = new InlineKeyboardButton();
            inlineButton.setText(fourthProjectCommandDTO.getCommandName());
            inlineButton.setCallbackData(fourthProjectCommandDTO.getCommandName());
            inlineKeyboardButtonsThirdRow.add(inlineButton);
        }
        if(null != fifthProjectCommandDTO.getCommand() && fifthProjectCommandDTO.getId() != fourthProjectCommandDTO.getId()){
            InlineKeyboardButton inlineButton = new InlineKeyboardButton();
            inlineButton.setText(fifthProjectCommandDTO.getCommandName());
            inlineButton.setCallbackData(fifthProjectCommandDTO.getCommandName());
            inlineKeyboardButtonsThirdRow.add(inlineButton);
        }


        inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);
        inlineKeyboardButtons.add(inlineKeyboardButtonsSecondRow);
        inlineKeyboardButtons.add(inlineKeyboardButtonsThirdRow);
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
        List<ProjectCommandDTO> projectCommandDTOList = UserContextHolder.currentContext().getProjectCommandDTOList();
        ProjectCommandDTO parentProjectCommandDTO = new ProjectCommandDTO();
        ProjectCommandDTO secondProjectCommandDTO = new ProjectCommandDTO();
        ProjectCommandDTO thirdProjectCommandDTO = new ProjectCommandDTO();
        ProjectCommandDTO fourthProjectCommandDTO = new ProjectCommandDTO();
        ProjectCommandDTO fifthProjectCommandDTO = new ProjectCommandDTO();

        for (ProjectCommandDTO commandDTO : projectCommandDTOList) {
            if (null != projectCommandDTO && null != projectCommandDTO.getParent() && commandDTO.getId() == projectCommandDTO.getParent().getId()){
                if (null == commandDTO.getParent()){
                    parentProjectCommandDTO = commandDTO;
                    for (ProjectCommandDTO commandDTOChild : projectCommandDTOList) {
                        if (null != commandDTOChild.getParent() && commandDTOChild.getParent().getId() == projectCommandDTO.getId()){
                            secondProjectCommandDTO = commandDTOChild;
                            for (ProjectCommandDTO commandDTOChildSecond : projectCommandDTOList) {
                                if (null != commandDTOChildSecond.getParent() && commandDTOChild.getId() == commandDTOChildSecond.getParent().getId()){
                                    thirdProjectCommandDTO = commandDTOChild;
                                    for (ProjectCommandDTO commandDTOChildThird : projectCommandDTOList) {
                                        if (null != commandDTOChildThird.getParent() && null != commandDTOChildThird.getParent().getParent() && commandDTOChild.getId() == commandDTOChildThird.getParent().getParent().getId()){
                                            fourthProjectCommandDTO = commandDTOChild;
                                            for (ProjectCommandDTO commandDTOChildFourth : projectCommandDTOList) {
                                                if (null != commandDTOChildThird.getParent() &&  null != commandDTOChildFourth.getParent().getParent() &&  null != commandDTOChildFourth.getParent().getParent().getParent() && commandDTOChild.getId() == commandDTOChildFourth.getParent().getParent().getParent().getId()){
                                                    fifthProjectCommandDTO = commandDTOChild;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }else if (null == commandDTO.getParent().getParent()){
                    secondProjectCommandDTO = commandDTO;
                    parentProjectCommandDTO = commandDTO.getParent();
                    for (ProjectCommandDTO commandDTOChild : projectCommandDTOList) {
                        if (null != commandDTOChild.getParent() && commandDTOChild.getParent().getId() == projectCommandDTO.getId()){
                            thirdProjectCommandDTO = commandDTOChild;
                        }
                    }
                }else if (null == commandDTO.getParent().getParent().getParent()){
                    secondProjectCommandDTO = commandDTO;
                    thirdProjectCommandDTO = commandDTO.getParent();
                    parentProjectCommandDTO = commandDTO.getParent().getParent();
                    for (ProjectCommandDTO commandDTOChild : projectCommandDTOList) {
                        if (null != commandDTOChild.getParent() && commandDTOChild.getParent().getId() == projectCommandDTO.getId()){
                            fourthProjectCommandDTO = commandDTOChild;
                        }
                    }
                }else if (null == commandDTO.getParent().getParent().getParent().getParent()){
                    secondProjectCommandDTO = commandDTO;
                    thirdProjectCommandDTO = commandDTO.getParent();
                    fourthProjectCommandDTO = commandDTO.getParent().getParent();
                    parentProjectCommandDTO = commandDTO.getParent().getParent().getParent();
                    for (ProjectCommandDTO commandDTOChild : projectCommandDTOList) {
                        if (null != commandDTOChild.getParent() && commandDTOChild.getParent().getId() == projectCommandDTO.getId()){
                            fifthProjectCommandDTO = commandDTOChild;
                        }
                    }
                }else if (null == commandDTO.getParent().getParent().getParent().getParent().getParent()){
                    secondProjectCommandDTO = commandDTO;
                    thirdProjectCommandDTO = commandDTO.getParent();
                    fourthProjectCommandDTO = commandDTO.getParent().getParent();
                    fifthProjectCommandDTO = commandDTO.getParent().getParent().getParent();
                    parentProjectCommandDTO = commandDTO.getParent().getParent().getParent().getParent();
                }
            }else if (null != projectCommandDTO && null == projectCommandDTO.getParent()){
                for (ProjectCommandDTO commandDTOChild : projectCommandDTOList) {
                    if (null != commandDTOChild.getParent() && commandDTOChild.getParent().getId() == projectCommandDTO.getId()){
                        secondProjectCommandDTO = commandDTOChild;
                        for (ProjectCommandDTO commandDTOChildSecond : projectCommandDTOList) {
                            if (null != commandDTOChildSecond.getParent() && commandDTOChild.getId() == commandDTOChildSecond.getParent().getId()){
                                thirdProjectCommandDTO = commandDTOChild;
                                for (ProjectCommandDTO commandDTOChildThird : projectCommandDTOList) {
                                    if (null != commandDTOChildThird.getParent() && null != commandDTOChildThird.getParent().getParent() && commandDTOChild.getId() == commandDTOChildThird.getParent().getParent().getId()){
                                        fourthProjectCommandDTO = commandDTOChild;
                                        for (ProjectCommandDTO commandDTOChildFourth : projectCommandDTOList) {
                                            if (null != commandDTOChildFourth.getParent() && null != commandDTOChildFourth.getParent().getParent() && null != commandDTOChildFourth.getParent().getParent().getParent() && commandDTOChild.getId() == commandDTOChildFourth.getParent().getParent().getParent().getId()){
                                                fifthProjectCommandDTO = commandDTOChild;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRowLineOne = new KeyboardRow();
        KeyboardRow keyboardRowLineTwo = new KeyboardRow();
        KeyboardRow keyboardRowLineThree = new KeyboardRow();
        keyboardRowLineOne.add(commandName);
        if(null != parentProjectCommandDTO.getCommand()){
            keyboardRowLineOne.add(parentProjectCommandDTO.getCommandName());
        }
        if(null != secondProjectCommandDTO.getCommand()){
            keyboardRowLineTwo.add(secondProjectCommandDTO.getCommandName());
        }
        if(null != thirdProjectCommandDTO.getCommand()){
            keyboardRowLineTwo.add(thirdProjectCommandDTO.getCommandName());
        }
        if(null != fourthProjectCommandDTO.getCommand()){
            keyboardRowLineThree.add(fourthProjectCommandDTO.getCommandName());
        }
        if(null != fifthProjectCommandDTO.getCommand()){
            keyboardRowLineThree.add(fifthProjectCommandDTO.getCommandName());
        }
        keyboardRows.add(keyboardRowLineOne);
        keyboardRows.add(keyboardRowLineTwo);
        keyboardRows.add(keyboardRowLineThree);
        keyboard.setKeyboard(keyboardRows);

        logger.info("User id {} sent reply keyboard buttons message {} from command {} with a command name like {}", user.getId(), answer, command, commandName);
        return new SendMessage()
                .setChatId(chatId).enableHtml(true)
                .setText(answer)
                .setReplyMarkup(keyboard);
    }


}
