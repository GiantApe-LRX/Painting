package com.cubemonkey.mgallery.entity;


/**
* @Description 油画对象
* @author CubeMonkey Email:2192231938@qq.com
* @version 1.0
* @date 2020年9月7日下午9:41:08
*
 */
public class Painting{

	private Integer id;//油画编号
	private String pname;//名称
	private Integer category;//分类 1-现实主义 2-抽象主义
	private Integer price;//价格
	private String preview;//油画图片地址
	private String description;//描述
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the pname
	 */
	public String getPname() {
		return pname;
	}
	/**
	 * @param pname the pname to set
	 */
	public void setPname(String pname) {
		this.pname = pname;
	}
	/**
	 * @return the category
	 */
	public Integer getCategory() {
		return category;
	}
	/**
	 * @param categroy the category to set
	 */
	public void setCategory(Integer category) {
		this.category = category;
	}
	/**
	 * @return the price
	 */
	public Integer getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(Integer price) {
		this.price = price;
	}
	/**
	 * @return the preview
	 */
	public String getPreview() {
		return preview;
	}
	/**
	 * @param preview the preview to set
	 */
	public void setPreview(String preview) {
		this.preview = preview;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	public Painting(Integer id, String pname, Integer category, Integer price, String preview, String description) {
		super();
		this.id = id;
		this.pname = pname;
		this.category = category;
		this.price = price;
		this.preview = preview;
		this.description = description;
	}
	public Painting() {
		super();
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Painting [id=" + id + ", pname=" + pname + ", categroy=" + category + ", price=" + price + ", preview="
				+ preview + ", description=" + description + "]";
	}
	
	
}
