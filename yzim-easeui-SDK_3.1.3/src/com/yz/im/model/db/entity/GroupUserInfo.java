package com.yz.im.model.db.entity;

/**
 * 群组 数据表
 */
public class GroupUserInfo extends BaseBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 用户头像 
	 */
	private String image;
	/**
	 * 用户名称
	 */
	private String name;
	/**
	 * 用户所在单位，学校
	 */
	private String school;
	/**
	 * 学号
	 */
	private String sno;
	/**
	 * 圈子类型
	 */
	private String groupType;
	/**
	 * 用户身份
	 */
	private String idType;
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getSno() {
		return sno;
	}
	public void setSno(String sno) {
		this.sno = sno;
	}
	public String getGroupType() {
		return groupType;
	}
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	
	
	

}
