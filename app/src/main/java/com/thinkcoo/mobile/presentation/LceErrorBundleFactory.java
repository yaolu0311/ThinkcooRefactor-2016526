package com.thinkcoo.mobile.presentation;

import android.content.Context;
import android.content.Intent;
import android.widget.TabHost;

import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.model.entity.JobResultEmptyException;
import com.thinkcoo.mobile.model.exception.BuyRecommendGoodsEmptyException;
import com.thinkcoo.mobile.model.exception.EmptyMaualStudentsInClassException;
import com.thinkcoo.mobile.model.exception.EmptyStudentsInClassException;
import com.thinkcoo.mobile.model.exception.MyCollectJobEmptyException;
import com.thinkcoo.mobile.model.exception.NoNetWorkException;
import com.thinkcoo.mobile.model.exception.RequestJobEmptyException;
import com.thinkcoo.mobile.model.exception.SellRecommendGoodsEmptyException;
import com.thinkcoo.mobile.model.exception.user.UserHarvestEmptyException;
import com.thinkcoo.mobile.presentation.views.activitys.CreateClassActivity;

import javax.inject.Inject;

/**
 * Created by Robert.yao on 2016/6/28.
 */
public class LceErrorBundleFactory {

    @Inject
    public LceErrorBundleFactory() {
    }

    public LceErrorBundle create(Throwable throwable, Context context){

        if (throwable instanceof UserHarvestEmptyException){
            return new UserHarvestEmptyLceErrorBundle(context);
        }else if(throwable instanceof NoNetWorkException){
            return new NoNetWorkLceErrorBundle(context);
        }else if (throwable instanceof SellRecommendGoodsEmptyException){
            return new SellRecommendGoodsEmptyLceErrorBundle(context);
        }else if (throwable instanceof BuyRecommendGoodsEmptyException){
            return new BuyRecommendGoodsEmptyLceErrorBundle(context);
        }else if (throwable instanceof EmptyStudentsInClassException){
            return new EmptyStudentsInClassErrorBundle(context);
        }else if(throwable instanceof EmptyMaualStudentsInClassException){
            return new EmptyMaualStudentsInClassExceptionBundle(context);
        }else if (throwable instanceof JobResultEmptyException){
            return new JobResultEmptyExceptionBundle(context);
        }else if (throwable instanceof RequestJobEmptyException){
            return new RequestJobEmptyExceptionBundle(context);
        }else if (throwable instanceof MyCollectJobEmptyException){
            return new MyCollectJobEmptyExceptionBundle(context);
        }
        return new DefaultLceErrorBundle(context);
    }

    private abstract class HolderContextLceErrorBundle implements LceErrorBundle{

        public HolderContextLceErrorBundle(Context context) {
            this.context = context;
        }

        private Context context;

        public Context getContext() {
            return context;
        }
    }

    /**
     * 未知异常引发的默认lce操作
     */
    private class DefaultLceErrorBundle extends HolderContextLceErrorBundle{

        public DefaultLceErrorBundle(Context context) {
            super(context);
        }

        @Override
        public int getErrorMsg() {
            return R.string.load_data_failure;
        }

        @Override
        public int getErrorImg() {
            return 0;
        }

        @Override
        public ErrorProcessIntent getErrorProcessIntent() {
            return null;
        }
    }

    /**
     * 用户收获为空时lce操作
     */
    private class UserHarvestEmptyLceErrorBundle extends HolderContextLceErrorBundle{

        public UserHarvestEmptyLceErrorBundle(Context context) {
            super(context);
        }

        @Override
        public int getErrorMsg() {
            return R.string.user_harvest_empty;
        }

        @Override
        public int getErrorImg() {
            return 0;
        }

        @Override
        public ErrorProcessIntent getErrorProcessIntent() {
            return null;
        }
    }

    /**
     * 无网络异常引发的lce操作
     */
    private class NoNetWorkLceErrorBundle extends HolderContextLceErrorBundle {

        public NoNetWorkLceErrorBundle(Context context) {
            super(context);
        }

        @Override
        public int getErrorMsg() {
            return R.string.net_no_click_setup_net;
        }

        @Override
        public int getErrorImg() {
            return 0;
        }

        @Override
        public ErrorProcessIntent getErrorProcessIntent() {
            return null;
        }
    }


    private class SellRecommendGoodsEmptyLceErrorBundle extends HolderContextLceErrorBundle {

        public SellRecommendGoodsEmptyLceErrorBundle(Context context) {
            super(context);
        }

        @Override
        public int getErrorMsg() {
            return R.string.sell_recommend_goods_empty;
        }

        @Override
        public int getErrorImg() {
            return R.mipmap.empty;
        }

        @Override
        public ErrorProcessIntent getErrorProcessIntent() {
            return null;
        }
    }

    private class BuyRecommendGoodsEmptyLceErrorBundle extends HolderContextLceErrorBundle {

        public BuyRecommendGoodsEmptyLceErrorBundle(Context context) {
            super(context);
        }

        @Override
        public int getErrorMsg() {
            return R.string.sell_recommend_goods_empty;
        }

        @Override
        public int getErrorImg() {
            return R.mipmap.empty;
        }

        @Override
        public ErrorProcessIntent getErrorProcessIntent() {
            return null;
        }
    }

    private class EmptyMaualStudentsInClassExceptionBundle extends HolderContextLceErrorBundle {

        public EmptyMaualStudentsInClassExceptionBundle(Context context) {
            super(context);
        }

        @Override
        public int getErrorMsg() {
            return R.string.load_data_failure;
        }

        @Override
        public int getErrorImg() {
            return 0;
        }

        @Override
        public ErrorProcessIntent getErrorProcessIntent() {
          return null;
        }
    }
    private class EmptyStudentsInClassErrorBundle extends HolderContextLceErrorBundle {

        public EmptyStudentsInClassErrorBundle(Context context) {
            super(context);
        }

        @Override
        public int getErrorMsg() {
            return R.string.empty_chat;
        }

        @Override
        public int getErrorImg() {
            return R.mipmap.sorry;
        }

        @Override
        public ErrorProcessIntent getErrorProcessIntent() {
            Intent intent = new Intent();
            intent.setAction(CreateClassActivity.ACTION_CREATE_CLASS);
            return getBrocastErrorProcessIntent(intent);
        }
    }
    private ErrorProcessIntent getBrocastErrorProcessIntent(Intent intent){
        return new ErrorProcessIntent(ErrorProcessIntent.INTENT_TYPE_SEND_BROCAST,intent);
    }

    private class JobResultEmptyExceptionBundle extends HolderContextLceErrorBundle {

        public JobResultEmptyExceptionBundle(Context context) {
            super(context);
        }

        @Override
        public int getErrorMsg() {
            return R.string.job_result_empty;
        }

        @Override
        public int getErrorImg() {
            return 0;
        }

        @Override
        public ErrorProcessIntent getErrorProcessIntent() {
            return null;
        }
    }

    private class RequestJobEmptyExceptionBundle extends HolderContextLceErrorBundle {

        public RequestJobEmptyExceptionBundle(Context context) {
            super(context);
        }

        @Override
        public int getErrorMsg() {
            return R.string.request_job_empty;
        }

        @Override
        public int getErrorImg() {
            return 0;
        }

        @Override
        public ErrorProcessIntent getErrorProcessIntent() {
            return null;
        }
    }

    private class MyCollectJobEmptyExceptionBundle extends HolderContextLceErrorBundle {

        public MyCollectJobEmptyExceptionBundle(Context context) {
            super(context);
        }

        @Override
        public int getErrorMsg() {
            return R.string.my_collect_job_empty;
        }

        @Override
        public int getErrorImg() {
            return 0;
        }

        @Override
        public ErrorProcessIntent getErrorProcessIntent() {
            return null;
        }
    }
}
