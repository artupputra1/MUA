package com.example.mua.mua.service;

public class MuaServiceProvider {

    String id;
    String service;
    String price;
    String information;
    String duration;

    public MuaServiceProvider(String id, String service, String price, String duration, String information) {
        this.id = id;
        this.service = service;
        this.price = price;
        this.duration = duration;
        this.information = information;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
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
}
