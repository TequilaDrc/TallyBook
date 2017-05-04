package com.tequila.tallybook.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tequila.tallybook.R;
import com.tequila.tallybook.view.ImageBarnnerViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Tequila on 2017/5/4.
 */

public class HomeFragment extends Fragment {

    private int[] images = new int[] {
            R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e, R.drawable.f
    };

    @Bind(R.id.image_group)
    ImageBarnnerViewGroup mGroup;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        ButterKnife.bind(this, view);

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;

        for (int i = 0; i < images.length; i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT));
            imageView.setImageResource(images[i]);
            mGroup.addView(imageView);
        }

        return view;
    }
}


































