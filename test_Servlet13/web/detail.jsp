<%@ page import="com.atlisheng.servlet.bean.Dept" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
		<title>部门详情</title>
	</head>
	<body>
	<h3>欢迎${user.username}</h3>
		部门详情
		<hr>
		<form action="dept/modify" method="post">
			部门编号<input type="text" name="deptno" value="${dept.deptno}" readonly /><br>
			部门名称<input type="text" name="dname" value="${dept.dname}"/><br>
			部门位置<input type="text" name="loc" value="${dept.loc}"/><br>
			<input type="submit" value="修改"/>
		</form>
	<hr>
	<a href="dept/list">返回</a>
	</body>
</html>