package com.botmasterzzz.telegram.dao;

import com.botmasterzzz.telegram.entity.TelegramBotUserEntity;

import java.util.List;

public interface TelegramUserDAO {

    long telegramUserAdd(TelegramBotUserEntity telegramBotUserEntity);

    TelegramBotUserEntity telegramUserGet(long id);

    TelegramBotUserEntity telegramUserGetTelegramId(long telegramUserId);

    List<TelegramBotUserEntity> getTelegramUserList();

    boolean exists(Class<?> clazz, long idValue);

}
