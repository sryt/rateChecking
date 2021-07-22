package com.example.grocerieslist.db.prodImg;

import android.graphics.Bitmap;

/**
 * Created by Tejaswi on 09/12/20.
 */
public class ProdImgClass {
    String id;
    String name;
    Bitmap img;
    String flag;

    public ProdImgClass() {
    }

    public ProdImgClass(String name, Bitmap img, String flag) {
        this.name = name;
        this.img = img;
        this.flag = flag;
    }

    public ProdImgClass(String id, String name, Bitmap img, String flag) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.flag = flag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "ProdImgClass{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", img=" + img +
                ", flag='" + flag + '\'' +
                '}';
    }
}
