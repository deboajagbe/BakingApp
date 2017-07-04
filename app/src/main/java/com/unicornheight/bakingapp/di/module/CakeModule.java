

package com.unicornheight.bakingapp.di.module;

import com.unicornheight.bakingapp.api.CakeApiService;
import com.unicornheight.bakingapp.di.scope.PerActivity;
import com.unicornheight.bakingapp.mvp.view.MainView;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class CakeModule {

    private MainView mView;

    public CakeModule(MainView view) {
        mView = view;
    }

    @PerActivity
    @Provides
    CakeApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(CakeApiService.class);
    }

    @PerActivity
    @Provides
    MainView provideView() {
        return mView;
    }
}
