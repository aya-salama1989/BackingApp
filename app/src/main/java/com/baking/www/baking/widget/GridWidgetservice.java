package com.baking.www.baking.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.baking.www.baking.DataFetchers.dataModels.Ingredients;
import com.baking.www.baking.R;

import org.json.JSONArray;
import org.json.JSONException;

import static com.baking.www.baking.providers.ContractClass.RecipeEntry.COLUMN_RECIPE_INGREDIENTS;
import static com.baking.www.baking.providers.ContractClass.RecipeEntry.RECIPES_CONTENT_URI;

/**
 * Created by Dell on 26/05/2017.
 */

public class GridWidgetservice extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        return new GridRemoteViewFactory(this.getApplicationContext(), intent);
    }
}

class GridRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private Cursor mCursor;
    private int mAppWidgetId;

    private Ingredients ingredients;

    public GridRemoteViewFactory(Context context, Intent intent) {
        this.mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        mCursor = mContext.getContentResolver().query(RECIPES_CONTENT_URI, null, null, null, null);
        if (mCursor == null || mCursor.getCount() == 0) return;
        mCursor.moveToFirst();
        String ingredientsString = mCursor.getString(mCursor.getColumnIndex(COLUMN_RECIPE_INGREDIENTS));
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(ingredientsString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ingredients = new Ingredients(jsonArray);
    }

    @Override
    public void onDestroy() {
        mCursor.close();
    }

    @Override
    public int getCount() {
        if (mCursor == null) return 0;
        return ingredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.item_recipe_widget);
//        String ingredientsString = mCursor.getString(mCursor.getColumnIndex(COLUMN_RECIPE_INGREDIENTS));
        views.setTextViewText(R.id.widget_text, ingredients.get(position).getIngredient());
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
