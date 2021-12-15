package com.voda.voda_admin.Model;

public class Menu {

    private String name;
    private String category;
    private String explanation;
    private String tag;
    private Integer price;
    private String imageurl;

    public Menu(){ }

    public Menu(String name, String category, String explanation, String tag, Integer price, String imageurl) {
        this.name = name;
        this.category = category;
        this.explanation = explanation;
        this.tag = tag;
        this.price = price;
        this.imageurl = imageurl;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }

    public String getExplanation() { return explanation; }

    public void setExplanation(String explanation) { this.explanation = explanation; }

    public String getTag() { return tag; }

    public void setTag(String tag) { this.tag = tag; }

    public Integer getPrice() { return price; }

    public void setPrice(Integer price) { this.price = price; }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
