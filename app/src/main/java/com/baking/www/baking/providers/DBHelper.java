package com.baking.www.baking.providers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.baking.www.baking.providers.ContractClass.CREATE_TABLA_RECIPES;
import static com.baking.www.baking.providers.ContractClass.RecipeEntry.TABLE_NAME;

/**
 * Created by Dell on 25/05/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    // The database name
    private static final String DATABASE_NAME = "recipes.db";
    // database version
    private static final int DATABASE_VERSION = 2;

    private static DBHelper dbHelper;

    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DBHelper getDataBaseInstance(Context context) {
        if (dbHelper == null) {
            dbHelper = new DBHelper(context);
        }
        return dbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLA_RECIPES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
