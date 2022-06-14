package com.example.recycleapp;

public class recyclebin {
    private int points;
    private int cnt;

    public recyclebin() {
        this.points=0;
        this.cnt=0;
    }

    public void updateBin(int points) {
        this.points =this.points+ points;
        this.cnt++;
    }

    public int getCnt() {
        return cnt;
    }
    public int getPoints() {
        return points;
    }



}


