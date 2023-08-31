package com.assignment.ticktacktoeassignment;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainActivityData extends ViewModel {
    public MutableLiveData<Integer> clickedValue;
    public MutableLiveData<Boolean> textSet;

    public MainActivityData(){
        clickedValue = new MediatorLiveData<Integer>();
        clickedValue.setValue(0);

    }

    public int getClickedValue(){
        return clickedValue.getValue();
    }
    public void setClickedValue(int value){
        clickedValue.setValue(value);
    }


}
