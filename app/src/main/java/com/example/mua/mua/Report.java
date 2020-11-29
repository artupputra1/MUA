package com.example.mua.mua;

public class Report {

    String month;
    String total;

    public Report(String month, String total) {
        this.month = month;
        this.total = total;
    }


    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
