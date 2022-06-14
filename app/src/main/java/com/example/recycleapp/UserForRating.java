package com.example.recycleapp;

public class UserForRating {
    private String name;
    private recyclebin bin;

    public UserForRating(String name, recyclebin bin) {
        this.name = name;
        this.bin = bin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public recyclebin getBin() {
        return bin;
    }

    public void setBin(recyclebin bin) {
        this.bin = bin;
    }
}
