package com.yz.im.model.entity;

/**
 * Created by cys on 2016/7/7
 * in com.yz.im.model.entity
 */
public class BaseContactBean<T> {

    /**
     * 昵称首字母
     */
    protected String initialLetter;

    /**
     * 头像
     */
    protected String avatar;

    /**
     * 名称
     */
    protected String name;

    /**
     * 选中状态
     */
    protected boolean isChecked;

    protected T obj;

    public String getInitialLetter() {
        return initialLetter;
    }

    public void setInitialLetter(String initialLetter) {
        this.initialLetter = initialLetter;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
