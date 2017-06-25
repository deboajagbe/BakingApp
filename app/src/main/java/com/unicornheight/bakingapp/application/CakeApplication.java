

package com.unicornheight.bakingapp.application;


import android.app.Application;

import com.unicornheight.bakingapp.di.components.ApplicationComponent;
import com.unicornheight.bakingapp.di.components.DaggerApplicationComponent;
import com.unicornheight.bakingapp.di.module.ApplicationModule;

public class CakeApplication extends Application {

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeApplicationComponent();
    }

    private void initializeApplicationComponent() {
        mApplicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this, "http://go.udacity.com"))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
