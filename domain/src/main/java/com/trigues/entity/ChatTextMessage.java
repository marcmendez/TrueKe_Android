package com.trigues.entity;

/**
 * Created by mbaque on 04/05/2017.
 */

public class ChatTextMessage extends ChatMessage {

    String message;

    public ChatTextMessage (int userId, long date, String message){
        super(userId, date);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
