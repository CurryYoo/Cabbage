package com.example.cabbage.network;

import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author: xiemugan
 * Date: 2020/6/5
 * Description: 网络请求类
 **/
public class HttpRequest {
    private static Retrofit retrofit;
    private static GetApi getApi;
    private static String url = "http://121.36.229.144:8021/";

    static {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                //打印retrofit日志
                Log.i("RetrofitLog", "retrofitBack = " + message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    // 获取用户信息
    public static void requestUserInfo(String userId, IUserInfoCallback callback) {
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        getApi = retrofit.create(GetApi.class);
        getApi.getUserInfo(userId).enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                if (response != null && response.body() != null) {
                    UserInfo userInfo = response.body();
                    callback.onResponse(userInfo);
                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                callback.onFailure();
            }
        });
    }

    public static void requestLogin(String username, String password, IUserInfoCallback callback) {
        getApi = retrofit.create(GetApi.class);
        getApi.login(username, password).enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                if (response != null && response.body() != null) {
                    UserInfo userInfo = response.body();
                    callback.onResponse(userInfo);
                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                t.printStackTrace();
                callback.onFailure();
            }
        });
    }

    // 搜索品种
    public static void requestSearch(String speciesId, IUserInfoCallback callback) {

    }

    // 获取品种具体数据
    public static void requestSpeciesData(String speciesId, String userId, IUserInfoCallback callback) {

    }

    public interface IUserInfoCallback {
        void onResponse(UserInfo userInfo);

        void onFailure();
    }


}
