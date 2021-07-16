package com.project.cmcontractor.models;

public class Client {

    private String name, email;
    private int phoneno;

    public Client() {
    }

    public Client(String name, String email, int phoneno) {
        this.name = name;
        this.email = email;
        this.phoneno = phoneno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(int phoneno) {
        this.phoneno = phoneno;
    }
}
