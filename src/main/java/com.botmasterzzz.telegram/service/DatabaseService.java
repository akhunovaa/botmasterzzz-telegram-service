package com.botmasterzzz.telegram.service;

import com.botmasterzzz.telegram.entity.TelegramAttributesDataEntity;

public interface DatabaseService {

    void telegramAttributeAdd(String name, String value, Long botInstanceId, Long userId);

    TelegramAttributesDataEntity telegramAttributeGet(Long userId, Long instanceId, String attributeName);

    void telegramMediaLogAdd(String note, Long mediaId, Long telegramUserId);

    Long getCountOfLoggedToMedia(Long mediaFileId);
}
