package com.botmasterzzz.telegram.service;

import com.botmasterzzz.telegram.entity.TelegramBotUserEntity;

public interface TelegramUserService {

    TelegramBotUserEntity getTelegramUser(Long telegramUserId);

}
