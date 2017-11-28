package com.example.vishalmahajan.denmhouse.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by vishal mahajan on 8/12/2017.
 */
public class TodayDealModel implements Serializable {

    String id, title,description,oldRate,newRate,type,productCode,image;
    int counter=0,itemCount;


    public TodayDealModel(String id, String title, String description, String newRate, String image, int itemCount) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.oldRate = oldRate;
        this.newRate = newRate;
        this.type = type;
        this.productCode = productCode;
        this.image = image;
        this.counter = counter;
        this.itemCount = itemCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getOldRate() {
        return oldRate;
    }

    public void setOldRate(String oldRate) {
        this.oldRate = oldRate;
    }

    public String getNewRate() {
        return newRate;
    }

    public void setNewRate(String newRate) {
        this.newRate = newRate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }
}
