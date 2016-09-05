package com.thinkcoo.mobile.model.entity.RequestParam;

import com.thinkcoo.mobile.presentation.views.PageMachine;

/**
 * author ：ml on 2016/8/12
 */
public class LoadRockResultStudentListParam implements RequestParam {

    private String uuid;
    private String groupId;
    private PageMachine pageMachine;
    private boolean isUpdateNow;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public PageMachine getPageMachine() {
        return pageMachine;
    }

    public void setPageMachine(PageMachine pageMachine) {
        this.pageMachine = pageMachine;
    }

    public boolean isUpdateNow() {
        return isUpdateNow;
    }

    public void setUpdateNow(boolean updateNow) {
        isUpdateNow = updateNow;
    }
}
