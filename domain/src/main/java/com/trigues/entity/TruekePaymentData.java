package com.trigues.entity;

/**
 * Created by Albert on 19/05/2017.
 */

public class TruekePaymentData {
    private int product_id;
    private String chat_id;
    private int payment_id;

    public TruekePaymentData(int product_id, String chat_id, int payment_id) {
        this.product_id = product_id;
        this.chat_id = chat_id;
        this.payment_id = payment_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getChat_id() {
        return chat_id;
    }

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }

    public int getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }
}
