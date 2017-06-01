package com.baking.www.baking.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.baking.www.baking.DataFetchers.dataModels.Steps;
import com.baking.www.baking.R;
import com.baking.www.baking.adapters.ViewPagerAdapter;
import com.baking.www.baking.utilities.Logging;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StepDetailsFragment extends Fragment {

    private static final String ARG_STEP_DATA = "step_data";
    private static final String ARG_STEP_POSITION = "step_position";

    @BindView(R.id.vp_steps)
    ViewPager viewPager;
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.btn_previous)
    Button btnPrevious;
    private ViewPagerAdapter viewPagerAdapter;
    private View v;

    public StepDetailsFragment() {
        // Required empty public constructor
    }

    public static StepDetailsFragment newInstance(String steps, int stepPosition) {
        StepDetailsFragment fragment = new StepDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_STEP_DATA, steps);
        args.putInt(ARG_STEP_POSITION, stepPosition);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_STEP_DATA);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_step_details, container, false);
        ButterKnife.bind(this, v);
        initViews();
        return v;
    }

   private Steps steps;
    private void initViews() {
        JSONArray jsonArray;

        try {
            jsonArray = new JSONArray(getArguments().getString(ARG_STEP_DATA));
            steps = new Steps(jsonArray);
        } catch (JSONException e) {
            Logging.log(e.getMessage());
        }

        //TODO: send step position
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), fragmentList(steps),
                getArguments().getInt(ARG_STEP_POSITION));
        viewPager.setAdapter(viewPagerAdapter);

        btnNext.setOnClickListener((View v) -> {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        });

        btnPrevious.setOnClickListener((View v) -> {
            viewPager.setCurrentItem(viewPager.getCurrentItem()  + 1);
        });
    }


    private List<Fragment> fragmentList(Steps steps){
        List<Fragment> list = new ArrayList<>();
        for(int i = 0; i<steps.size();i++){
            list.add(StepDetailsFragmentsArray.newInstance(i, steps.get(i)));
        }
        return list;
    }

}
