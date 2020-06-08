package com.example.grocery.model;

import java.io.Serializable;

public class Products implements Serializable {
    private String price_str;
    private String name_str;
    private String itemCategory;
    private String url = null;
    private String total_price = "0";
    private String quantity = "0";


    public Products() {
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUri() {
        return url;
    }

    public void setUri(String url) {
        this.url = url;
    }

    public String getPrice_str() {
        return price_str;
    }

    public void setPrice_str(String price_str) {
        this.price_str = price_str;
    }

    public String getName_str() {
        return name_str;
    }

    public void setName_str(String name_str) {
        this.name_str = name_str;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }
}
