package com.cubemonkey.mgallery.dao;

import java.util.ArrayList;
import java.util.List;


import com.cubemonkey.mgallery.entity.Painting;
import com.cubemonkey.mgallery.utils.PageModel;
import com.cubemonkey.mgallery.utils.XmlDataSource;


/**
* @Description 分页查询油画数据
* @author CubeMonkey Email:2192231938@qq.com
* @version 1.0
* @date 2020年9月7日下午9:40:12
*
 */
public class PaintingDao {
	/**
	 * 分页查询油画数据
	 * @param page 页号
	 * @param rows 每页记录数
	 * @return PageModel分页对象
	 */
	public PageModel pagination(int page, int rows) {
		List<Painting> list = XmlDataSource.getRawData();
		return new PageModel(list, page, rows);
	}
	/**
	 * 按类别分页查询
	 * @param category 分类编号
	 * @param page 页号
	 * @param rows 每页记录数
	 * @return 分页对象
	 */
	public PageModel pagination(int category, int page, int rows) {
		List<Painting> list = XmlDataSource.getRawData();
		List<Painting> categoryList = new ArrayList<Painting>();
		for(Painting painting:list) {
			if (painting.getCategory() == category) {
				categoryList.add(painting);
			}
		}
		return new PageModel(categoryList, page, rows);
	}
	/**
	 * 数据新增
	 * @param painting painting 准备新增的Painting数据
	 */
	public void create(Painting painting) {
		XmlDataSource.append(painting);
	}
	
	/**
	 * 按编号查询油画
	 * @param id 油画编号
	 * @return 油画对象
	 */
	public Painting findById(Integer id) {
		List<Painting> data = XmlDataSource.getRawData();
		Painting painting = null;
		for(Painting p:data) {
			if (id == p.getId()) {
				painting = p;
				break;
			}
		}
		return painting;
	}
	/**
	 * 修改油画信息
	 * @param painting
	 */
	public void update(Painting painting) {
		XmlDataSource.update(painting);
	}
	/**
	 * 删除油画信息
	 * @param id 油画编号
	 */
	public void delete(int id) {
		XmlDataSource.delete(id);
	}
}
