package com.example.vishalmahajan.denmhouse.Models;

/**
 * Created by Android-Dev2 on 9/14/2017.
 */

public class ItemModel {

    int id;
    String productTitle,productType,productCode,productPrice,productSubtotal,productCount,productImage;

    public ItemModel(int id, String productTitle, String productType, String productCode, String productPrice, String productSubtotal, String productCount, String productImage) {
        this.id = id;
        this.productTitle = productTitle;
        this.productType = productType;
        this.productCode = productCode;
        this.productPrice = productPrice;
        this.productSubtotal = productSubtotal;
        this.productCount = productCount;
        this.productImage = productImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductSubtotal() {
        return productSubtotal;
    }

    public void setProductSubtotal(String productSubtotal) {
        this.productSubtotal = productSubtotal;
    }

    public String getProductCount() {
        return productCount;
    }

    public void setProductCount(String productCount) {
        this.productCount = productCount;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
}
