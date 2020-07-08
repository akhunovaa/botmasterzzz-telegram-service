package com.botmasterzzz.telegram.service.impl;

import com.botmasterzzz.telegram.dao.TelegramAttributesDAO;
import com.botmasterzzz.telegram.dao.TelegramInstanceDAO;
import com.botmasterzzz.telegram.dao.TelegramUserDAO;
import com.botmasterzzz.telegram.entity.TelegramAttributesDataEntity;
import com.botmasterzzz.telegram.entity.TelegramBotUserEntity;
import com.botmasterzzz.telegram.entity.TelegramInstanceEntity;
import com.botmasterzzz.telegram.service.DatabaseService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class DatabaseServiceImpl implements DatabaseService {

    private final TelegramAttributesDAO attributesDAO;

    private final TelegramUserDAO telegramUserDAO;

    private final TelegramInstanceDAO telegramInstanceDAO;

    public DatabaseServiceImpl(TelegramAttributesDAO attributesDAO, TelegramUserDAO telegramUserDAO, TelegramInstanceDAO telegramInstanceDAO) {
        this.attributesDAO = attributesDAO;
        this.telegramUserDAO = telegramUserDAO;
        this.telegramInstanceDAO = telegramInstanceDAO;
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
}
