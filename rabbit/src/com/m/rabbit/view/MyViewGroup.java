package com.m.rabbit.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.VelocityTracker;
import android.view.ViewGroup;

public class MyViewGroup extends ViewGroup {
	
	
	
	public MyViewGroup(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MyViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyViewGroup(Context context) {
		super(context);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		VelocityTracker tracker = VelocityTracker.obtain();
	}

}
