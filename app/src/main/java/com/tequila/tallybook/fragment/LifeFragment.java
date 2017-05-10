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

public class LifeFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.life_fragment, container, false);
        return view;
    }

    @Override
    public boolean onBackPressed() {
        return true;
    }
}
