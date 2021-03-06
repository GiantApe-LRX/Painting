﻿<%@page contentType = "text/html; charset=utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Gallery</title>
	<link rel="stylesheet" type="text/css" href="css\common.css">
	<script type="text/javascript" src="js\js1.js"></script>
</head>
<body>
	<c:if test="${param.c != null }">
		<c:set var="categoryParm" value="&c=${param.c }"></c:set>
	</c:if>
	<c:if test="${param.c == null }">
		<c:set var="categoryParm" value=""></c:set>
	</c:if>
	<div class="header">
		<div class="logo">
			<img src="image\logo.png">
		</div>
		<div class="menu"   onclick="show_menu()" onmouseleave="show_menu1()">
			 <div class="menu_title" ><a href="###">内容分类</a></div>
			 <ul id="title">
				<li><a href="/page?c=1">现实主义</a></li>
				<li><a href="/page?c=2">抽象主义</a></li>
			 </ul>
		</div>
		<div class="auth">
			<ul>
				<li><a href="#">登录</a></li>
				<li><a href="#">注册</a></li>
			</ul>
		</div>
	</div>
	<div class="content">
	  <div class="banner">
  		<img src="image/welcome.png" class="banner-img">
	  </div>
	  <div class="img-content">
		<ul>
			<c:forEach items="${pageModel.pageData}" var="painting">
			<li>
				<img src="${painting.preview }" class="img-li">
				<div class="info">
					<h3>${painting.pname }</h3>
					<p>
						${painting.description }
					</p>
					<div class="img-btn">
						<div class="price"><fmt:formatNumber pattern="￥0.00">${painting.price }</fmt:formatNumber></div>
						<div class="btn">
							<a href="#" class="cart">
							   <img src="image/cart.svg">
							 </a>
						</div>
					</div>
				</div>
			</li>
			</c:forEach>
		</ul>
	  </div>
	  <div class="page-nav">
		<ul>
			<li><a href="/page?p=1&r=6${categoryParm}">首页</a></li>
			<li><a href="/page?p=${pageModel.hasPreviousPage?pageModel.page-1:1 }${categoryParm}">上一页</a></li>
			<c:forEach begin="1" end="${pageModel.totalPage}" var="pno" step="1">				
				<li>
					<span ${pno == pageModel.page?"class='first-page'":""}>
						<a href="/page?p=${pno }${categoryParm}">
							${pno }
						</a>
					</span>
				</li>
			</c:forEach>
			<li><a href="/page?p=${pageModel.hasNextPage?pageModel.page+1:pageModel.totalPage }${categoryParm}">下一页</a></li>
			<li><a href="/page?p=${pageModel.totalPage}${categoryParm}">尾页</a></li>
		</ul>
	  </div>
	</div>
	<div class="footer">
		<p><span>M-GALLARY</span>©2020 POWERED BY IMOOC.INC<a href="/management.html" style="color:red">管理中心</a></p>
	</div>
</body>
</html>