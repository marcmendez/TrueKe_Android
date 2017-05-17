package com.trigues.entity;

import java.util.HashMap;

/**
 * Created by mbaque on 04/05/2017.
 */

public class ChatImage extends ChatMessage {

    String encodedImage;

    public ChatImage (int userId, long date, String encodedImage, String chatId){
        super(userId, date, chatId);
        this.encodedImage = encodedImage;
    }

    public ChatImage (HashMap<String,Object> params){
        super(((Long) params.get("fromUserId")).intValue(), (Long) params.get("date"), (String) params.get("chatId"));

        this.encodedImage = (String) params.get("encodedImage");
    }

    public String getEncodedImage() {
        return encodedImage;
    }

    public void setEncodedImage(String encodedImage) {
        this.encodedImage = encodedImage;
    }
}
