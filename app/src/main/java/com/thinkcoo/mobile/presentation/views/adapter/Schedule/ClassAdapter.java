package com.thinkcoo.mobile.presentation.views.adapter.Schedule;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.model.entity.ClassGroup;
import com.thinkcoo.mobile.model.entity.Schedule;
import com.thinkcoo.mobile.presentation.mvp.presenters.StudentManagePresenter;
import com.thinkcoo.mobile.presentation.views.activitys.StudentManageActivity;

import java.util.List;


public class ClassAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    public List<ClassGroup> mClassGroupList;
    private StudentManagePresenter mStudentManagePresenter;
    private boolean isCreateAuthor;
    private final int TYPE_CLASS = 0x0001;
    private final int TYPE_ADD = 0x0002;
    private LayoutInflater mLayoutInflater;
    public int OnLongPosition;

    public ClassAdapter(Context context, StudentManagePresenter studentManagePresenter, List<ClassGroup> classGroupList, boolean isCreateAuthor) {
        mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.mClassGroupList = classGroupList;
        this.mStudentManagePresenter = studentManagePresenter;
        this.isCreateAuthor = isCreateAuthor;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int viewType) {
        if (viewType == TYPE_CLASS) {
            return new ClassViewHolder(mLayoutInflater.inflate(R.layout.item_class, viewGroup, false));
        } else {
            return new AddViewHolder(mLayoutInflater.inflate(R.layout.item_add, viewGroup, false));
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ClassViewHolder) {
            ((ClassViewHolder) holder).shchool.setText(mClassGroupList.get(position).getSchoolName());
            ((ClassViewHolder) holder).classname.setText(mClassGroupList.get(position).getGroupName());
            ((ClassViewHolder) holder).parentLayout.
                    setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            OnLongPosition = position;
                            ((StudentManageActivity) context).mDeleteClass.show();
                            return false;
                        }
                    });


            ((ClassViewHolder) holder).parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((StudentManageActivity) context).setSelectClassPosion(position);
                    notifyDataSetChanged();
                    mStudentManagePresenter.loadStudentList(((StudentManageActivity) context).mEvent.scheduleId, mClassGroupList.get(position).getGroupId());
                }
            });

            if (position == ((StudentManageActivity) context).getSelectClassPosion()) {
                ((ClassViewHolder) holder).parentLayout.setBackgroundResource(R.drawable.select_grad_button);
            } else {
                ((ClassViewHolder) holder).parentLayout.setBackgroundResource(R.drawable.ac_green_bule_class);
            }

        } else {
            ((AddViewHolder) holder).parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((StudentManageActivity) context).mEvent.scheduleType == Schedule.SCHEDULE_TYPE_ACTIVITY) {
                        ((StudentManageActivity) context).mNavigator.navigateToCreateActivityGroup(context, ((StudentManageActivity) context).mEvent);
                    } else {
                        ((StudentManageActivity) context).mNavigator.navigateToCreatelass(context, ((StudentManageActivity) context).mEvent);
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (!isCreateAuthor || (isCreateAuthor && position < mClassGroupList.size())) {
            return TYPE_CLASS;
        } else {
            return TYPE_ADD;
        }


    }


    //RecyclerView显示数据条数
    @Override
    public int getItemCount() {
        if (isCreateAuthor) {
            //创建者
            return mClassGroupList.size() + 1;
        } else {
            //参与者
            return mClassGroupList.size();
        }

    }

    //自定义的ViewHolder,减少findViewById调用次数
    class ClassViewHolder extends RecyclerView.ViewHolder {

        TextView shchool;
        TextView classname;
        RelativeLayout parentLayout;

        //在布局中找到所含有的UI组件
        public ClassViewHolder(View itemView) {
            super(itemView);
            shchool = (TextView) itemView.findViewById(R.id.text_school);
            classname = (TextView) itemView.findViewById(R.id.text_class);
            parentLayout = (RelativeLayout) itemView.findViewById(R.id.parentLayout);
        }
    }

    //自定义的ViewHolder,减少findViewById调用次数
    class AddViewHolder extends RecyclerView.ViewHolder {
        //在布局中找到所含有的UI组件
        RelativeLayout parentLayout;

        public AddViewHolder(View itemView) {
            super(itemView);
            parentLayout = (RelativeLayout) itemView.findViewById(R.id.parentLayout);
        }
    }
}