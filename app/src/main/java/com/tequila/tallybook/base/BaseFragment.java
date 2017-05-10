package com.tequila.tallybook.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.widget.Toast;

import com.tequila.tallybook.utils.BackHandledInterface;

/**
 * Created by Tequila on 2017/5/9.
 */

public abstract class BaseFragment extends Fragment {

    protected BackHandledInterface mBackHandledInterface;

    /**
     * 所有继承BackHandledFragment的子类都将在这个方法中实现物理Back键按下后的逻辑
     * FragmentActivity捕捉到物理返回键点击事件后会首先询问Fragment是否消费该事件
     * 如果没有Fragment消息时FragmentActivity自己才会消费该事件
     */
    public abstract boolean onBackPressed();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!(getActivity() instanceof BackHandledInterface)){
            throw new ClassCastException("Hosting Activity must implement BackHandledInterface");
        }else{
            this.mBackHandledInterface = (BackHandledInterface)getActivity();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //告诉FragmentActivity，当前Fragment在栈顶
        mBackHandledInterface.setSelectedFragment(this);
    }

    // 显示Toast
    public void showToast(String info) {
        Toast.makeText(getContext(), info, Toast.LENGTH_SHORT).show();
    }

    // 显示中间的Toast
    public void showCenterToase(String info) {

        Toast toast = Toast.makeText(getContext(), info, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
