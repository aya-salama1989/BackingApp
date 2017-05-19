package com.baking.www.baking.DataFetchers.Fetchers;

import android.content.Context;

import com.android.volley.toolbox.JsonArrayRequest;
import com.baking.www.baking.DataFetchers.dataModels.Recipes;
import com.baking.www.baking.utilities.Logging;

import org.json.JSONArray;

/**
 * Created by Dell on 15/05/2017.
 */

public class RecipesDataFetcher extends BaseDataFetcher {
    public RecipesDataFetcher(Context context, BaseDataFetcherListener mListener) {
        super(context, mListener);
    }

    public void getRecipes() {
        String URL = BaseURL;
        Logging.log("getCountries: " + URL);
        JsonArrayRequest jsonObjReq = new JsonArrayRequest(URL, (JSONArray jsonArr) -> {
            Logging.log("getCountries response: " + jsonArr.toString());
            Recipes recipes = new Recipes(jsonArr);
            ((RecipesFetcherDataListener) mListener).onConnectionDone(recipes);
        }, this.errorListener);
        retryPolicy(jsonObjReq);
        getReQ().add(jsonObjReq);
    }

    public interface RecipesFetcherDataListener extends BaseDataFetcherListener {
        void onConnectionDone(Recipes recipes);
    }
}
