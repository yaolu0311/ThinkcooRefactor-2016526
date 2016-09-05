package com.yz.im.model.db.entity;

import java.io.Serializable;

/**
 * 系统设置 数据表
 */
public class SystemSetingFlage extends BaseBean implements Serializable {

	/** 自己填写 */
	private static final long serialVersionUID = 1L;
	
	private int id;//数据库id
	private String isMessageRemind;//新消息提醒  0
	private String isReceveStranger;//允许陌生人发消息   1
	private String isVerify;//加我为朋友时需要验证 ："0"不需要验证，"1"需要验证   0
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the isMessageRemind
	 */
	public String getIsMessageRemind() {
		return isMessageRemind;
	}
	/**
	 * @param isMessageRemind the isMessageRemind to set
	 */
	public void setIsMessageRemind(String isMessageRemind) {
		this.isMessageRemind = isMessageRemind;
	}
	/**
	 * @return the isReceveStranger
	 */
	public String getIsReceveStranger() {
		return isReceveStranger;
	}
	/**
	 * @param isReceveStranger the isReceveStranger to set
	 */
	public void setIsReceveStranger(String isReceveStranger) {
		this.isReceveStranger = isReceveStranger;
	}
	/**
	 * @return the isVerify
	 */
	public String getIsVerify() {
		return isVerify;
	}
	/**
	 * @param isVerify the isVerify to set
	 */
	public void setIsVerify(String isVerify) {
		this.isVerify = isVerify;
	}
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
}
