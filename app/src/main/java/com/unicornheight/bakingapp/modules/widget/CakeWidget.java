package com.unicornheight.bakingapp.modules.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.unicornheight.bakingapp.R;
import com.unicornheight.bakingapp.modules.details.DetailActivity;
import com.unicornheight.bakingapp.mvp.model.Cake;
import com.unicornheight.bakingapp.mvp.model.CakesResponseIngredients;
import com.unicornheight.bakingapp.mvp.model.Storage;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class CakeWidget extends RemoteViewsService {


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new CakeFactory(getApplicationContext(), intent);
    }

    class CakeFactory implements RemoteViewsService.RemoteViewsFactory {
        private Context mContext;
        private int mAppWidgetId;
        private List<Cake> widgetList = new ArrayList<>();
        Storage storage;


        public CakeFactory(Context context, Intent intent) {

            mContext = context;
            mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        private void updateWidgetListView() {
            if (storage.getSavedCakes() != null) {
                widgetList = storage.getSavedCakes();
            } else {
                widgetList = null;
            }
        }

        @Override
        public void onCreate() {
            storage = new Storage(mContext);
            updateWidgetListView();
        }

        @Override
        public void onDataSetChanged() {
            updateWidgetListView();
        }

        @Override
        public void onDestroy() {
            widgetList.clear();
        }

        @Override
        public int getCount() {
            return widgetList.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(),
                    R.layout.cake_widget);
            if (widgetList != null) {
                for (Cake cake : widgetList) {
                    remoteViews.setTextViewText(R.id.widget_cake_name,
                            cake.getName());
                    remoteViews.setImageViewResource(R.id.widget_image,
                            R.drawable.empty);
//                    for (CakesResponseIngredients cakesResponseIngredients : cake.getIngredients()) {
//                        remoteViews.setTextViewText(R.id.widget_cake_ingredients,
//                                cakesResponseIngredients.getIngredient() + " " + cakesResponseIngredients.getMeasure() + " " + cakesResponseIngredients.getQuantity());
//                    }
//                    Bundle extras = new Bundle();
//                    extras.putInt(DetailActivity.CAKE, position);
//                    Intent fillInIntent = new Intent();
//                    fillInIntent.putExtras(extras);
//                    remoteViews.setOnClickFillInIntent(R.id.widget_cake_name, fillInIntent);

                }
            }
            // Return the remote views object.
            return remoteViews;
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
}