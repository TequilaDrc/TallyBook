package com.tequila.tallybook.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

import java.util.Timer;
import java.util.TimerTask;

import static com.tequila.tallybook.activity.SharedApplication.context;


/**
 * Created by tequila on 2017/5/4.
 */

public class ImageBarnnerViewGroup extends ViewGroup {

    private int children;
    private int childwidth;
    private int childheight;

    private int x;
    private int index = 0;

    private Scroller scroller;
    private int mTouchSlop;
    private int mStartX;

    private boolean isClick;

    private ImageBarnnerLister lister;

    public void setLister(ImageBarnnerLister lister) {
        this.lister = lister;
    }

    public interface ImageBarnnerLister {
        void clickImageIndex(int pos);
    }

    private ImageBarnnerViewGroupLisnner barnnerViewGroupLisnner;

    public void setBarnnerViewGroupLisnner(ImageBarnnerViewGroupLisnner barnnerViewGroupLisnner) {
        this.barnnerViewGroupLisnner = barnnerViewGroupLisnner;
    }

    private boolean isAuto = true;
    private Timer timer = new Timer();
    private TimerTask task;
    private Handler autoHander = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:

                    if (++index >= children) {
                        index = 0;
                    }

                    scrollTo(childwidth * index, 0);
                    barnnerViewGroupLisnner.selectImage(index);
                    break;
            }
        }
    };

    private void startAuto() {
        isAuto = true;
    }

    private void stopAuto() {
        isAuto = false;
    }

    public ImageBarnnerViewGroup(Context context) {
        super(context);
        initObj();
    }

    public ImageBarnnerViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        initObj();
    }

    public ImageBarnnerViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initObj();
    }

    private void initObj() {
        scroller = new Scroller(getContext());
        mTouchSlop = ViewConfiguration.get(context).getScaledPagingTouchSlop();

        task = new TimerTask() {
            @Override
            public void run() {
                if (isAuto) {
                    autoHander.sendEmptyMessage(0);
                }
            }
        };

        timer.schedule(task, 100, 4000);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();

        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), 0);
            invalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        children = getChildCount();

        if (0 == children) {
            setMeasuredDimension(0, 0);
        } else {
            measureChildren(widthMeasureSpec, heightMeasureSpec);
            View view = getChildAt(0);
            childwidth = view.getMeasuredWidth();
            childheight = view.getMeasuredHeight();

            int width = view.getMeasuredWidth() * children;
            setMeasuredDimension(width, childheight);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                stopAuto();
                if (!scroller.isFinished()) {
                    scroller.abortAnimation();
                }
                isClick = true;
                mStartX = (int) ev.getX();
                x = mStartX;
                break;
            case MotionEvent.ACTION_MOVE:
                int diff = (int) Math.abs(ev.getX() - mStartX);
                x = (int) ev.getX();

                //横向滑动距离超过slop值才拦截；
                if (diff > mTouchSlop) {
                    isClick = false;
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isClick) {
                    lister.clickImageIndex(index);
                }
                startAuto();
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_MOVE:
                int moveX = (int) event.getX();
                int distance = moveX - x;
                scrollBy(-distance, 0);
                x = moveX;
                break;
            case MotionEvent.ACTION_UP:

                int scrollx = getScrollX();
                index = (scrollx + childwidth / 2) / childwidth;

                if (index < 0) {
                    index = children - 1;
                } else if (index > children - 1) {
                    index = 0;
                }

                int dx = index * childwidth - scrollx;
                scroller.startScroll(scrollx, 0, dx, 0);
                postInvalidate();

                startAuto();
                barnnerViewGroupLisnner.selectImage(index);
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            int leftMargin = 0;
            for (int i = 0; i < children; i++) {
                View view = getChildAt(i);
                view.layout(leftMargin, 0, leftMargin + childwidth, childheight);
                leftMargin += childwidth;
                view.setClickable(true);
            }
        }
    }

    public interface ImageBarnnerViewGroupLisnner {
        void selectImage(int index);
    }
}