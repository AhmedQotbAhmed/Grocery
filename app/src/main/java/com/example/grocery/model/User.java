package com.example.grocery.model;

public class User {

    private String fname,lname,email,password,mobile;

    public User(String fname, String lname, String email, String password, String mobile) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getMobile() {
        return mobile;
    }
}
