package com.botmasterzzz.telegram.controller;

import com.botmasterzzz.bot.api.impl.methods.BotApiMethod;
import com.botmasterzzz.bot.api.impl.methods.PartialBotApiMethod;
import com.botmasterzzz.bot.api.impl.objects.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BotApiMethodController {

    private static final Logger logger = LoggerFactory.getLogger(BotApiMethodController.class);

    private Object bean;
    private Method method;
    private Process processUpdate;

    public BotApiMethodController(Object bean, Method method) {
        this.bean = bean;
        this.method = method;

        processUpdate = typeListReturnDetect() ? this::processList : this::processSingle;
    }

    public abstract boolean successUpdatePredicate(Update update);

    public List<PartialBotApiMethod> process(Update update) {
        try {
            return processUpdate.accept(update);
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error("bad invoke method", e);
        }
        return Collections.emptyList();
    }

    public boolean typeListReturnDetect() {
        return List.class.equals(method.getReturnType());
    }

    private List<PartialBotApiMethod> processSingle(Update update)
            throws InvocationTargetException, IllegalAccessException {
        PartialBotApiMethod botApiMethod = (PartialBotApiMethod) method.invoke(bean, update);
        return botApiMethod != null ? Collections.singletonList(botApiMethod) : new ArrayList<>(0);
    }

    public List<PartialBotApiMethod> processList(Update update)
            throws InvocationTargetException, IllegalAccessException {
        List<PartialBotApiMethod> botApiMethods = (List<PartialBotApiMethod>) method.invoke(bean, update);
        return botApiMethods != null ? botApiMethods : new ArrayList<>(0);
    }

    private interface Process {

        List<PartialBotApiMethod> accept(Update update)
                throws InvocationTargetException, IllegalAccessException;
    }
}