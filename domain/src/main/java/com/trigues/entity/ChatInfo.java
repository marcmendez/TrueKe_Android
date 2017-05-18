package com.trigues.entity;

/**
 * Created by marc on 11/05/17.
 */

public class ChatInfo {
    int chatID;
    int productID1;
    int productID2;

    public ChatInfo() {
    }

    public int getChatID() {
        return chatID;
    }
    public int getProductID1() {
        return productID1;
    }
    public int getProductID2() {
        return productID2;
    }

    public void setChatID(int chatID) {
        this.chatID = chatID;
    }

    public void setProductID1(int productID1) {this.productID1 = productID1;}
    public void setProductID2(int productID2) {this.productID2 = productID2;}

}
