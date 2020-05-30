package com.example.grocery.model;

import java.util.HashMap;

public class Category {

    private  HashMap<String,Products> products_list=new HashMap<>();
    private String category;


    public HashMap<String, Products> getProducts_list() {
        return products_list;
    }

    public String getCategory() {
        return category;
    }


    public void setProducts_ToList(String name, Products product) {
        this.products_list.put(name,product);
    }


    public void setProducts_list(HashMap<String, Products> products_list) {
        this.products_list = products_list;
    }

    public void setCategory(String category) {
        this.category = category;
    }


}
