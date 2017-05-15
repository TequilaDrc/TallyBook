package com.tequila.tallybook.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.tequila.tallybook.R;
import com.tequila.tallybook.base.BaseDialog;
import com.tequila.tallybook.event.ExitEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Tequila on 2017/5/15.
 */

public class ExitDialog extends BaseDialog {


    public ExitDialog(Context context) {
        super(context);

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_exit, null);
        setContentView(view);

        ButterKnife.bind(this, view);
    }

    @OnClick(R.id.btn_goLogin)
    public void goLogin() {
        ExitEvent event = new ExitEvent();
        event.setItem(0);
        EventBus.getDefault().post(event);
    }

    @OnClick(R.id.btn_exit)
    public void goExit() {
        ExitEvent event = new ExitEvent();
        event.setItem(1);
        EventBus.getDefault().post(event);
    }

    @OnClick(R.id.btn_cancel)
    public void cancel() {
        dismiss();
    }
}
