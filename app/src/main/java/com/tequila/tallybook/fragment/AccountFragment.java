package com.tequila.tallybook.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tequila.tallybook.R;
import com.tequila.tallybook.base.BaseFragment;
import com.tequila.tallybook.mode.TimeTrace;
import com.tequila.tallybook.utils.adapter.TimeTraceListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Tequila on 2017/5/4.
 */

public class AccountFragment extends BaseFragment {

    @Bind(R.id.timeLine)
    RecyclerView timeLine;

    private View mRootView;
    private List<TimeTrace> timeTraceList = new ArrayList<TimeTrace>(10);
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
        initData();

        return mRootView;
    }

    private void initData() {
        // 模拟一些假的数据
        timeTraceList.add(new TimeTrace("2016-05-25 17:48:00", "[沈阳市] [沈阳和平五部]的派件已签收 感谢使用中通快递,期待再次为您服务!", "至尊宝"));
        timeTraceList.add(new TimeTrace("2016-05-25 14:13:00", "[沈阳市] [沈阳和平五部]的东北大学代理点正在派件 电话:18040xxxxxx 请保持电话畅通、耐心等待", "至尊宝"));
        timeTraceList.add(new TimeTrace("2016-05-25 13:01:04", "[沈阳市] 快件到达 [沈阳和平五部]", "至尊宝"));
        timeTraceList.add(new TimeTrace("2016-05-25 12:19:47", "[沈阳市] 快件离开 [沈阳中转]已发往[沈阳和平五部]", "至尊宝"));
        timeTraceList.add(new TimeTrace("2016-05-25 11:12:44", "[沈阳市] 快件到达 [沈阳中转]", "至尊宝"));
        timeTraceList.add(new TimeTrace("2016-05-24 03:12:12", "[嘉兴市] 快件离开 [杭州中转部]已发往[沈阳中转]", "至尊宝"));
        timeTraceList.add(new TimeTrace("2016-05-23 21:06:46", "[杭州市] 快件到达 [杭州汽运部]", "至尊宝"));
        timeTraceList.add(new TimeTrace("2016-05-23 18:59:41", "[杭州市] 快件离开 [杭州乔司区]已发往[沈阳]", "至尊宝"));
        timeTraceList.add(new TimeTrace("2016-05-23 18:35:32", "[杭州市] [杭州乔司区]的市场部已收件 电话:18358xxxxxx", "至尊宝"));
        timeTraceList.add(new TimeTrace("2016-05-25 17:48:00", "[沈阳市] [沈阳和平五部]的派件已签收 感谢使用中通快递,期待再次为您服务!", "至尊宝"));
        timeTraceList.add(new TimeTrace("2016-05-25 14:13:00", "[沈阳市] [沈阳和平五部]的东北大学代理点正在派件 电话:18040xxxxxx 请保持电话畅通、耐心等待", "至尊宝"));
        timeTraceList.add(new TimeTrace("2016-05-25 13:01:04", "[沈阳市] 快件到达 [沈阳和平五部]", "至尊宝"));
        timeTraceList.add(new TimeTrace("2016-05-25 12:19:47", "[沈阳市] 快件离开 [沈阳中转]已发往[沈阳和平五部]", "至尊宝"));
        timeTraceList.add(new TimeTrace("2016-05-25 11:12:44", "[沈阳市] 快件到达 [沈阳中转]", "至尊宝"));
        timeTraceList.add(new TimeTrace("2016-05-24 03:12:12", "[嘉兴市] 快件离开 [杭州中转部]已发往[沈阳中转]", "至尊宝"));
        timeTraceList.add(new TimeTrace("2016-05-23 21:06:46", "[杭州市] 快件到达 [杭州汽运部]", "至尊宝"));
        timeTraceList.add(new TimeTrace("2016-05-23 18:59:41", "[杭州市] 快件离开 [杭州乔司区]已发往[沈阳]", "至尊宝"));
        timeTraceList.add(new TimeTrace("2016-05-23 18:35:32", "[杭州市] [杭州乔司区]的市场部已收件 电话:18358xxxxxx", "至尊宝"));
        timeTraceList.add(new TimeTrace("2016-05-25 17:48:00", "[沈阳市] [沈阳和平五部]的派件已签收 感谢使用中通快递,期待再次为您服务!", "至尊宝"));
        timeTraceList.add(new TimeTrace("2016-05-25 14:13:00", "[沈阳市] [沈阳和平五部]的东北大学代理点正在派件 电话:18040xxxxxx 请保持电话畅通、耐心等待", "至尊宝"));
        timeTraceList.add(new TimeTrace("2016-05-25 13:01:04", "[沈阳市] 快件到达 [沈阳和平五部]", "至尊宝"));
        timeTraceList.add(new TimeTrace("2016-05-25 12:19:47", "[沈阳市] 快件离开 [沈阳中转]已发往[沈阳和平五部]", "至尊宝"));
        timeTraceList.add(new TimeTrace("2016-05-25 11:12:44", "[沈阳市] 快件到达 [沈阳中转]", "至尊宝"));
        timeTraceList.add(new TimeTrace("2016-05-24 03:12:12", "[嘉兴市] 快件离开 [杭州中转部]已发往[沈阳中转]", "至尊宝"));
        timeTraceList.add(new TimeTrace("2016-05-23 21:06:46", "[杭州市] 快件到达 [杭州汽运部]", "至尊宝"));
        timeTraceList.add(new TimeTrace("2016-05-23 18:59:41", "[杭州市] 快件离开 [杭州乔司区]已发往[沈阳]", "至尊宝"));
        timeTraceList.add(new TimeTrace("2016-05-23 18:35:32", "[杭州市] [杭州乔司区]的市场部已收件 电话:18358xxxxxx", "至尊宝"));
        adapter = new TimeTraceListAdapter(getContext(), timeTraceList);
        timeLine.setLayoutManager(new LinearLayoutManager(getContext()));
        timeLine.setAdapter(adapter);
        adapter.setOnItemClickListener(new TimeTraceListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showCenterToase(timeTraceList.get(position).getAcceptStation());
            }
        });
    }

    @Override
    public boolean onBackPressed() {
        return true;
    }
}
