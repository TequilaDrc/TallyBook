package com.tequila.tallybook.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tequila.tallybook.R;
import com.tequila.tallybook.base.BaseFragment;
import com.tequila.tallybook.dialog.AccountDialog;
import com.tequila.tallybook.mode.AccountDetailsModel;
import com.tequila.tallybook.mode.ResultModel;
import com.tequila.tallybook.mode.TimeTrace;
import com.tequila.tallybook.utils.CommonAsyncTask;
import com.tequila.tallybook.utils.adapter.TimeTraceListAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Tequila on 2017/5/4.
 */

public class AccountFragment extends BaseFragment {

    @Bind(R.id.timeLine)
    RecyclerView timeLine;

    private View mRootView;
    private TimeTraceListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(mRootView == null){
            mRootView = inflater.inflate(R.layout.account_fragment, container, false);
        }

        ViewGroup mViewGroup = (ViewGroup)mRootView.getParent();
        if(mViewGroup!=null){
            mViewGroup.removeView(mRootView);
        }

        ButterKnife.bind(this, mRootView);
        getData();

        return mRootView;
    }

    private void getData() {

        AccountAsyncTask task = new AccountAsyncTask(getContext(), "获取数据中...");
        task.execute("");

    }

    private class AccountAsyncTask extends CommonAsyncTask<List<TimeTrace>> {
        public AccountAsyncTask(Context context, String waitStr) {
            super(context, waitStr);
        }

        @Override
        public List<TimeTrace> convert(Object[] obj) {

            List<TimeTrace> list = new ArrayList<>();
            list.clear();

            try {
                Call<ResultModel> call = getDataService().getLifeData();
                Response<ResultModel> response = call.execute();
                ResultModel model = response.body();
                if (model.getSucceedFlag().equals("1")) {

                    String lstData = model.getReturnInfo().toString();
                    list = new Gson().fromJson(lstData, new TypeToken<List<TimeTrace>>(){}.getType());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return list;
        }

        @Override
        public void setTData(final List<TimeTrace> timeTraces) {

            adapter = new TimeTraceListAdapter(getContext(), timeTraces);
            timeLine.setLayoutManager(new LinearLayoutManager(getContext()));
            timeLine.setAdapter(adapter);
            adapter.setOnItemClickListener(new TimeTraceListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    AccountDetailsAsyncTask task = new AccountDetailsAsyncTask(getContext(), "请稍等...");
                    task.execute(timeTraces.get(position).getsBillNo());
                }
            });
        }
    }

    private class AccountDetailsAsyncTask extends CommonAsyncTask<List<AccountDetailsModel>> {

        public AccountDetailsAsyncTask(Context context, String waitStr) {
            super(context, waitStr);
        }

        @Override
        public List<AccountDetailsModel> convert(Object[] obj) {

            List<AccountDetailsModel> list = new ArrayList<>();
            list.clear();

            try {
                Call<ResultModel> call = getDataService().getAccountDetails(obj[0].toString());
                Response<ResultModel> response = call.execute();
                ResultModel model = response.body();
                if (model.getSucceedFlag().equals("1")) {

                    String lstData = model.getReturnInfo().toString();
                    list = new Gson().fromJson(lstData, new TypeToken<List<AccountDetailsModel>>(){}.getType());

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return list;
        }

        @Override
        public void setTData(List<AccountDetailsModel> accountDetailsModels) {
            AccountDialog dialog = new AccountDialog(getContext(), accountDetailsModels);
            dialog.show();
        }
    }

    @Override
    public boolean onBackPressed() {
        return true;
    }
}
