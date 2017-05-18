package com.tequila.tallybook.net;

import com.tequila.tallybook.mode.ResultModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Tequila on 2017/5/17.
 */

public interface DataService {

    @GET("networkVerify.php")
    Call<ResultModel> networkVerify();

    @FormUrlEncoded
    @POST("addUser.php")
    Call<ResultModel> addUser(@Field("sUserName") String sUserName, @Field("sUserPhone") String sUserPhone, @Field("sPasswd") String sPasswd);

    @POST("SynchronousData.php")
    Call<ResultModel> synchronousData();
}
