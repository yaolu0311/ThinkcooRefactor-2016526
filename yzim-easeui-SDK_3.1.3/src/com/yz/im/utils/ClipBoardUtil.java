package com.yz.im.utils;

import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;

/**
 * Created by cys on 2016/8/13
 */
public class ClipBoardUtil {

    private ClipboardManager mManager;

    public ClipBoardUtil(Context context) {
        mManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
    }

    public void setText(String boardText){
        if (TextUtils.isEmpty(boardText)) {
            return;
        }
        mManager.setText(boardText);
    }

    public String getText(){
        return (String) mManager.getText();
    }
}
