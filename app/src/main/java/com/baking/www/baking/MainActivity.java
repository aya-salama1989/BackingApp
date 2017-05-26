package com.baking.www.baking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.baking.www.baking.DataFetchers.dataModels.Ingredients;
import com.baking.www.baking.DataFetchers.dataModels.Recipe;
import com.baking.www.baking.DataFetchers.dataModels.Steps;
import com.baking.www.baking.fragments.MainFragment;
import com.google.gson.Gson;


public class MainActivity extends AppCompatActivity
        implements MainFragment.OnFragmentInteractionListener {

    public static boolean mTwoPanel;
    private int mSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.tablet_layout) != null) {
            mTwoPanel = true;
        } else {
            mTwoPanel = false;
        }
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setToolBar() {

    }

    private void initViews() {
        setToolBar();
        MainFragment mainFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_frag_holder, mainFragment).commit();
    }


    @Override
    public void onFragmentInteraction(Recipe recipe, int size) {

        if (recipe != null) {
            Intent intent = new Intent(this, RecipeDetailsActivity.class);
            Ingredients ingredients = recipe.getIngredients();
            Steps steps = recipe.getSteps();
            intent.putExtra("recipe", recipe);
            Gson gson = new Gson();
            String ingredientsJson = gson.toJson(ingredients);
            String stepsJson = gson.toJson(steps);
            intent.putExtra("ingredients", ingredientsJson);
            intent.putExtra("steps", stepsJson);
            startActivity(intent);
        }

        mSize = size;

    }


}
