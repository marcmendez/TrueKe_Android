package com.trigues.entity;

/**
 * Created by Marc PC on 07/06/2017.
 */

public class ImageOtherUserType {
    private int userid;
    private String imagePath;

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }



    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getImagePath() {
        return imagePath;
    }
}
