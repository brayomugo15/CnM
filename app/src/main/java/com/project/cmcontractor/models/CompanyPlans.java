package com.project.cmcontractor.models;

public class CompanyPlans {

    private String companyid, planname;
    private int plancost, durationest, consultationfee;

    public CompanyPlans(String companyid, String planname, int durationest, int plancost, int consultationfee) {
        this.companyid = companyid;
        this.planname = planname;
        this.durationest = durationest;
        this.plancost = plancost;
        this.consultationfee = consultationfee;
    }

    public CompanyPlans() {
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

    public int getDurationest() {
        return durationest;
    }

    public void setDurationest(int durationest) {
        this.durationest = durationest;
    }

    public int getPlancost() {
        return plancost;
    }

    public void setPlancost(int plancost) {
        this.plancost = plancost;
    }

    public int getConsultationfee() {
        return consultationfee;
    }

    public void setConsultationfee(int consultationfee) {
        this.consultationfee = consultationfee;
    }
}
