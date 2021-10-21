package com.voda.voda_admin;

public class Menu {

    private String name;
    private String category;
    private String explanation;
    private String tag;
    private Integer price;

    public Menu(){ }

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
}
