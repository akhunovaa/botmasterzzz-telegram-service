package com.botmasterzzz.telegram.controller;

import com.botmasterzzz.bot.api.impl.methods.BotApiMethod;
import com.botmasterzzz.bot.api.impl.objects.Update;
import com.botmasterzzz.telegram.config.container.BotApiMethodContainer;
import com.botmasterzzz.telegram.config.context.UserContextHolder;
import com.botmasterzzz.telegram.dto.ProjectCommandDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Handle {

    private static final Logger logger = LoggerFactory.getLogger(BotApiMethodController.class);

    private static BotApiMethodContainer container = BotApiMethodContainer.getInstanse();

    public List<BotApiMethod> handleMessage(Update update) {
        BotApiMethodController methodController = getHandle(update);
        return methodController.process(update);
    }

    public BotApiMethodController getHandle(Update update) {
        String message = update.hasMessage() ? update.getMessage().getText() : update.getCallbackQuery().getMessage().getText();
        BotApiMethodController controller;
        int commandMessageType = 1;

        List<ProjectCommandDTO> projectCommandDTOList = UserContextHolder.currentContext().getProjectCommandDTOList();
        for (ProjectCommandDTO projectCommandDTO : projectCommandDTOList) {
            if(projectCommandDTO.getCommandName().equalsIgnoreCase(message) || projectCommandDTO.getCommand().equalsIgnoreCase(message)){
                commandMessageType = Math.toIntExact(projectCommandDTO.getCommandType().getId());
                logger.info("Command Message Type {} was formed to command {} with an answer {} ", commandMessageType, projectCommandDTO.getCommandName(), projectCommandDTO.getAnswer());
                UserContextHolder.currentContext().setProjectCommandDTO(projectCommandDTO);
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
