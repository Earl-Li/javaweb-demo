<%@ page import="com.atlisheng.servlet.bean.Dept" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>部门详情</title>
	</head>
	<body>
	<h3>欢迎<%=session.getAttribute("user")%></h3>
		部门详情
		<hr>
		<%
		Dept dept=(Dept) request.getAttribute("dept");
		%>
		<form action="<%=request.getContextPath()%>/dept/modify" method="post">
			部门编号<input type="text" name="deptno" value="<%=dept.getDeptno()%>" readonly /><br>
			部门名称<input type="text" name="dname" value="<%=dept.getDname()%>"/><br>
			部门位置<input type="text" name="loc" value="<%=dept.getLoc()%>"/><br>
			<input type="submit" value="修改"/>
		</form>
	<hr>
	<a href="<%=request.getContextPath()%>/dept/list">返回</a>
	</body>
</html>