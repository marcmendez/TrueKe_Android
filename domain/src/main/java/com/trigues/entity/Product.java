package com.trigues.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mbaque on 24/03/2017.
 */

public class Product {

    int id;

    @SerializedName("user_id")
    int userId;
    String title;
    String description;
    List<String> images;

    @SerializedName("category")
    String productCategory;

    List<String> desiredCategories;

    @SerializedName("min_price")
    int minPrice;

    @SerializedName("max_price")
    int maxPrice;


    public Product(int id, int userId, String title, String description, List<String> images, String productCategory, List<String> desiredCategories, int minPrice, int maxPrice) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.images = images;
        this.productCategory = productCategory;
        this.desiredCategories = desiredCategories;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public void setProduct(Product p) {
        this.id = p.getId();
        this.userId = p.getUserId();
        this.title = p.getTitle();
        this.description = p.getDescription();
        if(p.getImages() != null) this.images = p.getImages();
        else this.images = new ArrayList();
        this.productCategory = p.getProductCategory();
        this.desiredCategories = p.getDesiredCategories();
        this.minPrice = p.getMinPrice();
        this.maxPrice = p.getMaxPrice();
    }

    public boolean haveImages() {
        if(images.isEmpty()) return false;
        else return true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public List<String> getDesiredCategories() {
        return desiredCategories;
    }

    public void setDesiredCategories(List<String> desiredCategories) {
        this.desiredCategories = desiredCategories;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }
}
