package com.example.jsonapplication;

public class itemModel {

    private String username;
    private String name;
    private String email;
    private String address;
    private int avatar;
    private boolean selected;

    public itemModel(String username,String name , String email, int avatar,String address ) {
        this.username = username;
        this.name= name;
        this.avatar = avatar;
        this.email = email;
        this.address = address;
        this.selected = false;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}