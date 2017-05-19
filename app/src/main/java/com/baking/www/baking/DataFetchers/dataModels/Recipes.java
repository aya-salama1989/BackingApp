package com.baking.www.baking.DataFetchers.dataModels;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.LinkedList;

/**
 * Created by Dell on 10/05/2017.
 */

public class Recipes extends LinkedList<Recipe> {
    public Recipes(JSONArray jsonArray){
        for (int i= 0; i<jsonArray.length(); i++){
            try {
                add(new Recipe(jsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public Recipes() {

    }
}
