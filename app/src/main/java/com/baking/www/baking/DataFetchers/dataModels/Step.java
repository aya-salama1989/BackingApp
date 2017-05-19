package com.baking.www.baking.DataFetchers.dataModels;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dell on 10/05/2017.
 */

public class Step {
    private String TAG = getClass().getName();

    private int id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;

    public Step(JSONObject jsonObject) {
        try {
            if (jsonObject.has("id")) {
                id = jsonObject.getInt("id");
            }
            if (jsonObject.has("shortDescription")) {
                shortDescription = jsonObject.getString("shortDescription");
            }
            if (jsonObject.has("description")) {
                description = jsonObject.getString("description");
            }
            if (jsonObject.has("videoURL")) {
                videoURL = jsonObject.getString("videoURL");
            }
            if (jsonObject.has("thumbnailURL")) {
                thumbnailURL = jsonObject.getString("thumbnailURL");
            }
        } catch (JSONException ex) {
            Log.e(TAG, ex.getMessage());
        }

    }

    public int getId() {
        return id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }
}
