package com.unicornheight.bakingapp.presenter;

import android.os.Looper;

import com.unicornheight.bakingapp.api.CakeApiService;
import com.unicornheight.bakingapp.mapper.CakeMapper;
import com.unicornheight.bakingapp.modules.home.SimpleIdlingResource;
import com.unicornheight.bakingapp.mvp.model.Cake;
import com.unicornheight.bakingapp.mvp.model.CakesResponse;
import com.unicornheight.bakingapp.mvp.model.CakesResponseIngredients;
import com.unicornheight.bakingapp.mvp.model.Storage;
import com.unicornheight.bakingapp.mvp.presenter.CakePresenter;
import com.unicornheight.bakingapp.mvp.view.MainView;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.plugins.RxJavaSchedulersHook;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;


@RunWith(PowerMockRunner.class)
@PrepareForTest({Observable.class, AndroidSchedulers.class, Looper.class, CakesResponse.class})
public class CakePresenterTest {

    public static final String TEST_ERROR_MESSAGE = "error_message";

    @InjectMocks
    private CakePresenter presenter;
    @Mock
    private CakeApiService mApiService;
    @Mock private CakeMapper mCakeMapper;
    @Mock private Storage mStorage;
    @Mock private MainView mView;
    @Mock
    private Observable<List<CakesResponse>> mObservable;
    SimpleIdlingResource resource;

    @Captor
    private ArgumentCaptor<Subscriber<CakesResponse>> captor;

    private final RxJavaSchedulersHook mRxJavaSchedulersHook = new RxJavaSchedulersHook() {
        @Override
        public Scheduler getIOScheduler() {
            return Schedulers.immediate();
        }

        @Override
        public Scheduler getNewThreadScheduler() {
            return Schedulers.immediate();
        }
    };

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        ArrayList<Cake> cakes = new ArrayList<>();
        cakes.add(new Cake());
        when(mStorage.getSavedCakes()).thenReturn(cakes);
   }

    @Test
    public void getCakes() throws Exception {
        PowerMockito.mockStatic(Looper.class);
        Mockito.when(AndroidSchedulers.mainThread()).thenReturn(mRxJavaSchedulersHook.getComputationScheduler());

        Mockito.when(mApiService.getCakes()).thenReturn(mObservable);
        //        when(observable.subscribeOn(Schedulers.newThread())).thenReturn(observable);
        //        when(observable.observeOn(AndroidSchedulers.mainThread())).thenReturn(observable);

        presenter.getCakes(resource);
        verify(mView, atLeastOnce()).onShowDialog("Loading cakes....");
    }

    @Test
    public void onCompleted() throws Exception {
        presenter.onCompleted();
        verify(mView, times(1)).onHideDialog();
        verify(mView, times(1)).onShowToast("Cakes loading complete!");
    }

    @Test
    public void onError() throws Exception {
        presenter.onError(new Throwable(TEST_ERROR_MESSAGE));
        verify(mView, times(1)).onHideDialog();
        verify(mView, times(1)).onShowToast("Error loading cakes " + TEST_ERROR_MESSAGE);
    }

    @Test
    public void onNext() throws Exception {

        CakesResponse response = mock(CakesResponse.class);
        CakesResponseIngredients[] responseCakes = new CakesResponseIngredients[1];
        Mockito.when(response.getIngredients()).thenReturn(Arrays.asList(responseCakes));
        presenter.onNext((List<CakesResponse>) response);


        verify(mCakeMapper, times(1)).mapCakes(mStorage, (List<CakesResponse>) response);
        verify(mView, times(1)).onClearItems();
        verify(mView, times(1)).onCakeLoaded(Matchers.anyList());
    }

    @Test
    public void getCakesFromDatabase() throws Exception {
        presenter.getCakesFromDatabase();
        verify(mView, times(1)).onClearItems();
        verify(mView, times(1)).onCakeLoaded(Matchers.anyList());
    }
}