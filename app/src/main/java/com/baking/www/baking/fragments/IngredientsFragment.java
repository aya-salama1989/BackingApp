package com.baking.www.baking.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baking.www.baking.DataFetchers.dataModels.Ingredients;
import com.baking.www.baking.R;
import com.baking.www.baking.adapters.IngredientsRecyclerAdapter;

import org.json.JSONArray;
import org.json.JSONException;


public class IngredientsFragment extends Fragment implements  IngredientsRecyclerAdapter.OnIngredientClickListener{

    private static final String INGREDIENTS_KEY = "ingredients";
    private View v;
    private RecyclerView ingredientsRV;
    private Ingredients mIngredients;
    private IngredientsRecyclerAdapter ingredientsRecyclerAdapter;


    public IngredientsFragment() {
        // Required empty public constructor
    }


    public static IngredientsFragment newInstance(String ingredients) {
        IngredientsFragment fragment = new IngredientsFragment();
        Bundle args = new Bundle();
        args.putString(INGREDIENTS_KEY, ingredients);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_ingredients, container, false);
        String ingredients = getArguments().getString(INGREDIENTS_KEY);

        JSONArray ingredientsJsonArray = null;
        try {
            ingredientsJsonArray = new JSONArray(ingredients);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mIngredients = new Ingredients(ingredientsJsonArray);
        initViews();
        return v;
    }

    private void initViews() {
        LinearLayoutManager ingredientsLayoutManager = new LinearLayoutManager(getActivity());
        ingredientsRecyclerAdapter = new IngredientsRecyclerAdapter(this, mIngredients);
        ingredientsRV = (RecyclerView) v.findViewById(R.id.rv_ingredients);
        ingredientsRV.setLayoutManager(ingredientsLayoutManager);
        ingredientsRV.setAdapter(ingredientsRecyclerAdapter);
    }


    @Override
    public void onIngredientItemClick(int itemPosition) {

    }
}
