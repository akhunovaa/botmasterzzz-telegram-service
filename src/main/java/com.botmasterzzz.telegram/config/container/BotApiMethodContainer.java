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

    public void addBotController(String path, BotApiMethodController controller) {
        if (controllerMap.containsKey(path)) {
            LOGGER.info("path {} already added", path);
        }
        controllerMap.put(path, controller);
    }

    public BotApiMethodController getBotApiMethodController(String path) {
        return controllerMap.get(path);
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