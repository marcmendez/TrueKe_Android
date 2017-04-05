package com.trigues.entity;

import java.util.List;

/**
 * Created by mbaque on 24/03/2017.
 */

public class Product {

    int id;
    int userId;
    String title;
    String description;
    List<String> images;
    String productCategory;
    List<String> desiredCategories;
    int minPrice;
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
