package com.example.drag;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.m.rabbit.R;

/**
 * Created by Flavien Laurent (flavienlaurent.com) on 23/08/13.
 */
public class DragLayout extends FrameLayout {

    private final ViewDragHelper mDragHelper;

    private View mDragView1;
    private View mDragView2;

    private boolean mDragEdge;
    private boolean mDragHorizontal;
    private boolean mDragCapture;
    private boolean mDragVertical;

    public DragLayout(Context context) {
        this(context, null);
    }

    public DragLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public DragLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mDragHelper = ViewDragHelper.create(this, callback);
        gestureDetector = new GestureDetectorCompat(context, new YScrollDetector());
    }

    @Override
    protected void onFinishInflate() {
        mDragView1 = findViewById(R.id.drag1);
        mDragView2 = findViewById(R.id.drag2);
    }

    public void setDragHorizontal(boolean dragHorizontal) {
        mDragHorizontal = dragHorizontal;
        mDragView2.setVisibility(View.GONE);
    }

    public void setDragVertical(boolean dragVertical) {
        mDragVertical = dragVertical;
        mDragView2.setVisibility(View.GONE);
    }

    public void setDragEdge(boolean dragEdge) {
        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
        mDragEdge = dragEdge;
        mDragView2.setVisibility(View.GONE);
    }

    public void setDragCapture(boolean dragCapture) {
        mDragCapture = dragCapture;
    }
    
    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            if (mDragCapture) {
                return child == mDragView1;
            }
            return true;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            invalidate();
        }

        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
        }

        @Override
        public void onEdgeTouched(int edgeFlags, int pointerId) {
            super.onEdgeTouched(edgeFlags, pointerId);
        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            if (mDragEdge) {
                mDragHelper.captureChildView(mDragView1, pointerId);
            }
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            if (mDragVertical) {
                final int topBound = getPaddingTop();
                final int bottomBound = getHeight() - mDragView1.getHeight();

                final int newTop = Math.min(Math.max(top, topBound), bottomBound);

                return newTop;
            }
            return super.clampViewPositionVertical(child, top, dy);
        }
        
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (mDragHorizontal || mDragCapture || mDragEdge) {
            	
            	System.out.println(" left "+left);
            	
                final int leftBound = getPaddingLeft();
                final int rightBound = getWidth() - mDragView1.getWidth();

                final int newLeft = Math.min(Math.max(left, leftBound), rightBound);

                return newLeft;
            }
            return super.clampViewPositionHorizontal(child, left, dx);
        }
    };

    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mDragHelper.cancel();
            return false;
        }
        boolean b =  mDragHelper.shouldInterceptTouchEvent(ev) && gestureDetector.onTouchEvent(ev);
        
        System.out.println(" onInterceptTouchEvent "+ev.getX() +" "+ ev.getY() +" "+b);
        return b;
    }
    
    GestureDetectorCompat gestureDetector ;

	class YScrollDetector extends SimpleOnGestureListener {
	    @Override
	    public boolean onScroll(MotionEvent e1, MotionEvent e2, float dx, float dy) {
	        return Math.abs(dy) <= Math.abs(dx);
	    }
	}

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
    	System.out.println(" onTouchEvent "+ev.getX() +" "+ ev.getY());
    	try {
    		mDragHelper.processTouchEvent(ev);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }

}
