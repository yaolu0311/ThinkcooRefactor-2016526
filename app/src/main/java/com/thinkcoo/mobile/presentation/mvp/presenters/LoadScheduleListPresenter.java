package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.thinkcoo.mobile.domain.schedule.LoadEventListUseCase;
import com.thinkcoo.mobile.model.exception.user.EmptyException;
import com.thinkcoo.mobile.presentation.mvp.views.ScheduleView;
import com.thinkcoo.mobile.presentation.views.component.mydayview.Event;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by Leevin
 * CreateTime: 2016/6/28  15:51
 */
public class LoadScheduleListPresenter extends MvpBasePresenter<ScheduleView>{

    public static final String TAG = "LoadScheduleListPresenter";
    private LoadEventListUseCase mLoadEventListUseCase;

    @Inject
    public LoadScheduleListPresenter(LoadEventListUseCase loadEventListUseCase) {
        mLoadEventListUseCase = loadEventListUseCase;
    }

    public void loadEvents(String startDate, String endDate) {
        if (!isViewAttached()) {
            return;
        }
//        getView().showProgressDialog(R.string.loading);
        mLoadEventListUseCase.execute(getLoadEventsSub(),startDate,endDate);
    }

    private Subscriber getLoadEventsSub() {
        return new Subscriber<List<Event>>() {
      @Override
            public void onCompleted() {
                  if (!isViewAttached()) {
                      return;
                  }
//                getView().hideProgressDialogIfShowing();
            }

            @Override
            public void onError(Throwable e) {
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialogIfShowing();
                if (e instanceof EmptyException) {
                    ThinkcooLog.e(TAG,e.getMessage()+"==============");
                }
//                ThinkcooLog.e(TAG,e.getMessage(),e);
            }

            @Override
            public void onNext(List<Event> events) {
                if (!isViewAttached()) {
                    return;
                }
                // TODO: 2016/7/15
                getView().hideProgressDialogIfShowing();
//                getView().setEvents(events);
            }
        };
    }

}
