package com.botmasterzzz.telegram.controller.telegram.gkh.impl;

import com.botmasterzzz.bot.api.impl.objects.replykeyboard.InlineKeyboardMarkup;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.buttons.InlineKeyboardButton;
import com.botmasterzzz.telegram.controller.telegram.gkh.GkhMessageService;
import com.botmasterzzz.telegram.dto.CallBackData;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GkhMessageServiceImpl implements GkhMessageService {

    @Autowired
    private Gson gson;

    @Override
    public InlineKeyboardMarkup getInlineKeyboardForGate() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();

        List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = new ArrayList<>();

        InlineKeyboardButton registerInlineButton = new InlineKeyboardButton();
        registerInlineButton.setText("\uD83D\uDD13Открыть");
        registerInlineButton.setCallbackData(gson.toJson(new CallBackData("gate_open")));
        inlineKeyboardButtonsFirstRow.add(registerInlineButton);

        InlineKeyboardButton netMerchButton = new InlineKeyboardButton();
        netMerchButton.setText("\uD83D\uDD12Закрыть");
        netMerchButton.setCallbackData(gson.toJson(new CallBackData("gate_close")));
        inlineKeyboardButtonsFirstRow.add(netMerchButton);

        inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);

        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);
        return inlineKeyboardMarkup;
    }
}
