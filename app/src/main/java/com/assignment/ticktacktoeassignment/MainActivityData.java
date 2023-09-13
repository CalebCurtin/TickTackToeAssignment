package com.assignment.ticktacktoeassignment;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainActivityData extends ViewModel {
    public MutableLiveData<Fragments> clickedValue;
    public int boardSize = 3;
    public int winCondition = 3;
    public boolean xOnPlayer1 = true; // true if player 1 starts with X, false if player 1 starts with O
    public boolean playerTwoIsAI = true;

    public int playerOneAvatar = R.drawable.defaultuser;
    public int playerTwoAvatar = R.drawable.defaultuser;
    public int xMarker = R.drawable.x;
    public int oMarker = R.drawable.o;
    public int playerOneMarker = xMarker;
    public int playerTwoMarker = oMarker;
    public String playerOneName = "Player 1";
    public String playerTwoName = "Player 2";

    public MainActivityData() {
        clickedValue = new MediatorLiveData<Fragments>();
        clickedValue.setValue(Fragments.MENU_FRAGMENT);
    }

    public Fragments getCurrentFragment(){
        return clickedValue.getValue();
    }
    public void changeFragment(Fragments value){
        clickedValue.setValue(value);
    }

    public enum Fragments {
        SETTINGS_FRAGMENT,
        GAME_FRAGMENT,
        PROFILE_FRAGMENT,
        STATS_FRAGMENT,
        MENU_FRAGMENT;
    }
}
