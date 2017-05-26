package com.baking.www.baking.fragments;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baking.www.baking.DataFetchers.Fetchers.RecipesDataFetcher;
import com.baking.www.baking.DataFetchers.dataModels.Recipe;
import com.baking.www.baking.DataFetchers.dataModels.Recipes;
import com.baking.www.baking.R;
import com.baking.www.baking.adapters.RecipesRecyclerAdapter;
import com.baking.www.baking.utilities.Logging;
import com.google.gson.Gson;

import static com.baking.www.baking.MainActivity.mTwoPanel;
import static com.baking.www.baking.providers.ContractClass.RecipeEntry.COLUMN_RECIPE_IMAGE;
import static com.baking.www.baking.providers.ContractClass.RecipeEntry.COLUMN_RECIPE_INGREDIENTS;
import static com.baking.www.baking.providers.ContractClass.RecipeEntry.COLUMN_RECIPE_NAME;
import static com.baking.www.baking.providers.ContractClass.RecipeEntry.COLUMN_RECIPE_SERVINGS;
import static com.baking.www.baking.providers.ContractClass.RecipeEntry.COLUMN_RECIPE_STEPS;
import static com.baking.www.baking.providers.ContractClass.RecipeEntry.CONTENT_URI;
import static com.baking.www.baking.utilities.InternetConnectivity.checkOnlineState;


public class MainFragment extends Fragment implements RecipesRecyclerAdapter.OnRecipeItemSelected,
        RecipesDataFetcher.RecipesFetcherDataListener {

    private View v;
    private RecipesDataFetcher recipesDataFetcher;
    private OnFragmentInteractionListener mListener;

    private RecyclerView recyclerView;
    private RecipesRecyclerAdapter recipesRecyclerAdapter;
    private Recipes mRecipes;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_main, container, false);
        mRecipes = new Recipes();
        recyclerView = (RecyclerView) v.findViewById(R.id.rv_recipes);
        if (mTwoPanel) {
            layoutManager = new GridLayoutManager(getActivity(), 2);
        } else {
            layoutManager = new LinearLayoutManager(getActivity());
        }

        recipesRecyclerAdapter = new RecipesRecyclerAdapter(this, mRecipes);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recipesRecyclerAdapter);
        return v;
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
    public void onItemSelected(int itemPosition) {
        mListener.onFragmentInteraction(mRecipes.get(itemPosition), mRecipes.size());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mRecipes.isEmpty()) {
            getData();
        }
    }

    @Override
    public void onConnectionFailure() {
        if (!checkOnlineState(getActivity())) {
            Logging.shortToast(getActivity(), getString(R.string.internet_error));
        } else {
            Logging.shortToast(getActivity(), getString(R.string.server_error));
        }
    }

    private void getData() {
        recipesDataFetcher = new RecipesDataFetcher(getActivity(), this);
        recipesDataFetcher.getRecipes();
    }

    @Override
    public void onConnectionDone(Recipes recipes) {
        mRecipes.addAll(recipes);
        recipesRecyclerAdapter.notifyDataSetChanged();

        //TODO: Do bulk inset in a background thread
        ContentValues[] contentValues = new ContentValues[recipes.size()];
        for (int i = 0; i < recipes.size(); i++) {
            ContentValues contentValue = new ContentValues();
            Gson gson = new Gson();
            String ingredients = gson.toJson(recipes.get(i).getIngredients());
            String steps = gson.toJson(recipes.get(i).getSteps());
            contentValue.put(COLUMN_RECIPE_INGREDIENTS, ingredients);
            contentValue.put(COLUMN_RECIPE_STEPS, steps);
            contentValue.put(COLUMN_RECIPE_SERVINGS, recipes.get(i).getServings());
            contentValue.put(COLUMN_RECIPE_IMAGE, recipes.get(i).getImage());
            contentValue.put(COLUMN_RECIPE_NAME, recipes.get(i).getName());
            contentValues[i] = contentValue;
        }

        getContext().getContentResolver().bulkInsert(CONTENT_URI, contentValues);
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Recipe recipe, int size);
    }
}
