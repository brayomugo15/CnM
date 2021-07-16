package com.project.cmcontractor.models;

public class Payment {

    private String orderno, orderdate;
    private int cost;

    public Payment(String orderno, String orderdate, int cost) {
        this.orderno = orderno;
        this.orderdate = orderdate;
        this.cost = cost;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
