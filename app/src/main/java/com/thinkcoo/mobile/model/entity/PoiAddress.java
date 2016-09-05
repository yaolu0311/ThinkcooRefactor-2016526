package com.thinkcoo.mobile.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * <p>Title: PoiAddress.java</p>
 * <p>百度poi实体</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: 西安影子</p>
 * <p>CreateTime: 2015-11-19 上午12:04:39</p>
 * 
 * @author 自己填写
 * @version V1.0
 * @since JDK1.7
 * @CheckItem 自己填写
 */
public class PoiAddress {
	
	/** 自己填写 */
	private static final long serialVersionUID = 1L;
	
	private String status;//成功返回0
	private String message;//成功返回ok,失败返回错误说明
	private String total;//检索总数
	private List<Results> results;//结果信息

	public boolean isSuccess(){
		return "0".equals(status);
	}
	/**
	 * @return the results
	 */
	public List<Results> getResults() {
		return results;
	}

	/**
	 * @param results
	 *            the results to set
	 */
	public void setResults(List<Results> results) {
		this.results = results;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the total
	 */
	public String getTotal() {
		return total;
	}

	/**
	 * @param total
	 *            the total to set
	 */
	public void setTotal(String total) {
		this.total = total;
	}

	/**
	 * 百度place web api 结果集合
	 * @return the results
	 */
	public class Results {
		private String name;//poi名称 
		private String address;//poi地址信息 
		private String street_id;//街道id
		private String telephone;//poi电话信息 
		private String uid;//poi的唯一标示 
		private Location location;//poi经纬度坐标
		
		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param name
		 *            the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * @return the address
		 */
		public String getAddress() {
			return address;
		}

		/**
		 * @param address
		 *            the address to set
		 */
		public void setAddress(String address) {
			this.address = address;
		}

		/**
		 * @return the street_id
		 */
		public String getStreet_id() {
			return street_id;
		}

		/**
		 * @param street_id
		 *            the street_id to set
		 */
		public void setStreet_id(String street_id) {
			this.street_id = street_id;
		}

		/**
		 * @return the telephone
		 */
		public String getTelephone() {
			return telephone;
		}

		/**
		 * @param telephone
		 *            the telephone to set
		 */
		public void setTelephone(String telephone) {
			this.telephone = telephone;
		}

		/**
		 * @return the uid
		 */
		public String getUid() {
			return uid;
		}

		/**
		 * @param uid
		 *            the uid to set
		 */
		public void setUid(String uid) {
			this.uid = uid;
		}

		/**
		 * @return the location
		 */
		public Location getLocation() {
			return location;
		}

		/**
		 * @param location
		 *            the location to set
		 */
		public void setLocation(Location location) {
			this.location = location;
		}

	}

	/**
	 * 
	 * <p>Title: PoiAddress.java</p>
	 * <p>经纬度信息</p>
	 * <p>Copyright: Copyright (c) 2011</p>
	 * <p>Company: 西安影子</p>
	 * <p>CreateTime: 2015-12-8 下午3:04:14</p>
	 * 
	 * @author 自己填写
	 * @version V1.0
	 * @since JDK1.7
	 * @CheckItem 自己填写
	 */
	public class Location implements Serializable{
		/** 自己填写 */
		private static final long serialVersionUID = 1L;
		private String lat;
		private String lng;

		/**
		 * @return the lat
		 */
		public String getLat() {
			return lat;
		}

		/**
		 * @param lat
		 *            the lat to set
		 */
		public void setLat(String lat) {
			this.lat = lat;
		}

		/**
		 * @return the lng
		 */
		public String getLng() {
			return lng;
		}

		/**
		 * @param lng
		 *            the lng to set
		 */
		public void setLng(String lng) {
			this.lng = lng;
		}

	}
}
