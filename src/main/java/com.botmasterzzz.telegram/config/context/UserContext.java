package com.botmasterzzz.telegram.config.context;

import com.botmasterzzz.bot.api.impl.objects.Chat;
import com.botmasterzzz.bot.api.impl.objects.Update;
import com.botmasterzzz.bot.api.impl.objects.User;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.InlineKeyboardMarkup;
import com.botmasterzzz.telegram.dto.CallBackData;
import com.botmasterzzz.telegram.dto.ProjectCommandDTO;
import com.botmasterzzz.telegram.entity.GetPartsDetailsEntity;
import com.botmasterzzz.telegram.entity.GetPartsEntity;

import java.util.List;

public class UserContext {

    private Update update;
    private User user;
    private Chat chat;
    private ProjectCommandDTO projectCommandDTO;
    private InlineKeyboardMarkup inlineKeyboardMarkup;
    private CallBackData callBackData;
    private List<ProjectCommandDTO> projectCommandDTOList;
    private List<GetPartsEntity> getPartsEntityList;
    private List<GetPartsDetailsEntity> getPartsDetailsEntityList;
    private Long instanceId;
    private boolean remain;
    private String attributeName;
    private boolean anon;
    private long partId;

    public void setUser(User user) {
        this.user = user;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public Update getUpdate() {
        return update;
    }

    public void setUpdate(Update update) {
        this.update = update;
    }

    public User getUser() {
        return user;
    }

    public CallBackData getCallBackData() {
        return callBackData;
    }

    public void setCallBackData(CallBackData callBackData) {
        this.callBackData = callBackData;
    }

    public InlineKeyboardMarkup getInlineKeyboardMarkup() {
        return inlineKeyboardMarkup;
    }

    public void setInlineKeyboardMarkup(InlineKeyboardMarkup inlineKeyboardMarkup) {
        this.inlineKeyboardMarkup = inlineKeyboardMarkup;
    }

    public List<ProjectCommandDTO> getProjectCommandDTOList() {
        return projectCommandDTOList;
    }

    public void setProjectCommandDTOList(List<ProjectCommandDTO> projectCommandDTOList) {
        this.projectCommandDTOList = projectCommandDTOList;
    }

    public ProjectCommandDTO getProjectCommandDTO() {
        return projectCommandDTO;
    }

    public void setProjectCommandDTO(ProjectCommandDTO projectCommandDTO) {
        this.projectCommandDTO = projectCommandDTO;
    }

    public Long getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Long instanceId) {
        this.instanceId = instanceId;
    }

    public boolean isRemain() {
        return remain;
    }

    public void setRemain(boolean remain) {
        this.remain = remain;
    }

    public long getPartId() {
        return partId;
    }

    public void setPartId(long partId) {
        this.partId = partId;
    }

    public List<GetPartsEntity> getGetPartsEntityList() {
        return getPartsEntityList;
    }

    public void setGetPartsEntityList(List<GetPartsEntity> getPartsEntityList) {
        this.getPartsEntityList = getPartsEntityList;
    }

    public List<GetPartsDetailsEntity> getGetPartsDetailsEntityList() {
        return getPartsDetailsEntityList;
    }

    public void setGetPartsDetailsEntityList(List<GetPartsDetailsEntity> getPartsDetailsEntityList) {
        this.getPartsDetailsEntityList = getPartsDetailsEntityList;
    }

    public boolean isAnon() {
        return anon;
    }

    public void setAnon(boolean anon) {
        this.anon = anon;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }
}
