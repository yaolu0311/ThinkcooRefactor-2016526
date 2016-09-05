package com.thinkcoo.mobile.presentation.mvp.presenters;


import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.thinkcoo.mobile.domain.data_dictionary.GetAddressDDUseCase;
import com.thinkcoo.mobile.model.entity.Province;
import com.thinkcoo.mobile.presentation.mvp.views.AddressView;
import java.util.List;
import javax.inject.Inject;
import rx.Subscriber;

/**
 * Created by Leevin
 * CreateTime: 2016/6/14  11:04
 */
public class AddressPresenter extends MvpBasePresenter<AddressView> {

    public static final String TAG = "AddressPresenter";

    GetAddressDDUseCase mGetAddressDDUseCase;

    @Inject
    public AddressPresenter(GetAddressDDUseCase getAddressDDUseCase) {
        mGetAddressDDUseCase = getAddressDDUseCase;
    }

    @Override
    public void detachView(boolean retainInstance) {
        mGetAddressDDUseCase.unSubscribe();
    }

    public void loadDD() {
        mGetAddressDDUseCase.execute(getLoadDDSub());
    }

    private Subscriber<List<Province>> getLoadDDSub() {

        return new Subscriber<List<Province>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ThinkcooLog.e(TAG,e.getMessage(),e);
            }

            @Override
            public void onNext(List<Province> provinceList) {
                if (!isViewAttached()){
                    return;
                }
                getView().setAddressDataDictionary(provinceList);
                getView().setSelect();
            }
        };
    }
}
