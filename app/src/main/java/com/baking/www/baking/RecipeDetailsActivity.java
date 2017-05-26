package com.baking.www.baking;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.baking.www.baking.DataFetchers.dataModels.Recipe;
import com.baking.www.baking.DataFetchers.dataModels.Steps;
import com.baking.www.baking.fragments.IngredientsFragment;
import com.baking.www.baking.fragments.RecipeDetailsFragment;
import com.baking.www.baking.fragments.StepDetailsFragment;

import org.json.JSONArray;
import org.json.JSONException;

import static com.baking.www.baking.MainActivity.mTwoPanel;
import static com.baking.www.baking.fragments.RecipeDetailsFragment.ITEM_INGREDIENT;

public class RecipeDetailsActivity extends AppCompatActivity
        implements RecipeDetailsFragment.OnFragmentInteractionListener {
    private Recipe recipe;
    private String ingredients;
    private String steps;
    private Steps mSteps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        recipe = getIntent().getParcelableExtra("recipe");
        ingredients = getIntent().getExtras().getString("ingredients");
        steps = getIntent().getExtras().getString("steps");

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(steps);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mSteps = new Steps(jsonArray);

        RecipeDetailsFragment recipeDetailsFragment
                = RecipeDetailsFragment.newInstance(recipe, ingredients, steps);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recipe_details_frag_holder, recipeDetailsFragment).commit();
        if (mTwoPanel) {
            IngredientsFragment ingredientsFragment
                    = IngredientsFragment.newInstance(ingredients);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.details_frag_holder, ingredientsFragment).commit();
        }else {

        }
    }

    @Override
    public void onFragmentInteraction(int itemType) {
        if (itemType == ITEM_INGREDIENT) {
            IngredientsFragment ingredientsFragment
                    = IngredientsFragment.newInstance(ingredients);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recipe_details_frag_holder, ingredientsFragment).commit();
        } else {

            StepDetailsFragment stepDetailsFragment
                    = StepDetailsFragment.newInstance(mSteps.get(itemType));
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recipe_details_frag_holder, stepDetailsFragment).commit();


        }
    }
}
