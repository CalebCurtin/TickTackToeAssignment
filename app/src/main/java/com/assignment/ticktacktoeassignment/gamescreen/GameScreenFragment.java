package com.assignment.ticktacktoeassignment.gamescreen;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.assignment.ticktacktoeassignment.MainActivityData;
import com.assignment.ticktacktoeassignment.R;

import java.util.ArrayList;

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
    private int boardSize = 5; // <-- This should be changeable from the settings panel
    private int goalSize = 5; // <-- This should be changeable from the settings panel
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

        //setupListeners(rootView);
        setupRecycler(rootView);
        return rootView;
    }

    /**
     * Check if a player has won.
     * There is almost certainly a better way to do this
     * @return 0 if no win, 1 if player 1 wins, 2 if player 2 wins
     */
    public int checkWin(int posX, int posY) {
        Log.println(Log.INFO, "santasspy", "Checking for win at " + posX + ", " + posY + " with goal size " + goalSize);
        int maxChain = 0;
        int player = board[posX][posY];

        // check vertical line
        for (int y = posY - goalSize; y < posY + goalSize; y++) {
            if (inBounds(posX, y) && board[posX][y] == player) {
                maxChain++;
                if (maxChain >= goalSize) { break; }
            } else {
                maxChain = 0;
            }
        }

        if (maxChain >= 3) {
            return player;
        } else {
            maxChain = 0;
        }

        // check horizontal lines
        for (int x = posX - goalSize; x < posX + goalSize; x++) {
            if (inBounds(x, posY) && board[x][posY] == player) {
                maxChain++;
                if (maxChain >= goalSize) { break; }
            } else {
                maxChain = 0;
            }
        }

        if (maxChain >= 3) {
            return player;
        } else {
            maxChain = 0;
        }
        // Check diagonal lines
        for (int i = -goalSize; i < goalSize; i++) {
            int x = posX + i;
            int y = posY + i;
            if (inBounds(x, y) && board[x][y] == player) {
                maxChain++;
                if (maxChain >= goalSize) { break; }
            } else {
                maxChain = 0;
            }
        }

        if (maxChain >= 3) {
            return player;
        } else {
            maxChain = 0;
        }

        for (int i = -goalSize; i < goalSize; i++) {
            int x = posX - i;
            int y = posY + i;
            if (inBounds(x, y) && board[x][y] == player) {
                maxChain++;
                if (maxChain >= goalSize) { break; }
            } else {
                maxChain = 0;
            }
        }

        if (maxChain >= 3) {
            return player;
        } else {
            maxChain = 0;
        }

        // Check for a draw
        if (moveCount >= boardSize * boardSize) {
            return 3;
        }

        return 0;
    }

    private void loadMainMenu() {
        MainActivityData mainActivityDataViewModel = new ViewModelProvider(getActivity()).get(MainActivityData.class);
        mainActivityDataViewModel.setClickedValue(MainActivityData.Fragments.MENU_FRAGMENT);
    }

    private void setupRecycler(View rootView) {
        RecyclerView recyclerView = rootView.findViewById(R.id.gameRecyclerView);
        ArrayList<RecyclerData> recyclerDataArrayList = new ArrayList<>();

        for (int i = 0; i < boardSize * boardSize; i++) {
            recyclerDataArrayList.add(new RecyclerData(R.drawable.x, i % boardSize, i / boardSize, this));
        }

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), boardSize);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(recyclerDataArrayList, this, layoutManager);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    /**
     * Updates the board and changes the image as required.
     * This is probably bad practice and should be handled within the click listener of the ImageView,
     *  but this seems to co-ordinate them all better so until told otherwise this seems to work
     * @param x
     * @param y
     * @param imageView
     */
    public void placeToken(int x, int y, ImageView imageView) {
        if (inBounds(x, y) && board[x][y] == 0) {
            if (player1) {
                imageView.setImageResource(R.drawable.o);
                board[x][y] = 1;
            } else {
                imageView.setImageResource(R.drawable.x);
                board[x][y] = 2;
            }
            player1 = !player1;

            // print board state to logcat
            String debug = "Board State: \n";
            for (int i = 0; i < boardSize; i++) {
                for (int j = 0; j < boardSize; j++) {
                    debug += board[j][i] + " ";
                }
                debug += "\n";
            }
            Log.println(Log.INFO, "TTT", debug);

            // check for winner
            moveCount++;
            int winner = checkWin(x, y);
            if (winner == 1 || winner == 2) {
                Log.println(Log.INFO, "TTT", "Player " + winner + " won");
                loadMainMenu();
            } else if (winner == 3) {
                Log.println(Log.INFO, "TTT", "It was a draw");
                loadMainMenu();
            }
        }
    }

    /**
     * Helper function for making sure we don't try and access outside the board array
     * @param x
     * @param y
     * @return true if [x, y] is in bounds, or false otherwise
     */
    private boolean inBounds(int x, int y) { return x >= 0 && x < boardSize && y >= 0 && y < boardSize; }
}