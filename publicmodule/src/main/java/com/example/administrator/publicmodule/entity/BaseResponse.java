package com.example.administrator.publicmodule.entity;


public class BaseResponse<T> extends Response{

	/**
	 * 数据
	 */
	private T data;

	/**
	 * 
	 * Description: 数据
	 * @return
	 */
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}

}