package com.botmasterzzz.telegram.controller.telegram.getparts.impl;

import com.botmasterzzz.bot.api.impl.objects.replykeyboard.InlineKeyboardMarkup;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.buttons.InlineKeyboardButton;
import com.botmasterzzz.telegram.config.context.UserContextHolder;
import com.botmasterzzz.telegram.controller.telegram.getparts.GetPartsMessageService;
import com.botmasterzzz.telegram.dao.GetPartsDAO;
import com.botmasterzzz.telegram.dto.CallBackData;
import com.botmasterzzz.telegram.entity.GetPartsDetailsEntity;
import com.botmasterzzz.telegram.entity.GetPartsEntity;
import com.botmasterzzz.telegram.service.StorageService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class GetPartsMessageServiceImpl implements GetPartsMessageService {

    @Value("${multipart.file.upload.path}")
    private String path;

    private static final String FILE_PATH = "/home/repository/get_parts/images/";

    @Autowired
    private Gson gson;

    @Autowired
    private GetPartsDAO getPartsDAO;

    private static final Logger logger = LoggerFactory.getLogger(StorageService.class);

    @Override
    public InlineKeyboardMarkup getInlineKeyboardForCatalog() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();

        List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = new ArrayList<>();


        InlineKeyboardButton firstInlineButton = new InlineKeyboardButton();
        firstInlineButton.setText("KAMAZ");
        firstInlineButton.setCallbackData(gson.toJson(new CallBackData("kamaz")));
        inlineKeyboardButtonsFirstRow.add(firstInlineButton);

        inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);

        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);
        return inlineKeyboardMarkup;
    }

    @Override
    public InlineKeyboardMarkup getInlineKeyboardForKamaz(int offset) {
        List<GetPartsDetailsEntity> getPartsDetailsEntityList = UserContextHolder.currentContext().getGetPartsDetailsEntityList();
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();

        List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsSecondRow = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsThirdRow = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsFourthRow = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsFifthRow = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsSixthRow = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsReturnRow = new ArrayList<>();
        if (!getPartsDetailsEntityList.isEmpty()){
            CallBackData callBackData = new CallBackData("cat-kamaz");
            String catName;
            if (offset < getPartsDetailsEntityList.size()) {
                InlineKeyboardButton firstButton = new InlineKeyboardButton();
                catName = String.valueOf(null == getPartsDetailsEntityList.get(offset) ? "Другое" : getPartsDetailsEntityList.get(offset));
                firstButton.setText(catName);
                callBackData.setCategoryId(offset);
                firstButton.setCallbackData(gson.toJson(callBackData));
                inlineKeyboardButtonsFirstRow.add(firstButton);
                inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);
            }
            if (offset + 1 < getPartsDetailsEntityList.size()){
                InlineKeyboardButton secondButton = new InlineKeyboardButton();
                catName = String.valueOf(null == getPartsDetailsEntityList.get(offset + 1) ? "Другое" : getPartsDetailsEntityList.get(offset + 1));
                secondButton.setText(catName);
                callBackData.setCategoryId(offset + 1);
                secondButton.setCallbackData(gson.toJson(callBackData));
                inlineKeyboardButtonsSecondRow.add(secondButton);
                inlineKeyboardButtons.add(inlineKeyboardButtonsSecondRow);
            }

            if (offset + 2 < getPartsDetailsEntityList.size()){
                InlineKeyboardButton thirdButton = new InlineKeyboardButton();
                catName = String.valueOf(null == getPartsDetailsEntityList.get(offset + 2) ? "Другое" : getPartsDetailsEntityList.get(offset + 2));
                thirdButton.setText(catName);
                callBackData.setCategoryId(offset + 2);
                thirdButton.setCallbackData(gson.toJson(callBackData));
                inlineKeyboardButtonsThirdRow.add(thirdButton);
                inlineKeyboardButtons.add(inlineKeyboardButtonsThirdRow);
            }

            if (offset + 3 < getPartsDetailsEntityList.size()){
                InlineKeyboardButton fourthButton = new InlineKeyboardButton();
                catName = String.valueOf(null == getPartsDetailsEntityList.get(offset + 3) ? "Другое" : getPartsDetailsEntityList.get(offset + 3));
                fourthButton.setText(catName);
                callBackData.setCategoryId(offset + 3);
                fourthButton.setCallbackData(gson.toJson(callBackData));
                inlineKeyboardButtonsFourthRow.add(fourthButton);
                inlineKeyboardButtons.add(inlineKeyboardButtonsFourthRow);
            }

            if (offset + 4 < getPartsDetailsEntityList.size()){
                InlineKeyboardButton fifthButton = new InlineKeyboardButton();
                catName = String.valueOf(null == getPartsDetailsEntityList.get(offset + 4) ? "Другое" : getPartsDetailsEntityList.get(offset + 4));
                fifthButton.setText(catName);
                callBackData.setCategoryId(offset + 4);
                fifthButton.setCallbackData(gson.toJson(callBackData));
                inlineKeyboardButtonsFifthRow.add(fifthButton);
                inlineKeyboardButtons.add(inlineKeyboardButtonsFifthRow);
            }

        }

        InlineKeyboardButton rowLeftInlineButton = new InlineKeyboardButton();
        InlineKeyboardButton rowRightInlineButton = new InlineKeyboardButton();
        CallBackData callBackDataForArrows = new CallBackData("cat-nav-arrow");
        rowLeftInlineButton.setText("<");
        rowRightInlineButton.setText(">");

        callBackDataForArrows.setOffset(offset - 4 <= 0 ? 0 : offset - 4);
        rowLeftInlineButton.setCallbackData(gson.toJson(callBackDataForArrows));

        callBackDataForArrows.setOffset(offset + 4 > getPartsDetailsEntityList.size() ? getPartsDetailsEntityList.size() - 4 : offset + 4);
        rowRightInlineButton.setCallbackData(gson.toJson(callBackDataForArrows));

        inlineKeyboardButtonsSixthRow.add(rowLeftInlineButton);
        inlineKeyboardButtonsSixthRow.add(rowRightInlineButton);

        InlineKeyboardButton returnButton = new InlineKeyboardButton();
        returnButton.setText("⏏️Вернуться");
        returnButton.setCallbackData(gson.toJson(new CallBackData("return-to-catalog")));
        inlineKeyboardButtonsReturnRow.add(returnButton);


        inlineKeyboardButtons.add(inlineKeyboardButtonsSixthRow);
        inlineKeyboardButtons.add(inlineKeyboardButtonsReturnRow);

        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);
        return inlineKeyboardMarkup;
    }

    @Override
    public InlineKeyboardMarkup getPartsPhotoButton(long partId, int offset, int limit) {
        String path = FILE_PATH + partId;
        logger.info("Images load from file  path: {}", path);
        logger.info("Location load: {}", Arrays.toString(new File(FILE_PATH).list()));
        logger.info("Location load: {}", Arrays.toString(new File(path).list()));
        logger.info("Location load: {}", Arrays.toString(new File("/home").list()));
        logger.info("Location load: {}", Arrays.toString(new File("/home/repository").list()));
        logger.info("Location load: {}", Arrays.toString(new File("/home/repository/get_parts").list()));
        File file = new File(path);
        String[] files = file.list();
        int fileCount = null != files ? files.length : 0;
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsFirstRow = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsSecondRowRow = new ArrayList<>();
        CallBackData callBackData = new CallBackData("photo-part");
        if (fileCount > 0) {
            InlineKeyboardButton firstInlineButton = new InlineKeyboardButton();
            firstInlineButton.setText("Фотография #1");
            callBackData.setFileSelected(1);
            firstInlineButton.setCallbackData(gson.toJson(callBackData));
            inlineKeyboardButtonsFirstRow.add(firstInlineButton);
            if (fileCount > 1) {
                InlineKeyboardButton secondInlineButton = new InlineKeyboardButton();
                secondInlineButton.setText("Фотография #2");
                callBackData.setFileSelected(2);
                secondInlineButton.setCallbackData(gson.toJson(callBackData));
                inlineKeyboardButtonsFirstRow.add(secondInlineButton);
            }
            if (fileCount > 2) {
                InlineKeyboardButton thirdInlineButton = new InlineKeyboardButton();
                thirdInlineButton.setText("Фотография #3");
                callBackData.setFileSelected(3);
                thirdInlineButton.setCallbackData(gson.toJson(callBackData));
                inlineKeyboardButtonsFirstRow.add(thirdInlineButton);
            }
        }
        InlineKeyboardButton rowLeftInlineButton = new InlineKeyboardButton();
        InlineKeyboardButton rowRightInlineButton = new InlineKeyboardButton();
        CallBackData callBackDataForArrows = new CallBackData("part-nav-arrow");
        rowLeftInlineButton.setText("<");
        rowRightInlineButton.setText(">");


        callBackDataForArrows.setOffset(offset > 0 ? offset - 1 : 0);
        rowLeftInlineButton.setCallbackData(gson.toJson(callBackDataForArrows));

        callBackDataForArrows.setOffset(offset >= limit ? limit : offset + 1);
        rowRightInlineButton.setCallbackData(gson.toJson(callBackDataForArrows));

        inlineKeyboardButtonsSecondRowRow.add(rowLeftInlineButton);
        inlineKeyboardButtonsSecondRowRow.add(rowRightInlineButton);
        inlineKeyboardButtons.add(inlineKeyboardButtonsFirstRow);
        if(limit > 0) {
            inlineKeyboardButtons.add(inlineKeyboardButtonsSecondRowRow);
        }
        inlineKeyboardMarkup.setKeyboard(inlineKeyboardButtons);
        return inlineKeyboardMarkup;
    }

    @Override
    public List<GetPartsEntity> searchPart(String text) {
        List<GetPartsEntity> getPartsEntityList = getPartsDAO.getPartsSearchEntityGet(text);
        return getPartsEntityList;
    }

    @Override
    public List<GetPartsEntity> getPartsList() {
        List<GetPartsEntity> getPartsEntityList = getPartsDAO.getGetPartsEntityList();
        return getPartsEntityList;
    }

    @Override
    public List<GetPartsDetailsEntity> getPartsDetailsCatList() {
        List<GetPartsDetailsEntity> getPartsDetailsEntityList = getPartsDAO.getPartsDetailsCatList();
        return getPartsDetailsEntityList;
    }

    @Override
    public List<GetPartsEntity> getPartsListCatName(String catName) {
        List<GetPartsEntity> getPartsEntityList = getPartsDAO.getGetPartsEntityList();
        List<GetPartsEntity> getPartsEntityNewList = new ArrayList<>();
        for (GetPartsEntity getPartsEntity : getPartsEntityList) {
            if (getPartsEntity.getGetPartsDetailsEntity().getCatName().equals(catName)){
                getPartsEntityNewList.add(getPartsEntity);
            }
        }
        return getPartsEntityNewList;
    }
}
