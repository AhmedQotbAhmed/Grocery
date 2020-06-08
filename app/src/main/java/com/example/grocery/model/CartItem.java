package com.example.grocery.model;

import android.util.Log;

import java.util.List;

public class CartItem {
    public List<Products> list;
    private double totalPrice;
    private double quantity;
    private double price;

    public CartItem() {
    }


    public String pluseOprition(String quantity_str) {
        quantity = Double.valueOf(quantity_str.replace(" k", ""));
        quantity += 0.25;
        totalPrice = price * quantity;
        return quantity + "";
    }

    public String minusOprition(String quantity_str) {
        if (Double.valueOf(quantity_str.replace(" k", "")) > 0.25) {
            quantity = Double.valueOf(quantity_str.replace(" k", ""));
            Log.e("price", quantity + "");

            quantity -= 0.25;
            totalPrice = price * quantity;
            return quantity + "";
        } else {
            quantity = Double.valueOf(quantity_str.replace(" k", ""));
            quantity = 0.25;
            totalPrice = price * quantity;
            return quantity + "";
        }


    }

    public String getTotalPrice() {

        return totalPrice + "";
    }

    public double get_Total_Price() {
        return totalPrice;
    }

    public void setTotlePrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Products> getList() {
        return list;
    }

    public void setList(List<Products> list) {
        this.list = list;
    }
}
