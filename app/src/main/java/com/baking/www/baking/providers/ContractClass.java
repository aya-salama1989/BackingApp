package com.baking.www.baking.providers;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

import static com.baking.www.baking.providers.ContractClass.RecipeEntry.COLUMN_RECIPE_ID;
import static com.baking.www.baking.providers.ContractClass.RecipeEntry.COLUMN_RECIPE_IMAGE;
import static com.baking.www.baking.providers.ContractClass.RecipeEntry.COLUMN_RECIPE_INGREDIENTS;
import static com.baking.www.baking.providers.ContractClass.RecipeEntry.COLUMN_RECIPE_NAME;
import static com.baking.www.baking.providers.ContractClass.RecipeEntry.COLUMN_RECIPE_SERVINGS;
import static com.baking.www.baking.providers.ContractClass.RecipeEntry.COLUMN_RECIPE_STEPS;
import static com.baking.www.baking.providers.ContractClass.RecipeEntry.TABLE_NAME;

/**
 * Created by Dell on 25/05/2017.
 */

public class ContractClass {

    public static final String AUTHORITY = "com.baking.www.baking";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_RECIPES = "recipes";

    public static final long INVALID_RECIPE_ID = -1;

    public static final String CREATE_TABLA_RECIPES = "create table " + TABLE_NAME + "("
            + COLUMN_RECIPE_ID + " INTEGER, " + COLUMN_RECIPE_NAME + " TEXT, "
            + COLUMN_RECIPE_INGREDIENTS + " TEXT, " + COLUMN_RECIPE_STEPS + " TEXT, "
            + COLUMN_RECIPE_SERVINGS + " INTEGER, " + COLUMN_RECIPE_IMAGE + " TEXT);";
    public static final String EXTRA_RECIPE_ID = "extra_recipe_id";

    public static final class RecipeEntry implements BaseColumns {

        public static final Uri RECIPES_CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_RECIPES).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "/" + PATH_RECIPES;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + AUTHORITY + "/" + PATH_RECIPES;

        public static final String TABLE_NAME = "recipes";
        public static final String COLUMN_RECIPE_ID = "recipeID";

        public static final String COLUMN_RECIPE_NAME = "recipeName";
        public static final String COLUMN_RECIPE_INGREDIENTS = "recipeIngredients";
        public static final String COLUMN_RECIPE_STEPS= "recipeSteps";
        public static final String COLUMN_RECIPE_SERVINGS= "recipeServings";
        public static final String COLUMN_RECIPE_IMAGE= "recipeImage";
    }
}
