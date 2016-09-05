package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.thinkcoo.mobile.domain.account.AutoLoginUseCase;
import com.thinkcoo.mobile.model.entity.Account;
import com.thinkcoo.mobile.model.entity.User;
import javax.inject.Inject;
import rx.Subscriber;

/**
 * Created by Robert.yao on 2016/8/17.
 */
public class MainPagePresenter extends MvpBasePresenter<MvpView> {
    
    private static final String TAG = "MainPagePresenter";

    AutoLoginUseCase mAutoLoginUseCase;

    @Inject
    public MainPagePresenter(AutoLoginUseCase autoLoginUseCase) {
        mAutoLoginUseCase = autoLoginUseCase;
    }

    public void autoLogin(Account account){
        mAutoLoginUseCase.execute(getLoginSub(),account);
    }

    private Subscriber getLoginSub() {
        
        return new Subscriber<User>() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable e) {
                ThinkcooLog.e(TAG,"==== 自动登录失败 ====",e);
            }
            @Override
            public void onNext(User user) {
                ThinkcooLog.e(TAG,"==== 自动登录成功 ====");
            }
        };
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        mAutoLoginUseCase.unSubscribe();
    }
}
