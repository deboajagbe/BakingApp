

package com.unicornheight.bakingapp.mvp.presenter;
import android.util.Log;

import com.unicornheight.bakingapp.api.CakeApiService;
import com.unicornheight.bakingapp.base.BasePresenter;
import com.unicornheight.bakingapp.mapper.CakeMapper;
import com.unicornheight.bakingapp.modules.home.SimpleIdlingResource;
import com.unicornheight.bakingapp.mvp.model.Cake;
import com.unicornheight.bakingapp.mvp.model.CakesResponse;
import com.unicornheight.bakingapp.mvp.model.Storage;
import com.unicornheight.bakingapp.mvp.view.MainView;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;


public class CakePresenter extends BasePresenter<MainView> implements Observer<List<CakesResponse>> {

    @Inject protected CakeApiService mApiService;
    @Inject protected CakeMapper mCakeMapper;
    @Inject protected Storage mStorage;
    SimpleIdlingResource resource;

    @Inject
    public CakePresenter() {
    }

    public void getCakes(SimpleIdlingResource resource) {
        this.resource = resource;
        getView().onShowDialog("Loading cakes....");
        if (resource != null) resource.setIdleState(false);
        Observable<List<CakesResponse>> cakesResponseObservable = mApiService.getCakes();
        subscribe(cakesResponseObservable, this);

    }

    @Override
    public void onCompleted() {
        getView().onHideDialog();
        if (resource != null) resource.setIdleState(true);
        getView().onShowToast("Cakes loading complete!");

    }

    @Override
    public void onError(Throwable e) {
        getView().onHideDialog();
        getView().onShowToast("Error loading cakes " + e.getMessage());
        Log.v("ERROR REC", e.getMessage().toString() );
    }

    @Override
    public void onNext(List<CakesResponse>  cakesResponse) {
        List<Cake> cakes = mCakeMapper.mapCakes(mStorage, cakesResponse);
        getView().onClearItems();
        getView().onCakeLoaded(cakes);
    }

    public void getCakesFromDatabase() {
        List<Cake> cakes = mStorage.getSavedCakes();
        getView().onClearItems();
        getView().onCakeLoaded(cakes);
    }
}
