package com.botmasterzzz.telegram.dao;

import com.botmasterzzz.telegram.entity.TelegramMediaStatisticEntity;
import com.botmasterzzz.telegram.entity.TelegramUserMediaEntity;

import java.util.List;
import java.util.Optional;

public interface TelegramUserMediaDAO {

    Long telegramUserMediaAdd(TelegramUserMediaEntity telegramUserMediaEntity);

    TelegramUserMediaEntity telegramUserMediaGet(Long id);

    List<TelegramUserMediaEntity> telegramUserMediaList(int mediaType);

    Optional<TelegramMediaStatisticEntity> findTouchTypeMedia(long telegramUserId, Long mediaFileId, String touchType);

    long countUserTouch(long telegramUserId, Long mediaFileId, String touchType);

    void mediaTouchAdd(TelegramMediaStatisticEntity telegramMediaStatisticEntity);

}
