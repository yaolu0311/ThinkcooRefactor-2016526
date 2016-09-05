package com.yz.im.model.db.entity;

/**
 * 群名片 数据表
 */
public class GroupMemberRemarkBean extends BaseBean{
	
	private int id;//数据库id
	private String groupId;//本地圈子id
	private String easemobGroupId;//环信圈子id
	private String userId;//用户id
	private String nickName;//用户在圈子里的名片
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
	 * @return the groupId
	 */
	public String getGroupId() {
		return groupId;
	}
	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	/**
	 * @return the easemobGroupId
	 */
	public String getEasemobGroupId() {
		return easemobGroupId;
	}
	/**
	 * @param easemobGroupId the easemobGroupId to set
	 */
	public void setEasemobGroupId(String easemobGroupId) {
		this.easemobGroupId = easemobGroupId;
	}
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the nickName
	 */
	public String getNickName() {
		return nickName;
	}
	/**
	 * @param nickName the nickName to set
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	

}
