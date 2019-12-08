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
	<header>
		<h1>
			Welcome admin
		</h1>
		<nav>
			<ul>
				<li><a href="admin?page=index">Home</a></li>
				<li><a href="admin?page=addproduct">Add Product</a></li>
				<li><a href="#">Settings</a></li>
				<li><a href="#">Pages</a></li>
			</ul>
		</nav>
	</header>
	
	<div class="signup-header">
	 	<h2>Edit Product</h2>
	 </div>

	<!-- 更新商品 -->
	 <form method="post" action="AdminController">
	 <!-- 設定page參數值為edit_product,方便給Contrller操控 -->
	 <input type="hidden" name="page" value="edit_product">
	 <!-- 設定id參數,傳給Contrller ,預設p是修改時點選到的商品資訊顯示出來 -->
	 <input type="hidden" name="id" value="<c:out value="${p.getId() }"/>">
     
     <!-- 把4個修改的參數傳遞給Conrtller利用,updateProduct方法 -->
	 	<div class="signup-group">
	 		<label>Name</label>
	 		<input type="text" name="name" value="<c:out value="${p.getName() }"></c:out>" required>
	 	</div>
	 	<div class="signup-group">
	 		<label>Price</label>
	 		<input type="text" name="price" value="<c:out value="${p.getPrice() }"></c:out>" required>
	 	</div>
	 	<div class="signup-group">
	 		<label>Category</label>
	 		<input type="text" name="category" value="<c:out value="${p.getCategory() }"></c:out>" required>
	 	</div>
	 	
	 	<div class="signup-group">
	 		<label>Featured</label>
	 		<input type="text" name="featured" value="<c:out value="${p.getFeatured() }"></c:out>" required>
	 	</div>
	 	<div class="signup-group">
	 		<label>Image</label>
	 		<img style="height: 160px;width: 160px;" src="<c:out value="${p.getImage() }"></c:out>">
	 	</div>
	 	<div class="signup-group">
			<input type="submit" value="Process">	 
		</div>
	 </form>
	
	 <footer>
		<div class="footer"> &copy; 2018 Copyright:
	      Tiazon.com
	    </div>
	</footer>
</body>
</html>