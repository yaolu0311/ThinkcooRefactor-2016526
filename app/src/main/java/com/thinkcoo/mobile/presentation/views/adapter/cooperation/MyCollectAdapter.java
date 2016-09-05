package com.thinkcoo.mobile.presentation.views.adapter.cooperation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.model.entity.CooperationCollection;

import java.util.List;

/**
 * author ：ml on 2016/7/25
 */
public class MyCollectAdapter extends BaseAdapter {

    private List<CooperationCollection> mlist;
    private Context mContext;

    public MyCollectAdapter(List<CooperationCollection> mList, Context context) {
        this.mContext = context;
        this.mlist = mList;
    }


    @Override
    public int getCount() {
        return null != mlist ? mlist.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return null != mlist ? mlist.get(position) : 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView==null){
             holder =  new Holder();
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_collect_list, null);
            holder.tv__coll_name = (TextView) view.findViewById(R.id.tv__coll_name);
            TextView tv_company_name = (TextView) view.findViewById(R.id.tv_company_name);
            TextView tv_collect_pay = (TextView) view.findViewById(R.id.tv_collect_pay);
            TextView tv_collect_time = (TextView) view.findViewById(R.id.tv_collect_time);
            convertView.setTag(holder);
        }else {
             holder = (Holder) convertView.getTag();
        }
        // TODO: 2016/7/25   有过期数据问题 显示



        return convertView;
    }

    class Holder {
        private TextView tv__coll_name;  //项目名称
        private TextView tv_company_name;   // 公司名称
        private TextView tv_collect_pay;   // 项目预算
        private TextView tv_collect_time;  // 发布的时间

    }
}
