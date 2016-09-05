package com.example.administrator.publicmodule.model.db.base;

import android.database.Cursor;

import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.yzkj.android.orm.DbException;
import com.yzkj.android.orm.DbManager;
import com.yzkj.android.orm.sqlite.WhereBuilder;

import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2016/5/24.
 */
public abstract class BaseDaoImpl<T> implements BaseDao<T>{


    public static  final String TAG = "BaseDaoImpl";

    protected DbManager mDbManager;

    public BaseDaoImpl(DbManager dbManager) {
        mDbManager = dbManager;
    }

    @Override
    public void insert(T... t){
        if (!checkT(t)){
            logParamNull("insert");
            return;
        }
        for (int i = 0 ; i < t.length ; i++){
            try {
                mDbManager.save(t[i]);
            } catch (DbException e) {
                log(e);
            }
        }
    }

    @Override
    public void replace(T... t){

        if (!checkT(t)){
            logParamNull("replace");
            return;
        }
        for (int i = 0 ; i < t.length ; i++){
            try {
                mDbManager.replace(t[i]);
            } catch (DbException e) {
                log(e);
            }
        }
    }

    @Override
    public void delete(T ...t){

        if (checkT(t)){
            logParamNull("delete");
            return;
        }
        for (int i = 0; i < t.length; i++) {
            try {
                mDbManager.delete(t[i]);
            } catch (DbException e) {
                log(e);
            }
        }
    }

    @Override
    public void deleteAll(Class<?> deleteEntityClass){
        if (null == deleteEntityClass){
            logParamNull("deleteAll");
            return;
        }
        try {
            mDbManager.delete(deleteEntityClass);
        } catch (DbException e) {
            log(e);
        }
    }

    @Override
    public void deleteById(Class<?> deleteEntityClass, Object id){

        if (null == deleteEntityClass || null == id){
            logParamNull("deleteById");
            return;
        }
        try {
            mDbManager.deleteById(deleteEntityClass,id);
        } catch (DbException e) {
            log(e);
        }

    }

    @Override
    public void deleteBySelector(Class<?> deleteEntityClass , WhereBuilder whereBuilder) {

        if (null == whereBuilder || null == deleteEntityClass){
            logParamNull("deleteBySelector");
            return;
        }
        try {
            mDbManager.delete(deleteEntityClass,whereBuilder);
        } catch (DbException e) {
            log(e);
        }

    }

    @Override
    public void update(T... t)  {

        if (!checkT(t)){
            logParamNull("update");
            return ;
        }
        for (int i = 0; i < t.length; i++) {
            try {
                mDbManager.update(t[i]);
            } catch (DbException e) {
                log(e);
            }
        }

    }

    @Override
    public void updateById(T t, Object id)  {

        if (null == t || null == id){
            logParamNull("updateById");
            return;
        }
        try {
            mDbManager.update( t ,"_id = " + id);
        } catch (DbException e) {
            log(e);
        }

    }

    @Override
    public void updateBySelector(Class<?> updateEntityClass, WhereBuilder whereBuilder)  {

        if (null == updateEntityClass || null == whereBuilder){
            logParamNull("updateBySelector");
            return;
        }
        try {
            mDbManager.update(updateEntityClass,whereBuilder);
        } catch (DbException e) {
            log(e);
        }
    }

    @Override
    public List<T> queryAll(Class<T> queryEntityClass)   {

        List<T> result = null;
        if (null != queryEntityClass){
            try {
                result = mDbManager.findAll(queryEntityClass);
            } catch (DbException e) {
                log(e);
            }
        }else {
            logParamNull("queryAll");
        }
        result = processNullList(result);
        return result;
    }
    @Override
    public List<T> queryAll(WhereBuilder whereBuilder,Class<T> tClass)   {
        List<T> result = null;
        if (null != whereBuilder && null != tClass){
            try {
                result = mDbManager.selector(tClass).where(whereBuilder).findAll();
            } catch (DbException e) {
                log(e);
            }
        }else {
            logParamNull("queryAll");
        }
        result = processNullList(result);
        return result;
    }


    @Override
    public T queryFirst(WhereBuilder whereBuilder, Class<T> tClass ,T defaultReturnEntity)   {

        if (null == whereBuilder || null == tClass){
            return defaultReturnEntity;
        }
        T result = null;
        try{
            result = mDbManager.selector(tClass).where(whereBuilder).findFirst();
        }catch (DbException e){
            log(e);
        }
        if (null == result){
            return defaultReturnEntity;
        }
        return result;
    }

    @Override
    public T queryById(Class<T> queryEntityClass, Object id , T defaultReturnEntity) {

        if (null == queryEntityClass || null == id) {
            logParamNull("queryById");
            return defaultReturnEntity;
        }
        try {
            T result = mDbManager.findById(queryEntityClass, id);
            if (null == result) {
                result = defaultReturnEntity;
            }
            return result;
        } catch (DbException e) {
            log(e);
            return defaultReturnEntity;
        }
    }

    private void logParamNull(String methodName){
        ThinkcooLog.e(TAG,"=== 调用方法  : " + methodName + " 发生参数异常 ===");
    }

    private void log(DbException e){
        ThinkcooLog.e(TAG,e.getMessage(),e);
    }
    private  List<T> processNullList(List<T> result){
        if (null == result || result.size() == 0){
            return Collections.EMPTY_LIST;
        }
        return  result;
    }
    private boolean checkT(T[] t) {
        if (null == t || t.length == 0){
            return false;
        }
        return true;
    }

    protected void safeCloseCursor(Cursor cursor){
        if (null != cursor && !cursor.isClosed()){
            cursor.close();
        }
    }
}
