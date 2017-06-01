package com.baking.www.baking.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baking.www.baking.DataFetchers.dataModels.Recipe;
import com.baking.www.baking.DataFetchers.dataModels.Steps;
import com.baking.www.baking.R;
import com.baking.www.baking.adapters.StepsRecyclerAdapter;
import com.baking.www.baking.utilities.Logging;

import org.json.JSONArray;
import org.json.JSONException;


public class RecipeDetailsFragment extends
        Fragment implements StepsRecyclerAdapter.OnStepClickListener {

    public static final int ITEM_INGREDIENT = 0;
    public static final int ITEM_STEP = 1;
    private static final String RECIPE_KEY = "recipe";
    private static final String INGREDIENTS_KEY = "ingredients";
    private static final String STEPS_KEY = "steps";
    private String mParam1;
    private String steps;
    private String mIngredients;
    private Recipe mRecipe;
    private RecyclerView stepsRV;
    private View v;

    private OnFragmentInteractionListener mListener;
    private Steps mSteps;

    private StepsRecyclerAdapter stepsRecyclerAdapter;

    public RecipeDetailsFragment() {
    }


    public static RecipeDetailsFragment newInstance(Recipe recipe,
                                                    String ingredients, String steps) {
        RecipeDetailsFragment fragment = new RecipeDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(RECIPE_KEY, recipe);
        args.putString(INGREDIENTS_KEY, ingredients);
        args.putString(STEPS_KEY, steps);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(RECIPE_KEY, mRecipe);
        outState.putString(INGREDIENTS_KEY, mIngredients);
        outState.putString(STEPS_KEY, steps);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mRecipe = savedInstanceState.getParcelable(RECIPE_KEY);
            steps = savedInstanceState.getString(STEPS_KEY);
            mIngredients = savedInstanceState.getString(INGREDIENTS_KEY);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_recipe_details, container, false);

        if (savedInstanceState == null) {
            mRecipe = getArguments().getParcelable(RECIPE_KEY);
            steps = getArguments().getString(STEPS_KEY);
            mIngredients = getArguments().getString(INGREDIENTS_KEY);
        }else{
            mRecipe = savedInstanceState.getParcelable(RECIPE_KEY);
            steps= savedInstanceState.getString(STEPS_KEY);
            mIngredients = savedInstanceState.getString(INGREDIENTS_KEY);
        }

        try {
            JSONArray stepssJsonArray = new JSONArray(steps);
            mSteps = new Steps(stepssJsonArray);
        } catch (JSONException e) {
            Logging.log(e.getMessage());
        }
        initViews();
        return v;
    }


    private void initViews() {
        stepsRecyclerAdapter = new StepsRecyclerAdapter(mSteps, this);
        LinearLayoutManager stepsLayoutManager = new LinearLayoutManager(getActivity());

        stepsRV = (RecyclerView) v.findViewById(R.id.rv_steps);
        stepsRV.setLayoutManager(stepsLayoutManager);
        stepsRV.setAdapter(stepsRecyclerAdapter);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onStepItemClick(int itemPosition) {
        if (itemPosition == 0) {
            mListener.onFragmentInteraction(ITEM_INGREDIENT);
        } else {
            mListener.onFragmentInteraction(itemPosition);
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(int itemPosition);
    }
}
