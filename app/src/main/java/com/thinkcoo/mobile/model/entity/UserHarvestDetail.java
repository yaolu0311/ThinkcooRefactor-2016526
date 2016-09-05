package com.thinkcoo.mobile.model.entity;

import android.os.Parcel;
import android.os.Parcelable;
import com.thinkcoo.mobile.model.entity.nullentities.Nullable;
import com.thinkcoo.mobile.model.entity.serverresponse.UserHarvestDetailResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leevin
 * CreateTime: 2016/6/3  8:26
 */
public class UserHarvestDetail implements Nullable, Parcelable {

    public static final UserHarvestDetail NULL_USER_HARVEST_DETAIL = new UserHarvestDetail();

    @Override
    public boolean isEmpty() {
        return false;
    }

    private String grantRank;
    private String grantLevel;
    private List<GrantPicBean> grantPicList;

    public static UserHarvestDetail fromServerResponse(UserHarvestDetailResponse data) {
        UserHarvestDetail userHarvestDetail = new UserHarvestDetail();
        userHarvestDetail.setGrantLevel(data.getGrantLevel() ==null ? "":data.getGrantLevel());
        userHarvestDetail.setGrantRank(data.getRanking() == null ?"":data.getRanking());
        if (data.getGrantPicList() == null) {
            return userHarvestDetail;
        }
        ArrayList<GrantPicBean> picBeanList = new ArrayList<>();
        for (UserHarvestDetailResponse.GrantPicListBean serverPicbean :data.getGrantPicList()) {
            GrantPicBean grantPicBean = new GrantPicBean();
            grantPicBean.setGrantPicPath(serverPicbean.getGrantPic() == null ?"":serverPicbean.getGrantPic());
            grantPicBean.setGrantPicId(serverPicbean.getGrantPicId() == null ?"":serverPicbean.getGrantPicId());
            picBeanList.add(grantPicBean);
        }
        userHarvestDetail.setGrantPicList(picBeanList);
        return userHarvestDetail;
    }

   public static class GrantPicBean {
        private String grantPicId;
        private String grantPicPath;

        public String getGrantPicId() {
            return grantPicId;
        }

        public void setGrantPicId(String grantPicId) {
            this.grantPicId = grantPicId;
        }

        public String getGrantPicPath() {
            return grantPicPath;
        }

        public void setGrantPicPath(String grantPicPath) {
            this.grantPicPath = grantPicPath;
        }
    }

    public String getGrantRank() {
        return grantRank;
    }

    public void setGrantRank(String grantRank) {
        this.grantRank = grantRank;
    }

    public String getGrantLevel() {
        return grantLevel;
    }

    public void setGrantLevel(String grantLevel) {
        this.grantLevel = grantLevel;
    }

    public List<GrantPicBean> getGrantPicList() {
        return grantPicList;
    }

    public void setGrantPicList(List<GrantPicBean> grantPicList) {
        this.grantPicList = grantPicList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserHarvestDetail that = (UserHarvestDetail) o;

        if (grantRank != null ? !grantRank.equals(that.grantRank) : that.grantRank != null)
            return false;
        if (grantLevel != null ? !grantLevel.equals(that.grantLevel) : that.grantLevel != null)
            return false;
        return grantPicList != null ? grantPicList.equals(that.grantPicList) : that.grantPicList == null;

    }

    @Override
    public int hashCode() {
        int result = grantRank != null ? grantRank.hashCode() : 0;
        result = 31 * result + (grantLevel != null ? grantLevel.hashCode() : 0);
        result = 31 * result + (grantPicList != null ? grantPicList.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.grantRank);
        dest.writeString(this.grantLevel);
        dest.writeList(this.grantPicList);
    }

    public UserHarvestDetail() {
    }

    protected UserHarvestDetail(Parcel in) {
        this.grantRank = in.readString();
        this.grantLevel = in.readString();
        this.grantPicList = new ArrayList<GrantPicBean>();
        in.readList(this.grantPicList, GrantPicBean.class.getClassLoader());
    }

    public static final Creator<UserHarvestDetail> CREATOR = new Creator<UserHarvestDetail>() {
        @Override
        public UserHarvestDetail createFromParcel(Parcel source) {
            return new UserHarvestDetail(source);
        }

        @Override
        public UserHarvestDetail[] newArray(int size) {
            return new UserHarvestDetail[size];
        }
    };
}
