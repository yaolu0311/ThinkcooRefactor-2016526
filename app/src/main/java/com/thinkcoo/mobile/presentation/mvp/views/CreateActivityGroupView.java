package com.thinkcoo.mobile.presentation.mvp.views;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.thinkcoo.mobile.model.entity.Student;

import java.util.List;

/**
 * Created by Robert.yao on 2016/3/22.
 */
public interface CreateActivityGroupView extends MvpView , BaseHintView , BaseActivityHelpView{
   void toActiveMemberActivity(String groupid);
}
