package com.trigues.entity;

/**
 * Created by Eduard on 28/04/2017.
 */

public class productsMatch {
    private Integer product_id1;
    private Integer product_id2;
    private Integer wants;

    public productsMatch(Integer product_id1, Integer product_id2, Integer wants) {
        this.product_id1 = product_id1;
        this.product_id2 = product_id2;
        this.wants = wants;
    }

    public int getProductId1() {
        return product_id1;
    }

    public void setProductId1(Integer id) {
        this.product_id1 = id;
    }

    public Integer getProductId2() {
        return product_id2;
    }

    public void setProductId2(Integer id) {
        this.product_id2 = id;
    }

    public Integer getWants() {
        return wants;
    }

    public void setWants(int id) {
        this.wants = wants;
    }
}
