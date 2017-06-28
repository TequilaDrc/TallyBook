package com.tequila.tallybook.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tequila.tallybook.R;
import com.tequila.tallybook.base.BaseFragment;
import com.tequila.tallybook.bean.UserBean;
import com.tequila.tallybook.dialog.ExitDialog;
import com.tequila.tallybook.mode.ResultModel;
import com.tequila.tallybook.net.NetworkManager;
import com.tequila.tallybook.net.query.saveLifeInfoQuery;
import com.tequila.tallybook.utils.CommonAsyncTask;
import com.tequila.tallybook.utils.Preference;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Tequila on 2017/5/4.
 */

public class LifeFragment extends BaseFragment {

    private View mRootView;
    private UserBean bean = new UserBean();
    private String loginName;

    @Bind(R.id.edit_money)
    EditText edit_money;
    @Bind(R.id.edit_plu)
    EditText edit_plu;
    @Bind(R.id.edit_remark)
    EditText edit_remark;

    @Bind(R.id.btnDai)
    ImageButton btnDai;
    @Bind(R.id.btnYang)
    ImageButton btnYang;
    @Bind(R.id.btnXu)
    ImageButton btnXu;
    @Bind(R.id.btnZhou)
    ImageButton btnZhou;
    @Bind(R.id.btnZhang)
    ImageButton btnZhang;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(mRootView == null){
            mRootView = inflater.inflate(R.layout.life_fragment, container, false);
        }

        loginName = Preference.getInstance(getContext()).getLoginName();

        ViewGroup mViewGroup = (ViewGroup)mRootView.getParent();
        if(mViewGroup!=null){
            mViewGroup.removeView(mRootView);
        }

        ButterKnife.bind(this, mRootView);

        return mRootView;
    }

    @OnClick({R.id.btnDai, R.id.btnYang, R.id.btnXu, R.id.btnZhou, R.id.btnZhang})
    public void btnNameClick(View view) {
        switch (view.getId()) {
            case R.id.btnDai:
                if (bean.isDai()) {
                    bean.setDai(false);
                    view.setBackground(getResources().getDrawable(R.drawable.blackdai));
                } else {
                    bean.setDai(true);
                    view.setBackground(getResources().getDrawable(R.drawable.bluedai));
                }
                break;
            case R.id.btnYang:
                if (bean.isYang()) {
                    bean.setYang(false);
                    view.setBackground(getResources().getDrawable(R.drawable.blackyang));
                } else {
                    bean.setYang(true);
                    view.setBackground(getResources().getDrawable(R.drawable.blueyang));
                }
                break;
            case R.id.btnXu:

                if (bean.isXu()) {
                    bean.setXu(false);
                    view.setBackground(getResources().getDrawable(R.drawable.blackxu));
                } else {
                    bean.setXu(true);
                    view.setBackground(getResources().getDrawable(R.drawable.bluexu));
                }
                break;
            case R.id.btnZhou:
                if (bean.isZhou()) {
                    bean.setZhou(false);
                    view.setBackground(getResources().getDrawable(R.drawable.blackzhou));
                } else {
                    bean.setZhou(true);
                    view.setBackground(getResources().getDrawable(R.drawable.bluezhou));
                }
                break;
            case R.id.btnZhang:
                if (bean.isZhang()) {
                    bean.setZhang(false);
                    view.setBackground(getResources().getDrawable(R.drawable.blackzhang));
                } else {
                    bean.setZhang(true);
                    view.setBackground(getResources().getDrawable(R.drawable.bluezhang));
                }
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.btn_save)
    public void doSave() {
        if (bean.getPeopleNumber() == 0) {
            showCenterToase("请选择人数");
            return;
        }

        if (TextUtils.isEmpty(edit_money.getText().toString())) {
            showCenterToase("请填写金额");
            return;
        }

        if (TextUtils.isEmpty(edit_plu.getText().toString())) {
            showCenterToase("请填写购买物品");
            return;
        }

        if (TextUtils.isEmpty(loginName)) {
            loginName = Preference.getInstance(getContext()).getLoginName();
        }

        saveLifeInfoQuery saveInfo = new saveLifeInfoQuery();
        saveInfo.setsMakerName(loginName);
        saveInfo.setPeopleNumber(bean.getPeopleNumber());
        saveInfo.setPeopleName(bean.getPeopleName());
        float money = Float.parseFloat(edit_money.getText().toString());
        saveInfo.setMoney(money);
        saveInfo.setPluInfo(edit_plu.getText().toString());
        saveInfo.setRemark(edit_remark.getText().toString());

        String saveJson = new Gson().toJson(saveInfo, new TypeToken<saveLifeInfoQuery>(){}.getType());
        if (NetworkManager.nm.isNetworkAvailable(getContext())) {

            saveLifeInfoAsyncTask task = new saveLifeInfoAsyncTask(getContext(), "保存中...");
            task.execute(saveJson);
        }
    }

    private class saveLifeInfoAsyncTask extends CommonAsyncTask<ResultModel> {

        public saveLifeInfoAsyncTask(Context context, String waitStr) {
            super(context, waitStr);
        }

        @Override
        public ResultModel convert(Object[] obj) {

            ResultModel model = new ResultModel();

            try {

                RequestBody requestBody =
                    RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj[0].toString());

                Call<ResultModel> call = getDataService().saveLifeInfo(requestBody);
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
                showCenterToase("保存成功!");
                clear();
            } else {
                showCenterToase(model.getErrorInfo());
            }
        }
    }

    private void clear() {
        bean.setDai(false);
        bean.setXu(false);
        bean.setYang(false);
        bean.setZhang(false);
        bean.setZhou(false);
        btnDai.setBackground(getResources().getDrawable(R.drawable.blackdai));
        btnXu.setBackground(getResources().getDrawable(R.drawable.blackxu));
        btnYang.setBackground(getResources().getDrawable(R.drawable.blackyang));
        btnZhang.setBackground(getResources().getDrawable(R.drawable.blackzhang));
        btnZhou.setBackground(getResources().getDrawable(R.drawable.blackzhou));

        edit_money.setText("");
        edit_plu.setText("");
        edit_remark.setText("");
    }

    @Override
    public boolean onBackPressed() {

        ExitDialog dialog = new ExitDialog(getContext());
        dialog.show();

        return true;
    }
}
