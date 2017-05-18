package com.tequila.tallybook.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tequila.tallybook.R;
import com.tequila.tallybook.base.BaseFragment;
import com.tequila.tallybook.mode.ResultModel;
import com.tequila.tallybook.mode.UsrUserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Tequila on 2017/5/4.
 */

public class LifeFragment extends BaseFragment {

    private List<UsrUserModel> lstData = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.life_fragment, container, false);

//        getData();
        return view;
    }

    private void getData() {

        Call<ResultModel> call = getDataService().synchronousData();
        call.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                ResultModel resultModel = response.body();
                if (resultModel.getSucceedFlag().equals("1")) {
                    showCenterToase(resultModel.getReturnInfo().toString());
                }
            }

            @Override
            public void onFailure(Call<ResultModel> call, Throwable t) {
                showCenterToase(t.getMessage());
            }
        });
    }

    @Override
    public boolean onBackPressed() {
        return true;
    }
}
