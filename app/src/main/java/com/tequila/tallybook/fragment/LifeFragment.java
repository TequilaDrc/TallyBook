package com.tequila.tallybook.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.tequila.tallybook.R;
import com.tequila.tallybook.base.BaseFragment;
import com.tequila.tallybook.bean.UserBean;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Tequila on 2017/5/4.
 */

public class LifeFragment extends BaseFragment {

    private View mRootView;
    private UserBean bean = new UserBean();

    @Bind(R.id.edit_money)
    EditText edit_money;
    @Bind(R.id.edit_plu)
    EditText edit_plu;
    @Bind(R.id.edit_remark)
    EditText edit_remark;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        if(mRootView == null){
            mRootView = inflater.inflate(R.layout.life_fragment, container, false);
        }

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

        
    }

    @Override
    public boolean onBackPressed() {
        return true;
    }
}
