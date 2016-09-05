package com.thinkcoo.mobile.model.rest.apis;

import com.example.administrator.publicmodule.entity.BaseResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.CheckVcodeResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.LoginResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.VcodeResponse;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/5/19.
 */
public interface AccountApi {

    @POST("applogin.json")
    Observable<BaseResponse<LoginResponse>> login(@Query("phoneNumber") String phoneNumber, @Query("password") String passWord);
    @POST("appgetcode.json")
    Observable<BaseResponse<VcodeResponse>> loadVcode(@Query("phoneNumber") String phoneNumber, @Query("isValidate") String isValidate, @Query("moduleType") String moduleType);
    @POST("appinfovalidate.json")
    Observable<BaseResponse> register(@Query("phoneNumber") String phoneNumber, @Query("checkCode") String checkCode, @Query("cert") String moduleType, @Query("isValidate")String isValidate);
    @POST("appreg.json")
    Observable<BaseResponse> completeRegister(@Query("phoneNumber") String phoneNumber, @Query("password") String password, @Query("areaCode") String areaCode, @Query("realName") String realName);
    @POST("appinfovalidate.json")
    Observable<BaseResponse<CheckVcodeResponse>> forGetPassword(@Query("phoneNumber") String phoneNumber, @Query("checkCode") String checkCode, @Query("cert") String moduleType, @Query("isValidate")String isValidate);
    @POST("appresetpwd.json")
    Observable<BaseResponse> setupPassword(@Query("cert") String cert,@Query("userId") String userId, @Query("password") String newPassword);
    @POST("updatenotepwd.json")
    Observable<BaseResponse> modifyPassword (@Query("userId") String userId, @Query("pwd") String pwd, @Query("newPwd") String newPwd);

    @POST("updatenoteusername.json")
    Observable<BaseResponse> changePhoneNumber(@Query("userId") String userId,@Query("newUserName") String newPhoneNumber, @Query("password") String password,@Query("checkCode") String vcodeCode, @Query("cert") String cert, @Query("oldUserName") String oldPhoneNumbe);

}
