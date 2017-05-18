package com.trigues.entity;

import java.util.HashMap;

/**
 * Created by mbaque on 05/05/2017.
 */

public class ChatLocation extends ChatMessage {

    Float latitude;
    Float longitude;

    public ChatLocation() {
    }

    public ChatLocation(int fromUserId, Long date, Float latitude, Float longitude, String chatId) {
        super(fromUserId, date, chatId);
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public ChatLocation (HashMap<String, Object> params){
        super(((Long) params.get("fromUserId")).intValue(), (Long) params.get("date"), (String) params.get("chatId"));
        this.latitude = ((Double) params.get("latitude")).floatValue();
        this.longitude = ((Double) params.get("longitude")).floatValue();
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }
}
