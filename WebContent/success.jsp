<%@page import="com.beans.Product"%>
<%@page import="java.util.ArrayList"%>
<%@page import="javax.swing.JOptionPane"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous"> 
<link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
	<!-- 購物車設定 -->
	<c:set var="x" value="0"></c:set>
	<c:forEach items="${cartlist }" var="i">
		<c:set var="x" value="${x+1 }"></c:set>
	</c:forEach>
	<!-- 導覽列 -->
	<header>
		<h1>
			Tiazon
		</h1>
		<nav>
			<ul>
				<li><a href="Controller?page=index">Home</a></li>
				<c:choose>
					<c:when test="${session == null}">
						<li><a href="Controller?page=login">Login</a></li>
						<li><a href="Controller?page=sign-up">Sign-up</a></li>
					</c:when>
					<c:when test="${session != null}">
						<li><a href="Controller?page=logout" style="color: #F24638;">Logout</a></li>
						<li><a href="#">My Account(<c:out value="${username }"></c:out>)</a></li>
					</c:when>
				</c:choose>
				<li><a href="Controller?page=showcart">cart(<c:out value="${x}"/>)</a></li>
			</ul>
		</nav>
	</header>
	
	
	<!-- 1.如果session為空,請先登入 --><!-- 2.如果購物車為0,提示你的購物車為0 -->	<!-- 如果有購買的話 -->	
	<c:choose>
		<c:when test="${session == null}">
				<%
				JOptionPane.showMessageDialog(null, "Please Login first", "Info", JOptionPane.INFORMATION_MESSAGE);
				request.getRequestDispatcher("login.jsp").forward(request, response);
				%>
		</c:when>
		
		<c:when test="${x == 0}">
				
				<%
				JOptionPane.showMessageDialog(null, "Your shopping bag is empty", "Info", JOptionPane.INFORMATION_MESSAGE);
				request.getRequestDispatcher("cart.jsp").forward(request, response);
				%>
		</c:when>

		<c:when test="${session != null}">
				
				
				<div class="container" style="margin-top: 60px;margin-bottom: 60px;">
					<h4>感謝您~  <font color="#6bb1f8"><c:out value="${name }"></c:out></font>消費我們的商品,我們將會再將商品寄於您的 <font color="#6bb1f8"><c:out value="${address }"></c:out></font> 地址4~5個工作天</h4>
					<h4>確認mail將很快寄信到您的 <font color="#6bb1f8"><c:out value="${email }"></c:out></font>.</h4>
					
					<br><br>
					<a href="Controller?page=index"><input type="button" value="Continue Shopping" class="btn btn-warning" style="width:100%;padding:8px;font-size:16px;"></a>
				</div>
			
		</c:when>
	
	</c:choose>
	
	<!-- 頁尾 -->
	<footer style="position: fixed;bottom: 0;left: 0;width: 100%;">
		<div class="footer"> &copy; 2018 Copyright:
	      <a href="Controller?page=index"> Tiazon.com</a>
	    </div>
	</footer>
</body>
</html>