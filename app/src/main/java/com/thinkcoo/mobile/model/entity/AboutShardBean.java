package com.thinkcoo.mobile.model.entity;


import java.io.Serializable;

/**
 * <p>Title: AboutShardBean.java</p>
 * <p>Description: TODO(用一句话描述该文件做什么)</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: 西安影子</p>
 * <p>CreateTime: 2015-11-17 上午9:43:40</p>
 * 
 * @author 自己填写
 * @version V1.0
 * @since JDK1.7
 * @CheckItem 自己填写
 */
public class AboutShardBean implements Serializable {

	/** 自己填写 */
	private static final long serialVersionUID = 1L;
	
	private int imagepath;
	private String imageName;
	/**
	 * @return the imagepath
	 */
	public int getImagepath() {
		return imagepath;
	}
	/**
	 * @param imagepath the imagepath to set
	 */
	public void setImagepath(int imagepath) {
		this.imagepath = imagepath;
	}
	/**
	 * @return the imageName
	 */
	public String getImageName() {
		return imageName;
	}
	/**
	 * @param imageName the imageName to set
	 */
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	/**
	 * @param imagepath
	 * @param imageName
	 */
	public AboutShardBean(int imagepath, String imageName) {
		super();
		this.imagepath = imagepath;
		this.imageName = imageName;
	}
	
	
	
	

}
