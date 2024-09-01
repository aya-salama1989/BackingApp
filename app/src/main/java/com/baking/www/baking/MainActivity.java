package com.baking.www.baking;

import android.content.Intent;
import android.os.Bundle;

import com.baking.www.baking.DataFetchers.Fetchers.RecipesDataFetcher;
import com.baking.www.baking.DataFetchers.dataModels.Ingredients;
import com.baking.www.baking.DataFetchers.dataModels.Recipe;
import com.baking.www.baking.DataFetchers.dataModels.Recipes;
import com.baking.www.baking.DataFetchers.dataModels.Steps;
import com.baking.www.baking.adapters.RecipesRecyclerAdapter;
import com.baking.www.baking.utilities.Logging;
import com.google.gson.Gson;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static com.baking.www.baking.utilities.InternetConnectivity.checkOnlineState;


public class MainActivity extends AppCompatActivity
        implements RecipesRecyclerAdapter.OnRecipeItemSelected,
        RecipesDataFetcher.RecipesFetcherDataListener {

    public static String BUNDLE_KEY_RECIPE = "recipe";
    public static String BUNDLE_KEY_INGREDIENTS = "ingredients";
    public static String BUNDLE_KEY_STEPS = "steps";


    public static boolean mTwoPanel;

    private RecipesDataFetcher recipesDataFetcher;

    private RecyclerView recyclerView;
    private RecipesRecyclerAdapter recipesRecyclerAdapter;
    private Recipes mRecipes;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecipes = new Recipes();
        if (savedInstanceState != null) {
            ArrayList<Recipe> parcelableArrayList = savedInstanceState.getParcelableArrayList("mRecipes");
            for (int i = 0; i < parcelableArrayList.size(); i++) {
                mRecipes.add(parcelableArrayList.get(i));
            }
        }
        if (findViewById(R.id.tablet_layout) != null) {
            mTwoPanel = true;
        } else {
            mTwoPanel = false;
        }
        initViews();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        ArrayList<Recipe> parcelableArrayList = new ArrayList<>();
        for (int i = 0; i < mRecipes.size(); i++) {
            parcelableArrayList.add(i, mRecipes.get(i));
        }
        outState.putParcelableArrayList("mRecipes", parcelableArrayList);
        super.onSaveInstanceState(outState);
    }


    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.rv_recipes);
        if (mTwoPanel) {
            layoutManager = new GridLayoutManager(this, 2);
        } else {
            layoutManager = new LinearLayoutManager(this);
        }
        recipesRecyclerAdapter = new RecipesRecyclerAdapter(this, mRecipes);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recipesRecyclerAdapter);
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
        if (!checkOnlineState(this)) {
            Logging.shortToast(this, getString(R.string.internet_error));
        } else {
            Logging.shortToast(this, getString(R.string.server_error));
        }
    }

    private void getData() {
        recipesDataFetcher = new RecipesDataFetcher(this, this);
        recipesDataFetcher.getRecipes(null);
    }

    @Override
    public void onConnectionDone(Recipes recipes) {
        mRecipes.addAll(recipes);
        recipesRecyclerAdapter.notifyDataSetChanged();
    }


    @Override
    public void onItemSelected(int itemPosition) {
        Recipe recipe = mRecipes.get(itemPosition);
        Intent intent = new Intent(this, RecipeDetailsActivity.class);
        Ingredients ingredients = recipe.getIngredients();
        Steps steps = recipe.getSteps();
        intent.putExtra(BUNDLE_KEY_RECIPE, recipe);
        Gson gson = new Gson();
        String ingredientsJson = gson.toJson(ingredients);
        String stepsJson = gson.toJson(steps);
        intent.putExtra(BUNDLE_KEY_INGREDIENTS, ingredientsJson);
        intent.putExtra(BUNDLE_KEY_STEPS, stepsJson);
        startActivity(intent);
    }
}
