package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.thinkcoo.mobile.domain.cooperation.ApplyProjectCase;
import com.thinkcoo.mobile.domain.cooperation.CancelCollectionProjectCase;
import com.thinkcoo.mobile.domain.cooperation.CollectionProjectCase;
import com.thinkcoo.mobile.domain.cooperation.LoadProjectDetailCase;
import com.thinkcoo.mobile.domain.cooperation.UpLoadReturnDataCase;
import com.thinkcoo.mobile.domain.cooperation.UploadFileCase;
import com.thinkcoo.mobile.presentation.ErrorMessageFactory;

/**
 * author ：ml on 2016/7/26
 */
public class CooperationProjectPresenter extends MvpBasePresenter<MvpView> {
    private LoadProjectDetailCase mLoadProjectDetailCase;
    private ApplyProjectCase mApplyProjectCase;
    private CollectionProjectCase mCollectionProjectCase;
    private CancelCollectionProjectCase mCancelCollectionProjectCase;
    private UploadFileCase mUploadFileCase;
    private UpLoadReturnDataCase mUpLoadReturnDataCase;
    private ErrorMessageFactory mErrorMessageFactory;

    public CooperationProjectPresenter(LoadProjectDetailCase loadProjectDetailCase, ApplyProjectCase applyProjectCase, CollectionProjectCase collectionProjectCase, CancelCollectionProjectCase cancelCollectionProjectCase,
                                       UploadFileCase uploadFileCase, UpLoadReturnDataCase upLoadReturnDataCase, ErrorMessageFactory errorMessageFactory
    ) {
        this.mLoadProjectDetailCase = loadProjectDetailCase;
        this.mApplyProjectCase = applyProjectCase;
        this.mCollectionProjectCase = collectionProjectCase;
        this.mCancelCollectionProjectCase = cancelCollectionProjectCase;
        this.mUploadFileCase = uploadFileCase;
        this.mUpLoadReturnDataCase = upLoadReturnDataCase;
        this.mErrorMessageFactory = errorMessageFactory;
    }
    // TODO: 2016/7/27   文件的上传下载 以及上传服务端返回的数据



}
