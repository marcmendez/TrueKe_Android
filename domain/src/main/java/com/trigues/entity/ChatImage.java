package com.trigues.entity;

/**
 * Created by mbaque on 04/05/2017.
 */

public class ChatImage extends ChatMessage {

    String encodedImage;

    public ChatImage (int userId, long date, String encodedImage){
        super(userId, date);
        this.encodedImage = encodedImage;
    }

    public String getEncodedImage() {
        return encodedImage;
    }

    public void setEncodedImage(String encodedImage) {
        this.encodedImage = encodedImage;
    }
}
