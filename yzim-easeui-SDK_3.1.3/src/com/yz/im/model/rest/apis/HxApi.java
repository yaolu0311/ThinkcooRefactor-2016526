package com.yz.im.model.rest.apis;

import com.yz.im.model.entity.serverresponse.BaseResponse;
import com.yz.im.model.entity.serverresponse.FriendResponse;
import com.yz.im.model.entity.serverresponse.GroupResponse;
import com.yz.im.model.entity.serverresponse.NoticeMessageResponse;
import com.yz.im.model.entity.serverresponse.ShieldResponse;
import com.yz.im.model.entity.serverresponse.SystemSettingResponse;

import java.util.List;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by cys on 2016/7/12
 */
public interface HxApi {

    /**
     * 获取屏蔽人列表
     *
     * @param userId
     * @return
     */
    @POST("queryshield.json")
    Observable<BaseResponse<List<ShieldResponse>>> loadShieldList(@Query("userId") String userId);

    /**
     * 好友列表
     *
     * @param userId
     * @param type   0 获取好友   1 获取黑名单  2 获取全部
     * @return
     */
    @POST("userbookinfo.json")
    Observable<BaseResponse<List<FriendResponse>>> loadFriendList(@Query("userId") String userId, @Query("userType") String type);

    /**
     * 圈子列表
     *
     * @param userId
     * @return
     */
    @POST("grouplist.json")
    Observable<BaseResponse<List<GroupResponse>>> loadGroupList(@Query("userId") String userId);

    /**
     * 系统设置
     *
     * @param userId
     * @return
     */
    @POST("queryuserstatus.json")
    Observable<BaseResponse<SystemSettingResponse>> loadSystemSettingList(@Query("userId") String userId);

    @POST("setting.json")
    Observable<BaseResponse> updateSetting(@Query("userId") String userId, @Query("newMessage") String newMessage, @Query("textingMsg") String textingMsg, @Query("unfamiliarMsg") String unfamiliarMsg);

    /**
     * @param type  type=="0" 表示验证消息  type=="1" 表示其他通知
     */
    @POST("systemmessagetesting.json")
    Observable<BaseResponse<List<NoticeMessageResponse>>> loadNoticeMessage(@Query("userId") String userId, @Query("type") String type);


    /**
     * @param operationType  0表示同意验证消息   2表示删除验证消息
     */
    @POST("systemtesting.json")
    Observable<BaseResponse<List<NoticeMessageResponse>>> dealWithNoticeMessage(@Query("userId") String userId, @Query("messageId") String messageId,  @Query("operationType") String operationType);

}
