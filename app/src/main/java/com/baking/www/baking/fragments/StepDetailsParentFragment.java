package com.baking.www.baking.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.baking.www.baking.DataFetchers.dataModels.Steps;
import com.baking.www.baking.R;
import com.baking.www.baking.adapters.ViewPagerAdapter;
import com.baking.www.baking.utilities.Logging;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import static com.baking.www.baking.MainActivity.mTwoPanel;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


public class StepDetailsParentFragment extends Fragment {

    private static final String ARG_STEP_DATA = "step_data";
    private static final String ARG_STEP_POSITION = "step_position";


    private View v;
    private ViewPager viewPager ;
    private Steps steps;

    public StepDetailsParentFragment() {
        // Required empty public constructor
    }

    public static StepDetailsParentFragment newInstance(String steps, int stepPosition) {
        StepDetailsParentFragment fragment = new StepDetailsParentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_STEP_DATA, steps);
        args.putInt(ARG_STEP_POSITION, stepPosition);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_step_details, container, false);
        initViews();
        return v;
    }

    private void initViews() {
        LinearLayout linearLayout = v.findViewById(R.id.next_prev_steps);
        viewPager = v.findViewById(R.id.vp_steps);
        Button btnNext = v.findViewById(R.id.btn_next);
        Button btnPrevious = v.findViewById(R.id.btn_previous);

        JSONArray jsonArray;

        try {
            jsonArray = new JSONArray(getArguments().getString(ARG_STEP_DATA));
            steps = new Steps(jsonArray);
        } catch (JSONException e) {
            Logging.log(e.getMessage());
        }

        //TODO: send step position
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), fragmentList(steps));
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(getArguments().getInt(ARG_STEP_POSITION));

        if (mTwoPanel) {
            linearLayout.setVisibility(View.GONE);
        } else {
            linearLayout.setVisibility(View.VISIBLE);
            btnNext.setOnClickListener((View v) -> {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            });

            btnPrevious.setOnClickListener((View v) -> {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            });
        }
    }


    private List<Fragment> fragmentList(Steps steps) {
        List<Fragment> list = new ArrayList<>();
        for (int i = 0; i < steps.size(); i++) {
            list.add(StepDetailsChildFragment.newInstance(i, steps.get(i)));
        }
        return list;
    }

}
