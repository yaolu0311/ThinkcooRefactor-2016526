package com.yz.im.model.rest.apis;

import com.yz.im.model.entity.serverresponse.BaseResponse;
import com.yz.im.model.entity.serverresponse.FindUserResponse;
import com.yz.im.model.entity.serverresponse.FriendInfoResponse;
import com.yz.im.model.entity.serverresponse.StrangerInfoResponse;

import java.util.List;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by cys on 2016/7/28
 */
public interface HxUserApi {

    @POST("queryfrienddetails.json")
    Observable<BaseResponse<FriendInfoResponse>> loadFriendInfo(@Query("userId") String userId, @Query("friendId") String friendId);

    @POST("friendssetversion6.json")
    Observable<BaseResponse> updateFriendToggleStatus(@Query("userId") String userId, @Query("friendId") String friendId, @Query("isStick") String isStick,
                                                      @Query("isDisturb") String isDisturb, @Query("isBlack") String isBlack);

    @POST("frienddele.json")
    Observable<BaseResponse> deleteFriend(@Query("userId") String userId, @Query("friendId") String friendId, @Query("type") String type);

    @POST("userinfofound.json")
    Observable<BaseResponse<StrangerInfoResponse>> loadStrangerInfo(@Query("userId") String userId, @Query("friendId") String friendId);

    @POST("stangerseting.json")
    Observable<BaseResponse> updateShieldStatus(@Query("userId") String userId, @Query("friendId") String friendId, @Query("type") String type);


    @POST("blackfriendversion6.json")
    Observable<BaseResponse> reliefBlackFriend(@Query("userId") String userId, @Query("friendId") String friendId, @Query("setType") String setType, @Query("set") String set);

    @POST("searchfriends.json")
    Observable<BaseResponse<List<FindUserResponse>>> findUserByNumber(@Query("userId") String userId, @Query("searchValue") String searchValue, @Query("findUserType") String findUserType,
                                                                @Query("pageNo") String pageNo, @Query("pageSize") String pageSize);

    @POST("userexactquery.json")
    Observable<BaseResponse<List<FindUserResponse>>> findUserByName(@Query("userId") String userId, @Query("areaCode") String areaCode, @Query("school") String school,  @Query("department") String department,
                                                                    @Query("professional") String professional,  @Query("realName") String realName, @Query("pageNo") String pageNo, @Query("pageSize") String pageSize);

    /**
     * 添加用户
     */
    @POST("sendcheckmsg.json")
    Observable<BaseResponse> sendInviteUserReason(@Query("userId") String userId, @Query("friendIdList") String friendIdList, @Query("content") String content);

    @POST("getphonenumquery.json")
    Observable<BaseResponse<List<FindUserResponse>>> getContactList(@Query("userId") String userId, @Query("userNameList") String userNameList );

}
