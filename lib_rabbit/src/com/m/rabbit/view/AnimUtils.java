package com.m.rabbit.view;

import android.view.View;

public class AnimUtils {
private void deleteGoods(final View convertView) {
	
//	holder.drawer = (MenuDrawer) view.findViewById(R.id.drawer);
//	holder.drawer.setTouchMode(MenuDrawer.TOUCH_MODE_FULLSCREEN);
//	holder.drawer.setDropShadowEnabled(false);
//	holder.drawer.setOverlayDrawable(new ColorDrawable(0x22000000));
//	holder.drawer.setPosition(Position.RIGHT);
//	drawerManager.addDrawer(holder.drawer);
	
//	ViewPropertyAnimator.animate(convertView)
//		.translationX(-convertView.getWidth())
//		// X轴方向的移动距离
//		.alpha(0).setDuration(300)
//		.setListener(new AnimatorListenerAdapter() {
//			@Override
//			public void onAnimationEnd(Animator animation) {
//				// Item滑出界面之后执行删除
//				ViewHolder holder = (ViewHolder) convertView.getTag();
//				holder.drawer.closeMenu(false);
//
//				final ViewGroup.LayoutParams lp = convertView
//						.getLayoutParams();// 获取item的布局参数
//				final int originalHeight = convertView.getHeight();// item的高度
//
//				ValueAnimator animator = ValueAnimator.ofInt(
//						convertView.getHeight(), 0).setDuration(200);
//				animator.setTarget(convertView);
//				animator.start();
//
//				animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//					@Override
//					public void onAnimationUpdate(
//							ValueAnimator valueAnimator) {
//						// 这段代码的效果是ListView删除某item之后，其他的item向上滑动的效果
//						lp.height = (Integer) valueAnimator
//								.getAnimatedValue();
//						convertView.setLayoutParams(lp);
//					}
//				});
//
//				animator.addListener(new AnimatorListenerAdapter() {
//					@Override
//					public void onAnimationEnd(Animator animation) {
//						mGoods.remove(goods);
//						mTralleyAdapter.notifyDataSetChanged();
//						setTotal();
//
//						ViewHelper.setAlpha(convertView, 1f);
//						ViewHelper.setTranslationX(convertView, 0);
//						ViewGroup.LayoutParams lp = convertView
//								.getLayoutParams();
//						lp.height = originalHeight;
//						convertView.setLayoutParams(lp);
//					}
//				});
//
//			}
//		});
	
}
}
