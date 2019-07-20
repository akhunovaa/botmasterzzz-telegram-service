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

    @Override
    public InlineKeyboardMarkup getCloseInlineKeyboardForGate() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();

        List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = new ArrayList<>();

        InlineKeyboardButton netMerchButton = new InlineKeyboardButton();
        netMerchButton.setText("\uD83D\uDD12Закрыть");
        netMerchButton.setCallbackData(gson.toJson(new CallBackData("gate_close")));
        inlineKeyboardButtonsFirstRow.add(netMerchButton);

        inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);

        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);
        return inlineKeyboardMarkup;
    }

    @Override
    public InlineKeyboardMarkup getOpenInlineKeyboardForGate() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();

        List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = new ArrayList<>();

        InlineKeyboardButton registerInlineButton = new InlineKeyboardButton();
        registerInlineButton.setText("\uD83D\uDD13Открыть");
        registerInlineButton.setCallbackData(gson.toJson(new CallBackData("gate_open")));
        inlineKeyboardButtonsFirstRow.add(registerInlineButton);

        inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);

        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);
        return inlineKeyboardMarkup;
    }

    @Override
    public InlineKeyboardMarkup getInlineKeyboardForGasAccount() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();

        List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsSecondRow = new ArrayList<>();

        InlineKeyboardButton registerInlineButton = new InlineKeyboardButton();
        registerInlineButton.setText("Получить показания");
        registerInlineButton.setCallbackData(gson.toJson(new CallBackData("get_gas")));
        inlineKeyboardButtonsFirstRow.add(registerInlineButton);

        InlineKeyboardButton netMerchButton = new InlineKeyboardButton();
        netMerchButton.setText("Отправить показания");
        netMerchButton.setCallbackData(gson.toJson(new CallBackData("send_gas")));

        inlineKeyboardButtonsSecondRow.add(netMerchButton);

        inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);
        inlineKeyboardButtons.add(inlineKeyboardButtonsSecondRow);

        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);
        return inlineKeyboardMarkup;
    }

    @Override
    public InlineKeyboardMarkup getInlineKeyboardForEnergyAccount() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();

        List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsSecondRow = new ArrayList<>();

        InlineKeyboardButton registerInlineButton = new InlineKeyboardButton();
        registerInlineButton.setText("Получить показания электроэнергии");
        registerInlineButton.setCallbackData(gson.toJson(new CallBackData("get_energy")));
        inlineKeyboardButtonsFirstRow.add(registerInlineButton);

        InlineKeyboardButton netMerchButton = new InlineKeyboardButton();
        netMerchButton.setText("Отправить показания электроэнергии");
        netMerchButton.setCallbackData(gson.toJson(new CallBackData("send_energy")));
        inlineKeyboardButtonsSecondRow.add(netMerchButton);

        inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);
        inlineKeyboardButtons.add(inlineKeyboardButtonsSecondRow);

        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);
        return inlineKeyboardMarkup;
    }

    @Override
    public InlineKeyboardMarkup getGetGasInlineKeyboardForAccount() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();

        List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = new ArrayList<>();

        InlineKeyboardButton registerInlineButton = new InlineKeyboardButton();
        registerInlineButton.setText("Получить показания счетчика газа");
        registerInlineButton.setCallbackData(gson.toJson(new CallBackData("get_gas")));
        inlineKeyboardButtonsFirstRow.add(registerInlineButton);

        inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);

        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);
        return inlineKeyboardMarkup;
    }

    @Override
    public InlineKeyboardMarkup getSendGasInlineKeyboardForAccount() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();

        List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = new ArrayList<>();

        InlineKeyboardButton registerInlineButton = new InlineKeyboardButton();
        registerInlineButton.setText("Отправить показания");
        registerInlineButton.setCallbackData(gson.toJson(new CallBackData("send_gas")));
        inlineKeyboardButtonsFirstRow.add(registerInlineButton);

        inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);

        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);
        return inlineKeyboardMarkup;
    }

    @Override
    public InlineKeyboardMarkup getGetEnergyInlineKeyboardForAccount() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();

        List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = new ArrayList<>();

        InlineKeyboardButton registerInlineButton = new InlineKeyboardButton();
        registerInlineButton.setText("Получить показания по электроэнергии");
        registerInlineButton.setCallbackData(gson.toJson(new CallBackData("get_energy")));
        inlineKeyboardButtonsFirstRow.add(registerInlineButton);

        inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);

        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);
        return inlineKeyboardMarkup;
    }

    @Override
    public InlineKeyboardMarkup getSendEnergyInlineKeyboardForAccount() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();

        List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = new ArrayList<>();

        InlineKeyboardButton registerInlineButton = new InlineKeyboardButton();
        registerInlineButton.setText("Отправить показания по электроэнергии");
        registerInlineButton.setCallbackData(gson.toJson(new CallBackData("send_energy")));
        inlineKeyboardButtonsFirstRow.add(registerInlineButton);

        inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);

        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);
        return inlineKeyboardMarkup;

    }

    @Override
    public InlineKeyboardMarkup getInlineKeyboardForWaterAccount() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();

        List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsSecondRow = new ArrayList<>();

        InlineKeyboardButton b1 = new InlineKeyboardButton();
        b1.setText("Получить ГВС");
        b1.setCallbackData(gson.toJson(new CallBackData("get_hot_water")));
        inlineKeyboardButtonsFirstRow.add(b1);

        InlineKeyboardButton b2 = new InlineKeyboardButton();
        b2.setText("Отправить ГВС");
        b2.setCallbackData(gson.toJson(new CallBackData("send_hot_water")));
        inlineKeyboardButtonsFirstRow.add(b2);

        InlineKeyboardButton b3 = new InlineKeyboardButton();
        b3.setText("Получить ХВС");
        b3.setCallbackData(gson.toJson(new CallBackData("get_cold_water")));

        inlineKeyboardButtonsSecondRow.add(b3);

        InlineKeyboardButton b4 = new InlineKeyboardButton();
        b4.setText("Отправить ХВС");
        b4.setCallbackData(gson.toJson(new CallBackData("send_cold_water")));

        inlineKeyboardButtonsSecondRow.add(b4);

        inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);
        inlineKeyboardButtons.add(inlineKeyboardButtonsSecondRow);

        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);
        return inlineKeyboardMarkup;
    }

    @Override
    public InlineKeyboardMarkup getGetHotWaterInlineKeyboardForAccount() {
        return null;


        //  🔐Личный кабинет
    }

    @Override
    public InlineKeyboardMarkup getSendHotWaterInlineKeyboardForAccount() {
        return null;
    }

    @Override
    public InlineKeyboardMarkup getGetColdWaterInlineKeyboardForAccount() {
        return null;
    }

    @Override
    public InlineKeyboardMarkup getSendColdWaterInlineKeyboardForAccount() {
        return null;
    }
}
