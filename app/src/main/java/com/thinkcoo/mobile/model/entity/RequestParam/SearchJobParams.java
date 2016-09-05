package com.thinkcoo.mobile.model.entity.RequestParam;

import com.thinkcoo.mobile.model.entity.JobFilter;
import com.thinkcoo.mobile.presentation.views.PageMachine;

/**
 * Created by Robert.yao on 2016/8/20.
 */
public class SearchJobParams implements RequestParam{

    boolean updateNow;
    JobFilter jobFilter;
    PageMachine pageMachine;


    public boolean isUpdateNow() {
        return updateNow;
    }

    public void setUpdateNow(boolean updateNow) {
        this.updateNow = updateNow;
    }

    public JobFilter getJobFilter() {
        return jobFilter;
    }

    public void setJobFilter(JobFilter jobFilter) {
        this.jobFilter = jobFilter;
    }

    public PageMachine getPageMachine() {
        return pageMachine;
    }

    public void setPageMachine(PageMachine pageMachine) {
        this.pageMachine = pageMachine;
    }
}
