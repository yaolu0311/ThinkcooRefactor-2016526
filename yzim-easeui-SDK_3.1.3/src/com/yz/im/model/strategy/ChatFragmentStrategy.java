package com.yz.im.model.strategy;

import android.content.Context;
import android.os.Parcelable;

import com.hyphenate.easeui.widget.EaseChatExtendMenu;
import com.hyphenate.easeui.widget.EaseChatInputMenu;

/**
 * Created by Administrator on 2016/6/30.
 */
public interface ChatFragmentStrategy extends Parcelable{

    void registerExtendMenuItem(EaseChatInputMenu inputMenu, EaseChatExtendMenu.EaseChatExtendMenuItemClickListener clickListener);
    void gotoNextPage(Context context, String id);
    int getRightImage();
}
