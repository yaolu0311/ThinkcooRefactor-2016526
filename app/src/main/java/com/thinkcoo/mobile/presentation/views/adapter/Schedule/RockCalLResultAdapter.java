package com.thinkcoo.mobile.presentation.views.adapter.Schedule;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.thinkcoo.mobile.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/1.
 */
public class RockCalLResultAdapter extends BaseAdapter {
    private Context context;
    private List<String> list = new ArrayList<>();
    private ViewHolder holder;

    public RockCalLResultAdapter(Context context, List list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
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
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_sc_rockcallresult, null);
            holder.iv_usericon = (ImageView) convertView.findViewById(R.id.iv_usericon);
            holder.tv_useridname = (TextView) convertView.findViewById(R.id.tv_useridname);
            holder.tv_classname = (TextView) convertView.findViewById(R.id.tv_classname);
            holder.tv_studentid = (TextView) convertView.findViewById(R.id.tv_studentid);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    private class ViewHolder {
        ImageView iv_usericon;
        TextView tv_useridname;
        TextView tv_classname;
        TextView tv_studentid;

    }
}
