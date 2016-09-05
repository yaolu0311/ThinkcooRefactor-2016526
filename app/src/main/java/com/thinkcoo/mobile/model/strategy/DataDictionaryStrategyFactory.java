package com.thinkcoo.mobile.model.strategy;

import android.database.Cursor;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.ActivityScope;
import com.thinkcoo.mobile.model.entity.DataDictionary;
import com.thinkcoo.mobile.model.entity.GoodsCategory;
import com.thinkcoo.mobile.model.repository.DataDictionaryRepository;

import javax.inject.Inject;


/**
 * Created by Robert.yao on 2016/6/20.
 */
@ActivityScope
public class DataDictionaryStrategyFactory {

    public static final int DD_TYPE_POLITICS = 0x0001;
    public static final int DD_TYPE_NATION = 0x0002;
    public static final int DD_TYPE_EDUCATION = 0x0003;
    public static final int DD_TYPE_CERTIFICATE = 0x0004;

    public static final int DD_TYPE_SCHOOL = 0x0005;
    public static final int DD_TYPE_SCHOOL_DEPARTMENT = 0x0006;
    public static final int DD_TYPE_SCHOOL_MAJOR = 0x0007;
    public static final int DD_TYPE_INDUSTRY_DIRECTION = 0x0008;
    public static final int DD_TYPE_GOODS_CATEGORY = 0X0009;
    public static final int DD_TYPE_QUALITY = 0x0010;



    private static String POLITICS_DD_TABLE_NAME  = "com_thinkcoo_mobile_personal_bean_politics";
    private static String NATION_DD_TABLE_NAME    = "com_thinkcoo_mobile_personal_bean_NationSelectBean";
    private static String EDUCATION_DD_TABLE_NAME = "com_thinkcoo_mobile_personal_bean_educational";
    private static String CERTIFICATE_DD_TABLE_NAME = "com_thinkcoo_mobile_personal_bean_certificate";

    private static String SHCOOL_DD_TABLE_NAME = "com_thinkcoo_mobile_softschedule_bean_SchoolInfo";
    private static String SHCOOL_DEPARTMENT_DD_TABLE_NAME = "com_thinkcoo_mobile_softschedule_bean_DepartmentInfo";
    private static String SHCOOL_MAJOR_DD_TABLE_NAME = "com_thinkcoo_mobile_softschedule_bean_MajorInfo";

    private static String INDUSTRY_DIRECTION_DD_TABLE_NAME = "com_thinkcoo_mobile_getjob_bean_IndustryDirectionBean";
    private static String GOODS_CATOGORY_DD_TABLE_NAME = "goods_category";
    private static String QUALITY_DD_TABLE_NAME = "qulity";

    private static  final String WHERE = " where %s LIKE '%%%s%%' ";//where schoolName LIKE '%西%''%s%%'

    @Inject
    public DataDictionaryStrategyFactory() {
    }

    public  DataDictionaryStrategy create(int type){

        switch (type){
            case DD_TYPE_CERTIFICATE:
                return  new CertificateDataDictionaryStrategy();
            case DD_TYPE_EDUCATION:
                return  new EducationDataDictionaryStrategy();
            case DD_TYPE_NATION:
                return  new NationDataDictionaryStrategy();
            case DD_TYPE_POLITICS:
                return  new PoliticsDataDictionaryStrategy();
            case DD_TYPE_SCHOOL:
                return  new SchoolDataDictionaryStrategy();
            case DD_TYPE_SCHOOL_DEPARTMENT:
                return  new SchoolDepartmentStrategy();
            case DD_TYPE_SCHOOL_MAJOR:
                return  new SchoolMajorStrategy();
            case DD_TYPE_INDUSTRY_DIRECTION:
                return new IndustryDirectionStrategy();
            case DD_TYPE_GOODS_CATEGORY:
                return new GoodsCategoryStrategy();
            case DD_TYPE_QUALITY:
                return new QualityCategoryStrategy();
            default:
                throw new IllegalArgumentException("Bab data dictionary type!!!");
        }

    }

    private  class CertificateDataDictionaryStrategy implements DataDictionaryStrategy {

        @Override
        public int getDataDictionaryTitle() {
            return R.string.certificate;
        }

        @Override
        public String getDataDictionaryTableName() {
            return CERTIFICATE_DD_TABLE_NAME;
        }

        @Override
        public DataDictionary dataFill(Cursor cursor) {
            return simpleDataFill(cursor);
        }

        @Override
        public String getSearchWhere(String ...searchKey) {
            return simpleWhere(searchKey[0]);
        }
    }

    private  class EducationDataDictionaryStrategy implements DataDictionaryStrategy {

        @Override
        public int getDataDictionaryTitle() {
            return R.string.jiaoyujingli;
        }

        @Override
        public String getDataDictionaryTableName() {
            return EDUCATION_DD_TABLE_NAME;
        }

        @Override
        public DataDictionary dataFill(Cursor cursor) {
            return simpleDataFill(cursor);
        }

        @Override
        public String getSearchWhere(String ...searchKey) {
            return simpleWhere(searchKey[0]);
        }
    }

    private  class NationDataDictionaryStrategy implements DataDictionaryStrategy {

        @Override
        public int getDataDictionaryTitle() {
            return R.string.minzu;
        }

        @Override
        public String getDataDictionaryTableName() {
            return NATION_DD_TABLE_NAME;
        }

        @Override
        public DataDictionary dataFill(Cursor cursor) {
            return simpleDataFill(cursor);
        }

        @Override
        public String getSearchWhere(String ...searchKey) {
            return simpleWhere(searchKey[0]);
        }
    }

    private  class PoliticsDataDictionaryStrategy implements DataDictionaryStrategy {

        @Override
        public int getDataDictionaryTitle() {
            return R.string.zhengzhimianmao;
        }

        @Override
        public String getDataDictionaryTableName() {
            return POLITICS_DD_TABLE_NAME;
        }

        @Override
        public DataDictionary dataFill(Cursor cursor) {
            return simpleDataFill(cursor);
        }

        @Override
        public String getSearchWhere(String ...searchKey) {
            return simpleWhere(searchKey[0]);
        }
    }

    private class SchoolDataDictionaryStrategy implements DataDictionaryStrategy{

        @Override
        public int getDataDictionaryTitle() {
            return R.string.xuexiao;
        }

        @Override
        public String getDataDictionaryTableName() {
            return SHCOOL_DD_TABLE_NAME;
        }

        @Override
        public String getSearchWhere(String ...searchKey) {
            return compoundWhere("schoolName",searchKey[0]);
        }

        @Override
        public DataDictionary dataFill(Cursor cursor) {

            DataDictionary dataDictionary = new DataDictionary();

            dataDictionary.setId(cursor.getInt(cursor.getColumnIndex("id")));
            dataDictionary.setDisplayName(cursor.getString(cursor.getColumnIndex("schoolName")));
            dataDictionary.setCode(cursor.getString(cursor.getColumnIndex("schoolCode")));
            dataDictionary.setData1(cursor.getString(cursor.getColumnIndex("areaCode")));
            dataDictionary.setData2(cursor.getString(cursor.getColumnIndex("areaName")));

            return dataDictionary;
        }
    }


    private class SchoolDepartmentStrategy implements DataDictionaryStrategy{

        @Override
        public int getDataDictionaryTitle() {
            return R.string.yuanxi;
        }

        @Override
        public String getDataDictionaryTableName() {
            return SHCOOL_DEPARTMENT_DD_TABLE_NAME;
        }

        @Override
        public String getSearchWhere(String ...searchKey) {

            StringBuilder whereBuilder = new StringBuilder();
            whereBuilder.append(" WHERE ");
            whereBuilder.append("departmentName LIKE '");
            whereBuilder.append("%");
            whereBuilder.append(searchKey[0]);
            whereBuilder.append("%'");
            whereBuilder.append(" and ");
            whereBuilder.append("schoolCode");
            whereBuilder.append("=");
            whereBuilder.append("(SELECT schoolCode FROM ");
            whereBuilder.append(SHCOOL_DD_TABLE_NAME);
            whereBuilder.append(" WHERE schoolName='");
            whereBuilder.append(searchKey[1]);
            whereBuilder.append("')");
            return whereBuilder.toString();
        }

        @Override
        public DataDictionary dataFill(Cursor cursor) {

            DataDictionary dataDictionary = new DataDictionary();

            dataDictionary.setId(cursor.getInt(cursor.getColumnIndex("id")));
            dataDictionary.setDisplayName(cursor.getString(cursor.getColumnIndex("departmentName")));
            dataDictionary.setCode(cursor.getString(cursor.getColumnIndex("departmentCode")));
            dataDictionary.setParentCode(cursor.getString(cursor.getColumnIndex("schoolCode")));


            return dataDictionary;
        }

    }

    private class SchoolMajorStrategy implements DataDictionaryStrategy{

        @Override
        public int getDataDictionaryTitle() {
            return R.string.zhuanye;
        }

        @Override
        public String getDataDictionaryTableName() {
            return SHCOOL_MAJOR_DD_TABLE_NAME;
        }

        @Override
        public String getSearchWhere(String ...searchKey) {
            return compoundWhere("majorName",searchKey[0]);
        }

        @Override
        public DataDictionary dataFill(Cursor cursor) {

            DataDictionary dataDictionary = new DataDictionary();

            dataDictionary.setId(cursor.getInt(cursor.getColumnIndex("id")));
            dataDictionary.setDisplayName(cursor.getString(cursor.getColumnIndex("majorName")));
            dataDictionary.setCode(cursor.getString(cursor.getColumnIndex("majorCode")));
            dataDictionary.setParentCode(cursor.getString(cursor.getColumnIndex("parentCode")));
            dataDictionary.setData1(cursor.getString(cursor.getColumnIndex("level")));

            return dataDictionary;
        }
    }
    private class IndustryDirectionStrategy implements DataDictionaryStrategy {

        @Override
        public int getDataDictionaryTitle() {
            return R.string.industry_direction;
        }

        @Override
        public String getDataDictionaryTableName() {
            return INDUSTRY_DIRECTION_DD_TABLE_NAME;
        }

        @Override
        public String getSearchWhere(String ...searchKey) {
            return simpleWhere(searchKey[0]);
        }

        @Override
        public DataDictionary dataFill(Cursor cursor) {
            DataDictionary dataDictionary = new DataDictionary();
            dataDictionary.setId(cursor.getInt(cursor.getColumnIndex("id")));
            dataDictionary.setDisplayName(cursor.getString(cursor.getColumnIndex("display_name")));
            dataDictionary.setCode(cursor.getString(cursor.getColumnIndex("code")));
            return dataDictionary;
        }
    }

    private class GoodsCategoryStrategy implements DataDictionaryStrategy {

        @Override
        public int getDataDictionaryTitle() {
            return R.string.goods_category;
        }

        @Override
        public String getDataDictionaryTableName() {
            return GOODS_CATOGORY_DD_TABLE_NAME;
        }

        @Override
        public String getSearchWhere(String... searchKey) {
            return simpleWhere(searchKey[0]);
        }

        @Override
        public DataDictionary dataFill(Cursor cursor) {
            return simpleDataFill(cursor);
        }
    }

    private class QualityCategoryStrategy implements DataDictionaryStrategy{

        @Override
        public int getDataDictionaryTitle() {
            return R.string.select_quality;
        }

        @Override
        public String getDataDictionaryTableName() {
            return QUALITY_DD_TABLE_NAME;
        }

        @Override
        public String getSearchWhere(String... searchKey) {
            return simpleWhere(searchKey[0]);
        }

        @Override
        public DataDictionary dataFill(Cursor cursor) {
            return simpleDataFill(cursor);
        }
    }

    private  String simpleWhere(String searchKey){
        return compoundWhere("display_name",searchKey);
    }
    private String compoundWhere(String filedKey , String searchKey){
        return String.format(WHERE,filedKey,searchKey);
    }
    private  DataDictionary simpleDataFill(Cursor cursor){
        DataDictionary dataDictionary = new DataDictionary();
        dataDictionary.setCode(String.valueOf(cursor.getInt(cursor.getColumnIndex("id"))));
        dataDictionary.setDisplayName(cursor.getString(cursor.getColumnIndex("display_name")));
        return dataDictionary;
    }


}
