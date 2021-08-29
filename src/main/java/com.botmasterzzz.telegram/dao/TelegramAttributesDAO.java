package com.botmasterzzz.telegram.dao;

import com.botmasterzzz.telegram.entity.TelegramAttributesDataEntity;

public interface TelegramAttributesDAO {

    void telegramAttributeAdd(TelegramAttributesDataEntity telegramAttributesDataEntity);

    TelegramAttributesDataEntity telegramAttributeGet(Long userId, Long instanceId, String attributeName);
}
