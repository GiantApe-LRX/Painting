package com.cubemonkey.mgallery.utils;
/**
* 数据源类，用于将XML文件解析 为Java对象
* @Description
* @author CubeMonkey Email:2192231938@qq.com
* @version 1.0
* @date 2020年9月7日下午3:47:32
 */

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.cubemonkey.mgallery.entity.Painting;


public class XmlDataSource {
	//通过static静态关键字保证数据的唯一性
	private static List<Painting> data = new ArrayList<Painting>();
	private static String dataFile;
	static {
		dataFile = XmlDataSource.class.getResource("/painting.xml").getPath();
//		System.out.println(dataFile);
		reload();
	}
	/**
	 * 更新data中的数据
	 */
	private static void reload() {
		URLDecoder decoder = new URLDecoder();
		try {
			dataFile = decoder.decode(dataFile, "UTF-8");
			System.out.println(dataFile);
			SAXReader reader = new SAXReader();
			Document document = reader.read(dataFile);
			List<Node> nodes = document.selectNodes("/root/painting");
			data.clear();
			for(Node node:nodes) {
				Element element = (Element)node;
				String id = element.attributeValue("id");
				String pname = element.elementText("pname");
				String category = element.elementText("category");
				String price = element.elementText("price");
				String preview = element.elementText("preview");
				String description = element.elementText("description");
				Painting painting = new Painting(Integer.parseInt(id), pname, Integer.parseInt(category), Integer.parseInt(price), preview, description);
				data.add(painting);
				System.out.println(painting);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static List<Painting> getRawData(){
		return data;
	}
	/**
	 * 追加新的油画数据
	 * @param painting Painting实体对象
	 */
	public static void append(Painting painting) {
		//1.读取XML文档，得到Document对象
		SAXReader reader = new SAXReader();
		Writer writer = null;
		try {
			Document document = reader.read(dataFile);
			Element root = document.getRootElement();
			//2.创建新的Painting节点
			Element p = root.addElement("painting");
			//3.创建painting节点的各个子节点
			p.addAttribute("id", String.valueOf(data.size()+1));
			p.addElement("pname").setText(painting.getPname());;
			p.addElement("category").setText(painting.getCategory().toString());
			p.addElement("price").setText(painting.getPrice().toString());
			p.addElement("preview").setText(painting.getPreview());
			p.addElement("description").setText(painting.getDescription());
			//4.写入XML，完成追加操作
			writer = new OutputStreamWriter(new FileOutputStream(dataFile), "UTF-8");
			document.write(writer);
			System.out.println(dataFile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			reload();
		}
	}
	/**
	 * 更新对应id的XML油画数据
	 * @param painting 要更新的油画数据
	 * @throws IOException
	 */
	public static void update(Painting painting) {
		SAXReader reader = new SAXReader();
		Writer writer = null;
		try {
			Document document = reader.read(dataFile);
			//节点路径[@属性名=属性值]
			List<Node> nodes = document.selectNodes("/root/painting[@id="+painting.getId()+"]");
			if(nodes.size() == 0) {
				throw new RuntimeException("id="+painting.getId()+"编号油画不存在");
			}
			Element p = (Element) nodes.get(0);
		
			p.selectSingleNode("pname").setText(painting.getPname());
			p.selectSingleNode("category").setText(painting.getCategory().toString());
			p.selectSingleNode("price").setText(painting.getPrice().toString());;
			p.selectSingleNode("preview").setText(painting.getPreview());
			p.selectSingleNode("description").setText(painting.getDescription());
			
			writer = new OutputStreamWriter(new FileOutputStream(dataFile), "UTF-8");
			document.write(writer);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			reload();
		}
	}
	/**
	 * 删除指定编号的油画
	 * @param id 油画编号
	 */
	public static void delete(int id) {
		SAXReader reader = new SAXReader();
		Writer writer = null;
		try {
			Document document = reader.read(dataFile);
			System.out.println(dataFile);
			//节点路径[@属性名=属性值]
			List<Node> nodes = document.selectNodes("/root/painting[@id="+id+"]");
			if(nodes.size() == 0) {
				throw new RuntimeException("id="+id+"编号油画不存在");
			}
			Element p = (Element) nodes.get(0);
		
			Element parent = p.getParent();
			parent.remove(p);
			
			writer = new OutputStreamWriter(new FileOutputStream(dataFile), "UTF-8");
			document.write(writer);
			System.out.println("删除成功");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			reload();
		}
	}
	public static void main(String[] args) {
//		new XmlDataSource();
//		System.out.println(dataFile);
//		XmlDataSource.append(new Painting(null, "测试油画", 1, 3800, "/upload/10.jpg", "测试油画描述"));
//		List<Painting> ps = XmlDataSource.getRawData();
//		XmlDataSource.update(new Painting(16, "测试油画", 1, 3800, "/upload/10.jpg", "测试油画描述"));
//		XmlDataSource.delete(16);
	}
}
