

package com.unicornheight.bakingapp.api;


import com.unicornheight.bakingapp.mvp.model.CakesResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import rx.Observable;

public interface CakeApiService {

    @GET("/android-baking-app-json")
    Observable<List<CakesResponse>>getCakes();

    @GET("/android-baking-app-json")
    Call<List<CakesResponse>>getTheCakes();
}
