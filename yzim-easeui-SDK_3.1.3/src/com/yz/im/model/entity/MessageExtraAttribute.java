package com.yz.im.model.entity;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cys on 2016/8/8
 */
public class MessageExtraAttribute {

    public static final String KEY_SENDER_ID = "fromId";
    public static final String KEY_SENDER_NAME = "fromName";
    public static final String KEY_SENDER_IMAGE = "fromImage";
    public static final String KEY_RECEIVER_ID = "fromToId";
    public static final String KEY_GROUP_NAME = "groupName";
    public static final String KEY_GROUP_IMAGE = "groupRealName";
    public static final String KEY_SENDER_GROUP_REMARK = "groupRealName";

    private Map<String, String> mMap;

    public MessageExtraAttribute() {
        mMap = new HashMap<>();
    }

    public void putSenderId(String senderId) {
        if (TextUtils.isEmpty(senderId)) {
            return;
        }
        mMap.put(KEY_SENDER_ID, senderId);
    }

    public void putSenderName(String senderName) {
        if (TextUtils.isEmpty(senderName)) {
            return;
        }
        mMap.put(KEY_SENDER_NAME, senderName);
    }

    public void putSenderImage(String senderImage) {
        if (TextUtils.isEmpty(senderImage)) {
            return;
        }
        mMap.put(KEY_SENDER_IMAGE, senderImage);
    }

    public void putReceiverId(String receiverId) {
        if (TextUtils.isEmpty(receiverId)) {
            return;
        }
        mMap.put(KEY_RECEIVER_ID, receiverId);
    }

    public void putGroupName(String groupName) {
        if (TextUtils.isEmpty(groupName)) {
            return;
        }
        mMap.put(KEY_GROUP_NAME, groupName);
    }

    public void putGroupImage(String groupImage) {
        if (TextUtils.isEmpty(groupImage)) {
            return;
        }
        mMap.put(KEY_GROUP_IMAGE, groupImage);
    }

    public void putUserGroupRemark(String groupRemark) {
        if (TextUtils.isEmpty(groupRemark)) {
            return;
        }
        mMap.put(KEY_SENDER_GROUP_REMARK, groupRemark);
    }

    public String getSenderId(){
        return mMap.get(KEY_SENDER_ID);
    }

    public String getSenderName(){
        return mMap.get(KEY_SENDER_NAME);
    }

    public String getSenderImage(){
        return mMap.get(KEY_SENDER_IMAGE);
    }

    public String getReceiverId(){
        return mMap.get(KEY_RECEIVER_ID);
    }

    public String getGroupName(){
        return mMap.get(KEY_GROUP_NAME);
    }

    public String getUserGroupRemark(){
        return mMap.get(KEY_GROUP_IMAGE);
    }

    public String getGroupImage(){
        return mMap.get(KEY_SENDER_GROUP_REMARK);
    }

    public void setOtherAttribute(String key, String value){
        if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) {
            return;
        }
        mMap.put(key, value);
    }

    public String getAttributeBykEY(String key){
        if (TextUtils.isEmpty(key)) {
            return "";
        }
        return mMap.get(key);
    }

    public Map<String, String> getMap() {
        return mMap;
    }
}
