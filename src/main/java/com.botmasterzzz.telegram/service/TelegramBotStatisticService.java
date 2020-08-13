package com.botmasterzzz.telegram.service;

import com.botmasterzzz.bot.api.impl.objects.Message;
import com.botmasterzzz.bot.api.impl.objects.User;
import com.botmasterzzz.telegram.entity.TelegramBotUsageStatisticEntity;
import com.botmasterzzz.telegram.entity.TelegramBotUserEntity;

import java.util.List;

public interface TelegramBotStatisticService {

    boolean telegramUserExists(long telegramUserId);

    void telegramUserAdd(User user);

    void telegramUserAdd(User user, Long referralId);

    List<TelegramBotUserEntity> getTelegramUserList();

    void telegramStatisticAdd(Message message, Long botInstance, long telegramUserId);

    void telegramStatisticAdd(String message, Long telegramUserId);

    void telegramStatisticAdd(Message message, Long botInstance, long telegramUserId, String callBackData);

    TelegramBotUsageStatisticEntity telegramStatisticGet(long id);

    List<TelegramBotUsageStatisticEntity> getTelegramStatisticList();
}
