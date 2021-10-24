package com.voda.voda_admin.Model;

import java.util.ArrayList;

public class Order {
    //TODO Order 객체 child 선언

    private String type;
    private String time;
    private String request;
    private ArrayList<String> menus;

    public Order(String type, String time, String request, ArrayList<String> menus) {
        this.type = type;
        this.time = time;
        this.request = request;
        this.menus = menus;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public ArrayList<String> getMenus() {
        return menus;
    }

    public void setMenus(ArrayList<String> menus) {
        this.menus = menus;
    }
}
