package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.thinkcoo.mobile.domain.UseCaseRuntimeTrack;
import com.thinkcoo.mobile.domain.account.AutoLoginUseCase;
import com.thinkcoo.mobile.domain.account.GetLoggedAccountUseCase;
import com.thinkcoo.mobile.domain.account.LoginUseCase;
import com.thinkcoo.mobile.domain.applaunch.CopyDataDictionaryDbFileUseCase;
import com.thinkcoo.mobile.injector.ActivityScope;
import com.thinkcoo.mobile.model.entity.Account;
import com.thinkcoo.mobile.presentation.ErrorMessageFactory;
import com.thinkcoo.mobile.presentation.mvp.views.WelcomeView;
import javax.inject.Inject;
import rx.Subscriber;

/**
 * Created by robert on 2016/5/22.
 */
@ActivityScope
public class AppLaunchPresenter extends MvpBasePresenter<MvpView>{

    private static  String TAG = "AppLaunchPresenter";

    CopyDataDictionaryDbFileUseCase mCopyDataDictionaryDbFileUseCase;
    GetLoggedAccountUseCase mGetLoggedAccountUseCase;
    ErrorMessageFactory mErrorMessageFactory;
    UseCaseRuntimeTrack mUseCaseRuntimeTrack;
    LoginUseCase mLoginUseCase;
    AutoLoginUseCase mAutoLoginUseCase;

    @Inject
    public AppLaunchPresenter(
            CopyDataDictionaryDbFileUseCase copyDataDictionaryDbFileUseCase,
            ErrorMessageFactory errorMessageFactory,
            GetLoggedAccountUseCase getLoggedAccountUseCase,
            UseCaseRuntimeTrack useCaseRuntimeTrack,
            LoginUseCase loginUseCase,
            AutoLoginUseCase autoLoginUseCase
    ) {

        this.mCopyDataDictionaryDbFileUseCase = copyDataDictionaryDbFileUseCase;
        this.mErrorMessageFactory = errorMessageFactory;
        this.mGetLoggedAccountUseCase = getLoggedAccountUseCase;
        this.mUseCaseRuntimeTrack = useCaseRuntimeTrack;
        this.mLoginUseCase = loginUseCase;
        this.mAutoLoginUseCase = autoLoginUseCase;
    }

    public void appLaunch() {
        if (!isViewAttached()){
            return;
        }
        ThinkcooLog.d(TAG,"=== 开始执行兼容老版本数据用例 ===");
        mUseCaseRuntimeTrack.firstUseCaseStart();
        mCopyDataDictionaryDbFileUseCase.execute(getCopyDataDictionaryDbFileSub());
    }

    public void autoLogin(Account account){
        mAutoLoginUseCase.execute(getAutoLoginSub(account),account);
    }

    private Subscriber getAutoLoginSub(final Account account) {

        return new Subscriber() {
            @Override
            public void onCompleted() {

            }
            @Override
            public void onError(Throwable e) {
                if (!isViewAttached()){
                    return;
                }
                getWelcomeView().showToast("自动登录失败");
                getWelcomeView().gotoLoginPageTryLogin(account);
            }
            @Override
            public void onNext(Object o) {
                if (!isViewAttached()){
                    return;
                }
                getWelcomeView().gotoHomePage();
            }
        };
    }

    private Subscriber getCopyDataDictionaryDbFileSub() {
        return new Subscriber<Void>() {
            @Override
            public void onCompleted() {
                ThinkcooLog.d(TAG, "=== 执行拷贝字典数据库用例完成 ===");
                ThinkcooLog.d(TAG, "=== 开始执行获取上次登录账户用例 ===");
                mUseCaseRuntimeTrack.lastUseCaseEnd();
                mGetLoggedAccountUseCase.execute(getLoggedAccountSub(), mUseCaseRuntimeTrack.getWelcomePageHoldMillisecond());
            }

            @Override
            public void onError(Throwable e) {
                ThinkcooLog.e(TAG, e.getMessage(), e);
                showToastByThrowableAndFinishView(e);
            }

            @Override
            public void onNext(Void b) {
            }
        };
    }

    private Subscriber getLoggedAccountSub() {
        return new Subscriber<Account>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Account account) {
                if (!isViewAttached()){
                    return;
                }
                ThinkcooLog.d(TAG,"=== 开始根据获得账号跳转界面 ===");
                getWelcomeView().gotoNextPageByAccount(account);
            }
        };
    }


    private void showToastByThrowableAndFinishView(Throwable e){
        if (!isViewAttached()){
            return;
        }
        getWelcomeView().showToast(mErrorMessageFactory.createErrorMsg(e));
        getWelcomeView().closeSelf();
    }


    private WelcomeView getWelcomeView() {
        return (WelcomeView)getView();
    }


    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        mCopyDataDictionaryDbFileUseCase.unSubscribe();
        mGetLoggedAccountUseCase.unSubscribe();
        mAutoLoginUseCase.unSubscribe();
    }
}
