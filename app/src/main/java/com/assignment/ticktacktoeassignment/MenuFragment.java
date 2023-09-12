package com.assignment.ticktacktoeassignment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MenuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuFragment newInstance(String param1, String param2) {
        MenuFragment fragment = new MenuFragment();
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
        // Setup view reference
        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);

        // Setup button references
        Button gameSinglePlayerButton = rootView.findViewById(R.id.start1PlayerButton);
        Button gameTwoPlayerButton = rootView.findViewById(R.id.start2PlayerButton);
        Button settingsButton = rootView.findViewById(R.id.settingsButton);
        Button profileButton = rootView.findViewById(R.id.profileButton);
        Button statsButton = rootView.findViewById(R.id.statsButton);
        // Setup button listeners
        setupListeners(gameSinglePlayerButton, gameTwoPlayerButton, settingsButton, profileButton, statsButton);

        return rootView;
    }

    private void setupListeners(Button gameSinglePlayerButton, Button gameTwoPlayerButton, Button settingsButton, Button profileButton, Button statsButton) {
        MainActivityData mainActivityDataViewModel = new ViewModelProvider(getActivity()).get(MainActivityData.class);
        gameSinglePlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivityDataViewModel.playerTwoIsAI = true;
                mainActivityDataViewModel.changeFragment(MainActivityData.Fragments.GAME_FRAGMENT);
            }
        });

        gameTwoPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivityDataViewModel.playerTwoIsAI = false;
                mainActivityDataViewModel.changeFragment(MainActivityData.Fragments.GAME_FRAGMENT);
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivityDataViewModel.changeFragment(MainActivityData.Fragments.SETTINGS_FRAGMENT);
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivityDataViewModel.changeFragment(MainActivityData.Fragments.PROFILE_FRAGMENT);
            }
        });

        statsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivityDataViewModel.changeFragment(MainActivityData.Fragments.STATS_FRAGMENT);
            }
        });
    }
}