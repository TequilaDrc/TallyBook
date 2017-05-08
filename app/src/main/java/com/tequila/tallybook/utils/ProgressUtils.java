package com.tequila.tallybook.utils;

import android.content.Context;

import com.kaopiz.kprogresshud.KProgressHUD;

/**
 * Created by Tequila on 2017/5/8.
 */

public class ProgressUtils {

    public static KProgressHUD showWait(Context context) {
        return showWait(context, "请稍后...");
    }

    public static KProgressHUD showWait(Context context, String msg) {
        return KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(msg)
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
    }
}
