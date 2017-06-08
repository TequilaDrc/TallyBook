package com.tequila.tallybook.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.tequila.tallybook.R;
import com.tequila.tallybook.base.BaseActivity;
import com.tequila.tallybook.mode.ResultModel;
import com.tequila.tallybook.mode.UsrUserModel;
import com.tequila.tallybook.utils.CommonAsyncTask;
import com.tequila.tallybook.utils.SysApplication;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Tequila on 2017/5/15.
 */

public class RegisterActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.et_userName)
    EditText et_userName;
    @Bind(R.id.et_phoneNumber)
    EditText et_phoneNumber;
    @Bind(R.id.et_passWord)
    EditText et_passWord;

    private String userName, phoneNumber, password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        SysApplication.getInstance().addActivity(this);

        ButterKnife.bind(this);

        initToolbar(toolbar);
    }

    private void initToolbar(Toolbar toolbar) {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoLoginActivity();
            }
        });
        toolbar.setNavigationIcon(R.mipmap.ic_back_arrow);
    }

    /**
     * 注册按钮
     */
    @OnClick(R.id.btn_register)
    public void registerClik() {
        if (TextUtils.isEmpty(et_userName.getText().toString())) {
            showCenterToase("用户名不能为空");
            return;
        }

        if (TextUtils.isEmpty(et_phoneNumber.getText().toString())) {
            showCenterToase("手机号不能为空");
            return;
        } else {
            if (!isMobileNO(et_phoneNumber.getText().toString())) {
                showCenterToase("请输入正确的手机号");
                return;
            }
        }

        userName = et_userName.getText().toString();
        phoneNumber = et_phoneNumber.getText().toString();
        password = et_passWord.getText().toString();

        RegisterAsyncTask task = new RegisterAsyncTask(this, "注册中...");
        task.execute(userName, phoneNumber, password);
    }

    private class RegisterAsyncTask extends CommonAsyncTask<ResultModel> {

        public RegisterAsyncTask(Context context, String waitStr) {
            super(context, waitStr);
        }

        @Override
        public ResultModel convert(Object[] obj) {
            ResultModel model = new ResultModel();

            try {
                Call<ResultModel> call = getDataService().addUser(obj[0].toString(), obj[1].toString(), obj[2].toString());
                Response<ResultModel> response = call.execute();
                model = response.body();
            } catch (IOException e) {
                e.printStackTrace();
                model.setSucceedFlag("0");
                model.setErrorInfo(e.getMessage());
            }

            return model;
        }

        @Override
        public void setTData(ResultModel model) {
            if (model.getSucceedFlag().equals("1")) {
                showCenterToase("注册成功!");

                UsrUserModel userModel = new UsrUserModel();
                userModel.setsUserName(userName);
                userModel.setsUserPhone(phoneNumber);
                userModel.setsPasswd(password);

                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", userModel);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            } else {
                showCenterToase("注册失败!" + model.getErrorInfo());
            }
        }
    }

    /**
     * 判断是否是输入正确的手机号
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {

        String telRegex = "[1][3458]\\d{9}";//"[1]"代表第1位为数字1，"[3458]"代表第二位可以为3、4、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。

        return mobiles.matches(telRegex);
    }

    /**
     * 跳转登陆界面
     */
    private void gotoLoginActivity() {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        finish();
    }

    /**
     * 获取返回键点击退出
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            gotoLoginActivity();

            return false;
        }
        return false;
    }
}
