package com.thinkcoo.mobile.presentation.views.fragment;


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
import com.thinkcoo.mobile.injector.components.DaggerScheduleComponent;
import com.thinkcoo.mobile.injector.modules.ScheduleModule;
import com.thinkcoo.mobile.model.entity.Student;
import com.thinkcoo.mobile.presentation.mvp.presenters.BaseLcePagedPresenter;
import com.thinkcoo.mobile.presentation.mvp.presenters.SelectStudentPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.SelectStudentView;
import com.thinkcoo.mobile.presentation.views.activitys.CreateClassActivity;
import com.yz.im.ui.base.IMNavigator;
import com.yz.im.utils.GlideRoundTransform;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Administrator on 2016/8/6.
 */
public class StudentFragment extends BaseLcePagedFragment<Student> implements SelectStudentView<Student> {

    @Inject
    SelectStudentPresenter mSelectStudentPresenter;
    private OnRecyclerViewItemClickListener mOnItemClickListener;
    private boolean isVisibileCheckBox;//是否显示CheckBox true显示，false隐藏


    @Override
    protected BaseLcePagedPresenter provideBaseLcePagedPresenter() {
        return mSelectStudentPresenter;
    }

    @Override
    protected LceAdapterViewBind<Student> provideLceAdapterViewBind() {

        return new LceAdapterViewBind<Student>() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_student, null);

                MyViewHolder myViewHolder = new MyViewHolder(itemView);

                return myViewHolder;
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final Student student) {

                ((MyViewHolder) holder).bind(student);

                ((MyViewHolder) holder).id.setText(student.getStudentNo());
                ((MyViewHolder) holder).name.setText(student.getRealName());
                Glide.with(getContext())
                        .load(student.getHeadPortrait())
                        .placeholder(R.drawable.default_avatar)
                        .transform(new GlideRoundTransform(getContext()))
                        .into(((MyViewHolder) holder).headPortrait);

//        holder.headPortrait.setImageURI(Uri.parse(student.getHeadPortrait()));
                holder.itemView.setTag(student);


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        IMNavigator imNavigator = new IMNavigator();
                        imNavigator.navigateToSingleChatActivity(getContext(), student.getAccountId());
//                        mOnItemClickListener.onItemClick(student);
                    }
                });
                if (isVisibileCheckBox == false) {
                    ((MyViewHolder) holder).isCheck.setVisibility(View.GONE);
                    return;
                } else {
                    ((MyViewHolder) holder).isCheck.setVisibility(View.VISIBLE);
                    ((MyViewHolder) holder).isCheck.setChecked(student.isCheck());
                    ((MyViewHolder) holder).isCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            student.setCheck(isChecked);
                        }

                    });
                }
            }
        };
    }


    @Override
    protected void initInject() {
        DaggerScheduleComponent.builder().applicationComponent(getFragmentInjectHelper().getApplicationComponent()).scheduleModule(new ScheduleModule()).build().inject(this);
    }

    @Override
    public String getEventId() {
        return getHostActivity().getevent().scheduleId;
    }

    private CreateClassActivity getHostActivity() {
        return (CreateClassActivity) getActivity();
    }

    @Override
    public String getSchool() {
        return getHostActivity().getSchoolName();
    }

    @Override
    public String getClassProfession() {
        return getHostActivity().getClassName();
    }

    public String getSelectedStudentString() {

        List<Student> students = mRecyclerViewAdapter.getDataList();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            if (student.isCheck()) {
                stringBuilder.append(student.getAccountId());
                if (i < students.size()) {
                    stringBuilder.append(",");
                }
            }
        }
        return stringBuilder.toString();
    }

    public boolean isVisibileCheckBox() {
        return isVisibileCheckBox;
    }

    public void setVisibileCheckBox(boolean visibileCheckBox) {
        isVisibileCheckBox = visibileCheckBox;
    }

    public void refresh() {
        loadData(false);
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
            headPortrait = (ImageView) itemView.findViewById(R.id.iv_headPortrait);
            isCheck = (CheckBox) itemView.findViewById(R.id.is_check);
        }

        public void bind(Student student) {

        }
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(Student student);
    }

    @Override
    protected boolean isSearchMode() {
        return true;
    }

    @Override
    public void setDataList(List<Student> dataList) {
        super.setDataList(dataList);
        getHostActivity().showAddButton();
    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {
        super.showError(e, pullToRefresh);
        mRecyclerViewAdapter.getDataList().clear();
        getHostActivity().hideAddButton();
    }
}
