package com.thinkcoo.mobile.model.entity;

import com.thinkcoo.mobile.model.entity.nullentities.Nullable;

/**
 * Created by Robert.yao on 2016/3/30.
 */
public class LocationBean implements Nullable{

    /**
     * 本地数据库编号
     */
    private int id;
    /**
     * 定位精度半径，单位是米
     */
    private float radius;
    /**
     *  维度
     */
    private String latitude;
    /**
     * 经度
     */
    private String longitude;
    /**
     * 反地理编码
     */
    private String addrStr;
    /**
     * 获取省份信息
     */
    private String province;
    /**
     * 获取城市信息
     */
    private String city;
    /**
     * 获取区县信息
     */
    private String district;
    /**
     * 错误
     */
    private int isError;
    /**
     * 详细地址
     */
    private String addressInfo;
    /**
     * 百度城市编码
     */
    private String cityCode;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(String addressInfo) {
        this.addressInfo = addressInfo;
    }

    public int getIsError() {
        return isError;
    }

    public void setIsError(int isError) {
        this.isError = isError;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getAddrStr() {
        return addrStr;
    }

    public void setAddrStr(String addrStr) {
        this.addrStr = addrStr;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    @Override
    public String toString() {

        return "LocationBean{" +
                "id=" + id +
                ", radius=" + radius +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", addrStr='" + addrStr + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", isError=" + isError +
                ", addressInfo='" + addressInfo + '\'' +
                ", cityCode='" + cityCode + '\'' +
                '}';
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
