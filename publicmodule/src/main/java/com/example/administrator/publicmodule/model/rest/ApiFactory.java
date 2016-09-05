package com.example.administrator.publicmodule.model.rest;



/**
 * Created by Robert.yao on 2016/3/25.
 */
public interface ApiFactory {

      /**
       * 以下是各模块节点api base url 对应的key
       */
      String LOGIN_PREFIX = "yingzi-login";//登录 ->这个key是为了api统一而自创的

      String QECHART_PREFIX = "yingzi-chat-mobile";//快信节点
      String COURSE_PREFIX = "yingzi-schedule-mobile";//软课表节点
      String PERSONAL_PREFIX = "yingzi-personal-mobile";//自信节点
      String TRADE_PREFIX = "yingzi-trade-mobile";//自贸区节点
      String CALENDAR_PREFIX = "yingzi-calendar-mobile";//勤务表节点
      String GETJOB_PREFIX = "yingzi-hr-mobile";//找工作
      String BAIDUAPI = "baidu";

      int cacheSize();
      void putWebNode(String webNodeKey, String webNodeUrl);
      int getWebNodeSize();
      <T> T createApiByClass(Class<T> accountApiClass, String webNodeKey);
}
