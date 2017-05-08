package com.tequila.tallybook.view;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tequila.tallybook.R;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Tequila on 2017/5/8.
 */

public class TextURLView extends LinearLayout {

    private Context mContext;
    @Bind(R.id.tv_url_view)
    TextView url;

    public TextURLView(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public TextURLView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    private void initView(){
        LayoutInflater.from(mContext).inflate(R.layout.common_url_textview, this);
        ButterKnife.bind(this);
    }

    public void setText(int txtRes){
//        url.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);   //下划线
        url.setText(txtRes);
        url.setTextColor(getResources().getColor(R.color.blue1));
        url.setTextSize(18);
    }
}

