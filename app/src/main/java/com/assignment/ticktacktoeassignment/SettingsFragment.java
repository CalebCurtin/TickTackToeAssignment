package com.assignment.ticktacktoeassignment;

import android.os.Bundle;
import android.widget.Spinner;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Spinner boardSizeSpinner;
    private Spinner winConditionSpinner;
    private Spinner playerMarkersSpinner;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
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
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        boardSizeSpinner = view.findViewById(R.id.BoardSize);
        winConditionSpinner = view.findViewById(R.id.WinCon);
        playerMarkersSpinner = view.findViewById(R.id.Markers);

        populateBoardSizeSpinner();
        populateWinConditionSpinner();
        populatePlayerMarkersSpinner();

        return view;
    }

    private void populateBoardSizeSpinner() {
        String[] boardSizeOptions = {"3x3", "4x4", "5x5"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, boardSizeOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        boardSizeSpinner.setAdapter(adapter);
    }

    private void populateWinConditionSpinner() {
        String[] winConditionOptions = {"3 in a row", "4 in a row", "5 in a row"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, winConditionOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        winConditionSpinner.setAdapter(adapter);
    }

    private void populatePlayerMarkersSpinner()
    {
        String[] playerMarkerOptions = {"X", "O",}; //will implement custom a further date
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, playerMarkerOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        playerMarkersSpinner.setAdapter(adapter);
    }
}