package com.thinkcoo.mobile.model.rest.apis;

import com.example.administrator.publicmodule.entity.BaseResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.CooperationCollectionResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.CoopertionDetailResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.ProjectListResponse;

import java.util.List;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * author ：ml on 2016/7/20
 * 找合作
 */
public interface CooperationApi {
    /**
     * @param userId
     * @param keyWord      关键字
     * @param projectCycle 项目周期
     * @param pageNow      当前的页数
     * @param pageSize     页数
     * @return 和搜索界面界面使用同一个接口  搜索结果根据String keyword,String projectCycle,int num
     */
    @POST("queryprojectinfo.json")
    Observable<BaseResponse<List<ProjectListResponse>>> queryprojectinfo(@Query("userId") String userId, @Query("keyWord") String keyWord, @Query("projectCycle") String projectCycle, @Query("pageNow") String pageNow, @Query("pageSize") String pageSize);


    /**
     * 收藏列表
     *
     * @param userId
     * @param pageNow
     * @param pageSize
     * @return
     */
    @POST("querycollect.json")
    Observable<BaseResponse<List<CooperationCollectionResponse>>> querycollect(@Query("userId") String userId, @Query("pageNow") String pageNow, @Query("pageSize") String pageSize);

    /**
     * 查看项目详细信息
     *
     * @param userId
     * @param projectId 项目对应的ID
     * @return
     */
    @POST("projectdetails.json")
    Observable<BaseResponse<CoopertionDetailResponse>> projectdetails(@Query("userId") String userId, @Query("projectId") String projectId);

    /**
     * 添加项目收藏
     *
     * @param userId
     * @param projectId 项目对应的ID
     * @return
     */
    @POST("addcollect.json")
    Observable<BaseResponse> addcollect(@Query("userId") String userId, @Query("projectId") String projectId);

    /**
     * 取消收藏收藏
     *
     * @param userId
     * @param projectId 项目对应的ID
     * @return
     */
    @POST("delcollect.json")
    Observable<BaseResponse> delcollect(@Query("userId") String userId, @Query("projectId") String projectId);

    /**
     * 获取公司信息      GetJobApi
     * //http://www.thinkcoo.com/yingzi-mobile/hr/companydetails.json
     * @POST("/companydetails.json")
     * Observable<BaseResponse<List<CompanyDetailsResponse>>> companydetails(@Query("companyId") String companyId);
     *
     **/


    /**、有问题的接口
     * @POST取消申请
     * @POST文件上传成功后，返给服务器的数据
     * @POST 我的申请
     *  @POST 删除申请
     *  @post 上传文件
     */


}
