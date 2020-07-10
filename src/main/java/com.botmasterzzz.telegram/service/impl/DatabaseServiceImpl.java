package com.botmasterzzz.telegram.service.impl;

import com.botmasterzzz.telegram.dao.*;
import com.botmasterzzz.telegram.entity.*;
import com.botmasterzzz.telegram.service.DatabaseService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class DatabaseServiceImpl implements DatabaseService {

    private final TelegramAttributesDAO attributesDAO;

    private final TelegramUserDAO telegramUserDAO;

    private final TelegramInstanceDAO telegramInstanceDAO;

    private final TelegramMediaLogDAO telegramMediaLogDAO;

    private final TelegramUserMediaDAO telegramUserMediaDAO;

    public DatabaseServiceImpl(TelegramAttributesDAO attributesDAO, TelegramUserDAO telegramUserDAO, TelegramInstanceDAO telegramInstanceDAO, TelegramMediaLogDAO telegramMediaLogDAO, TelegramUserMediaDAO telegramUserMediaDAO) {
        this.attributesDAO = attributesDAO;
        this.telegramUserDAO = telegramUserDAO;
        this.telegramInstanceDAO = telegramInstanceDAO;
        this.telegramMediaLogDAO = telegramMediaLogDAO;
        this.telegramUserMediaDAO = telegramUserMediaDAO;
    }

    @Async
    @Override
    public void telegramAttributeAdd(String name, String value, Long botInstanceId, Long telegramUserId) {
        TelegramBotUserEntity telegramBotUserEntity = telegramUserDAO.telegramUserGetTelegramId(telegramUserId);
        TelegramInstanceEntity telegramInstanceEntity = telegramInstanceDAO.telegramInstanceGet(telegramBotUserEntity.getId(), botInstanceId);
        TelegramAttributesDataEntity telegramAttributesDataEntity = new TelegramAttributesDataEntity();
        telegramAttributesDataEntity.setName(name);
        telegramAttributesDataEntity.setValue(value);
        telegramAttributesDataEntity.setTelegramBotUserEntity(telegramBotUserEntity);
        telegramAttributesDataEntity.setTelegramInstanceEntity(telegramInstanceEntity);
        attributesDAO.telegramAttributeAdd(telegramAttributesDataEntity);
    }

    @Override
    public TelegramAttributesDataEntity telegramAttributeGet(Long userId, Long instanceId, String attributeName) {
        return attributesDAO.telegramAttributeGet(userId, instanceId, attributeName);
    }

    @Async
    @Override
    public void telegramMediaLogAdd(String note, Long mediaId, Long telegramUserId) {
        TelegramUserMediaEntity userMediaEntity = telegramUserMediaDAO.telegramUserMediaGet(mediaId);
        TelegramMediaLogEntity telegramMediaLogEntity = new TelegramMediaLogEntity();
        telegramMediaLogEntity.setNote(note);
        telegramMediaLogEntity.setTelegramUserId(telegramUserId);
        telegramMediaLogEntity.setTelegramUserMediaEntity(userMediaEntity);
        telegramMediaLogDAO.mediaLogAdd(telegramMediaLogEntity);
    }

    @Override
    public Long getCountOfLoggedToMedia(Long mediaFileId) {
        return telegramMediaLogDAO.getCountOfMediaLog(mediaFileId);
    }

    @Override
    public Long getCountOfLoggedToUser(Long telegramUserId) {
        return telegramMediaLogDAO.getCountOfMediaLogToUser(telegramUserId);
    }
}
