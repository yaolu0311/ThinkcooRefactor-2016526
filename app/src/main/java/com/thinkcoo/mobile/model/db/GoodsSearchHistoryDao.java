package com.thinkcoo.mobile.model.db;

import com.example.administrator.publicmodule.model.db.base.BaseDaoImpl;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.thinkcoo.mobile.model.entity.GoodsSearchHistory;
import com.yzkj.android.orm.DbException;
import com.yzkj.android.orm.DbManager;
import com.yzkj.android.orm.sqlite.WhereBuilder;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Robert.yao on 2016/8/2.
 */
public class GoodsSearchHistoryDao extends BaseDaoImpl<GoodsSearchHistory> {

    @Inject
    public GoodsSearchHistoryDao(@Named("user") DbManager dbManager) {
        super(dbManager);
    }

    public List<GoodsSearchHistory> queryByGoodsType(int goodsType) {
        try{
            return mDbManager.selector(GoodsSearchHistory.class).where("goodsType","=",goodsType).findAll();
        }catch (Exception e){
            ThinkcooLog.e(TAG,e.getMessage(),e);
            return Collections.emptyList();
        }
    }

    public boolean deleteAllByType(int type) {
        try {
            mDbManager.delete(GoodsSearchHistory.class, WhereBuilder.b("goodsType","=",type));
            return true;
        } catch (DbException e) {
            ThinkcooLog.e(TAG,e.getMessage(),e);
            return false;
        }
    }
}
