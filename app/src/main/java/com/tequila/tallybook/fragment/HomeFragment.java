package com.tequila.tallybook.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tequila.tallybook.R;
import com.tequila.tallybook.view.ImageBarnnerFramLayout;
import com.tequila.tallybook.view.ViewBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Tequila on 2017/5/4.
 */

public class HomeFragment extends Fragment
        implements ImageBarnnerFramLayout.FramLayoutLisenner {

    private int[] images = new int[] {
            R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e, R.drawable.f
    };

    @Bind(R.id.image_group)
    ImageBarnnerFramLayout mGroup;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        ButterKnife.bind(this, view);

        initViewGroup();

        return view;
    }

    private void initViewGroup() {

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        ViewBean.WIDTH = dm.widthPixels;

        List<Bitmap> list = new ArrayList<Bitmap>();
        for (int i = 0; i < images.length; i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), images[i]);
            list.add(bitmap);
        }

        mGroup.addBitmaps(list);
        mGroup.setLisenner(this);
    }

    @Override
    public void clickImageIndex(int pos) {
        Toast.makeText(getContext(), "点击了第" + pos + "张图", Toast.LENGTH_SHORT).show();
    }
}