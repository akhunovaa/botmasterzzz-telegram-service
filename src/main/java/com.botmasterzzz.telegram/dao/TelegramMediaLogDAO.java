package com.botmasterzzz.telegram.dao;

import com.botmasterzzz.telegram.entity.TelegramMediaLogEntity;

public interface TelegramMediaLogDAO {

    void mediaLogAdd(TelegramMediaLogEntity telegramMediaLogEntity);

    long getCountOfMediaLog(Long mediaId);

}
