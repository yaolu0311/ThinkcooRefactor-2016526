package com.thinkcoo.mobile.presentation.views.adapter.Schedule;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.model.entity.ClassGroup;

import java.util.List;


public class ClassScoreAdapter extends RecyclerView.Adapter<ClassScoreAdapter.ClassViewHolder> {

    private Context context;
    public List<ClassGroup> mClassGroupList;

    public ClassScoreAdapter(Context context, List<ClassGroup> classGroupList) {

        this.context = context;
        this.mClassGroupList = classGroupList;

    }

    @Override
    public ClassViewHolder onCreateViewHolder(ViewGroup viewGroup, final int viewType) {

        return new ClassViewHolder(LayoutInflater.from(context).inflate(R.layout.item_results_analysis, viewGroup, false));


    }

    @Override
    public void onBindViewHolder(ClassViewHolder holder, final int position) {
        final ClassGroup classGroup = mClassGroupList.get(position);
        holder.classname.setText(classGroup.getGroupName());

    }


    //RecyclerView显示数据条数
    @Override
    public int getItemCount() {

        //参与者
        return mClassGroupList.size();


    }

    //自定义的ViewHolder,减少findViewById调用次数
    class ClassViewHolder extends RecyclerView.ViewHolder {
        TextView classname;

        //在布局中找到所含有的UI组件
        public ClassViewHolder(View itemView) {
            super(itemView);
            classname = (TextView) itemView.findViewById(R.id.ac_class);
        }
    }


}