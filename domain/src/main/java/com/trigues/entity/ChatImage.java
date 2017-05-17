package com.trigues.entity;

import java.util.HashMap;

/**
 * Created by mbaque on 04/05/2017.
 */

public class ChatImage extends ChatMessage {

    String encodedImage;

    public ChatImage (int userId, long date, String encodedImage){
        super(userId, date);
        this.encodedImage = encodedImage;
    }

    public ChatImage (String key, int userId, long date, String encodedImage){
        super(key, userId, date);
        this.encodedImage = encodedImage;
    }

    public ChatImage (String key, HashMap<String,Object> params){
        super(key, ((Long) params.get("fromUserId")).intValue(), (Long) params.get("date"));

        this.encodedImage = (String) params.get("encodedImage");
    }

    public String getEncodedImage() {
        return encodedImage;
    }

    public void setEncodedImage(String encodedImage) {
        this.encodedImage = encodedImage;
    }
}
