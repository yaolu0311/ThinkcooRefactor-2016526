package com.thinkcoo.mobile.presentation.views.adapter.train;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.model.entity.TrainPopupItemEntity;

import java.util.List;

public class TrainSearchListMenuAdapter extends BaseAdapter{

	private List<TrainPopupItemEntity> mlist;
	private Context context;

	public TrainSearchListMenuAdapter(List<TrainPopupItemEntity> mlist, Context context) {
		super();
		this.mlist = mlist;
		this.context = context;
	}

	@Override
	public int getCount() {
		return null==mlist?0:mlist.size();
	}

	@Override
	public Object getItem(int position) {
		return null!=mlist?mlist.get(position):0;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder  holder;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=LayoutInflater.from(context).inflate(R.layout.item_train_search_menu,null);
			holder.ac_search_menu_list_name=(TextView) convertView.findViewById(R.id.ac_search_menu_list_name);
			holder.ac_search_menu_image=(ImageView) convertView.findViewById(R.id.ac_search_menu_image);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}

		TrainPopupItemEntity itemEntity = mlist.get(position);
		holder.ac_search_menu_list_name.setText(itemEntity.getName());
		holder.ac_search_menu_image.setVisibility(itemEntity.isChecked() ? View.VISIBLE :View.INVISIBLE);
		
		return convertView;
	}

	public class ViewHolder{
		private  TextView ac_search_menu_list_name;//名称
		private ImageView ac_search_menu_image;//对号
	}

}
