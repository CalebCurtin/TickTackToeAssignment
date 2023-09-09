package com.assignment.ticktacktoeassignment;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainActivityData extends ViewModel {
    public MutableLiveData<Fragments> clickedValue;
    public int boardSize = 3;
    public int winCondition = 3;
    public boolean xOnPlayer1 = true; // true if player 1 starts with X, false if player 1 starts with O

    public int selectedAvatarImage=0;

    public String playerOneName = "Player 1";

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
