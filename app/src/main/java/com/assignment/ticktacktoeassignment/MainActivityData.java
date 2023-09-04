package com.assignment.ticktacktoeassignment;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainActivityData extends ViewModel {
    public MutableLiveData<Fragments> clickedValue;
    public MutableLiveData<Boolean> textSet;

    public MainActivityData(){
        clickedValue = new MediatorLiveData<Fragments>();
        clickedValue.setValue(Fragments.MENU_FRAGMENT);

    }

    public Fragments getClickedValue(){
        return clickedValue.getValue();
    }
    public void setClickedValue(Fragments value){
        clickedValue.setValue(value);
    }

    public enum Fragments {
        SETTINGS_FRAGMENT,
        GAME_FRAGMENT,
        PROFILE_FRAGMENT,
        MENU_FRAGMENT;
    }
}
