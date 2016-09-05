package com.yz.im.model.rest.apis;

import com.yz.im.model.entity.serverresponse.BaseResponse;
import com.yz.im.model.entity.serverresponse.UserInfoResponse;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by cys on 2016/7/28
 */
public interface HxPersonApi {

    @POST("querypersonalinformation.json")
    Observable<BaseResponse<UserInfoResponse>> loadUserInfo(@Query("userId") String userId);
}
