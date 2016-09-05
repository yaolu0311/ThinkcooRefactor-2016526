package com.yz.im.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.LinearLayout;

/**
 * <p>
 * Title: CustomCheckable.java
 * </p>
 * <p>
 * Description: TODO(用一句话描述该文件做什么)
 * </p>
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * <p>
 * Company: 西安影子
 * </p>
 * <p>
 * CreateTime: 2015-8-17 上午11:31:19
 * </p>
 * 
 * @author 自己填写
 * @version V1.0
 * @since JDK1.7
 * @CheckItem 自己填写
 */
public class CustomCheckable extends LinearLayout implements Checkable {

	public CustomCheckable(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	private boolean mChecked = false;

	@Override
	public void toggle() {
		setChecked(!mChecked);
	}

	@Override
	public boolean isChecked() {
		return mChecked;
	}

	@Override
	public void setChecked(boolean checked) {
		if (mChecked != checked) {
			mChecked = checked;
			refreshDrawableState();

			toggleState(this, checked);

//			for (int i = 0; i < getChildCount(); i++) {
//				if (getChildAt(i) instanceof RelativeLayout) {
//					RelativeLayout relativeLayout = (RelativeLayout) getChildAt(i);
//					for (int j = 0; j < relativeLayout.getChildCount(); j++) {
//						View view = relativeLayout.getChildAt(j);
//						if (view instanceof Checkable) {
//							((Checkable) view).setChecked(checked);
//							return;
//						}
//					}
//				}
//			}
//			for (int i = 0; i < getChildCount(); i++) {
//				if (getChildAt(i) instanceof LinearLayout) {
//					LinearLayout linearLayout = (LinearLayout) getChildAt(i);
//					for (int j = 0; j < linearLayout.getChildCount(); j++) {
//						View view = linearLayout.getChildAt(j);
//						if (view instanceof Checkable) {
//							((Checkable) view).setChecked(checked);
//							return;
//						}
//					}
//				}
//			}
		}
	}


	private void toggleState(View v, boolean check){
		if (v instanceof ViewGroup) {
			int count = ((ViewGroup) v).getChildCount();
			for (int i = 0; i < count; i++) {
				View view= ((ViewGroup) v).getChildAt(i);
				toggleState(view, check);
			}
		}else if (v instanceof Checkable) {
			((Checkable)v).setChecked(check);
		}
	}

}
