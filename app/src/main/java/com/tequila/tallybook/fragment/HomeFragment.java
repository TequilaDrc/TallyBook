package com.tequila.tallybook.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.nineoldandroids.view.ViewHelper;
import com.tequila.tallybook.R;
import com.tequila.tallybook.activity.LoginActivity;
import com.tequila.tallybook.base.BaseFragment;
import com.tequila.tallybook.bean.ItemBean;
import com.tequila.tallybook.dialog.ExitDialog;
import com.tequila.tallybook.event.ExitEvent;
import com.tequila.tallybook.mode.ResultModel;
import com.tequila.tallybook.mode.TallyViewBodyModel;
import com.tequila.tallybook.mode.TallyViewHeadModel;
import com.tequila.tallybook.utils.CommonAsyncTask;
import com.tequila.tallybook.utils.ItemDataUtils;
import com.tequila.tallybook.utils.Preference;
import com.tequila.tallybook.utils.SysApplication;
import com.tequila.tallybook.widget.DragLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import lecho.lib.hellocharts.listener.ComboLineColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.ComboLineColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ComboLineColumnChartView;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Tequila on 2017/5/4.
 */

public class HomeFragment extends BaseFragment {
//        implements ImageBarnnerFramLayout.FramLayoutLisenner {
//
//    private int[] images = new int[] {
//            R.drawable.a, R.drawable.b, R.drawable.c,
//            R.drawable.d, R.drawable.e, R.drawable.f
//    };

    private ComboLineColumnChartData data;
    private boolean hasAxes = true;
    private boolean hasAxesNames = false;
    private boolean hasPoints = true;
    private boolean hasLines = true;
    private boolean isCubic = false;
    private boolean hasLabels = false;
    private List<TallyViewHeadModel> tallyViewHeadModelList;
    private List<TallyViewBodyModel> tallyViewBodyModelList;

    private View mRootView;//缓存fragment View
    private QuickAdapter<ItemBean> quickAdapter;

//    @Bind(R.id.image_group)
//    ImageBarnnerFramLayout mGroup;
    @Bind(R.id.dl)
    DragLayout dl;
    @Bind(R.id.lv)
    ListView lv;
    @Bind(R.id.iv_icon)
    ImageView ivIcon;
    @Bind(R.id.iv_bottom)
    ImageView ivBottom;
    @Bind(R.id.name)
    TextView name;

    @Bind(R.id.chart)
    ComboLineColumnChartView chart;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        if(mRootView == null){
            mRootView = inflater.inflate(R.layout.home_fragment, container, false);
        }

        ViewGroup mViewGroup = (ViewGroup)mRootView.getParent();
        if(mViewGroup!=null){
            mViewGroup.removeView(mRootView);
        }

        ButterKnife.bind(this, mRootView);

        initDragLayout();
        initView();
//        initViewGroup();

        generateValues();

        EventBus.getDefault().register(this);

        return mRootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    // 获取柱状图数据
    private void generateValues() {

        getTallyDataAsyncTask task = new getTallyDataAsyncTask(getContext(), "");
        task.execute("");
    }

    // 生成数据
    private void generateData() {

        data = new ComboLineColumnChartData(generateColumnData(), generateLineData());

        List<AxisValue> mAxisXValuesList = new ArrayList<>();

        for (int i = 0; i < tallyViewHeadModelList.size(); i++) {
            mAxisXValuesList.add(new AxisValue(i).setLabel(tallyViewHeadModelList.get(i).getMarkerName()));
        }

        if (hasAxes) {
            Axis axisX = new Axis();
            axisX.setMaxLabelChars(tallyViewHeadModelList.size());
            axisX.setValues(mAxisXValuesList);
            axisX.setTextColor(R.color.black);
            axisX.setTextSize(10);

            Axis axisY = new Axis().setHasLines(true);
            if (hasAxesNames) {
                axisX.setName("姓 名");
                axisY.setName("消 费");
            }
            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);
        } else {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }

        chart.setComboLineColumnChartData(data);
    }

    private LineChartData generateLineData() {

        List<Line> lines = new ArrayList<Line>();

        List<PointValue> values = new ArrayList<PointValue>();
        for (int j = 0; j < tallyViewHeadModelList.size(); ++j) {
            values.add(new PointValue(j, tallyViewHeadModelList.get(j).getSumPrice()));
        }

        Line line = new Line(values);
        line.setColor(ChartUtils.COLORS[0]);
        line.setCubic(isCubic);
        line.setHasLabels(hasLabels);
        line.setHasLines(hasLines);
        line.setHasPoints(hasPoints);
        lines.add(line);

        LineChartData lineChartData = new LineChartData(lines);

        return lineChartData;

    }

    private ColumnChartData generateColumnData() {
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        for (int i = 0; i < tallyViewHeadModelList.size(); ++i) {

            values = new ArrayList<SubcolumnValue>();
            values.add(new SubcolumnValue(tallyViewBodyModelList.get(i).getSumPrice(), ChartUtils.COLOR_GREEN));

            columns.add(new Column(values));
        }

        ColumnChartData columnChartData = new ColumnChartData(columns);
        return columnChartData;
    }

    private class ValueTouchListener implements ComboLineColumnChartOnValueSelectListener {

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub
        }

        @Override
        public void onColumnValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value) {
//            Toast.makeText(getActivity(), "Selected column: " + value, Toast.LENGTH_SHORT).show();
            showCenterToase("累计生活平分费用为: " + value.getValue());
        }

        @Override
        public void onPointValueSelected(int lineIndex, int pointIndex, PointValue value) {
//            Toast.makeText(getActivity(), "Selected line point: " + value, Toast.LENGTH_SHORT).show();
            showCenterToase("累计生活费用为: " + value.getY());
        }
    }

    private class getTallyDataAsyncTask extends CommonAsyncTask<String> {

        public getTallyDataAsyncTask(Context context, String waitStr) {
            super(context, waitStr);
        }

        @Override
        public String convert(Object[] obj) {
            try {
                Call<ResultModel> call = getDataService().getTallyViewHeadData();
                Response<ResultModel> response = call.execute();
                ResultModel model = response.body();
                if (model.getSucceedFlag().equals("1")) {
                    tallyViewHeadModelList = new Gson().fromJson(model.getReturnInfo().toString(), new TypeToken<List<TallyViewHeadModel>>(){}.getType());
                }

                Call<ResultModel> call1 = getDataService().getTallyViewBodyData();
                Response<ResultModel> response1 = call1.execute();
                ResultModel model1 = response1.body();
                if (model1.getSucceedFlag().equals("1")) {
                    tallyViewBodyModelList = new Gson().fromJson(model1.getReturnInfo().toString(), new TypeToken<List<TallyViewBodyModel>>(){}.getType());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void setTData(String s) {

            generateData();
        }
    }

    private void initDragLayout() {

        dl.setDragListener(new DragLayout.DragListener() {

            //界面打开的时候
            @Override
            public void onOpen() {
//                showCenterToase("打开");
            }

            //界面关闭的时候
            @Override
            public void onClose() {
//                showCenterToase("关闭");
            }

            //界面滑动的时候
            @Override
            public void onDrag(float percent) {
                ViewHelper.setAlpha(ivIcon, 1 - percent);
            }
        });
    }

    private void initView() {

        String loginName = Preference.getInstance(getContext()).getLoginName();

        if (TextUtils.isEmpty(loginName) || loginName == null) {
            name.setText("林兮枫");
        } else {
            name.setText(loginName);
        }

        lv.setAdapter(quickAdapter = new QuickAdapter<ItemBean>(getContext(),
                R.layout.item_left_layout, ItemDataUtils.getItemBeans()) {

            @Override
            protected void convert(BaseAdapterHelper helper, ItemBean item) {
                helper.setImageResource(R.id.item_img, item.getImg())
                        .setText(R.id.item_tv, item.getTitle());
            }
        });

        chart.setOnValueTouchListener(new ValueTouchListener());
    }

    @OnItemClick(R.id.lv)
    public void itemClick (AdapterView<?> arg0, View arg1,
                   int position, long arg3) {
        showToast("点击 " + ItemDataUtils.getItemBeans().get(position).getTitle());
    }

    @OnClick(R.id.iv_icon)
    public void iconClick () {
        dl.open();
    }

    @OnClick(R.id.btnQRCode)
    public void btnQRCode () {
        showToast("二维码");
    }

    @OnClick({R.id.btnSet, R.id.tvSet})
    public void set() {
        showToast("设置");
    }

    @OnClick({R.id.btnNeight, R.id.tvNeight})
    public void neight() {
        showToast("夜间");
    }

    @Subscribe
    public void getExitClick(ExitEvent event) {
        switch (event.getItem()) {
            case 0:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
            case 1:
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().finish();
                SysApplication.getInstance().exit();
                android.os.Process.killProcess(android.os.Process.myPid());
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onBackPressed() {

        ExitDialog dialog = new ExitDialog(getContext());
        dialog.show();

        return true;
    }

//    private void initViewGroup() {
//
//        DisplayMetrics dm = new DisplayMetrics();
//        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
//        ViewBean.WIDTH = dm.widthPixels;
//
//        mGroup.addResource(images);
//        mGroup.setLisenner(this);
//    }
//
//    @Override
//    public void clickImageIndex(int pos) {
//        Toast.makeText(getContext(), "点击了第" + pos + "张图", Toast.LENGTH_SHORT).show();
//    }
}