package com.baking.www.baking;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.baking.www.baking.DataFetchers.dataModels.Recipes;

import static com.baking.www.baking.providers.ContractClass.RecipeEntry.COLUMN_RECIPE_NAME;
import static com.baking.www.baking.providers.ContractClass.RecipeEntry.CONTENT_URI;

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
    private Recipes mRecipes;


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
        mCursor = mContext.getContentResolver().query(CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onDestroy() {
        mCursor.close();
    }

    @Override
    public int getCount() {
        if (mCursor == null) return 0;
        return mCursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (mCursor == null || mCursor.getCount() == 0) return null;
        mCursor.moveToPosition(position);
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.item_recipe_widget);

        String recipeTitle = mCursor.getString(mCursor.getColumnIndex(COLUMN_RECIPE_NAME));
//        String imageUrl = mCursor.getString(mCursor.getColumnIndex(COLUMN_RECIPE_IMAGE));

        views.setImageViewResource(R.id.widget_image, R.drawable.exo_controls_play);
        views.setTextViewText(R.id.widget_text, recipeTitle);

        // Fill in the onClick PendingIntent Template using the specific plant Id for each item individually
        Bundle extras = new Bundle();
//        extras.putLong(PlantDetailActivity.EXTRA_RECIPE_ID, plantId);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        views.setOnClickFillInIntent(R.id.widget_image, fillInIntent);
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
