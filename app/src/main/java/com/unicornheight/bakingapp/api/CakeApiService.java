

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


// http://go.udacity.com/android-baking-app-json
 // filippella/a728a34822a3bc7add98e477a4057b69/raw/310d712e87941f569074a63fedb675d2b611342a/cakes