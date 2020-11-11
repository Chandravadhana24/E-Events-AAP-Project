package com.example.login_at1;

import android.graphics.Bitmap;

public class modelClass {

    private String imageNo;
    private Bitmap image;

    public modelClass(String imageNo, Bitmap image) {
        this.imageNo = imageNo;
        this.image = image;
    }

    public String getImageNo() {
        return imageNo;
    }

    public void setImageNo(String imageNo) {
        this.imageNo = imageNo;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
