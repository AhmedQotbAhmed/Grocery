package com.example.grocery.model;

public class Post {
    private String price_str;
    private String name_str;
    private String itemCategory;
    private String url = null;

    public Post() {
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
