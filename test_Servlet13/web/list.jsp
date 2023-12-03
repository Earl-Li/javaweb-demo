<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.atlisheng.servlet.bean.Dept" %>
<%@ page import="java.lang.String" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
		<title>部门列表</title>
	</head>
	<body>
	
		<h3>欢迎${user.username}</h3>
		<h4>当前在线人数:${onlineCount}</h4>
		<script type="text/javascript">
			function del(dno){
				if (window.confirm("亲，删除了不可恢复哟!")) {
					window.location.href="${pageContext.request.contextPath}/dept/delete?deptno="+dno
				}
			}
		</script>
		<h1 align="center">部门列表</h1>
		<hr>
		<table border="2px" align="center" width="50%">
			<thead>
				<tr>
					<th>序号</th>
					<th>部门编号</th>
					<th>部门名称</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody align="center">
			<c:forEach items="${deptList}" var="list" varStatus="status">
				<tr>
					<td>${status.count}</td>
					<td>${list.deptno}</td>
					<td>${list.dname}</td>
					<td>
						<a href="javascript:void(0)" onclick="del(${list.deptno})">删除</a>
						<a href="dept/detail?deptno=${list.deptno}">修改</a>
						<a href="dept/detail?deptno=${list.deptno}">详情</a>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		<hr >
		<a href="add.jsp">新增部门</a>
		<a href="dept/logout">退出</a>
	</body>
</html>