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
import com.baking.www.baking.widget.GridWidgetService;

public class RecipeDetailsActivity extends AppCompatActivity
        implements RecipeDetailsFragment.OnFragmentInteractionListener {
    public static final String STEP_DETAILS_FRAGMENT_TAG = "stepDetailsFragment";
    public static final String INGREDIENTS_FRAGMENT_TAG = "ingredientsFragment";
    public static final String RECIPE_DETAILS_FRAGMENT_TAG = "recipeDetailsFragment";
    public static final String RECIPE_TITLE = "recipe_title";

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
                String visibleFragment =savedInstanceState.getString("visibleFragment");
                if (visibleFragment.equalsIgnoreCase(STEP_DETAILS_FRAGMENT_TAG)) {
                    getSupportFragmentManager().findFragmentByTag(STEP_DETAILS_FRAGMENT_TAG);
                }

                if (visibleFragment.equalsIgnoreCase(INGREDIENTS_FRAGMENT_TAG)) {
                    getSupportFragmentManager().findFragmentByTag(INGREDIENTS_FRAGMENT_TAG);
                }
            } else {
                getSupportFragmentManager().getFragment(savedInstanceState, RECIPE_DETAILS_FRAGMENT_TAG);
            }
        } else {
            fragment = RecipeDetailsFragment.newInstance(recipe, ingredients, steps);
            replaceFragment(fragment, null, FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            if (mTwoPanel) {
                fragment = IngredientsFragment.newInstance(ingredients);
                replaceTwoPanelFragment(fragment, INGREDIENTS_FRAGMENT_TAG);
            }
        }

    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        if (mTwoPanel) {
            if (fragment instanceof StepDetailsParentFragment) {
                outState.putString("visibleFragment", STEP_DETAILS_FRAGMENT_TAG);
            } else if (fragment instanceof IngredientsFragment) {
                outState.putString("visibleFragment", INGREDIENTS_FRAGMENT_TAG);
            }
        } else {
            if (fragment != null)
                getSupportFragmentManager().putFragment(outState, RECIPE_DETAILS_FRAGMENT_TAG, fragment);
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
            cursor.close();
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
            fragmentTag = INGREDIENTS_FRAGMENT_TAG;
        } else {
            fragment = StepDetailsParentFragment.newInstance(steps, itemType);
            fragmentTag = STEP_DETAILS_FRAGMENT_TAG;
        }

        if (mTwoPanel) {
            replaceTwoPanelFragment(fragment, fragmentTag);
        } else {
            replaceFragment(fragment, fragmentTag, 0);
        }
    }

    private void replaceFragment(Fragment fragment, String tag, int transition) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction().replace(R.id.recipe_details_frag_holder, fragment, tag);
        fragmentTransaction.setTransition((transition == 0) ? FragmentTransaction.TRANSIT_NONE : transition);
        if (tag != null) fragmentTransaction.addToBackStack(STEP_DETAILS_FRAGMENT_TAG);
        fragmentTransaction.commit();
    }

    private void replaceTwoPanelFragment(Fragment fragment,  String tag){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.details_frag_holder, fragment, tag ).commit();
    }

    private void updateWidget(String recipeTitle) {
        Intent intent = new Intent(this, GridWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, R.xml.backing_widget_provider_info);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        intent.putExtra(RECIPE_TITLE, recipeTitle);
        sendBroadcast(intent);
    }
}