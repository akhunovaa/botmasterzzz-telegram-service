package com.botmasterzzz.telegram.service;

import com.botmasterzzz.telegram.entity.TelegramBotUserEntity;

import java.util.List;

public interface TelegramUserService {

    TelegramBotUserEntity getTelegramUser(Long telegramUserId);

    List<TelegramBotUserEntity> getTelegramUserList();

}
