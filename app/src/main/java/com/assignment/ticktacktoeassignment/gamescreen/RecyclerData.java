package com.assignment.ticktacktoeassignment.gamescreen;

import android.util.Log;

public class RecyclerData {
    private int imageID;

    public int getImageID() {
        Log.println(Log.INFO, "TTT", "Got an imageID");
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public RecyclerData(int imageID) {
        this.imageID = imageID;
    }
}