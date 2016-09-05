package com.yz.im.mvp.mvpContract;

import com.yz.im.mvp.IMBasePresenter;
import com.yz.im.mvp.IMMvpView;

/**
 * Created by cys on 2016/7/25
 */
public interface SingleEditTextContract {

    interface SingleEditTextView extends IMMvpView{
        String getOldContent();
    }

    abstract class SingleEditTextPresenter extends IMBasePresenter<SingleEditTextView> {
        public abstract void updateContent(String friendId, String groupId, String content, String editType);
    }
}
