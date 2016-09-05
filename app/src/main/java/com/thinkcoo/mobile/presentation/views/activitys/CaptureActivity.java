package com.thinkcoo.mobile.presentation.views.activitys;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.DaggerUserComponent;
import com.thinkcoo.mobile.injector.modules.UserModule;
import com.thinkcoo.mobile.presentation.mvp.presenters.CaturePresenter;
import com.thinkcoo.mobile.presentation.mvp.views.CaptureView;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivity;
import com.thinkcoo.mobile.presentation.views.activitys.base.Navigator;
import com.thinkcoo.mobile.presentation.views.activitys.zxing.camera.AmbientLightManager;
import com.thinkcoo.mobile.presentation.views.activitys.zxing.camera.CameraManager;
import com.thinkcoo.mobile.presentation.views.activitys.zxing.decoding.CaptureActivityHandler;
import com.thinkcoo.mobile.presentation.views.activitys.zxing.decoding.FinishListener;
import com.thinkcoo.mobile.presentation.views.activitys.zxing.decoding.InactivityTimer;
import com.thinkcoo.mobile.presentation.views.activitys.zxing.decoding.RGBLuminanceSource;
import com.thinkcoo.mobile.presentation.views.activitys.zxing.view.ViewfinderView;
import com.thinkcoo.mobile.utils.TakePhotoUtils;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.inject.Inject;


/**
 * <p>
 * Title: CaptureActivity.java
 * </p>
 * <p>
 * 二维码扫描
 * </p>
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * <p>
 * Company: 西安影子
 * </p>
 * <p>
 * CreateTime: 2015-10-22 下午4:22:01
 * </p>
 *
 * @author 自己填写
 * @version V1.0
 * @CheckItem 自己填写
 * @since JDK1.7
 */
public class CaptureActivity extends BaseActivity implements
        Callback,
        OnClickListener, CaptureView, TakePhotoUtils.TakePhotoListener {

    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;
    private static final long VIBRATE_DURATION = 200L;
    private AmbientLightManager ambientLightManager;
    private boolean isTorchOn = false;// 默认关灯
    // 二维码扫描界面未开启照相权限时弹出的对话框
    // 界面下方的文本信息,开关灯
    private TextView tv_zx_light;
    private String flageActivity;//从哪个页面跳转过来

    private ImageView image_zx_back, img_zx_photo, img_zx_light,
            img_zx_timetable_code;// 返回按钮，相册按钮，开灯按钮，扫描二维码按钮

    private TextView ac_sweep_texttwo, ac_sweep_textone, ac_creat_code;
    @Inject
    CaturePresenter mCaturePresenter;

    /**
     * Called when the activity is first created.
     */
    @Inject
    TakePhotoUtils mTakePhotoUtils;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_scan_qr_code);
        CameraManager.init(getApplication());
        flageActivity = getIntent().getStringExtra("flageActivity");

        img_zx_photo = (ImageView) findViewById(R.id.img_zx_photo);
        img_zx_light = (ImageView) findViewById(R.id.img_zx_light);
        tv_zx_light = (TextView) findViewById(R.id.tv_zx_light);
        img_zx_timetable_code = (ImageView) findViewById(R.id.img_zx_timetable_code);
        image_zx_back = (ImageView) findViewById(R.id.image_zx_back);
        viewfinderView = (ViewfinderView) findViewById(R.id.ac_2weicard_viewfinder_view);
        ac_sweep_texttwo = (TextView) findViewById(R.id.ac_sweep_texttwo);
        ac_sweep_textone = (TextView) findViewById(R.id.ac_sweep_textone);
        ac_creat_code = (TextView) findViewById(R.id.ac_creat_code);


        //邀请好友
        ac_sweep_texttwo.setText(getResources().getString(R.string.soft_saoyisao_yaoqing));
        ac_sweep_textone.setVisibility(viewfinderView.INVISIBLE);
        ac_creat_code.setText(getResources().getString(R.string.soft_my_code));

        img_zx_photo.setOnClickListener(this);
        img_zx_light.setOnClickListener(this);
        img_zx_timetable_code.setOnClickListener(this);
        image_zx_back.setOnClickListener(this);

        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
        ambientLightManager = new AmbientLightManager(this);

        mTakePhotoUtils.init(this, this);
        mTakePhotoUtils.onCreate(savedInstanceState);
    }


    @Override
    protected void onResume() {
        super.onResume();
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.ac_2weicard_preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        ambientLightManager.start(CameraManager.get());
        vibrate = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        ambientLightManager.stop();
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        mTakePhotoUtils.release();
        super.onDestroy();
    }

    @Override
    protected MvpPresenter generatePresenter() {
        return mCaturePresenter;
    }

    @Override
    protected void initDaggerInject() {
        DaggerUserComponent.builder().applicationComponent(getApplicationComponent()).userModule(new UserModule()).activityModule(getActivityModule()).build().inject(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_CAMERA:// 拦截相机键
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            displayFrameworkBugMessageAndExit();
            return;
        } catch (RuntimeException e) {
            displayFrameworkBugMessageAndExit();
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats,
                    characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();

    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final OnCompletionListener beepListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    private void displayFrameworkBugMessageAndExit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.soft_warn));
        builder.setMessage(getResources().getString(
                R.string.soft_captureActivity_camerFailure));
        builder.setPositiveButton(getResources()
                .getString(R.string.cal_confirm), new FinishListener(this));
        builder.setOnCancelListener(new FinishListener(this));
        builder.show();
    }

    /**
     * 扫描结果接收处理
     *
     * @param result
     * @param barcode
     */
    public void handleDecode(Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        mCaturePresenter.inviteFriend(result.getText());
    }


    /**
     * Description: 按钮的点击事件
     *
     * @param v
     * @see OnClickListener#onClick(View)
     */
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.image_zx_back:// 返回
                finish();
                break;
            case R.id.img_zx_photo:// 相册
                mTakePhotoUtils.singleSelectUseSystemMode();
                break;
            case R.id.img_zx_light:// 开关灯控制
                if (isTorchOn) {// 关灯
                    tv_zx_light
                            .setText(getResources().getString(R.string.soft_off));
                    img_zx_light.setImageResource(R.mipmap.guandeng);
                    isTorchOn = false;
                    CameraManager.get().setTorch(false);
                } else {// 开灯
                    tv_zx_light.setText(getResources().getString(R.string.soft_on));
                    img_zx_light.setImageResource(R.mipmap.kaideng);
                    isTorchOn = true;
                    CameraManager.get().setTorch(true);
                }
                break;
            case R.id.img_zx_timetable_code:// 二维码生成
                navigator.navigateToCreateCode(this);
                break;
        }
    }

    /**
     * 解码图片结果
     */
    private Handler mImageHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:// 失败
                    showToast((String) msg.obj);
                    break;
                case 1:// 成功
                    mCaturePresenter.inviteFriend((String) msg.obj);

                    break;
            }
        }

    };

    /**
     * 扫描二维码图片的方法
     *
     * @param path
     * @return
     */
    public Result scannImage(String path) {

        if (TextUtils.isEmpty(path)) {
            return null;
        }
        Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
        hints.put(DecodeHintType.CHARACTER_SET, "UTF8"); // 设置二维码内容的编码

        // 图片设置
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 先获取原大小
        Bitmap scanBitmap = BitmapFactory.decodeFile(path, options);
        options.inJustDecodeBounds = false; // 获取新的大小
        int sampleSize = (int) (options.outHeight / (float) 200);
        if (sampleSize <= 0)
            sampleSize = 1;
        options.inSampleSize = sampleSize;
        // 解码图片
        scanBitmap = BitmapFactory.decodeFile(path, options);
        // 生成rgb资源
        RGBLuminanceSource source = new RGBLuminanceSource(scanBitmap);
        // 把文件打成二进制
        BinaryBitmap binaryBitmap = new BinaryBitmap(
                new HybridBinarizer(source));
        QRCodeReader reader = new QRCodeReader();
        try {
            return reader.decode(binaryBitmap, hints);// 解码返回

        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (ChecksumException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mTakePhotoUtils.onSaveInstanceState(outState);
    }

    public static Intent getJumpIntent(Context context) {
        Intent intent = new Intent(context, CaptureActivity.class);

        return intent;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mTakePhotoUtils.onActivityResult(requestCode,resultCode,data);

    }
    @Override
    public void onSuccess(final List<String> resultList) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Result result = scannImage(resultList.get(0));
                Message m = mImageHandler.obtainMessage();
                if (result != null) {
                    m.what = 1;// 二维码扫描成功
                    m.obj = result.getText();
                } else {
                    m.what = 0;// 没有发现二维码
                    m.obj = getResources()
                            .getString(
                                    R.string.soft_captureActivity_nofind);
                }
                mImageHandler.sendMessage(m);
            }
        }).start();
    }

    @Override
    public void onFailure(String errorMsg) {
        showToast(errorMsg);
    }
}