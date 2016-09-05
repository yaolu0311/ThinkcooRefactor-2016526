package com.example.administrator.publicmodule.entity;




public class PageBean  {

	/** 自己填写 */
	private static final long serialVersionUID = 1L;
	/**总页码*/
	private int pageCount;
	/**当前页码*/
	private int pageNo;
	/**每页条数*/
	private int pageSize;
	/**总条数*/
	private int totalCount;
	
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
}
