package com.botmasterzzz.telegram.controller;

import com.botmasterzzz.bot.api.impl.methods.PartialBotApiMethod;
import com.botmasterzzz.bot.api.impl.objects.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class BotApiMethodController {

    private static final Logger logger = LoggerFactory.getLogger(BotApiMethodController.class);

    private Object bean;
    private Method method;
    private Process processUpdate;

    public BotApiMethodController(Object bean, Method method) {
        this.bean = bean;
        this.method = method;
        processUpdate = this::processSingle;
    }

    public abstract boolean successUpdatePredicate(Update update);

    public PartialBotApiMethod process(Update update) {
        PartialBotApiMethod process = null;
        try {
            process = processUpdate.accept(update);
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error("bad invoke method", e);
        }
        return process;
    }

    private PartialBotApiMethod processSingle(Update update) throws InvocationTargetException, IllegalAccessException {
        PartialBotApiMethod partialBotApiMethod = (PartialBotApiMethod) method.invoke(bean, update);
        return partialBotApiMethod;
    }

    private interface Process {
        PartialBotApiMethod accept(Update update) throws InvocationTargetException, IllegalAccessException;
    }
}