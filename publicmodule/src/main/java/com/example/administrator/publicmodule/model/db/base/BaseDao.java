package com.example.administrator.publicmodule.model.db.base;

import com.yzkj.android.orm.sqlite.WhereBuilder;

import java.util.List;


/**
 * Created by Administrator on 2016/5/24.
 */
public interface BaseDao<T> {

    //增
    void insert(T... t);
    void replace(T... t);
    //刪
    void delete(T... t) ;
    void deleteAll(Class<?> deleteEntityClass);
    void deleteById(Class<?> deleteEntityClass, Object id);
    void deleteBySelector(Class<?> deleteEntityClass, WhereBuilder whereBuilder);
    //改
    void update(T... t);
    void updateById(T t, Object id);
    void updateBySelector(Class<?> updateEntityClass, WhereBuilder whereBuilder);
    //查詢
    List<T> queryAll(Class<T> queryEntityClass);
    List<T> queryAll(WhereBuilder whereBuilder, Class<T> tClass);
    T queryFirst(WhereBuilder whereBuilder, Class<T> tClass, T defaultReturnEntity);
    T queryById(Class<T> queryEntityClass, Object id, T defaultReturnEntity);

}
