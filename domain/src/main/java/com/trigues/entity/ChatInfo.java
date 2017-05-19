package com.trigues.entity;

/**
 * Created by marc on 11/05/17.
 */

public class ChatInfo {
    String nameOtherUser;
    String title;
    String productMatched;


    public ChatInfo() {
    }

    public String getNameOtherUser() {
        return nameOtherUser;
    }
    public String getTitle() { return title;}
    public String getProductMatched() {
        return productMatched;
    }


    public void setProductOwn(String nameOtherUser) {
        this.nameOtherUser = nameOtherUser;
    }
    public void setTitle(String title) {this.title = title;}
    public void setProductMatched(String productMatched) {this.productMatched = productMatched;}






}
