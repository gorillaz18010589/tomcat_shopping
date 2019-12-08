<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>

	<div class="signup-header">
	 	<h2> <mark>Admin</mark> Login</h2>
	</div>

  <!-- 管理者登入頁面 -->
<form method="post" action="AdminController">
	 
	 <input type="hidden" name="page" value="admin-login-form">
	 
	 	<!-- 帳密錯誤訊息 -->
	 	<font color="#F24638"><c:out value="${msg }"></c:out></font>
	 
	 	<!-- 使用者輸入帳號:參數為username -->
	 	<div class="signup-group">
	 		<label>Username</label>
	 		<input type="text" name="username" placeholder="Your Username" value="<c:out value="${username }"></c:out>">
	 	</div>
	 	<!-- 使用者輸入帳號:參數為password -->
	 	<div class="signup-group">
	 		<label>Password</label>
	 		<input type="password" name="password" placeholder="Enter password">
	 	</div>
	 	<!-- 送出按鈕 -->
	 	<div class="signup-group">
	 		<button type="submit" name="login" class="signup-btn">Log in</button>
	 	</div>
	 </form>
</body>
</html>