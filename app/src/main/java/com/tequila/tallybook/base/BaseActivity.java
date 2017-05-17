package com.tequila.tallybook.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.tequila.tallybook.mode.ResultModel;
import com.tequila.tallybook.net.DataService;
import com.tequila.tallybook.net.NetUtils;
import com.tequila.tallybook.utils.ProgressUtils;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Tequila on 2017/5/8.
 */

public class BaseActivity extends AppCompatActivity {

    public KProgressHUD kProgressHUD;
    // 屏幕的宽高
    public static int screen_width = 0;
    public static int screen_height = 0;

    InputMethodManager imm; // 输入法管理

    private DataService dataService = NetUtils.getDataService();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);  // 获取手机屏幕的大小
        screen_width = dm.widthPixels;
        screen_height = dm.heightPixels;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);   // 强制使得主线程可以访问网络

        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        //禁止旋转屏幕
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public DataService getDataService() {
        return dataService;
    }

    /**
     * 等待显示
     * */
    public void showWait() {
        kProgressHUD = ProgressUtils.showWait(this);
    }

    public void showWait(String msg) {
        kProgressHUD = ProgressUtils.showWait(this, msg);
    }

    /**
     * 等待消失
     * */
    public void hideWait() {
        if (kProgressHUD != null) {
            kProgressHUD.dismiss();
        }
    }

    // 关闭输入法
    public void closeInputMethod() {

        boolean isOpen = imm.isActive();
        if (isOpen) {
            imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    // 显示Toast
    public void showToast(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }

    // 显示中间的Toast
    public void showCenterToase(String info) {

        Toast toast = Toast.makeText(this, info, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    // 判断网络是否可用
    public boolean isNetworkAvailable() {
        // 得到网络连接信息
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        // 去进行判断网络是否连接
        if (manager.getActiveNetworkInfo() != null) {
            if (manager.getActiveNetworkInfo().isAvailable()) {
                // 检测能不能连接接口
                if (!TextUtils.isEmpty(networkAvailable())) return true;
            }
        }
        return false;
    }

    /**
     * 验证程序能否访问服务器
     * @return
     */
    private String networkAvailable() {
        String str = "";

        Call<ResultModel> call = getDataService().networkVerify();

        try {
            Response<ResultModel> response = call.execute();
            ResultModel model = response.body();
            if (model.getSucceedFlag().equals("1")) {
                str = model.getReturnInfo();
            }
        } catch (IOException e) {
            e.printStackTrace();
            str = "";
        }

        return str;
    }
}
