package com.tequila.tallybook.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Tequila on 2017/5/17.
 * 文件记录数据类
 */

public class Preference {

    private static Preference preference = null;
    private SharedPreferences sharedPreference;
    private String packageName = "";

    private static final String LOGIN_NAME = "loginName";   // 用户名称
    private static final String PASSWORD = "password";      // 用户密码
    private static final String LOGIN_PHONE = "loginPhone"; // 用户手机号

    public static synchronized Preference getInstance(Context context){
        if(preference == null)
            preference = new Preference(context);
        return preference;
    }

    public Preference(Context context) {
        packageName = context.getPackageName() + "_preferences";
        sharedPreference = context.getSharedPreferences(
                packageName, context.MODE_PRIVATE);
    }

    public String getLoginName() {
        String loginName = sharedPreference.getString(LOGIN_NAME, "");
        return loginName;
    }

    public void setLoginName(String loginName) {
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString(LOGIN_NAME, loginName);
        editor.commit();
    }

    public String getPassword() {
        String password = sharedPreference.getString(PASSWORD, "");
        return password;
    }

    public void setPassword(String password) {
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString(PASSWORD, password);
        editor.commit();
    }

    public String getLoginPhone() {
        String loginPhone = sharedPreference.getString(LOGIN_PHONE, "");
        return loginPhone;
    }

    public void setLoginPhone(String loginPhone) {
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString(LOGIN_PHONE, loginPhone);
        editor.commit();
    }
}
