<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>新增部门</title>
		<base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
	</head>
	<body>
	<h3>欢迎${user.username}</h3>
		<form action="dept/save" method="post">
			部门编号<input type="text" name="deptno"/><br>
			部门名称<input type="text" name="dname"/><br>
			部门位置<input type="text" name="loc"/><br>
			<input type="submit" value="添加"/>
		</form>
		<hr>
		<a href="dept/list">返回</a>
	</body>
</html>