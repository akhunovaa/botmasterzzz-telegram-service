package com.botmasterzzz.telegram.controller.telegram.getparts;

import com.botmasterzzz.bot.api.impl.methods.send.SendPhoto;
import com.botmasterzzz.bot.api.impl.methods.update.EditMessageText;
import com.botmasterzzz.bot.api.impl.objects.InputFile;
import com.botmasterzzz.bot.api.impl.objects.Update;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.InlineKeyboardMarkup;
import com.botmasterzzz.telegram.config.annotations.BotController;
import com.botmasterzzz.telegram.config.annotations.BotRequestMapping;
import com.botmasterzzz.telegram.config.context.UserContextHolder;
import com.botmasterzzz.telegram.dto.CallBackData;
import com.botmasterzzz.telegram.entity.GetPartsEntity;
import com.botmasterzzz.telegram.util.HelperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.List;

@BotController
public class InnerCatalogGetPartsMenuController {

    private static final Logger logger = LoggerFactory.getLogger(InnerCatalogGetPartsMenuController.class);

    @Autowired
    private GetPartsMessageService getPartsMessageService;

    @BotRequestMapping(value = "getparts-kamaz")
    public EditMessageText kamaz(Update update) {
        List<GetPartsEntity> getPartsEntityList = getPartsMessageService.getPartsList();
        UserContextHolder.currentContext().setGetPartsEntityList(getPartsEntityList);
        InlineKeyboardMarkup inlineKeyboardMarkup = getPartsMessageService.getInlineKeyboardForKamaz();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("⚙️<b>Раздел</b>\n");
        stringBuilder.append("Выберите наименование раздела:");
        EditMessageText editMessageText = getEditMessage(stringBuilder.toString(), update);
        editMessageText.setReplyMarkup(inlineKeyboardMarkup);
        return editMessageText;
    }

    @BotRequestMapping(value = "getparts-photo-part")
    public SendPhoto photo(Update update) {
        long partId = UserContextHolder.currentContext().getPartId();
        CallBackData callBackData = UserContextHolder.currentContext().getCallBackData();
        String fileName = "/home/repository/get_parts/images/" + partId + "/" + callBackData.getFileSelected() + "-image.jpg";
        SendPhoto sendPhoto = new SendPhoto();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("⚙ Фото #" + callBackData.getFileSelected() + "\n");
        File file1 = new File(fileName);
        sendPhoto.setPhoto(new InputFile(file1, "image-one"));
        sendPhoto.setCaption(stringBuilder.toString());
        sendPhoto.setChatId(update.getCallbackQuery().getMessage().getChatId());
        sendPhoto.disableNotification();
        return sendPhoto;
    }

    @BotRequestMapping(value = "getparts-return-to-catalog")
    public EditMessageText catalog(Update update) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\uD83D\uDCC2<b>Каталог</b>\n");
        stringBuilder.append("Выберите раздел:\n");
        InlineKeyboardMarkup inlineKeyboardMarkup = getPartsMessageService.getInlineKeyboardForCatalog();
        EditMessageText editMessageText = getEditMessage(stringBuilder.toString(), update);
        editMessageText.setReplyMarkup(inlineKeyboardMarkup);
        return editMessageText;
    }

    @BotRequestMapping(value = "getparts-part-nav-arrow")
    public EditMessageText partNavigation(Update update) {
        CallBackData callBackData = UserContextHolder.currentContext().getCallBackData();
        EditMessageText editMessageText;
        StringBuilder stringBuilder = new StringBuilder();
        List<GetPartsEntity> getPartsEntityList =  UserContextHolder.currentContext().getGetPartsEntityList();
        int offset = callBackData.getOffset(getPartsEntityList.size());
        InlineKeyboardMarkup inlineKeyboardMarkup = getPartsMessageService.getPartsPhotoButton(getPartsEntityList.get(offset).getId(), offset, getPartsEntityList.size());
        if (!getPartsEntityList.isEmpty()){
            stringBuilder.append("\uD83D\uDCC2<b>Поиск по каталогу</b>\n");
            stringBuilder.append("<b>Наименование:</b> ").append(HelperUtil.stringBeautyFormat(getPartsEntityList.get(offset).getName())).append("\n");
            stringBuilder.append("<b>Брэнд:</b> ").append(HelperUtil.stringBeautyFormat(getPartsEntityList.get(offset).getBrandName())).append("\n");
            stringBuilder.append("<b>Артикул:</b> ").append(HelperUtil.stringBeautyFormat(getPartsEntityList.get(offset).getArticle())).append("\n");
            stringBuilder.append("<b>Категория:</b> ").append(HelperUtil.stringBeautyFormat(getPartsEntityList.get(offset).getGetPartsDetailsEntity().getCatName())).append("\n");
            stringBuilder.append("<b>Описание:</b> ").append(HelperUtil.stringBeautyFormat(getPartsEntityList.get(offset).getGetPartsDetailsEntity().getDescription())).append("\n");
            stringBuilder.append("<b>Цвет:</b> ").append(HelperUtil.stringBeautyFormat(getPartsEntityList.get(offset).getGetPartsDetailsEntity().getColour())).append("\n");
            stringBuilder.append("<b>Материал:</b> ").append(HelperUtil.stringBeautyFormat(getPartsEntityList.get(offset).getGetPartsDetailsEntity().getMaterial())).append("\n");
            stringBuilder.append("<b>Высота:</b> ").append(HelperUtil.stringBeautyMeausreFormat(getPartsEntityList.get(offset).getGetPartsDetailsEntity().getHeight())).append("\n");
            stringBuilder.append("<b>Длина:</b> ").append(HelperUtil.stringBeautyMeausreFormat(getPartsEntityList.get(offset).getGetPartsDetailsEntity().getLength())).append("\n");
            stringBuilder.append("<b>Вес:</b> ").append(HelperUtil.stringBeautyFormat(getPartsEntityList.get(offset).getGetPartsDetailsEntity().getWeight())).append("\n");
            stringBuilder.append("<b>Ширина:</b> ").append(HelperUtil.stringBeautyMeausreFormat(getPartsEntityList.get(offset).getGetPartsDetailsEntity().getWidth())).append("\n");
            UserContextHolder.currentContext().setPartId(getPartsEntityList.get(offset).getId());
            UserContextHolder.currentContext().setGetPartsEntityList(getPartsEntityList);
            editMessageText = getEditMessage(stringBuilder.toString(), update);
            editMessageText.setReplyMarkup(inlineKeyboardMarkup);
        }else {
            stringBuilder.append("Ничего не найдено. Поробуйте снова!");
            editMessageText = getEditMessage(stringBuilder.toString(), update);
        }
        return editMessageText;
    }

    private EditMessageText getEditMessage(String text, Update update) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(update.getCallbackQuery().getMessage().getChatId());
        editMessageText.setText(text);
        editMessageText.enableHtml(Boolean.TRUE);
        editMessageText.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        return editMessageText;
    }

}
