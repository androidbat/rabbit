package com.todddavies.components.progressbar;

import android.R.integer;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View{
	private Paint mCirclePaint;
	private RectF mRectF;
	private int width,height,progress;
	public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		mCirclePaint = new Paint();
		mCirclePaint.setColor(Color.parseColor("#ff0000"));
		mCirclePaint.setAntiAlias(true);
		mCirclePaint.setStyle(Style.STROKE);
		mCirclePaint.setStrokeWidth(10);
	}

	public MyView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public MyView(Context context) {
		this(context,null);
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		width = w;
		height = h;
		setupRect();
	}
	
	
	
	private void setupRect() {
		mRectF = new RectF(0+10, 0+10, width-10, height-10);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(Color.parseColor("#0000ff"));
//		canvas.drawCircle(width/2, height/2, 70, mCirclePaint);
		canvas.drawArc(mRectF, -90+progress, 30, false, mCirclePaint);
		if (progress == 360) {
			progress = 0 ;
		}else{
			
		}
		progress++;
		postInvalidate();
	}
	
}
