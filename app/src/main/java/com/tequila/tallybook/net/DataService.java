package com.tequila.tallybook.net;

import com.tequila.tallybook.mode.ResultModel;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Tequila on 2017/5/17.
 */

public interface DataService {

    @POST("getNewApkClientInfo.php")
    Call<ResultModel> getNewApkClientInfo();

    @GET("networkverify.php")
    Call<ResultModel> networkVerify();

    @FormUrlEncoded
    @POST("adduser.php")
    Call<ResultModel> addUser(@Field("sUserName") String sUserName, @Field("sUserPhone") String sUserPhone, @Field("sPasswd") String sPasswd);

    @FormUrlEncoded
    @POST("loginvalidation.php")
    Call<ResultModel> loginvalidation(@Field("sUserName") String sUserName, @Field("sPasswd") String sPasswd);

    @POST("synchronousdata.php")
    Call<ResultModel> synchronousData();

    @POST("saveLifeInfo.php")
    Call<ResultModel> saveLifeInfo(@Body RequestBody requestBody);

    @POST("getLifeData.php")
    Call<ResultModel> getLifeData();

    @POST("getTallyViewHeadData.php")
    Call<ResultModel> getTallyViewHeadData();

    @POST("getTallyViewBodyData.php")
    Call<ResultModel> getTallyViewBodyData();

    @FormUrlEncoded
    @POST("getAccountDetails.php")
    Call<ResultModel> getAccountDetails(@Field("sBillNo") String sBillNo);

    @POST("alterTallyData.php")
    Call<ResultModel> alterTallyData(@Body RequestBody requestBody);

    @POST("settleAccounts.php")
    Call<ResultModel> settleAccounts();
}
