package com.botmasterzzz.telegram.config;

import com.botmasterzzz.bot.api.impl.objects.Update;
import com.botmasterzzz.telegram.config.annotations.BotController;
import com.botmasterzzz.telegram.config.annotations.BotRequestMapping;
import com.botmasterzzz.telegram.config.container.BotApiMethodContainer;
import com.botmasterzzz.telegram.controller.BotApiMethodController;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class TelegramUpdateHandlerBeanPostProcessor implements BeanPostProcessor, Ordered {

    private BotApiMethodContainer container = BotApiMethodContainer.getInstanse();
    private Map<String, Object> botControllerMap = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        if (bean.getClass().isAnnotationPresent(BotController.class)) {
            botControllerMap.put(beanName, bean);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        if (!botControllerMap.containsKey(beanName)) {
            return bean;
        }

        Object original = botControllerMap.get(beanName);
        Arrays.stream(original.getClass().getMethods())
                .filter(method -> method.isAnnotationPresent(BotRequestMapping.class))
                .forEach((Method method) -> generateController(bean, method));
        return bean;
    }


    private void generateController(Object bean, Method method) {
        BotController botController = bean.getClass().getAnnotation(BotController.class);
        BotRequestMapping botRequestMapping = method.getAnnotation(BotRequestMapping.class);

        String path = (botController.value().length != 0 ? botController.value()[0] : "")
                + (botRequestMapping.value().length != 0 ? botRequestMapping.value()[0] : "");

        BotApiMethodController controller = null;

        switch (botRequestMapping.method()[0]) {
            case MSG:
                controller = createControllerUpdate2ApiMethod(bean, method);
                break;
            case EDIT:
                controller = createProcessListForController(bean, method);
                break;
            default:
                break;
        }

        container.addBotController(path, controller);
    }

    private BotApiMethodController createControllerUpdate2ApiMethod(Object bean, Method method) {
        return new BotApiMethodController(bean, method) {
            @Override
            public boolean successUpdatePredicate(Update update) {
                return update != null && update.hasMessage() && update.getMessage().hasText();
            }
        };
    }

    private BotApiMethodController createProcessListForController(Object bean, Method method) {
        return new BotApiMethodController(bean, method) {
            @Override
            public boolean successUpdatePredicate(Update update) {
                return update != null && update.hasCallbackQuery()
                        && update.getCallbackQuery() != null;
            }
        };
    }

    @Override
    public int getOrder() {
        return 100;
    }
}