package com.example.administrator.publicmodule.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.administrator.publicmodule.R;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;

import java.util.ArrayList;
import java.util.LinkedList;

public class ActionSheetDialog {

    private static final String TAG = "ActionSheetDialog";

    public static final int MAX_SIZE = 15;
    public static final int Min_SIZE = 13;

    private Context context;
    private Dialog dialog;
    private TextView txt_title;
    private TextView txt_cancel;
    private LinearLayout lLayout_content;
    private ScrollView sLayout_content;
    private boolean showTitle = false;
    private ArrayList<SheetItemEntity> sheetItemList;
    private Display display;
    private ToggleButton togglebutton;

    private OnSheetItemClickListener mOnSheetItemClickListener;
    private SheetItemEntity mPreviousClickItemEntity;

    public ActionSheetDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public ActionSheetDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(R.layout.view_actionsheet, null);

        // 设置Dialog最小宽度为屏幕宽度
        view.setMinimumWidth(display.getWidth());

        // 获取自定义Dialog布局中的控件
        sLayout_content = (ScrollView) view.findViewById(R.id.sLayout_content);
        lLayout_content = (LinearLayout) view.findViewById(R.id.lLayout_content);
        txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_cancel = (TextView) view.findViewById(R.id.txt_cancel);
        txt_cancel.setOnClickListener(new OnClickListener() {// 取消事件
            @Override
            public void onClick(View v) {
                if (togglebutton != null) {
                    togglebutton.setChecked(false);
                }
                dialog.dismiss();
            }
        });

        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = 0;
        dialogWindow.setAttributes(lp);

        return this;
    }

    public ActionSheetDialog setTitle(String title) {
        showTitle = true;
        txt_title.setVisibility(View.VISIBLE);
        txt_title.setText(title);
        return this;
    }

    public ActionSheetDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public ActionSheetDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public ActionSheetDialog setToggleButton(ToggleButton togglebutton) {
        this.togglebutton = togglebutton;
        return this;
    }

    /**
     * 添加或更新数据
     *
     * @param itemEntities
     * @param isAppend
     * @return
     */
    public ActionSheetDialog setSheetItems(LinkedList<SheetItemEntity> itemEntities, boolean isAppend) {
        if (null == itemEntities || itemEntities.size() <= 0) {
            return this;
        }
        checkSheetItemListIsNull();
        if (!isAppend) {
            sheetItemList.clear();
        }

        int count = itemEntities.size();
        for (int i = 0; i < count; i++) {
            sheetItemList.add(itemEntities.get(i));
        }
        setSheetItems();
        return this;
    }

    private void checkSheetItemListIsNull() {
        if (sheetItemList == null) {
            sheetItemList = new ArrayList<>();
        }
    }

    public ActionSheetDialog addSheetItemClickListener(OnSheetItemClickListener listener) {
        this.mOnSheetItemClickListener = listener;
        return this;
    }

    public static class SheetItemEntity implements Cloneable {
        private String mItemName;
        private SheetItemColor mItemColor;
        private String code;
        private int textSize;
        private boolean isCanClick = true;

        public SheetItemEntity() {
        }

        public SheetItemEntity(String itemName, String code, SheetItemColor itemColor) {
            mItemName = itemName;
            mItemColor = itemColor;
            this.code = code;
            textSize = MAX_SIZE;
        }

        public String getCode() {
            return getString(code);
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getItemName() {
            return getString(mItemName);
        }

        public void setItemName(String itemName) {
            mItemName = itemName;
        }

        public void setTextSize(int textSize) {
            this.textSize = textSize;
        }

        public int getTextSize() {
            return textSize;
        }

        public SheetItemColor getItemColor() {
            return mItemColor == null ? SheetItemColor.Blue : mItemColor;
        }

        private String getString(String string) {
            if (TextUtils.isEmpty(string)) {
                return "";
            }
            return string;
        }

        public boolean isCanClick() {
            return isCanClick;
        }

        public void setCanClick(boolean canClick) {
            isCanClick = canClick;
        }

        public void setItemColor(SheetItemColor itemColor) {
            mItemColor = itemColor;
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            SheetItemEntity deepCloneEntity = null;
            try {
                deepCloneEntity = (SheetItemEntity) super.clone();
                String name = deepCloneEntity.getItemName();
                deepCloneEntity.setItemName(name);
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return deepCloneEntity;
        }
    }

    /**
     * 设置条目布局
     */
    private void setSheetItems() {
        if (sheetItemList == null || sheetItemList.size() <= 0) {
            return;
        }

        int size = sheetItemList.size();

        // TODO 高度控制，非最佳解决办法
        // 添加条目过多的时候控制高度
        if (size >= 7) {
            LayoutParams params = (LayoutParams) sLayout_content
                    .getLayoutParams();
            params.height = display.getHeight() / 2;
            sLayout_content.setLayoutParams(params);
        }

        // 循环添加条目
        for (int i = 1; i <= size; i++) {
            final SheetItemEntity sheetItem = sheetItemList.get(i - 1);
            SheetItemColor color = sheetItem.getItemColor();

            TextView textView = createTextView(sheetItem);
            setTextViewBackgroundStyle(size, i, textView);
            setTextViewColor(color, textView);
            setTextViewHeight(textView);
            setTextViewClickListener(sheetItem, textView);

            lLayout_content.addView(textView);
        }
    }

    private TextView createTextView(SheetItemEntity sheetItem) {
        TextView textView = new TextView(context);
        textView.setText(sheetItem.getItemName());
        textView.setTextSize(sheetItem.getTextSize());
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    private void setTextViewBackgroundStyle(int size, int i, TextView textView) {
        if (size == 1) {
            if (showTitle) {
                textView.setBackgroundResource(R.drawable.action_sheet_bottom_selector);
            } else {
                textView.setBackgroundResource(R.drawable.action_sheet_single_selector);
            }
        } else {
            if (i == 1) {
                textView.setBackgroundResource(R.drawable.action_sheet_top_selector);
            } else if (i == size) {
                textView.setBackgroundResource(R.drawable.action_sheet_bottom_selector);
            } else {
                textView.setBackgroundResource(R.drawable.action_sheet_middle_selector);
            }
        }
    }

    private void setTextViewColor(SheetItemColor color, TextView textView) {
        // 字体颜色
        if (color == null) {
            textView.setTextColor(Color.parseColor(SheetItemColor.Blue
                    .getName()));
        } else {
            textView.setTextColor(Color.parseColor(color.getName()));
        }
    }

    private void setTextViewHeight(TextView textView) {
        float scale = context.getResources().getDisplayMetrics().density;
        int height = (int) (45 * scale + 0.5f);
        textView.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT, height));
    }

    private void setTextViewClickListener(final SheetItemEntity sheetItem, TextView textView) {
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                callBackOnSheetItemClick(sheetItem);
                try {
                    mPreviousClickItemEntity = (SheetItemEntity) sheetItem.clone();
                } catch (CloneNotSupportedException e) {
                    ThinkcooLog.e(TAG, e.getMessage(), e);
                }
                if (sheetItem.isCanClick()) {
                    dialog.dismiss();
                }
            }
        });
    }

    private void callBackOnSheetItemClick(SheetItemEntity sheetItem) {
        if (null != mOnSheetItemClickListener) {
            mOnSheetItemClickListener.onClick(sheetItem);
        }
    }

    public SheetItemEntity getPreviousClickItemEntity() {
        if (null == mPreviousClickItemEntity) {
            return new SheetItemEntity();
        }
        return mPreviousClickItemEntity;
    }

    public void show() {
        dialog.show();
    }

    public interface OnSheetItemClickListener {
        void onClick(SheetItemEntity sheetItemEntity);
    }

    public enum SheetItemColor {
        Blue("#037BFF"), Red("#FD4A2E"), GREY("#8F8F8F");
        private String name;

        SheetItemColor(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
