package com.thinkcoo.mobile.presentation.views.adapter.Schedule;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.thinkcoo.mobile.R;

import java.util.List;

/**
 * Created by Administrator on 2016/7/1
 *    设置半径
 */
public class RockCallAdapter extends BaseAdapter{
    private Context context;
    private List<String> list ;


    public RockCallAdapter(Context context,List list){
        this.context=context;
        this.list=list;
    }


    @Override
    public int getCount() {
        return null == list ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         ViewHolder holder;
          if (convertView==null){
              holder = new ViewHolder();
              convertView=View.inflate(context, R.layout.item_sc_rockcall_distance_item,null);
              holder.tv_listviewitem =  (TextView) convertView.findViewById(R.id.tv_right_color);
              convertView.setTag(holder);
          }else {
              holder = (ViewHolder) convertView.getTag();
          }

        holder.tv_listviewitem.setText(list.get(position));
        return convertView;
    }

    private class ViewHolder {
        TextView tv_listviewitem;
    }
}
