package com.trigues.entity;

/**
 * Created by Albert on 04/06/2017.
 */

public class VoteData {
    private int product_id;
    private float rating;

    public VoteData(int product_id, float rating) {
        this.product_id = product_id;
        this.rating = rating;
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
}
