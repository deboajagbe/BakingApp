package com.unicornheight.bakingapp.base;

import com.unicornheight.bakingapp.mvp.model.CakesResponse;
import com.unicornheight.bakingapp.mvp.presenter.CakePresenter;
import com.unicornheight.bakingapp.mvp.view.BaseView;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BasePresenter<V extends BaseView> {

    @Inject protected V mView;

    protected V getView() {
        return mView;
    }

    protected <T> void subscribe(Observable<List<CakesResponse>> observable, CakePresenter observer) {
        observable.subscribeOn(Schedulers.newThread())
                .toSingle()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
