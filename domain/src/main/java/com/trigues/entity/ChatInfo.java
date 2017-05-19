package com.trigues.entity;

/**
 * Created by marc on 11/05/17.
 */

public class ChatInfo {
    String id;
    String nameOtherUser;
    String title;

    int my_product;
    int product_id1;
    int product_id2;


    public ChatInfo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameOtherUser() {
        return nameOtherUser;
    }

    public void setNameOtherUser(String nameOtherUser) {
        this.nameOtherUser = nameOtherUser;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMy_product() {
        return my_product;
    }

    public void setMy_product(int my_product) {
        this.my_product = my_product;
    }

    public int getProduct_id1() {
        return product_id1;
    }

    public void setProduct_id1(int product_id1) {
        this.product_id1 = product_id1;
    }

    public int getProduct_id2() {
        return product_id2;
    }

    public void setProduct_id2(int product_id2) {
        this.product_id2 = product_id2;
    }
}
