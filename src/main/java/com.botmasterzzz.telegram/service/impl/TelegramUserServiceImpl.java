package com.botmasterzzz.telegram.service.impl;

import com.botmasterzzz.telegram.dao.TelegramUserDAO;
import com.botmasterzzz.telegram.entity.TelegramBotUserEntity;
import com.botmasterzzz.telegram.service.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TelegramUserServiceImpl implements TelegramUserService {

    private final TelegramUserDAO telegramUserDAO;

    @Autowired
    public TelegramUserServiceImpl(TelegramUserDAO telegramUserDAO) {
        this.telegramUserDAO = telegramUserDAO;
    }


    @Override
    public TelegramBotUserEntity getTelegramUser(Long telegramUserId) {
        return telegramUserDAO.telegramUserGetTelegramId(telegramUserId);
    }

    @Override
    public List<TelegramBotUserEntity> getTelegramUserList() {
        return telegramUserDAO.getTelegramUserList();
    }
}
