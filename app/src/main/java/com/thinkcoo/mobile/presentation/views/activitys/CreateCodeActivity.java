package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.DaggerUserComponent;
import com.thinkcoo.mobile.injector.components.DaggerUserComponent;
import com.thinkcoo.mobile.injector.modules.UserModule;
import com.thinkcoo.mobile.model.entity.UserBasicInfo;
import com.thinkcoo.mobile.presentation.mvp.presenters.UserMainPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.UserMainView;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivity;
import com.thinkcoo.mobile.utils.PhotoUriUtils;
import com.yz.im.IMHelper;

import java.io.UnsupportedEncodingException;

import javax.inject.Inject;


/**
 * <p>
 * Title: TimeTableCodeActivity.java
 * </p>
 * <p>
 * 二维码生成页面
 * </p>
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * <p>
 * Company: 西安影子
 * </p>
 * <p>
 * CreateTime: 2015-10-13 下午3:30:16
 * </p>
 * 
 * @author 生成课表二维码的页面
 * @version V1.0
 * @since JDK1.7
 * @CheckItem 自己填写
 */
public class CreateCodeActivity extends BaseActivity implements OnClickListener ,UserMainView{

	private TextView tv_titleName;// title
	private ImageView img_back, image_zx_creat_code;
	private RelativeLayout ac_time_code_save,ac_time_code_send;

	private Bitmap bmp = null;// 生成的二维码内容
	private String flageActivity;//页面标识符
	private TextView ac_schedule_promptone,ac_schedule_prompttwo;

	@Inject
	UserMainPresenter mUserMainPresenter;
//	private DbUtils mDbUtils;

	// 图片宽度的一般
//	private static final int IMAGE_HALFWIDTH = 20;
	// 插入到二维码里面的图片对象
//	private Bitmap mBitmap;
	// 需要插图图片的大小 这里设定为40*40
//	int[] pixels = new int[2 * IMAGE_HALFWIDTH * 2 * IMAGE_HALFWIDTH];

	/**
	 * Description: 自己填写
	 * 
	 * @param savedInstanceState
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_timetable_code);
		flageActivity=getIntent().getStringExtra("flageActivity");
//		mDbUtils=DB.getDbUtils(DB.FLAG_USER_DB);
		initview();
		mUserMainPresenter.getUserBasicInfo(false);
	}

	@Override
	protected MvpPresenter generatePresenter() {
		return mUserMainPresenter;
	}

	@Override
	protected void initDaggerInject() {
		DaggerUserComponent.builder().applicationComponent(getApplicationComponent()).activityModule(getActivityModule()).userModule(new UserModule()).build().inject(this);
	}


	/**
	 * Description: 初始化
	 */
	private void initview() {
		// TODO Auto-generated method stub

		tv_titleName = (TextView) findViewById(R.id.tv_titleName);
		

		img_back = (ImageView) findViewById(R.id.img_back);
		image_zx_creat_code = (ImageView) findViewById(R.id.image_zx_creat_code);// 生成的二维码
		ac_time_code_save = (RelativeLayout) findViewById(R.id.ac_time_code_save);
		ac_time_code_send = (RelativeLayout) findViewById(R.id.ac_time_code_send);
		ac_schedule_prompttwo=(TextView) findViewById(R.id.ac_schedule_prompttwo);
		ac_schedule_promptone=(TextView) findViewById(R.id.ac_schedule_promptone);


			//邀请好友
			ac_schedule_prompttwo.setText(getResources().getString(R.string.soft_yaoqing_friends));
			ac_schedule_promptone.setVisibility(View.INVISIBLE);
//			tv_titleName.setText(getString(R.string.soft_my_code));

		
//		img_back.setOnClickListener(this);
		image_zx_creat_code.setOnClickListener(this);
		ac_time_code_save.setOnClickListener(this);
		ac_time_code_send.setOnClickListener(this);

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
		case R.id.img_back: // 返回事件

			break;
		case R.id.ac_time_code_save:// 保存二维码
			PhotoUriUtils.saveImageToGallery(this,bmp);
			Toast.makeText(CreateCodeActivity.this, "二维码保存成功", Toast.LENGTH_SHORT).show();
			break;
		case R.id.ac_time_code_send:// 发送二维码
			String imagePath = PhotoUriUtils.saveImageToGallery(this,bmp);
			IMHelper.getInstance().getNavigator().navigateToFriendTransferZxingActivity(this, imagePath);

			break;
		}
	}



	public void creatCode(String userId) {


		try {
			userId = new String(userId.getBytes("UTF-8"), "ISO-8859-1");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		// Bitmap bmp = null;
		try {
			if (userId != null && !"".equals(userId)) {// 对文本框的内容进行判空
				bmp = cretaeBitmap(userId);
			}
		} catch (WriterException e) {
			e.printStackTrace();
		}
		if (bmp != null) {
			image_zx_creat_code.setImageBitmap(bmp);

		}
	}

	/**
	 * 生成二维码图片
	 * 
	 * @param
	 * @return Bitmap
	 * @throws WriterException
	 */
	public Bitmap cretaeBitmap(String str) throws WriterException {
		// 生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
		BitMatrix matrix = new MultiFormatWriter().encode(str,
				BarcodeFormat.QR_CODE, 450, 450);
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		// 二维矩阵转为一维像素数组,也就是一直横着排了
//		int halfW = width / 2;
//		int halfH = height / 2;
		int[] pixels = new int[width * height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
//				if (x > halfW - IMAGE_HALFWIDTH && x < halfW + IMAGE_HALFWIDTH
//						&& y > halfH - IMAGE_HALFWIDTH
//						&& y < halfH + IMAGE_HALFWIDTH) {
//					pixels[y * width + x] = mBitmap.getPixel(x - halfW
//							+ IMAGE_HALFWIDTH, y - halfH + IMAGE_HALFWIDTH);
//				} else {
					if (matrix.get(x, y)) {// 有内容就画成黑色
						pixels[y * width + x] = 0xff000000;
					} else {// 没有内容就画成白色，防止保存图片变成全黑
						pixels[y * width + x] = 0xffffffff;
					}
				}
//			}
		}
		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		// 通过像素数组生成bitmap
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);

		return bitmap;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();


	}
	public static Intent getJumpIntent(Context context) {
		Intent intent = new Intent(context,CreateCodeActivity.class);

		return intent;
	}

	@Override
	public void setUserMainInfo(UserBasicInfo userBasicInfo) {
		creatCode(userBasicInfo.getUserId());// 生成二维码
	}

	@Override
	public Context getActivityContext() {
		return this;
	}

	@Override
	public void closeSelf() {
		finish();
	}

	@Override
	public void showToast(String errorMsg) {
		mGlobalToast.showShortToast(errorMsg);
	}
}