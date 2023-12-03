<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>index</title>
		<base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
	</head>
	<body>
		<p align="center">LOGIN PAGE</p>
		<hr>
		<form action="user/login" method="post">
			username:<input type="text" name="username"><br>
			password:<input type="text" name="password"><br>
			<input type="submit" value="登录">
			<input type="checkbox" name="key" value="1">十天免登录
		</form>
	</body>
</html>