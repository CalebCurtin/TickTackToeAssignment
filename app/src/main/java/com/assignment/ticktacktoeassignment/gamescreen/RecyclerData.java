package com.assignment.ticktacktoeassignment.gamescreen;

import android.util.Log;

public class RecyclerData {
    public int imageID;
    public int x;
    public int y;
    private GameScreenFragment parent;

    public RecyclerData(int imageID, int x, int y, GameScreenFragment parent) {
        this.imageID = imageID;
        this.x = x;
        this.y = y;
        this.parent = parent;
    }

    public void onClick() {
        parent.checkWin(x, y);
    }
}