package com.example.administrator.publicmodule.util;

public class EasemobConstantsUtils {

	//用户偏移量
	public static long USER_OFFSET = 100000;
	//圈子偏移量
	public static long CIRCLE_OFFSET = 1000000000;

	public EasemobConstantsUtils() {
	}

	/**
	 * 通过系统用户ID获取环信用户名
	 * @param sysUserId
	 * @return 环信用户名
	 */
	public  long getEasemobUserName(long sysUserId)
	{
		return USER_OFFSET + sysUserId;
	}

	/**
	 * 通过环信用户名获取系统用户ID
	 * @param easemobUserName
	 * @return 系统用户ID
	 */
	public  long getSysUserId(long easemobUserName)
	{
		return easemobUserName - USER_OFFSET;
	}
	/**
	 * 通过系统圈子ID获取环信圈子名
	 * @param sysCircleId
	 * @return 环信圈子名
	 */
	public  long getEasemobCircleName(long sysCircleId)
	{
		return CIRCLE_OFFSET + sysCircleId;
	}
	/**
	 * 通过环信圈子名获取系统圈子ID
	 * @param easemobCircleName
	 * @return 系统圈子ID
	 */
	public  long getSysCircleId(long easemobCircleName)
	{
		return easemobCircleName - CIRCLE_OFFSET;
	}
		
}
