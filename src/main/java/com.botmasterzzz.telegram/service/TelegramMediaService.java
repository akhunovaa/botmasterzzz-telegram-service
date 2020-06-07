package com.botmasterzzz.telegram.service;

import com.botmasterzzz.bot.api.impl.objects.Document;
import com.botmasterzzz.bot.api.impl.objects.PhotoSize;
import com.botmasterzzz.bot.api.impl.objects.Video;
import com.botmasterzzz.telegram.entity.TelegramMediaStatisticEntity;
import com.botmasterzzz.telegram.entity.TelegramUserMediaEntity;

import java.util.List;
import java.util.Optional;

public interface TelegramMediaService {

    void telegramUserMediaAdd(List<PhotoSize> pictures, Long telegramUserId);

    void telegramUserMediaAdd(Video video, Long telegramUserId);

    void telegramUserMediaAdd(Document document, Long telegramUserId);

    TelegramUserMediaEntity telegramUserMediaGet(Long id);

    List<TelegramUserMediaEntity> telegramUserMediaList(int mediaType);

    void telegramUserMediaTouchAdd(Long telegramUserId, Long fileId, String touchType);

    Optional<TelegramMediaStatisticEntity> findHeartUserTouch(Long telegramUserId, Long fileId);

    Optional<TelegramMediaStatisticEntity> findLikeUserTouch(Long telegramUserId, Long fileId);

    Optional<TelegramMediaStatisticEntity> findDislikeUserTouch(Long telegramUserId, Long fileId);

    long countUserTouch(Long telegramUserId, Long fileId, String touchType);

}
