package com.thinkcoo.mobile.presentation.views.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.model.entity.Banner;
import com.thinkcoo.mobile.presentation.mvp.presenters.BannerPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.BannerView;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Robert.yao on 2016/7/18.
 */
public abstract class BaseBannerFragment extends BaseFragment implements BannerView {

    public static final int AUTO_TURNING_TIME = 20 * 1000;
    private static final String TAG = "BaseBannerFragment";

    @Inject
    BannerPresenter mBannerPresenter;

    @Bind(R.id.convenientBanner)
    ConvenientBanner mConvenientBanner;


    @Override
    protected MvpBasePresenter generatePresenter() {
        return mBannerPresenter;
    }

    @Override
    public void setBannerList(List<Banner> bannerList) {
        if (null != bannerList && !bannerList.isEmpty()){
            mConvenientBanner.setPages(refreshPages(),bannerList);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mConvenientBanner.startTurning(AUTO_TURNING_TIME);
    }

    @Override
    public void onPause() {
        super.onPause();
        mConvenientBanner.stopTurning();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.banner;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.banner,null);
        ButterKnife.bind(this, rootView);
        setupConvenientBanner();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ThinkcooLog.e(TAG,"=== onViewCreated ===");
        mBannerPresenter.loadBannerList(provideBannerType());
    }

    protected abstract int provideBannerType();

    private void setupConvenientBanner() {
        mConvenientBanner.setPages(initPages(),initData());
        mConvenientBanner.setPageIndicator(createPageIndicator());
        mConvenientBanner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
    }

    private int[] createPageIndicator() {
        return new int[]{R.mipmap.icon_point,R.mipmap.icon_point_pre};
    }

    private List<Integer> initData() {
        List<Integer> drawableList = new ArrayList<>();
        drawableList.add(R.mipmap.default_no_image);
        drawableList.add(R.mipmap.default_no_image);
        return drawableList;
    }

    private CBViewHolderCreator<LocalImageHolderView> initPages() {

      return new CBViewHolderCreator<LocalImageHolderView>() {
          @Override
          public LocalImageHolderView createHolder() {
              return new LocalImageHolderView();
          }
      };
    }

    private CBViewHolderCreator<NetworkImageHolderView> refreshPages() {

       return new CBViewHolderCreator<NetworkImageHolderView>() {
           @Override
           public NetworkImageHolderView createHolder() {
               return new NetworkImageHolderView();
           }
       };
    }

    public class LocalImageHolderView implements Holder<Integer> {
        private ImageView mImageView;
        @Override
        public View createView(Context context) {
            mImageView = new ImageView(context);
            mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return mImageView;
        }
        @Override
        public void UpdateUI(Context context, final int position, Integer integer) {
             mImageView.setImageResource(integer);
        }
    }

    public class NetworkImageHolderView implements Holder<Banner>{

        private ImageView mImageView;

        @Override
        public View createView(Context context) {
            mImageView = new ImageView(context);
            mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return mImageView;
        }
        @Override
        public void UpdateUI(Context context, int position, Banner data) {
            Glide.with(context).load(data.getImageUrl()).placeholder(R.mipmap.default_no_image).into(mImageView);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
