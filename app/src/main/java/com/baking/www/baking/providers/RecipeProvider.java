package com.baking.www.baking.providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.baking.www.baking.providers.ContractClass.AUTHORITY;
import static com.baking.www.baking.providers.ContractClass.INVALID_RECIPE_ID;
import static com.baking.www.baking.providers.ContractClass.PATH_RECIPES;
import static com.baking.www.baking.providers.ContractClass.RecipeEntry.COLUMN_RECIPE_ID;
import static com.baking.www.baking.providers.ContractClass.RecipeEntry.CONTENT_ITEM_TYPE;
import static com.baking.www.baking.providers.ContractClass.RecipeEntry.CONTENT_TYPE;
import static com.baking.www.baking.providers.ContractClass.RecipeEntry.TABLE_NAME;
import static com.baking.www.baking.providers.DBHelper.getDataBaseInstance;

/**
 * Created by Dell on 25/05/2017.
 */

public class RecipeProvider extends ContentProvider {

    public static final int RECIPES = 100;
    public static final int RECIPE_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private DBHelper recipeDBHelper;

    // Define a static buildUriMatcher method that associates URI's with their int match
    public static UriMatcher buildUriMatcher() {
        // Initialize a UriMatcher
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        // Add URI matches
        uriMatcher.addURI(AUTHORITY, PATH_RECIPES, RECIPES);
        uriMatcher.addURI(AUTHORITY, PATH_RECIPES + "/#", RECIPE_WITH_ID);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        recipeDBHelper = getDataBaseInstance(context);
        return true;
    }


    @Nullable
    @Override
    public Cursor query(Uri uri, @Nullable String[] projection,
                        @Nullable String selection, @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {

        int match = sUriMatcher.match(uri);
        Cursor retCursor;
        switch (match) {
            case RECIPES:
                retCursor = recipeDBHelper.getReadableDatabase().query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                retCursor.setNotificationUri(getContext().getContentResolver(), uri);
                return retCursor;
            case RECIPE_WITH_ID:
                String id = uri.getPathSegments().get(1);
                retCursor = recipeDBHelper.getReadableDatabase().query(
                        TABLE_NAME,
                        projection,
                        COLUMN_RECIPE_ID + "=?",
                        new String[]{id},
                        null,
                        null,
                        sortOrder
                );
                retCursor.setNotificationUri(getContext().getContentResolver(), uri);
                return retCursor;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case RECIPES:
                return CONTENT_TYPE;
            case RECIPE_WITH_ID:
                return CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        SQLiteDatabase sqLiteDatabase = recipeDBHelper.getWritableDatabase();

        switch (sUriMatcher.match(uri)) {
            case RECIPES:
                sqLiteDatabase.beginTransaction();
                int rowsInserted = 0;
                try {
                    for (ContentValues value : values) {
                        long id = sqLiteDatabase.insert(TABLE_NAME, null, value);
                        if (id != INVALID_RECIPE_ID) {
                            rowsInserted++;
                        }
                    }
                    sqLiteDatabase.setTransactionSuccessful();
                } finally {
                    sqLiteDatabase.endTransaction();
                }

                if (rowsInserted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsInserted;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase sqLiteDatabase = recipeDBHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        Uri returnedUri = null;

        switch (match) {
            case RECIPES:
                break;
            case RECIPE_WITH_ID:
                break;
            default:
        }
        return returnedUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values,
                      @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
