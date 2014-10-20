package com.m.rabbit.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.m.rabbit.R;

public class PullToRefreshView extends LinearLayout implements OnTouchListener {
	
	/** 下拉状态 */
	public static final int STATUS_PULL_TO_REFRESH = 0;

	/** 释放立即刷新状态 */
	public static final int STATUS_RELEASE_TO_REFRESH = 1;

	/** 正在刷新状态 */
	public static final int STATUS_REFRESHING = 2;

	/** 刷新完成或未刷新状态*/
	public static final int STATUS_REFRESH_FINISHED = 3;
	
	private int currentStatus = -1;
	private int lastStatus = -1;
	
	private View header;
	private ProgressBar progressBar;
	private ImageView arrow;
	private TextView description;
	private TextView updateAt;
	private ListView mListView;
	private int touchSlop;
	private int headHeight;
	private MarginLayoutParams headerLayoutParams;
	private Scroller scroller;
	private boolean isFirst = true;
	private boolean isHideRefreshHead = true;
	
	public PullToRefreshView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	private void init(Context context) {
		preferences = PreferenceManager.getDefaultSharedPreferences(context);
		header = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.pull_to_refresh, null, false);
		progressBar = (ProgressBar) header.findViewById(R.id.progress_bar);
		arrow = (ImageView) header.findViewById(R.id.arrow);
		description = (TextView) header.findViewById(R.id.description);
		updateAt = (TextView) header.findViewById(R.id.updated_at);
		touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
		
		try {
			measureView(header);
			headHeight = header.getMeasuredHeight();
		} catch (Exception e) {
			headHeight = (int) getContext().getResources().getDimension(R.dimen.pull_header_height);
		}
		
		addView(header, 0);
		scroller = new Scroller(getContext(),sInterpolator);
	}
	
	private static final Interpolator sInterpolator = new Interpolator() {
		public float getInterpolation(float t) {
			t -= 1.0f;
			return t * t * t * t * t + 1.0f;
		}
	};
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if (changed && isFirst) {
			headHeight = header.getHeight();
			headerLayoutParams = (MarginLayoutParams) header.getLayoutParams();
			headerLayoutParams.topMargin = -headHeight;
			mListView = (ListView) getChildAt(1);
			mListView.setOnTouchListener(this);
			isFirst = false;
		}
	}
	
	private boolean isCanPull(){
		
		if (mListView.getChildCount() >0) {
			final int pos = mListView.getFirstVisiblePosition();
			final int firstTop = mListView.getChildAt(0).getTop();
			if (pos == 0 && firstTop == 0 ) {
				return true;
			}else{
				return false;
			}
		}else{
			return true;
		}
	}

	
	private float mLastY;
	private int mLastScrollY;
	private boolean isMove;
	private boolean isDrading;
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		boolean can = isCanPull();
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mLastY = event.getRawY();
			mLastScrollY = getScrollY();
			isMove = false;
			isDrading = false;
			if (currentStatus != STATUS_REFRESHING && !isHideRefreshHead) 
				currentStatus = -1;
			return false;
		case MotionEvent.ACTION_MOVE:
			final float y = event.getRawY();
			final float yDiff = y - mLastY;
			mLastY = y;
			if (can) {
				if (yDiff > 0) {
					isDrading = true;
				}
				
				final int scrollY = getScrollY();
				if (isDrading && currentStatus != STATUS_REFRESHING && scrollY <=0) {
					int instance = (int) (-yDiff/2);
					if (scrollY +instance > 0 ) {
						instance = 0 - scrollY;
					}
					scrollBy(0, instance);
					if (scrollY < -headHeight ) {
						currentStatus = STATUS_RELEASE_TO_REFRESH;
					}else{
						currentStatus = STATUS_PULL_TO_REFRESH;
					}
				}
				
				if (scrollY == 0 ) {
					currentStatus = STATUS_REFRESH_FINISHED;
				}
			}
			
			if (currentStatus == STATUS_REFRESHING && !isHideRefreshHead) {
				if (Math.abs(yDiff) > touchSlop) {
					int posY = mLastScrollY+((int) (-yDiff/1.8));
					
					if (posY > 0 && getScrollY() == 0 ) {
						isMove = false;
						return false;
					}
					
					if (can) {
						if (posY > 0) {
							mListView.setSelection(0);
							posY = 0 ;
						}
						scrollTo(0, posY);
						isMove = true;
						return true;
					}
				}
			}
			break;
		case MotionEvent.ACTION_UP:
		default:
			mLastY = -1;
			if (currentStatus == STATUS_PULL_TO_REFRESH) {
				refreshFinished();//恢复状态
				return true;
			}else if (currentStatus == STATUS_RELEASE_TO_REFRESH) {
				currentStatus = STATUS_REFRESHING;
				mListView.setSelection(0);
				if (isHideRefreshHead) {
					lastStatus = STATUS_REFRESHING;
					smoothToResetPos(0);
					postDelayed(new Runnable() {
						public void run() {
							if (mListener != null) {
								mListener.onRefresh();
							}
						}
					}, 300);
				}else{
					smoothToResetPos(-headHeight);
					updateHeaderView();
				}
				postDelayed(new Runnable() {
					public void run() {
						setListFocus(false);
					}
				}, 50);
				currentStatus = STATUS_REFRESH_FINISHED;
				return true;
			}else if (currentStatus == STATUS_REFRESHING) {
				if (getScrollY() < 0 && !isHideRefreshHead) {
					smoothToResetPos(-headHeight);
				}
				setListFocus(false);
				return true;
			}
			break;
		}
		
		if (currentStatus == STATUS_PULL_TO_REFRESH
				|| currentStatus == STATUS_RELEASE_TO_REFRESH || isMove) {
			// 当前正处于下拉或释放状态，要让ListView失去焦点，否则被点击的那一项会一直处于选中状态
			updateHeaderView();
			setListFocus(false);
			// 当前正处于下拉或释放状态，通过返回true屏蔽掉ListView的滚动事件
			return true;
		}
		
		return false;
	}
	
	private void smoothToResetPos(int pos) {
		final int startY = getScrollY();
		final int dy = pos - startY;
		if (startY == pos) {
			return;
		}
		final int duration = 500;
		scroller.startScroll(0, startY, 0, dy, duration);
		invalidate();
	}
	
	public void refreshFinished(){
		if (getScrollY() < 0 ) {
			smoothToResetPos(0);
		}
		currentStatus = STATUS_REFRESH_FINISHED;
	}
	
	private void setListFocus(boolean focus){
		mListView.setPressed(focus);
		mListView.setFocusable(focus);
		mListView.setFocusableInTouchMode(focus);
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			mLastY = ev.getRawY();
			mLastScrollY = getScrollY();
			isMove = false;
			if (currentStatus != STATUS_REFRESHING && !isHideRefreshHead) 
				currentStatus = -1;
		}
		return super.onInterceptTouchEvent(ev);
	}
	
	@Override
	public void computeScroll() {
		if (!scroller.isFinished()) {
			if (scroller.computeScrollOffset()) {
				int oldX = getScrollX();
				int oldY = getScrollY();
				int x = scroller.getCurrX();
				int y = scroller.getCurrY();

				if (oldX != x || oldY != y) {
					scrollTo(x, y);
				}

				// Keep on drawing until the animation has finished.
				invalidate();
				return;
			}
		}
		super.computeScroll();
	}
	
	/** 更新下拉头中的信息。*/
	private void updateHeaderView() {
		if (lastStatus != currentStatus) {
			if (currentStatus == STATUS_PULL_TO_REFRESH) {
				description.setText(getResources().getString(R.string.pull_to_refresh));
				arrow.setVisibility(View.VISIBLE);
				if (lastStatus != STATUS_RELEASE_TO_REFRESH) {
					rotateArrow(0);
				}else{
					rotateArrow();
				}
				progressBar.setVisibility(View.GONE);
				updateAt.setVisibility(View.GONE);
			} else if (currentStatus == STATUS_RELEASE_TO_REFRESH) {
				description.setText(getResources().getString(R.string.release_to_refresh));
				arrow.setVisibility(View.VISIBLE);
				progressBar.setVisibility(View.GONE);
				updateAt.setVisibility(View.GONE);
				rotateArrow();
			} else if (currentStatus == STATUS_REFRESHING) {
				description.setText(getResources().getString(R.string.refreshing));
				progressBar.setVisibility(View.VISIBLE);
				updateAt.setVisibility(View.VISIBLE);
				arrow.clearAnimation();
				arrow.setVisibility(View.GONE);
				refreshUpdatedAtValue();
			}
		}
		lastStatus = currentStatus;
	}
	
	/**
	 * 根据当前的状态来旋转箭头。
	 */
	private void rotateArrow(int duration) {
		float pivotX = arrow.getWidth() / 2f;
		float pivotY = arrow.getHeight() / 2f;
		float fromDegrees = 0f;
		float toDegrees = 0f;
		if (currentStatus == STATUS_PULL_TO_REFRESH) {
			fromDegrees = -180f;
			toDegrees = 0f;
		} else if (currentStatus == STATUS_RELEASE_TO_REFRESH) {
			fromDegrees = 0f;
			toDegrees = -180f;
		}
		RotateAnimation animation = new RotateAnimation(fromDegrees, toDegrees, pivotX, pivotY);
		animation.setDuration(duration);
		animation.setFillAfter(true);
		arrow.startAnimation(animation);
	}
	
	private void rotateArrow(){
		rotateArrow(200);
	}
	
	
	// 此方法直接照搬自网络上的一个下拉刷新的demo，此处是“估计”headView的width以及height
	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}
	
	/**
	 * 刷新下拉头中上次更新时间的文字描述。
	 */
	private void refreshUpdatedAtValue() {
		lastUpdateTime = preferences.getLong(UPDATED_AT + mId, -1);
		long currentTime = System.currentTimeMillis();
		long timePassed = currentTime - lastUpdateTime;
		long timeIntoFormat;
		String updateAtValue;
		if (lastUpdateTime == -1) {
			updateAtValue = getResources().getString(R.string.not_updated_yet);
		} else if (timePassed < 0) {
			updateAtValue = getResources().getString(R.string.time_error);
		} else if (timePassed < ONE_MINUTE) {
			updateAtValue = getResources().getString(R.string.updated_just_now);
		} else if (timePassed < ONE_HOUR) {
			timeIntoFormat = timePassed / ONE_MINUTE;
			String value = timeIntoFormat + "分钟";
			updateAtValue = String.format(getResources().getString(R.string.updated_at), value);
		} else if (timePassed < ONE_DAY) {
			timeIntoFormat = timePassed / ONE_HOUR;
			String value = timeIntoFormat + "小时";
			updateAtValue = String.format(getResources().getString(R.string.updated_at), value);
		} else if (timePassed < ONE_MONTH) {
			timeIntoFormat = timePassed / ONE_DAY;
			String value = timeIntoFormat + "天";
			updateAtValue = String.format(getResources().getString(R.string.updated_at), value);
		} else if (timePassed < ONE_YEAR) {
			timeIntoFormat = timePassed / ONE_MONTH;
			String value = timeIntoFormat + "个月";
			updateAtValue = String.format(getResources().getString(R.string.updated_at), value);
		} else {
			timeIntoFormat = timePassed / ONE_YEAR;
			String value = timeIntoFormat + "年";
			updateAtValue = String.format(getResources().getString(R.string.updated_at), value);
		}
		updateAt.setText(updateAtValue);
	}
	
	/**
	 * 一分钟的毫秒值，用于判断上次的更新时间
	 */
	public static final long ONE_MINUTE = 60 * 1000;

	/**
	 * 一小时的毫秒值，用于判断上次的更新时间
	 */
	public static final long ONE_HOUR = 60 * ONE_MINUTE;

	/**
	 * 一天的毫秒值，用于判断上次的更新时间
	 */
	public static final long ONE_DAY = 24 * ONE_HOUR;

	/**
	 * 一月的毫秒值，用于判断上次的更新时间
	 */
	public static final long ONE_MONTH = 30 * ONE_DAY;

	/**
	 * 一年的毫秒值，用于判断上次的更新时间
	 */
	public static final long ONE_YEAR = 12 * ONE_MONTH;

	/**
	 * 上次更新时间的字符串常量，用于作为SharedPreferences的键值
	 */
	private static final String UPDATED_AT = "updated_at";

	/**
	 * 用于存储上次更新时间
	 */
	private SharedPreferences preferences;
	/**
	 * 上次更新时间的毫秒值
	 */
	private long lastUpdateTime;
	
	/**
	 * 为了防止不同界面的下拉刷新在上次更新时间上互相有冲突，使用id来做区分
	 */
	private int mId = -1;
	
	private PullToRefreshListener mListener;
	public interface PullToRefreshListener {
		void onRefresh();
		void startRefresh();
	}
	public void setOnRefreshListener(PullToRefreshListener listener) {
		mListener = listener;
	}
}
