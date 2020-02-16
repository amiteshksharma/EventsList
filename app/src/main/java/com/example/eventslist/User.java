package com.example.eventslist;

public class User {

    private String u, p, id;

    public User() {}

    public User(String u, String p, String id) {
        this.u = u;
        this.p = p;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    public String getU() {
        return u;
    }

    public void setU(String u) {
        this.u = u;
    }
}
