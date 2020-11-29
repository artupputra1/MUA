package com.example.mua.review;

public class Review {

    String id;
    String service_id;
    String user_id;
    String user_name;
    String review;
    String rating;
    String image;


    public Review(String id, String service_id, String user_id, String user_name, String review, String rating, String image) {
        this.id = id;
        this.service_id = service_id;
        this.user_id = user_id;
        this.user_name = user_name;
        this.review = review;
        this.rating = rating;
        this.image = image;
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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
