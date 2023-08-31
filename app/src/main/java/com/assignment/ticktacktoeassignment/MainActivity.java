package com.assignment.ticktacktoeassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.view.View;
import android.os.Bundle;
import android.content.res.Configuration;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {
    private MenuFragment menuFragment = new MenuFragment();
    private GameScreenFragment gameScreenFragment = new GameScreenFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadInitialMenu();
    }

    private void loadInitialMenu() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment frag = fm.findFragmentById(R.id.mainMenu_Container);

        View mainMenuContainer = findViewById(R.id.mainMenu_Container);
        if (frag == null) {
            fm.beginTransaction().add(R.id.mainMenu_Container, menuFragment).commit();
        } else {
            fm.beginTransaction().replace(R.id.mainMenu_Container, menuFragment).commit();
        }
    }

    private void loadGameScreen() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment frag = fm.findFragmentById(R.id.mainMenu_Container);

        View mainMenuContainer = findViewById(R.id.mainMenu_Container);
        if (frag == null) {
            fm.beginTransaction().add(R.id.mainMenu_Container, gameScreenFragment).commit();
        } else {
            fm.beginTransaction().replace(R.id.mainMenu_Container, gameScreenFragment).commit();
        }
    }
}