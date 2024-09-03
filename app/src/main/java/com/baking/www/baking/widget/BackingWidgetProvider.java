package com.baking.www.baking.widget;

import static com.baking.www.baking.RecipeDetailsActivity.RECIPE_TITLE;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.baking.www.baking.MainActivity;
import com.baking.www.baking.R;
import com.baking.www.baking.utilities.Logging;

/**
 * Implementation of App Widget functionality.
 */
public class BackingWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            Intent intent = new Intent(context, GridWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.collection_widget_provider);
            rv.setRemoteAdapter(R.id.widget_grid, intent);
            rv.setTextViewText(R.id.id_recipeTitle, "Check today's recipe ingredient");

            // Create an Intent to launch ExampleActivity
            Intent clickIntent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, clickIntent, PendingIntent.FLAG_IMMUTABLE);
            rv.setOnClickPendingIntent(R.id.main_widget, pendingIntent);
            appWidgetManager.updateAppWidget(appWidgetId, rv);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Logging.log(intent.getStringExtra(RECIPE_TITLE));
    }

    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
        super.onRestored(context, oldWidgetIds, newWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

