package com.thinkcoo.mobile.model.db;

import android.database.Cursor;
import android.util.SparseArray;

import com.example.administrator.publicmodule.model.db.base.BaseDaoImpl;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.thinkcoo.mobile.model.entity.Address;
import com.thinkcoo.mobile.model.entity.Area;
import com.thinkcoo.mobile.model.entity.City;
import com.thinkcoo.mobile.model.entity.DataDictionary;
import com.thinkcoo.mobile.model.entity.Province;
import com.thinkcoo.mobile.model.strategy.DataDictionaryStrategy;
import com.yzkj.android.orm.DbException;
import com.yzkj.android.orm.DbManager;
import com.yzkj.android.orm.sqlite.SqlInfo;
import com.yzkj.android.orm.table.DbModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by Leevin
 * CreateTime: 2016/6/14  9:43
 */

@Singleton
public class DataDictionaryDao extends BaseDaoImpl<DataDictionary> {

    public static final String TAG = "DataDictionaryDao";
    private static final String SQL_QUERY_ADDRESS = "SELECT * FROM "  + DataDictionaryDb.AddressTable.TABLE_NAME+" order by " +DataDictionaryDb.AddressTable.COLUMN_NAME_AREACODE;
    private static final String SQL_QUERY_DATA_DICTIONARY = "SELECT * FROM %s";
    private static final String SQL_QUERY_CITY_BY_SCHOOL = "SELECT areaName FROM com_thinkcoo_mobile_softschedule_bean_SchoolInfo where schoolName='%s'";
    private static final String SQL_QUERY_CITY_BY_CODE = "SELECT a.areacode,a.areaName FROM  com_thinkcoo_mobile_train_bean_AreaInfo a JOIN  com_thinkcoo_mobile_train_bean_AreaInfo b ON a.areaCode LIKE b.areaCode||'%' WHERE b.areaCode  ='%s' and a.areaName<> cityName and a.arealevel<4";
    @Inject
    public DataDictionaryDao(@Named("dd") DbManager dbManager) {
        super(dbManager);
    }

    public List<DataDictionary> queryDataDictionary(DataDictionaryStrategy dataDictionaryStrategy, String ...searchKey) {

        List<DataDictionary> dataDictionaryList = new ArrayList<>();
        Cursor cursor = null;
        try{
            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append(String.format(SQL_QUERY_DATA_DICTIONARY,dataDictionaryStrategy.getDataDictionaryTableName()));
            if (null != searchKey && searchKey.length > 0){
                sqlBuilder.append(dataDictionaryStrategy.getSearchWhere(searchKey));
            }
            cursor = mDbManager.execQuery(sqlBuilder.toString());
            if (null != cursor){
                for (cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
                    dataDictionaryList.add(dataDictionaryStrategy.dataFill(cursor));
                }
            }
        }catch (Exception e){
            ThinkcooLog.e(TAG,e.getMessage(),e);
        }finally {
            safeCloseCursor(cursor);
        }
        return dataDictionaryList;
    }

    public List<Province> queryAddress() {
        List<Province> provinceList = new ArrayList<>();
        SparseArray<Province> provinceSparseArray = new SparseArray<>();
        SparseArray<City> citySparseArray = new SparseArray<>();
        Cursor provinceCursor = null ;
        try {
           provinceCursor = mDbManager.execQuery(SQL_QUERY_ADDRESS);
            if (provinceCursor != null && provinceCursor.getCount() > 0 && provinceCursor.moveToFirst()) {
                do {
                    int areaCode = provinceCursor.getInt(provinceCursor.getColumnIndex(DataDictionaryDb.AddressTable.COLUMN_NAME_AREACODE));
                    String areaName = provinceCursor.getString(provinceCursor.getColumnIndex(DataDictionaryDb.AddressTable.COLUMN_NAME_AREANAME));
                    int areaLevel = provinceCursor.getInt(provinceCursor.getColumnIndex(DataDictionaryDb.AddressTable.COLUMN_NAME_AREALEVEL));
                    int parentCode = provinceCursor.getInt(provinceCursor.getColumnIndex(DataDictionaryDb.AddressTable.COLUMN_NAME_PARENTCODE));
                    switch (areaLevel){
                        case  DataDictionaryDb.AddressTable.PROVINCE_LEVEL:
                            Province province = provinceSparseArray.get(areaCode);
                            if (province ==null){
                                province = new Province(areaName,areaCode);
                                provinceSparseArray.put(areaCode,province);
                                provinceList.add(province);
                            }
                            break;
                        case DataDictionaryDb.AddressTable.CITY_LEVEL:
                            City city = new City(areaName,areaCode);
                            Province parentProvince = provinceSparseArray.get(parentCode);
                            if (areaName.equals(DataDictionaryDb.AddressTable.MUNICIPAL_DISTRICT)){
                                city.setName(parentProvince.getName().replaceAll(DataDictionaryDb.AddressTable.CITY_CHARACTER,""));
                            }
                            citySparseArray.put(areaCode,city);
                            parentProvince.getCityList().add(city);
                            break;
                        case  DataDictionaryDb.AddressTable.AREA_LEVEL:
                            if (areaName.equals(DataDictionaryDb.AddressTable.MUNICIPAL_DISTRICT)) {
                                break;
                            }
                            Area area = new Area(areaName,areaCode);
                            City parentCity = citySparseArray.get(parentCode);
                            parentCity.getAreaList().add(area);
                            break;
                    }
                }while (provinceCursor.moveToNext());
            }
        } catch (DbException e) {
            ThinkcooLog.e(TAG,e.getMessage(),e);
        } finally {
            safeCloseCursor(provinceCursor);
        }
        return provinceList;
    }

    public Observable<String> queryCityNameBuSchoolName(String schoolName){
        String result = "";
        String querySql = String.format(SQL_QUERY_CITY_BY_SCHOOL, schoolName);
        SqlInfo sqlInfo = new SqlInfo(querySql);
        try{
            DbModel dbModel = mDbManager.findDbModelFirst(sqlInfo);
            result = dbModel.getString("areaName");
        }catch (Exception e){
            ThinkcooLog.e(TAG,e.getMessage(),e);
        }
        return  Observable.just(result);
    }


    public Observable<List<Address>> queryChildAreaByCode(String areaCode) {
        String sql = String.format(SQL_QUERY_CITY_BY_CODE, areaCode);
        SqlInfo sqlInfo = new SqlInfo(sql);
        List<Address> areaList = new ArrayList<>();
        try {
            List<DbModel> dbModelAll = mDbManager.findDbModelAll(sqlInfo);
            for (DbModel dbModel : dbModelAll) {
                Address address = new Address(dbModel.getString("areaName"), Integer.parseInt(dbModel.getString("areaCode")));
                areaList.add(address);
            }
        } catch (DbException e) {
            ThinkcooLog.e(TAG,e.getMessage(),e);
        }

        return  Observable.just(areaList);
    }
}
