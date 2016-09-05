package com.thinkcoo.mobile.presentation.views.adapter.Schedule;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.model.entity.Student;

import java.util.List;

/**
 * Created bywyy
 * CreateTime: 2016/6/1  9:24
 */
public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.MyViewHolder> {

    private Context mContext;
    private List<Student> mData;
    private OnRecyclerViewItemClickListener mOnItemClickListener;
    private boolean isVisibileCheckBox;//是否显示CheckBox true显示，false隐藏
    public StudentListAdapter(Context context,OnRecyclerViewItemClickListener onItemClickListener, List<Student> data) {
        this.mContext = context;
        this.mOnItemClickListener = onItemClickListener;
        this.mData = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_student, null);
        MyViewHolder myViewHolder = new MyViewHolder(itemView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Student student = mData.get(position);
        holder.id.setText(student.getStudentNo());
        holder.name.setText(student.getRealName());
        Glide.with(mContext)
                .load(student.getHeadPortrait())
                .placeholder(R.drawable.default_avatar)
                .crossFade()
                .into( holder.headPortrait);
//        holder.headPortrait.setImageURI(Uri.parse(student.getHeadPortrait()));
        holder.itemView.setTag(student);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(student);
            }
        });
        if (isVisibileCheckBox == false) {
            holder.isCheck.setVisibility(View.GONE);
            return;
        } else {
            holder.isCheck.setVisibility(View.VISIBLE);
            holder.isCheck.setChecked(mData.get(position).isCheck());
            holder.isCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mData.get(position).setCheck(isChecked);
                }

            });
        }
    }
        @Override
        public int getItemCount () {
            return mData == null ? 0 : mData.size();
        }

    public boolean isVisibileCheckBox() {
        return isVisibileCheckBox;
    }

    public void setVisibileCheckBox(boolean visibileCheckBox) {
        isVisibileCheckBox = visibileCheckBox;
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView id;
        private final CheckBox isCheck;
        private final ImageView headPortrait;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            id = (TextView) itemView.findViewById(R.id.merber_id);
            headPortrait  = (ImageView) itemView.findViewById(R.id.iv_headPortrait);
            isCheck = (CheckBox) itemView.findViewById(R.id.is_check);
        }
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(Student student);
    }

}
