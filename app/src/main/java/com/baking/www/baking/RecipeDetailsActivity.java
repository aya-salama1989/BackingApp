package com.baking.www.baking;

import static com.baking.www.baking.MainActivity.BUNDLE_KEY_INGREDIENTS;
import static com.baking.www.baking.MainActivity.BUNDLE_KEY_RECIPE;
import static com.baking.www.baking.MainActivity.BUNDLE_KEY_STEPS;
import static com.baking.www.baking.MainActivity.mTwoPanel;
import static com.baking.www.baking.fragments.RecipeDetailsFragment.ITEM_INGREDIENT;
import static com.baking.www.baking.providers.ContractClass.RecipeEntry.COLUMN_RECIPE_ID;
import static com.baking.www.baking.providers.ContractClass.RecipeEntry.COLUMN_RECIPE_IMAGE;
import static com.baking.www.baking.providers.ContractClass.RecipeEntry.COLUMN_RECIPE_INGREDIENTS;
import static com.baking.www.baking.providers.ContractClass.RecipeEntry.COLUMN_RECIPE_NAME;
import static com.baking.www.baking.providers.ContractClass.RecipeEntry.COLUMN_RECIPE_SERVINGS;
import static com.baking.www.baking.providers.ContractClass.RecipeEntry.COLUMN_RECIPE_STEPS;
import static com.baking.www.baking.providers.ContractClass.RecipeEntry.RECIPES_CONTENT_URI;

import android.appwidget.AppWidgetManager;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.baking.www.baking.DataFetchers.dataModels.Recipe;
import com.baking.www.baking.fragments.IngredientsFragment;
import com.baking.www.baking.fragments.RecipeDetailsFragment;
import com.baking.www.baking.fragments.StepDetailsParentFragment;
import com.baking.www.baking.widget.GridWidgetservice;

public class RecipeDetailsActivity extends AppCompatActivity
        implements RecipeDetailsFragment.OnFragmentInteractionListener {
    public static final String STEPDETAILSFRAGMENT_TAG = "stepDetailsFragment";
    public static final String INGREDIENTSFRAGMENT_TAG = "ingredientsFragment";
    public static final String RECIPEDETAILSFRAGMENT_TAG = "recipeDetailsFragment";

    private Recipe recipe;
    private String ingredients;
    private String steps;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        recipe = getIntent().getParcelableExtra(BUNDLE_KEY_RECIPE);
        ingredients = getIntent().getExtras().getString(BUNDLE_KEY_INGREDIENTS);
        steps = getIntent().getExtras().getString(BUNDLE_KEY_STEPS);

        if (savedInstanceState != null) {
            if (mTwoPanel) {
                if (savedInstanceState.getString("visibleFragment").equalsIgnoreCase(STEPDETAILSFRAGMENT_TAG)) {
                    getSupportFragmentManager().findFragmentByTag(STEPDETAILSFRAGMENT_TAG);
                } else if (savedInstanceState.getString("visibleFragment").equalsIgnoreCase(INGREDIENTSFRAGMENT_TAG)) {
                    getSupportFragmentManager().findFragmentByTag(INGREDIENTSFRAGMENT_TAG);
                }
            } else {
                getSupportFragmentManager().getFragment(savedInstanceState, RECIPEDETAILSFRAGMENT_TAG);
            }
        } else {
//            JSONArray jsonArray = null;
//            try {
//                jsonArray = new JSONArray(steps);
//            } catch (JSONException e) {
//                Logging.log(e.getMessage());
//            }
//
//            Steps mSteps = new Steps(jsonArray);

            fragment = RecipeDetailsFragment.newInstance(recipe, ingredients, steps);
            getSupportFragmentManager().beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .replace(R.id.recipe_details_frag_holder, fragment)
                    .commit();

            if (mTwoPanel) {
                fragment = IngredientsFragment.newInstance(ingredients);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.details_frag_holder, fragment, INGREDIENTSFRAGMENT_TAG)
                        .commit();
            }
        }

    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        if (mTwoPanel) {
            if (fragment instanceof StepDetailsParentFragment) {
                outState.putString("visibleFragment", STEPDETAILSFRAGMENT_TAG);
            } else if (fragment instanceof IngredientsFragment) {
                outState.putString("visibleFragment", INGREDIENTSFRAGMENT_TAG);
            }
        } else {
            if (fragment != null)
                getSupportFragmentManager().putFragment(outState, RECIPEDETAILSFRAGMENT_TAG, fragment);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Cursor cursor = getContentResolver().query(RECIPES_CONTENT_URI, null, null, null, null);
            ContentValues contentValue = new ContentValues();
            contentValue.put(COLUMN_RECIPE_INGREDIENTS, ingredients);
            contentValue.put(COLUMN_RECIPE_STEPS, steps);
            contentValue.put(COLUMN_RECIPE_ID, recipe.getId());
            contentValue.put(COLUMN_RECIPE_SERVINGS, recipe.getServings());
            contentValue.put(COLUMN_RECIPE_IMAGE, recipe.getImage());
            contentValue.put(COLUMN_RECIPE_NAME, recipe.getName());
            if (cursor.getCount() == 0) {
                getContentResolver().insert(RECIPES_CONTENT_URI, contentValue);
            } else {
                getContentResolver()
                        .update(RECIPES_CONTENT_URI.buildUpon().appendPath(String.valueOf(0)).build(),
                                contentValue, null, null);
                updateWidget(recipe.getName());
            }
        } else {
            throw new UnsupportedOperationException("no such operation");
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onFragmentInteraction(int itemType) {
        Fragment fragment;
        String fragmentTag;
        if (itemType == ITEM_INGREDIENT) {
            fragment = IngredientsFragment.newInstance(ingredients);
            fragmentTag = INGREDIENTSFRAGMENT_TAG;
        } else {
            fragment = StepDetailsParentFragment.newInstance(steps, itemType);
            fragmentTag = STEPDETAILSFRAGMENT_TAG;
        }

        if (mTwoPanel) {
            replaceTwoPanelFragment(fragment, fragmentTag);
        } else {
            replaceFragment(fragment, fragmentTag);
        }
    }

    private void replaceFragment(Fragment fragment, String tag){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction
                .replace(R.id.recipe_details_frag_holder, fragment, tag)
                .setTransition(FragmentTransaction.TRANSIT_NONE)
                .addToBackStack(STEPDETAILSFRAGMENT_TAG).commit();
    }

    private void replaceTwoPanelFragment(Fragment fragment,  String tag){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.details_frag_holder, fragment, tag ).commit();
    }

    private void updateWidget(String recipeTitle) {
        Intent intent = new Intent(this, GridWidgetservice.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, R.xml.backing_widget_provider_info);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        intent.putExtra("recipe_title", recipeTitle);
        sendBroadcast(intent);
    }
}
