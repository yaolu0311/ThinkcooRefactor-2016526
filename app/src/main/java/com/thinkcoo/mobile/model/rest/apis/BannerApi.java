package com.thinkcoo.mobile.model.rest.apis;
import com.example.administrator.publicmodule.entity.BaseResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.HomeImageBean;
import java.util.List;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Robert.yao on 2016/7/19.
 */
public interface BannerApi {
    @POST("queryadvertisement.json")
    Observable<BaseResponse<List<HomeImageBean>>> loadBannerList();
}
