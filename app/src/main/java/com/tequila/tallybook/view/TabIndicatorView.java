package com.tequila.tallybook.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tequila.tallybook.R;

/**
 * Created by Tequila on 2017/5/4.
 */

public class TabIndicatorView extends RelativeLayout {

    private TextView tab_title;
    private ImageView tab_icon;

    private int normalIconId, focusIconId;

    public TabIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.tab_indicator, this);
        tab_title = (TextView) findViewById(R.id.tab_indicator_hint);
        tab_icon = (ImageView) findViewById(R.id.tab_indicator_icon);
    }

    public TabIndicatorView(Context context) {
        this(context, null);
    }

    public void setTitle(String title) {
        tab_title.setText(title);
    }

    public void setTitle(int titleId) {
        tab_title.setText(titleId);
    }

//	public void setTextColor(int normalIconId, int focusIconId){
//		this.normalIconId = normalIconId;
//		this.focusIconId = focusIconId;
//		tab_title.setTextColor(Color.WHITE);
//	}

    public void setTabIcon(int normalIconId, int focusIconId) {
        this.normalIconId = normalIconId;
        this.focusIconId = focusIconId;
        tab_icon.setImageResource(normalIconId);
    }

    public void setSelect(boolean selected) {
        if (selected) {
            tab_icon.setImageResource(focusIconId);
            tab_title.setTextColor(Color.parseColor("#56abe4"));//#56abe4
        } else {
            tab_icon.setImageResource(normalIconId);
            tab_title.setTextColor(Color.WHITE);
        }
    }
}