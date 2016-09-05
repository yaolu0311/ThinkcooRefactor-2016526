package com.thinkcoo.mobile.presentation.mvp.presenters;

import android.text.TextUtils;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.domain.trade.CreateMyGoodsUseCase;
import com.thinkcoo.mobile.domain.trade.DeleteMyGoodsPhotoUseCase;
import com.thinkcoo.mobile.domain.trade.EditMyGoodsUseCase;
import com.thinkcoo.mobile.domain.trade.LoadMyGoodsDetailUseCase;
import com.thinkcoo.mobile.model.entity.GoodsPhoto;
import com.thinkcoo.mobile.model.entity.MyGoods;
import com.thinkcoo.mobile.model.entity.MyGoodsDetail;
import com.thinkcoo.mobile.presentation.ErrorMessageFactory;
import com.thinkcoo.mobile.presentation.mvp.views.ReleaseMySellGoodsView;
import javax.inject.Inject;
import rx.Subscriber;

/**
 * Created by Robert.yao on 2016/8/10.
 */
public class ReleaseMyGoodsPresenter extends BaseDetailPresenter<ReleaseMySellGoodsView<MyGoods,MyGoodsDetail>,MyGoods,MyGoodsDetail>{

    DeleteMyGoodsPhotoUseCase mDeleteMyGoodsPhotoUseCase;

    @Inject
    public ReleaseMyGoodsPresenter(EditMyGoodsUseCase editDetailUseCase, CreateMyGoodsUseCase addDetailUseCase, LoadMyGoodsDetailUseCase loadDetailUseCase,DeleteMyGoodsPhotoUseCase deleteMyGoodsPhotoUseCase, ErrorMessageFactory errorMessageFactory) {
        super(editDetailUseCase, addDetailUseCase, loadDetailUseCase, errorMessageFactory);
        mDeleteMyGoodsPhotoUseCase = deleteMyGoodsPhotoUseCase;

    }

    @Override
    protected boolean checkHost(MyGoods host) {


        if (null == host || null == host.getMyGoodsDetail()){
            getView().showToast("请填写完商品信息");
            return false;
        }
        if (null == host.getMyGoodsDetail().getGoodsPhotoList() || host.getMyGoodsDetail().getGoodsPhotoList().isEmpty()){
            getView().showToast("上传商品图片，才能更好吸引买家");
        }
        if (TextUtils.isEmpty(host.getName())){
            getView().showToast("请填写商品名称");
            return false;
        }
        if (TextUtils.isEmpty(host.getPrice())){
            getView().showToast("请填写商品价格");
            return false;
        }
        if (TextUtils.isEmpty(host.getCategory())){
            getView().showToast("请选择商品种类");
            return false;
        }
        if (TextUtils.isEmpty(host.getMyGoodsDetail().getQuality())){
            getView().showToast("请选择商品质量");
            return false;
        }
        if (TextUtils.isEmpty(host.getSchoolName())){
            getView().showToast("请选择学校");
            return false;
        }
        if (null == host.getMyGoodsDetail().getLocation() || TextUtils.isEmpty(host.getMyGoodsDetail().getLocation().getAddress())){
            getView().showToast("请选择学校地址");
            return false;
        }
        if (TextUtils.isEmpty(host.getMyGoodsDetail().getIntroduce())){
            getView().showToast("请为您的商品填写描述");
            return false;
        }
        return true;
    }

    @Override
    protected boolean checkAndCompareHost(MyGoods newHost, MyGoods rawHost) {
        return true;
    }

    @Override
    protected void detailAttachToHost(MyGoods hostObject, MyGoodsDetail detail) {
        hostObject.setMyGoodsDetail(detail);
    }

    public void deleteGoodsPhoto(GoodsPhoto goodsPhoto, final int position) {
        if (!goodsPhoto.isFromServer()){
            getView().deletePhotoByPosition(position);
        }else {
            getView().showProgressDialog(R.string.Is_sending_a_request);
            mDeleteMyGoodsPhotoUseCase.execute(new Subscriber() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    if (!isViewAttached()){
                        return;
                    }
                    getView().hideProgressDialogIfShowing();
                    getView().showToast(mErrorMessageFactory.createErrorMsg(e));
                }

                @Override
                public void onNext(Object o) {
                    if (!isViewAttached()){
                        return;
                    }
                    getView().hideProgressDialogIfShowing();
                    getView().deletePhotoByPosition(position);
                }
            });
        }
    }


    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        mDeleteMyGoodsPhotoUseCase.unSubscribe();
    }
}
