package com.botmasterzzz.telegram.controller;

import com.botmasterzzz.bot.api.impl.methods.BotApiMethod;
import com.botmasterzzz.bot.api.impl.objects.Update;
import com.botmasterzzz.telegram.config.container.BotApiMethodContainer;
import com.botmasterzzz.telegram.config.context.UserContextHolder;
import com.botmasterzzz.telegram.dto.CallBackData;
import com.botmasterzzz.telegram.dto.ProjectCommandDTO;
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

    public List<BotApiMethod> handleMessage(Update update) {
        BotApiMethodController methodController = getHandle(update);
        return methodController.process(update);
    }

    public BotApiMethodController getHandle(Update update) {
        String message = update.hasMessage() ? update.getMessage().getText() : update.getCallbackQuery().getData();
        BotApiMethodController controller;
        if (update.hasCallbackQuery()){
            CallBackData callBackData = gson.fromJson(update.getCallbackQuery().getData(), CallBackData.class);
            message = callBackData.getPath();
            UserContextHolder.currentContext().setCallBackData(callBackData);
        }
        int instanceId = Math.toIntExact(UserContextHolder.currentContext().getInstanceId());
        switch (instanceId){
            case 1:
                controller = container.getControllerMap().get("getparts-" + message);
                return controller;
            case 5:
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
            default:
                controller = container.getControllerMap().get("text");
        }
        return controller;
    }

}
