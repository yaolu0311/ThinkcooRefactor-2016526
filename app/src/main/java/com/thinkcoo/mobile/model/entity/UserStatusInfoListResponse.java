package com.thinkcoo.mobile.model.entity;

import com.example.administrator.publicmodule.entity.Response;
import com.thinkcoo.mobile.model.entity.serverresponse.UserStatusInfoResponse;

import java.util.List;

/**
 * Created by Robert.yao on 2016/6/22.
 */
public class UserStatusInfoListResponse extends Response {

    private List<UserStatusInfoResponse> mUserStatusInfoResponseList;

    public List<UserStatusInfoResponse> getUserStatusInfoResponseList() {
        return mUserStatusInfoResponseList;
    }
    public void setUserStatusInfoResponseList(List<UserStatusInfoResponse> userStatusInfoResponseList) {
        mUserStatusInfoResponseList = userStatusInfoResponseList;
    }
}
