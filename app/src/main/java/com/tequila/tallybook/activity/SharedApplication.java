package com.tequila.tallybook.activity;

import android.app.Application;
import android.content.Context;

import com.tequila.tallybook.datasource.CustomDBHelper;
import com.tequila.tallybook.utils.CrashExceptionHandler;
import com.tequila.tallybook.utils.FolderManager;

import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by tequila on 2017/4/13.
 */

public class SharedApplication extends Application {

    static SharedApplication app;
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        app = this;
        context = this;

        new CustomDBHelper(context).getReadableDatabase().close();

        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));		// 设置默认时区的，设置的是上海的时区。
        Locale.setDefault(Locale.CHINA);

//        configCollectCrashInfo();
    }

    /**
     * 配置奔溃信息的搜集
     */
    private void configCollectCrashInfo() {
        CrashExceptionHandler crashExceptionHandler = new CrashExceptionHandler(this, FolderManager.getCrashLogFolder());
        Thread.setDefaultUncaughtExceptionHandler(crashExceptionHandler);
    }

    public static SharedApplication getInstance() {

        if (app != null) {
            return app;
        } else {
            return null;
        }
    }
}
