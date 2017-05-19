package com.baking.www.baking.DataFetchers.dataModels;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dell on 10/05/2017.
 */

public class Recipe implements Parcelable{
    private String TAG = getClass().getName();

    private int id;
    private String name;
    private Ingredients ingredients;
    private Steps steps;
    private int servings;
    private String image;

    public Recipe(JSONObject jsonObject) {
        try {
            if (jsonObject.has("id")) {
                id = jsonObject.getInt("id");
            }
            if (jsonObject.has("name")) {
                name = jsonObject.getString("name");
            }
            if (jsonObject.has("ingredients")) {
                ingredients = new Ingredients(jsonObject.getJSONArray("ingredients"));
            }
            if (jsonObject.has("steps")) {
                steps = new Steps(jsonObject.getJSONArray("steps"));
            }
            if (jsonObject.has("servings")) {
                servings = jsonObject.getInt("servings");
            }
            if (jsonObject.has("image")) {
                image = jsonObject.getString("image");
            }
        } catch (JSONException ex) {
            Log.e(TAG, ex.getMessage());
        }

    }

    protected Recipe(Parcel in) {
        id = in.readInt();
        name = in.readString();
        servings = in.readInt();
        image = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Ingredients getIngredients() {
        return ingredients;
    }

    public Steps getSteps() {
        return steps;
    }

    public int getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(servings);
        dest.writeString(image);

    }
}
