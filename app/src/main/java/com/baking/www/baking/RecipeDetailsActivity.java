package com.baking.www.baking;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.baking.www.baking.DataFetchers.dataModels.Recipe;
import com.baking.www.baking.fragments.RecipeDetailsFragment;

public class RecipeDetailsActivity extends AppCompatActivity
        implements RecipeDetailsFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        //TODO: it starts nullifying here
        Recipe recipe = getIntent().getParcelableExtra("recipe");
        RecipeDetailsFragment recipeDetailsFragment = RecipeDetailsFragment.newInstance(recipe);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.details_frag_holder, recipeDetailsFragment).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
