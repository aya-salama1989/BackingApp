package com.baking.www.baking.DataFetchers.dataModels;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dell on 10/05/2017.
 */

public class Ingredient {
    private String TAG = getClass().getName();
    private int quantity;
    private String measure;
    private String ingredient;

    public Ingredient(JSONObject jsonObject) {
        try {
            if(jsonObject.has("quantity")){
                quantity = jsonObject.getInt("quantity");
            }
            if(jsonObject.has("measure")){
                measure = jsonObject.getString("measure");
            }
            if(jsonObject.has("ingredient")){
                ingredient = jsonObject.getString("ingredient");
            }
        }catch (JSONException ex){
            Log.e(TAG, ex.getMessage());
        }

    }

    public int getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }
}
