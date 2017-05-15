package com.tequila.tallybook.base;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Tequila on 2017/5/15.
 */

public class BaseDialog extends Dialog {

    protected Context context;

    public BaseDialog(Context context) {
        super(context);
        setDialogTheme();
        this.context = context;
    }

    /**
     * set dialog theme(设置对话框主题)
     */
    private void setDialogTheme() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);// android:windowNoTitle
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));// android:windowBackground
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);// android:backgroundDimEnabled默认是true的
        setCanceledOnTouchOutside(true);
    }
}
