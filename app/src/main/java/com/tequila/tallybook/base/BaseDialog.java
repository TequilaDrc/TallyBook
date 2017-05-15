package com.tequila.tallybook.base;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.widget.Toast;

/**
 * Created by Tequila on 2017/5/15.
 */

public class BaseDialog extends Dialog {

    protected Context context;

    public BaseDialog(Context context) {
        super(context);
        this.context = context;

        requestWindowFeature(Window.FEATURE_NO_TITLE);// android:windowNoTitle
        setCanceledOnTouchOutside(false);
    }

    // 显示Toast
    public void showToast(String info) {
        Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
    }

    // 显示中间的Toast
    public void showCenterToase(String info) {

        Toast toast = Toast.makeText(context, info, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
