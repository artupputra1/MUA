package com.example.mua.booking;

public class Booking {
    String id;
    String service_id;
    String service_name;
    String mua_name;
    String user_id;
    String date;
    String time;
    String customer_name;
    String phone;
    String amount_person;
    String address;
    String total_price;
    String payment_proof;
    String status;

    public Booking(String id, String service_id, String service_name, String mua_name, String user_id, String date, String time, String customer_name, String phone, String amount_person, String address, String total_price, String payment_proof, String status) {
        this.id = id;
        this.service_id = service_id;
        this.service_name = service_name;
        this.mua_name = mua_name;
        this.user_id = user_id;
        this.date = date;
        this.time = time;
        this.customer_name = customer_name;
        this.phone = phone;
        this.amount_person = amount_person;
        this.address = address;
        this.total_price = total_price;
        this.payment_proof = payment_proof;
        this.status = status;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAmount_person() {
        return amount_person;
    }

    public void setAmount_person(String amount_person) {
        this.amount_person = amount_person;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getPayment_proof() {
        return payment_proof;
    }

    public void setPayment_proof(String payment_proof) {
        this.payment_proof = payment_proof;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getMua_name() {
        return mua_name;
    }

    public void setMua_name(String mua_name) {
        this.mua_name = mua_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
