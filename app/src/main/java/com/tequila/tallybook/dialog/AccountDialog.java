package com.tequila.tallybook.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tequila.tallybook.R;
import com.tequila.tallybook.base.BaseDialog;
import com.tequila.tallybook.event.AccountEvent;
import com.tequila.tallybook.mode.AccountDetailsModel;
import com.tequila.tallybook.mode.ResultModel;
import com.tequila.tallybook.net.NetworkManager;
import com.tequila.tallybook.net.query.AlterTallyDataQuery;
import com.tequila.tallybook.utils.CommonAsyncTask;
import com.tequila.tallybook.utils.Preference;
import com.tequila.tallybook.utils.adapter.CommonAdapter;
import com.tequila.tallybook.utils.adapter.ViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Tequila on 2017/6/14.
 */

public class AccountDialog extends BaseDialog {

    @Bind(R.id.lv_details)
    ListView lv_details;

    private List<AccountDetailsModel> lstData;
    private AccountAdapter adapter;
    private String sBillNo = "";

    public AccountDialog(Context context, List<AccountDetailsModel> lstData, String sBillNo) {
        super(context);

        this.lstData = lstData;
        this.sBillNo = sBillNo;

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

    @OnClick(R.id.btn_delete)
    public void delete() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("确定删除这条信息吗?");
        builder.setTitle("提示");

        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String userName = Preference.getInstance(getContext()).getLoginName();

                if (TextUtils.isEmpty(userName)) {
                    showCenterToase("用户名为空!");
                    return;
                }

                AlterTallyDataQuery query = new AlterTallyDataQuery();
                query.setsBillNo(sBillNo);
                query.setsMakerName(userName);

                String dataJson = new Gson().toJson(query, new TypeToken<AlterTallyDataQuery>(){}.getType());
                if (NetworkManager.nm.isNetworkAvailable(getContext())) {

                    deleteDataAsyncTask task = new deleteDataAsyncTask(getContext(), "保存中...");
                    task.execute(dataJson);
                }
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

    private class deleteDataAsyncTask extends CommonAsyncTask<ResultModel> {

        public deleteDataAsyncTask(Context context, String waitStr) {
            super(context, waitStr);
        }

        @Override
        public ResultModel convert(Object[] obj) {

            ResultModel model = new ResultModel();

            try {

                RequestBody requestBody =
                        RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj[0].toString());

                Call<ResultModel> call = getDataService().alterTallyData(requestBody);
                Response<ResultModel> response = call.execute();
                model = response.body();

            } catch (IOException e) {
                e.printStackTrace();
                model.setErrorInfo(e.getMessage());
                model.setSucceedFlag("0");
            }

            return model;
        }

        @Override
        public void setTData(ResultModel model) {

            if (model.getSucceedFlag().equals("1")) {
                showCenterToase("删除成功!");

                dismiss();

                AccountEvent event = new AccountEvent();
                event.setFlag(true);
                EventBus.getDefault().post(event);

            } else if (model.getSucceedFlag().equals("2")) {
                showCenterToase("这不是您做的记录，您无权删除!");
            } else {
                showCenterToase(model.getErrorInfo());
            }
        }
    }

    @OnClick(R.id.btn_update)
    public void update() {
        showCenterToase("该功能尚未开放!");
    }

    @OnClick(R.id.btn_cancel)
    public void cancel() {
        dismiss();
    }
}
