package com.yz.im.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.publicmodule.util.IdOffsetUtil;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.util.PathUtil;
import com.yz.im.Constant;
import com.yz.im.IMHelper;
import com.yz.im.model.cache.FriendCache;
import com.yz.im.model.db.entity.Group;
import com.yz.im.model.entity.MessageExtraAttribute;
import com.yz.im.model.entity.serverresponse.UserInfoResponse;
import com.yz.im.model.im.provider.ChatFragmentHelper;
import com.yz.im.model.strategy.ChatFragmentStrategy;
import com.yz.im.ui.activity.ChatActivity;
import com.yz.im.utils.ToastUtil;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by cys on 2016/7/1.
 */
public class IMChatFragment extends EaseChatFragment implements View.OnClickListener {

    // constant start from 11 to avoid conflict with constant in base class
    public static final int ITEM_VIDEO = 11;
    public static final int ITEM_FILE = 12;
    public static final int ITEM_VOICE_CALL = 13;
    public static final int ITEM_VIDEO_CALL = 14;
    public static final int ITEM_USER_CARD = 15;

    public static final int REQUEST_CODE_SELECT_VIDEO = 11;
    public static final int REQUEST_CODE_SELECT_FILE = 12;
    public static final int REQUEST_CODE_GROUP_DETAIL = 13;
    public static final int REQUEST_CODE_CONTEXT_MENU = 14;
    public static final int REQUEST_CODE_SELECT_AT_USER = 15;
    public static final int REQUEST_CODE_SELECT_CAPTURE_IMAGE = 16;
    public static final int REQUEST_CODE_SELECT_LOCAL_IMAGE = 17;
    public static final int REQUEST_CODE_MAP = 18;
    public static final int REQUEST_CODE_USER_CARD = 19;

    public static final int REQUEST_CODE_SEND_MONEY = 16;

    public static final int MESSAGE_TYPE_SENT_VOICE_CALL = 1;
    public static final int MESSAGE_TYPE_RECV_VOICE_CALL = 2;
    public static final int MESSAGE_TYPE_SENT_VIDEO_CALL = 3;
    public static final int MESSAGE_TYPE_RECV_VIDEO_CALL = 4;
    public static final int MESSAGE_TYPE_RECV_MONEY = 5;
    public static final int MESSAGE_TYPE_SEND_MONEY = 6;
    public static final int MESSAGE_TYPE_SEND_LUCKY = 7;
    public static final int MESSAGE_TYPE_RECV_LUCKY = 8;

    public static final int MESSAGE_TYPE_SENT_USER_CARD = 1;
    public static final int MESSAGE_TYPE_RECV_USER_CARD = 2;
    public static final int MESSAGE_TYPE_SYSTEM_MSG = 3;

    public static final String KEY_CARD_MESSAGE = "card_message";

    private int chatType;
    private String chatId;
    private ChatFragmentStrategy mStrategy;

    /**附加字段*/
    private MessageExtraAttribute mExtraAttribute;
    private FriendCache mFriendCache;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        mFriendCache = FriendCache.getInstance(getContext());
        getDataFromIntent();
        createExtraAttribute();
        return view;
    }

    private void getDataFromIntent() {
        Bundle bundle = getArguments();
        if (null == bundle) {
            throw new NullPointerException("=== 启动聊天界面时未设置arguments ===");
        }
        chatType = bundle.getInt(Constant.EXTRA_CHAT_TYPE);
        chatId = bundle.getString(Constant.EXTRA_USER_ID);
        mStrategy = bundle.getParcelable(ChatActivity.KEY_STRATEGY);
    }

    private void createExtraAttribute() {
        mExtraAttribute= new MessageExtraAttribute();

        UserInfoResponse infoResponse = getLoginUserInfo();
        addSenderInfoToAttribute(infoResponse);

        if (chatType == EaseConstant.CHATTYPE_GROUP) {
            addGroupInfoToAttribute();
        }else if(chatType == EaseConstant.CHATTYPE_SINGLE){
            mExtraAttribute.putReceiverId(IdOffsetUtil.minusOffset(chatId));
        }
    }

    private UserInfoResponse getLoginUserInfo(){
        return IMHelper.getInstance().getInfoResponse();
    }

    private void addSenderInfoToAttribute(UserInfoResponse response) {
        if (response == null) {
            return;
        }
        mExtraAttribute.putSenderId(response.getUserId());
        mExtraAttribute.putSenderImage(response.getHeadPortrait());
        mExtraAttribute.putSenderName(response.getFullName());
    }

    private Group getChatGroupInfo(){
        return IMHelper.getInstance().getGroupInfo(chatId, true);
    }

    private void addGroupInfoToAttribute() {
        Group group = getChatGroupInfo();
        if (group == null) {
            return;
        }
        mExtraAttribute.putGroupImage(group.getGroupImage());
        mExtraAttribute.putGroupName(group.getGroupName());
        String remarkName = group.getRealName();
        if (TextUtils.isEmpty(remarkName)) {
            remarkName = mExtraAttribute.getSenderName();
        }
        mExtraAttribute.putUserGroupRemark(remarkName);
        mExtraAttribute.putReceiverId(chatId);
    }

    @Override
    protected void initView() {
        super.initView();
        chatFragmentHelper = new ChatFragmentHelper(getContext(), chatId, mExtraAttribute);
    }

    @Override
    protected void setUpView() {
        super.setUpView();
        setChatFragmentListener(chatFragmentHelper);
        titleBar.setRightLayoutClickListener(this);
        setTitleLayout();
        setAtFriendFunction();
    }

    private void setAtFriendFunction() {
        if (chatType == EaseConstant.CHATTYPE_GROUP) {
            inputMenu.getPrimaryMenu().getEditText().addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (count == 1 && "@".equals(String.valueOf(s.charAt(start)))) {
                        ((ChatActivity) getActivity()).mNavigator.navigateToFriendCardActivity(getContext());
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }
    }

    private void setTitleLayout() {
        titleBar.setLeftImageResource(R.drawable.back);
        titleBar.setRightImageResource(mStrategy.getRightImage());
        if ("100000".equals(chatId)) {
            titleBar.getRightLayout().setVisibility(View.GONE);
        }

    }

    @Override
    protected void registerExtendMenuItem() {
        super.registerExtendMenuItem();
        mStrategy.registerExtendMenuItem(inputMenu, extendMenuItemClickListener);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.right_layout == id) {
            if (chatType == Constant.CHATTYPE_SINGLE) {
                mStrategy.gotoNextPage(getContext(), IdOffsetUtil.minusOffset(chatId));
            } else {
                Group group = IMHelper.getInstance().getGroupInfo(chatId, true);
                if (group == null) {
                    ((ChatActivity)getContext()).showToast(R.string.forbid_see_group_info);
                    return;
                }
                mStrategy.gotoNextPage(getContext(), group.getGroupId());
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_SELECT_CAPTURE_IMAGE:
                    if (cameraFile != null && cameraFile.exists()) {
                        sendImageMessage(cameraFile.getAbsolutePath());
                    }
                    break;
                case REQUEST_CODE_SELECT_LOCAL_IMAGE:
                    sendImageByUri(data);
                    break;
                case REQUEST_CODE_MAP:
                    sendLocalMessage(data);
                    break;
                case REQUEST_CODE_SELECT_VIDEO:
                    sendVideo(data);
                    break;
                case REQUEST_CODE_SELECT_FILE:
                    sendFile(data);
                    break;
                case REQUEST_CODE_USER_CARD:
                    sendUserCard(data);
                    break;
                case REQUEST_CODE_SELECT_AT_USER:  //选择@好友后回值
                    if (data != null) {
                        String username = data.getStringExtra("username");
                        inputAtUsername(username, false);
                    }
                    break;
            }
        }
    }

    private void sendImageByUri(Intent data) {
        if (data != null) {
            Uri selectedImage = data.getData();
            if (selectedImage != null) {
                sendPicByUri(selectedImage);
            }
        }
    }

    private void sendLocalMessage(Intent data) {
        double latitude = data.getDoubleExtra("latitude", 0);
        double longitude = data.getDoubleExtra("longitude", 0);
        String locationAddress = data.getStringExtra("address");
        if (locationAddress != null && !locationAddress.equals("")) {
            sendLocationMessage(latitude, longitude, locationAddress);
        } else {
            ToastUtil.getInstance(getContext()).showToastById(R.string.unable_to_get_loaction);
        }
    }

    private void sendVideo(Intent data) {
        if (data != null) {
            int duration = data.getIntExtra("dur", 0);
            String videoPath = data.getStringExtra("path");
            File file = new File(PathUtil.getInstance().getImagePath(), "thvideo" + System.currentTimeMillis());  //// TODO: 2016/6/30 file path
            try {
                FileOutputStream fos = new FileOutputStream(file);
                Bitmap ThumbBitmap = ThumbnailUtils.createVideoThumbnail(videoPath, 3);
                ThumbBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.close();
                sendVideoMessage(videoPath, file.getAbsolutePath(), duration);
            } catch (Exception e) {
                ThinkcooLog.e("IMChatFragment", e.getLocalizedMessage(), e);
            }
        }
    }

    private void sendFile(Intent data) {
        if (data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                sendFileByUri(uri);
            }
        }
    }

    private void sendUserCard(Intent data) {
        if (data != null) {
            String content = data.getStringExtra(KEY_CARD_MESSAGE);
            sendUserCardMessage(content.toString().trim());
        }
    }

    public String getChatId() {
        return chatId;
    }
}
