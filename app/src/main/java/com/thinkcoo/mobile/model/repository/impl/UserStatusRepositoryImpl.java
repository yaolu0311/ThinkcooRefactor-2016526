package com.thinkcoo.mobile.model.repository.impl;

import com.example.administrator.publicmodule.entity.BaseResponse;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.thinkcoo.mobile.model.cache.LoginUserCache;
import com.thinkcoo.mobile.model.cache.UserCacheProviders;
import com.thinkcoo.mobile.model.entity.EducationDetail;
import com.thinkcoo.mobile.model.entity.FullTimeJobDetail;
import com.thinkcoo.mobile.model.entity.PartTimeJobDetail;
import com.thinkcoo.mobile.model.entity.TrainDetail;
import com.thinkcoo.mobile.model.entity.User;
import com.thinkcoo.mobile.model.entity.UserStatus;
import com.thinkcoo.mobile.model.entity.UserStatusDetail;
import com.thinkcoo.mobile.model.entity.serverresponse.UserEducationStatusDetails;
import com.thinkcoo.mobile.model.entity.serverresponse.UserJobStatusDetails;
import com.thinkcoo.mobile.model.entity.serverresponse.UserStatusInfoResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.UserTrainStatusDetails;
import com.thinkcoo.mobile.model.entity.serverresponse.robust.ServerDataConverter;
import com.thinkcoo.mobile.model.exception.ServerResponseException;
import com.thinkcoo.mobile.model.exception.user.EmptyException;
import com.thinkcoo.mobile.model.repository.UserStatusRepository;
import com.thinkcoo.mobile.model.rest.apis.UserApi;
import com.thinkcoo.mobile.presentation.views.activitys.UserStatusDetailActivity;

import java.util.List;

import javax.inject.Inject;

import io.rx_cache.DynamicKey;
import io.rx_cache.EvictProvider;
import io.rx_cache.Reply;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Administrator on 2016/5/31.
 */
public class UserStatusRepositoryImpl implements UserStatusRepository {

    public static final String TAG = "UserStatusRepositoryImpl";

    UserApi mUserApi;
    LoginUserCache mLoginUserCache;
    UserCacheProviders mUserCacheProviders;
    ServerDataConverter mServerDataConverter;

    @Inject
    public UserStatusRepositoryImpl(UserApi userApi, LoginUserCache loginUserCache,UserCacheProviders userCacheProviders,ServerDataConverter serverDataConverter) {
        mUserApi = userApi;
        mLoginUserCache = loginUserCache;
        mUserCacheProviders = userCacheProviders;
        mServerDataConverter = serverDataConverter;
    }


    public Observable<List<UserStatus>> loadUserStatusList(final boolean isUpdateNow) {

        return mLoginUserCache.get().flatMap(new Func1<User, Observable<List<UserStatus>>>() {
            @Override
            public Observable<List<UserStatus>> call(User user) {
                Observable<List<UserStatusInfoResponse>> apiObservable = mUserApi.loadUserStatusInfo(user.getUserId()).map(new Func1<BaseResponse<List<UserStatusInfoResponse>>, List<UserStatusInfoResponse>>() {
                    @Override
                    public List<UserStatusInfoResponse> call(BaseResponse<List<UserStatusInfoResponse>> listBaseResponse) {
                        return listBaseResponse.getData();
                    }
                });
                return mUserCacheProviders.loadUserStatusList(apiObservable,new DynamicKey(user.getUserId()),new EvictProvider(isUpdateNow)).flatMap(new Func1<Reply<List<UserStatusInfoResponse>>, Observable<List<UserStatus>>>() {
                    @Override
                    public Observable<List<UserStatus>> call(Reply<List<UserStatusInfoResponse>> listReply) {
                        List<UserStatusInfoResponse> userStatusInfoResponseList = listReply.getData();
                        if (null == userStatusInfoResponseList || userStatusInfoResponseList.isEmpty()){
                            return Observable.error(new EmptyException());
                        }
                        return Observable.just(UserStatus.fromServerResponse(userStatusInfoResponseList,mServerDataConverter));
                    }
                });
//                        .onErrorResumeNext(new Func1<Throwable, Observable<? extends List<UserStatus>>>() {
//                    @Override
//                    public Observable<? extends List<UserStatus>> call(Throwable throwable) {
//                        if (throwable instanceof ProxyProviders.RxCacheException){
//                            return Observable.just(new ArrayList<UserStatus>());
//                        }else {
//                            return Observable.error(throwable);
                        }
//                    }
//                });
//            }
        });
    }

    @Override
    public Observable<UserStatusDetail> loadUserEducationStatusDetail(final UserStatus userStatus) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse<UserEducationStatusDetails>>>() {
            @Override
            public Observable<BaseResponse<UserEducationStatusDetails>> call(User user) {
                return mUserApi.loadEducationStatusDetails(user.getUserId(), userStatus.getId());
            }
        }).flatMap(new Func1<BaseResponse<UserEducationStatusDetails>, Observable<UserStatusDetail>>() {
            @Override
            public Observable<UserStatusDetail> call(BaseResponse<UserEducationStatusDetails> userEducationStatusDetailsBaseResponse) {
                if (null == userEducationStatusDetailsBaseResponse || !userEducationStatusDetailsBaseResponse.isSuccess()) {
                    return Observable.error(new ServerResponseException(userEducationStatusDetailsBaseResponse.getMsg()));
                }
                UserEducationStatusDetails data = userEducationStatusDetailsBaseResponse.getData();
                UserStatusDetail userStatusDetail = UserEducationStatusDetails.fromServerResponse(data);
                return Observable.just(userStatusDetail);
            }
        }).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                ThinkcooLog.e(TAG, throwable.getMessage(), throwable);
            }
        });
    }

    @Override
    public Observable<UserStatusDetail> loadUserTrainStatusDetail(final UserStatus userStatus) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse<UserTrainStatusDetails>>>() {
            @Override
            public Observable<BaseResponse<UserTrainStatusDetails>> call(User user) {
                return mUserApi.loadTrainStatusDetails(user.getUserId(), userStatus.getId());
            }
        }).flatMap(new Func1<BaseResponse<UserTrainStatusDetails>, Observable<UserStatusDetail>>() {
            @Override
            public Observable<UserStatusDetail> call(BaseResponse<UserTrainStatusDetails> userTrainStatusDetailsBaseResponse) {
                if (null == userTrainStatusDetailsBaseResponse || !userTrainStatusDetailsBaseResponse.isSuccess()) {
                    return Observable.error(new ServerResponseException(userTrainStatusDetailsBaseResponse.getMsg()));
                }
                UserTrainStatusDetails data = userTrainStatusDetailsBaseResponse.getData();
                return Observable.just(UserTrainStatusDetails.fromServerResponse(data));
            }
        }).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                ThinkcooLog.e(TAG, throwable.getMessage(), throwable);
            }
        });
    }

    @Override
    public Observable<UserStatusDetail> loadUserFullTimeWorkStatusDetail(final UserStatus userStatus) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse<UserJobStatusDetails>>>() {
            @Override
            public Observable<BaseResponse<UserJobStatusDetails>> call(User user) {
                return mUserApi.loadFullTimeJobStatusDetails(user.getUserId(), userStatus.getId());
            }
        }).flatMap(new Func1<BaseResponse<UserJobStatusDetails>, Observable<UserStatusDetail>>() {
            @Override
            public Observable<UserStatusDetail> call(BaseResponse<UserJobStatusDetails> userJobStatusDetailsBaseResponse) {
                if (null == userJobStatusDetailsBaseResponse || !userJobStatusDetailsBaseResponse.isSuccess()) {
                    return Observable.error(new ServerResponseException(userJobStatusDetailsBaseResponse.getMsg()));
                }
                UserJobStatusDetails data = userJobStatusDetailsBaseResponse.getData();
                return Observable.just(UserJobStatusDetails.fromServerResponse(data));
            }
        }).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                ThinkcooLog.e(TAG, throwable.getMessage(), throwable);
            }
        });
    }

    @Override
    public Observable<UserStatusDetail> loadUserPartTimeWorkStatusDetail(final UserStatus userStatus) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse<UserJobStatusDetails>>>() {
            @Override
            public Observable<BaseResponse<UserJobStatusDetails>> call(User user) {
                return mUserApi.loadPartTimeJobStatusDetails(user.getUserId(), userStatus.getId());
            }
        }).flatMap(new Func1<BaseResponse<UserJobStatusDetails>, Observable<UserStatusDetail>>() {
            @Override
            public Observable<UserStatusDetail> call(BaseResponse<UserJobStatusDetails> userJobStatusDetailsBaseResponse) {
                if (null == userJobStatusDetailsBaseResponse || !userJobStatusDetailsBaseResponse.isSuccess()) {
                    return Observable.error(new ServerResponseException(userJobStatusDetailsBaseResponse.getMsg()));
                }
                UserJobStatusDetails data = userJobStatusDetailsBaseResponse.getData();
                return Observable.just(UserJobStatusDetails.fromServerResponse(data));
            }
        }).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                ThinkcooLog.e(TAG, throwable.getMessage(), throwable);
            }
        });
    }

    @Override
    public Observable<UserStatusDetail> loadUserStatusDetail(UserStatus userStatus) {
        int type = userStatus.getType();
        Observable observable;
        switch (type) {
            case UserStatusDetailActivity.EDUCATION_STATUS_TYPE:
                observable = loadUserEducationStatusDetail(userStatus);
                break;
            case UserStatusDetailActivity.TRAIN_STATUS_TYPE:
                observable = loadUserTrainStatusDetail(userStatus);
                break;
            case UserStatusDetailActivity.FULL_TIME_WORK_STATUS_TYPE:
                observable = loadUserFullTimeWorkStatusDetail(userStatus);
                break;
            case UserStatusDetailActivity.PART_TIME_WORK_STATUS_TYPE:
                observable = loadUserPartTimeWorkStatusDetail(userStatus);
                break;
            default:
                observable = Observable.error(new Throwable("illegal arguments when addUserStatus"));
                break;
        }
        return observable;
    }

    @Override
    public Observable deleteUserStatus(final UserStatus userStatus) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse>>() {
            @Override
            public Observable<BaseResponse> call(User user) {
                return mUserApi.deleteStatusById(user.getUserId(), user.getName(), userStatus.getId());
            }
        }).flatMap(new Func1<BaseResponse, Observable<?>>() {
            @Override
            public Observable<?> call(BaseResponse baseResponse) {
                if(!baseResponse.isSuccess()){
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
    public Observable updateUserStatus(UserStatus userStatus) {
        int type = userStatus.getType();
        Observable observable;
        switch (type) {
            case UserStatusDetailActivity.EDUCATION_STATUS_TYPE:
                observable = editEducationStatus(userStatus);
                break;
            case UserStatusDetailActivity.TRAIN_STATUS_TYPE:
                observable = editTrainStatus(userStatus);
                break;
            case UserStatusDetailActivity.FULL_TIME_WORK_STATUS_TYPE:
                observable = editFullTimeWorkStatus(userStatus);
                break;
            case UserStatusDetailActivity.PART_TIME_WORK_STATUS_TYPE:
                observable = editPartTimeWorkStatus(userStatus);
                break;
            default:
                observable = Observable.error(new Throwable("illegal parameter when updateUserStatus"));
                break;
        }
        return observable;
    }

    private Observable editEducationStatus(final UserStatus userStatus) {
        final EducationDetail educationDetail = (EducationDetail) userStatus.getUserStatusDetail();
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse>>() {
            @Override
            public Observable<BaseResponse> call(User user) {
                return mUserApi.updateEducationStatus(userStatus.getId(), user.getUserId(), user.getName(), userStatus.getStartTime(), userStatus.getTitle(), educationDetail.getDepartment(),
                        educationDetail.getMajor(), educationDetail.getClassNumber(), educationDetail.getStudentId(), educationDetail.getPostName(),
                        userStatus.getExtraInfo());
            }
        }).flatMap(new Func1<BaseResponse, Observable<?>>() {
            @Override
            public Observable<?> call(BaseResponse baseResponse) {
                if(!baseResponse.isSuccess()){
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

    private Observable editTrainStatus(final UserStatus userStatus) {
        final TrainDetail trainDetail = (TrainDetail) userStatus.getUserStatusDetail();
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse>>() {
            @Override
            public Observable<BaseResponse> call(User user) {
                return mUserApi.updateTrainStatus(userStatus.getId(), user.getUserId(), user.getName(), userStatus.getStartTime(), userStatus.getTitle(), trainDetail.getStudentId(),
                        trainDetail.getClassNumber(), userStatus.getExtraInfo());
            }
        }).flatMap(new Func1<BaseResponse, Observable<?>>() {
            @Override
            public Observable<?> call(BaseResponse baseResponse) {
                if(!baseResponse.isSuccess()){
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

    private Observable editFullTimeWorkStatus(final UserStatus userStatus) {
        final FullTimeJobDetail fullTimeJobDetail = (FullTimeJobDetail) userStatus.getUserStatusDetail();
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse>>() {
            @Override
            public Observable<BaseResponse> call(User user) {
                return mUserApi.updateFullTimeJobStatus(userStatus.getId(), user.getUserId(), user.getName(), userStatus.getStartTime(), userStatus.getTitle(), fullTimeJobDetail.getEmployerId(),
                        fullTimeJobDetail.getIndustry_direction(), fullTimeJobDetail.getBranchName(), userStatus.getExtraInfo(), fullTimeJobDetail.getResponsibility());
            }
        }).flatMap(new Func1<BaseResponse, Observable<?>>() {
            @Override
            public Observable<?> call(BaseResponse baseResponse) {
                if(!baseResponse.isSuccess()){
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

    private Observable editPartTimeWorkStatus(final UserStatus userStatus) {
        final PartTimeJobDetail partTimeJobDetail = (PartTimeJobDetail) userStatus.getUserStatusDetail();
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse>>() {
            @Override
            public Observable<BaseResponse> call(User user) {
                return mUserApi.updatePartTimeJobStatus(userStatus.getId(), user.getUserId(), user.getName(), userStatus.getStartTime(), userStatus.getTitle(), partTimeJobDetail.getEmployerId(),
                        partTimeJobDetail.getIndustry_direction(), partTimeJobDetail.getBranchName(), userStatus.getExtraInfo(), partTimeJobDetail.getResponsibility());
            }
        }).flatMap(new Func1<BaseResponse, Observable<?>>() {
            @Override
            public Observable<?> call(BaseResponse baseResponse) {
                if(!baseResponse.isSuccess()){
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
    public Observable toggleUserStatusOpenStatus(final String statusId, final String isDisplay) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse>>() {
            @Override
            public Observable<BaseResponse> call(User user) {
                return mUserApi.statusDisplayToggle(user.getUserId(), statusId, isDisplay);
            }
        }).flatMap(new Func1<BaseResponse, Observable<?>>() {
            @Override
            public Observable<?> call(BaseResponse baseResponse) {
                if(!baseResponse.isSuccess()){
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
    public Observable addUserStatus(UserStatus userStatus) {
        int type = userStatus.getType();
        Observable observable;
        switch (type) {
            case UserStatusDetailActivity.EDUCATION_STATUS_TYPE:
                observable = addEducationStatus(userStatus);
                break;
            case UserStatusDetailActivity.TRAIN_STATUS_TYPE:
                observable = addTrainStatus(userStatus);
                break;
            case UserStatusDetailActivity.FULL_TIME_WORK_STATUS_TYPE:
                observable = addFullTimeWorkStatus(userStatus);
                break;
            case UserStatusDetailActivity.PART_TIME_WORK_STATUS_TYPE:
                observable = addPartTimeWorkStatus(userStatus);
                break;
            default:
                observable = Observable.error(new Throwable("illegal arguments when addUserStatus"));
                break;
        }
        return observable;
    }

    private Observable addEducationStatus(final UserStatus userStatus) {
        final EducationDetail educationDetail = (EducationDetail) userStatus.getUserStatusDetail();
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse>>() {
            @Override
            public Observable<BaseResponse> call(User user) {
                return mUserApi.addEducationStatus(user.getUserId(), user.getName(), userStatus.getStartTime(), userStatus.getTitle(), educationDetail.getDepartment(),
                        educationDetail.getMajor(), educationDetail.getClassNumber(), educationDetail.getStudentId(), educationDetail.getPostName(),
                        userStatus.getExtraInfo());
            }
        }).flatMap(new Func1<BaseResponse, Observable<?>>() {
            @Override
            public Observable<?> call(BaseResponse baseResponse) {
                if(!baseResponse.isSuccess()){
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

    private Observable addTrainStatus(final UserStatus userStatus) {
        final TrainDetail trainDetail = (TrainDetail) userStatus.getUserStatusDetail();
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse>>() {
            @Override
            public Observable<BaseResponse> call(User user) {
                return mUserApi.addTrainStatus(user.getUserId(), user.getName(), userStatus.getStartTime(), userStatus.getTitle(), trainDetail.getStudentId(),
                        trainDetail.getClassNumber(), userStatus.getEditTime());
            }
        }).flatMap(new Func1<BaseResponse, Observable<?>>() {
            @Override
            public Observable<?> call(BaseResponse baseResponse) {
                if(!baseResponse.isSuccess()){
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

    private Observable addFullTimeWorkStatus(final UserStatus userStatus) {
        final FullTimeJobDetail fullTimeJobDetail = (FullTimeJobDetail) userStatus.getUserStatusDetail();
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse>>() {
            @Override
            public Observable<BaseResponse> call(User user) {
                return mUserApi.addFullTimeJobStatus(user.getUserId(), user.getName(), userStatus.getStartTime(), userStatus.getTitle(), fullTimeJobDetail.getEmployerId(),
                        fullTimeJobDetail.getIndustry_direction(), fullTimeJobDetail.getBranchName(), userStatus.getExtraInfo(), fullTimeJobDetail.getResponsibility());
            }
        }).flatMap(new Func1<BaseResponse, Observable<?>>() {
            @Override
            public Observable<?> call(BaseResponse baseResponse) {
                if(!baseResponse.isSuccess()){
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

    private Observable addPartTimeWorkStatus(final UserStatus userStatus) {
        final PartTimeJobDetail partTimeJobDetail = (PartTimeJobDetail) userStatus.getUserStatusDetail();
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse>>() {
            @Override
            public Observable<BaseResponse> call(User user) {
                return mUserApi.addPartTimeJobStatus(user.getUserId(), user.getName(), userStatus.getStartTime(), userStatus.getTitle(), partTimeJobDetail.getEmployerId(),
                        partTimeJobDetail.getIndustry_direction(), partTimeJobDetail.getBranchName(), userStatus.getExtraInfo(), partTimeJobDetail.getResponsibility());
            }
        }).flatMap(new Func1<BaseResponse, Observable<?>>() {
            @Override
            public Observable<?> call(BaseResponse baseResponse) {
                if(!baseResponse.isSuccess()){
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
