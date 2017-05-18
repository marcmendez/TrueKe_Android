package com.trigues.entity;

/**
 * Created by mbaque on 03/05/2017.
 */

public class Chat {

    String id;
    String user;
    String lastMessage;

    public Chat() {
    }

    public Chat(String id, String user, String lastMessage) {
        this.id = id;
        this.user = user;
        this.lastMessage = lastMessage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
