package com.hyphenate.easeui.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.publicmodule.util.IdOffsetUtil;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.controller.EaseUI.EaseUserProfileProvider;
import com.hyphenate.exceptions.HyphenateException;
import com.yz.im.IMHelper;
import com.yz.im.model.db.entity.Friend;
import com.yz.im.model.db.entity.Group;
import com.yz.im.model.db.entity.Shield;
import com.yz.im.model.entity.MessageExtraAttribute;
import com.yz.im.utils.GlideRoundTransform;

public class EaseUserUtils {

    private static final String TAG = "EaseUserUtils";

    static EaseUserProfileProvider userProvider;

    static {
        userProvider = EaseUI.getInstance().getUserProfileProvider();
    }

    public static void setUserAvatar(Context context, String username, ImageView imageView) {
        if (imageView == null) {
            return;
        }
        String imageUri = getUserImage(username);
        if (imageUri.equals("thinkcoo")) {
            Glide.with(context).load(R.drawable.thinkcoo_icon).transform(new GlideRoundTransform(context, 10)).into(imageView);
        }else {
            Glide.with(context).load(imageUri).transform(new GlideRoundTransform(context, 10))
                    .placeholder(R.drawable.default_avatar).error(R.drawable.default_avatar).into(imageView);
        }
    }

    private static String getUserImage(String username) {
        String imagePath = "";
        if (TextUtils.isEmpty(username)) {
            return imagePath;
        }
        Friend user = IMHelper.getInstance().getFriendInfo(username);
        if (user != null) {
            imagePath = user.getImage();
        } else {
            Shield shield = IMHelper.getInstance().getShieldInfo(username);
            if (shield != null) {
                imagePath = shield.getImager();
            }
        }
        return imagePath;
    }

    public static void setUserNick(String userId, TextView textView) {
        if (textView == null) {
            return;
        }
        String userName = getUserName(userId);
        textView.setText(userName);
    }

    public static String getUserName(String userId) {
        String name = userId;
        if (TextUtils.isEmpty(userId)) {
            return name;
        }

        Friend user = IMHelper.getInstance().getFriendInfo(userId);
        if (user != null) {
            name = user.getShowName();
        } else {
            Shield shield = IMHelper.getInstance().getShieldInfo(userId);
            if (shield != null) {
                name = shield.getName();
            }
        }
        return name;
    }


    /**
     * 根据附加字段取值
     */
    public static void setUserAvatar(Context context, EMMessage message, ImageView imageView) {
        if (imageView == null) {
            return;
        }
        if (message != null) {
            try {
                String userImage = message.getStringAttribute(MessageExtraAttribute.KEY_SENDER_IMAGE);
                if (TextUtils.isEmpty(userImage)) {
                    setUserAvatar(context, IdOffsetUtil.minusOffset(message.getFrom()), imageView);
                } else {
                    Glide.with(context).load(userImage).transform(new GlideRoundTransform(context, 10)).error(R.drawable.default_avatar).into(imageView);
                }
            } catch (Exception e) {
                setUserAvatar(context, IdOffsetUtil.minusOffset(message.getFrom()), imageView);
            }
        } else {
            Glide.with(context).load(R.drawable.default_avatar).transform(new GlideRoundTransform(context, 10)).into(imageView);
        }
    }

    /**
     * 根据附加字段取值
     */
    public static void setUserNick(EMMessage message, TextView textView) {
        if (textView == null) {
            return;
        }
        if (message != null) {
            try {
                String userName = message.getStringAttribute(MessageExtraAttribute.KEY_SENDER_NAME);
                if (!TextUtils.isEmpty(userName)) {
                    textView.setText(userName);
                } else {
                    setUserNick(IdOffsetUtil.minusOffset(message.getFrom()), textView);
                }
            } catch (HyphenateException e) {
                setUserNick(IdOffsetUtil.minusOffset(message.getFrom()), textView);
            }
        }
    }

    public static Group getGroupInfo(String groupId, boolean isHxGroupId) {
        return IMHelper.getInstance().getGroupInfo(groupId, isHxGroupId);
    }

    public static void setGroupAvatar(Context context, String groupId, ImageView imageView, boolean isHxGroupId) {
        if (imageView == null) {
            return;
        }
        Group group = getGroupInfo(groupId, isHxGroupId);
        if (group != null && group.getGroupImage() != null) {
            try {
                Glide.with(context).load(group.getGroupImage()).transform(new GlideRoundTransform(context, 10)).error(R.drawable.default_group).placeholder(R.drawable.default_group).into(imageView);
            } catch (Exception e) {
                Glide.with(context).load(R.drawable.default_group).transform(new GlideRoundTransform(context, 10)).into(imageView);
            }
        } else {
            Glide.with(context).load(R.drawable.default_group).transform(new GlideRoundTransform(context, 10)).into(imageView);
        }
    }

    public static void setGroupName(String groupId, TextView textView, boolean isHxGroupId) {
        if (textView == null) {
            return;
        }
        if (textView != null) {
            Group group = getGroupInfo(groupId, isHxGroupId);
            if (group != null && group.getGroupImage() != null) {
                textView.setText(group.getGroupName());
            } else {
                textView.setText(groupId);
            }
        }
    }


    /**
     * 根据附加字段
     */
    public static void setGroupAvatar(Context context, EMMessage message, ImageView imageView) {
        if (imageView == null) {
            return;
        }
        if (message != null ) {
            try {
                String groupImage = message.getStringAttribute(MessageExtraAttribute.KEY_GROUP_IMAGE);
                if (TextUtils.isEmpty(groupImage)) {
                    setGroupAvatar(context, message.getTo(), imageView, true);
                } else {
                    Glide.with(context).load(groupImage).transform(new GlideRoundTransform(context, 10)).error(R.drawable.default_group).placeholder(R.drawable.default_group).into(imageView);
                }
            } catch (Exception e) {
                Glide.with(context).load(R.drawable.default_group).transform(new GlideRoundTransform(context, 10)).into(imageView);
            }
        } else {
            Glide.with(context).load(R.drawable.default_group).transform(new GlideRoundTransform(context, 10)).into(imageView);
        }
    }

    /**
     * 根据附加字段
     */
    public static void setGroupName(EMMessage message, TextView textView) {
        if (textView == null) {
            return;
        }
        if (message != null) {
            try {
                String groupName = message.getStringAttribute(MessageExtraAttribute.KEY_GROUP_NAME);
                if (TextUtils.isEmpty(groupName)) {
                    textView.setText(groupName);
                } else {
                    String groupId = message.getTo();
                    Group group = getGroupInfo(groupId, true);
                    if (group != null && group.getGroupImage() != null) {
                        textView.setText(group.getGroupName());
                    } else {
                        textView.setText(groupId);
                    }
                }
            } catch (HyphenateException e) {
                ThinkcooLog.e(TAG, e.getMessage(), e);
            }
        }
    }


    public static void loadImage(Context context, String uri, ImageView imageView) {
        try {
            Glide.with(context).load(uri).into(imageView);
        } catch (Exception e) {
            Glide.with(context).load(R.drawable.ease_default_avatar).into(imageView);
        }
    }

    public static Friend getUserInfo(String username) {
        return IMHelper.getInstance().getFriendInfo(username);
    }
}
