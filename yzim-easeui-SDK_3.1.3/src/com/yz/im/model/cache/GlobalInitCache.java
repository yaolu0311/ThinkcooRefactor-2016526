package com.yz.im.model.cache;

/**
 * Created by cys on 2016/7/20
 */
public interface GlobalInitCache<T> {

    void insert(T t);

    void insertAll(T... list);

    void delete(T t);

    void deleteAll(T... list);

    void clear();

}
