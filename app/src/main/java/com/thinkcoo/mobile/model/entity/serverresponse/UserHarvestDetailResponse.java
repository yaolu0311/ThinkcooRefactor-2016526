package com.thinkcoo.mobile.model.entity.serverresponse;

import java.util.List;

/**
 * Created by Administrator on 2016/5/27.
 */
public class UserHarvestDetailResponse {


    /**
     * grantName : 你们
     * grantTime : 2016-05-27
     * ranking : null
     * grantUnits : 67661
     * grantLevel : null
     * grantPicList : [{"grantPicId":"48","grantPic":"http://117.34.109.231/yingzi-mobile/upload/personal/attachment/20160527134828540.jpg"}]
     */

    private String grantName;
    private String grantTime;
    private String ranking;
    private String grantUnits;
    private String grantLevel;
    /**
     * grantPicId : 48
     * grantPic : http://117.34.109.231/yingzi-mobile/upload/personal/attachment/20160527134828540.jpg
     */

    private List<GrantPicListBean> grantPicList;

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

    public List<GrantPicListBean> getGrantPicList() {
        return grantPicList;
    }

    public void setGrantPicList(List<GrantPicListBean> grantPicList) {
        this.grantPicList = grantPicList;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public String getGrantLevel() {
        return grantLevel;
    }

    public void setGrantLevel(String grantLevel) {
        this.grantLevel = grantLevel;
    }

    public static class GrantPicListBean {
        private String grantPicId;
        private String grantPic;

        public String getGrantPicId() {
            return grantPicId;
        }

        public void setGrantPicId(String grantPicId) {
            this.grantPicId = grantPicId;
        }

        public String getGrantPic() {
            return grantPic;
        }

        public void setGrantPic(String grantPic) {
            this.grantPic = grantPic;
        }
    }
}
