package com.thinkcoo.mobile.model.rest.apis;

import com.example.administrator.publicmodule.entity.BaseResponse;
import com.thinkcoo.mobile.model.entity.MyCollectionGoodsListResponse;
import com.thinkcoo.mobile.model.entity.QueryBuynformationResponseList;
import com.thinkcoo.mobile.model.entity.QuerySellInformationResponseList;
import com.thinkcoo.mobile.model.entity.serverresponse.BuyGoodsDetailResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.LookGoodsDetailResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.MyBuyGoodsListResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.MyCollectionBuyResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.MyCollectionGoodsResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.MySellGoodsListResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.GoodsSearchResultResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.SellGoodsDetailResponse;

import java.util.List;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/6/22.
 * 自贸区
 */
public interface TradeApi {
    /**
     * 主页查询出售
     *
     * @param userId    用户的id
     * @param typeId    类型的Id      出售的ID 为1   求购的ID 为2
     * @param place     定位的城市
     * @param longitude 经度信息
     * @param latitude  纬度信息
     * @param pageNow   当前页数
     * @param pageSize  页数
     */
    @POST("querycommoditylist.json")
    Observable<BaseResponse<QuerySellInformationResponseList>> querySellInformation(
            @Query("userId") String userId, @Query("typeId") String typeId, @Query("place") String place,
            @Query("longitude") String longitude, @Query("latitude") String latitude, @Query("pageNow") int pageNow,
            @Query("pageSize") int pageSize);

    /**
     * 主页查询 求购的信息
     */
    @POST("querycommoditylist.json")
    Observable<BaseResponse<QueryBuynformationResponseList>> queryBuyInformation(
            @Query("userId") String userId, @Query("typeId") String typeId, @Query("place") String place,
            @Query("longitude") String longitude, @Query("latitude") String latitude, @Query("pageNow") int pageNow,
            @Query("pageSize") int pageSize);


    /**
     * 我的收藏商品
     */
    @POST("querycollection.json")
    Observable<BaseResponse<MyCollectionGoodsListResponse>> loadMyCollectionGoods(
            @Query("userId") String userId, @Query("typeId") int typeId, @Query("pageNow") int pageNow,
            @Query("pageSize") int pageSize);

    /**
     * 我的自贸区-- 发布商品求购信息
     *
     * @param userId
     * @param place             定位的城市
     * @param commodityTitle    商品的名称
     * @param commodityCategory 商品类型
     * @param price             价格
     * @param realName          用户真实名称
     * @param commodityDetails  商品描述信息
     * @param school            学校
     * @param longitude         经度
     * @param latitude          纬度
     * @param address           学校地址
     * @return
     */
    @POST("addbuy.json")
    Observable<BaseResponse> addbuy(@Query("userId") String userId, @Query("place") String place, @Query("commodityTitle") String commodityTitle, @Query("commodityCategory") String commodityCategory, @Query("price") String price,
                                    @Query("realName") String realName, @Query("commodityDetails") String commodityDetails, @Query("school") String school, @Query("longitude") String longitude, @Query("latitude") String latitude,
                                    @Query("address") String address);

    /**
     * 我的自贸区-- 修改发布商品求购信息
     *
     * @param userId
     * @param place             定位的城市
     * @param buyId             商品买卖的ID
     * @param commodityTitle    商品的名称
     * @param commodityCategory 商品类型
     * @param price             价格
     * @param commodityDetails  商品描述信息
     * @param school            学校
     * @param longitude         经度
     * @param latitude          纬度
     * @param address           学校的地址
     * @return
     */
    @POST("updatebuy.json")
    Observable<BaseResponse> updatebuy(@Query("userId") String userId, @Query("place") String place, @Query("buyId") String buyId, @Query("commodityTitle") String commodityTitle, @Query("commodityCategory") String commodityCategory, @Query("price") String price,
                                       @Query("commodityDetails") String commodityDetails, @Query("school") String school, @Query("longitude") String longitude, @Query("latitude") String latitude,
                                       @Query("address") String address);

    /**
     * 我的收藏   添加收藏
     *
     * @param userId
     * @param typeId
     * @return
     */
    @POST("addcollection.json")
    Observable<BaseResponse> addcollection(@Query("userId") String userId, @Query("commodityId") String commodityId, @Query("typeId") String  typeId);

    /**
     * 我的收藏   取消收藏
     *
     * @param collectionId 收藏  ID
     * @return
     */
    @POST("delcollection")
    Observable<BaseResponse> delcollection(@Query("collectionId") String collectionId);

    /**
     * 出售详情界面
     *
     * @param userId
     * @param sellId 出售的ID
     * @return
     */
    @POST("queryselldetails.json")
    Observable<BaseResponse<List<LookGoodsDetailResponse>>> queryselldetails(@Query("userId") String userId, @Query("sellId") String sellId);

    /**
     * 发布出售商品
     *
     * @param userId
     * @param commodityName     发布的商品的标题
     * @param price             价格
     * @param commodityCategory 种类
     * @param quality           成色
     * @param school            学校
     * @param address           学校地址
     * @param introduce         商品描述
     * @param place             学校的所在城市
     * @param longitude         经度
     * @param latitude          纬度
     * @param filePath          图片商品的路径
     * @return
     */
    @POST("addsell.json")
    Observable<BaseResponse> addsell(@Query("userId") String userId, @Query("commodityName") String commodityName, @Query("price") String price, @Query("commodityCategory") String commodityCategory, @Query("quality") String quality
            , @Query("school") String school, @Query("address") String address, @Query("introduce") String introduce, @Query("place") String place, @Query("longitude") String longitude, @Query("latitude") String latitude,
                                     @Query("filePath") String filePath);

    /**
     * 修改发布出售商品
     *
     * @param userId
     * @param sellId            出售商品的ID
     * @param commodityName     发布的商品的标题
     * @param price             价格
     * @param commodityCategory 种类
     * @param quality           成色
     * @param school            学校
     * @param address           学校地址
     * @param introduce         商品描述信息
     * @param place             学校的城市
     * @param longitude         经度
     * @param latitude          纬度
     * @param filePath          图片路径
     * @return
     */
    @POST("updatesell.json")
    Observable<BaseResponse> updatesell(@Query("userId") String userId, @Query("sellId") String sellId, @Query("commodityName") String commodityName, @Query("price") String price, @Query("commodityCategory") String commodityCategory, @Query("quality") String quality
            , @Query("school") String school, @Query("address") String address, @Query("introduce") String introduce, @Query("place") String place, @Query("longitude") String longitude, @Query("latitude") String latitude,
                                        @Query("filePath") String filePath);

    /**
     * 更新或者删除商品网络图片
     *
     * @param userId
     * @param attachmentId 所有文件集合一个positionID
     * @return
     */
    @POST("delattachment.json")
    Observable<BaseResponse> delattachment(@Query("userId") String userId, @Query("attachmentId") String attachmentId);

    /**
     * 获取我的出售界面的信息
     *
     * @param userId
     * @param pageNow  当前的页数
     * @param pageSize 页数
     * @return
     */
    @POST("queryonsale.json")
    Observable<BaseResponse<MySellGoodsListResponse>> loadMySellGoodsList(@Query("userId") String userId, @Query("pageNow") int pageNow, @Query("pageSize") int pageSize);

    /**
     * 我的出售界面的信息刷新商品
     *
     * @param userId
     * @param sellId 出售商品的ID
     * @return
     */
    @POST("refreshsell.json")
    Observable<BaseResponse> refreshsell(@Query("userId") String userId, @Query("sellId") String sellId);

    /**
     * 我的出售界面的信息删除商品
     *
     * @param userId
     * @param sellId 出售商品的ID
     * @return
     */
    @POST("delsell.json")
    Observable<BaseResponse> delsell(@Query("userId") String userId, @Query("sellId") String sellId);

    /**
     * 我的求购下架的商品
     *
     * @param userId
     * @param buyId           商品买卖的ID
     * @param commodityStatus commandityStatus 0下架，1上架
     * @return
     */
    @POST("selloff.json")
    Observable<BaseResponse> selloff(@Query("userId") String userId, @Query("buyId") String buyId, @Query("commodityStatus") String commodityStatus);


    /**
     * 我的求购界面
     *
     * @param userId
     * @param pageNow  当前的页数
     * @param pageSize 页数
     * @return
     */
    @POST("querybuy.json")
    Observable<BaseResponse<MyBuyGoodsListResponse>> loadMyBuyGoodsList(@Query("userId") String userId, @Query("pageNow") int pageNow, @Query("pageSize") int pageSize);

    /**
     * 我的求购  删除商品
     *
     * @param userId
     * @param buyId  商品的id
     * @return
     */
    @POST("delbuy.json")
    Observable<BaseResponse> delbuy(@Query("userId") String userId, @Query("buyId") String buyId);

    /**
     * 我的求购   刷新商品
     *
     * @param userId
     * @param buyId
     * @return
     */
    @POST("refreshbuy.json")
    Observable<BaseResponse> refreshbuy(@Query("userId") String userId, @Query("buyId") String buyId);


    /**     我的出售界面  搜索所有刷新网络请求
     *   非附近
     * @param userId
     * @param type
     * @param place
     * @param school
     * @param commodityCategory
     * @param keyWord
     * @param orderBy
     * @param longitude
     * @param latitude
     * @param pageNow
     * @param pageSize
     * @return
     */
    /**
     * userId=20022, type=1, place=61, school=null, commodityCategory=null, keyWord=,
     * orderBy=0, longitude=null, latitude=null, pageNow=1, pageSize=10
     * <p/>
     * type,place,orderBy,pageNow,pageSize 必须字段
     */
    @POST("querycommodityseach.json")
    Observable<BaseResponse<List<GoodsSearchResultResponse>>> goodsSearch(@Query("userId") String userId,
                                                                          @Query("type") int type,
                                                                          @Query("place") String place,
                                                                          @Query("school") String school,
                                                                          @Query("commodityCategory") String commodityCategory,
                                                                          @Query("keyWord") String keyWord,
                                                                          @Query("orderBy") String orderBy,
                                                                          @Query("longitude") double longitude,
                                                                          @Query("latitude") double latitude,
                                                                          @Query("pageNow") int pageNow,
                                                                          @Query("pageSize") int pageSize);

    /**
     * 我的求购 查询范围的附近搜索
     *
     * @param userId
     * @param type
     * @param place
     * @param commodityCategory
     * @param distance
     * @param longitude
     * @param keyWord
     * @param orderBy
     * @param latitude
     * @param pageNow
     * @param pageSize
     * @return
     */
    @POST("querycommodityseachv7.json")
    Observable<BaseResponse<List<GoodsSearchResultResponse>>> goodsSearchContainDistance(
                                                                                 @Query("userId") String userId,
                                                                                 @Query("type") int type,
                                                                                 @Query("place") String place,
                                                                                 @Query("commodityCategory") String commodityCategory,
                                                                                 @Query("distance") String distance,
                                                                                 @Query("longitude") double longitude,
                                                                                 @Query("keyWord") String keyWord,
                                                                                 @Query("orderBy") String orderBy,
                                                                                 @Query("latitude") double latitude,
                                                                                 @Query("pageNow") int pageNow,
                                                                                 @Query("pageSize") int pageSize);

    @POST("queryselldetails.json")
    Observable<BaseResponse<SellGoodsDetailResponse>> loadSellGoodsDetail(@Query("userId") String userId , @Query("sellId") String sellId);
    @POST("querybuydetails.json")
    Observable<BaseResponse<BuyGoodsDetailResponse>> loadBuyGoodsDetail(@Query("userId") String userId , @Query("buyId") String buyId);

}
