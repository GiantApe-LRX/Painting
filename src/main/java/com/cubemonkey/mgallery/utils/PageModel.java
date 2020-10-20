package com.cubemonkey.mgallery.utils;

import java.util.ArrayList;
import java.util.List;

import com.sun.prism.paint.Paint;

import javafx.scene.chart.PieChart.Data;

/**
* @Description 分页模型对象
* @author CubeMonkey Email:2192231938@qq.com
* @version 
* @date 2020年9月7日下午9:40:42
*
*/
public class PageModel {
	private int page;//当前页号
	private int totalPage;//总页数
	private int rows;//每页记录数
	private int totalRows;//总记录数
	private int pageStartRow;//当前页从n行开始
	private int pageEndRow;//当前页到n行结束
	private boolean hasNextPage;//是否存在下一页
	private boolean hasPreviousPage;//是否存在上一页
	private List pageData;//当前页面数据
	
	public PageModel() {
		
	}
	/**
	 * 初始化PageModel对象，计算分页属性
	 * @param data 原始数据集合
	 * @param page 页号
	 * @param rows 每页记录数
	 */
	public PageModel(List data, int page, int rows) {
		this();
		this.page = page;
		this.rows = rows;
		this.totalRows = data.size();
		//总页数计算规则：总行数/每页记录数，能整除页数取整，不能整除向上取整
		//例如：18 / 6 = 3 | 20 / 6 ≈ 3.33 向上取整 = 4；
		this.totalPage = new Double(Math.ceil(this.totalRows*1.0 / this.rows)).intValue();
		this.pageStartRow = (this.page-1) * this.rows;
		this.pageEndRow = this.page * this.rows;
		if (this.pageEndRow > this.totalRows) {
			this.pageEndRow = this.totalRows;
		}
		this.pageData = data.subList(this.pageStartRow, this.pageEndRow);
		if (page < this.totalPage) {
			this.hasNextPage = true;
		}else {
			this.hasNextPage = false;
		}
		if (page > 1) {
			this.hasPreviousPage = true;
		}else {
			this.hasPreviousPage = false;
		}
	}
	/**
	 * @return the page
	 */
	public int getPage() {
		return page;
	}
	/**
	 * @param page the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}
	/**
	 * @return the totalPage
	 */
	public int getTotalPage() {
		return totalPage;
	}
	/**
	 * @param totalPage the totalPage to set
	 */
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	/**
	 * @return the rows
	 */
	public int getRows() {
		return rows;
	}
	/**
	 * @param rows the rows to set
	 */
	public void setRows(int rows) {
		this.rows = rows;
	}
	/**
	 * @return the totalRows
	 */
	public int getTotalRows() {
		return totalRows;
	}
	/**
	 * @param totalRows the totalRows to set
	 */
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}
	/**
	 * @return the pageStartRow
	 */
	public int getPageStartRow() {
		return pageStartRow;
	}
	/**
	 * @param pageStartRow the pageStartRow to set
	 */
	public void setPageStartRow(int pageStartRow) {
		this.pageStartRow = pageStartRow;
	}
	/**
	 * @return the pageEndRow
	 */
	public int getPageEndRow() {
		return pageEndRow;
	}
	/**
	 * @param pageEndRow the pageEndRow to set
	 */
	public void setPageEndRow(int pageEndRow) {
		this.pageEndRow = pageEndRow;
	}
	/**
	 * @return the hasNextPage
	 */
	public boolean isHasNextPage() {
		return hasNextPage;
	}
	/**
	 * @param hasNextPage the hasNextPage to set
	 */
	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}
	/**
	 * @return the hasPreviousPage
	 */
	public boolean isHasPreviousPage() {
		return hasPreviousPage;
	}
	/**
	 * @param hasPreviousPage the hasPreviousPage to set
	 */
	public void setHasPreviousPage(boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}
	/**
	 * @return the pageData
	 */
	public List getPageData() {
		return pageData;
	}
	/**
	 * @param pageData the pageData to set
	 */
	public void setPageData(List pageData) {
		this.pageData = pageData;
	}
	public static void main(String[] args) {
		List sample = new ArrayList();
		for(int i = 1; i <= 100; i++) {
			sample.add(i);
		}
		PageModel pageModel = new PageModel(sample, 6, 8);
		System.out.println(pageModel.getPageData());
		System.out.println(pageModel.getPageStartRow()+":"+pageModel.getPageEndRow());
	}
}
