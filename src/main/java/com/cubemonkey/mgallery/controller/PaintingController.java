package com.cubemonkey.mgallery.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cubemonkey.mgallery.entity.Painting;
import com.cubemonkey.mgallery.service.PaintingService;
import com.cubemonkey.mgallery.utils.PageModel;
import com.sun.prism.paint.Paint;

/**
 * Servlet implementation class PaintingController
 */
@WebServlet("/page")
public class PaintingController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PaintingService paintingService = new PaintingService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PaintingController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		response.setContentType("text/html; charset=utf-8");
		//3.请求转发至对应jsp(view)进行数据展现
		request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(request, response);;
	}

}
