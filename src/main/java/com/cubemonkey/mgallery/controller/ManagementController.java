package com.cubemonkey.mgallery.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.cubemonkey.mgallery.entity.Painting;
import com.cubemonkey.mgallery.service.PaintingService;
import com.cubemonkey.mgallery.utils.PageModel;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.xml.internal.bind.v2.model.core.ID;

import jdk.nashorn.internal.runtime.JSONFunctions;

/**
 * Servlet implementation class ManagementController
 */
@WebServlet("/management")
public class ManagementController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private PaintingService paintingService = new PaintingService();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManagementController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		String method = request.getParameter("method");
		if ("list".equals(method)) {
			this.list(request, response);
		}else if ("delete".equals(method)) {
			this.delete(request, response);
		}else if ("showCreatePage".equals(method)) {
			this.showCreatePage(request, response);
		}else if ("create".equals(method)) {
			this.create(request, response);
		}else if ("showUpdatePage".equals(method)) {
			this.showUpdatePage(request, response);
		}else if ("update".equals(method)) {
			this.update(request, response);
		}
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.接受http数据
		String page = request.getParameter("p");
		String rows = request.getParameter("r");
		String category = request.getParameter("c");
		if (page == null) {
			page = "1";
		}
		if (rows == null) {
			rows = "6";
		}
		//2.调用service方法，得到处理结果
		PageModel pageModel = paintingService.pagination(Integer.parseInt(page), Integer.parseInt(rows), category);
		request.setAttribute("pageModel", pageModel);
		//3.请求转发至对应jsp(view)进行数据展现
		request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
	}
	/**
	 * 显示添加油画页面
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void showCreatePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/jsp/create.jsp").forward(request, response);
	}
	/**
	 * 添加油画
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void create(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//文件上传时的数据处理与标准表单不同
		/*
		String pname = request.getParameter("pname");
		System.out.println(pname);
		*/
		//1.初始化FileUpload组件
		FileItemFactory factory = new DiskFileItemFactory();
		/**
		 * FileItemFactory 用于将前端表单的数据转化为一个个FileItem对象
		 * ServletFileUpload 则是为FileUpload组件提供Java Web的HTTP请求解析
		 */
		ServletFileUpload sf = new ServletFileUpload(factory);
		//2.遍历所有的FileItem
		try {
			List<FileItem> formData = sf.parseRequest(request);
			Painting painting = new Painting();
			for(FileItem fi:formData) {
				if (fi.isFormField()) {
					System.out.println("普通输入项:" + fi.getFieldName()+":"+fi.getString("UTF-8"));
					switch (fi.getFieldName()) {
					case "pname":
						painting.setPname(fi.getString("UTF-8"));
						break;
					case "category":
						painting.setCategory(Integer.parseInt(fi.getString("UTF-8")));
						break;
					case "price":
						painting.setPrice(Integer.parseInt(fi.getString("UTF-8")));
						break;
					case "description":
						painting.setDescription(fi.getString("UTF-8"));
						break;
					default:
						break;
					}
				}else {
					System.out.println("文件输入项:"+fi.getFieldName());
					//3.文件保存到服务器目录
					String path = request.getServletContext().getRealPath("/upload");
					System.out.println(path);
					String fileName = UUID.randomUUID().toString();
					String suffix = fi.getName().substring(fi.getName().lastIndexOf("."), fi.getName().length());
					//3.文件保存到服务器目录
					fi.write(new File(path, fileName+suffix));
					painting.setPreview("/upload/" + fileName + suffix);
				}
			}
			paintingService.create(painting);//新增功能
			response.sendRedirect("/management?method=list");//返回列表项
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}	
	/**
	 * 显示更新页面
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void showUpdatePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		Painting painting = paintingService.findById(Integer.parseInt(id));
		request.setAttribute("painting", painting);
		request.getRequestDispatcher("/WEB-INF/jsp/update.jsp").forward(request, response);
	}
	
	/**
	 * 修改油画信息
	 * @param request
	 * @param response
	 */
	private void update(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		//1.初始化FileUpload组件
		FileItemFactory factory = new DiskFileItemFactory();
		/**
		 * FileItemFactory 用于将前端表单的数据转化为一个个FileItem对象
		 * ServletFileUpload 则是为FileUpload组件提供Java Web的HTTP请求解析
		 */
		ServletFileUpload sf = new ServletFileUpload(factory);
		int isPreviewModified = 0;
		//2.遍历所有的FileItem
		try {
			List<FileItem> formData = sf.parseRequest(request);
			Painting painting = new Painting();
			for(FileItem fi:formData) {
				if (fi.isFormField()) {
					System.out.println("普通输入项:" + fi.getFieldName()+":"+fi.getString("UTF-8"));
					switch (fi.getFieldName()) {
					case "id":
						painting.setId(Integer.parseInt(fi.getString("UTF-8")));
						break;
					case "isPreviewModified":
						isPreviewModified = Integer.parseInt(fi.getString("UTF-8"));
						break;
					case "pname":
						painting.setPname(fi.getString("UTF-8"));
						break;
					case "category":
						painting.setCategory(Integer.parseInt(fi.getString("UTF-8")));
						break;
					case "price":
						painting.setPrice(Integer.parseInt(fi.getString("UTF-8")));
						break;
					case "description":
						painting.setDescription(fi.getString("UTF-8"));
						break;
					default:
						break;
					}
				}else {
					if (fi.getName() != null && fi.getName().length() > 3) {						
						System.out.println("文件输入项:"+fi.getFieldName()+":"+fi.getName());
						//3.文件保存到服务器目录
						String path = request.getServletContext().getRealPath("/upload");
						System.out.println(path);
						String fileName = UUID.randomUUID().toString();
						String suffix = fi.getName().substring(fi.getName().lastIndexOf("."), fi.getName().length());
						//3.文件保存到服务器目录
						fi.write(new File(path, fileName+suffix));
						painting.setPreview("/upload/" + fileName + suffix);
					}
				}
			}
			paintingService.update(painting, isPreviewModified);//新增功能
			response.sendRedirect("/management?method=list");//返回列表项
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}

	/**
	 * 删除油画信息
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		try {			
			paintingService.delete(Integer.parseInt(id));
			response.getWriter().println("{\"result\":\"ok\"}");
		} catch (Exception e) {
			response.getWriter().println("{\"result\":\""+e.getMessage()+"\"}");
		}
	}
}
