package com.trigues.entity;

/**
 * Created by Albert on 04/06/2017.
 */

public class VoteData {
    private String chatId;
    private boolean isUser1;
    private int product_id;
    private int otherProduct;
    private float rating;

    public VoteData(int product_id, float rating, String chatId, boolean isUser1, int otherProduct) {
        this.product_id = product_id;
        this.rating = rating;
        this.chatId = chatId;
        this.isUser1 = isUser1;
        this.otherProduct = otherProduct;
    }

    public int getOtherProduct() {
        return otherProduct;
    }

    public void setOtherProduct(int otherProduct) {
        this.otherProduct = otherProduct;
    }

    public boolean isUser1() {
        return isUser1;
    }

    public void setUser1(boolean user1) {
        isUser1 = user1;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }
}
