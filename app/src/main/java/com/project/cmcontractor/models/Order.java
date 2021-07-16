package com.project.cmcontractor.models;

public class Order {

    private String orderno, companyid, planname, email, orderdate, orderno_status, location;
    private int duration, cost;

    public Order() {
    }

    public Order(String orderno, String companyid, String planname, String email, String orderdate, String orderno_status, String location, int duration, int cost) {
        this.orderno = orderno;
        this.companyid = companyid;
        this.planname = planname;
        this.email = email;
        this.orderdate = orderdate;
        this.orderno_status = orderno_status;
        this.location = location;
        this.duration = duration;
        this.cost = cost;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public String getPlanname() {
        return planname;
    }

    public void setPlanname(String planname) {
        this.planname = planname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }

    public String getOrderno_status() {
        return orderno_status;
    }

    public void setOrderno_status(String orderno_status) {
        this.orderno_status = orderno_status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
