package com.thinkcoo.mobile.model.rest.apis;

import com.thinkcoo.mobile.model.entity.PoiAddress;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Robert.yao on 2016/8/11.
 */
public interface BaiduApi {

    @GET("place/v2/search")
    Observable<PoiAddress> querySchoolAddress(@Query("query") String schoolName,@Query("output") String output,@Query("ak") String ak,@Query("page_size") String pageSize,@Query("region") String region);

}
