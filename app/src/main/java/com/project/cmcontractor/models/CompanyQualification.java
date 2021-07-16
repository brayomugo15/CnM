package com.project.cmcontractor.models;

public class CompanyQualification {

    private String companyid, companyinsurancepolicy, companyinsurancetype, companyiso;

    public CompanyQualification(String companyid, String companyinsurancepolicy, String companyinsurancetype, String companyiso) {
        this.companyid = companyid;
        this.companyinsurancepolicy = companyinsurancepolicy;
        this.companyinsurancetype = companyinsurancetype;
        this.companyiso = companyiso;
    }

    public CompanyQualification() {
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public String getCompanyinsurancepolicy() {
        return companyinsurancepolicy;
    }

    public void setCompanyinsurancepolicy(String companyinsurancepolicy) {
        this.companyinsurancepolicy = companyinsurancepolicy;
    }

    public String getCompanyinsurancetype() {
        return companyinsurancetype;
    }

    public void setCompanyinsurancetype(String companyinsurancetype) {
        this.companyinsurancetype = companyinsurancetype;
    }

    public String getCompanyiso() {
        return companyiso;
    }

    public void setCompanyiso(String companyiso) {
        this.companyiso = companyiso;
    }
}
