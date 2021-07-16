package com.project.cmcontractor.models;

public class CompanyProjects {

    private String projectid, companyid, projectphoto;
    private int projectstatus;

    public CompanyProjects(String projectid, String companyid, String projectphoto, int projectstatus) {
        this.projectid = projectid;
        this.companyid = companyid;
        this.projectphoto = projectphoto;
        this.projectstatus = projectstatus;
    }

    public CompanyProjects() {
    }

    public String getProjectid() {
        return projectid;
    }

    public void setProjectid(String projectid) {
        this.projectid = projectid;
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public String getProjectphoto() {
        return projectphoto;
    }

    public void setProjectphoto(String projectphoto) {
        this.projectphoto = projectphoto;
    }

    public int getProjectstatus() {
        return projectstatus;
    }

    public void setProjectstatus(int projectstatus) {
        this.projectstatus = projectstatus;
    }
}
