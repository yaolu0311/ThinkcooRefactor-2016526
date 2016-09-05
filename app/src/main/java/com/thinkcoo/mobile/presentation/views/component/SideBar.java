package com.thinkcoo.mobile.presentation.views.component;

import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.thinkcoo.mobile.R;

/**
 *
 * 右侧触摸列view
 */
public class SideBar extends View {
    // 触摸事件
    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
    // 26个字母
    // public String[] b = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
    // "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
    // "W", "X", "Y", "Z", "#" };
    private int choose = -1; // 选中
    private Paint paint = new Paint();

    private TextView mTextDialog;
    private Context context;
    private List<String> listString;

    public void setTextView(TextView mTextDialog, List<String> listString) {
        this.mTextDialog = mTextDialog;
        this.listString = listString;
    }

    public SideBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    public SideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public SideBar(Context context) {
        super(context);
        this.context = context;
    }

    // 起始位置
    private int startPos;

    /**
     * 重写这个方法
     */
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 获取焦点改变背景颜色.
        int height = getHeight();// 获取对应高度
        int width = getWidth(); // 获取对应宽度
        int singleHeight = height / 29;// 获取每一个字母的高度
        if (listString == null ||(listString!=null
                && listString.size() ==0)) {
            return;
        }
        Collections.sort(listString);
        int pos = 29 - listString.size();
        startPos = pos / 2 + 1;
        float yPos = startPos * singleHeight;
        for (int i = 0; i < listString.size(); i++) {
            paint.setColor(Color.GRAY);
            paint.setTypeface(Typeface.DEFAULT);
            paint.setAntiAlias(true);
            paint.setTextSize(dip2px(context, 10));
            // 选中的状态
            if (i == choose) {
//				paint.setColor(Color.parseColor("#ffffff"));
                paint.setFakeBoldText(true);
            }
            // x坐标等于中间-字符串宽度的一半.
            float xPos = width / 2 - paint.measureText(listString.get(i)) / 2;
            canvas.drawText(listString.get(i), xPos, yPos, paint);
            yPos += singleHeight;
            paint.reset();// 重置画笔
        }
    }

    private float dip2px(Context context, int dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();// 点击y坐标
        final int oldChoose = choose;
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        final int c = (int) (y / getHeight() * 30);// 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数.
        if (listString==null ||(listString!=null && listString.size()==0)) {
            return true;
        }
        switch (action) {
            case MotionEvent.ACTION_UP:// 抬起
                setBackgroundDrawable(new ColorDrawable(0x00000000));
                choose = -1;//
                invalidate();
                if (mTextDialog != null) {
                    mTextDialog.setVisibility(View.INVISIBLE);
                }
                break;

            default:
                setBackgroundResource(R.drawable.sidebar_background);
                if (oldChoose != c) {
                    if (c >= 0 && c < 27) {
                        if (c >= startPos && c < (startPos + listString.size())) {
                            if (listener != null) {
                                listener.onTouchingLetterChanged(listString.get(c
                                        - startPos));
                            }
                            if (mTextDialog != null) {

                                mTextDialog.setText(listString.get(c - startPos));
                                mTextDialog.setVisibility(View.VISIBLE);
                            }
                        }

                        choose = c;
                        invalidate();
                    }
                }

                break;
        }
        return true;
    }

    /**
     * 向外公开的方法
     *
     * @param onTouchingLetterChangedListener
     */
    public void setOnTouchingLetterChangedListener(
            OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    /**
     * 接口
     *
     * @author coder
     *
     */
    public interface OnTouchingLetterChangedListener {
        public void onTouchingLetterChanged(String s);
    }

}
