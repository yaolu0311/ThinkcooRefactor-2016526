package com.example.administrator.publicmodule.entity;

/**
 * Created by Administrator on 2016/6/22.
 */
public class Response {

    protected static final int RESPONSE_OK = 1 ;

    /**
     * 响应状态码
     */
    private int status;
    /**
     * 异常提示信息
     */
    private String msg;

    /**
     * 页面对象
     */
    private PageBean page;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public boolean isSuccess(){
        return status == RESPONSE_OK;
    }
}
