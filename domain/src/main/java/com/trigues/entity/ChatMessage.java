package com.trigues.entity;

/**
 * Created by mbaque on 04/05/2017.
 */

public class ChatMessage {

    String key;
    int fromUserId;
    Long date;
    boolean read = false;

    public ChatMessage() {
    }

    public ChatMessage(int fromUserId, Long date) {
        this.fromUserId = fromUserId;
        this.date = date;
    }

    public ChatMessage(String key, int fromUserId, Long date) {
        this.key = key;
        this.fromUserId = fromUserId;
        this.date = date;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
