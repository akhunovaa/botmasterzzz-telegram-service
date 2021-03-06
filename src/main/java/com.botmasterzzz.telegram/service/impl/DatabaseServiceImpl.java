package com.botmasterzzz.telegram.service.impl;

import com.botmasterzzz.telegram.dao.*;
import com.botmasterzzz.telegram.entity.*;
import com.botmasterzzz.telegram.service.DatabaseService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseServiceImpl implements DatabaseService {

    private final TelegramAttributesDAO attributesDAO;

    private final MediaCommentsDataDAO mediaCommentsDataDAO;

    private final TelegramUserDAO telegramUserDAO;

    private final TelegramInstanceDAO telegramInstanceDAO;

    private final TelegramMediaLogDAO telegramMediaLogDAO;

    private final TelegramUserMediaDAO telegramUserMediaDAO;

    public DatabaseServiceImpl(TelegramAttributesDAO attributesDAO, TelegramUserDAO telegramUserDAO, TelegramInstanceDAO telegramInstanceDAO, TelegramMediaLogDAO telegramMediaLogDAO, TelegramUserMediaDAO telegramUserMediaDAO, MediaCommentsDataDAO mediaCommentsDataDAO) {
        this.attributesDAO = attributesDAO;
        this.telegramUserDAO = telegramUserDAO;
        this.telegramInstanceDAO = telegramInstanceDAO;
        this.telegramMediaLogDAO = telegramMediaLogDAO;
        this.telegramUserMediaDAO = telegramUserMediaDAO;
        this.mediaCommentsDataDAO = mediaCommentsDataDAO;
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
    public Long getCountOfDistinctLoggedToMedia(Long mediaFileId) {
        return telegramMediaLogDAO.getCountOfDistinctLoggedToMedia(mediaFileId);
    }

    @Override
    public Long getCountOfDiscuss(Long mediaFileId) {
        return telegramMediaLogDAO.getCountOfDiscuss(mediaFileId);
    }

    @Override
    public Long getCountOfLoggedToUser(Long telegramUserId) {
        return telegramMediaLogDAO.getCountOfMediaLogToUser(telegramUserId);
    }

    @Override
    public Long getUsersCountOfMediaLog(Long telegramUserId) {
        return telegramMediaLogDAO.getUsersCountOfMediaLog(telegramUserId);
    }

    @Override
    public void mediaCommentAdd(String comment, Long mediaFileId, Long telegramUserId) {
        TelegramUserMediaEntity userMediaEntity = telegramUserMediaDAO.telegramUserMediaGet(mediaFileId);
        TelegramBotUserEntity telegramBotUserEntity = telegramUserDAO.telegramUserGetTelegramId(telegramUserId);
        MediaCommentsDataEntity mediaCommentsDataEntity = new MediaCommentsDataEntity();
        mediaCommentsDataEntity.setTelegramUserMediaEntity(userMediaEntity);
        mediaCommentsDataEntity.setData(comment);
        mediaCommentsDataEntity.setTelegramBotUserEntity(telegramBotUserEntity);
        mediaCommentsDataDAO.commentAdd(mediaCommentsDataEntity);
    }

    @Override
    public List<MediaCommentsDataEntity> getCommentsForMedia(Long mediaFileId, int offset, int limit){
        return mediaCommentsDataDAO.getCommentsForMedia(mediaFileId, offset, limit);
    }
}
