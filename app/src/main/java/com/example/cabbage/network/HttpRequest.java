package com.example.cabbage.network;

import android.util.Log;

import com.example.cabbage.data.SurveyData;

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
    private static String url = "http://47.93.117.9:8021/";

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
        getApi = retrofit.create(GetApi.class);
    }

    // 登录
    public static void requestLogin(String username, String password, IUserInfoCallback callback) {
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

    // 修改密码
    public static void requestChangePassword(String token,String username, String oldPassword, String newPassword, INormalCallback callback) {
        getApi.changePassword(token,username, oldPassword, newPassword ).enqueue(new Callback<NormalInfo>() {
            @Override
            public void onResponse(Call<NormalInfo> call, Response<NormalInfo> response) {
                if (response != null && response.body() != null) {
                    NormalInfo normalInfo = response.body();
                    callback.onResponse(normalInfo);
                }
            }

            @Override
            public void onFailure(Call<NormalInfo> call, Throwable t) {
                t.printStackTrace();
                callback.onFailure();
            }
        });
    }

    // 查询材料
    public static void requestSearch(String token, String searchKeyword, IMaterialCallback callback) {
        requestSearch(token, searchKeyword, 1, 5, callback);
    }

    public static void requestSearch(String token, String searchKeyword, int pageNum, int pageSize, IMaterialCallback callback) {
        getApi.findMaterialBySearch(token, searchKeyword, pageNum, pageSize).enqueue(new Callback<MaterialInfo>() {
            @Override
            public void onResponse(Call<MaterialInfo> call, Response<MaterialInfo> response) {
                if (response != null && response.body() != null) {
                    MaterialInfo materialInfo = response.body();
                    callback.onResponse(materialInfo);
                }
            }

            @Override
            public void onFailure(Call<MaterialInfo> call, Throwable t) {
                t.printStackTrace();
                callback.onFailure();
            }
        });
    }

    // 新增不同观测时期数据
    public static void requestAddSurveyData(String token, String obsPeriod, String json, ISurveyCallback callback) {
        getApi.addSurveyData(token, obsPeriod, json).enqueue(new Callback<SurveyInfo>() {
            @Override
            public void onResponse(Call<SurveyInfo> call, Response<SurveyInfo> response) {
                if (response != null && response.body() != null) {
                    SurveyInfo surveyInfo = response.body();
                    callback.onResponse(surveyInfo);
                }
            }

            @Override
            public void onFailure(Call<SurveyInfo> call, Throwable t) {
                t.printStackTrace();
                callback.onFailure();
            }
        });
    }

    // 获取个人历史数据
    public static void getHistorySurveyData(String token, IHistoryCallback callback) {
        getApi.getHistorySurveyData(token).enqueue(new Callback<HistoryInfo>() {
            @Override
            public void onResponse(Call<HistoryInfo> call, Response<HistoryInfo> response) {
                if (response != null && response.body() != null) {
                    HistoryInfo historyInfo = response.body();
                    callback.onResponse(historyInfo);
                }
            }

            @Override
            public void onFailure(Call<HistoryInfo> call, Throwable t) {
                t.printStackTrace();
                callback.onFailure();
            }
        });
    }

    // 获取品种具体数据
    // 根据观测id和观测时期
    public static void getSurveyDataDetailBySurveyId(String token, String surveyPeriod, String surveyId, ISurveyCallback callback) {
        getApi.getSurveyDataDetailBySurveyId(token, surveyPeriod, surveyId).enqueue(new Callback<SurveyInfo>() {
            @Override
            public void onResponse(Call<SurveyInfo> call, Response<SurveyInfo> response) {
                if (response != null && response.body() != null) {
                    SurveyInfo surveyInfo = response.body();
                    callback.onResponse(surveyInfo);
                }
            }

            @Override
            public void onFailure(Call<SurveyInfo> call, Throwable t) {
                t.printStackTrace();
                callback.onFailure();
            }
        });
    }
    // 根据单株编号和观测时期
    public static void getSurveyDataDetailByPlantNumber(String token, String surveyPeriod, String plantNumber, ISurveyCallback callback) {
        getApi.getSurveyDataDetailByPlantNumber(token, surveyPeriod, plantNumber).enqueue(new Callback<SurveyInfo>() {
            @Override
            public void onResponse(Call<SurveyInfo> call, Response<SurveyInfo> response) {
                if (response != null && response.body() != null) {
                    SurveyInfo surveyInfo = response.body();
                    callback.onResponse(surveyInfo);
                }
            }

            @Override
            public void onFailure(Call<SurveyInfo> call, Throwable t) {
                t.printStackTrace();
                callback.onFailure();
            }
        });
    }

    // 获取图片


    // 上传图片


    public interface IUserInfoCallback {
        void onResponse(UserInfo userInfo);
        void onFailure();
    }

    public interface INormalCallback {
        void onResponse(NormalInfo normalInfo);
        void onFailure();
    }

    public interface IMaterialCallback {
        void onResponse(MaterialInfo materialInfo);
        void onFailure();
    }

    public interface IHistoryCallback {
        void onResponse(HistoryInfo historyInfo);
        void onFailure();
    }

    public interface ISurveyCallback {
        void onResponse(SurveyInfo surveyInfo);
        void onFailure();
    }

}
