package com.thinkcoo.mobile.model.repository.impl;

import android.content.Context;

import com.example.administrator.publicmodule.entity.BaseResponse;
import com.example.administrator.publicmodule.model.db.base.DbManagerProvider;
import com.example.administrator.publicmodule.model.rest.ApiFactory;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.thinkcoo.mobile.model.cache.LoginUserCache;
import com.thinkcoo.mobile.model.db.AccountDao;
import com.thinkcoo.mobile.model.entity.Account;
import com.thinkcoo.mobile.model.entity.CheckVcode;
import com.thinkcoo.mobile.model.entity.License;
import com.thinkcoo.mobile.model.entity.User;
import com.thinkcoo.mobile.model.entity.UserSpace;
import com.thinkcoo.mobile.model.entity.Vcode;
import com.thinkcoo.mobile.model.entity.WebNode;
import com.thinkcoo.mobile.model.entity.serverresponse.CheckVcodeResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.LoginResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.VcodeResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.robust.ServerDataConverter;
import com.thinkcoo.mobile.model.exception.ServerResponseException;
import com.thinkcoo.mobile.model.exception.account.LicenseNotAgreeException;
import com.thinkcoo.mobile.model.exception.account.LogoutException;
import com.thinkcoo.mobile.model.repository.AccountRepository;
import com.thinkcoo.mobile.model.rest.apis.AccountApi;
import com.thinkcoo.mobile.utils.EasemobConstantsUtils;
import com.yz.im.IMHelper;
import com.yz.im.services.BackgroundInitService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Administrator on 2016/5/19.
 */
@Singleton
public class AccountRepositoryImpl implements AccountRepository {

    public static final String TAG = "AccountRepositoryImpl";

    @Inject
    AccountDao mAccountDao;
    @Inject
    AccountApi mAccountApi;
    @Inject
    LoginUserCache mLazyLoginUserCache;
    @Inject
    ApiFactory mApiFactory;
    @Inject
    DbManagerProvider mDbManagerProvider;
    @Inject
    ServerDataConverter mServerDataConverter;
    @Inject
    EasemobConstantsUtils mEasemobConstantsUtils;
    @Inject
    Context mContext;

    @Inject
    LoginUserCache mLoginUserCache;

    @Inject
    public AccountRepositoryImpl() {
    }

    @Override
    public Observable<User> login(final Account account) {

        ThinkcooLog.e(TAG, "=== Repository login ===");
        return mAccountApi.login(account.getAccountName(), account.getPassword()).flatMap(new Func1<BaseResponse<LoginResponse>, Observable<User>>() {
            @Override
            public Observable<User> call(BaseResponse<LoginResponse> loginResponseBaseResponse) {

                ThinkcooLog.e(TAG, "=== Repository login call === ");
                if (loginResponseBaseResponse.isSuccess()) {
                    setAccountStatusToLogged(account);
                    User user = User.fromServerResponse(loginResponseBaseResponse.getData());
                    return Observable.just(user);
                } else {
                    return Observable.error(new ServerResponseException(loginResponseBaseResponse.getMsg()));
                }
            }
        }).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                ThinkcooLog.e(TAG, "=== Repository login call Throwable===");
                ThinkcooLog.e(TAG, throwable.getMessage(), throwable);
            }
        });
    }

    @Override
    public Observable autoLogin(final Account account) {
        return mAccountApi.login(account.getAccountName(), account.getPassword())
                .flatMap(new Func1<BaseResponse<LoginResponse>, Observable<User>>() {
            @Override
            public Observable<User> call(BaseResponse<LoginResponse> loginResponseBaseResponse) {

                ThinkcooLog.e(TAG, "=== Repository login call === ");
                if (loginResponseBaseResponse.isSuccess()) {
                    setAccountStatusToLogged(account);
                    User user = User.fromServerResponse(loginResponseBaseResponse.getData());
                    return userEnvironmentInit(user);
                } else {
                    return Observable.error(new ServerResponseException(loginResponseBaseResponse.getMsg()));
                }
            }
        }).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                ThinkcooLog.e(TAG, "=== Repository login call Throwable===");
                ThinkcooLog.e(TAG, throwable.getMessage(), throwable);
            }
        });
    }

    private void setAccountStatusToLogged(Account account) {
        account.setLogin(true);
        try {
            mAccountDao.deleteAll(Account.class);
            mAccountDao.replace(account);
        } catch (Exception e) {
            ThinkcooLog.e(TAG, e.getMessage(), e);
        }
    }

    @Override
    public Observable<String> logout() {

        return  Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

                try {
                    //FIXME 在这里调用logout不是最佳实践
                    IMHelper.getInstance().logout(true);
                    mLoginUserCache.destroy();
                    Account account = mAccountDao.getLoggedAccount();
                    account.setLogin(false);
                    mAccountDao.update(account);
                    subscriber.onNext(account.getAccountName());
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(new LogoutException());
                }

            }
        });

    }

    @Override
    public Observable register(Account account, Vcode vcode, License license) {
        if (license.isEmpty() || !license.isAgree()) {
            return Observable.error(new LicenseNotAgreeException());
        }
        return mAccountApi.register(account.getAccountName(), vcode.getContent(), vcode.getCert(), "0")
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends BaseResponse>>() {
                    @Override
                    public Observable<? extends BaseResponse> call(Throwable throwable) {
                        return null;
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
    public Observable gotPassword(Account account, Vcode vcode) {
        return mAccountApi.forGetPassword(account.getAccountName(), vcode.getContent(), vcode.getCert(), "1").flatMap(new Func1<BaseResponse<CheckVcodeResponse>, Observable<CheckVcode>>() {
            @Override
            public Observable<CheckVcode> call(BaseResponse<CheckVcodeResponse> checkVcodeResponse) {
                if (!checkVcodeResponse.isSuccess()) {
                    return Observable.error(new ServerResponseException(checkVcodeResponse.getMsg()));
                }
                return Observable.just(CheckVcode.fromServerResponse(checkVcodeResponse.getData(),mServerDataConverter));
            }
        });
    }

    @Override
    public Observable changePassword(Account account) {
        return null;
    }

    @Override
    public Observable changeAccountName(final Account account, final Vcode vcode, final String oldPhoneNumbe) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse>>() {
            @Override
            public Observable<BaseResponse> call(User user) {
                return mAccountApi.changePhoneNumber(user.getUserId(),account.getAccountName(),account.getPassword(),vcode.getContent(),vcode.getCert(),oldPhoneNumbe);
            }
        }).flatMap(new Func1<BaseResponse, Observable<?>>() {
            @Override
            public Observable<?> call(BaseResponse baseResponse) {
                if (!baseResponse.isSuccess()) {
                    Observable.error(new ServerResponseException(baseResponse.getMsg()));
                }
                return Observable.empty();
            }
        });
    }

    @Override
    public Observable<Vcode> requestRegisterVcode(Account account) {

        return mAccountApi.loadVcode(account.getAccountName(), "0", "0").flatMap(new Func1<BaseResponse<VcodeResponse>, Observable<Vcode>>() {
            @Override
            public Observable<Vcode> call(BaseResponse<VcodeResponse> vcodeResponseBaseResponse) {
                return Observable.just(Vcode.fromServerResponse(vcodeResponseBaseResponse.getData()));
            }
        });
    }

    @Override
    public Observable completeRegister(Account account, String areaCode, String userName) {
        return mAccountApi.completeRegister(account.getAccountName(), account.getPassword(), areaCode, userName)
                .flatMap(new Func1<BaseResponse, Observable<?>>() {
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
    public Observable completeFindPassword(CheckVcode checkVcode, String newPassword) {
        return mAccountApi.setupPassword(checkVcode.getEncryptStr(), checkVcode.getUserId(), newPassword).flatMap(new Func1<BaseResponse, Observable<?>>() {
            @Override
            public Observable<?> call(BaseResponse baseResponse) {
                if (!baseResponse.isSuccess()){
                    return Observable.error(new ServerResponseException(baseResponse.getMsg()));
                }
                return Observable.empty();
            }
        });
    }

    @Override
    public Observable<Account> getLoggedAccount() {
        return Observable.create(new Observable.OnSubscribe<Account>() {
            @Override
            public void call(Subscriber<? super Account> subscriber) {
                try {
                    subscriber.onNext(mAccountDao.getLoggedAccount());
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    @Override
    public Observable userEnvironmentInit(final User user) {

        return Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {

                //生成用户空间
                createUserSpace(user);
                //生成用户数据库
                createUserDb(user);
                //将节点信息保存到apiFactory
                fillWebNode(user);
                //登录到环信
                loginToHx(user);
                //user 保存到缓存
                mLazyLoginUserCache.put(user);

                subscriber.onNext(null);
                subscriber.onCompleted();

                ThinkcooLog.d(TAG,"===初始化用户环境完成 ===");

            }
        }).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                ThinkcooLog.e(TAG, throwable.getMessage(), throwable);
            }
        });
    }

    private void createUserDb(User user) {
        mDbManagerProvider.setUserDbName(user.getUserSpace().getUserDbDir());
    }

    private void loginToHx(User user) {
        String userId = user.getUserId();
        BackgroundInitService.startInit(mContext, userId);
    }

    private void createUserSpace(User user) {
        user.setUserSpace(UserSpace.createByUserId(user.getUserId()));
    }

    private void fillWebNode(User user) {

        List<WebNode> webNodeList = user.getUserWebNodeList();
        if (null == webNodeList || webNodeList.size() == 0) {
            ThinkcooLog.e(TAG, "=== 服务器没有返回节点信息 ===");
            return;
        }
        for (WebNode webNode : webNodeList) {
            ThinkcooLog.d(TAG, "=== 登录获取到节点名 : " + webNode.getSymbol() + " , 节点地址 : " + webNode.getApiUrl());
            mApiFactory.putWebNode(webNode.getSymbol(), webNode.getApiUrl());
        }
    }

    @Override
    public Observable modifyPassword(final String oldPwd, final String newPwd) {
        return mLazyLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse>>() {
            @Override
            public Observable<BaseResponse> call(User user) {
                return mAccountApi.modifyPassword(user.getUserId(), oldPwd, newPwd);
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
