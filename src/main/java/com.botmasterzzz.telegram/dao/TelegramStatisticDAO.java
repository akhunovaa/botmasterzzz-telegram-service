package com.botmasterzzz.telegram.dao;

import com.botmasterzzz.telegram.entity.TelegramBotUsageStatisticEntity;

import java.util.List;

public interface TelegramStatisticDAO {

    void telegramStatisticAdd(TelegramBotUsageStatisticEntity telegramBotUsageStatisticEntity);

    TelegramBotUsageStatisticEntity telegramStatisticGet(long id);

    List<TelegramBotUsageStatisticEntity> getTelegramStatisticList();


}
