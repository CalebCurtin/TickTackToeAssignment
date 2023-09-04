package com.assignment.ticktacktoeassignment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
    private boolean player1 = true;

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
        setupListeners(rootView);
        return rootView;
    }

    /**
     * Adds a click listener to all squares in the grid, which will reveal their image once pressed
     * @param view
     */
    private void setupListeners(View view) {
        addClickListener(view.findViewById(R.id.imageView_topLeft));
        addClickListener(view.findViewById(R.id.imageView_topCenter));
        addClickListener(view.findViewById(R.id.imageView_topRight));

        addClickListener(view.findViewById(R.id.imageView_midLeft));
        addClickListener(view.findViewById(R.id.imageView_midCenter));
        addClickListener(view.findViewById(R.id.imageView_midRight));

        addClickListener(view.findViewById(R.id.imageView_botLeft));
        addClickListener(view.findViewById(R.id.imageView_botCenter));
        addClickListener(view.findViewById(R.id.imageView_botRight));
    }

    private void addClickListener(ImageView segment) {
        segment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                segment.setAlpha(1.0f);
                if (player1) {
                    segment.setImageResource(R.drawable.o);
                }

                player1 = !player1;
            }
        });
    }
}