package com.trigues.entity;

/**
 * Created by Albert on 19/05/2017.
 */

public class TruekeData {
    private int status;
    private String chatID;
    private String truekeID;

    public TruekeData(int status, String chatID, String truekeID) {
        this.status = status;
        this.chatID = chatID;
        this.truekeID = truekeID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getChatID() {
        return chatID;
    }

    public void setChatID(String chatID) {
        this.chatID = chatID;
    }

    public String getTruekeID() {
        return truekeID;
    }

    public void setTruekeID(String truekeID) {
        this.truekeID = truekeID;
    }
}
