package com.tequila.tallybook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.tequila.tallybook.R;
import com.tequila.tallybook.base.BaseActivity;
import com.tequila.tallybook.view.TextURLView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        mTextViewURL.setText(R.string.forget_password);

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.login_anim);
        anim.setFillAfter(true);
        rl_user.startAnimation(anim);
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

        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    /**
     * 注册按钮
     */
    @OnClick(R.id.register)
    public void register() {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        finish();
    }

    /**
     * 忘记密码按钮
     */
    @OnClick(R.id.tv_forget_password)
    public void forgetPassword() {
        showToast("忘记密码");
    }
}
