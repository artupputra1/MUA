package com.example.mua.provider.detail_provider;

public class ReviewProvider {

    String id;
    String service_id;
    String review;
    String name_user;
    String name_service;

    public ReviewProvider(String id, String service_id, String review, String name_user, String name_service) {
        this.id = id;
        this.service_id = service_id;
        this.review = review;
        this.name_user = name_user;
        this.name_service = name_service;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getName_user() {
        return name_user;
    }

    public void setName_user(String name_user) {
        this.name_user = name_user;
    }

    public String getName_service() {
        return name_service;
    }

    public void setName_service(String name_service) {
        this.name_service = name_service;
    }
}
