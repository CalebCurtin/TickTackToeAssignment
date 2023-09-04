package com.assignment.ticktacktoeassignment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.logging.Logger;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameScreenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameScreenFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Game Params
    private boolean player1 = true;
    private int boardSize = 3; // <-- This should be changeable from the settings panel
    private int moveCount = 0;
    private int[][] board = new int[boardSize][boardSize];

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GameScreenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GameScreenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GameScreenFragment newInstance(String param1, String param2) {
        GameScreenFragment fragment = new GameScreenFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_game_screen, container, false);
        board = new int[boardSize][boardSize];
        moveCount = 0;

        setupListeners(rootView);
        return rootView;
    }

    /**
     * Adds a click listener to all squares in the grid, which will reveal their image once pressed
     * @param view
     */
    private void setupListeners(View view) {
        addClickListener(view.findViewById(R.id.imageView_topLeft), 0, 0);
        addClickListener(view.findViewById(R.id.imageView_topCenter), 0, 1);
        addClickListener(view.findViewById(R.id.imageView_topRight), 0, 2);

        addClickListener(view.findViewById(R.id.imageView_midLeft), 1, 0);
        addClickListener(view.findViewById(R.id.imageView_midCenter), 1, 1);
        addClickListener(view.findViewById(R.id.imageView_midRight), 1, 2);

        addClickListener(view.findViewById(R.id.imageView_botLeft), 2, 0);
        addClickListener(view.findViewById(R.id.imageView_botCenter), 2, 1);
        addClickListener(view.findViewById(R.id.imageView_botRight), 2, 2);
    }

    private void addClickListener(ImageView segment, int x, int y) {
        segment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (board[x][y] == 0) {

                    // reveal the square and change the marker
                    segment.setAlpha(1.0f);
                    if (player1) {
                        segment.setImageResource(R.drawable.o);
                        board[x][y] = 1;
                    } else {
                        segment.setImageResource(R.drawable.x);
                        board[x][y] = 2;
                    }

                    // update variables
                    player1 = !player1;
                    moveCount++;

                    // print board state to logcat
                    String debug = "Board State: \n";
                    for (int i = 0; i < boardSize; i++) {
                        for (int j = 0; j < boardSize; j++) {
                            debug += board[i][j] + " ";
                        }
                        debug += "\n";
                    }
                    Log.println(Log.INFO, "TTT", debug);

                    // check for winner
                    int winner = checkWin();
                    if (winner == 1 || winner == 2) {
                        Log.println(Log.INFO, "TTT", "Player " + winner + " won");
                        loadMainMenu();
                    } else if (winner == 3) {
                        Log.println(Log.INFO, "TTT", "It was a draw");
                        loadMainMenu();
                    }
                }
            }
        });
    }

    /**
     * Check if a player has won.
     * There is almost certainly a better way to do this
     * @return 0 if no win, 1 if player 1 wins, 2 if player 2 wins
     */
    private int checkWin() {
        // Check for a draw
        if (moveCount >= 9) {
            return 3;
        }

        // check vertical lines
        for (int x = 0; x < boardSize; x++) {
            if (board[x][0] != 0 && board[x][1] == board[x][0] && board[x][2] == board[x][0]) {
                return board[x][0];
            }
        }

        // check horizontal lines
        for (int y = 0; y < boardSize; y++) {
            if (board[0][y] != 0 && board[1][y] == board[0][y] && board[2][y] == board[0][y]) {
                return board[0][y];
            }
        }

        // Check diagonal lines
        if (board[0][0] != 0 && board[1][1] == board[0][0] && board[2][2] == board[0][0]) {
            return board[0][0];
        }
        if (board[0][2] != 0 && board[1][1] == board[0][2] && board[2][0] == board[0][2]) {
            return board[0][2];
        }

        return 0;
    }

    private void loadMainMenu() {
        MainActivityData mainActivityDataViewModel = new ViewModelProvider(getActivity()).get(MainActivityData.class);
        mainActivityDataViewModel.setClickedValue(MainActivityData.Fragments.MENU_FRAGMENT);
    }
}