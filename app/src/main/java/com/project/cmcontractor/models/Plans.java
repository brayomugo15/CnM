package com.project.cmcontractor.models;

public class Plans {

    private String plan_name, plan_type;
    private int plan_image;

    public Plans(String plan_name, String plan_type, int plan_image) {
        this.plan_name = plan_name;
        this.plan_type = plan_type;
        this.plan_image = plan_image;
    }

    public Plans() {
    }

    public String getPlan_name() {
        return plan_name;
    }

    public void setPlan_name(String plan_name) {
        this.plan_name = plan_name;
    }

    public String getPlan_type() {
        return plan_type;
    }

    public void setPlan_type(String plan_type) {
        this.plan_type = plan_type;
    }

    public int getPlan_image() {
        return plan_image;
    }

    public void setPlan_image(int plan_image) {
        this.plan_image = plan_image;
    }
}
