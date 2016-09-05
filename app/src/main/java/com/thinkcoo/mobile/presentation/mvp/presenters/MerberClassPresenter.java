package com.thinkcoo.mobile.presentation.mvp.presenters;

import android.text.TextUtils;

import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.domain.schedule.GetClassListCase;
import com.thinkcoo.mobile.model.entity.ClassGroup;
import com.thinkcoo.mobile.presentation.ErrorMessageFactory;
import com.thinkcoo.mobile.presentation.mvp.views.MerBerClassView;
import com.yz.im.domain.LoadGroupMemberUseCase;
import com.yz.im.model.entity.serverresponse.GroupMemberResponse;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/8/20.
 */
public class MerberClassPresenter extends MvpBasePresenter<MerBerClassView> {

    public static final String TAG = "MerberClassPresenter";

    GetClassListCase mGetClassListCase;
    LoadGroupMemberUseCase mMemberUseCase;
    ErrorMessageFactory mErrorMessageFactory;
    @Inject
    public MerberClassPresenter(GetClassListCase getClassListCase ,
//                                LoadGroupMemberUseCase memberUseCase,
                                ErrorMessageFactory errorMessageFactory) {
        mGetClassListCase = getClassListCase;
//        mMemberUseCase = memberUseCase;
        mErrorMessageFactory = errorMessageFactory;
    }
    public void loadClassList(String eventId) {
        getView().showProgressDialog(R.string.loading);

        mGetClassListCase.execute(loadClsssSub(),eventId);
    }


    private Subscriber<List<ClassGroup>> loadClsssSub() {

        return new Subscriber<List<ClassGroup>>() {
            @Override
            public void onCompleted() {
                getView().hideProgressDialogIfShowing();
            }

            @Override
            public void onError(Throwable e) {
                ThinkcooLog.e(TAG, e.getMessage(), e);
                getView().hideProgressDialogIfShowing();
                getView().showToast(mErrorMessageFactory.createErrorMsg(e));
            }

            @Override
            public void onNext(List<ClassGroup> classGroupList) {
                if (!isViewAttached()) {
                    return;
                }

                getView().setClassList(classGroupList);


            }
        };
    }

    public void loadGroupMemberList(String groupId) {
        if (!isViewAttached()) {
            return;
        }

        if (TextUtils.isEmpty(groupId)) {
//            getView().showToast(R.string.operation_failture);// TODO: 2016/8/22
            return;
        }
        getView().showProgressDialog(R.string.loading_data);
//        mMemberUseCase.execute(getMemberSub(), groupId);
    }

    private Subscriber<List<GroupMemberResponse>> getMemberSub() {
        return new Subscriber<List<GroupMemberResponse>>() {
            @Override
            public void onCompleted() {
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
            public void onNext(List<GroupMemberResponse> list) {
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialogIfShowing();
                getView().setData(list);
            }
        };
    }
    @Override
    public void detachView(boolean retainInstance) {
        mGetClassListCase.unSubscribe();

    }
}