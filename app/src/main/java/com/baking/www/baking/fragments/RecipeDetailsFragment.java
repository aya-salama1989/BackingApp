package com.baking.www.baking.fragments;

import static com.baking.www.baking.MainActivity.BUNDLE_KEY_INGREDIENTS;
import static com.baking.www.baking.MainActivity.BUNDLE_KEY_RECIPE;
import static com.baking.www.baking.MainActivity.BUNDLE_KEY_STEPS;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private String steps;
    private String mIngredients;
    private Recipe mRecipe;
    private View v;

    private OnFragmentInteractionListener mListener;
    private Steps mSteps;

    public static RecipeDetailsFragment newInstance(Recipe recipe,
                                                    String ingredients, String steps) {
        RecipeDetailsFragment fragment = new RecipeDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(BUNDLE_KEY_RECIPE, recipe);
        args.putString(BUNDLE_KEY_INGREDIENTS, ingredients);
        args.putString(BUNDLE_KEY_STEPS, steps);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(BUNDLE_KEY_RECIPE, mRecipe);
        outState.putString(BUNDLE_KEY_INGREDIENTS, mIngredients);
        outState.putString(BUNDLE_KEY_STEPS, steps);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mRecipe = savedInstanceState.getParcelable(BUNDLE_KEY_RECIPE);
            steps = savedInstanceState.getString(BUNDLE_KEY_STEPS);
            mIngredients = savedInstanceState.getString(BUNDLE_KEY_INGREDIENTS);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_recipe_details, container, false);

        Bundle bundle = (savedInstanceState == null) ? getArguments() : savedInstanceState;
        mRecipe = bundle.getParcelable(BUNDLE_KEY_RECIPE);
        steps = bundle.getString(BUNDLE_KEY_STEPS);
        mIngredients = bundle.getString(BUNDLE_KEY_INGREDIENTS);
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
        StepsRecyclerAdapter stepsRecyclerAdapter = new StepsRecyclerAdapter(mSteps, this);
        LinearLayoutManager stepsLayoutManager = new LinearLayoutManager(getActivity());
        RecyclerView stepsRV = v.findViewById(R.id.rv_steps);
        stepsRV.setLayoutManager(stepsLayoutManager);
        stepsRV.setAdapter(stepsRecyclerAdapter);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context
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
        int pos = (itemPosition == 0)? ITEM_INGREDIENT : itemPosition;
        mListener.onFragmentInteraction(pos);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(int itemPosition);
    }
}
