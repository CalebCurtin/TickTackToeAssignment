package com.assignment.ticktacktoeassignment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StatsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StatsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StatsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatsFragment newInstance(String param1, String param2) {
        StatsFragment fragment = new StatsFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats, container, false);

        fillStats(view);

        Button MenuButton = view.findViewById(R.id.Menu);
        MenuButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                // Get the view model
                MainActivityData viewModel = new ViewModelProvider(requireActivity()).get(MainActivityData.class);
                // Navigate back to the menu fragment
                viewModel.changeFragment(MainActivityData.Fragments.MENU_FRAGMENT);
            }
        });
        return view;
    }

    private void fillStats(View view) {
        // Get the stats
        MainActivityData data = new ViewModelProvider(requireActivity()).get(MainActivityData.class);
        Map<String, Integer> stats = data.getStats();

        // Set the stats
        ((TextView)view.findViewById(R.id.stat_p1_totalgames)).setText(String.valueOf(stats.get("p1_totalGames")));
        ((TextView)view.findViewById(R.id.stat_p2_totalgames)).setText(String.valueOf(stats.get("p2_totalGames")));
        ((TextView)view.findViewById(R.id.stat_p1_wins)).setText(String.valueOf(stats.get("p1_wins")));
        ((TextView)view.findViewById(R.id.stat_p2_wins)).setText(String.valueOf(stats.get("p2_wins")));
        ((TextView)view.findViewById(R.id.stat_p1_losses)).setText(String.valueOf(stats.get("p1_losses")));
        ((TextView)view.findViewById(R.id.stat_p2_losses)).setText(String.valueOf(stats.get("p2_losses")));
        ((TextView)view.findViewById(R.id.stat_p1_draws)).setText(String.valueOf(stats.get("p1_draws")));
        ((TextView)view.findViewById(R.id.stat_p2_draws)).setText(String.valueOf(stats.get("p2_draws")));
        ((TextView)view.findViewById(R.id.stat_p1_winpercent)).setText(String.valueOf(stats.get("p1_winPercent")) + "%");
        ((TextView)view.findViewById(R.id.stat_p2_winpercent)).setText(String.valueOf(stats.get("p2_winPercent")) + "%");

    }
}