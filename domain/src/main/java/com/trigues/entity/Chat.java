package com.trigues.entity;

/**
 * Created by mbaque on 03/05/2017.
 */

public class Chat {

    String user;
    String lastMessage;

    public Chat() {
    }

    public Chat(String user, String lastMessage) {
        this.user = user;
        this.lastMessage = lastMessage;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
