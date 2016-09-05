package com.thinkcoo.mobile.presentation.views.adapter.train;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.model.entity.MyAppointment;

import java.util.List;

/**
 * author ：ml on 2016/7/28
 */
public class MyAppointmentAdapter extends RecyclerView.Adapter<MyAppointmentAdapter.Holder> {

    public LayoutInflater inflater;
    public List<MyAppointment> myAppointmentList;


    public MyAppointmentAdapter(Context context, List<MyAppointment> list) {
        inflater = LayoutInflater.from(context);
        myAppointmentList = list;

    }

    @Override
    public MyAppointmentAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_list_train_course, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(MyAppointmentAdapter.Holder holder, int position) {
        // TODO: 2016/7/28
              holder.bindData(myAppointmentList.get(position));

    }

    @Override
    public int getItemCount() {
        return myAppointmentList.size();
    }
    public static class Holder extends RecyclerView.ViewHolder {
        TextView tv_train_course;
        TextView tv_train_school;
        TextView tv_train_courseType;
        TextView tv_train_coursePrice;
        Button btn_delete;

        public Holder(View itemView) {

            super(itemView);
            tv_train_course =  (TextView) itemView.findViewById(R.id.tv_train_course);//银行考试笔试班  培训的课程名称
            tv_train_school = (TextView) itemView.findViewById(R.id.tv_train_school);//西安教育         培训机构名称
            tv_train_courseType = (TextView) itemView.findViewById(R.id.tv_train_courseType);// 业余班     培训类型
            tv_train_coursePrice = (TextView) itemView.findViewById(R.id.tv_train_coursePrice);// 价格
            btn_delete = (Button) itemView.findViewById(R.id.btn_delete);  //删除按钮
        }

        public void bindData(MyAppointment myAppointmentList) {
            // TODO: 2016/7/28
//            tv_train_course.setText(myAppointmentList.get(// TODO: 2016/7/28  根据position));
//            tv_train_school.setText(myAppointmentList.get());
//            tv_train_courseType.setText(myAppointmentList.get();
//            tv_train_coursePrice.setText(myAppointmentList.get();
        }

    }
    public void refresh(List<MyAppointment> list){
        myAppointmentList = list;
        notifyDataSetChanged();
    }

}
