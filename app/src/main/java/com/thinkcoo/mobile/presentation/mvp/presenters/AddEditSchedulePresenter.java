package com.thinkcoo.mobile.presentation.mvp.presenters;

import android.text.TextUtils;

import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.domain.schedule.AddScheduleUseCase;
import com.thinkcoo.mobile.domain.schedule.LoadScheduleUseCase;
import com.thinkcoo.mobile.domain.schedule.UpdateScheduleUseCase;
import com.thinkcoo.mobile.model.entity.Schedule;
import com.thinkcoo.mobile.model.entity.EventTime;
import com.thinkcoo.mobile.presentation.ErrorMessageFactory;
import com.thinkcoo.mobile.presentation.mvp.views.AddEditScheduleView;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by Leevin
 * CreateTime: 2016/6/30  11:01
 */
public class AddEditSchedulePresenter extends MvpBasePresenter<AddEditScheduleView> {

    private static final String TAG = "AddEditSchedulePresenter";
    AddScheduleUseCase mAddScheduleUseCase;
    LoadScheduleUseCase mLoadScheduleUseCase;
    UpdateScheduleUseCase mUpdateScheduleUseCase;
    ErrorMessageFactory mErrorMessageFactory;

    private Schedule mSchedule;
    private Schedule mUpdatedSchedule;

    @Inject
    public AddEditSchedulePresenter(AddScheduleUseCase addScheduleUseCase, LoadScheduleUseCase loadScheduleUseCase, UpdateScheduleUseCase updateScheduleUseCase, ErrorMessageFactory errorMessageFactory) {
        mAddScheduleUseCase = addScheduleUseCase;
        mLoadScheduleUseCase = loadScheduleUseCase;
        mUpdateScheduleUseCase = updateScheduleUseCase;
        mErrorMessageFactory = errorMessageFactory;
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        mAddScheduleUseCase.unSubscribe();
        mLoadScheduleUseCase.unSubscribe();
        mUpdateScheduleUseCase.unSubscribe();
    }

    // 添加
    public void addSchedule() {
        if (!isViewAttached()) {
            return;
        }
        Schedule schedule = getView().getSchedule();
        if (!checkSchedule(schedule)) {
            return;
        }
        getView().showProgressDialog(R.string.commiting);
        mAddScheduleUseCase.execute(getAddScheduleUseCaseSub(),schedule);
    }

    private Subscriber getAddScheduleUseCaseSub() {
        return new Subscriber<Void>() {

            @Override
            public void onCompleted() {
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialogIfShowing();
                getView().closeActivity();// 成功后关闭界面,onResume会重新拉取数据
                getView().showToast(getString(R.string.add_success));
            }

            @Override
            public void onError(Throwable e) {
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialogIfShowing();
                getView().showToast(mErrorMessageFactory.createErrorMsg(e));
                ThinkcooLog.e(TAG, e.getMessage(), e);
            }

            @Override
            public void onNext(Void aVoid) {

            }
        };
    }

    // 加载
    public void loadSchedule(String scheduleId) {
        if (!isViewAttached()) {
            return;
        }
        getView().showProgressDialog(R.string.loading);
        mLoadScheduleUseCase.execute(getLoadScheduleUseCaseSub(),scheduleId);
    }

    private Subscriber getLoadScheduleUseCaseSub() {
        return new Subscriber<Schedule>() {
            @Override
            public void onCompleted() {
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialogIfShowing();
            }

            @Override
            public void onError(Throwable e) {
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialogIfShowing();
                getView().showToast(mErrorMessageFactory.createErrorMsg(e));
                ThinkcooLog.e(TAG, e.getMessage(), e);
            }

            @Override
            public void onNext(Schedule schedule) {
                mSchedule = schedule;
                getView().setSchedule(schedule);
            }
        };
    }

    // 更新
    public void updateSchedule() {
        if (!isViewAttached()) {
            return;
        }
        mUpdatedSchedule = getView().getSchedule();
        if (!checkSchedule(mUpdatedSchedule)) {
            return;
        }
     /*   if (!isChanged(schedule)) {
            getView().closeActivity();
            return;
        }*/
        getView().showProgressDialog(R.string.loading);
        mUpdateScheduleUseCase.execute(getUpdateScheduleUseCaseSub(),mUpdatedSchedule);
    }

    private Subscriber getUpdateScheduleUseCaseSub() {
            return new Subscriber<Void>() {
                @Override
                public void onCompleted() {
                    if (!isViewAttached()) {
                        return;
                    }
                    getView().hideProgressDialogIfShowing();
                    getView().showToast(getString(R.string.update_success));
                    getView().setResultOk(mUpdatedSchedule);
                }

                @Override
                public void onError(Throwable e) {
                    if (!isViewAttached()) {
                        return;
                    }
                    getView().hideProgressDialogIfShowing();
                    ThinkcooLog.e(TAG, e.getMessage(), e);
                    getView().showToast(getString(R.string.update_failure));
//                    getView().closeActivity();
                }

                @Override
                public void onNext(Void aVoid) {

                }
            };
    }


    private boolean checkSchedule(Schedule schedule) {

        if (schedule == null) {
            return false;
        }

        if (TextUtils.isEmpty(schedule.getName())) {
            getView().showToast(getString(R.string.event_name_cannot_be_empty));
            return false;
        }

        if (schedule.getType() == 0) {
            getView().showToast(getString(R.string.event_type_cannot_be_empty));
            return false;
        }

        List<EventTime> eventList = schedule.getEventTimeList();
        if (eventList == null || eventList.size() == 0 ) {
            getView().showToast(getString(R.string.please_check_time));
            return false;
        }

        for (int i = 0; i < eventList.size() ; i++) {
            EventTime eventTime = eventList.get(i);
            if (eventTime == null) {
                return false;
            }
            if (i == 0) { //  主时段的位置不能为空
                if (TextUtils.isEmpty(eventTime.getLocation())) {
                   getView().showToast(getString(R.string.location_cannot_be_empty));
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isChanged(Schedule schedule) {
        return !mSchedule.equals(schedule);
    }


    public String getString(int resId) {
        return getView().getActivityContext().getString(resId);
    }

}
