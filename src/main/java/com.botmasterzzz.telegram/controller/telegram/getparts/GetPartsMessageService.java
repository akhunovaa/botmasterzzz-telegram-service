package com.botmasterzzz.telegram.controller.telegram.getparts;

import com.botmasterzzz.bot.api.impl.objects.replykeyboard.InlineKeyboardMarkup;

public interface GetPartsMessageService {

    InlineKeyboardMarkup getInlineKeyboardForCatalog();

    InlineKeyboardMarkup getInlineKeyboardForKamaz();

    InlineKeyboardMarkup getInlineKeyboardForMovelex();

    InlineKeyboardMarkup getInlineKeyboardForNefaz();

    InlineKeyboardMarkup getInlineKeyboardForBelzan();

    InlineKeyboardMarkup getInlineKeyboardForMercedes();

}