package com.thinkcoo.mobile.model.repository;

import rx.Observable;

/**
 * Created by robert on 2016/5/22.
 */
public interface AppLaunchRepository {

    Observable copyDataDictionaryDbFile();
    Observable compatOldVersionDb();

}
