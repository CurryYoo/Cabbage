package com.example.cabbage.network;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

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
    Call<NormalInfo> changePassword(@Header("token") String token, @Path("username") String username, @Query("oldPassword") String oldPassword, @Query("newPassword") String newPassword);

    // 查询材料
    @GET("material/findMaterialBySearch")
    Call<MaterialInfo> findMaterialBySearch(@Header("token") String token, @Query("search_keyword") String searchKeyword, @Query("pageNum") int pageNum, @Query("pageSize") int pageSize);

    // 添加材料数据
    @POST("characterAnalysis/addDifObsPeriodData")
    Call<ResultInfo> addSurveyData(@Header("token") String token, @Query("obsPeriod") String obsPeriod, @Query("json") String json);

    // 获取个人历史数据
    @GET("characterAnalysis/findHistoryDataByUserAndSearchCriteria")
    Call<HistoryInfo> getHistorySurveyData(@Header("token") String token, @Query("pageSize") int pageSize);

    // 获取材料数据详情
    // 根据观测id和观测时期
    @GET("characterAnalysis/showObsDataDetailByObsId")
    Call<SurveyInfo> getSurveyDataDetailBySurveyId(@Header("token") String token, @Query("obsPeriod") String obsPeriod, @Query("observationId") String observationId);

    // 根据单株编号和观测时期
    @GET("characterAnalysis/showObsDataDetailByPlantNumber")
    Call<SurveyInfo> getSurveyDataDetailByPlantNumber(@Header("token") String token, @Query("obsPeriod") String obsPeriod, @Query("plantNumber") String plantNumber);

    // 获取测量帮助
    @GET("/characterAnalysis/findMeasurementBySpecificCharacter")
    Call<HelpInfo> getMeasurementBySpecificCharacter(@Header("token") String token, @Query("specificCharacter") String specificCharacter);

    // 获取图片信息
    @GET("/qiNiuContent/photoList")
    Call<PhotoListInfo> getPhotoList(@Header("token") String token, @Query("obsPeriod") String obsPeriod, @Query("specCharacter") String specCharacter, @Query("pageNum") int pageNum, @Query("pageSize") int pageSize);

    @GET("/qiNiuContent/specCharacterPhoto")
    Call<PhotoListInfo> getPhoto(@Header("token") String token, @Query("observationId") String observationId, @Query("specCharacter") String specCharacter);

    // 上传图片信息
    @Multipart
    @POST("/qiNiuContent")
    Call<NormalInfo> uploadPicture(@Header("token") String token, @QueryMap Map<String, String> params, @Part MultipartBody.Part file);

    // 时期下图片上传

    // 批量上传图片

}
