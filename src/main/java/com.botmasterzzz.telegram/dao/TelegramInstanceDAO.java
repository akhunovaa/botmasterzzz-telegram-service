package com.botmasterzzz.telegram.dao;

import com.botmasterzzz.telegram.entity.TelegramInstanceEntity;

import java.util.List;

public interface TelegramInstanceDAO {

    Long telegramInstanceAdd(TelegramInstanceEntity telegramInstanceEntity);

    TelegramInstanceEntity telegramInstanceGet(Long userId, Long projectId);

    List<TelegramInstanceEntity> getTelegramInstanceList(long userId);

    List<TelegramInstanceEntity> getFullTelegramInstanceList();

    void telegramInstanceDelete(TelegramInstanceEntity telegramInstanceEntity);

    TelegramInstanceEntity telegramInstanceUpdate(TelegramInstanceEntity telegramInstanceEntity);

}
