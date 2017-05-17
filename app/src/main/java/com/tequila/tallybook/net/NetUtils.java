package com.tequila.tallybook.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Tequila on 2017/5/17.
 */

public class NetUtils {

    public static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();

    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.2.14/TallyBook/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build();

    public static DataService getDataService() {
        DataService plaEventService = retrofit.create(DataService.class);
        return plaEventService;
    }
}
