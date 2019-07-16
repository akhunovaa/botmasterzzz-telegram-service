package com.botmasterzzz.telegram.dao.impl;

import com.botmasterzzz.bot.api.impl.objects.Message;
import com.botmasterzzz.bot.api.impl.objects.User;
import com.botmasterzzz.telegram.dao.TelegramStatisticDAO;
import com.botmasterzzz.telegram.dao.TelegramUserDAO;
import com.botmasterzzz.telegram.entity.TelegramBotUsageStatisticEntity;
import com.botmasterzzz.telegram.entity.TelegramBotUserEntity;
import com.botmasterzzz.telegram.entity.TelegramInstanceEntity;
import com.botmasterzzz.telegram.service.TelegramBotStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TelegramBotStatisticServiceImpl implements TelegramBotStatisticService {

    @Autowired
    private TelegramStatisticDAO telegramStatisticDAO;

    @Autowired
    private TelegramUserDAO telegramUserDAO;

    @Override
    public boolean telegramUserExists(long telegramUserId) {
        return telegramUserDAO.exists(TelegramBotUserEntity.class, telegramUserId);
    }

    @Override
    public void telegramUserAdd(User user) {
        TelegramBotUserEntity telegramBotUserEntity = new TelegramBotUserEntity();
        telegramBotUserEntity.setBlocked(false);
        telegramBotUserEntity.setBot(false);
        telegramBotUserEntity.setFirstName(user.getFirstName());
        telegramBotUserEntity.setLastName(user.getLastName());
        telegramBotUserEntity.setUsername(user.getUserName());
        telegramBotUserEntity.setLanguageCode(user.getLanguageCode());
        telegramBotUserEntity.setTelegramId(Long.valueOf(user.getId()));
        telegramBotUserEntity.setNote("TelegramBotStatisticService added");
        telegramUserDAO.telegramUserAdd(telegramBotUserEntity);
    }

    @Override
    public List<TelegramBotUserEntity> getTelegramUserList() {
        return telegramUserDAO.getTelegramUserList();
    }

    @Override
    public void telegramStatisticAdd(Message message, Long botInstance) {
        TelegramBotUsageStatisticEntity telegramBotUsageStatisticEntity = new TelegramBotUsageStatisticEntity();
        telegramBotUsageStatisticEntity.setMessage(message.getText());
        telegramBotUsageStatisticEntity.setChatId(message.getChatId());
        telegramBotUsageStatisticEntity.setMessageId(message.getMessageId());
        telegramBotUsageStatisticEntity.setMessengerId(1);
        telegramBotUsageStatisticEntity.setNote("TelegramBotStatisticService added");

        TelegramInstanceEntity telegramInstanceEntity = new TelegramInstanceEntity();
        telegramInstanceEntity.setId(botInstance);
        telegramBotUsageStatisticEntity.setTelegramInstanceEntity(telegramInstanceEntity);

        TelegramBotUserEntity telegramBotUserEntity = telegramUserDAO.telegramUserGet(message.getFrom().getId());
        telegramBotUsageStatisticEntity.setTelegramBotUserEntity(telegramBotUserEntity);

        telegramStatisticDAO.telegramStatisticAdd(telegramBotUsageStatisticEntity);

    }

    @Override
    public TelegramBotUsageStatisticEntity telegramStatisticGet(long id) {
        return telegramStatisticDAO.telegramStatisticGet(id);
    }

    @Override
    public List<TelegramBotUsageStatisticEntity> getTelegramStatisticList() {
        return telegramStatisticDAO.getTelegramStatisticList();
    }
}
