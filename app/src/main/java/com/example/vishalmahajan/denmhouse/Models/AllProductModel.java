package com.example.vishalmahajan.denmhouse.Models;

/**
 * Created by Android-Dev2 on 8/14/2017.
 */

public class AllProductModel {
    String  id,title,description,oldRate,newRate,image;
    int itemCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AllProductModel(String id, String title, String description, String newRate, String image, int itemCount) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.oldRate = oldRate;
        this.newRate = newRate;
        this.image = image;
        this.itemCount = itemCount;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }
}


