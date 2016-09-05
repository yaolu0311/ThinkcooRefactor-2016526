package com.thinkcoo.mobile.model.strategy;

import android.database.Cursor;
import com.thinkcoo.mobile.model.entity.DataDictionary;

/**
 * Created by Robert.yao on 2016/6/20.
 */
public interface DataDictionaryStrategy {

    int getDataDictionaryTitle();
    String getDataDictionaryTableName();
    String getSearchWhere(String ...searchKey);
    DataDictionary dataFill(Cursor cursor);

}
