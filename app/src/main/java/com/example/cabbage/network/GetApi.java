package com.example.cabbage.network;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Author: xiemugan
 * Date: 2020/6/6
 * Description:
 **/
public interface GetApi {

    @POST("auth/login")
    Call<UserInfo> login(@Query("username") String username, @Query("password") String password);

    @POST("characterAnalysis/addDifObsPeriodData")
    Call<NormalInfo> addSurveyData(@Header("token") String token, @Query("obsPeriod") String obsPeriod, @Query("json") String json);

}
