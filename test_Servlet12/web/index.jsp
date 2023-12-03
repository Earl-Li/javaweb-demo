<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>index</title>
		<%--设置整个网页的基础路径是：http://localhost:8080/oa/ --%>
		<base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
	</head>
	<body>
		<p align="center">LOGIN PAGE</p>
		<hr>
		<%--<form action="<%=request.getContextPath()%>/user/login" method="post">--%>
		<%--更改为base标签下的模式--%>
		<form action="user/login" method="post">
			username:<input type="text" name="username"><br>
			password:<input type="text" name="password"><br>
			<%--十天免登录一般是复选框，放在登录按钮前面或者后面都可以提交数据，放在后面好看一点--%>
			<%--<input type="checkbox" name="key" value="1">十天免登录--%>
			<input type="submit" value="登录">
			<input type="checkbox" name="key" value="1">十天免登录
			<%--get方法可以在payload里面看表单具体提交数据，post方法不可以--%>
		</form>
		<%--编写一个userServlet类路径<%=request.getContextPath()%>/user/login验证用户账号--%>

		<%--以下是之前的登录功能前的查看部门列表超链接--%>
		<%--<a href="<%=request.getContextPath()%>/dept/list">查看部门列表</a>--%>
	</body>
</html>