package com.thinkcoo.mobile.presentation.views.adapter.User;

import java.util.List;

import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.model.entity.AboutShardBean;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * <p>
 * Title: AboutShardAdapter.java
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
 * CreateTime: 2015-11-17 上午9:40:46
 * </p>
 * 
 * @author 分享的适配器
 * @version V1.0
 * @since JDK1.7
 * @CheckItem 自己填写
 */
public class AboutShardAdapter extends BaseAdapter {

	private Context context;
	private List<AboutShardBean> mlist;

	/**
	 * @param context
	 * @param mlist
	 */
	public AboutShardAdapter(Context context, List<AboutShardBean> mlist) {
		super();
		this.context = context;
		this.mlist = mlist;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return null!=mlist?mlist.size():0;
	}

	/**
	 * Description: 自己填写
	 * 
	 * @param position
	 * @return
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null!=mlist?mlist.get(position):0;
	}

	/**
	 * Description: 自己填写
	 * 
	 * @param position
	 * @return
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/**
	 * Description: 自己填写
	 * 
	 * @param position
	 * @param convertView
	 * @param parent
	 * @return
	 * @see android.widget.Adapter#getView(int, View,
	 *      ViewGroup)
	 */
	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		HoledyView holedy;
		if(convertView==null){
			holedy=new HoledyView();
			
			convertView=LayoutInflater.from(context).inflate(R.layout.item_about_shard,null);
			
			convertView.setTag(holedy);
			
		}else{
			
			holedy=(HoledyView) convertView.getTag();
		}
		
		AboutShardBean bean=mlist.get(position);
		
		holedy.about_shard_image=(ImageView) convertView.findViewById(R.id.about_shard_image);
		
		holedy.about_shard_imagename=(TextView) convertView.findViewById(R.id.about_shard_imagename);
		
//		holedy.about_shard_image.setBackground(context.getResources().getDrawable(bean.getImagepath()));
		holedy.about_shard_image.setImageResource(bean.getImagepath());
		holedy.about_shard_imagename.setText(bean.getImageName());
		
		
		return convertView;
	}
	
	public class HoledyView{
		
		private ImageView about_shard_image;
		private TextView about_shard_imagename;
	}

}
