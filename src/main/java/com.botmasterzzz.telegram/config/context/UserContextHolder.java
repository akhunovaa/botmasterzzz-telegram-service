package com.botmasterzzz.telegram.config.context;

import com.botmasterzzz.bot.api.impl.objects.Chat;
import com.botmasterzzz.bot.api.impl.objects.Update;
import com.botmasterzzz.bot.api.impl.objects.User;
import com.botmasterzzz.telegram.dto.ProjectCommandDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserContextHolder {

    private static final Map<Long, UserContext> tgUserContexts = new HashMap<>();
    private static final ThreadLocal<UserContext> tgContextThreadLocal = new ThreadLocal<>();

    private UserContextHolder() {
        throw new IllegalStateException("Utility class");
    }

    public static UserContext currentContext() {
        return tgContextThreadLocal.get();
    }

    public static void setupContext(Update update, List<ProjectCommandDTO> projectCommandDTOList) {
        User user;
        Chat chat;
        Long chatId;
        if (update.hasCallbackQuery()) {
            user = update.getCallbackQuery().getFrom();
            chat = update.getCallbackQuery().getMessage().getChat();
            chatId = update.getCallbackQuery().getMessage().getChatId();
        }else {
            user = update.getMessage().getFrom();
            chat = update.getMessage().getChat();
            chatId = chat.getId();
        }

        if (!tgUserContexts.containsKey(chatId)) {
            UserContext value = new UserContext();
            value.setUpdate(update);
            value.setUser(user);
            value.setChat(chat);
            value.setProjectCommandDTOList(projectCommandDTOList);
            tgUserContexts.put(chatId, value);
        }
        UserContext tgContext = tgUserContexts.get(chatId);
        tgContextThreadLocal.set(tgContext);
    }
}
