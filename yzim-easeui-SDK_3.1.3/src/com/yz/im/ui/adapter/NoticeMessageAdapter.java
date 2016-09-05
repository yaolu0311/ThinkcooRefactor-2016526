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
import com.yz.im.model.entity.serverresponse.NoticeMessageResponse;
import com.yz.im.ui.activity.NoticeMessageActivity;
import com.yz.im.utils.GlideRoundTransform;

import java.util.List;

/**
 * Created by cys on 2016/8/5
 */
public class NoticeMessageAdapter extends RecyclerView.Adapter<NoticeMessageAdapter.NoticeMsgViewHolder>{

    private Context mContext;
    private List<NoticeMessageResponse> mList;
    private String mType;

    private NoticeActionListener mListener;

    public NoticeMessageAdapter(Context context, List<NoticeMessageResponse> list, String type) {
        mContext = context;
        mList = list;
        mType = type;
    }

    @Override
    public NoticeMsgViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_notice_message, null);
        NoticeMsgViewHolder holder = new NoticeMsgViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(NoticeMsgViewHolder holder, final int position) {
        final NoticeMessageResponse response = mList.get(position);
        holder.bindData(mContext, response, mType);
        holder.mUnOperate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mList == null) {
                    return;
                }
                mListener.agreeMessage(position, response);
            }
        });

        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener == null) {
                    return;
                }
                mListener.onItemClick(position,response);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class NoticeMsgViewHolder extends RecyclerView.ViewHolder{

        private static final String MSG_FROM_FRIEND = "0";
        private static final String MSG_FROM_GROUP = "2";
        private static final String MSG_UN_VERIFY = "-1";
        private static final String MSG_VERIFY = "0";

        private ImageView mHead;
        private TextView mMessage;
        private TextView mUnOperate;
        private TextView mOperate;
        private RelativeLayout mLayout;

        public NoticeMsgViewHolder(View itemView) {
            super(itemView);
            mHead = (ImageView) itemView.findViewById(R.id.img_head);
            mMessage = (TextView) itemView.findViewById(R.id.tv_message);
            mUnOperate = (TextView) itemView.findViewById(R.id.tv_un_verify);
            mOperate = (TextView) itemView.findViewById(R.id.tv_over_verify);
            mLayout = (RelativeLayout) itemView.findViewById(R.id.item_notice);
        }

        private void bindData(Context context, NoticeMessageResponse response, String type){
            if (response == null) {
                return;
            }
            if (type.equals(NoticeMessageActivity.TYPE_VERIFY_MSG)) {
                showVerifyMessageInfo(context, response);
            }else if(type.equals(NoticeMessageActivity.TYPE_OTHER_MSG)){
                Glide.with(context).load(R.drawable.icon_msg_notice).transform(new GlideRoundTransform(context, 10)).into(mHead);
                mMessage.setText(response.getMessageContext());
            }

        }

        private void showVerifyMessageInfo(Context context, NoticeMessageResponse response) {
            int imageResource = R.drawable.icon_msg_notice;
            String isFriend = response.getMessageType();
            if (MSG_FROM_FRIEND.equals(isFriend)) {
                imageResource = R.drawable.default_avatar;
                mMessage.setText(context.getString(R.string.friend_notice_message, checkStringEmpty(response.getName())));
            }else if(MSG_FROM_GROUP.equals(isFriend)){
                imageResource = R.drawable.default_group;
                mMessage.setText(context.getString(R.string.group_verify_notice_message, checkStringEmpty(response.getName()), checkStringEmpty(response.getCircleName())));
            }
            Glide.with(context).load(response.getUserImage()).error(imageResource).transform(new GlideRoundTransform(context, 10)).into(mHead);

            String messageStatus = response.getMessageState();
            if (MSG_UN_VERIFY.equals(messageStatus)) {
                mUnOperate.setVisibility(View.VISIBLE);
                mOperate.setVisibility(View.GONE);
                mUnOperate.setText(R.string.agree);
            }else if (MSG_VERIFY.equals(messageStatus)) {
                mOperate.setVisibility(View.VISIBLE);
                mUnOperate.setVisibility(View.GONE);
                mOperate.setText(R.string.over_agree);
            }
        }

        private String checkStringEmpty(String str){
            if (TextUtils.isEmpty(str)) {
                return "";
            }
            return str;
        }
    }

    public interface NoticeActionListener{
        void agreeMessage(int position, NoticeMessageResponse response);
        void onItemClick(int position, NoticeMessageResponse response);
    }

    public void setListener(NoticeActionListener listener) {
        mListener = listener;
    }
}
