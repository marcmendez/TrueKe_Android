package com.trigues.entity;

/**
 * Created by Albert on 07/04/2017.
 */

public class Payment {
    private int id;
    private int user_id;
    private String type; //poner tipo con enum
    private String number;
    private String expireDate;
    private String name;
    private String country;
    private String province;
    private String city;
    private int postalCode;
    private String address;
    private String phone;

    public Payment(int id, int user_id, String type, String number, String expireDate, String name) {

    }

    public Payment(int id, int user_id, String type, String number, String expireDate, String name,
                   String country, String province, String city, int postalCode, String address, String phone) {
        this.id = id;
        this.user_id = user_id;
        this.type = type;
        this.number = number;
        this.expireDate = expireDate;
        this.name = name;
        this.country = country;
        this.province = province;
        this.city = city;
        this.postalCode = postalCode;
        this.address = address;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
