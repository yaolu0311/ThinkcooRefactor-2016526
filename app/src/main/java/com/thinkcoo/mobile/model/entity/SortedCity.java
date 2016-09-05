package com.thinkcoo.mobile.model.entity;

import com.example.administrator.publicmodule.util.PinyinHelper;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Robert.yao on 2016/7/26.
 */
public class SortedCity implements SortedAndSectionEntity ,Comparable<SortedCity>{

    public static List<SortedCity> provinceList2SortedCityList(List<Province> provinceList){

        List<SortedCity> sortedCityList = new ArrayList<>();

        for (Province province: provinceList) {
            List<City> cityList = province.getCityList();
            for (City city: cityList) {
                SortedCity sc = new SortedCity();
                sc.setId(city.getStringCode());
                sc.setCityName(city.getName());
                sc.setProvinceName(province.getName());
                sc.setLocationCode(city.getBaiduCode());
                sc.setSortHead(cityName2SortHead(city.getName()));
                sc.setSectionHead(province2SectionHead(sc.getProvinceName()));
                sortedCityList.add(sc);
            }
        }
        return sortedCityList;
    }

    private static String province2SectionHead(String provinceName) {
        return PinyinHelper.getInstance().getFirstUpCaseLetter(provinceName);
    }

    private static String cityName2SortHead(String name) {
        return PinyinHelper.getInstance().getFirstUpCaseLetter(name);
    }

    private String id;
    private String provinceName;
    private String cityName;
    private double lon;
    private double lat;
    private String locationCode;
    private String sortHead;
    private String sectionHead;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getSortHead() {
        return sortHead;
    }
    @Override
    public int getSectionId() {
        return Integer.parseInt(id.substring(0,2));
        //return provinceName.hashCode();
    }
    @Override
    public String getSectionName() {
        return provinceName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public void setSortHead(String sortHead) {
        this.sortHead = sortHead;
    }
    @Override
    public String toString() {
        return "SortedCity{" +
                "provinceName='" + provinceName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", lon=" + lon +
                ", lat=" + lat +
                ", locationCode='" + locationCode + '\'' +
                ", sortHead='" + sortHead + '\'' +
                '}';
    }

    @Override
    public int compareTo(SortedCity another) {
        return this.getSortHead().compareTo(another.getSortHead());
    }

    @Override
    public String getSectionNameFirstChar() {
        return sectionHead;
    }

    public void setSectionHead(String sectionHead) {
        this.sectionHead = sectionHead;
    }

}
