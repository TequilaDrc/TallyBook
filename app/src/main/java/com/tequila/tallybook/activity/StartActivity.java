package com.tequila.tallybook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.tequila.tallybook.R;
import com.tequila.tallybook.utils.Preference;

/**
 * Created by tequila on 2017/5/3.
 */

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                String loginName = Preference.getInstance(StartActivity.this).getLoginName();
                String passwd = Preference.getInstance(StartActivity.this).getPassword();

                if ((!TextUtils.isEmpty(loginName)) && (!TextUtils.isEmpty(passwd))) {
                    startActivity(new Intent(StartActivity.this, MainActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(StartActivity.this, LoginActivity.class));
                    finish();
                }
            }
        }, 1000);
    }
}
