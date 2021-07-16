package com.project.cmcontractor.models;

public class CompanyProfile {

    private String companyid, companyname, companyemail, companyimage, companylocation, companyphone;
    private double companyrating, companyratingno;

    public CompanyProfile(String companyid, String companyname, String companyemail, String companyimage, String companylocation, String companyphone, double companyrating, double companyratingno) {
        this.companyid = companyid;
        this.companyname = companyname;
        this.companyemail = companyemail;
        this.companyimage = companyimage;
        this.companylocation = companylocation;
        this.companyphone = companyphone;
        this.companyrating = companyrating;
        this.companyratingno = companyratingno;
    }

    public CompanyProfile() {
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getCompanyemail() {
        return companyemail;
    }

    public void setCompanyemail(String companyemail) {
        this.companyemail = companyemail;
    }

    public String getCompanyimage() {
        return companyimage;
    }

    public void setCompanyimage(String companyimage) {
        this.companyimage = companyimage;
    }

    public String getCompanylocation() {
        return companylocation;
    }

    public void setCompanylocation(String companylocation) {
        this.companylocation = companylocation;
    }

    public String getCompanyphone() {
        return companyphone;
    }

    public void setCompanyphone(String companyphone) {
        this.companyphone = companyphone;
    }

    public double getCompanyrating() {
        return companyrating;
    }

    public void setCompanyrating(double companyrating) {
        this.companyrating = companyrating;
    }

    public double getCompanyratingno() {
        return companyratingno;
    }

    public void setCompanyratingno(double companyratingno) {
        this.companyratingno = companyratingno;
    }
}
