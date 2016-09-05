package com.thinkcoo.mobile.presentation.views.activitys;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.publicmodule.model.rest.ApiFactoryImpl;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.ry.upgrade.UpgradeConfig;
import com.ry.upgrade.UpgradeInterface;
import com.ry.upgrade.listener.UpgradeListener;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.model.entity.AboutShardBean;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseSimpleActivity;
import com.thinkcoo.mobile.presentation.views.adapter.User.AboutShardAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by Robert.yao on 2016/8/8.
 */
public class AboutActivity extends BaseSimpleActivity {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_other)
    TextView tvOther;
    @Bind(R.id.iv_more)
    ImageView ivMore;
//    @Bind(R.id.title_layout)
//    RelativeLayout titleLayout;
    @Bind(R.id.ac_about_iage)
    ImageView acAboutIage;
    @Bind(R.id.ac_about_versions)
    TextView acAboutVersions;
    @Bind(R.id.ac_personal_about_Share)
    RelativeLayout acPersonalAboutShare;
    @Bind(R.id.view_personal_invite)
    View viewPersonalInvite;
    @Bind(R.id.ac_layout_personal_invite)
    RelativeLayout acLayoutPersonalInvite;
    @Bind(R.id.view_personal_aboutone)
    View viewPersonalAboutone;
    @Bind(R.id.ac_personal_about_opinion)
    RelativeLayout acPersonalAboutOpinion;
    @Bind(R.id.view_personal_abouttwo)
    View viewPersonalAbouttwo;
    @Bind(R.id.ac_personal_about_upload)
    RelativeLayout acPersonalAboutUpload;
    @Bind(R.id.view_personal_aboutthree)
    View viewPersonalAboutthree;
    @Bind(R.id.ac_personal_about_my)
    RelativeLayout acPersonalAboutMy;
    @Bind(R.id.view_personal_aboutl)
    View viewPersonalAboutl;
    public static  final String CHECK_APK_UPDATE = "yingzi-entry-mobile/queryappapkupdate.json";
    public static  final String URL_PROTOCAL = "http://www.thinkcoo.com/yingzi-schedule-web/aboutus.html";
    private static final String TAG = "AboutActivity";

    public static Intent getJumpIntent(Context context) {
        Intent intent = new Intent(context,AboutActivity.class);

        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_about_index);
        ButterKnife.bind(this);
        ShareSDK.initSDK(this);
        setupViews();

    }

    private void setupViews() {
        tvTitle.setText("关于");
        acAboutVersions.setText("思扣" + " "
                +getAppVersionName(this));
    }

    @OnClick({R.id.iv_back, R.id.ac_personal_about_Share, R.id.ac_layout_personal_invite, R.id.ac_personal_about_opinion, R.id.ac_personal_about_upload, R.id.ac_personal_about_my})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ac_personal_about_Share:
                aboutshard();
                break;
            case R.id.ac_layout_personal_invite:
                //mNavigator.navigateToCaptureActivity(this);
                break;
            case R.id.ac_personal_about_opinion:
                mNavigator.navigateToFeedBackActivity(this);
                break;
            case R.id.ac_personal_about_upload:
                doCheckUpdate();
                break;
            case R.id.ac_personal_about_my:
                mNavigator.navigateToWebView(this,URL_PROTOCAL,"关于我们");
                break;
        }
    }



    private void doCheckUpdate() {

        String url = ApiFactoryImpl.LOGINPREFIX_PUBLIC+CHECK_APK_UPDATE;
        ThinkcooLog.d(TAG,url);
        UpgradeConfig config = new UpgradeConfig.Buidler()
                .setCheckUpdateUrl(url).setDebug(true)
                .setUpgradeListener(new UpgradeListener() {

                    @Override
                    public void onUpgradeResult(int results) {

                        switch (results) {
                            case UpgradeInterface.NO_NEW_VERSION:
                                Toast.makeText(AboutActivity.this,
                                        R.string.no_update, Toast.LENGTH_LONG)
                                        .show();
                                break;
                            case UpgradeInterface.FAILURE_GET_VERSION:
                                Toast.makeText(AboutActivity.this,
                                        R.string.check_update_error,
                                        Toast.LENGTH_LONG).show();
                                break;
                            case UpgradeInterface.HAS_NEW_VERSION:
                                break;
                            case UpgradeInterface.NO_SUPPORT_THIS_VERSION:
                                break;
                        }

                    }
                }).setIsDeltaUpdate(false).build();

        UpgradeInterface.checkUpdate(this, config);

    }

    /**
     * Description: 分享的操作
     */
    private void aboutshard() {
        final Dialog dialog = new Dialog(this, R.style.AlertDialogStyle);
        View view = LayoutInflater.from(this).inflate(
                R.layout.v_dialog_about_share, null);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度
        // 当Window的Attributes改变时系统会调用此函数,可以直接调用以应用上面对窗口参数的更改,也可以用setAttributes
        // dialog.onWindowAttributesChanged(lp);
        dialogWindow.setAttributes(lp);
        dialog.show();
        TextView ac_about_cacel_butt = (TextView) view
                .findViewById(R.id.ac_about_cacel_butt);
        GridView ac_about_gridview_shard = (GridView) view
                .findViewById(R.id.ac_about_gridview_shard);
        final ArrayList<AboutShardBean> mlist = new ArrayList<AboutShardBean>();
        int psth[] = { R.mipmap.weixin, R.mipmap.pengyouquan,
                R.mipmap.qq, R.mipmap.qqkongjina, R.mipmap.xinlang };
       String[]  names = getResources().getStringArray(R.array.share_platform_names);
        for (int i = 0; i < names.length; i++) {
            AboutShardBean bean = new AboutShardBean(psth[i], names[i]);
            mlist.add(bean);
        }
        AboutShardAdapter madapter = new AboutShardAdapter(this, mlist);
        ac_about_gridview_shard.setAdapter(madapter);
        ac_about_gridview_shard
                .setSelector(new ColorDrawable(Color.TRANSPARENT));
        ac_about_cacel_butt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });

        // gridview的点击事件
        ac_about_gridview_shard
                .setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1,
                                            int arg2, long arg3) {
                        // TODO Auto-generated method stub
                        String getNmae = mlist.get(arg2).getImageName();
                        switch (getNmae) {
                            case "微信好友":
                                // instanceShare.showShare(SharePlatform.SHARE_WECHAT);
                                share(1);
                                break;
                            case "微信朋友圈":
                                // instanceShare.showShare(SharePlatform.SHARE_WECHATMOMENTS);
                                share(2);
                                break;
                            case "QQ好友":
                                // instanceShare.showShare(SharePlatform.SHARE_QQ);
                                share(3);
                                break;
                            case "QQ空间":
                                // instanceShare.showShare(SharePlatform.SHARE_QZONE);
                                share(4);
                                break;
                            case "新浪微博":
                                // instanceShare.showShare(SharePlatform.SHARE_WEIBO);
                                share(0);
                                break;

                        }
                    }
                });
    }

    /**
     *
     * Description: 每个平台分享代码
     *
     * @param key
     */

    public void share(int key) {
        ShareParams sp = new ShareParams();
        switch (key) {
            // 新浪微博
            case 0:
                // 新浪格式 :文本 + 空格 + url
                sp.setText(getString(R.string.share_text)+getString(R.string.share_url));
                sp.setImageUrl(getString(R.string.share_image_url));
                Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
                weibo.setPlatformActionListener(new PlatformActionListener() {

                    @Override
                    public void onError(Platform arg0, int arg1, Throwable arg2) {
                        handler.sendEmptyMessage(arg1);
                    }

                    @Override
                    public void onComplete(Platform arg0, int arg1,
                                           HashMap<String, Object> arg2) {
                        handler.sendEmptyMessage(1);

                    }

                    @Override
                    public void onCancel(Platform arg0, int arg1) {

                    }
                }); // 设置分享事件回调
                // 执行图文分享
                weibo.share(sp);
                break;
            // 微信好友
            case 1:
                sp.setShareType(Platform.SHARE_WEBPAGE);
                sp.setTitle(getString(R.string.share_title));
                sp.setText(getString(R.string.share_text));
                sp.setImageUrl(getString(R.string.share_image_url));
                sp.setUrl(getString(R.string.share_url));
                Platform weChat = ShareSDK.getPlatform(Wechat.NAME);
                weChat.setPlatformActionListener(new PlatformActionListener() {

                    @Override
                    public void onError(Platform arg0, int arg1, Throwable arg2) {
                        handler.sendEmptyMessage(arg1);
                    }

                    @Override
                    public void onComplete(Platform arg0, int arg1,
                                           HashMap<String, Object> arg2) {
                        handler.sendEmptyMessage(1);
                    }

                    @Override
                    public void onCancel(Platform arg0, int arg1) {

                    }
                }); // 设置分享事件回调
                // 执行图文分享
                weChat.share(sp);
                break;
            // 微信朋友圏
            case 2:
                sp.setShareType(Platform.SHARE_WEBPAGE);
                sp.setTitle(getString(R.string.share_title));
                sp.setText(getString(R.string.share_text));
                sp.setImageUrl(getString(R.string.share_image_url));
                sp.setUrl(getString(R.string.share_url));
                Platform weChatMoment = ShareSDK.getPlatform(WechatMoments.NAME);
                weChatMoment
                        .setPlatformActionListener(new PlatformActionListener() {

                            @Override
                            public void onError(Platform arg0, int arg1,
                                                Throwable arg2) {
                                handler.sendEmptyMessage(arg1);
                            }

                            @Override
                            public void onComplete(Platform arg0, int arg1,
                                                   HashMap<String, Object> arg2) {
                                handler.sendEmptyMessage(1);
                            }

                            @Override
                            public void onCancel(Platform arg0, int arg1) {

                            }
                        }); // 设置分享事件回调
                // 执行图文分享
                weChatMoment.share(sp);
                break;
            // QQ好友
            case 3:
                sp.setTitle(getString(R.string.share_title));
                sp.setTitleUrl(getString(R.string.share_url));
                sp.setText(getString(R.string.share_text));
                sp.setImageUrl(getString(R.string.share_image_url));
                Platform qq = ShareSDK.getPlatform(QQ.NAME);
                qq.setPlatformActionListener(new PlatformActionListener() {

                    @Override
                    public void onError(Platform arg0, int arg1, Throwable arg2) {
                        handler.sendEmptyMessage(arg1);
                    }

                    @Override
                    public void onComplete(Platform arg0, int arg1,
                                           HashMap<String, Object> arg2) {
                        handler.sendEmptyMessage(1);
                    }

                    @Override
                    public void onCancel(Platform arg0, int arg1) {

                    }
                }); // 设置分享事件回调
                // 执行图文分享
                qq.share(sp);
                break;
            // QQ空间
            case 4:
                sp.setTitle(getString(R.string.share_title));
                sp.setTitleUrl(getString(R.string.share_url)); // 标题的超链接
                sp.setText(getString(R.string.share_text));
                sp.setImageUrl(getString(R.string.share_image_url));
                sp.setSite(getString(R.string.app_name));
                sp.setSiteUrl("http://www.yingzitech.com");// 发布分享网站的地址

                Platform qzone = ShareSDK.getPlatform(QZone.NAME);
                qzone.setPlatformActionListener(new PlatformActionListener() {

                    @Override
                    public void onError(Platform arg0, int arg1, Throwable arg2) {
                        handler.sendEmptyMessage(arg1);
                    }

                    @Override
                    public void onComplete(Platform arg0, int arg1,
                                           HashMap<String, Object> arg2) {
                        handler.sendEmptyMessage(1);
                    }

                    @Override
                    public void onCancel(Platform arg0, int arg1) {

                    }
                }); // 设置分享事件回调
                // 执行图文分享
                qzone.share(sp);
                break;

        }
    }
    /**
     * 获取当前应用程序版本号
     *
     * @param context
     * @return eg. 1.0.1.1
     */
    public String getAppVersionName(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = info.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            ThinkcooLog.e("VersionInfo",e.getMessage());
        }
        return versionName;
    }
    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 1)// 成功
                Toast.makeText(AboutActivity.this,
                        R.string.share_success, Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(AboutActivity.this,
                        R.string.share_failure, Toast.LENGTH_SHORT).show();
        };

    };
}
