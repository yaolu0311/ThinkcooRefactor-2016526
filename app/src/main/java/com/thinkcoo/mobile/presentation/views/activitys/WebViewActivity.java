package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseSimpleActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebViewActivity extends BaseSimpleActivity implements OnClickListener {


    private static final String EXTRA_IMAGELINKURL_KEY ="extra_imagelinkurl_key" ;
    private static final String EXTRA_TITLE_KEY ="extra_title_key";

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.webView)
    WebView webView;
    private String linkUrl;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_home_webview);
        ButterKnife.bind(this);
        linkUrl = getIntent().getExtras().getString(EXTRA_IMAGELINKURL_KEY);
        title = getIntent().getExtras().getString(EXTRA_TITLE_KEY);
        setupViews();
    }

    /**
     * Description: 自己填写
     */
    private void setupViews() {

        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBlockNetworkImage(false);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);//隐藏Zoom缩放按钮
        // //设置webview为单列显示，是一些大图片适应屏幕宽度
        webView.getSettings()
                .setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        webView.loadUrl(linkUrl);

    }

    /**
     * Description: 自己填写
     *
     * @param v
     * @see OnClickListener#onClick(View)
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
               finish();
                break;
        }
    }


    /**
     * 拦截返回键
     *
     * @param
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



    public static Intent getwebviewIntent(Context context, String imageLinkUrl, String title) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(WebViewActivity.EXTRA_IMAGELINKURL_KEY, imageLinkUrl);
        intent.putExtra(WebViewActivity.EXTRA_TITLE_KEY,title );
        return intent;
    }
}
