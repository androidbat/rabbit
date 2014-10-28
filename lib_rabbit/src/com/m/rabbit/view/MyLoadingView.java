package com.m.rabbit.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;


public class MyLoadingView extends View{
	public  int mScreenHeight;
	public  int mScreenWidth;
	//图片宽度高度
    private int bWidth, bHeight;
	private Paint mPaint = new Paint();
	private Bitmap refresh;
	private int left=mScreenWidth;
	public MyLoadingView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public MyLoadingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public MyLoadingView(Context context) {
		super(context);
		init(context);
	}
	private void init(Context context){
		DisplayMetrics dm = getResources().getDisplayMetrics();
        mScreenWidth = dm.widthPixels;
        mScreenHeight = dm.heightPixels;
		setWillNotDraw(false);
	    mPaint.setAntiAlias(true);
	    bWidth = refresh.getWidth();
	    bHeight = refresh.getHeight();
	}
	
	public void setRefresh(Bitmap bm){
		refresh = bm;
	}

	private int pos=0;
	private int num=5;
	private void initLeft(){
		int space=bWidth*4;
		num=space/2;
		
		left=mScreenWidth-2*pos;
		if(pos<num){
			pos=pos+1;
		}else{
			pos=0;
		}
	}
	private void drawBitmap(Canvas canvas){
		int count=mScreenWidth/bWidth/4+1;
		initLeft();
		for(int i=0;i<count;i++){
			canvas.drawBitmap(refresh, left, 0, mPaint);
			left = left-bWidth*4;
		}
		invalidate();
	}
	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		drawBitmap(canvas);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
	}
}
