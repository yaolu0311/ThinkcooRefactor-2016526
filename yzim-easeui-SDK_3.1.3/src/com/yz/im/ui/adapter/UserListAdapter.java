package com.yz.im.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyphenate.easeui.R;
import com.yz.im.Constant;
import com.yz.im.model.entity.serverresponse.FindUserResponse;
import com.yz.im.utils.GlideRoundTransform;

import java.util.List;

/**
 * Created by cys on 2016/8/2
 * 搜索用户列表适配器
 */
public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder> {

    private Context mContext;
    private List<FindUserResponse> mList;

    private UserCallBack mUserCallBack;

    public UserListAdapter(Context context, List<FindUserResponse> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_user_search, null);
        UserViewHolder holder = new UserViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, final int position) {
        final FindUserResponse response = mList.get(position);
        holder.bindData(mContext, response);
        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUserCallBack == null) {
                    return;
                }
                mUserCallBack.onItemClick(position, response);
            }
        });

        holder.mAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUserCallBack == null || v.getId() == R.id.tv_is_friend) {
                    return;
                }
                mUserCallBack.addUser(position, response);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder{

        private static final String MAN = "男";
        private static final String WOMEN = "女";

        private ImageView mHead;
        private ImageView mUserSex;
        private TextView mName;
        private TextView mUserId;
        private TextView mAddEvent;
        private RelativeLayout mLayout;

        public UserViewHolder(View itemView) {
            super(itemView);
            mHead = (ImageView) itemView.findViewById(R.id.img_head);
            mUserSex = (ImageView) itemView.findViewById(R.id.iv_user_sex);
            mName = (TextView) itemView.findViewById(R.id.tv_user_name);
            mUserId = (TextView) itemView.findViewById(R.id.tv_user_id);
            mLayout  = (RelativeLayout) itemView.findViewById(R.id.item_find_user);
        }

        private void bindData(Context context, FindUserResponse userResponse) {
            if (userResponse == null) {
                return;
            }
            String isFriend = userResponse.getIsFriends();
            if (isFriend.equals(Constant.IS_FRIEND)) {
                mAddEvent = (TextView) itemView.findViewById(R.id.tv_is_friend);
            } else {
                mAddEvent = (TextView) itemView.findViewById(R.id.tv_not_friend);
            }
            mAddEvent.setVisibility(View.VISIBLE);
            Glide.with(context).load(userResponse.getImager()).error(R.drawable.default_avatar).transform(new GlideRoundTransform(context, 10)).into(mHead);
            setSexImage(userResponse.getSex());
            mName.setText(userResponse.getName());
            mUserId.setText(userResponse.getUserId());
        }

        private void setSexImage(String sex) {
            if (TextUtils.isEmpty(sex)) {
                mUserSex.setImageResource(R.drawable.defaule_sex_image);
            } else if (MAN.equals(sex)) {
                mUserSex.setImageResource(R.drawable.man);
            } else if (WOMEN.equals(sex)) {
                mUserSex.setImageResource(R.drawable.women);
            }
        }
    }

    public interface UserCallBack{
        void addUser(int position, FindUserResponse response);
        void onItemClick(int position, FindUserResponse response);
    }

    public void setUserCallBack(UserCallBack userCallBack) {
        mUserCallBack = userCallBack;
    }
}
