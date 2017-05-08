package com.tequila.tallybook.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;


public class CustomRelativeLayout extends RelativeLayout {
    private com.tequila.tallybook.widget.DragLayout dl;

    public CustomRelativeLayout(Context context) {
        super(context);
    }

    public CustomRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setDragLayout(com.tequila.tallybook.widget.DragLayout dl) {
        this.dl = dl;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (dl.getStatus() != com.tequila.tallybook.widget.DragLayout.Status.CLOSE) {
            return true;
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (dl.getStatus() != com.tequila.tallybook.widget.DragLayout.Status.CLOSE) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                dl.close();
            }
            return true;
        }
        return super.onTouchEvent(event);
    }

}
