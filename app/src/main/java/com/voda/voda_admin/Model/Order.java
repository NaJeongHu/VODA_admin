package com.voda.voda_admin.Model;

public class Order {
    //TODO Order 객체 child 선언

    private String type;
    private String time;
    private String request;

    public Order(String type, String time, String request) {
        this.type = type;
        this.time = time;
        this.request = request;
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
}
