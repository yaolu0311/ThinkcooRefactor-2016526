package com.thinkcoo.mobile.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.ActivityScope;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

/**
 * Created by Administrator on 2016/5/25.
 */
public class PublicUIUtil {


    private static final String TAG = "PublicUIUtil";

    /**
     * 自贸区中的类别
     */
    public final static String[] goodsTypes = new String[] { "图书/音像", "文体户外",
            "生活用品", "小家电", "电脑/配件", "数码产品", "其他", "附近" };

    @Inject
    public PublicUIUtil() {
    }

    /**
     * 设置图片大小
     */
    public RelativeLayout.LayoutParams setLayout(Context context) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                R.mipmap.slidebutton_bg);
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();// 获取图片宽高
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width,
                height);
        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);// 设置居父窗体右边
        lp.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);// 竖直居中
        return lp;
    }

    public String makePriceString(String goodsPrice){
        if (goodsPrice != null && !TextUtils.isEmpty(goodsPrice) ) {
            try{
                DecimalFormat myFormatter = new DecimalFormat(
                        "###,###.##");
                String output = myFormatter.format(Double
                        .parseDouble(goodsPrice));
                return output;
            }catch(Exception e){
                return "0";
            }
        }
        return "0";
    }


    public String getGoodsDisplayPrice(String price){
        return "¥" + makePriceString(price) + "元";
    }

    public String getCategoryDisplayString(String categoryNumberString){
        try{
            int offset = Integer.parseInt(categoryNumberString);
            return goodsTypes[offset-1];
        }catch (Exception e){
            ThinkcooLog.e(TAG,e.getMessage(),e);
        }
        return goodsTypes[0];
    }

    /**
     * * TextView 画圆角背景
     * @param w 背景宽
     * @param h 背景高
     * @param v 操作textview
     * @param mBgColor 背景颜色
     * @param radian 圆角弧度(选填,单位px)
     */
    public static void setBackgroundRounded(Context context, int w, int h, View v, int mBgColor, int radian)
    {
        if(radian==0){
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            double dH = (metrics.heightPixels / 100) * 1.5;
            radian = (int)dH;
        }
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);
        paint.setAntiAlias(true);
        paint.setColor(mBgColor);
        RectF rec = new RectF(0, 0, w, h);
        c.drawRoundRect(rec, radian, radian, paint);
        v.setBackgroundDrawable(new BitmapDrawable(context.getResources(), bmp));
    }




    public String getGoodsFormatTime(String publishTime){
        try {
            SimpleDateFormat format = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
            long time = format.parse(publishTime).getTime();
            String times = formatDisplayTimeTrade(time);
            return times;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     *
     * Description: 自贸区列表时间显示规范
     *
     * @param time
     * @return
     */
    private String formatDisplayTimeTrade(long time) {
        String display = "";
        SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
        String times = formate.format(time);// 把要比较的时间转化为yyyy/MM/dd的格式
        // 获取当前的系统时间
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String systemtime = format.format(date);

        String arrysystemtime[] = systemtime.split("-");
        String arraytimes[] = times.split("-");

        for (int i = 0; i < arraytimes.length; i++) {

            if (arrysystemtime[0].equals(arraytimes[0])) {
                // 年相等
                if (arrysystemtime[1].equals(arraytimes[1])) {
                    // 月相等
                    if (arrysystemtime[2].equals(arraytimes[2])) {
                        // 天相等,今天时间用例如格式为“17:17” ，采用24小时制
                        DateFormat todayformat = new SimpleDateFormat(
                                "HH:mm");
                        display = todayformat.format(time);
                    } else {
                        // 天不等
                        display = times;
                    }
                } else {
                    // 月不等
                    display = times;
                }
            } else {
                // 年不等
                display = times;
            }
        }
        return display;
    }

}
