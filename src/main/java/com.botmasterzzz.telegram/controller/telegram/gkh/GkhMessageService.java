package com.botmasterzzz.telegram.controller.telegram.gkh;

import com.botmasterzzz.bot.api.impl.objects.replykeyboard.InlineKeyboardMarkup;

public interface GkhMessageService {

    InlineKeyboardMarkup getInlineKeyboardForGate();

    InlineKeyboardMarkup getCloseInlineKeyboardForGate();

    InlineKeyboardMarkup getOpenInlineKeyboardForGate();

    InlineKeyboardMarkup getInlineKeyboardForGasAccount();

    InlineKeyboardMarkup getGetGasInlineKeyboardForAccount();

    InlineKeyboardMarkup getSendGasInlineKeyboardForAccount();

    InlineKeyboardMarkup getInlineKeyboardForEnergyAccount();

    InlineKeyboardMarkup getInlineKeyboardForWaterAccount();

    InlineKeyboardMarkup getGetHotWaterInlineKeyboardForAccount();

    InlineKeyboardMarkup getSendHotWaterInlineKeyboardForAccount();

    InlineKeyboardMarkup getGetColdWaterInlineKeyboardForAccount();

    InlineKeyboardMarkup getSendColdWaterInlineKeyboardForAccount();

    InlineKeyboardMarkup getGetEnergyInlineKeyboardForAccount();

    InlineKeyboardMarkup getSendEnergyInlineKeyboardForAccount();

    InlineKeyboardMarkup getInlineKeyboardForSalesTickets();

    InlineKeyboardMarkup getInlineKeyboardForLostTickets();

    InlineKeyboardMarkup getInlineKeyboardForMastersTickets();

    InlineKeyboardMarkup getInlineKeyboardForOtherTickets();


}
