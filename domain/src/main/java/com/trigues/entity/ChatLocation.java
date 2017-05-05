package com.trigues.entity;

/**
 * Created by mbaque on 05/05/2017.
 */

public class ChatLocation extends ChatMessage {

    Float latitude;
    Float longitude;

    public ChatLocation() {
    }

    public ChatLocation(int fromUserId, Long date, Float latitude, Float longitude) {
        super(fromUserId, date);
        this.latitude = latitude;
        this.longitude = longitude;
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
