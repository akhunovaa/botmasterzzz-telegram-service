package com.botmasterzzz.telegram.service.impl;

import com.botmasterzzz.bot.api.impl.objects.replykeyboard.InlineKeyboardMarkup;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.buttons.InlineKeyboardButton;
import com.botmasterzzz.telegram.dao.ReklamDAO;
import com.botmasterzzz.telegram.dto.CallBackData;
import com.botmasterzzz.telegram.entity.LotsEntity;
import com.botmasterzzz.telegram.service.ReklamMessageService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReklamMessageServiceImpl implements ReklamMessageService {

    @Autowired
    private Gson gson;

    @Autowired
    private ReklamDAO rdao;

    @Override
    public InlineKeyboardMarkup getInlineKeyboardForBalAcc() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();

        List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = new ArrayList<>();

        InlineKeyboardButton registerInlineButton = new InlineKeyboardButton();
        registerInlineButton.setText("Пополнить");
        registerInlineButton.setCallbackData(gson.toJson(new CallBackData("balacc_income")));
        inlineKeyboardButtonsFirstRow.add(registerInlineButton);

        InlineKeyboardButton netMerchButton = new InlineKeyboardButton();
        netMerchButton.setText("Вывести");
        netMerchButton.setCallbackData(gson.toJson(new CallBackData("balacc_outcome")));
        inlineKeyboardButtonsFirstRow.add(netMerchButton);

        inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);

        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);
        return inlineKeyboardMarkup;
    }

    @Override
    public InlineKeyboardMarkup getInlineKeyboardForWantMoney() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();

        List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = new ArrayList<>();

        InlineKeyboardButton registerInlineButton = new InlineKeyboardButton();
        registerInlineButton.setText("Список заявок");
        registerInlineButton.setCallbackData(gson.toJson(new CallBackData("lots_list")));
        inlineKeyboardButtonsFirstRow.add(registerInlineButton);

        InlineKeyboardButton netMerchButton = new InlineKeyboardButton();
        netMerchButton.setText("Принятые заявки");
        netMerchButton.setCallbackData(gson.toJson(new CallBackData("lots_inwork")));
        inlineKeyboardButtonsFirstRow.add(netMerchButton);

        inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);

        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);
        return inlineKeyboardMarkup;
    }


    @Override
    public InlineKeyboardMarkup getInlineKeyboardForWantSubs() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();

        List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = new ArrayList<>();

        InlineKeyboardButton registerInlineButton = new InlineKeyboardButton();
        registerInlineButton.setText("Активные заявки");
        registerInlineButton.setCallbackData(gson.toJson(new CallBackData("lots_active")));
        inlineKeyboardButtonsFirstRow.add(registerInlineButton);

        InlineKeyboardButton netMerchButton = new InlineKeyboardButton();
        netMerchButton.setText("Добавить заявку");
        netMerchButton.setCallbackData(gson.toJson(new CallBackData("lots_adding")));
        inlineKeyboardButtonsFirstRow.add(netMerchButton);

        inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);

        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);
        return inlineKeyboardMarkup;
    }

    @Override
    public List<LotsEntity> getLotsForCustomer(int offset, int limit) {
        return rdao.getLotsForCustomer(offset, limit);
    }

    @Override
    public double getAccountTotal(long id) {
        return rdao.getAccountTotal(id);
    }

    @Override
    public double getAccountTotalByUserId(long id) {
        return rdao.getAccountByUserId(id).getTotal();
//        return rdao
    }
}
