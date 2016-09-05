package com.yz.im.model.strategy;

/**
 * Created by cys on 2016/8/4
 */
public interface GroupInfoStrategy {

    int isShowName();
    int isShowHead();
    int isShowIntroduction();
    int isShowGroupMember();
    int isShowRemarkName();
    int isShowTransfer();
    int isMsgTop();
    int isMsgDisturb();
    int isReportGroup();
    int isSupportChat();
    int isSupportQuit();

    boolean isOwner();
}
