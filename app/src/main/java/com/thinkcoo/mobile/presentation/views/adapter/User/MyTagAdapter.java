package com.thinkcoo.mobile.presentation.views.adapter.User;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.model.entity.UserSkill;
import com.thinkcoo.mobile.presentation.views.component.FlowLayout;
import com.thinkcoo.mobile.utils.PublicUIUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/6/3.
 */
public class MyTagAdapter extends TagAdapter {

    private CallBackClickListener mListener;
    public List<UserSkill> mlist;
    public Context mContext;
    // 默认不是编辑模式
    private boolean isEditMode = false;

    public MyTagAdapter(List<UserSkill> mlist, Context context) {
        super(mlist, context);
        this.mlist = mlist;
        this.mContext = context;
    }

    // 是否编辑模式
    public boolean isEditMode() {
        return isEditMode;
    }

    public void setEditMode(boolean editMode) {
        isEditMode = editMode;
    }

    @Override
    public View getView(FlowLayout parent, final int position, Object o) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View viewinflate = mInflater.inflate(R.layout.item_person_skilllist, parent, false);
        TextView tv_text = (TextView) viewinflate.findViewById(R.id.text_item_personal_skill);
        ImageView im_del = (ImageView) viewinflate.findViewById(R.id.image_personal_skill_del);
        UserSkill skill = (UserSkill) o;
        String skillContent = skill.getSkill();
        // String skillId = skill.getSkillId();

        //内容等于空   添加技能图片的按钮
        if (String.valueOf(skillContent).equals("")) {
            if (!isEditMode) {
                tv_text.setText("");
                int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                tv_text.measure(spec, spec);
                tv_text.setHeight(tv_text.getMeasuredHeight());
                tv_text.setText("");
                tv_text.setBackgroundResource(R.mipmap.addpic_pressed);
                tv_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onAddListener();
                    }
                });
            }
        } else {    // 内容不等于空
            if (String.valueOf(skillContent).contains("#")) {
                // 获取最后"#"的位置
                int lastIndex = String.valueOf(skillContent).lastIndexOf("#");
                // 获取爱好内容
                tv_text.setText(String.valueOf(skillContent).subSequence(0, lastIndex));
                // 获取背景颜色值
                String blockColor = String.valueOf(skillContent).substring(lastIndex,
                        String.valueOf(skillContent).length());
                int spec = View.MeasureSpec.makeMeasureSpec(0,
                        View.MeasureSpec.UNSPECIFIED);
                tv_text.measure(spec, spec);
                try {
                    PublicUIUtil.setBackgroundRounded(mContext, tv_text.getMeasuredWidth(), tv_text.getMeasuredHeight(), tv_text, Color.parseColor(blockColor), 6);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                tv_text.setText(String.valueOf(skillContent));
            }
            tv_text.setPadding(30, 4, 30, 4);
            im_del.setClickable(false);
        }
        if (isEditMode && !String.valueOf(skillContent).equals("")) {  // 是否隐藏删除图标
            im_del.setVisibility(View.VISIBLE);
        } else {
            im_del.setVisibility(View.GONE);
        }
        // 删除的监听
        im_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onClick(mlist.get(position).getSkillId());
                }
            }
        });

        return viewinflate;
    }


    public interface CallBackClickListener {
        void onClick(String skillId);

        void onAddListener();
    }

    public void setCallBackClickListener(CallBackClickListener listener) {
        this.mListener = listener;
    }
}
