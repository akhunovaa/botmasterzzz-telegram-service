package com.botmasterzzz.telegram.controller.telegram.gkh;

import com.botmasterzzz.bot.api.impl.objects.replykeyboard.InlineKeyboardMarkup;

public interface GkhMessageService {

    InlineKeyboardMarkup getInlineKeyboardForGate();

    InlineKeyboardMarkup getCloseInlineKeyboardForGate();

    InlineKeyboardMarkup getOpenInlineKeyboardForGate();

    InlineKeyboardMarkup getInlineKeyboardForAccount();

    InlineKeyboardMarkup getGetGasInlineKeyboardForAccount();

    InlineKeyboardMarkup getSendGasInlineKeyboardForAccount();

}
