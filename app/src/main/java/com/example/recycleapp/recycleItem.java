package com.example.recycleapp;

public class recycleItem {
    private String name;
    private int value;
    private String picname;
    private String kindOfBin;

    public recycleItem(String name, int value, String kindOfBin, String picname) {
        this.name = name;
        this.value = value;
        this.kindOfBin = kindOfBin;
        this.picname = picname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getPicname() {
        return picname;
    }

    public void setPicname(String picname) {
        this.picname = picname;
    }

    public String getKindOfBin() {
        return kindOfBin;
    }

    public void setKindOfBin(String kindOfBin) {
        this.kindOfBin = kindOfBin;
    }
}