/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.thinkcoo.mobile.presentation.views.activitys.zxing.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;

import com.google.zxing.ResultPoint;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.presentation.views.activitys.zxing.camera.CameraManager;

/**
 * This view is overlaid on top of the camera preview. It adds the viewfinder
 * rectangle and partial transparency outside it, as well as the laser scanner
 * animation and result points.
 */
public final class ViewfinderView extends View {

	private static final int[] SCANNER_ALPHA = { 0, 64, 128, 192, 255, 192,
			128, 64 };
	/**
	 * 扫描点的颜色
	 */
	private static final int CURRENT_POINT_OPACITY = Color.GREEN;
	/**
	 * 刷新界面的时间
	 */
	private static final long ANIMATION_DELAY = 100L;

	private final Paint paint;
	private Bitmap resultBitmap;
	private final int maskColor;
	private final int resultColor;
	private final int resultPointColor;
	private int scannerAlpha;
	private Collection<ResultPoint> possibleResultPoints=null;
	private Collection<ResultPoint> lastPossibleResultPoints=null;

	private int i = 0;// 添加的
	private Rect mRect;// 扫描线填充边界
//	private GradientDrawable mDrawable=null;// 采用渐变图作为扫描线
	private Drawable lineDrawable=null;// 采用图片作为扫描线

	// This constructor is used when the class is built from an XML resource.
	public ViewfinderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		Resources resources = getResources();
		maskColor = resources.getColor(R.color.viewfinder_mask);
		resultColor = resources.getColor(R.color.result_view);
		resultPointColor = resources.getColor(R.color.green);
		mRect = new Rect();
		lineDrawable = getResources().getDrawable(R.mipmap.saomiaoxian);
//		mDrawable = new GradientDrawable(
//				GradientDrawable.Orientation.LEFT_RIGHT, new int[] { left,
//						left, center, right, right });
		scannerAlpha = 0;
		possibleResultPoints = new ArrayList<ResultPoint>(5);
	}

	@Override
	public void onDraw(Canvas canvas) {
		Rect frame = CameraManager.get().getFramingRect();
		if (frame == null) {
			return;
		}
		int width = canvas.getWidth();
		int height = canvas.getHeight();

		//画出扫描框外面的阴影部分，共四个部分，扫描框的上面到屏幕上面，扫描框的下面到屏幕下面
		//扫描框的左边面到屏幕左边，扫描框的右边到屏幕右边
		// 画扫描框外部的暗色背景
		// 设置蒙板颜色
		paint.setColor(resultBitmap != null ? resultColor : maskColor);
		// 头部
		canvas.drawRect(0, 0, width, frame.top, paint);
		// 左边
		canvas.drawRect(0, frame.top, frame.left, frame.bottom, paint);
		// 右边
		canvas.drawRect(frame.right, frame.top, width, frame.bottom, paint);
		// 底部
		canvas.drawRect(0, frame.bottom, width, height, paint);

		if (resultBitmap != null) {
			// 在扫描框中画出预览图
			paint.setAlpha(CURRENT_POINT_OPACITY);
			canvas.drawBitmap(resultBitmap, frame.left, frame.top, paint);
		} else {
			// 画出四个角
			paint.setColor(getResources().getColor(R.color.white));
			// 左上角
			canvas.drawRect(frame.left, frame.top, frame.left + 30,
					frame.top + 10, paint);//横
			canvas.drawRect(frame.left, frame.top, frame.left + 10,
					frame.top + 30, paint);//竖
			// 右上角
			canvas.drawRect(frame.right - 30, frame.top, frame.right,
					frame.top + 10, paint);//横
			canvas.drawRect(frame.right - 10, frame.top, frame.right,
					frame.top + 30, paint);//竖
			// 左下角
			canvas.drawRect(frame.left, frame.bottom - 10, frame.left + 30,
					frame.bottom, paint);//横
			canvas.drawRect(frame.left, frame.bottom - 30, frame.left + 10,
					frame.bottom, paint);//竖
			// 右下角
			canvas.drawRect(frame.right - 30, frame.bottom - 10, frame.right,
					frame.bottom, paint);//横
			canvas.drawRect(frame.right - 10, frame.bottom - 30, frame.right,
					frame.bottom, paint);//竖

			// 设置扫描点
			paint.setColor(getResources().getColor(R.color.green));
			// 设置绿色线条的透明值
			paint.setAlpha(SCANNER_ALPHA[scannerAlpha]);
			// 透明度变化
			scannerAlpha = (scannerAlpha + 1) % SCANNER_ALPHA.length;

			// 画出固定在中部的线条
			// int middle = frame.height() / 2 + frame.top;
			// canvas.drawRect(frame.left + 2, middle - 1, frame.right - 1,
			// middle + 2, paint);
			// 将扫描线修改为上下走的线
			if ((i += 3) < frame.bottom - frame.top) {
				/* 以下为用渐变线条作为扫描线 */
				// 渐变图为矩形
				// mDrawable.setShape(GradientDrawable.RECTANGLE);
				// 渐变图为线型
				// mDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
				// 线型矩形的四个圆角半径
				// mDrawable
				// .setCornerRadii(new float[] { 8, 8, 8, 8, 8, 8, 8, 8 });
				// 位置边界
				// mRect.set(frame.left + 10, frame.top + i, frame.right - 10,
				// frame.top + 1 + i);
				// 设置渐变图填充边界
				// mDrawable.setBounds(mRect);
				// 画出渐变线条
				// mDrawable.draw(canvas);
				/* 以下为图片作为扫描线 */
				mRect.set(frame.left - 6, frame.top + i - 6, frame.right + 6,
						frame.top + 6 + i);
				lineDrawable.setBounds(mRect);
				lineDrawable.draw(canvas);
				// 刷新
				invalidate();
			} else {
				i = 0;
			}
			
			Collection<ResultPoint> currentPossible = possibleResultPoints;
			Collection<ResultPoint> currentLast = lastPossibleResultPoints;
			if (currentPossible.isEmpty()) {
				lastPossibleResultPoints = null;
			} else {
				possibleResultPoints = new HashSet<ResultPoint>(5);
				lastPossibleResultPoints = currentPossible;
				paint.setAlpha(CURRENT_POINT_OPACITY);
				paint.setColor(resultPointColor);
				for (ResultPoint point : currentPossible) {
					canvas.drawCircle(frame.left + point.getX(), frame.top
							+ point.getY(), 6.0f, paint);
				}
			}
			if (currentLast != null) {
				paint.setAlpha(CURRENT_POINT_OPACITY / 2);
				paint.setColor(resultPointColor);
				for (ResultPoint point : currentLast) {
					canvas.drawCircle(frame.left + point.getX(), frame.top
							+ point.getY(), 3.0f, paint);
				}
			}

			postInvalidateDelayed(ANIMATION_DELAY, frame.left, frame.top,
					frame.right, frame.bottom);
		}
	}

	public void drawViewfinder() {
		resultBitmap = null;
		invalidate();
	}

	/**
	 * Draw a bitmap with the result points highlighted instead of the live
	 * scanning display.
	 * 
	 * @param barcode
	 *            An image of the decoded barcode.
	 */
	public void drawResultBitmap(Bitmap barcode) {
		resultBitmap = barcode;
		invalidate();
	}

	public void addPossibleResultPoint(ResultPoint point) {
		possibleResultPoints.add(point);
	}

}
