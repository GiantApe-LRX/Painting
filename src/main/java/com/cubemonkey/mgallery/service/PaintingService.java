package com.cubemonkey.mgallery.service;

import java.util.List;

import com.cubemonkey.mgallery.dao.PaintingDao;
import com.cubemonkey.mgallery.entity.Painting;
import com.cubemonkey.mgallery.utils.PageModel;
import com.cubemonkey.mgallery.utils.XmlDataSource;
import com.sun.media.jfxmedia.control.VideoDataBuffer;
import com.sun.org.apache.bcel.internal.generic.NEW;

/**
* @Description PaintingService 油画服务类
* @author CubeMonkey Email:2192231938@qq.com
* @version 1.0
* @date 2020年9月7日下午9:42:02
*
*/
public class PaintingService {
	private PaintingDao paintingDao = new PaintingDao();
	/**
	 * pagination数据分页查询
	 * @param pageNo 页号 
	 * @param rows 每页记录数
	 * @param category 可选参数，分类编号
	 * @return 分页对象
	 */
	public PageModel pagination(int page, int rows, String...category) {
		if (rows == 0) {
			throw new RuntimeException("无效的rows参数");
		}
		if (category.length == 0 || category[0] == null) {			
			return paintingDao.pagination(page, rows);
		}else {
			return paintingDao.pagination(Integer.parseInt(category[0]), page, rows);
		}
	}
	/**
	 * 新增油画
	 * @param painting 准备新增的Painting数据
	 */
	public void create(Painting painting) {
		paintingDao.create(painting);
	}
	
	/**
	 * 按编号查询油画
	 * @param id 油画编号
	 * @return 油画对象
	 */
	public Painting findById(Integer id) {
		Painting painting = paintingDao.findById(id);
		if (painting == null) {
			throw new RuntimeException("[id="+id+"]油画不存在");
		}
		return painting;
	}
	public static void main(String[] args) {
		PaintingService paintingService = new PaintingService();
		PageModel pageModel = paintingService.pagination(2, 6);
		List<Painting> paintings = pageModel.getPageData();
		for(Painting painting: paintings) {
			System.out.println(painting.getPname());
		}
		System.out.println(pageModel.getPageStartRow()+":"+pageModel.getPageEndRow());
	}
	/**
	 * 修改油画信息
	 * @param painting
	 */
	public void update(Painting newPainting, int isPriviewModified) {
		Painting oldPainting = findById(newPainting.getId());
		oldPainting.setPname(newPainting.getPname());
		oldPainting.setCategory(newPainting.getCategory());
		oldPainting.setPrice(newPainting.getPrice());
		oldPainting.setDescription(newPainting.getDescription());
		if (isPriviewModified == 1) {
			oldPainting.setPreview(newPainting.getPreview());
		}
		paintingDao.update(oldPainting);
	}
	/**
	 * 删除油画信息
	 * @param id 油画编号
	 */
	public void delete(int id) {
		paintingDao.delete(id);
	}
}
