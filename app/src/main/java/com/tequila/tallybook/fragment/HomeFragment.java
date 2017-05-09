package com.tequila.tallybook.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.nineoldandroids.view.ViewHelper;
import com.tequila.tallybook.R;
import com.tequila.tallybook.base.BaseFragment;
import com.tequila.tallybook.bean.ItemBean;
import com.tequila.tallybook.utils.ItemDataUtils;
import com.tequila.tallybook.widget.DragLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_fragment, container, false);

        ButterKnife.bind(this, view);

        initDragLayout();
        initView();
//        initViewGroup();

        return view;
    }

    private void initDragLayout() {

        dl.setDragListener(new DragLayout.DragListener() {

            //界面打开的时候
            @Override
            public void onOpen() {
//                Toast.makeText(getContext(), "打开",Toast.LENGTH_SHORT).show();
            }

            //界面关闭的时候
            @Override
            public void onClose() {
//                Toast.makeText(getContext(), "关闭",Toast.LENGTH_SHORT).show();
            }

            //界面滑动的时候
            @Override
            public void onDrag(float percent) {
                ViewHelper.setAlpha(ivIcon, 1 - percent);
            }
        });
    }

    private void initView() {

        lv.setAdapter(quickAdapter = new QuickAdapter<ItemBean>(getContext(),
                R.layout.item_left_layout, ItemDataUtils.getItemBeans()) {

            @Override
            protected void convert(BaseAdapterHelper helper, ItemBean item) {
                helper.setImageResource(R.id.item_img, item.getImg())
                        .setText(R.id.item_tv, item.getTitle());
            }
        });
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