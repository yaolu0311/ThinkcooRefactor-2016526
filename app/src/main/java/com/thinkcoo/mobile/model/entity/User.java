package com.thinkcoo.mobile.model.entity;

import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.thinkcoo.mobile.model.entity.nullentities.NullUser;
import com.thinkcoo.mobile.model.entity.nullentities.Nullable;
import com.thinkcoo.mobile.model.entity.serverresponse.LoginResponse;

import java.util.ArrayList;
import java.util.List;

public class User implements Nullable ,Cloneable {

    public static  final String TAG = "User";
    public static final User NULL_USER = new NullUser();

    public static User fromServerResponse(LoginResponse loginResponse){

        User user = new User();

        user.setCert(loginResponse.getCert());
        user.setName(loginResponse.getName());
        user.setUserId(loginResponse.getUserId());
        user.setPortraitURL(loginResponse.getPortraitURL());
        user.setType(loginResponse.getType());

        fillWebNodeList(user , loginResponse);

        return user;
    }

    private static void fillWebNodeList(User user, LoginResponse loginResponse) {

        List<WebNode> userWebNodeList = user.getUserWebNodeList();

        if (null == userWebNodeList){
            userWebNodeList = new ArrayList<>();
        }else {
            userWebNodeList.clear();
        }
        List<LoginResponse.ModulesBean> moduleBeanList = loginResponse.getModules();
        if (null == moduleBeanList || moduleBeanList.size() == 0){
            ThinkcooLog.e(TAG,"=== 服务器没有返回任何的节点信息 ===");
            return;
        }
        for (LoginResponse.ModulesBean module: moduleBeanList) {
            userWebNodeList.add(WebNode.fromServerResponse(module));
        }
    }

    private String userId;
    private String cert;
    private String name;
    private String signature;
    private String sex;
    private String portraitURL;
    private int type;

    private UserSpace userSpace;
    private List<WebNode> userWebNodeList = new ArrayList<>();

    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getPortraitURL() {
        return portraitURL;
    }
    public void setPortraitURL(String portraitURL) {
        this.portraitURL = portraitURL;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCert() {
        return cert;
    }
    public void setCert(String cert) {
        this.cert = cert;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getSignature() {
        return signature;
    }
    public void setSignature(String signature) {
        this.signature = signature;
    }
    public void setUserSpace(UserSpace userSpace) {
        this.userSpace = userSpace;
    }
    public UserSpace getUserSpace() {
        return userSpace;
    }
    public List<WebNode> getUserWebNodeList() {
        return userWebNodeList;
    }
    public void setUserWebNodeList(List<WebNode> userWebNodeList) {
        this.userWebNodeList = userWebNodeList;
    }
    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public User clone() throws CloneNotSupportedException {

        User user = new User();

        user.setCert(this.cert);
        user.setName(this.name);
        user.setPortraitURL(this.portraitURL);
        user.setSex(this.sex);
        user.setSignature(this.signature);
        user.setUserSpace(null == userSpace ?  null :this.userSpace.clone());
        user.setUserId(this.userId);
        user.setType(this.type);
        user.setUserWebNodeList(cloneWebNodeList());

        return user;
    }

    private List<WebNode> cloneWebNodeList() {
        List<WebNode> cloneList = new ArrayList<>();
        if (null == this.userWebNodeList || 0 == this.userWebNodeList.size()){
            return cloneList;
        }
        for (WebNode webNode: userWebNodeList) {
            cloneList.add(webNode.clone());
        }
        return cloneList;
    }
}
