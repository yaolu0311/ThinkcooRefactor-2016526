package com.thinkcoo.mobile.model.entity.serverresponse;

import java.util.List;

/**
 * Created by Administrator on 2016/5/27.
 */
public class UserHarvestsResponse{

    /**
     * grantName : 测试1
     * grantTime : 2015-05-27
     * grantUnits : 上海交大
     * grantId : 27
     * grantPic : null
     */

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private String grantName;
        private String grantTime;
        private String grantUnits;
        private String grantId;
        private String grantPic;

        public String getGrantName() {
            return grantName;
        }

        public void setGrantName(String grantName) {
            this.grantName = grantName;
        }

        public String getGrantTime() {
            return grantTime;
        }

        public void setGrantTime(String grantTime) {
            this.grantTime = grantTime;
        }

        public String getGrantUnits() {
            return grantUnits;
        }

        public void setGrantUnits(String grantUnits) {
            this.grantUnits = grantUnits;
        }

        public String getGrantId() {
            return grantId;
        }

        public void setGrantId(String grantId) {
            this.grantId = grantId;
        }

        public String getGrantPic() {
            return grantPic;
        }

        public void setGrantPic(String grantPic) {
            this.grantPic = grantPic;
        }
    }
}
