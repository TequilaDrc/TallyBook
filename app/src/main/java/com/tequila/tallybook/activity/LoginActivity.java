package com.tequila.tallybook.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.tequila.tallybook.Debug;
import com.tequila.tallybook.R;
import com.tequila.tallybook.base.BaseActivity;
import com.tequila.tallybook.mode.ResultModel;
import com.tequila.tallybook.mode.UsrUserModel;
import com.tequila.tallybook.net.NetworkManager;
import com.tequila.tallybook.utils.CommonAsyncTask;
import com.tequila.tallybook.utils.Preference;
import com.tequila.tallybook.utils.SysApplication;
import com.tequila.tallybook.view.TextURLView;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Tequila on 2017/5/8.
 */

public class LoginActivity extends BaseActivity{

    @Bind(R.id.rl_user)
    RelativeLayout rl_user;
    @Bind(R.id.tv_forget_password)
    TextURLView mTextViewURL;
    @Bind(R.id.account)
    EditText account;
    @Bind(R.id.password)
    EditText password;

    private UsrUserModel user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = (UsrUserModel)getIntent().getSerializableExtra("user");

        ButterKnife.bind(this);

        mTextViewURL.setText(R.string.forget_password);

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.login_anim);
        anim.setFillAfter(true);
        rl_user.startAnimation(anim);

        String loginName = Preference.getInstance(this).getLoginName();
        String passwd = Preference.getInstance(this).getPassword();

        if (!TextUtils.isEmpty(loginName)) account.setText(loginName);
        if (!TextUtils.isEmpty(passwd)) password.setText(passwd);

        if (user != null) {
            account.setText(user.getsUserName());
            password.setText(user.getsPasswd());
        }
    }

    /**
     * 登陆按钮
     */
    @OnClick(R.id.login)
    public void login() {

        String accountStr = account.getText().toString();
        String passwordStr = password.getText().toString();

        if (TextUtils.isEmpty(accountStr)) {
            showCenterToase("账号不能为空");
            return;
        }

        if (TextUtils.isEmpty(passwordStr)) {
            showCenterToase("密码不能为空");
            return;
        }

        if (!Debug.isDebug()) {
            if (!NetworkManager.nm.isNetworkAvailable(this)) {
                showCenterToase("网络异常,请检查网络!");
                return;
            }

            LoginasyncTask task = new LoginasyncTask(this, "登陆中...");
            task.execute(accountStr, passwordStr);
        } else {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    private class LoginasyncTask extends CommonAsyncTask<ResultModel> {

        public LoginasyncTask(Context context, String waitStr) {
            super(context, waitStr);
        }

        @Override
        public ResultModel convert(Object[] obj) {

            ResultModel model = null;

            Call<ResultModel> call = getDataService().loginvalidation(obj[0].toString(), obj[1].toString());
            try {
                Response<ResultModel> response = call.execute();
                model = response.body();

            } catch (IOException e) {
                model = new ResultModel();
                model.setSucceedFlag("0");
                model.setErrorInfo(e.getMessage());
                model.setReturnInfo("");
            }

            return model;
        }

        @Override
        public void setTData(ResultModel model) {

            if (model.getSucceedFlag().equals("1")) {
                UsrUserModel userModel = new Gson().fromJson(model.getReturnInfo().toString(), UsrUserModel.class);

                if (userModel != null) {

                    if (!TextUtils.isEmpty(userModel.getsUserName())) {
                        Preference.getInstance(LoginActivity.this).setLoginName(userModel.getsUserName());
                        Preference.getInstance(LoginActivity.this).setPassword(userModel.getsPasswd());
                        Preference.getInstance(LoginActivity.this).setLoginPhone(userModel.getsUserPhone());

                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }
                }
            } else if (model.getSucceedFlag().equals("0")) {
                showCenterToase(model.getErrorInfo());
            } else if (model.getSucceedFlag().equals("2")) {
                showCenterToase("密码与账号不符,请检查账号与密码");
            }
        }
    }

    /**
     * 注册按钮
     */
    @OnClick(R.id.register)
    public void register() {
        if (NetworkManager.nm.isNetworkAvailable(this)) {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            finish();
        } else {
            showCenterToase("网络异常,请检查网络!");
        }
    }

    /**
     * 忘记密码按钮
     */
    @OnClick(R.id.tv_forget_password)
    public void forgetPassword() {

        if (NetworkManager.nm.isNetworkAvailable(this)) {
            showToast("忘记密码");
        } else {
            showCenterToase("网络异常,请检查网络!");
        }
    }

    /**
     * 退出Dialog
     * */
    protected  void ExitDiaLog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setMessage("确认退出吗?");
        builder.setTitle("提示");

        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                SysApplication.getInstance().exit();
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.create().show();
    }

    /**
     * 获取返回键点击退出
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            ExitDiaLog();

            return false;
        }
        return false;
    }
}
