package com.example.mua.provider;

public class Provider {

    String id;
    String name_business;
    String image;
    String address;
    String price;
    String category_id;

    public Provider(String id, String name_business, String image, String address, String price, String category_id) {
        this.id = id;
        this.name_business = name_business;
        this.image = image;
        this.address = address;
        this.price = price;
        this.category_id = category_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName_business() {
        return name_business;
    }

    public void setName_business(String name_business) {
        this.name_business = name_business;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

}
