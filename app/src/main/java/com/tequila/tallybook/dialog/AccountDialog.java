package com.tequila.tallybook.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.tequila.tallybook.R;
import com.tequila.tallybook.base.BaseDialog;
import com.tequila.tallybook.mode.AccountDetailsModel;
import com.tequila.tallybook.utils.adapter.CommonAdapter;
import com.tequila.tallybook.utils.adapter.ViewHolder;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Tequila on 2017/6/14.
 */

public class AccountDialog extends BaseDialog {

    @Bind(R.id.lv_details)
    ListView lv_details;

    private List<AccountDetailsModel> lstData;
    private AccountAdapter adapter;

    public AccountDialog(Context context, List<AccountDetailsModel> lstData) {
        super(context);

        this.lstData = lstData;

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_account, null);
        setContentView(view);

        ButterKnife.bind(this, view);

        adapter = new AccountAdapter(getContext(), R.layout.item_account);
        lv_details.setAdapter(adapter);
    }

    private class AccountAdapter extends CommonAdapter<AccountDetailsModel> {

        public AccountAdapter(Context context, int viewId) {
            super(context, viewId, lstData);
        }

        @Override
        public void convert(ViewHolder holder, AccountDetailsModel accountDetailsModel) {

            holder.setText(R.id.tvUserName, "名        称 : " + accountDetailsModel.getsUserName())
                    .setText(R.id.tvPrice, "平均消费 : " + accountDetailsModel.getfPrice() + " 元");
        }
    }

    @OnClick(R.id.btn_cancel)
    public void cancel() {
        dismiss();
    }
}
