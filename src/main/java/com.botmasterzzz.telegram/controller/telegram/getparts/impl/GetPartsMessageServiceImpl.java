package com.botmasterzzz.telegram.controller.telegram.getparts.impl;

import com.botmasterzzz.bot.api.impl.objects.replykeyboard.InlineKeyboardMarkup;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.buttons.InlineKeyboardButton;
import com.botmasterzzz.telegram.controller.telegram.getparts.GetPartsMessageService;
import com.botmasterzzz.telegram.dao.GetPartsDAO;
import com.botmasterzzz.telegram.dto.CallBackData;
import com.botmasterzzz.telegram.entity.GetPartsEntity;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetPartsMessageServiceImpl implements GetPartsMessageService {

    @Autowired
    private Gson gson;

    @Autowired
    private GetPartsDAO getPartsDAO;

    @Override
    public InlineKeyboardMarkup getInlineKeyboardForCatalog() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();

        List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsSecondRow = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsThirdRow = new ArrayList<>();

        InlineKeyboardButton firstInlineButton = new InlineKeyboardButton();
        firstInlineButton.setText("KAMAZ");
        firstInlineButton.setCallbackData(gson.toJson(new CallBackData("kamaz")));
        inlineKeyboardButtonsFirstRow.add(firstInlineButton);

        InlineKeyboardButton secondButton = new InlineKeyboardButton();
        secondButton.setText("MOVELEX");
        secondButton.setCallbackData(gson.toJson(new CallBackData("movelex")));
        inlineKeyboardButtonsFirstRow.add(secondButton);

        InlineKeyboardButton thirdButton = new InlineKeyboardButton();
        thirdButton.setText("НЕФАЗ");
        thirdButton.setCallbackData(gson.toJson(new CallBackData("nefaz")));
        inlineKeyboardButtonsSecondRow.add(thirdButton);

        InlineKeyboardButton fourthButton = new InlineKeyboardButton();
        fourthButton.setText("БЕЛЗАН");
        fourthButton.setCallbackData(gson.toJson(new CallBackData("belzan")));
        inlineKeyboardButtonsSecondRow.add(fourthButton);

        InlineKeyboardButton fifthButton = new InlineKeyboardButton();
        fifthButton.setText("MERCEDES");
        fifthButton.setCallbackData(gson.toJson(new CallBackData("mercedes")));
        inlineKeyboardButtonsThirdRow.add(fifthButton);

        inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);
        inlineKeyboardButtons.add(inlineKeyboardButtonsSecondRow);
        inlineKeyboardButtons.add(inlineKeyboardButtonsThirdRow);

        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);
        return inlineKeyboardMarkup;
    }

    @Override
    public InlineKeyboardMarkup getInlineKeyboardForKamaz() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();

        List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsSecondRow = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsThirdRow = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsReturnRow = new ArrayList<>();

        InlineKeyboardButton firstInlineButton = new InlineKeyboardButton();
        firstInlineButton.setText("Механизмы управления/Тормоза");
        firstInlineButton.setCallbackData(gson.toJson(new CallBackData("kamaz-tormoza")));
        inlineKeyboardButtonsFirstRow.add(firstInlineButton);

        InlineKeyboardButton secondButton = new InlineKeyboardButton();
        secondButton.setText("Двигатель");
        secondButton.setCallbackData(gson.toJson(new CallBackData("kamaz-dvigatel")));
        inlineKeyboardButtonsSecondRow.add(secondButton);

        InlineKeyboardButton thirdButton = new InlineKeyboardButton();
        thirdButton.setText("Ходовая часть/Подвеска автомобиля");
        thirdButton.setCallbackData(gson.toJson(new CallBackData("kamaz-podveska")));
        inlineKeyboardButtonsThirdRow.add(thirdButton);

        InlineKeyboardButton returnButton = new InlineKeyboardButton();
        returnButton.setText("⏏️Вернуться");
        returnButton.setCallbackData(gson.toJson(new CallBackData("return-to-catalog")));
        inlineKeyboardButtonsReturnRow.add(returnButton);

        inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);
        inlineKeyboardButtons.add(inlineKeyboardButtonsSecondRow);
        inlineKeyboardButtons.add(inlineKeyboardButtonsThirdRow);
        inlineKeyboardButtons.add(inlineKeyboardButtonsReturnRow);

        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);
        return inlineKeyboardMarkup;
    }

    @Override
    public InlineKeyboardMarkup getInlineKeyboardForMovelex() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();

        List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsReturnRow = new ArrayList<>();

        InlineKeyboardButton firstInlineButton = new InlineKeyboardButton();
        firstInlineButton.setText("Двигатель");
        firstInlineButton.setCallbackData(gson.toJson(new CallBackData("movelex-dvigatel")));
        inlineKeyboardButtonsFirstRow.add(firstInlineButton);


        InlineKeyboardButton returnButton = new InlineKeyboardButton();
        returnButton.setText("⏏️Вернуться");
        returnButton.setCallbackData(gson.toJson(new CallBackData("return-to-catalog")));
        inlineKeyboardButtonsReturnRow.add(returnButton);

        inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);
        inlineKeyboardButtons.add(inlineKeyboardButtonsReturnRow);

        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);
        return inlineKeyboardMarkup;
    }

    @Override
    public InlineKeyboardMarkup getInlineKeyboardForNefaz() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();

        List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsReturnRow = new ArrayList<>();

        InlineKeyboardButton firstInlineButton = new InlineKeyboardButton();
        firstInlineButton.setText("Кузов/Платформа");
        firstInlineButton.setCallbackData(gson.toJson(new CallBackData("nefaz-kuzov")));
        inlineKeyboardButtonsFirstRow.add(firstInlineButton);


        InlineKeyboardButton returnButton = new InlineKeyboardButton();
        returnButton.setText("⏏️Вернуться");
        returnButton.setCallbackData(gson.toJson(new CallBackData("return-to-catalog")));
        inlineKeyboardButtonsReturnRow.add(returnButton);

        inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);
        inlineKeyboardButtons.add(inlineKeyboardButtonsReturnRow);

        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);
        return inlineKeyboardMarkup;
    }

    @Override
    public InlineKeyboardMarkup getInlineKeyboardForBelzan() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();

        List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsReturnRow = new ArrayList<>();

        InlineKeyboardButton firstInlineButton = new InlineKeyboardButton();
        firstInlineButton.setText("Ходовая часть/Подвеска автомобиля");
        firstInlineButton.setCallbackData(gson.toJson(new CallBackData("belzan-kuzov")));
        inlineKeyboardButtonsFirstRow.add(firstInlineButton);

        InlineKeyboardButton returnButton = new InlineKeyboardButton();
        returnButton.setText("⏏️Вернуться");
        returnButton.setCallbackData(gson.toJson(new CallBackData("return-to-catalog")));
        inlineKeyboardButtonsReturnRow.add(returnButton);

        inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);
        inlineKeyboardButtons.add(inlineKeyboardButtonsReturnRow);

        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);
        return inlineKeyboardMarkup;
    }

    @Override
    public InlineKeyboardMarkup getInlineKeyboardForMercedes() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();

        List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsSecondRow = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsThirdRow = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsReturnRow = new ArrayList<>();

        InlineKeyboardButton firstInlineButton = new InlineKeyboardButton();
        firstInlineButton.setText("Трансмиссия/Сцепление");
        firstInlineButton.setCallbackData(gson.toJson(new CallBackData("mercedes-transmission")));
        inlineKeyboardButtonsFirstRow.add(firstInlineButton);

        InlineKeyboardButton secondInlineButton = new InlineKeyboardButton();
        secondInlineButton.setText("Двигатель/Система охлаждения");
        secondInlineButton.setCallbackData(gson.toJson(new CallBackData("mercedes-dvigatel")));
        inlineKeyboardButtonsSecondRow.add(secondInlineButton);

        InlineKeyboardButton thirdInlineButton = new InlineKeyboardButton();
        thirdInlineButton.setText("Кузов/Кабина (кузов)");
        thirdInlineButton.setCallbackData(gson.toJson(new CallBackData("mercedes-kuzov")));
        inlineKeyboardButtonsThirdRow.add(thirdInlineButton);

        InlineKeyboardButton returnButton = new InlineKeyboardButton();
        returnButton.setText("⏏️Вернуться");
        returnButton.setCallbackData(gson.toJson(new CallBackData("return-to-catalog")));
        inlineKeyboardButtonsReturnRow.add(returnButton);

        inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);
        inlineKeyboardButtons.add(inlineKeyboardButtonsSecondRow);
        inlineKeyboardButtons.add(inlineKeyboardButtonsThirdRow);
        inlineKeyboardButtons.add(inlineKeyboardButtonsReturnRow);

        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);
        return inlineKeyboardMarkup;
    }

    @Override
    public InlineKeyboardMarkup getPartsPhotoButton() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = new ArrayList<>();
        InlineKeyboardButton firstInlineButton = new InlineKeyboardButton();
        firstInlineButton.setText("Фотография");
        firstInlineButton.setCallbackData(gson.toJson(new CallBackData("photo-part")));
        inlineKeyboardButtonsFirstRow.add(firstInlineButton);
        inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);
        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);
        return inlineKeyboardMarkup;
    }

    @Override
    public GetPartsEntity searchPart(String text) {
        GetPartsEntity getPartsEntity = getPartsDAO.getPartsSearchEntityGet(text);
        return getPartsEntity;
    }
}
