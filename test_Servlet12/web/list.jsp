<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ page import="java.util.List,com.atlisheng.servlet.Dept" %>--%>
<%--导包还可以分开写--%>
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
	
		<h3>欢迎${user}</h3>
		<script type="text/javascript">
			function del(dno){
				if (window.confirm("亲，删除了不可恢复哟!")) {
					/*window.location.href="<%--<%=request.getContextPath()%>--%>/dept/delete?deptno="+dno*/
					/*base标签可能对JS代码不起作用，JS代码前最好写上"/ser12"*/
					window.location.href="${pageContext.request.contextPath}/dept/delete?deptno="+dno
				}
			}
		</script>
		<%--数据用java代码展示，相当于显示的部分写成java代码，静态的不变--%>
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
			<%--以上为静态的，以下的表格进行改造，思路是当成java代码输出来写--%>
			<%--<%
			//取出List对象
				List<Dept> deptlist=(List<Dept>) request.getAttribute("deptList");
				int i=0;
				for (Dept dept:deptlist) {

			%>

			<tr>
				<td><%=i++%></td>
				<td><%=dept.getDeptno()%></td>
				<td><%=dept.getDname()%></td>
				<td>
					<a href="javascript:void(0)" onclick="del(<%=dept.getDeptno()%>)">删除</a>
					<a href="<%=request.getContextPath()%>/dept/detail?deptno=<%=dept.getDeptno()%>">修改</a>
					<a href="<%=request.getContextPath()%>/dept/detail?deptno=<%=dept.getDeptno()%>">详情</a>
				</td>
			</tr>


			<%
				}
			%>--%>
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