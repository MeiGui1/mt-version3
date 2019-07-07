package com.example.frontend.Models;

import java.io.Serializable;

public class Patient implements Serializable {

    private int id;

    private String shortname;

    private String gender;

    public int getId() {
        return id;
    }

    public String getShortname() {
        return shortname;
    }

    public String getGender() {
        return gender;
    }
}
