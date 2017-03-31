package com.trigues.entity;

            /**
 * Created by mbaque on 24/03/2017.
 */

            public class User {

        int id;
        String phone;
        String user;
        String password;
        String email;
        String birthDate;
        int products;
        int truekes;
        float rating;

    public User(String email, String password){
        this.email = email;
        this.password = password;
    }

    public User(int id, String phone, String user, String password, String email, String birthDate, int products, int truekes, float rating) {
        this.id = id;
        this.phone = phone;
        this.user = user;
        this.password = password;
        this.email = email;
        this.birthDate = birthDate;
        this.products = products;
        this.truekes = truekes;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public int getProducts() {
        return products;
    }

    public void setProducts(int products) {
        this.products = products;
    }

    public int getTruekes() {
        return truekes;
    }

    public void setTruekes(int truekes) {
        this.truekes = truekes;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}