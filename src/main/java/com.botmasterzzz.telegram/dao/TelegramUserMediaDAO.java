package com.botmasterzzz.telegram.dao;

import com.botmasterzzz.telegram.dto.OwnerStatisticDTO;
import com.botmasterzzz.telegram.dto.TopRatingUsersDTO;
import com.botmasterzzz.telegram.entity.TelegramMediaStatisticEntity;
import com.botmasterzzz.telegram.entity.TelegramUserMediaEntity;

import java.util.List;
import java.util.Optional;

public interface TelegramUserMediaDAO {

    Long telegramUserMediaAdd(TelegramUserMediaEntity telegramUserMediaEntity);

    TelegramUserMediaEntity telegramUserMediaGet(Long id);

    void telegramUserMediaUpdate(TelegramUserMediaEntity telegramUserMediaEntity);

    List<TelegramUserMediaEntity> telegramUserMediaList(int mediaType);

    List<TelegramUserMediaEntity> telegramUserMediaListForToday();

    Optional<TelegramMediaStatisticEntity> findTouchTypeMedia(long telegramUserId, Long mediaFileId, String touchType);

    long countUserTouch(Long mediaFileId, String touchType);

    void mediaTouchAdd(TelegramMediaStatisticEntity telegramMediaStatisticEntity);

    List<TopRatingUsersDTO> topActiveUsersGet();

    List<OwnerStatisticDTO> getUsersActivityStatistic(Long telegramUserId);

    List<OwnerStatisticDTO> getSelfActivityStatistic(Long telegramUserId);

}
