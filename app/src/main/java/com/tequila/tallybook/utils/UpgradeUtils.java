package com.tequila.tallybook.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tequila.tallybook.mode.ResultModel;
import com.tequila.tallybook.net.NetUtils;
import com.tequila.tallybook.net.query.UpdataQuery;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 系统升级
 * Created by lidongfeng on 16/4/29.
 */
public class UpgradeUtils {

    private static UpdataQuery query;

    public static UpdataQuery getQuery() {
        return query;
    }

    public static void setQuery(UpdataQuery q) {
        query = q;
    }

    // 给设备程序识别版本(升级)用的
    public static int getVerCode(Context context) {
        int verCode = -1;
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);

            verCode = info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return verCode;
    }

    // 给用户看的
    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return verName;
    }

    // 检查服务器端版本号，如果有新版本，则发送消息到事件总线，主窗口获得提醒通知
    public static void checkVersion(final Context context, final int i) {

        Call<ResultModel> call = NetUtils.getDataService().getNewApkClientInfo();
        call.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                ResultModel rst = response.body();
                String data = rst.getReturnInfo().toString();
                Gson gson = new Gson();
                setQuery(gson.fromJson(data, UpdataQuery.class));

                if (i == 0) {
                    if (CompareVersion.compare(getQuery().getVersion(), getVerName(context)) == 1) {
                        UpdataDiaLog(context, "有新版本需要下载！", getQuery());
                    } else {
                        UpdataDiaLog(context, "没有新版本！", null);
                    }
                } else if (i == 1) {
                    if (CompareVersion.compare(getQuery().getVersion(), getVerName(context)) == 1) {
                        UpdataDiaLog(context, "最新版本为：v" + getQuery().getVersion() + " 是否需要下载", getQuery());
                    } else {
                        UpdataDiaLog(context, "本版本为最新版本！", null);
                    }
                } else {
                    if (CompareVersion.compare(getQuery().getVersion(), getVerName(context)) == 1) {
                        UpdataDiaLog(context, "有新版本需要下载！", getQuery());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public static File getFileFromServer(String path, ProgressDialog pd) throws Exception {
        //如果相等的话表示当前的sdcard挂载在手机上并且是可用的
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            //获取到文件的大小
            pd.setMax(conn.getContentLength());
            InputStream is = conn.getInputStream();
            File file = new File(Environment.getExternalStorageDirectory(), "wanrun.apk");
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len;
            int total = 0;
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                total += len;
                //获取当前下载量
                pd.setProgress(total);
            }
            fos.close();
            bis.close();
            is.close();
            return file;
        } else {
            return null;
        }
    }

    private static void UpdataDiaLog(final Context context, String str, final UpdataQuery query) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(str);
        builder.setTitle("提示");

        if (query != null) {
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();

                    CheckVersionTask cvt = new CheckVersionTask(context);
                    cvt.run();
                }
            });
        }

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    private static class CheckVersionTask implements Runnable {

        private static Context context;
        private static UpdataQuery query;

        public CheckVersionTask(Context context) {
            this.context = context;
        }

        public void run() {
            Message msg = new Message();
            msg.what = 1;
            msg.obj = context;
            handler.sendMessage(msg);
        }
    }

    static Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {

                case 1: {

                    downLoadApk(msg.obj);
                }
                break;
                case -1: {
                    Toast.makeText((Context) msg.obj, "下载新版本失败", Toast.LENGTH_SHORT).show();
                }
                break;
                default:
                    break;
            }
        }
    };

    // 从服务器中下载APK
    private static void downLoadApk(final Object object) {

        final ProgressDialog pd;    //进度条对话框
        pd = new  ProgressDialog((Context) object);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在下载更新");
        pd.show();
        new Thread(){
            @Override
            public void run() {
                try {
                    File file = getFileFromServer(getQuery().getUrl(), pd);
                    sleep(1000);
                    installApk(file, object);
                    pd.dismiss(); //结束掉进度条对话框
                } catch (Exception e) {
                    Message msg = new Message();
                    msg.what = -1;
                    handler.sendMessage(msg);
                    e.printStackTrace();
                }
            }}.start();
    }

    //安装apk
    protected static void installApk(File file, Object object) {
        Context context = (Context) object;
        Intent intent = new Intent();
        // 执行动作
        intent.setAction(Intent.ACTION_VIEW);
        // 如果没有setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);这一步的话，最后安装好了，点打开，是不会打开新版本应用的。
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // 执行的数据类型
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        context.startActivity(intent);
        // 如果没有android.os.Process.killProcess(android.os.Process.myPid());最后不会提示完成、打开。
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}