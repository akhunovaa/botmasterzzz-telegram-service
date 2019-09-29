package com.botmasterzzz.telegram.controller;

import com.botmasterzzz.bot.api.impl.methods.PartialBotApiMethod;
import com.botmasterzzz.bot.api.impl.objects.Update;
import com.botmasterzzz.telegram.config.container.BotApiMethodContainer;
import com.botmasterzzz.telegram.config.context.UserContextHolder;
import com.botmasterzzz.telegram.dto.CallBackData;
import com.botmasterzzz.telegram.dto.ProjectCommandDTO;
import com.botmasterzzz.telegram.service.TelegramBotStatisticService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Handle {

    private static final Logger logger = LoggerFactory.getLogger(BotApiMethodController.class);

    private static BotApiMethodContainer container = BotApiMethodContainer.getInstanse();

    @Autowired
    private Gson gson;

    @Autowired
    private TelegramBotStatisticService telegramBotStatisticService;

    public PartialBotApiMethod handleMessage(Update update) {
        BotApiMethodController methodController = getHandle(update);
        return methodController.process(update);
    }

    public BotApiMethodController getHandle(Update update) {
        String message = update.hasMessage() ? update.getMessage().getText() : update.getCallbackQuery().getData();
        CallBackData callBackData;
        int instanceId = Math.toIntExact(UserContextHolder.currentContext().getInstanceId());
        BotApiMethodController controller;
        if (update.hasCallbackQuery()){
            boolean userExists = telegramBotStatisticService.telegramUserExists(update.getCallbackQuery().getFrom().getId());
            if (!userExists){
                telegramBotStatisticService.telegramUserAdd(update.getCallbackQuery().getFrom());
            }
            telegramBotStatisticService.telegramStatisticAdd(update.getCallbackQuery().getMessage(), (long) instanceId, update.getCallbackQuery().getFrom().getId(), update.getCallbackQuery().getData());
        }else {
            boolean userExists = telegramBotStatisticService.telegramUserExists(update.getMessage().getFrom().getId());
            if (!userExists){
                telegramBotStatisticService.telegramUserAdd(update.getMessage().getFrom());
            }
            telegramBotStatisticService.telegramStatisticAdd(update.getMessage(), (long) instanceId, update.getMessage().getFrom().getId());
        }
        switch (instanceId){
            case 1:
                if (update.hasCallbackQuery()){
                    callBackData = gson.fromJson(update.getCallbackQuery().getData(), CallBackData.class);
                    UserContextHolder.currentContext().setCallBackData(callBackData);
                    message = callBackData.getPath();
                }
                boolean remain = UserContextHolder.currentContext().isRemain();
                controller = !remain ? container.getControllerMap().get("getparts-" + message) : container.getControllerMap().get("getparts-SECRET-FIND") ;
                return controller;
            case 5:
                if (update.hasCallbackQuery()){
                    callBackData = gson.fromJson(update.getCallbackQuery().getData(), CallBackData.class);
                    UserContextHolder.currentContext().setCallBackData(callBackData);
                    message = callBackData.getPath();
                }
                controller = container.getControllerMap().get("gkh-" + message);
                return controller;
        }
        int commandMessageType = 1;
        UserContextHolder.currentContext().setProjectCommandDTO(null);
        List<ProjectCommandDTO> projectCommandDTOList = UserContextHolder.currentContext().getProjectCommandDTOList();
        for (ProjectCommandDTO projectCommandDTO : projectCommandDTOList) {
            if (update.hasMessage()){
                if(projectCommandDTO.getCommand().equalsIgnoreCase(message)){
                    commandMessageType = Math.toIntExact(projectCommandDTO.getCommandType().getId());
                    logger.info("Command Message Type {} was formed to command {} with an answer {} ", commandMessageType, projectCommandDTO.getCommandName(), projectCommandDTO.getAnswer());
                    UserContextHolder.currentContext().setProjectCommandDTO(projectCommandDTO);
                }
            }else if (update.hasCallbackQuery()) {
                if (projectCommandDTO.getCommand().equalsIgnoreCase(message)) {
                    commandMessageType = Math.toIntExact(projectCommandDTO.getCommandType().getId());
                    logger.info("Command Message Type {} was formed to command {} with an answer {} ", commandMessageType, projectCommandDTO.getCommandName(), projectCommandDTO.getAnswer());
                    UserContextHolder.currentContext().setProjectCommandDTO(projectCommandDTO);
                }
            }
        }
        switch (commandMessageType){
            case 1:
                controller = container.getControllerMap().get("text");
                break;
            case 2:
                controller = container.getControllerMap().get("inner_button");
                break;
            case 3:
                controller = container.getControllerMap().get("outter_button");
                break;
            case 4:
                controller = container.getControllerMap().get("picture");
                break;
            case 5:
                controller = container.getControllerMap().get("random_text");
                break;
            case 6:
                controller = container.getControllerMap().get("random_picture");
                break;
            default:
                controller = container.getControllerMap().get("text");
        }
        return controller;
    }

}
