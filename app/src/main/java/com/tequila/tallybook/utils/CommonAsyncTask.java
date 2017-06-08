package com.tequila.tallybook.utils;

import android.content.Context;
import android.os.AsyncTask;

import com.kaopiz.kprogresshud.KProgressHUD;

/**
 * Created by Tequila on 2017/4/28.
 *
 * 异步处理的抽象类
 */

public abstract class CommonAsyncTask<T> extends AsyncTask<Object, Integer, T> {

    private KProgressHUD kProgressHUD;
    private String waitStr = null;
    private Context context;

    public CommonAsyncTask(Context context, String waitStr) {
        super();
        this.context = context;
        this.waitStr = waitStr;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        if (waitStr == "" || waitStr == null)
            waitStr = "请稍后...";

        kProgressHUD = ProgressUtils.showWait(context, waitStr);

    }

    @Override
    protected T doInBackground(Object... params) {

        Object[] obj = null;

        if (params.length <= 0) {
            obj = null;
        } else {
            obj = new Object[params.length];
            for (int i = 0; i < params.length; i++) {
                obj[i] = params[i];
            }
        }

        T t = convert(obj);

        return t;
    }

    @Override
    protected void onPostExecute(T t) {
        super.onPostExecute(t);

        setTData(t);

        hideWait();
    }

    public abstract T convert(Object[] obj);
    public abstract void setTData(T t);

    /**
     * 关闭等待框
     */
    private void hideWait() {
        if (kProgressHUD != null) {
            kProgressHUD.dismiss();
        }
    }
}
