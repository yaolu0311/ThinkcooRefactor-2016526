package com.thinkcoo.mobile.model.db;

/**
 * Created by Leevin
 * CreateTime: 2016/6/17  9:38
 */
public interface DataDictionaryDb {

    interface AddressTable {

        String TABLE_NAME = "com_thinkcoo_mobile_train_bean_AreaInfo";
        String COLUMN_NAME_ID = "id";
        String COLUMN_NAME_AREACODE = "areaCode";
        String COLUMN_NAME_AREANAME = "areaName";
        String COLUMN_NAME_PARENTCODE = "parentCode";
        String COLUMN_NAME_BAIDUCODE = "baiduCode";
        String COLUMN_NAME_AREALEVEL = "areaLevel";

        // 表中相关字段
        int PROVINCE_LEVEL = 1;
        int CITY_LEVEL = 2;
        int AREA_LEVEL = 3;
        String MUNICIPAL_DISTRICT = "市辖区";
        String CITY_CHARACTER= "市";
    }


}
