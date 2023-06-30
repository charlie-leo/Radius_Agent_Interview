package com.radiusagent.interview.service;


/*
 * Created by Charles Raj I on 2023.
 *  @author Charles Raj I
 */

import com.radiusagent.interview.model.FacilitiesResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface RetrofitInterface {

    @GET("/iranjith4/ad-assignment/db/")
    Observable<FacilitiesResponse> getFacilities();

}
