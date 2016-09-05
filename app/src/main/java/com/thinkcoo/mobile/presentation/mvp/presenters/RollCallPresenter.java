package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.domain.location.GetLocationUseCase;
import com.thinkcoo.mobile.domain.location.UpLoadLocationCase;
import com.thinkcoo.mobile.domain.schedule.RollCallCase;
import com.thinkcoo.mobile.domain.schedule.SignInUseCase;
import com.thinkcoo.mobile.domain.schedule.StartRollCallUseCase;
import com.thinkcoo.mobile.domain.schedule.UpdateAttenceRadiuCase;
import com.thinkcoo.mobile.model.entity.Location;
import com.thinkcoo.mobile.presentation.mvp.views.RollCallView;
import com.thinkcoo.mobile.presentation.views.component.mydayview.Event;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/6/30.
 */
public class RollCallPresenter extends MvpBasePresenter<RollCallView> {

    private RollCallCase mRollCallCase;
    private GetLocationUseCase mGetLocationUseCase;
    private UpLoadLocationCase mUpLoadLocationCase;
    public static final String TAG = "RollCallPresenter";

    //修改半径的case
    private UpdateAttenceRadiuCase mUpadteAttenceRadiuCase;
    //开始点名
    private StartRollCallUseCase mStartRollCallUseCase;
    // 签到
    private SignInUseCase mSignInUseCase;

    private Event mEvent;
    private String mUuid;

    @Inject
    public RollCallPresenter(RollCallCase rollCallCase, GetLocationUseCase getLocationUseCase, SignInUseCase signInUseCase,
                             UpdateAttenceRadiuCase updateAttenceRadiuCase, StartRollCallUseCase startRollCallUseCase,
                             UpLoadLocationCase upLoadLocationCase) {
        this.mSignInUseCase = signInUseCase;
        this.mStartRollCallUseCase = startRollCallUseCase;
        this.mRollCallCase = rollCallCase;
        this.mUpadteAttenceRadiuCase = updateAttenceRadiuCase;
        this.mGetLocationUseCase = getLocationUseCase;
        this.mUpLoadLocationCase = upLoadLocationCase;

    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        mGetLocationUseCase.unSubscribe();
        mUpLoadLocationCase.unSubscribe();
        mRollCallCase.unSubscribe();
    }

    /**
     * 更新半径信息
     */
    public void updateRadiu(String accountId, String eventId, String attenceRadiu) {
        if (!isViewAttached()) {
            return;
        }
        mUpadteAttenceRadiuCase.execute(getUpdateRadiuSub(),accountId,eventId,attenceRadiu);
    }

    public Subscriber getUpdateRadiuSub() {
        return new Subscriber() {
            @Override
            public void onCompleted() {
                if (!isViewAttached()) {
                    return;
                }
                getView().showToast(getString(R.string.update_success));
            }

            @Override
            public void onError(Throwable e) {
                if (!isViewAttached()) {
                    return;
                }
                ThinkcooLog.e(TAG, e.getMessage(), e);
                getView().showToast(getString(R.string.update_failure));
            }

            @Override
            public void onNext(Object o) {

            }
        };
    }

    // 创建者点名
    public void ownerShakeAndShake(Event event, String uuid) {
        if (!isViewAttached()) {
            return;
        }
        this.mEvent = event;
        this.mUuid = uuid;
        mGetLocationUseCase.execute(getLocationSub());
    }


    public Subscriber getLocationSub() {
        return new Subscriber<Location> () {
            @Override
            public void onCompleted() {
                ThinkcooLog.e(TAG, "========onCompleted===================");
            }

            @Override
            public void onError(Throwable e) {
                ThinkcooLog.e(TAG, e.getMessage(),e);
                getView().showToast(e.getMessage());
                getView().reOpenShake();
            }

            @Override
            public void onNext(Location location) {
                if (!isViewAttached()) {
                    return;
                }
                // TODO: 2016/8/13  定位成功
                if (mEvent.isCreateAuthor) {
                    mStartRollCallUseCase.execute(getStartRollCallUseCaseSub(), mEvent, mUuid, location);
                } else {
                    mSignInUseCase.execute(getSignerShakeAndShakeSub(),mEvent,location);
                }
            }
        };
    }

    private Subscriber getStartRollCallUseCaseSub() {
        return new Subscriber() {
            @Override
            public void onCompleted() {
                if (!isViewAttached()) {
                    return;
                }
                getView().showToast(getString(R.string.roll_call_success));
                getView().reOpenShake();
                getView().gotoRollCallResultPage(mUuid);
            }

            @Override
            public void onError(Throwable e) {
                if (!isViewAttached()) {
                    return;
                }
                ThinkcooLog.e(TAG,e.getMessage(),e);
                getView().showToast(e.getMessage());
                getView().reOpenShake();
//                getView().showToast(getString(R.string.rock_call_failure_try_again));
            }

            @Override
            public void onNext(Object o) {
            }
        };
    }
    /**
     * 学生签到
     */
    public void signerShakeAndShake(Event event) {
        if (!isViewAttached()) {
            return;
        }
        this.mEvent = event;
        mGetLocationUseCase.execute(getLocationSub());
    }

    public Subscriber getSignerShakeAndShakeSub() {
        return new Subscriber() {
            @Override
            public void onCompleted() {
                if (!isViewAttached()) {
                    return;
                }
                getView().showToast(getString(R.string.student_sign_success));
                getView().reOpenShake();
            }

            @Override
            public void onError(Throwable e) {
                ThinkcooLog.e(TAG,e.getMessage(),e);
                if (!isViewAttached()) {
                    return;
                }
//                getView().showToast(getString(R.string.sign_in_failure_try_again));
                getView().reOpenShake();
            }

            @Override
            public void onNext(Object o) {

            }
        };
    }


    public String getString(int resId) {
        return getView().getActivityContext().getString(resId);
    }

}
