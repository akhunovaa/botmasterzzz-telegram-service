package com.botmasterzzz.telegram.config.container;

import com.botmasterzzz.telegram.controller.BotApiMethodController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class BotApiMethodContainer {

    private static final Logger LOGGER = LoggerFactory.getLogger(BotApiMethodContainer.class);

    private Map<String, BotApiMethodController> controllerMap;

    public static BotApiMethodContainer getInstanse() {
        return Holder.INST;
    }

    public void addBotController(String type, BotApiMethodController controller) {
        if (controllerMap.containsKey(type)) {
            LOGGER.info("path {} already added", type);
        }
        controllerMap.put(type, controller);
    }

    public BotApiMethodController getBotApiMethodController(String type) {
        return controllerMap.get(type);
    }

    private BotApiMethodContainer() {
        controllerMap = new HashMap<>();
    }

    private static class Holder {
        static final BotApiMethodContainer INST = new BotApiMethodContainer();
    }

    public Map<String, BotApiMethodController> getControllerMap() {
        return controllerMap;
    }
}