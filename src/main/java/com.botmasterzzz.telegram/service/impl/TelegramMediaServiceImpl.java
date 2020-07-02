package com.botmasterzzz.telegram.service.impl;

import com.botmasterzzz.bot.api.impl.objects.Document;
import com.botmasterzzz.bot.api.impl.objects.PhotoSize;
import com.botmasterzzz.bot.api.impl.objects.Video;
import com.botmasterzzz.telegram.dao.TelegramUserDAO;
import com.botmasterzzz.telegram.dao.TelegramUserMediaDAO;
import com.botmasterzzz.telegram.dto.OwnerStatisticDTO;
import com.botmasterzzz.telegram.dto.TopRatingUsersDTO;
import com.botmasterzzz.telegram.entity.TelegramBotUserEntity;
import com.botmasterzzz.telegram.entity.TelegramMediaStatisticEntity;
import com.botmasterzzz.telegram.entity.TelegramUserMediaEntity;
import com.botmasterzzz.telegram.service.TelegramMediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TelegramMediaServiceImpl implements TelegramMediaService {

    @Autowired
    private TelegramUserMediaDAO telegramUserMediaDAO;

    @Autowired
    private TelegramUserDAO telegramUserDAO;

    @Override
    public void telegramUserMediaAdd(List<PhotoSize> pictures, Long telegramUserId, boolean isAnon, String message) {
        TelegramBotUserEntity telegramBotUserEntity = telegramUserDAO.telegramUserGetTelegramId(telegramUserId);
        int lasIndex = pictures.size() - 1;
        PhotoSize picture = pictures.get(lasIndex);
        TelegramUserMediaEntity telegramUserMediaEntity = new TelegramUserMediaEntity();
        telegramUserMediaEntity.setFileId(picture.getFileId());
        telegramUserMediaEntity.setWidth(picture.getWidth());
        telegramUserMediaEntity.setHeight(picture.getHeight());
        telegramUserMediaEntity.setFileSize(picture.getFileSize());
        telegramUserMediaEntity.setFileType(1);
        telegramUserMediaEntity.setAnon(isAnon);
        telegramUserMediaEntity.setMessage(message);
        telegramUserMediaEntity.setTelegramBotUserEntity(telegramBotUserEntity);
        telegramUserMediaDAO.telegramUserMediaAdd(telegramUserMediaEntity);
    }

    @Override
    public void telegramUserMediaAdd(Video video, Long telegramUserId, boolean isAnon, String message) {
        TelegramBotUserEntity telegramBotUserEntity = telegramUserDAO.telegramUserGetTelegramId(telegramUserId);
        TelegramUserMediaEntity telegramUserMediaEntity = new TelegramUserMediaEntity();
        telegramUserMediaEntity.setFileId(video.getFileId());
        telegramUserMediaEntity.setWidth(video.getWidth());
        telegramUserMediaEntity.setHeight(video.getHeight());
        telegramUserMediaEntity.setFileSize(video.getFileSize());
        telegramUserMediaEntity.setFileType(2);
        telegramUserMediaEntity.setAnon(isAnon);
        telegramUserMediaEntity.setMessage(message);
        telegramUserMediaEntity.setTelegramBotUserEntity(telegramBotUserEntity);
        telegramUserMediaDAO.telegramUserMediaAdd(telegramUserMediaEntity);
    }

    @Override
    public void telegramUserMediaAdd(Document document, Long telegramUserId, boolean isAnon, String message) {
        TelegramBotUserEntity telegramBotUserEntity = telegramUserDAO.telegramUserGetTelegramId(telegramUserId);
        TelegramUserMediaEntity telegramUserMediaEntity = new TelegramUserMediaEntity();
        telegramUserMediaEntity.setFileId(document.getFileId());
        telegramUserMediaEntity.setWidth(0);
        telegramUserMediaEntity.setHeight(0);
        telegramUserMediaEntity.setFileSize(document.getFileSize());
        telegramUserMediaEntity.setFileType(2);
        telegramUserMediaEntity.setAnon(isAnon);
        telegramUserMediaEntity.setMessage(message);
        telegramUserMediaEntity.setTelegramBotUserEntity(telegramBotUserEntity);
        telegramUserMediaDAO.telegramUserMediaAdd(telegramUserMediaEntity);
    }

    @Override
    public TelegramUserMediaEntity telegramUserMediaGet(Long id) {
        return telegramUserMediaDAO.telegramUserMediaGet(id);
    }

    @Override
    public void telegramUserMediaUpdate(TelegramUserMediaEntity telegramUserMediaEntity) {
        telegramUserMediaDAO.telegramUserMediaUpdate(telegramUserMediaEntity);
    }

    @Override
    public List<TelegramUserMediaEntity> telegramUserMediaListForToday() {
        return telegramUserMediaDAO.telegramUserMediaListForToday();
    }

    @Override
    public List<TelegramUserMediaEntity> telegramUserMediaList(int mediaType) {
        return telegramUserMediaDAO.telegramUserMediaList(mediaType);
    }

    @Override
    public void telegramUserMediaTouchAdd(Long telegramUserId, Long fileId, String touchType) {
        TelegramUserMediaEntity telegramUserMediaEntity = telegramUserMediaDAO.telegramUserMediaGet(fileId);
        TelegramMediaStatisticEntity telegramMediaStatisticEntity = new TelegramMediaStatisticEntity();
        telegramMediaStatisticEntity.setTelegramUserId(telegramUserId);
        telegramMediaStatisticEntity.setTelegramUserMediaEntity(telegramUserMediaEntity);
        telegramMediaStatisticEntity.setTouchType(touchType);
        telegramUserMediaDAO.mediaTouchAdd(telegramMediaStatisticEntity);
    }

    @Override
    public Optional<TelegramMediaStatisticEntity> findHeartUserTouch(Long telegramUserId, Long fileId) {
        return telegramUserMediaDAO.findTouchTypeMedia(telegramUserId, fileId, "HEART");
    }

    @Override
    public Optional<TelegramMediaStatisticEntity> findLikeUserTouch(Long telegramUserId, Long fileId) {
        return telegramUserMediaDAO.findTouchTypeMedia(telegramUserId, fileId, "LIKE");
    }

    @Override
    public Optional<TelegramMediaStatisticEntity> findDislikeUserTouch(Long telegramUserId, Long fileId) {
        return telegramUserMediaDAO.findTouchTypeMedia(telegramUserId, fileId, "DISLIKE");
    }

    @Override
    public long countUserTouch(Long fileId, String touchType) {
        return telegramUserMediaDAO.countUserTouch(fileId, touchType);
    }

    @Override
    public List<TopRatingUsersDTO> topActiveUsersGet() {
        return telegramUserMediaDAO.topActiveUsersGet();
    }

    @Override
    public List<TopRatingUsersDTO> topUsersGet() {
        return telegramUserMediaDAO.topUsersGet();
    }

    @Override
    public List<OwnerStatisticDTO> getUsersActivityStatistic(Long telegramUserId) {
        return telegramUserMediaDAO.getUsersActivityStatistic(telegramUserId);
    }

    @Override
    public List<OwnerStatisticDTO> getSelfActivityStatistic(Long telegramUserId){
        return telegramUserMediaDAO.getSelfActivityStatistic(telegramUserId);
    }
}
