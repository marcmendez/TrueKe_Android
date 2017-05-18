package com.trigues.entity;

            /**
 * Created by mbaque on 24/03/2017.
 */
public class User {

    private int id;
    private String phone;
    private String user;
    private String password;
    private String email;
    private String birthDate;
    private String imagePath;
    private int products;
    private int truekes;
    private int ratingsNumber;
    private float ratingsValue;


    public User(String email, String password){
        this.email = email;
        this.password = password;
    }

    public User(int id, String phone, String user, String password, String email, String birthDate, String imagePath, int products, int truekes, int ratingsNumber, float ratingsValue) {
        this.id = id;
        this.phone = phone;
        this.user = user;
        this.password = password;
        this.email = email;
        this.birthDate = birthDate;
        this.imagePath = imagePath;
        this.products = products;
        this.truekes = truekes;
        this.ratingsNumber = ratingsNumber;
        this.ratingsValue = ratingsValue;
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

    public float getRatingsValue() {
        return ratingsValue;
    }

    public void setRatingsValue(float ratingsValue) {
        this.ratingsValue = ratingsValue;
    }

    public int getRatingsNumber() {
        return ratingsNumber;
    }

    public void setRatingsNumber(int ratingsNumber) {
        this.ratingsNumber = ratingsNumber;
    }

                public String getImagePath() {
                    return imagePath;
                }

                public void setImagePath(String imagePath) {
                    this.imagePath = imagePath;
                }
            }