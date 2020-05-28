package com.example.grocery.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Invoice implements Serializable {


    private String date;
    private String user;
    private HashMap<String, Products> productList;
    private String total_pric;

    public Invoice() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date date_classa = new Date();
        date = formatter.format(date_classa);
    }


    public HashMap<String, Products> getProductList() {
        return productList;
    }

    public void setProductList(HashMap<String, Products> productList) {
        this.productList = productList;
    }

    public String getTotal_pric() {
        return total_pric;
    }

    public void setTotal_pric(String total_pric) {
        this.total_pric = total_pric;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }


}
