package com.example.mua;


// Model Search
public class Search {

    String id;
    String provider_id;
    String name_mua;
    String service;
    String price;
    String information;
    String category_id;

    public Search(String id, String provider_id, String name_mua, String service, String price, String information, String category_id) {
        this.id = id;
        this.provider_id = provider_id;
        this.name_mua = name_mua;
        this.service = service;
        this.price = price;
        this.information = information;
        this.category_id = category_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(String provider_id) {
        this.provider_id = provider_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getName_mua() {
        return name_mua;
    }

    public void setName_mua(String name_mua) {
        this.name_mua = name_mua;
    }
}
