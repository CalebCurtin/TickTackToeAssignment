package com.assignment.ticktacktoeassignment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import com.assignment.ticktacktoeassignment.gamescreen.RecyclerData;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserProfileTwoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserProfileTwoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private MainActivityData viewModel;

    private Button backButton;

    private EditText playerName;

    private RecyclerView rv;


    public UserProfileTwoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_user_profile2.
     */
    // TODO: Rename and change types and number of parameters
    public static UserProfileTwoFragment newInstance(String param1, String param2) {
        UserProfileTwoFragment fragment = new UserProfileTwoFragment();
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
        View view = inflater.inflate(R.layout.fragment_user_profile2, container, false);

        playerName = view.findViewById(R.id.profile_editText);
        backButton = view.findViewById(R.id.profile_backBtn);
        viewModel = new ViewModelProvider(requireActivity()).get(MainActivityData.class);

       ArrayList<RecyclerData> recyclerDataArrayList = new ArrayList<>();
        RecyclerView rv = view.findViewById(R.id.avatarRecView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);

        rv.setLayoutManager(gridLayoutManager);

        ArrayList<AvatarData> avatarDataList = generateAvatarDataList();

        AvatarRecyclerViewAdapter adapter = new AvatarRecyclerViewAdapter(avatarDataList, viewModel, this);
        rv.setAdapter(adapter);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivityData viewModel = new ViewModelProvider(requireActivity()).get(MainActivityData.class);
                viewModel.playerTwoName = playerName.getText().toString();
                viewModel.changeFragment(MainActivityData.Fragments.MENU_FRAGMENT);
            }

        });
        return view;
    }


    private ArrayList<AvatarData> generateAvatarDataList() {
        ArrayList<AvatarData> avatarDataList = new ArrayList<>();
        avatarDataList.add(new AvatarData("koala"));
        avatarDataList.add(new AvatarData("dog"));
        avatarDataList.add(new AvatarData("cat"));
        avatarDataList.add(new AvatarData("pufferfish"));
        avatarDataList.add(new AvatarData("shark"));
        avatarDataList.add(new AvatarData("lion"));
        avatarDataList.add(new AvatarData("bear"));
        avatarDataList.add(new AvatarData("panda"));
        avatarDataList.add(new AvatarData("rabbit"));
        return avatarDataList;
    }
}

