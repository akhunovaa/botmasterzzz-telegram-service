package com.botmasterzzz.telegram.service;

import com.botmasterzzz.bot.api.impl.objects.Document;
import com.botmasterzzz.bot.api.impl.objects.PhotoSize;
import com.botmasterzzz.bot.api.impl.objects.Video;
import com.botmasterzzz.telegram.dto.OwnerStatisticDTO;
import com.botmasterzzz.telegram.dto.TopRatingUsersDTO;
import com.botmasterzzz.telegram.entity.TelegramBotUserEntity;
import com.botmasterzzz.telegram.entity.TelegramMediaStatisticEntity;
import com.botmasterzzz.telegram.entity.TelegramUserMediaEntity;

import java.util.List;
import java.util.Optional;

public interface TelegramMediaService {

    void telegramUserMediaAdd(List<PhotoSize> pictures, Long telegramUserId, boolean isAnon, String message);

    void portfolioMediaAdd(List<PhotoSize> pictures, Long telegramUserId, boolean isAnon, String message);

    void telegramUserMediaAdd(Video video, Long telegramUserId, boolean isAnon, String message);

    void telegramUserMediaAdd(Document document, Long telegramUserId, boolean isAnon, String message);

    void telegramUserMediaAdd(String path, Long telegramUserId, boolean isAnon, String message);

    TelegramUserMediaEntity telegramUserMediaGet(Long id);

    void telegramUserMediaUpdate(String filePath, String fileId);

    void telegramUserMediaUpdate(TelegramUserMediaEntity telegramUserMediaEntity);

    List<TelegramUserMediaEntity> telegramUserMediaListForToday();

    List<TelegramUserMediaEntity> telegramUserMediaListForYesterday();

    List<TelegramUserMediaEntity> telegramUserPersonalMediaList(TelegramBotUserEntity requestedTelegramUser);

    List<TelegramUserMediaEntity> portfolioPersonalMediaList(TelegramBotUserEntity requestedTelegramUser);

    List<TelegramUserMediaEntity> telegramUserMediaList(int mediaType);

    List<TelegramUserMediaEntity> portfolioMediaList(int mediaType);

    void telegramUserMediaTouchAdd(Long telegramUserId, Long fileId, String touchType);

    Optional<TelegramMediaStatisticEntity> findHeartUserTouch(Long telegramUserId, Long fileId);

    Optional<TelegramMediaStatisticEntity> findLikeUserTouch(Long telegramUserId, Long fileId);

    Optional<TelegramMediaStatisticEntity> findDislikeUserTouch(Long telegramUserId, Long fileId);

    long countUserTouch(Long fileId, String touchType);

    List<TopRatingUsersDTO> topActiveUsersGet();

    List<TopRatingUsersDTO> topUsersGet();

    List<OwnerStatisticDTO> getUsersActivityStatistic(Long telegramUserId);

    List<OwnerStatisticDTO> getSelfActivityStatistic(Long telegramUserId);

}
