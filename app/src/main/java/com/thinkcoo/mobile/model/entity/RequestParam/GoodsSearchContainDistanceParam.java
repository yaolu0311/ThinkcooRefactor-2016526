package com.thinkcoo.mobile.model.entity.RequestParam;

import com.thinkcoo.mobile.presentation.views.PageMachine;

/**
 * Created by Robert.yao on 2016/8/4.
 */
public class GoodsSearchContainDistanceParam implements RequestParam{

    private int type;
    private String place;
    private String school;
    private String commodityCategory;
    private String keyWord;
    private String orderBy;
    private double longitude;
    private double latitude;
    private PageMachine pageMachine;
    private String distance;

    private GoodsSearchContainDistanceParam(Builder builder) {
        type = builder.type;
        place = builder.place;
        school = builder.school;
        commodityCategory = builder.commodityCategory;
        keyWord = builder.keyWord;
        orderBy = builder.orderBy;
        longitude = builder.longitude;
        latitude = builder.latitude;
        pageMachine = builder.pageMachine;
        distance = builder.distance;
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    public int getType() {
        return type;
    }

    public String getPlace() {
        return place;
    }

    public String getSchool() {
        return school;
    }

    public String getCommodityCategory() {
        return commodityCategory;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getDistance() {
        return distance;
    }

    public PageMachine getPageMachine() {
        return pageMachine;
    }

    public static final class Builder {
        private int type;
        private String place;
        private String school;
        private String commodityCategory;
        private String keyWord;
        private String orderBy;
        private double longitude;
        private double latitude;
        private PageMachine pageMachine;
        private String distance;

        private Builder() {
        }

        public Builder type(int val) {
            type = val;
            return this;
        }

        public Builder place(String val) {
            place = val;
            return this;
        }

        public Builder school(String val) {
            school = val;
            return this;
        }

        public Builder commodityCategory(String val) {
            commodityCategory = val;
            return this;
        }

        public Builder keyWord(String val) {
            keyWord = val;
            return this;
        }

        public Builder orderBy(String val) {
            orderBy = val;
            return this;
        }

        public Builder longitude(double val) {
            longitude = val;
            return this;
        }

        public Builder latitude(double val) {
            latitude = val;
            return this;
        }

        public Builder pageMachine(PageMachine val) {
            pageMachine = val;
            return this;
        }

        public Builder distance(String val) {
            distance = val;
            return this;
        }

        public GoodsSearchContainDistanceParam build() {
            return new GoodsSearchContainDistanceParam(this);
        }
    }
}
