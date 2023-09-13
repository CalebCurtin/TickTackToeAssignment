package com.assignment.ticktacktoeassignment.gamescreen;

import android.media.Image;
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
import java.util.Random;

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
    private boolean player1 = true; // these bools should be done better
    private boolean gameActive = true;
    private boolean aiIsActive = false;
    private int boardSize = 3;      // fallback default
    private int goalSize = 3;       // fallback default
    private int moveCount = 0;
    private int[][] board = new int[boardSize][boardSize];
    private ImageView[][] boardImages = new ImageView[boardSize][boardSize];
    private int[] lastMove = new int[2];
    private ImageView lastImageClicked = null;

    private TextView infoText;
    private ImageView playerIndicator;
    private Button rematchButton;
    private Button homeButton;
    private Button menuButton;
    private Button settingsButton;
    private Button undoButton;
    private Button resetButton;
    private View rootView;
    private String player1Name;
    private String player2Name;
    private int player1Avatar;
    private int player2Avatar;
    private int xMarker;
    private int oMarker;

    private ImageView playerAvatar;

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
        player1Name = mainActivityDataViewModel.playerOneName;
        player2Name = mainActivityDataViewModel.playerTwoName;
        player1Avatar = mainActivityDataViewModel.playerOneAvatar;
        player2Avatar = mainActivityDataViewModel.playerTwoAvatar;
        aiIsActive = mainActivityDataViewModel.playerTwoIsAI;
        oMarker = mainActivityDataViewModel.oMarker;
        xMarker = mainActivityDataViewModel.xMarker;

        // Get all the components
        infoText = rootView.findViewById(R.id.gameScreenText);
        playerIndicator = rootView.findViewById(R.id.gameScreenPlayerIndicatorImage);
        playerAvatar = rootView.findViewById(R.id.gameScreenPlayerAvatar);
        rematchButton = rootView.findViewById(R.id.gameScreenRematchButton);
        homeButton = rootView.findViewById(R.id.gameScreenHomeButton);
        menuButton = rootView.findViewById(R.id.gameScreenMenuButton);
        undoButton = rootView.findViewById(R.id.gameScreenUndoButton);
        resetButton = rootView.findViewById(R.id.gameScreenResetButton);
        settingsButton = rootView.findViewById(R.id.gameScreenSettingsButton);


        // Give the buttons functionality
        buildButtons();


        // Setup the game
        restartGame();
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
            // Save some params for undo
            placedSomething = true;
            lastMove[0] = x;
            lastMove[1] = y;
            lastImageClicked = imageView;

            // Place a marker at the clicked location
            imageView.setImageAlpha(255);
            if (placeAnX) {
                imageView.setImageResource(xMarker);
                board[x][y] = 1;
            } else {
                imageView.setImageResource(oMarker);
                board[x][y] = 2;
            }

            // change which players turn it is
            placeAnX = !placeAnX;
            player1 = !player1;

            // print board state to logcat
            logBoardState();

            // check for winner and update the info text
            moveCount++;
            int winner = checkWin(x, y);
            if (winner == 1 || winner == 2) {
                // Someone won
                if (winner == 1) { infoText.setText(player1Name + " won!"); playerAvatar.setImageResource(player1Avatar); }
                if (winner == 2) { infoText.setText(player2Name + " won!"); playerAvatar.setImageResource(player2Avatar); }
                gameActive = false;
                playerIndicator.setVisibility(View.GONE);
                showButtons();
            } else if (winner == 3) {
                // Draw
                gameActive = false;
                infoText.setText("It's a Draw!");
                playerIndicator.setVisibility(View.GONE);
                playerAvatar.setVisibility(View.GONE);
                showButtons();
            } else {
                updatePlayerIndicator();
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
        setPlayerOne();
        playerIndicator.setVisibility(View.VISIBLE);
        rematchButton.setVisibility(View.GONE);
        homeButton.setVisibility(View.GONE);
        board = new int[boardSize][boardSize];
        boardImages = new ImageView[boardSize][boardSize];
        placeAnX = startPlayerIsX;
        player1 = true;
        moveCount = 0;
        setupRecycler(rootView); // TODO: There should be a better way to do this
        updatePlayerIndicator();
        gameActive = true;
    }

    private void buildButtons() {
        menuButton.setOnClickListener(new ChangeScreenOnClickListener(MainActivityData.Fragments.MENU_FRAGMENT));
        settingsButton.setOnClickListener(new ChangeScreenOnClickListener(MainActivityData.Fragments.SETTINGS_FRAGMENT));
        homeButton.setOnClickListener(new ChangeScreenOnClickListener(MainActivityData.Fragments.MENU_FRAGMENT));
        rematchButton.setOnClickListener(new ResetGameOnClickListener());
        resetButton.setOnClickListener(new ResetGameOnClickListener());
        undoButton.setOnClickListener(new UndoLastMoveOnClickListener());
    }

    private void updatePlayerIndicator() {
        if (player1) { setPlayerOne(); }
        else {         setPlayerTwo(); }

        if (placeAnX) { playerIndicator.setImageResource(xMarker); }
        else {          playerIndicator.setImageResource(oMarker); }
    }

    public void playerClickedOn(int x, int y) {
        boolean playerMadeAMove = placeToken(x, y, boardImages[x][y]);
        if (playerMadeAMove) {
            if (aiIsActive) {
                aiMakeMove();
            }
        }
    }

    private void aiMakeMove() {
        // Get all blank spots
        ArrayList<int[]> blankSpots = new ArrayList<>();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j] == 0) {
                    blankSpots.add(new int[] { i, j });
                }
            }
        }
        // Pick a spot to make a move
        Random random = new Random();
        int numBlankSpots = blankSpots.size();
        if (numBlankSpots > 0) {
            int[] pos = blankSpots.get(random.nextInt(blankSpots.size()));
            Log.println(Log.INFO, "santasspy", "AI Placing a token at [" + pos[0] + ", " + pos[1] + "]");
            placeToken(pos[0], pos[1], boardImages[pos[0]][pos[1]]);
            checkWin(pos[0], pos[1]);
        }
    }

    public void addImageViewToBoard(int x, int y, ImageView image) {
        boardImages[x][y] = image;
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

    private class UndoLastMoveOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            board[lastMove[0]][lastMove[1]] = 0;
            if (lastImageClicked != null) {
                lastImageClicked.setImageAlpha(0);
                moveCount--;
                placeAnX = !placeAnX;
                player1 = !player1;
                updatePlayerIndicator();
            }
        }
    }

    private void setPlayerOne() {
        playerAvatar.setImageResource(player1Avatar);
        infoText.setText(player1Name + "'s turn!");
    }

    private void setPlayerTwo() {
        playerAvatar.setImageResource(player2Avatar);
        infoText.setText(player2Name + "'s turn!");
    }
}