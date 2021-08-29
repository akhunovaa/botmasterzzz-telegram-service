package com.botmasterzzz.telegram.service;

import com.botmasterzzz.telegram.dto.TelegramDTO;

import java.util.List;

public interface TelegramInstanceService {

    TelegramDTO start(TelegramDTO telegramDTO);

    TelegramDTO stop(TelegramDTO telegramDTO);

    TelegramDTO getStatus(TelegramDTO telegramDTO);

    List<TelegramDTO> getStartedTelegramInstancesList(Long userId);

}
