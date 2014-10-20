package com.m.publiccode.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.m.rat.R;

public class ListFootLoadHelper implements OnScrollListener, OnClickListener{

	private boolean isLoadEnable;// 开启或者关闭加载更多功能
	private boolean isLoadFull;
	
	private TextView des_tv;
	private ProgressBar loading;
	private View footer;
	private int pageSize = 10;

	private OnFootLoading onLoadListener;
	
	private ListView mListView;
	public void setList(ListView listview,int pageSize){
		this.mListView = listview;
		this.pageSize = pageSize;
	}
	
	public void setLoadEnble(boolean enble){
		if (mListView == null) {
			return;
		}
		if (enble) {
			if (footer != null) {
				mListView.removeFooterView(footer);
			}
			footer = LayoutInflater.from(mListView.getContext().getApplicationContext()).inflate(R.layout.list_foot_bar, null);
			loading = (ProgressBar) footer.findViewById(R.id.loading);
			des_tv = (TextView) footer.findViewById(R.id.des_tv);
			loading.setVisibility(View.GONE);
			des_tv.setText(R.string.release_to_load);
			mListView.setOnScrollListener(this);
			mListView.addFooterView(footer);
			isLoadFull = false;
		}else{
			mListView.removeFooterView(footer);
			mListView.setOnScrollListener(null);
			footer = null;
		}
		isLoadEnable = enble;
	}

	/*
	 * 定义下拉刷新接口
	 */
	public interface OnRefreshListener {
		public void onRefresh();
	}

	public interface OnFootLoading {
		public void onLoad();
	}

	public void setOnLoadListener(OnFootLoading onLoadListener) {
		this.onLoadListener = onLoadListener;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		switch ( scrollState ){
		case OnScrollListener.SCROLL_STATE_FLING:
			break;
		case OnScrollListener.SCROLL_STATE_IDLE:
			try {
				if (!isLoadFull && isLoadEnable && mListView.getLastVisiblePosition() >= mListView.getPositionForView(footer)) {
					if (loading.getVisibility() != View.VISIBLE) {
						loading.setVisibility(View.VISIBLE);
						des_tv.setText(R.string.more_loading);
						if (onLoadListener != null) {
							onLoadListener.onLoad();
						}
					}
				}
			} catch (Exception e) {
			}
			break;
		case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
			break;
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		
	}
	
	
	/**
	 * 这个方法是根据结果的大小来决定footer显示的。
	 * <p>
	 * 这里假定每次请求的条数为10。如果请求到了10条。则认为还有数据。如过结果不足10条，则认为数据已经全部加载，这时footer显示已经全部加载
	 * </p>
	 * 
	 * @param resultSize
	 */
	public void setResultSize(int resultSize) {
		System.out.println(" resultSize "+resultSize);
		if (resultSize == -1) {
			des_tv.setText(R.string.load_failed);
			footer.setOnClickListener(this);
		}else if (resultSize >= 0 && resultSize < pageSize) {
			isLoadFull = true;
			des_tv.setText(R.string.load_full);
		} else if (resultSize == pageSize) {
			isLoadFull = false;
			des_tv.setText(R.string.release_to_load);
		}
		
		loading.setVisibility(View.GONE);
		if (resultSize != -1) {
			footer.setOnClickListener(null);
		}
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public void onClick(View v) {
		loading.setVisibility(View.VISIBLE);
		des_tv.setText(R.string.more_loading);
		if (onLoadListener != null) {
			onLoadListener.onLoad();
		}
	}
	
}
