package com.botmasterzzz.telegram.controller.telegram.getparts;

import com.botmasterzzz.bot.api.impl.objects.replykeyboard.InlineKeyboardMarkup;
import com.botmasterzzz.telegram.entity.GetPartsEntity;

import java.util.List;

public interface GetPartsMessageService {

    InlineKeyboardMarkup getInlineKeyboardForCatalog();

    InlineKeyboardMarkup getPartsPhotoButton(long partId, int offset, int limit);

    InlineKeyboardMarkup getInlineKeyboardForKamaz();

    InlineKeyboardMarkup getInlineKeyboardForMovelex();

    InlineKeyboardMarkup getInlineKeyboardForNefaz();

    InlineKeyboardMarkup getInlineKeyboardForBelzan();

    InlineKeyboardMarkup getInlineKeyboardForMercedes();

    List<GetPartsEntity> searchPart(String text);

}
