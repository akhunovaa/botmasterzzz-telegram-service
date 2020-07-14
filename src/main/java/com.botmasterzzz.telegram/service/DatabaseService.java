package com.botmasterzzz.telegram.service;

import com.botmasterzzz.telegram.entity.MediaCommentsDataEntity;
import com.botmasterzzz.telegram.entity.TelegramAttributesDataEntity;

import java.util.List;

public interface DatabaseService {

    void telegramAttributeAdd(String name, String value, Long botInstanceId, Long userId);

    TelegramAttributesDataEntity telegramAttributeGet(Long userId, Long instanceId, String attributeName);

    void telegramMediaLogAdd(String note, Long mediaId, Long telegramUserId);

    Long getCountOfLoggedToMedia(Long mediaFileId);

    Long getCountOfDiscuss(Long mediaFileId);

    Long getCountOfLoggedToUser(Long telegramUserId);

    Long getUsersCountOfMediaLog(Long telegramUserId);

    void mediaCommentAdd(String comment, Long mediaFileId, Long telegramUserId);

    List<MediaCommentsDataEntity> getCommentsForMedia(Long mediaFileId, int offset, int limit);
}
