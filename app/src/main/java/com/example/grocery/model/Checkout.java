package com.example.grocery.model;

public class Checkout {

    private String title;
    private String sub1;
    private String sub2;
    private int icon_tag;
    // State of the item
    private boolean expanded;

    public Checkout(String title, String sub1, String sub2,int icon_tag) {
        this.title = title;
        this.icon_tag=icon_tag;
        this.sub1 = sub1;
        this.sub2 = sub2;

    }

    public String getTitle() {
        return title;
    }

    public String getSub1() {
        return sub1;
    }

    public String getSub2() {
        return sub2;
    }
    public int getIcon_tag() {
        return icon_tag;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public boolean isExpanded() {
        return expanded;
    }
}