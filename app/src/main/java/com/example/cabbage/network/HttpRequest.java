package com.example.cabbage.network;

import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
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
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
//                .addInterceptor(chain -> {
//                    Request.Builder builder = chain.request().newBuilder()
//                            .addHeader("Connection", "close");
//                    return chain.proceed(builder.build());
//                })
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .build();
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
    public static void requestChangePassword(String token, String username, String oldPassword, String newPassword, INormalCallback callback) {
        getApi.changePassword(token, username, oldPassword, newPassword).enqueue(new Callback<NormalInfo>() {
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
    public static void requestAddSurveyData(String token, String obsPeriod, String json, IResultCallback callback) {
        getApi.addSurveyData(token, obsPeriod, json).enqueue(new Callback<ResultInfo>() {
            @Override
            public void onResponse(Call<ResultInfo> call, Response<ResultInfo> response) {
                if (response != null && response.body() != null) {
                    ResultInfo resultInfo = response.body();
                    callback.onResponse(resultInfo);
                }
            }

            @Override
            public void onFailure(Call<ResultInfo> call, Throwable t) {
                t.printStackTrace();
                callback.onFailure();
            }
        });
    }

    // 获取个人历史数据
    public static void getHistorySurveyData(String token, IHistoryCallback callback) {
        getApi.getHistorySurveyData(token, 100).enqueue(new Callback<HistoryInfo>() {
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

    // 获取测量帮助
    public static void getMeasurementBySpecificCharacter(String token, String specificCharacter, IHelpCallback callback) {
        getApi.getMeasurementBySpecificCharacter(token, specificCharacter).enqueue(new Callback<HelpInfo>() {
            @Override
            public void onResponse(Call<HelpInfo> call, Response<HelpInfo> response) {
                if (response != null && response.body() != null) {
                    HelpInfo helpInfo = response.body();
                    callback.onResponse(helpInfo);
                }
            }

            @Override
            public void onFailure(Call<HelpInfo> call, Throwable t) {
                t.printStackTrace();
                callback.onFailure();
            }
        });
    }

    // 获取图片
    public static void getPhotoList(String token, String surveyPeriod, String specCharacter, IPhotoListCallback callback) {
        getPhotoList(token, surveyPeriod, specCharacter, 1, 5, callback);
    }

    public static void getPhotoList(String token, String surveyPeriod, String specCharacter, int pageNum, int pageSize, IPhotoListCallback callback) {
        getApi.getPhotoList(token, surveyPeriod, specCharacter, pageNum, pageSize).enqueue(new Callback<PhotoListInfo>() {
            @Override
            public void onResponse(Call<PhotoListInfo> call, Response<PhotoListInfo> response) {
                if (response != null && response.body() != null) {
                    PhotoListInfo photoListInfo = response.body();
                    callback.onResponse(photoListInfo);
                }
            }

            @Override
            public void onFailure(Call<PhotoListInfo> call, Throwable t) {
                t.printStackTrace();
                callback.onFailure();
            }
        });
    }

    public static void getPhoto(String token, String surveyId, String specCharacter, IPhotoListCallback callback) {
        getApi.getPhoto(token, surveyId, specCharacter).enqueue(new Callback<PhotoListInfo>() {
            @Override
            public void onResponse(Call<PhotoListInfo> call, Response<PhotoListInfo> response) {
                if (response != null && response.body() != null) {
                    PhotoListInfo photoInfo = response.body();
                    callback.onResponse(photoInfo);
                }
            }

            @Override
            public void onFailure(Call<PhotoListInfo> call, Throwable t) {
                t.printStackTrace();
                callback.onFailure();
            }
        });
    }

    // 上传图片
    public static void uploadPicture(String token, String surveyPeriod, String surveyId, String specCharacter, String imgPath, INormalCallback callback) {
        Map<String, String> params = new HashMap<>();
        params.put("obsPeriod", surveyPeriod);
        params.put("observationId", surveyId);
        params.put("specCharacter", specCharacter);
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(imgPath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", fileName, requestFile);
        getApi.uploadPicture(token, params, body).enqueue(new Callback<NormalInfo>() {
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

    public static void uploadPictureOverall(String token, String surveyPeriod, String surveyId, String imgPath, INormalCallback callback) {
        Map<String, String> params = new HashMap<>();
        params.put("obsPeriod", surveyPeriod);
        params.put("observationId", surveyId);
        params.put("specCharacter", "overall");
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(imgPath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", fileName, requestFile);
        getApi.uploadPicture(token, params, body).enqueue(new Callback<NormalInfo>() {
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

    public interface IUserInfoCallback {
        void onResponse(UserInfo userInfo);

        void onFailure();
    }

    public interface INormalCallback {
        void onResponse(NormalInfo normalInfo);

        void onFailure();
    }

    public interface IResultCallback {
        void onResponse(ResultInfo resultInfo);

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

    public interface IHelpCallback {
        void onResponse(HelpInfo helpInfo);

        void onFailure();
    }

    public interface IPhotoListCallback {
        void onResponse(PhotoListInfo photoListInfo);

        void onFailure();
    }

    public interface IPhotoCallback {
        void onResponse(PhotoInfo photoInfo);

        void onFailure();
    }

}
