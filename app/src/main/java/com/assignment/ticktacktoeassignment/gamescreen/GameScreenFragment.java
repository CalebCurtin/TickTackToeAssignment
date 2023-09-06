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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
    private boolean placeAnX = true; // fallback default
    private boolean startPlayerIsX;
    private boolean gameActive = true;
    private int boardSize = 3;      // fallback default
    private int goalSize = 3;       // fallback default
    private int moveCount = 0;
    private int[][] board = new int[boardSize][boardSize];

    private TextView infoText;
    private ImageView playerIndicator;
    private Button rematchButton;
    private Button homeButton;
    private Button menuButton;
    private Button settingsButton;
    private Button undoButton;
    private Button resetButton;
    private View rootView;

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
        rootView = inflater.inflate(R.layout.fragment_game_screen, container, false);

        // Get data from settings
        MainActivityData mainActivityDataViewModel = new ViewModelProvider(getActivity()).get(MainActivityData.class);
        boardSize = mainActivityDataViewModel.boardSize;
        goalSize = mainActivityDataViewModel.winCondition;
        startPlayerIsX = mainActivityDataViewModel.xOnPlayer1;

        // Get all the components
        infoText = rootView.findViewById(R.id.gameScreenText);
        playerIndicator = rootView.findViewById(R.id.gameScreenPlayerIndicatorImage);
        rematchButton = rootView.findViewById(R.id.gameScreenRematchButton);
        homeButton = rootView.findViewById(R.id.gameScreenHomeButton);
        menuButton = rootView.findViewById(R.id.gameScreenMenuButton);
        undoButton = rootView.findViewById(R.id.gameScreenUndoButton);
        resetButton = rootView.findViewById(R.id.gameScreenResetButton);
        settingsButton = rootView.findViewById(R.id.gameScreenSettingsButton);

        // Give the buttons functionality
        buildButtons();

        // Setup the game
        board = new int[boardSize][boardSize];
        moveCount = 0;
        placeAnX = startPlayerIsX;
        gameActive = true;

        // Build the game view
        setupRecycler(rootView);

        // if player 1 is using O's we need to change the initial view for the player indicator
        if (!placeAnX) {
            playerIndicator.setImageResource(R.drawable.o);
        }
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
        }

        // Check for a draw
        if (moveCount >= boardSize * boardSize) {
            return 3;
        }

        return 0;
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
    public boolean placeToken(int x, int y, ImageView imageView) {
        boolean placedSomething = false;
        if (gameActive && inBounds(x, y) && board[x][y] == 0) {
            placedSomething = true;
            // Place a marker at the clicked location and update the string
            if (placeAnX) {
                imageView.setImageResource(R.drawable.x);
                playerIndicator.setImageResource(R.drawable.o);
                board[x][y] = 1;
            } else {
                imageView.setImageResource(R.drawable.o);
                playerIndicator.setImageResource(R.drawable.x);
                board[x][y] = 2;
            }

            // change which players turn it is
            placeAnX = !placeAnX;

            // print board state to logcat
            logBoardState();

            // check for winner and update the info text
            moveCount++;
            int winner = checkWin(x, y);
            if (winner == 1 || winner == 2) {
                // Someone won
                infoText.setText("Player " + ((moveCount-1)%2 + 1) + " won!");  // This is incredibly cursed
                playerIndicator.setVisibility(View.GONE);
                gameActive = false;
                showButtons();
            } else if (winner == 3) {
                // Draw
                gameActive = false;
                infoText.setText("Draw!");
                playerIndicator.setVisibility(View.GONE);
                showButtons();
            } else {
                infoText.setText("Player " + (moveCount%2 + 1) + "'s Turn");
            }
        }
        return placedSomething;
    }

    /**
     * Helper function for making sure we don't try and access outside the board array
     * @param x
     * @param y
     * @return true if [x, y] is in bounds, or false otherwise
     */
    private boolean inBounds(int x, int y) { return x >= 0 && x < boardSize && y >= 0 && y < boardSize; }

    private void logBoardState() {
        String debug = "Board State: \n";
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                debug += board[j][i] + " ";
            }
            debug += "\n";
        }
        Log.println(Log.INFO, "TTT", debug);
    }

    private void showButtons() {
        rematchButton.setVisibility(View.VISIBLE);
        homeButton.setVisibility(View.VISIBLE);
    }

    private void restartGame() {
        infoText.setText("Player 1's Turn");
        playerIndicator.setVisibility(View.VISIBLE);
        rematchButton.setVisibility(View.GONE);
        homeButton.setVisibility(View.GONE);
        board = new int[boardSize][boardSize];
        placeAnX = startPlayerIsX;
        moveCount = 0;
        setupRecycler(rootView); // TODO: There should be a better way to do this
        gameActive = true;

        if (placeAnX) {
            playerIndicator.setImageResource(R.drawable.x);
        } else {
            playerIndicator.setImageResource(R.drawable.o);
        }
    }

    private void buildButtons() {
        menuButton.setOnClickListener(new ChangeScreenOnClickListener(MainActivityData.Fragments.MENU_FRAGMENT));
        settingsButton.setOnClickListener(new ChangeScreenOnClickListener(MainActivityData.Fragments.SETTINGS_FRAGMENT));
        homeButton.setOnClickListener(new ChangeScreenOnClickListener(MainActivityData.Fragments.MENU_FRAGMENT));
        rematchButton.setOnClickListener(new ResetGameOnClickListener());
        resetButton.setOnClickListener(new ResetGameOnClickListener());
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: undo move here
            }
        });
    }

    private class ChangeScreenOnClickListener implements View.OnClickListener {
        private final MainActivityData.Fragments targetScreen;

        public ChangeScreenOnClickListener(MainActivityData.Fragments targetScreen) {
            this.targetScreen = targetScreen;
        }
        @Override
        public void onClick(View view) {
            MainActivityData mainActivityDataViewModel = new ViewModelProvider(getActivity()).get(MainActivityData.class);
            mainActivityDataViewModel.changeFragment(targetScreen);
        }
    }

    private class ResetGameOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            restartGame();
        }
    }
}