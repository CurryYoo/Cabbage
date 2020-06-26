package com.example.cabbage.network;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Author: xiemugan
 * Date: 2020/6/6
 * Description:
 **/
public interface GetApi {
    // 登录
    @POST("auth/login")
    Call<UserInfo> login(@Query("username") String username, @Query("password") String password);

    // 修改密码
    @PUT("users/{username}")
    Call<NormalInfo> changePassword(@Header("token") String token, @Query("oldPassword") String oldPassword, @Query("newPassword") String newPassword, @Path("username") String username);

    // 查询材料
    @GET("material/findMaterialBySearch")
    Call<MaterialInfo> findMaterialBySearch(@Header("token") String token, @Query("search_keyword") String searchKeyword, @Query("pageNum") int pageNum, @Query("pageSize") int pageSize);

    // 添加材料数据
    @POST("characterAnalysis/addDifObsPeriodData")
    Call<NormalInfo> addSurveyData(@Header("token") String token, @Query("obsPeriod") String obsPeriod, @Query("json") String json);

    // 获取个人历史数据
    @GET("characterAnalysis/historyObsData")
    Call<HistoryInfo> getHistorySurveyData(@Header("token") String token);

    // 获取材料数据详情
    // 根据观测id和观测时期
    @GET("characterAnalysis/showObsDataDetailByObsId")
    Call<SurveyInfo> getSurveyDataDetailBySurveyId(@Header("token") String token, @Query("obsPeriod") String obsPeriod, @Query("observationId") String observationId);
    // 根据单株编号和观测时期
    @GET("characterAnalysis/showObsDataDetailByPlantNumber")
    Call<SurveyInfo> getSurveyDataDetailByPlantNumber(@Header("token") String token, @Query("obsPeriod") String obsPeriod, @Query("plantNumber") String plantNumber);

    // 上传图片信息


    // 获取图片信息

}
