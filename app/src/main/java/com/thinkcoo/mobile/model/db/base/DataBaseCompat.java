package com.thinkcoo.mobile.model.db.base;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by robert on 2016/5/22.
 */
@Singleton
public class DataBaseCompat {

    @Inject
    public DataBaseCompat() {

    }
    public boolean tryCompatOldVersion(){
        return false;
    }
}
