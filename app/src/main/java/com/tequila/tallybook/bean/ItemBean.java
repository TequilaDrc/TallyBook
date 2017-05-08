package com.tequila.tallybook.bean;

/**
 * Created by Tequila on 2017/5/8.
 */

public class ItemBean {
    private int img;
    private String title;
    private boolean isClick=false;

    public ItemBean() {
    }

    public ItemBean(int img, String title, boolean isUpdate) {
        this.img = img;
        this.title = title;
        this.isClick = isUpdate;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isUpdate() {
        return isClick;
    }

    public void setIsUpdate(boolean isUpdate) {
        this.isClick = isUpdate;
    }

    @Override
    public String toString() {
        return "ItemBean{" +
                "img=" + img +
                ", title='" + title + '\'' +
                ", isUpdate=" + isClick +
                '}';
    }
}