package com.botmasterzzz.telegram.service;

import com.botmasterzzz.bot.api.impl.objects.replykeyboard.InlineKeyboardMarkup;
import com.botmasterzzz.telegram.entity.LotsEntity;

import java.util.List;

public interface ReklamMessageService {

    InlineKeyboardMarkup getInlineKeyboardForBalAcc();

    InlineKeyboardMarkup getInlineKeyboardForWantMoney();

    InlineKeyboardMarkup getInlineKeyboardForWantSubs();

    List<LotsEntity> getLotsForCustomer(int offset, int limit);

    double getAccountTotal(long id);

    double getAccountTotalByUserId(long id);

    boolean accountExists(Long telegramUserId);

    void accountSave(Long telegramUserId);
    }
