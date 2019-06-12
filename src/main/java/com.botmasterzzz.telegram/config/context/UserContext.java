package com.botmasterzzz.telegram.config.context;

import com.botmasterzzz.bot.api.impl.objects.Chat;
import com.botmasterzzz.bot.api.impl.objects.Update;
import com.botmasterzzz.bot.api.impl.objects.User;
import com.botmasterzzz.bot.api.impl.objects.replykeyboard.InlineKeyboardMarkup;
import com.botmasterzzz.telegram.dto.CallBackData;

public class UserContext {

    private Update update;
    private User user;
    private Chat chat;
    private long userId;
    private long chatId;
    private InlineKeyboardMarkup inlineKeyboardMarkup;
    private int messageId;
    private int categoryId;
    private int productId;
    private CallBackData callBackData;

    public void setUser(User user) {
        this.user = user;
        this.userId = user.getId();
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
        this.chatId = chat.getId();
    }

    public Update getUpdate() {
        return update;
    }

    public void setUpdate(Update update) {
        this.update = update;
    }

    public long getChatId() {
        return chatId;
    }

    public User getUser() {
        return user;
    }

    public long getUserId() {
        return userId;
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

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
