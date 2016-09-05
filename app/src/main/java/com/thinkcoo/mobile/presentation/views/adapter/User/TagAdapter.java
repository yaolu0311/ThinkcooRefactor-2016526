package com.thinkcoo.mobile.presentation.views.adapter.User;

import android.content.Context;
import android.view.View;

import com.thinkcoo.mobile.presentation.views.component.FlowLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * tag 布局适配器
 *
 */
public abstract class TagAdapter<T> {
	private List<T> mTagDatas;
	private OnDataChangedListener mOnDataChangedListener;
	private HashSet<Integer> mCheckedPosList = new HashSet<Integer>();


	public TagAdapter(List<T> datas, Context context) {
		mTagDatas = datas;
	}


	public TagAdapter(T[] datas) {
		mTagDatas = new ArrayList<T>(Arrays.asList(datas));
	}



	public interface OnDataChangedListener {
		void onChanged();
	}

	public void setData(List<T> data) {
		this.mTagDatas = data;
		notifyDataChanged();
	}

	public void setOnDataChangedListener(OnDataChangedListener listener) {
		mOnDataChangedListener = listener;
	}

	public void setSelectedList(int... pos) {
		for (int i = 0; i < pos.length; i++)
			mCheckedPosList.add(pos[i]);
		notifyDataChanged();
	}

	public HashSet<Integer> getPreCheckedList() {
		return mCheckedPosList;
	}

	public int getCount() {
		return mTagDatas == null ? 0 : mTagDatas.size();
	}

	public void notifyDataChanged() {
		mOnDataChangedListener.onChanged();
	}

	public T getItem(int position) {
		return mTagDatas.get(position);
	}

	public abstract View getView(FlowLayout parent, int position, T t);

}