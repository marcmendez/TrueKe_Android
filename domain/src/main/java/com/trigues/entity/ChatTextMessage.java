package com.trigues.entity;

import java.util.HashMap;

/**
 * Created by mbaque on 04/05/2017.
 */

public class ChatTextMessage extends ChatMessage {

    String message;

    public ChatTextMessage (int userId, long date, String message, String chatId, boolean read){
        super(userId, date, chatId, read);
        this.message = message;
    }

    public ChatTextMessage (HashMap<String, Object> params){
        super(((Long) params.get("fromUserId")).intValue(), (Long) params.get("date"), (String) params.get("chatId"), (Boolean) params.get("read"));

        this.message = (String) params.get("message");
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
