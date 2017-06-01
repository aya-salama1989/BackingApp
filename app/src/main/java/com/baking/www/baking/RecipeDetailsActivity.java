package com.baking.www.baking;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.baking.www.baking.DataFetchers.dataModels.Recipe;
import com.baking.www.baking.DataFetchers.dataModels.Steps;
import com.baking.www.baking.fragments.IngredientsFragment;
import com.baking.www.baking.fragments.RecipeDetailsFragment;
import com.baking.www.baking.fragments.StepDetailsFragment;
import com.baking.www.baking.utilities.Logging;

import org.json.JSONArray;
import org.json.JSONException;

import static com.baking.www.baking.MainActivity.mTwoPanel;
import static com.baking.www.baking.fragments.RecipeDetailsFragment.ITEM_INGREDIENT;

public class RecipeDetailsActivity extends AppCompatActivity
        implements RecipeDetailsFragment.OnFragmentInteractionListener {
    public static final String STEPDETAILSFRAGMENT_TAG = "stepDetailsFragment";
    public static final String INGREDIENTSFRAGMENT_TAG = "ingredientsFragment";
    public static final String RECIPEDETAILSFRAGMENT_TAG = "recipeDetailsFragment";
    private Recipe recipe;
    private String ingredients;
    private String steps;
    private Steps mSteps;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
            recipe = getIntent().getParcelableExtra("recipe");
            ingredients = getIntent().getExtras().getString("ingredients");
            steps = getIntent().getExtras().getString("steps");



        if (savedInstanceState != null) {
            if (fragment instanceof StepDetailsFragment) {
                getSupportFragmentManager().findFragmentByTag(STEPDETAILSFRAGMENT_TAG);
            } else if (fragment instanceof IngredientsFragment) {
                getSupportFragmentManager().findFragmentByTag(INGREDIENTSFRAGMENT_TAG);
            } else if (fragment instanceof RecipeDetailsFragment) {
                getSupportFragmentManager().findFragmentByTag(RECIPEDETAILSFRAGMENT_TAG);
            } else {
                getSupportFragmentManager().findFragmentByTag(RECIPEDETAILSFRAGMENT_TAG);
            }
        } else {
            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(steps);
            } catch (JSONException e) {
                Logging.log(e.getMessage());
            }
            mSteps = new Steps(jsonArray);
            fragment = RecipeDetailsFragment.newInstance(recipe, ingredients, steps);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recipe_details_frag_holder, fragment)
                    .addToBackStack(RECIPEDETAILSFRAGMENT_TAG).commit();
        }

        if (mTwoPanel) {
            fragment = IngredientsFragment.newInstance(ingredients);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.details_frag_holder, fragment, INGREDIENTSFRAGMENT_TAG)
                    .addToBackStack(INGREDIENTSFRAGMENT_TAG).commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (fragment instanceof StepDetailsFragment) {
            getSupportFragmentManager().putFragment(outState, STEPDETAILSFRAGMENT_TAG, fragment);
        } else if (fragment instanceof IngredientsFragment) {
            getSupportFragmentManager().putFragment(outState, INGREDIENTSFRAGMENT_TAG, fragment);
        } else if (fragment instanceof RecipeDetailsFragment) {
            getSupportFragmentManager().putFragment(outState, RECIPEDETAILSFRAGMENT_TAG, fragment);
        }
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onFragmentInteraction(int itemType) {
        if (itemType == ITEM_INGREDIENT) {
            fragment
                    = IngredientsFragment.newInstance(ingredients);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recipe_details_frag_holder, fragment, INGREDIENTSFRAGMENT_TAG)
                    .addToBackStack(INGREDIENTSFRAGMENT_TAG).commit();
        } else {
            fragment
                    = StepDetailsFragment.newInstance(steps, itemType);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recipe_details_frag_holder, fragment, STEPDETAILSFRAGMENT_TAG)
                    .addToBackStack(STEPDETAILSFRAGMENT_TAG).commit();
        }
    }
}
