package com.assignment.ticktacktoeassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.view.View;
import android.os.Bundle;

import com.assignment.ticktacktoeassignment.gamescreen.GameScreenFragment;


public class MainActivity extends AppCompatActivity {
    private MenuFragment menuFragment = new MenuFragment();
    private GameScreenFragment gameScreenFragment = new GameScreenFragment();
    private SettingsFragment settingsFragment = new SettingsFragment();
    private StatsFragment statsFragment = new StatsFragment();
    private UserProfileFragment userProfileFragment = new UserProfileFragment();

    private UserProfileTwoFragment userProfileTwoFragment = new UserProfileTwoFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup fragment swapping
        MainActivityData mainActivityDataViewModel = new ViewModelProvider(this).get(MainActivityData.class);
        setupFragmentSwapper(mainActivityDataViewModel);

        // Load the home screen fragment
        loadMenuScreen();
    }

    private void loadMenuScreen() {
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

    private void loadSettingsScreen() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment frag = fm.findFragmentById(R.id.mainMenu_Container);

        View mainMenuContainer = findViewById(R.id.mainMenu_Container);
        if (frag == null) {
            fm.beginTransaction().add(R.id.mainMenu_Container, settingsFragment).commit();
        } else {
            fm.beginTransaction().replace(R.id.mainMenu_Container, settingsFragment).commit();
        }
    }

    private void loadProfileScreen() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment frag = fm.findFragmentById(R.id.mainMenu_Container);

        View mainMenuContainer = findViewById(R.id.mainMenu_Container);
        if (frag == null) {
            fm.beginTransaction().add(R.id.mainMenu_Container, userProfileFragment).commit();
        } else {
            fm.beginTransaction().replace(R.id.mainMenu_Container, userProfileFragment).commit();
        }
    }

    private void loadProfileTwoScreen() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment frag = fm.findFragmentById(R.id.mainMenu_Container);

        View mainMenuContainer = findViewById(R.id.mainMenu_Container);
        if (frag == null) {
            fm.beginTransaction().add(R.id.mainMenu_Container, userProfileTwoFragment).commit();
        } else {
            fm.beginTransaction().replace(R.id.mainMenu_Container, userProfileTwoFragment).commit();
        }
    }

    private void loadStatsScreen() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment frag = fm.findFragmentById(R.id.mainMenu_Container);

        View mainMenuContainer = findViewById(R.id.mainMenu_Container);
        if (frag == null) {
            fm.beginTransaction().add(R.id.mainMenu_Container, statsFragment).commit();
        } else {
            fm.beginTransaction().replace(R.id.mainMenu_Container, statsFragment).commit();
        }
    }

    private void setupFragmentSwapper(MainActivityData mainActivityDataViewModel) {
        mainActivityDataViewModel.clickedValue.observe(this, new Observer<MainActivityData.Fragments>() {
            @Override
            public void onChanged(MainActivityData.Fragments clickedValue) {
                switch (mainActivityDataViewModel.getCurrentFragment()) {
                    case MENU_FRAGMENT:
                        loadMenuScreen();
                        break;
                    case GAME_FRAGMENT:
                        loadGameScreen();
                        break;
                    case SETTINGS_FRAGMENT:
                        loadSettingsScreen();
                        break;
                    case PROFILE_FRAGMENT:
                        loadProfileScreen();
                        break;
                    case STATS_FRAGMENT:
                        loadStatsScreen();
                        break;
                    case PROFILETWO_FRAGMENT:
                        loadProfileTwoScreen();
                        break;
                }
            }
        });
    }
}