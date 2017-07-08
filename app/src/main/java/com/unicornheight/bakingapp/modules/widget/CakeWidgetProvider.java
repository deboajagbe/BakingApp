package com.unicornheight.bakingapp.modules.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.widget.RemoteViews;
import com.unicornheight.bakingapp.R;
import com.unicornheight.bakingapp.modules.details.DetailActivity;
import com.unicornheight.bakingapp.modules.home.MainActivity;
import com.unicornheight.bakingapp.mvp.model.Cake;
import com.unicornheight.bakingapp.mvp.model.Storage;

import java.util.List;

/**
 * Created by deboajagbe on 6/28/17.
 */

public class CakeWidgetProvider extends AppWidgetProvider {


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        List<Cake> widgetList;
        Storage storage = new Storage(context);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.cake_widget);
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.pref_name), 0);
        if (sharedPreferences.contains(context.getString(R.string.pref_key))) {
            String cakeName = sharedPreferences.getString(context.getString(R.string.pref_key), "");
            String cakeIngredient = sharedPreferences.getString(context.getString(R.string.pref_ingredient), "");

            views.setTextViewText(R.id.widget_cake_name, cakeName);
            views.setImageViewResource(R.id.widget_image,
                    R.drawable.empty);
            views.setTextViewText(R.id.widget_ingredient, cakeIngredient);


            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.widget_image, pendingIntent);
            appWidgetManager.updateAppWidget(appWidgetId, views);

        } else {
            widgetList = storage.getSavedCakes();
            if (widgetList != null) {
                views.setTextViewText(R.id.widget_cake_name, context.getString(R.string.app_name));
                views.setImageViewResource(R.id.widget_image,
                        R.drawable.empty);
                Intent intent = new Intent(context, MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                views.setOnClickPendingIntent(R.id.widget_image, pendingIntent);
                appWidgetManager.updateAppWidget(appWidgetId, views);

            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        super.onDisabled(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Bundle extras = intent.getExtras();
        if (extras != null) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName thisAppWidget = new ComponentName(context.getPackageName(), CakeWidgetProvider.class.getName());
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);
            onUpdate(context, appWidgetManager, appWidgetIds);
            }
    }
}
