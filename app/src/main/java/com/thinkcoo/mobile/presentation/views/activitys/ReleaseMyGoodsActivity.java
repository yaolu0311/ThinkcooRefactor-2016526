package com.thinkcoo.mobile.presentation.views.activitys;

import android.view.View;
import android.widget.TextView;
import com.thinkcoo.mobile.model.entity.MyGoods;
import com.thinkcoo.mobile.model.entity.MyGoodsDetail;
import com.thinkcoo.mobile.presentation.mvp.presenters.BaseDetailPresenter;
import com.thinkcoo.mobile.presentation.mvp.presenters.ReleaseMyGoodsPresenter;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseDetailActivity;
import javax.inject.Inject;

/**
 * Created by Robert.yao on 2016/8/10.
 */
public abstract class ReleaseMyGoodsActivity extends BaseDetailActivity<MyGoods,MyGoodsDetail> {

    @Inject
    ReleaseMyGoodsPresenter mReleaseMyGoodsPresenter;

    @Override
    protected BaseDetailPresenter generateBaseDetailPresenter() {
        return mReleaseMyGoodsPresenter;
    }

    @Override
    protected void setupTitle(TextView textView) {
        if (isEditBuyMode()){
            textView.setText("编辑求购");
        }else if (isEditSellMode()){
            textView.setText("编辑出售");
        }else if (isAddBuyMode()){
            textView.setText("发布求购");
        }else if (isAddSellMode()){
            textView.setText("发布出售");
        }else {
            throw new IllegalArgumentException("error activity mode (ReleaseMyGoodsActivity)");
        }

    }
    @Override
    protected void setupRightTextView(TextView textView) {
        textView.setVisibility(View.VISIBLE);
        textView.setText("提交");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mReleaseMyGoodsPresenter.addHost();
            }
        });
    }

    private boolean isEditSellMode() {
        return isEditMode()&& !mHostObject.typeIsBuy();
    }
    private boolean isEditBuyMode() {
        return isEditMode() && mHostObject.typeIsBuy();
    }
    private boolean isAddSellMode() {
        return isAddMode() && !mHostObject.typeIsBuy();
    }
    private boolean isAddBuyMode() {
        return isAddMode() && mHostObject.typeIsBuy();
    }

}
