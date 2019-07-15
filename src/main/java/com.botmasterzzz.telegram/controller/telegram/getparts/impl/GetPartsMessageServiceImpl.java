package com.botmasterzzz.telegram.controller.telegram.getparts.impl;

import com.botmasterzzz.bot.api.impl.objects.replykeyboard.InlineKeyboardMarkup;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.buttons.InlineKeyboardButton;
import com.botmasterzzz.telegram.controller.telegram.getparts.GetPartsMessageService;
import com.botmasterzzz.telegram.dto.CallBackData;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetPartsMessageServiceImpl implements GetPartsMessageService {

    @Autowired
    private Gson gson;

    @Override
    public InlineKeyboardMarkup getInlineKeyboardForCatalog() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();

        List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsSecondRow = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsThirdRow = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsFourthRow = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsFifthRow = new ArrayList<>();

        InlineKeyboardButton firstInlineButton = new InlineKeyboardButton();
        firstInlineButton.setText("KAMAZ");
        firstInlineButton.setCallbackData(gson.toJson(new CallBackData("kamaz")));
        inlineKeyboardButtonsFirstRow.add(firstInlineButton);

        InlineKeyboardButton secondButton = new InlineKeyboardButton();
        secondButton.setText("MOVELEX");
        secondButton.setCallbackData(gson.toJson(new CallBackData("movelex")));
        inlineKeyboardButtonsSecondRow.add(secondButton);

        InlineKeyboardButton thirdButton = new InlineKeyboardButton();
        thirdButton.setText("НЕФАЗ");
        thirdButton.setCallbackData(gson.toJson(new CallBackData("nefaz")));
        inlineKeyboardButtonsThirdRow.add(thirdButton);


        InlineKeyboardButton fourthButton = new InlineKeyboardButton();
        fourthButton.setText("БЕЛЗАН");
        fourthButton.setCallbackData(gson.toJson(new CallBackData("belzan")));
        inlineKeyboardButtonsFourthRow.add(fourthButton);


        InlineKeyboardButton fifthButton = new InlineKeyboardButton();
        fifthButton.setText("MERCEDES");
        fifthButton.setCallbackData(gson.toJson(new CallBackData("mercedes")));
        inlineKeyboardButtonsFifthRow.add(fifthButton);

        inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);
        inlineKeyboardButtons.add(inlineKeyboardButtonsSecondRow);
        inlineKeyboardButtons.add(inlineKeyboardButtonsThirdRow);
        inlineKeyboardButtons.add(inlineKeyboardButtonsFourthRow);
        inlineKeyboardButtons.add(inlineKeyboardButtonsFifthRow);

        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);
        return inlineKeyboardMarkup;
    }
}
