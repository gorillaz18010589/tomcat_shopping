<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="RegeisterServlet" method="post">
	<input type="hidden" name="page" value="mail"> 
	<label>First Name:</label>
	<input type="text" name="fname" id="n1"/>
	<br><br>
	<label>Last Name:</label>
	<input type="text" name="lname" id="n2"/>
	<br><br>
	<label>Email:</label>
	<input type="text" name="email" id="n3"/>
	<br><br>
	<label>Password:</label>
	<input type="password" name="pword" id="n4"/>
	<br><br>
	<input type="submit" name="submit" value="submit"/>
	</form>
</body>
</html>