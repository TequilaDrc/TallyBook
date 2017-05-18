package com.tequila.tallybook.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tequila.tallybook.R;
import com.tequila.tallybook.base.BaseFragment;

/**
 * Created by Tequila on 2017/5/4.
 */

public class AccountFragment extends BaseFragment {

    private View mRootView;

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

        return mRootView;
    }

    @Override
    public boolean onBackPressed() {
        return true;
    }
}
