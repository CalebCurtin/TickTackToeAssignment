package com.assignment.ticktacktoeassignment;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.Map;

public class MainActivityData extends ViewModel {
    public MutableLiveData<Fragments> clickedValue;

    // Gameplay settings
    public int boardSize = 3;
    public int winCondition = 3;
    public boolean xOnPlayer1 = true; // true if player 1 starts with X, false if player 1 starts with O
    public boolean playerTwoIsAI = true;

    // Customization settings
    public int playerOneAvatar = R.drawable.defaultuser;
    public int playerTwoAvatar = R.drawable.defaultuser;
    public int xMarker = R.drawable.default_x;      // when making custom X and O markers, set this!!!
    public int oMarker = R.drawable.default_o;      // when making custom X and O markers, set this!!!
    public int playerOneMarker = xMarker;
    public int playerTwoMarker = oMarker;
    public String playerOneName = "Player 1";
    public String playerTwoName = "Player 2";

    // Stats
    private int p1_totalGames = 0;
    private int p2_totalGames = 0;
    private int p1_wins = 0;
    private int p2_wins = 0;
    private int p1_losses = 0;
    private int p2_losses = 0;
    private int p1_winPercent = 0;
    private int p2_winPercent = 0;
    private int p1_draws = 0;
    private int p2_draws = 0;

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

    public void gameEnded(boolean player2Played, int winner) {
        p1_totalGames++;
        switch (winner) {
            case 1: p1_wins++; break;
            case 2: p1_losses++; break;
            case 3: p1_draws++; break;
        }

        if (player2Played) {
            p2_totalGames++;
            switch (winner) {
                case 1: p2_wins++; break;
                case 2: p2_losses++; break;
                case 3: p2_draws++; break;
            }
        }

        if (p1_totalGames > 0) { p1_winPercent = (int)(((float)p1_wins / p1_totalGames) * 100); }
        if (p2_totalGames > 0) { p2_winPercent = (int)(((float)p2_wins / p2_totalGames) * 100); }
    }

    public Map<String, Integer> getStats() {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("p1_totalGames" , p1_totalGames);
        stats.put("p2_totalGames" , p2_totalGames);
        stats.put("p1_wins" , p1_wins);
        stats.put("p2_wins" , p2_wins);
        stats.put("p1_losses" , p1_losses);
        stats.put("p2_losses" , p2_losses);
        stats.put("p1_winPercent" , p1_winPercent);
        stats.put("p2_winPercent" , p2_winPercent);
        stats.put("p1_draws" , p1_draws);
        stats.put("p2_draws" , p2_draws);

        return stats;
    }
}
