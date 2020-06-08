package com.example.cabbage.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Author: xiemugan
 * Date: 2020/6/6
 * Description:
 **/
public interface GetApi {

    @GET("getUserInfo")
    Call<UserInfo> getUserInfo(@Query("id") String userId);
}
