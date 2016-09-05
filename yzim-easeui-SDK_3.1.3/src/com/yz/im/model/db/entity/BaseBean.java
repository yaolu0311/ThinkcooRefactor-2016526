package com.yz.im.model.db.entity;

import java.io.Serializable;
/**
 * 
 * <p>Title: BaseBean.java</p>
 * <p>基类</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: 西安影子</p>
 * <p>CreateTime: 2015-5-20 下午1:42:26</p>
 * 
 * @author liuzm
 * @version V1.0
 * @since JDK1.7
 * @CheckItem 自己填写
 */
public class BaseBean implements Serializable{

	/** 
	* @Fields serialVersionUID : TODO
	*/
	private static final long serialVersionUID = 1L;
	
	//
	/** 
	* @Fields 以下6个字段为数据库预留字段，如果在某个实体类启用，请注明用途和版本号
	*/
	private String reserve01;
	private String reserve02;
	private String reserve03;
	private String reserve04;
	private String reserve05;
	private String reserve06;
	public String getReserve01() {
		return reserve01;
	}
	public void setReserve01(String reserve01) {
		this.reserve01 = reserve01;
	}
	public String getReserve02() {
		return reserve02;
	}
	public void setReserve02(String reserve02) {
		this.reserve02 = reserve02;
	}
	public String getReserve03() {
		return reserve03;
	}
	public void setReserve03(String reserve03) {
		this.reserve03 = reserve03;
	}
	public String getReserve04() {
		return reserve04;
	}
	public void setReserve04(String reserve04) {
		this.reserve04 = reserve04;
	}
	public String getReserve05() {
		return reserve05;
	}
	public void setReserve05(String reserve05) {
		this.reserve05 = reserve05;
	}
	public String getReserve06() {
		return reserve06;
	}
	public void setReserve06(String reserve06) {
		this.reserve06 = reserve06;
	}
	
}
