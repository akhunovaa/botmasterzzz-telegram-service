package com.botmasterzzz.telegram.dao;

import com.botmasterzzz.telegram.entity.TelegramBotUserEntity;

import java.util.List;

public interface TelegramUserDAO {

    long telegramUserAdd(TelegramBotUserEntity telegramBotUserEntity);

    TelegramBotUserEntity telegramUserGet(long id);

    List<TelegramBotUserEntity> getTelegramUserList();


}
