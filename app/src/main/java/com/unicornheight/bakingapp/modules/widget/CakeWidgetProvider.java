package com.unicornheight.bakingapp.modules.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
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
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.cake_widget);
        List<Cake> widgetList;
        Storage storage = new Storage(context);

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

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    private static RemoteViews gridRemoteView(Context context) {
        List<Cake> widgetList;
        Storage storage = new Storage(context);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_grid_view);

        widgetList = storage.getSavedCakes();
        if (widgetList != null) {
            for (Cake cake : widgetList) {
                views.setTextViewText(R.id.widget_cake_name, cake.getName());
                views.setImageViewResource(R.id.widget_image,
                        R.drawable.empty);
                // Set the cakeWidget intent to act as the adapter for the GridView
                Intent intent = new Intent(context, CakeWidget.class);
                views.setRemoteAdapter(R.id.widget_grid_view, intent);
                // Set the DetailActivity intent to launch when clicked
                Intent appIntent = new Intent(context, DetailActivity.class);


                PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                views.setPendingIntentTemplate(R.id.widget_grid_view, appPendingIntent);
                //  views.setOnClickPendingIntent(R.id.widget_image, appPendingIntent);
                // Handle empty gardens
                views.setEmptyView(R.id.widget_grid_view, R.id.empty_view);

            }
        }
        return views;
    }
}
