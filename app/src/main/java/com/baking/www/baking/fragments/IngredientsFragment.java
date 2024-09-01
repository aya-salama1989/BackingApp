package com.baking.www.baking.fragments;

import static com.baking.www.baking.MainActivity.BUNDLE_KEY_INGREDIENTS;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baking.www.baking.DataFetchers.dataModels.Ingredients;
import com.baking.www.baking.R;
import com.baking.www.baking.adapters.IngredientsRecyclerAdapter;

import org.json.JSONArray;
import org.json.JSONException;


public class IngredientsFragment extends Fragment implements  IngredientsRecyclerAdapter.OnIngredientClickListener{

    private View v;
    private Ingredients mIngredients;


    public static androidx.fragment.app.Fragment newInstance(String ingredients) {
        IngredientsFragment fragment = new IngredientsFragment();
        Bundle args = new Bundle();
        args.putString(BUNDLE_KEY_INGREDIENTS, ingredients);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_ingredients, container, false);
        String ingredients = getArguments().getString(BUNDLE_KEY_INGREDIENTS);

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
        IngredientsRecyclerAdapter ingredientsRecyclerAdapter = new IngredientsRecyclerAdapter(this, mIngredients);
        RecyclerView ingredientsRV = v.findViewById(R.id.rv_ingredients);
        ingredientsRV.setLayoutManager(ingredientsLayoutManager);
        ingredientsRV.setAdapter(ingredientsRecyclerAdapter);
    }


    @Override
    public void onIngredientItemClick(int itemPosition) {

    }
}
