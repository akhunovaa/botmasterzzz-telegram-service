package com.botmasterzzz.telegram.controller.telegram.getparts;

import com.botmasterzzz.bot.api.impl.objects.replykeyboard.InlineKeyboardMarkup;
import com.botmasterzzz.telegram.entity.GetPartsEntity;

public interface GetPartsMessageService {

    InlineKeyboardMarkup getInlineKeyboardForCatalog();

    InlineKeyboardMarkup getPartsPhotoButton(long partId);

    InlineKeyboardMarkup getInlineKeyboardForKamaz();

    InlineKeyboardMarkup getInlineKeyboardForMovelex();

    InlineKeyboardMarkup getInlineKeyboardForNefaz();

    InlineKeyboardMarkup getInlineKeyboardForBelzan();

    InlineKeyboardMarkup getInlineKeyboardForMercedes();

    GetPartsEntity searchPart(String text);

}
