package com.tequila.tallybook.utils.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tequila.tallybook.R;
import com.tequila.tallybook.mode.TimeTrace;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Tequila on 2017/5/19.
 */

public class TimeTraceListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{

    private LayoutInflater inflater;
    private List<TimeTrace> traceList = new ArrayList<TimeTrace>(1);
    private static final int TYPE_TOP = 0x0000;
    private static final int TYPE_NORMAL= 0x0001;
    private OnItemClickListener mOnItemClickListener = null;

    public TimeTraceListAdapter(Context context, List<TimeTrace> traceList) {
        inflater = LayoutInflater.from(context);
        this.traceList = traceList;
    }

    public interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_trace, parent, false);
        ViewHolder vh = new ViewHolder(view);

        //将创建的View注册点击事件
        view.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ViewHolder itemHolder = (ViewHolder) holder;

        //将position保存在itemView的Tag中，以便点击时进行获取
        itemHolder.itemView.setTag(position);

        if (getItemViewType(position) == TYPE_TOP) {
            // 第一行头的竖线不显示
            itemHolder.tvTopLine.setVisibility(View.INVISIBLE);
            // 字体颜色加深
//            itemHolder.tvAcceptTime.setTextColor(0xff555555);
//            itemHolder.tvAcceptStation.setTextColor(0xff555555);
//            itemHolder.tvDot.setBackgroundResource(R.drawable.timelline_dot_first);
        } else if (getItemViewType(position) == TYPE_NORMAL) {
            itemHolder.tvTopLine.setVisibility(View.VISIBLE);
//            itemHolder.tvAcceptTime.setTextColor(0xff999999);
//            itemHolder.tvAcceptStation.setTextColor(0xff999999);
//            itemHolder.tvDot.setBackgroundResource(R.drawable.timelline_dot_normal);
        }

        itemHolder.tvAcceptTime.setTextColor(0xff999999);
        itemHolder.tvAcceptStation.setTextColor(0xff999999);
        itemHolder.tvDot.setBackgroundResource(R.drawable.timelline_dot_normal);

        itemHolder.bindHolder(traceList.get(position));
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, (int)v.getTag());
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return traceList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_TOP;
        }
        return TYPE_NORMAL;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tvAcceptTime)
        TextView tvAcceptTime;
        @Bind(R.id.tvAcceptStation)
        TextView tvAcceptStation;
        @Bind(R.id.tvTopLine)
        TextView tvTopLine;
        @Bind(R.id.tvDot)
        TextView tvDot;
        @Bind(R.id.tvName)
        TextView tvName;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindHolder(TimeTrace trace) {
            tvAcceptTime.setText(trace.getAcceptTime());
            tvAcceptStation.setText(trace.getAcceptStation());
            tvName.setText(trace.getName());
        }
    }
}
