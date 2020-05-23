package com.example.grocery.UI.main;

import com.example.grocery.model.Products;

import java.util.Arrays;
import java.util.List;

public class CartItem {
    public  List<Products> list;
    private double totalPrice;
    private double quantity;
    private double price;

    public CartItem() {
    }



    public  String pluseOprition(String quantity_str) {
        quantity = Double.valueOf(quantity_str.replace(" k", "")) / 1000.0;
        quantity += 0.25;
        totalPrice=price*quantity;
        return quantity + " k";
    }

    public String minusOprition(String quantity_str) {
        if (Integer.valueOf(quantity_str.replace("K", "")) / 1000 > 0.25) {
            quantity = Integer.valueOf(quantity_str.replace(" k", "")) / 1000.0;
            quantity -= 0.25;
            totalPrice=price*quantity;
            return quantity + " k";
        } else {
            return quantity_str;
        }
    }

    public String getTotalPrice() {
        return totalPrice+"LE";
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
