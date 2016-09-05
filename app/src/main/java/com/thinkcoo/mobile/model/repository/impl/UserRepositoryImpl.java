package com.thinkcoo.mobile.model.repository.impl;

import com.example.administrator.publicmodule.entity.BaseResponse;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.thinkcoo.mobile.model.cache.LoginUserCache;
import com.thinkcoo.mobile.model.cache.UserCacheProviders;
import com.thinkcoo.mobile.model.entity.User;
import com.thinkcoo.mobile.model.entity.UserBasicInfo;
import com.thinkcoo.mobile.model.entity.UserHarvest;
import com.thinkcoo.mobile.model.entity.UserHarvestDetail;
import com.thinkcoo.mobile.model.entity.UserHobby;
import com.thinkcoo.mobile.model.entity.UserSkill;
import com.thinkcoo.mobile.model.entity.UserWorkExperienceEntity;
import com.thinkcoo.mobile.model.entity.serverresponse.UserBasicInfoResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.UserHarvestDetailResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.UserHarvestsResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.UserHobbyResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.UserSkillResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.UserWorkExperienceResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.robust.ServerDataConverter;
import com.thinkcoo.mobile.model.exception.ServerResponseException;
import com.thinkcoo.mobile.model.exception.user.EmptyException;
import com.thinkcoo.mobile.model.repository.UserRepository;
import com.thinkcoo.mobile.model.rest.apis.UserApi;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.rx_cache.DynamicKey;
import io.rx_cache.DynamicKeyGroup;
import io.rx_cache.EvictProvider;
import io.rx_cache.Reply;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Robert.yao on 2016/3/22.
 */
public class UserRepositoryImpl implements UserRepository {

    public static final String TAG = "UserRepositoryImpl";

    UserApi mUserApi;
    UserCacheProviders mUserCacheProviders;
    LoginUserCache mLoginUserCache;
    ServerDataConverter mServerDataConverter;

    @Inject
    public UserRepositoryImpl(UserApi userApi, UserCacheProviders userCacheProviders, LoginUserCache loginUserCache, ServerDataConverter serverDataConverter) {
        this.mUserApi = userApi;
        this.mLoginUserCache = loginUserCache;
        this.mServerDataConverter = serverDataConverter;
        this.mUserCacheProviders = userCacheProviders;
    }

    @Override
    public Observable<User> getUserMainInfo() {
        return mLoginUserCache.get();
    }


    @Override
    public Observable SetUserBasicInfo(final UserBasicInfo userBasicInfo) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse>>() {
            @Override
            public Observable<BaseResponse> call(User user) {

                return mUserApi.updateUserBaseInfo(user.getUserId(), userBasicInfo.getCertificateType(), userBasicInfo.getCertificateNumber(),
                        userBasicInfo.getNation(), userBasicInfo.getBirthDate(), userBasicInfo.getPersonalPhone(),
                        userBasicInfo.getMail(), userBasicInfo.getPoliticalStatus(), userBasicInfo.getMaritalStatus(), userBasicInfo.getHighestEducation(),
                        userBasicInfo.getLiveAreaCode(), userBasicInfo.getLiveStreet(), userBasicInfo.getBirthPlace(), userBasicInfo.getBirthStreet());
            }
        }).flatMap(new Func1<BaseResponse, Observable<?>>() {
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
    public Observable<List<UserWorkExperienceEntity>> loadWorkExperienceList(final String workId) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse<List<UserWorkExperienceResponse>>>>() {
            @Override
            public Observable<BaseResponse<List<UserWorkExperienceResponse>>> call(User user) {
                return mUserApi.loadWorkExperienceList(user.getUserId(), workId);
            }
        }).flatMap(new Func1<BaseResponse<List<UserWorkExperienceResponse>>, Observable<List<UserWorkExperienceEntity>>>() {
            @Override
            public Observable<List<UserWorkExperienceEntity>> call(BaseResponse<List<UserWorkExperienceResponse>> listBaseResponse) {
                if (!listBaseResponse.isSuccess()) {
                    return Observable.error(new Throwable(listBaseResponse.getMsg()));
                }
                return Observable.just(UserWorkExperienceEntity.fromServerResponse(listBaseResponse.getData(), mServerDataConverter));
            }
        }).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                ThinkcooLog.e(TAG, throwable.getMessage(), throwable);
            }
        });
    }

    @Override
    public Observable deleteWorkExperience(final String experienceId) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse>>() {
            @Override
            public Observable<BaseResponse> call(User user) {
                return mUserApi.deleteWorkExperienceById(user.getUserId(), experienceId);
            }
        }).flatMap(new Func1<BaseResponse, Observable<?>>() {
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
    public Observable addWorkExperience(final String workId, final String content, final String time) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse>>() {
            @Override
            public Observable<BaseResponse> call(User user) {
                return mUserApi.addWorkExperience(user.getUserId(), workId, content, time);
            }
        }).flatMap(new Func1<BaseResponse, Observable<?>>() {
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
    public Observable updateWorkExperience(final UserWorkExperienceEntity response) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse>>() {
            @Override
            public Observable<BaseResponse> call(User user) {
                return mUserApi.updateWorkExperience(user.getUserId(), response.getId(), response.getContent(), response.getTime());
            }
        }).flatMap(new Func1<BaseResponse, Observable<?>>() {
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
    public Observable<UserBasicInfo> getUserBasicInfo(final boolean isUpdateNow) {

        return mLoginUserCache.get().flatMap(new Func1<User, Observable<UserBasicInfo>>() {
            @Override
            public Observable<UserBasicInfo> call(final User user) {

                Observable<UserBasicInfoResponse> apiObservable = mUserApi.loadUserBasicInfo(user.getUserId())
                        .map(new Func1<BaseResponse<UserBasicInfoResponse>, UserBasicInfoResponse>() {
                            @Override
                            public UserBasicInfoResponse call(BaseResponse<UserBasicInfoResponse> userBasicInfoResponseBaseResponse) {
                                return userBasicInfoResponseBaseResponse.getData();
                            }
                        });

                return mUserCacheProviders.loadUserBasicInfo(apiObservable, new DynamicKey(user.getUserId()), new EvictProvider(isUpdateNow)).flatMap(new Func1<Reply<UserBasicInfoResponse>, Observable<UserBasicInfo>>() {
                    @Override
                    public Observable<UserBasicInfo> call(Reply<UserBasicInfoResponse> userBasicInfoResponseReply) {
                        ThinkcooLog.e(TAG, "=== 从" + userBasicInfoResponseReply.getSource().toString() + " 获得了用户基本信息 ===");
                        return Observable.just(UserBasicInfo.fromServerResponse(userBasicInfoResponseReply.getData(), mServerDataConverter));
                    }
                });
            }
        });

    }

    @Override
    public Observable changeChangeUserSignature(final String signature) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse>>() {
            @Override
            public Observable<BaseResponse> call(User user) {
                return mUserApi.updateUserSignature(signature, user.getUserId());
            }
        }).flatMap(new Func1<BaseResponse, Observable<?>>() {
            @Override
            public Observable<User> call(BaseResponse baseResponse) {
                if (!baseResponse.isSuccess()) {
                    return Observable.error(new ServerResponseException(baseResponse.getMsg()));
                }
                return Observable.empty();
            }
        })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ThinkcooLog.e(TAG, throwable.getMessage(), throwable);
                    }
                });
    }

    public Observable<List<UserHarvest>> loadUserHarvestList(final String pageIndex, final String pageSize, final boolean isUpdateNow) {

        return mLoginUserCache.get().flatMap(new Func1<User, Observable<List<UserHarvest>>>() {
            @Override
            public Observable<List<UserHarvest>> call(User user) {

                Observable<UserHarvestsResponse> apiObservable = mUserApi.loadUserHarvestList(user.getUserId(), pageIndex, pageSize).map(new Func1<BaseResponse<UserHarvestsResponse>, UserHarvestsResponse>() {
                    @Override
                    public UserHarvestsResponse call(BaseResponse<UserHarvestsResponse> userHarvestsResponseBaseResponse) {
                        return userHarvestsResponseBaseResponse.getData();
                    }
                });
                return mUserCacheProviders.loadUserHarvestList(apiObservable, new DynamicKeyGroup(user.getUserId(), pageIndex), new EvictProvider(isUpdateNow))
                        .flatMap(new Func1<Reply<UserHarvestsResponse>, Observable<List<UserHarvest>>>() {
                            @Override
                            public Observable<List<UserHarvest>> call(Reply<UserHarvestsResponse> userHarvestsResponseReply) {
                                UserHarvestsResponse userHarvestsResponse = userHarvestsResponseReply.getData();
                                return Observable.just(UserHarvest.fromServerResponse(userHarvestsResponse));
                            }
                        }).onErrorResumeNext(new Func1<Throwable, Observable<? extends List<UserHarvest>>>() {
                            @Override
                            public Observable<? extends List<UserHarvest>> call(Throwable throwable) {
                                return Observable.error(throwable);
                            }
                        });
            }
        });

    }

    @Override
    public Observable addUserHarvest(final UserHarvest userHarvest) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse>>() {
            @Override
            public Observable<BaseResponse> call(User user) {
                return mUserApi.addUserHarvest(user.getUserId(), userHarvest.getGrantName(),
                        userHarvest.getGrantDepartment(), userHarvest.getGrantTime(),
                        userHarvest.getUserHarvestDetail().getGrantLevel(), userHarvest.getUserHarvestDetail().getGrantRank(), getAttachmentFromUserHarvest(userHarvest));
            }
        }).flatMap(new Func1<BaseResponse, Observable<?>>() {
            @Override
            public Observable<?> call(BaseResponse baseResponse) {
                if (!baseResponse.isSuccess()) {
                    return Observable.error(new ServerResponseException(baseResponse.getMsg()));
                }
                return Observable.empty();
            }
        });
    }

    @Override
    public Observable updateUserHarvest(final UserHarvest userHarvest) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse>>() {
            @Override
            public Observable<BaseResponse> call(User user) {
                return mUserApi.updateUserHarvest(user.getUserId(), userHarvest.getGrantId(),
                        userHarvest.getGrantName(), userHarvest.getGrantDepartment(),
                        userHarvest.getGrantTime(), userHarvest.getUserHarvestDetail().getGrantLevel(),
                        userHarvest.getUserHarvestDetail().getGrantRank(), getAttachmentFromUserHarvest(userHarvest));
            }
        }).flatMap(new Func1<BaseResponse, Observable<?>>() {
            @Override
            public Observable<?> call(BaseResponse baseResponse) {
                if (!baseResponse.isSuccess()) {
                    return Observable.error(new ServerResponseException(baseResponse.getMsg()));
                }
                return Observable.empty();
            }
        });
    }

    // 获取收获图片的所有路径以“,”号拼接
    public String getAttachmentFromUserHarvest(UserHarvest userHarvest) {
        StringBuilder builder = new StringBuilder();
        builder.append("");
        List<UserHarvestDetail.GrantPicBean> grantPicList = userHarvest.getUserHarvestDetail().getGrantPicList();
        if (grantPicList == null || grantPicList.size() == 0) {
            return builder.toString();
        }
        for (UserHarvestDetail.GrantPicBean grantPicBean : grantPicList) {
            builder.append(",").append(grantPicBean.getGrantPicPath());
        }
        return builder.toString();
    }

    @Override
    public Observable<UserHarvestDetail> loadUserHarvestDetail(final UserHarvest userHarvest) {

        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse<UserHarvestDetailResponse>>>() {
            @Override
            public Observable<BaseResponse<UserHarvestDetailResponse>> call(User user) {
                return mUserApi.loadUserHarvestDetail(user.getUserId(), userHarvest.getGrantId());
            }
        }).flatMap(new Func1<BaseResponse<UserHarvestDetailResponse>, Observable<UserHarvestDetail>>() {
            @Override
            public Observable<UserHarvestDetail> call(BaseResponse<UserHarvestDetailResponse> userHarvestDetailResponseBaseResponse) {
                if (!userHarvestDetailResponseBaseResponse.isSuccess()) {
                    return Observable.error(new ServerResponseException(userHarvestDetailResponseBaseResponse.getMsg()));
                }
                UserHarvestDetailResponse data = userHarvestDetailResponseBaseResponse.getData();
                if (null == data) {
                    return Observable.error(new EmptyException());
                }
                UserHarvestDetail userHarvestDetail = UserHarvestDetail.fromServerResponse(data);
                return Observable.just(userHarvestDetail);
            }
        }).doOnError(new Action1<Throwable>() {
                         @Override
                         public void call(Throwable throwable) {
                             ThinkcooLog.e(TAG, throwable.getMessage());
                         }
                     }
        );
    }

    @Override
    public Observable deleteUserHarvest(final String harvestId) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse>>() {
            @Override
            public Observable<BaseResponse> call(User user) {
                return mUserApi.deleteUserHarvest(user.getUserId(), harvestId);
            }
        }).flatMap(new Func1<BaseResponse, Observable<?>>() {
            @Override
            public Observable<?> call(BaseResponse baseResponse) {
                if (!baseResponse.isSuccess()) {
                    return Observable.error(new ServerResponseException(baseResponse.getMsg()));
                }
                return Observable.empty();
            }
        });
    }


    @Override
    public Observable changeUserSex(final String sex) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse>>() {
            @Override
            public Observable<BaseResponse> call(User user) {
                return mUserApi.updateUserSex(sex, user.getUserId());
            }
        }).flatMap(new Func1<BaseResponse, Observable<?>>() {
            @Override
            public Observable<User> call(BaseResponse baseResponse) {
                if (!baseResponse.isSuccess()) {
                    return Observable.error(new ServerResponseException(baseResponse.getMsg()));
                }
                return Observable.empty();
            }
        })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ThinkcooLog.e(TAG, throwable.getMessage(), throwable);
                    }
                });
    }

    @Override
    public Observable changeUserName(final String name) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse>>() {
            @Override
            public Observable<BaseResponse> call(User user) {
                return mUserApi.updateUserName(name, user.getUserId());
            }
        }).flatMap(new Func1<BaseResponse, Observable<?>>() {
            @Override
            public Observable<User> call(BaseResponse baseResponse) {
                if (!baseResponse.isSuccess()) {
                    return Observable.error(new ServerResponseException(baseResponse.getMsg()));
                }
                return Observable.empty();
            }
        })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ThinkcooLog.e(TAG, throwable.getMessage(), throwable);
                    }
                });
    }

    @Override
    public Observable deleteUserSkill(final String skillId) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse>>() {
            @Override
            public Observable<BaseResponse> call(User user) {
                return mUserApi.deleteSkill(user.getUserId(), skillId);
            }
        }).flatMap(new Func1<BaseResponse, Observable<?>>() {
            @Override
            public Observable<?> call(BaseResponse baseResponse) {
                if (!baseResponse.isSuccess()) {

                    return Observable.error(new ServerResponseException(baseResponse.getMsg()));
                }
                return Observable.empty();
            }

        }).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                ThinkcooLog.d(TAG, throwable.getMessage());
            }
        });
    }

    @Override
    public Observable addUserSkill(final String skill) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse>>() {
            @Override

            public Observable<BaseResponse> call(User user) {
                return mUserApi.addSkill(user.getUserId(), skill);
            }
        }).flatMap(new Func1<BaseResponse, Observable<?>>() {
            @Override
            public Observable<?> call(BaseResponse baseResponse) {
                if (!baseResponse.isSuccess()) {
                    return Observable.error(new ServerResponseException(baseResponse.getMsg()));
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
    public Observable<List<UserSkill>> getUserSkill() {

        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse<List<UserSkillResponse>>>>() {
            @Override
            public Observable<BaseResponse<List<UserSkillResponse>>> call(User user) {
                return mUserApi.loadUserSkills(user.getUserId());
            }
        }).flatMap(new Func1<BaseResponse<List<UserSkillResponse>>, Observable<List<UserSkill>>>() {
            @Override
            public Observable<List<UserSkill>> call(BaseResponse<List<UserSkillResponse>> listBaseResponse) {
                if (!listBaseResponse.isSuccess()) {
                    return Observable.error(new ServerResponseException(listBaseResponse.getMsg()));
                }
                List<UserSkillResponse> data = listBaseResponse.getData();
                if (null == data) {
                    data = new ArrayList<>();
                }
                return Observable.just(UserSkill.fromServerResponse(data));
            }
        }).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                ThinkcooLog.e(TAG, throwable.getMessage(), throwable);
            }
        });
    }

    @Override
    public Observable<List<UserHobby>> getUserHobby() {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse<List<UserHobbyResponse>>>>() {
            @Override
            public Observable<BaseResponse<List<UserHobbyResponse>>> call(User user) {
                return mUserApi.loadUserHobbies(user.getUserId());
            }
        }).flatMap(new Func1<BaseResponse<List<UserHobbyResponse>>, Observable<List<UserHobby>>>() {
            @Override
            public Observable<List<UserHobby>> call(BaseResponse<List<UserHobbyResponse>> listBaseResponse) {
                if (!listBaseResponse.isSuccess()) {
                    return Observable.error(new ServerResponseException(listBaseResponse.getMsg()));
                }
                List<UserHobbyResponse> data = listBaseResponse.getData();
                if (null == data) {
                    data = new ArrayList<>();
                }
                return Observable.just(UserHobby.fromServerResponse(data));
            }
        }).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                ThinkcooLog.e(TAG, throwable.getMessage(), throwable);
            }
        });
    }

    @Override
    public Observable deleteUserHobby(final String hobbyId) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse>>() {
            @Override
            public Observable<BaseResponse> call(User user) {
                return mUserApi.deleteHobby(user.getUserId(), hobbyId);
            }
        }).flatMap(new Func1<BaseResponse, Observable<?>>() {
            @Override
            public Observable<?> call(BaseResponse baseResponse) {
                if (!baseResponse.isSuccess()) {

                    return Observable.error(new ServerResponseException(baseResponse.getMsg()));
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
    public Observable addUserHobby(final String hobby) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse>>() {
            @Override
            public Observable<BaseResponse> call(User user) {
                return mUserApi.addHobby(user.getUserId(), hobby);
            }
        }).flatMap(new Func1<BaseResponse, Observable<?>>() {
            @Override
            public Observable<?> call(BaseResponse baseResponse) {
                if (!baseResponse.isSuccess()) {
                    return Observable.error(new ServerResponseException(baseResponse.getMsg()));
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
    public Observable<Boolean> uploadPhoto(final String s) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<Boolean>>() {
            @Override
            public Observable<Boolean> call(User user) {
                RequestBody fileRb  = RequestBody.create(MediaType.parse("multipart/form-data"),new File(s));
                RequestBody userIdRb  = RequestBody.create(MediaType.parse("text/plain"),user.getUserId());
                return mUserApi.upLoadUserPortraits(fileRb,userIdRb).doOnNext(new Action1<BaseResponse>() {
                    @Override
                    public void call(BaseResponse baseResponse) {
                        ThinkcooLog.d(TAG,"=== 上传文件Response : " + baseResponse.isSuccess() + " , " + baseResponse.getMsg() + " ===");
                    }
                }).flatMap(new Func1<BaseResponse, Observable<Boolean>>() {
                    @Override
                    public Observable<Boolean> call(BaseResponse baseResponse) {
                        return Observable.just(baseResponse.isSuccess());
                    }
                }).doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ThinkcooLog.e(TAG,throwable.getMessage(),throwable);
                    }
                });
            }
        });
    }

    @Override
    public Observable feedBack(final String feedbackContent, final String phone, final String email) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse>>() {
            @Override
            public Observable<BaseResponse> call(User user) {
                return mUserApi.feedBack(user.getUserId(), feedbackContent,phone,email);
            }
        }).flatMap(new Func1<BaseResponse, Observable<?>>() {
            @Override
            public Observable<?> call(BaseResponse baseResponse) {
                if (!baseResponse.isSuccess()) {
                    return Observable.error(new ServerResponseException(baseResponse.getMsg()));
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
    public Observable getInviteFriend(final String invitedUserId) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse>>() {
            @Override
            public Observable<BaseResponse> call(User user) {
                return mUserApi.inviteFriend(user.getUserId(), invitedUserId);
            }
        }).flatMap(new Func1<BaseResponse, Observable<?>>() {
            @Override
            public Observable<?> call(BaseResponse baseResponse) {
                if (!baseResponse.isSuccess()) {

                    return Observable.error(new ServerResponseException(baseResponse.getMsg()));
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
