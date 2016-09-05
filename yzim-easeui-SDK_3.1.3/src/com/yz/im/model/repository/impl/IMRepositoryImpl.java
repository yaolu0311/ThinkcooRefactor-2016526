package com.yz.im.model.repository.impl;

import android.content.Context;

import com.example.administrator.publicmodule.model.rest.ApiFactory;
import com.example.administrator.publicmodule.model.rest.ApiFactoryImpl;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.yz.im.IMHelper;
import com.yz.im.model.db.entity.Friend;
import com.yz.im.model.db.entity.Group;
import com.yz.im.model.db.entity.Shield;
import com.yz.im.model.db.entity.SystemSetting;
import com.yz.im.model.entity.base.ServerDataConverter;
import com.yz.im.model.entity.serverresponse.BaseResponse;
import com.yz.im.model.entity.serverresponse.FriendResponse;
import com.yz.im.model.entity.serverresponse.GroupResponse;
import com.yz.im.model.entity.serverresponse.NoticeMessageResponse;
import com.yz.im.model.entity.serverresponse.ShieldResponse;
import com.yz.im.model.entity.serverresponse.SystemSettingResponse;
import com.yz.im.model.repository.IMRepository;
import com.yz.im.model.rest.apis.HxApi;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by cys on 2016/7/12
 */
public class IMRepositoryImpl implements IMRepository {

    private static final String TAG = "IMRepositoryImpl";

    private HxApi mHxApi;
    private ServerDataConverter mServerDataConverter;
    private IMHelper mIMHelper;
    private Context mContext;

    public IMRepositoryImpl(Context context) {
        mContext = context.getApplicationContext();
        mHxApi = ApiFactoryImpl.getInstance(mContext).createApiByClass(HxApi.class, ApiFactory.QECHART_PREFIX);
        mServerDataConverter = ServerDataConverter.getInstance();
        mIMHelper = IMHelper.getInstance();
    }

    @Override
    public Observable<SystemSetting> loadSystemSetting(String userId) {
        return mHxApi.loadSystemSettingList(userId).flatMap(new Func1<BaseResponse<SystemSettingResponse>, Observable<SystemSetting>>() {
            @Override
            public Observable<SystemSetting> call(BaseResponse<SystemSettingResponse> systemSettingResponseBaseResponse) {
                if (!systemSettingResponseBaseResponse.isSuccess()) {
                    return Observable.error(new Throwable(systemSettingResponseBaseResponse.getMsg()));
                }
                return Observable.just(SystemSetting.fromServerResponse(systemSettingResponseBaseResponse.getData()));
            }
        }).doOnNext(new Action1<SystemSetting>() {
            @Override
            public void call(SystemSetting systemSetting) {
                mIMHelper.getSettingCache().insert(systemSetting);
            }
        }).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                ThinkcooLog.e(TAG, throwable.getMessage(), throwable);
            }
        });
    }

    @Override
    public Observable<List<Friend>> loadFriendList(String userId, String type) {
        return mHxApi.loadFriendList(userId, type).flatMap(new Func1<BaseResponse<List<FriendResponse>>, Observable<List<Friend>>>() {
            @Override
            public Observable<List<Friend>> call(BaseResponse<List<FriendResponse>> friendListResponseBaseResponse) {
                if (!friendListResponseBaseResponse.isSuccess()) {
                    return Observable.error(new Throwable(friendListResponseBaseResponse.getMsg()));
                }
                List<FriendResponse> list= friendListResponseBaseResponse.getData();
                if (list == null) {
                    list = new ArrayList<>();
                }
                FriendResponse response = new FriendResponse();
                response.setBlacklist("-1");
                response.setRemarkName("思扣");
                response.setImage("thinkcoo");
                response.setRealName("思扣");
                response.setUserId("0");
                list.add(response);
                return Observable.just(Friend.fromServerResponse(list));
            }
        }).doOnNext(new Action1<List<Friend>>() {
            @Override
            public void call(List<Friend> friends) {
                if (friends == null) {
                    friends = new ArrayList<>();
                }
                try {
                    mIMHelper.getFriendCache().insertAll((Friend[]) friends.toArray(new Friend[friends.size()]));
                } catch (Exception e) {
                    ThinkcooLog.e(TAG, e.getMessage(), e);
                }
            }
        }).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                ThinkcooLog.e(TAG, throwable.getMessage(), throwable);
            }
        });
    }

    @Override
    public Observable<List<Group>> loadGroupList(String userId) {
        return mHxApi.loadGroupList(userId).flatMap(new Func1<BaseResponse<List<GroupResponse>>, Observable<List<Group>>>() {
            @Override
            public Observable<List<Group>> call(BaseResponse<List<GroupResponse>> groupListResponseBaseResponse) {
                if (!groupListResponseBaseResponse.isSuccess()) {
                    Observable.error(new Throwable(groupListResponseBaseResponse.getMsg()));
                }
                return Observable.just(Group.fromServerResponse(groupListResponseBaseResponse.getData()));
            }
        }).doOnNext(new Action1<List<Group>>() {
            @Override
            public void call(List<Group> groups) {
                try {
                    mIMHelper.getGroupCache().insertAll((Group[]) groups.toArray(new Group[groups.size()]));
                } catch (Exception e) {
                    ThinkcooLog.e(TAG, e.getMessage(), e);
                }
            }
        }).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                ThinkcooLog.e(TAG, throwable.getMessage(), throwable);
            }
        });
    }

    @Override
    public Observable<List<Shield>> loadShieldList(String userId) {
        return mHxApi.loadShieldList(userId).flatMap(new Func1<BaseResponse<List<ShieldResponse>>, Observable<List<Shield>>>() {
            @Override
            public Observable<List<Shield>> call(BaseResponse<List<ShieldResponse>> shieldListResponseBaseResponse) {
                if (!shieldListResponseBaseResponse.isSuccess()) {
                    Observable.error(new Throwable(shieldListResponseBaseResponse.getMsg()));
                }
                return Observable.just(Shield.fromServerResponse(shieldListResponseBaseResponse.getData()));
            }
        }).doOnNext(new Action1<List<Shield>>() {
            @Override
            public void call(List<Shield> shields) {
                try {
                    mIMHelper.getShieldCache().insertAll((Shield[]) shields.toArray(new Shield[shields.size()]));
                } catch (Exception e) {
                    ThinkcooLog.e(TAG, e.getMessage(), e);
                }
            }
        }).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                ThinkcooLog.e(TAG, throwable.getMessage(), throwable);
            }
        });
    }

    @Override
    public Observable updateSetting(final String newMessage, final String textingMsg, final String unfamiliarMsg) {
        return mHxApi.updateSetting(mIMHelper.getInfoResponse().getUserId(), newMessage, textingMsg, unfamiliarMsg).flatMap(new Func1<BaseResponse, Observable<?>>() {
            @Override
            public Observable<?> call(BaseResponse baseResponse) {
                if (!baseResponse.isSuccess()) {
                    Observable.error(new Throwable(baseResponse.getMsg()));
                }
                return Observable.empty();
            }
        }).doOnCompleted(new Action0() {
            @Override
            public void call() {
                SystemSetting setting = new SystemSetting();
                setting.setIsMessageRemind(newMessage);
                setting.setIsReceveStranger(unfamiliarMsg);
                setting.setIsVerify(textingMsg);
                mIMHelper.getSettingCache().insertAll(setting);

            }
        }).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                ThinkcooLog.e(TAG, throwable.getMessage(), throwable);
            }
        });
    }

    @Override
    public Observable<List<NoticeMessageResponse>> loadNoticeMessage(String type) {
        return mHxApi.loadNoticeMessage(mIMHelper.getInfoResponse().getUserId(), type)
                .flatMap(new Func1<BaseResponse<List<NoticeMessageResponse>>, Observable<List<NoticeMessageResponse>>>() {
                    @Override
                    public Observable<List<NoticeMessageResponse>> call(BaseResponse<List<NoticeMessageResponse>> listBaseResponse) {
                        if (!listBaseResponse.isSuccess()) {
                            return Observable.error(new Throwable(listBaseResponse.getMsg()));
                        }
                        return Observable.just(listBaseResponse.getData());
                    }
                }).doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ThinkcooLog.e(TAG, throwable.getMessage(), throwable);
                    }
                });
    }

    @Override
    public Observable dealWithNoticeMessage(String messageId, String operationType) {
        return mHxApi.dealWithNoticeMessage(mIMHelper.getInfoResponse().getUserId(), messageId, operationType)
                .flatMap(new Func1<BaseResponse<List<NoticeMessageResponse>>, Observable<?>>() {
                    @Override
                    public Observable<?> call(BaseResponse<List<NoticeMessageResponse>> listBaseResponse) {
                        if (!listBaseResponse.isSuccess()) {
                            return Observable.error(new Throwable(listBaseResponse.getMsg()));
                        }
                        return Observable.empty();
                    }
                }).doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ThinkcooLog.e(TAG, throwable.getMessage(), throwable);
                    }
                });
    }
}
