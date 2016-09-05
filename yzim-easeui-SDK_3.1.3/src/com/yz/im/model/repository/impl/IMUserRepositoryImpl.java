package com.yz.im.model.repository.impl;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.administrator.publicmodule.model.rest.ApiFactory;
import com.example.administrator.publicmodule.model.rest.ApiFactoryImpl;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.yz.im.IMHelper;
import com.yz.im.model.cache.FriendCache;
import com.yz.im.model.cache.ShieldCache;
import com.yz.im.model.db.ContactDao;
import com.yz.im.model.db.entity.Friend;
import com.yz.im.model.db.entity.Shield;
import com.yz.im.model.entity.ContactBean;
import com.yz.im.model.entity.serverresponse.BaseResponse;
import com.yz.im.model.entity.serverresponse.FindUserResponse;
import com.yz.im.model.entity.serverresponse.FriendInfoResponse;
import com.yz.im.model.entity.serverresponse.StrangerInfoResponse;
import com.yz.im.model.entity.serverresponse.UserInfoResponse;
import com.yz.im.model.repository.IMUserRepository;
import com.yz.im.model.rest.apis.HxPersonApi;
import com.yz.im.model.rest.apis.HxUserApi;

import java.util.List;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by cys on 2016/7/19
 */
public class IMUserRepositoryImpl implements IMUserRepository {

    private static final String TAG = "IMUserRepositoryImpl";

    private Context mContext;
    private HxPersonApi mHxPersonApi;
    private IMHelper mIMHelper;
    private HxUserApi mApi;
    private FriendCache mFriendCache;
    private ShieldCache mShieldCache;

    public IMUserRepositoryImpl(Context context) {
        mContext = context;
        mHxPersonApi = ApiFactoryImpl.getInstance(context).createApiByClass(HxPersonApi.class, ApiFactory.PERSONAL_PREFIX);
        mApi = ApiFactoryImpl.getInstance(context).createApiByClass(HxUserApi.class, ApiFactory.QECHART_PREFIX);
        mIMHelper = IMHelper.getInstance();
        mFriendCache = FriendCache.getInstance(context);
        mShieldCache = ShieldCache.getInstance(context);
    }

    @Override
    public Observable<UserInfoResponse> loadUserInfo() {
        return mHxPersonApi.loadUserInfo(mIMHelper.getLoginUserId()).flatMap(new Func1<BaseResponse<UserInfoResponse>, Observable<UserInfoResponse>>() {
            @Override
            public Observable<UserInfoResponse> call(BaseResponse<UserInfoResponse> userInfoResponseBaseResponse) {
                if (!userInfoResponseBaseResponse.isSuccess()) {
                    return Observable.error(new Throwable(userInfoResponseBaseResponse.getMsg()));
                }
                return Observable.just(userInfoResponseBaseResponse.getData());
            }
        }).doOnNext(new Action1<UserInfoResponse>() {
            @Override
            public void call(UserInfoResponse response) {
                IMHelper.getInstance().setInfoResponse(response);
            }
        }).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                ThinkcooLog.e(TAG, throwable.getMessage(), throwable);
            }
        });
    }

    @Override
    public Observable<FriendInfoResponse> loadFriendInfo(String friendId) {
        return mApi.loadFriendInfo(mIMHelper.getInfoResponse().getUserId(), friendId)
                .flatMap(new Func1<BaseResponse<FriendInfoResponse>, Observable<FriendInfoResponse>>() {
                    @Override
                    public Observable<FriendInfoResponse> call(BaseResponse<FriendInfoResponse> friendInfoResponseBaseResponse) {
                        if (!friendInfoResponseBaseResponse.isSuccess()) {
                            return Observable.error(new Throwable(friendInfoResponseBaseResponse.getMsg()));
                        }
                        return Observable.just(friendInfoResponseBaseResponse.getData());
                    }
                }).doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ThinkcooLog.e(TAG, throwable.getMessage(), throwable);
                    }
                });
    }

    @Override
    public Observable updateFriendToggleStatus(final String friendId, final String msgTop, final String msgDisturb, final String blackList) {
        return mApi.updateFriendToggleStatus(mIMHelper.getInfoResponse().getUserId(), friendId, msgTop, msgDisturb, blackList)
                .flatMap(new Func1<BaseResponse, Observable<?>>() {
                    @Override
                    public Observable<?> call(BaseResponse baseResponse) {
                        if (!baseResponse.isSuccess()) {
                            return Observable.error(new Throwable(baseResponse.getMsg()));
                        }
                        return Observable.empty();
                    }
                }).doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        Friend friend = mFriendCache.getEntity(friendId);
                        if (friend == null) {
                            return;
                        }
                        friend.setStick(msgTop);
                        friend.setDisturb(msgDisturb);
                        friend.setBlacklist(blackList);
                        mFriendCache.insert(friend);
                    }
                }).doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ThinkcooLog.e(TAG, throwable.getMessage(), throwable);
                    }
                });
    }

    @Override
    public Observable deleteFriend(final String friendId, String type) {
        return mApi.deleteFriend(mIMHelper.getInfoResponse().getUserId(), friendId, type).flatMap(new Func1<BaseResponse, Observable<?>>() {
            @Override
            public Observable<?> call(BaseResponse baseResponse) {
                if (!baseResponse.isSuccess()) {
                    return Observable.error(new Throwable(baseResponse.getMsg()));
                }
                return Observable.empty();
            }
        }).doOnCompleted(new Action0() {
            @Override
            public void call() {
                Friend friend = mFriendCache.getEntity(friendId);
                if (friend == null) {
                    return;
                }
                mFriendCache.delete(friend);
            }
        }).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                ThinkcooLog.e(TAG, throwable.getMessage(), throwable);
            }
        });
    }

    @Override
    public Observable<StrangerInfoResponse> loadStrangerInfo(String userId) {
        return mApi.loadStrangerInfo(mIMHelper.getInfoResponse().getUserId(), userId)
                .flatMap(new Func1<BaseResponse<StrangerInfoResponse>, Observable<StrangerInfoResponse>>() {
                    @Override
                    public Observable<StrangerInfoResponse> call(BaseResponse<StrangerInfoResponse> baseResponse) {
                        if (!baseResponse.isSuccess()) {
                            return Observable.error(new Throwable(baseResponse.getMsg()));
                        }
                        return Observable.just(baseResponse.getData());
                    }
                }).doOnNext(new Action1<StrangerInfoResponse>() {
                    @Override
                    public void call(StrangerInfoResponse strangerInfoResponse) {
                        Shield shield = getShieldFromStrangerInfo(strangerInfoResponse);
                        mShieldCache.insert(shield);
                    }
                }).doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ThinkcooLog.e(TAG, throwable.getMessage(), throwable);
                    }
                });
    }

    @NonNull
    private Shield getShieldFromStrangerInfo(StrangerInfoResponse strangerInfoResponse) {
        Shield shield = new Shield();
        shield.setShield(strangerInfoResponse.getShield());
        shield.setImager(strangerInfoResponse.getImager());
        shield.setUserId(strangerInfoResponse.getUserId());
        shield.setName(strangerInfoResponse.getName());
        shield.setIsVerify(strangerInfoResponse.getIsVerify());
        shield.setSchool(strangerInfoResponse.getSchool());
        shield.setSex(strangerInfoResponse.getSex());
        shield.setUserName(strangerInfoResponse.getName());
        return shield;
    }

    @Override
    public Observable updateStrangerShieldStatus(final String userId, final String type) {
        return mApi.updateShieldStatus(mIMHelper.getInfoResponse().getUserId(), userId, type)
                .flatMap(new Func1<BaseResponse, Observable<?>>() {
                    @Override
                    public Observable<?> call(BaseResponse baseResponse) {
                        if (!baseResponse.isSuccess()) {
                            return Observable.error(new Throwable(baseResponse.getMsg()));
                        }
                        return Observable.empty();
                    }
                }).doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        Shield shield = mShieldCache.getEntity(userId);
                        if (shield == null) {
                            return;
                        }
                        shield.setShield(type);
                        mShieldCache.insert(shield);
                    }
                }).doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ThinkcooLog.e(TAG, throwable.getMessage(), throwable);
                    }
                });
    }

    @Override
    public Observable reliefBlackFriend(final String friendId) {
        return mApi.reliefBlackFriend(mIMHelper.getInfoResponse().getUserId(), friendId, "5", "0").flatMap(new Func1<BaseResponse, Observable<?>>() {
            @Override
            public Observable<?> call(BaseResponse baseResponse) {
                if (!baseResponse.isSuccess()) {
                    return Observable.error(new Throwable(baseResponse.getMsg()));
                }
                return Observable.empty();
            }
        }).doOnCompleted(new Action0() {
            @Override
            public void call() {
                Friend friend = mFriendCache.getEntity(friendId);
                friend.setBlacklist("0");
                mFriendCache.insert(friend);
            }
        }).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                ThinkcooLog.e(TAG, throwable.getMessage(), throwable);
            }
        });
    }

    @Override
    public Observable<List<FindUserResponse>> findUserByNumber(String searchValue, String findUserType, String pageNo, String pageSize) {
        return mApi.findUserByNumber(mIMHelper.getInfoResponse().getUserId(), searchValue, findUserType, pageNo, pageSize)
                .flatMap(new Func1<BaseResponse<List<FindUserResponse>>, Observable<List<FindUserResponse>>>() {
                    @Override
                    public Observable<List<FindUserResponse>> call(BaseResponse<List<FindUserResponse>> findUserResponseBaseResponse) {
                        if (!findUserResponseBaseResponse.isSuccess()) {
                            return Observable.error(new Throwable(findUserResponseBaseResponse.getMsg()));
                        }
                        return Observable.just(findUserResponseBaseResponse.getData());
                    }
                }).doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ThinkcooLog.e(TAG, throwable.getMessage(), throwable);
                    }
                });
    }

    @Override
    public Observable<List<FindUserResponse>> findUserByName(String areaCode, String school, String department, String professional, String realName, String pageNo, String pageSize) {
        return mApi.findUserByName(mIMHelper.getInfoResponse().getUserId(), areaCode, school, department, professional, realName, pageNo, pageSize)
                .flatMap(new Func1<BaseResponse<List<FindUserResponse>>, Observable<List<FindUserResponse>>>() {
                    @Override
                    public Observable<List<FindUserResponse>> call(BaseResponse<List<FindUserResponse>> listBaseResponse) {
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
    public Observable sendInviteReason(String friendIdList, String content) {
        return mApi.sendInviteUserReason(mIMHelper.getInfoResponse().getUserId(), friendIdList, content).flatMap(new Func1<BaseResponse, Observable<?>>() {
            @Override
            public Observable<?> call(BaseResponse baseResponse) {
                if (!baseResponse.isSuccess()) {
                    return Observable.error(new Throwable(baseResponse.getMsg()));
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

    @Override
    public Observable<List<FindUserResponse>> getContactList() {
        ContactDao contactDao = new ContactDao();
        return contactDao.queryContactList(mContext).flatMap(new Func1<List<ContactBean>, Observable<List<FindUserResponse>>>() {
            @Override
            public Observable<List<FindUserResponse>> call(final List<ContactBean> contactBeen) {
                StringBuilder stringBuilder = getContactPhoneNumber(contactBeen);
                return mApi.getContactList(mIMHelper.getInfoResponse().getUserId(), stringBuilder.toString())
                        .flatMap(new Func1<BaseResponse<List<FindUserResponse>>, Observable<List<FindUserResponse>>>() {
                            @Override
                            public Observable<List<FindUserResponse>> call(BaseResponse<List<FindUserResponse>> listBaseResponse) {
                                if (!listBaseResponse.isSuccess()) {
                                    return Observable.error(new Throwable(listBaseResponse.getMsg()));
                                }
                                return Observable.just(listBaseResponse.getData());
                            }
                        }).doOnNext(new Action1<List<FindUserResponse>>() {
                            @Override
                            public void call(List<FindUserResponse> list) {
                                if (list != null) {
                                    for (int i = 0; i < list.size(); i++) {
                                        FindUserResponse response = list.get(i);
                                        for (ContactBean bean : contactBeen) {
                                            if (bean.getPhoneNum().equals(response.getUserName())) {
                                                response.setUserName(bean.getDesplayName());
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        });
            }
        });
    }

    @NonNull
    private StringBuilder getContactPhoneNumber(List<ContactBean> contactBeen) {
        StringBuilder stringBuilder = new StringBuilder();
        int count = contactBeen.size();
        for (int i = 0; i < count; i++) {
            stringBuilder.append(contactBeen.get(i).getPhoneNum());
            if (i < count - 1) {
                stringBuilder.append(",");
            }
        }
        return stringBuilder;
    }


}
