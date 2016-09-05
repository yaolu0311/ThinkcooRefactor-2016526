package com.thinkcoo.mobile.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.CropOptions;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.ActivityScope;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import cn.finalteam.galleryfinal.widget.GFImageView;


/**
 * Created by Robert.yao on 2016/8/6.
 */
@ActivityScope
public class TakePhotoUtils {

    public static final int IMAGE_VIEW_TAG = 0x00000212;
    private static final String TAG = "TakePhotoUtils";

    private final int REQUEST_CODE_CAMERA = 1000;
    private final int REQUEST_CODE_GALLERY = 1001;
    private final int REQUEST_CODE_CROP = 1002;
    private final int REQUEST_CODE_EDIT = 1003;

    private static ThemeConfig sThemeConfig = null;
    private static ImageLoader sImageLoader = null;
    private static CropOptions sCropOptions = null;



    public interface TakePhotoListener{
        /**
         * 处理成功
         * @param resultList
         */
       void onSuccess(List<String> resultList);

        /**
         * 处理失败或异常
         * @param errorMsg
         */
        void onFailure(String errorMsg);
    }

    @Inject
    public TakePhotoUtils(){}


    TakePhoto mTakePhoto;

    WeakReference<TakePhotoListener> mTakePhotoListenerWeakReference;


    public void init(Context context,TakePhotoListener takePhotoListener){

        ThemeConfig themeConfig = buildTheme(context.getApplicationContext());
        FunctionConfig functionConfig = buildFunctionConfig();
        ImageLoader imageLoader = buildImageLoader();
        CoreConfig coreConfig = createCoreConfig(context,themeConfig,functionConfig,imageLoader);
        GalleryFinal.init(coreConfig);

        mTakePhotoListenerWeakReference = new WeakReference<>(takePhotoListener);
        mTakePhoto = new TakePhotoImpl((Activity) context,new InnerTakePhotoListener());

    }

    public void release(){
        GalleryFinal.init(createNullCoreConfig());
        mTakePhoto = null;
        if (null != mTakePhotoListenerWeakReference){
            mTakePhotoListenerWeakReference.clear();
        }
    }

    private ThemeConfig buildTheme(Context context) {

        if (null == sThemeConfig){
            sThemeConfig = new ThemeConfig.Builder().setTitleBarBgColor(context.getResources()
                    .getColor(R.color.blue_title))
                    .setIconBack(R.mipmap.tittle_back)
                    .setFabNornalColor(R.color.blue_title)
                    .setTitleBarTextColor(context.getResources().getColor(R.color.white)).build();
        }
        return sThemeConfig;
    }

    private CropOptions buildCropOptions(){
        if (null == sCropOptions){
            sCropOptions = new CropOptions.Builder().setAspectX(1).setAspectY(1).setWithOwnCrop(false).create();
        }
        return sCropOptions;
    }

    private FunctionConfig buildFunctionConfig(){

        FunctionConfig functionConfig =  new FunctionConfig.Builder()
                .setEnableCamera(false)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(false)
                .setEnablePreview(false)
                .setForceCrop(false)
        .build();

        return functionConfig;
    }
    private ImageLoader buildImageLoader(){

        if (sImageLoader == null){

            sImageLoader = new ImageLoader(){
                @Override
                public void displayImage(Activity activity, String path, final GFImageView imageView, Drawable defaultDrawable, int width, int height) {
                    Glide.with(activity)
                            .load(path)
                            .error(defaultDrawable)
                            .placeholder(defaultDrawable)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .override(width,height).into(new ImageViewTarget<GlideDrawable>(imageView) {

                        @Override
                        public void setRequest(Request request) {
                            imageView.setTag(R.id.image_view_tag_key,request);
                        }

                        @Override
                        public Request getRequest() {
                            return (Request)imageView.getTag();
                        }

                        @Override
                        protected void setResource(GlideDrawable resource) {
                            imageView.setImageDrawable(resource);
                        }
                    });
                }

                @Override
                public void clearMemoryCache() {

                }
            };
        }
        return sImageLoader;
    }
    private CoreConfig createCoreConfig(Context context,ThemeConfig themeConfig, FunctionConfig functionConfig, ImageLoader imageLoader) {
        return new CoreConfig.Builder(context,imageLoader,themeConfig).setFunctionConfig(functionConfig).build();
    }

    private CoreConfig createNullCoreConfig(){
        return new CoreConfig.Builder(null,buildImageLoader(),new ThemeConfig.Builder().build()).build();
    }
    /**
     * 请求打开相机，拍照完成后裁剪
     */
    public void takePhotoAndCrop(){
        mTakePhoto.onPickFromCaptureWithCrop(new PhotoUriUtils().createUsePhotoUri(),buildCropOptions());
    }
    /**
     * 请求从相册选取单个照片,并裁剪
     */
    public void singleSelectPhotoAndCrop(){
        GalleryFinal.openGallerySingle(REQUEST_CODE_GALLERY, new InnerGalleryFinalListener());
    }

    public void singleSelectUseSystemMode() {
        mTakePhoto.onPickFromGallery();
    }
    /**
     * 请求从相册选取多个照片,并裁剪
     * @param maxSize 最多选择多少个
     */
    public void multiSelectPhotoAndCrop(int maxSize){
        GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY,maxSize,new InnerGalleryFinalListener());
    }

    private class InnerGalleryFinalListener implements GalleryFinal.OnHanlderResultCallback{

        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            List<String> stringList = photoInfo2StringList(resultList);
            callBackSuccess(stringList.toArray(new String[stringList.size()]));
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            callBackSuccess(errorMsg);
        }

    }
    private List<String> photoInfo2StringList(List<PhotoInfo> resultList) {
        List<String> result = new ArrayList<>();
        if (null == resultList ){
            return result;
        }
        for (int i = 0; i < resultList.size(); i++) {
            result.add(resultList.get(i).getPhotoPath());
        }
        return result;
    }

    private class InnerTakePhotoListener implements TakePhoto.TakeResultListener{
        @Override
        public void takeSuccess(String imagePath) {
            callBackSuccess(imagePath);
        }
        @Override
        public void takeFail(String msg) {
            callBackFail(msg);
        }
        @Override
        public void takeCancel() {

        }
    }
    private void callBackFail(String msg) {
        if (takePhotoListenerIsRecycled()){
            return;
        }
        mTakePhotoListenerWeakReference.get().onFailure(msg);
    }
    private void callBackSuccess(String ...imagePaths) {
        if (takePhotoListenerIsRecycled()) {
            return;
        }
        mTakePhotoListenerWeakReference.get().onSuccess(Arrays.asList(imagePaths));
    }
    private boolean takePhotoListenerIsRecycled() {
        if (null == mTakePhotoListenerWeakReference || null == mTakePhotoListenerWeakReference.get()){
            ThinkcooLog.e(TAG,"=== 拍照回调出错，callback对象或已被回收 ===");
            return true;
        }
        return false;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (null != mTakePhoto){
            mTakePhoto.onActivityResult(requestCode,resultCode,data);
        }
    }
    public void onCreate(Bundle savedInstanceState){
        if (null != mTakePhoto){
            mTakePhoto.onCreate(savedInstanceState);
        }
    }
    public void onSaveInstanceState(Bundle savedInstanceState){
        if (null != mTakePhoto){
            mTakePhoto.onSaveInstanceState(savedInstanceState);
        }
    }

}
