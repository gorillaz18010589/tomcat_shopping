<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous"> 
<link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
	<!-- 導覽列 -->
	<header>
		<h1>
			Welcome admin
		</h1>
		<nav>
			<ul>
				<li><a href="AdminController?page=index">Home</a></li>
				<li><a href="AdminController?page=addproduct">Add Product</a></li>
				<li><a href="#">Settings</a></li>
				<li><a href="#">Pages</a></li>
			</ul>
		</nav>
	</header>
	
	<!-- 登入資料庫,取出Product資料存到result參數 -->
	 <sql:setDataSource user="root" password="root" driver="com.mysql.jdbc.Driver" url="jdbc:mysql://localhost:3305/myproject" var="ds"/>
	 
	  <sql:query var="result" dataSource="${ds }">
 
		 select * from product
		 
	   </sql:query>
	   
	  
	<!-- 品項列 -->
	<div class="container">
	<h2>Products List:</h2>
		 <table>
			<tr>
			<th>Item id</th>
			<th>Name</th>
			<th>Price</th>
			<th>Category</th>
			<th>Image</th>
			<th>Option</th>
		</tr>
	</table>
		
		<!-- 尋訪Proudct的資料存到row,顯示出這些商品的訊息 -->
		<!-- 尋訪每一筆商品清單var叫row -->
		 <c:forEach items="${result.rows }" var="row">
		  <table style="table-layout: fixed;width: 100%;">
		  	
				<tr>
					<td style="width: 50px;"><c:out value="${row.id }"></c:out></td>
					<td style="width: 100px;"><c:out value="${row.name }"></c:out></td>
					<td style="width: 100px;"><c:out value="${row.price }"></c:out></td>
					<td style="width: 100px;"><c:out value="${row.category}"/></td>
					<td style="width: 100px;"><img src="${row.image}" height="100" width="150" ></td>
					<td style="width: 100px;"><a href="<%= request.getContextPath() %>/AdminController?page=edit&id=${row.id}" style="color: #6bb1f8;">edit</a> ||
					<a href="<%= request.getContextPath() %>/AdminController?page=delete&id=${row.id}" style="color:#6bb1f8;">delete</a></td>
				</tr>
			</table>
		 </c:forEach>
		 </div>
	 <footer>
		<div class="footer"> &copy; 2018 Copyright:
	      Tiazon.com
	    </div>
	</footer>
	
</body>
</html>