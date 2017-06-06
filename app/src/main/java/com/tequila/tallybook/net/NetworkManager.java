package com.tequila.tallybook.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.text.TextUtils;

import com.tequila.tallybook.mode.ResultModel;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Tequila on 2017/6/6.
 */

public class NetworkManager {

    public static final NetworkManager nm = new NetworkManager();

    private NetworkManager() {}

    /**
     * 验证程序能否访问服务器
     * @return
     */
    public String networkAvailable() {
        String str = "";

        Call<ResultModel> call = NetUtils.getDataService().networkVerify();

        try {
            Response<ResultModel> response = call.execute();
            ResultModel model = response.body();
            if (model.getSucceedFlag().equals("1")) {
                str = model.getReturnInfo().toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
            str = "";
        }

        return str;
    }

    // 判断网络是否可用
    public boolean isNetworkAvailable(Context context) {
        // 得到网络连接信息
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // 去进行判断网络是否连接
        if (manager.getActiveNetworkInfo() != null) {
            if (manager.getActiveNetworkInfo().isAvailable()) {
                // 检测能不能连接接口
                if (!TextUtils.isEmpty(NetworkManager.nm.networkAvailable())) return true;
            }
        }
        return false;
    }
}
