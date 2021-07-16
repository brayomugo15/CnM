package com.project.cmcontractor.models;

public class CompanyServices {

    private String companyid;
    private boolean planning, construction, fabrication;

    public CompanyServices(String companyid, boolean planning, boolean construction, boolean fabrication) {
        this.companyid = companyid;
        this.planning = planning;
        this.construction = construction;
        this.fabrication = fabrication;
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public boolean isPlanning() {
        return planning;
    }

    public void setPlanning(boolean planning) {
        this.planning = planning;
    }

    public boolean isConstruction() {
        return construction;
    }

    public void setConstruction(boolean construction) {
        this.construction = construction;
    }

    public boolean isFabrication() {
        return fabrication;
    }

    public void setFabrication(boolean fabrication) {
        this.fabrication = fabrication;
    }
}
