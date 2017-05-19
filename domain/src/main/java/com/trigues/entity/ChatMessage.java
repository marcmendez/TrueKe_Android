package com.trigues.entity;

/**
 * Created by mbaque on 04/05/2017.
 */

public class ChatMessage {

    int fromUserId;
    Long date;
    String chatId;

    boolean read;

    public ChatMessage() {
    }

    public ChatMessage(int fromUserId, Long date, String chatId, boolean read) {
        this.fromUserId = fromUserId;
        this.date = date;
        this.chatId = chatId;
        this.read = read;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public int getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(int fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }
}
