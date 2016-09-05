package com.thinkcoo.mobile.model.entity;

import com.thinkcoo.mobile.model.entity.nullentities.Nullable;
import com.thinkcoo.mobile.model.entity.serverresponse.VcodeResponse;

/**
 * Created by Administrator on 2016/5/19.
 */
public class Vcode implements Nullable{

    public static Vcode fromServerResponse(VcodeResponse vcodeReponse){
        Vcode vcode = new Vcode();
        vcode.setContent(vcodeReponse.getCode());
        vcode.setCert(vcodeReponse.getEncryptStr());
        return  vcode;
    }

    /**
     * 验证码内容，4位数字短号组成
     */
    private String content;
    /**
     * 验证码Token
     */
    private String cert;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCert() {
        return cert;
    }

    public void setCert(String cert) {
        this.cert = cert;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
