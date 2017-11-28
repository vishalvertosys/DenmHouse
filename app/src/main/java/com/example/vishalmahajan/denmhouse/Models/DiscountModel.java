package com.example.vishalmahajan.denmhouse.Models;

/**
 * Created by Android-Dev2 on 8/14/2017.
 */

public class DiscountModel {
    String id;
    String title;
    String description;
    String oldRate;
    String newRate;
    String image;
    String type;
    String price;
    String productCode;

    public DiscountModel(){
        //default constructor if required
    }

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    String counter;
    int itemCount;
    int count;/*,subTotal*/
    double subTotal;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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


    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public DiscountModel(String id, String title, String description, String newRate, String image, int itemCount) {
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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

