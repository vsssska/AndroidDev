package com.example.lab5;

public class Location {
    private String name;
    private String adress;

    public Location(String name, String adress) {
        this.name = name;
        this.adress = adress;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return adress;
    }
}
