<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ page import="java.util.List,com.atlisheng.servlet.Dept" %>--%>
<%--导包还可以分开写--%>
<%@ page import="java.util.List" %>
<%@ page import="com.atlisheng.servlet.bean.Dept" %>
<%@ page import="java.lang.String" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title>部门列表</title>
	</head>
	<body>
	
		<h3>欢迎<%=session.getAttribute("user")%></h3>
		<script type="text/javascript">
			function del(dno){
				if (window.confirm("亲，删除了不可恢复哟!")) {
					window.location.href="<%=request.getContextPath()%>/dept/delete?deptno="+dno
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
			<%
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
			%>

				<%--静态的部分可以不要了--%>
				<%--<tr>
					<td>1</td>
					<td>10</td>
					<td>销售部</td>
					<td>
						<a href="javascript:void(0)" onclick="del(10)">删除</a>
						<a href="<%=request.getContextPath()%>/modify.jsp">修改</a>
						<a href="<%=request.getContextPath()%>/detail.jsp">详情</a>
					</td>
				</tr>
				<tr>
					<td>2</td>
					<td>20</td>
					<td>研发部</td>
					<td>
						<a href="javascript:void(0)" onclick="del(20)">删除</a>
						<a href="<%=request.getContextPath()%>/detail.jsp">修改</a>
						<a href="<%=request.getContextPath()%>/detail.jsp">详情</a>
					</td>
				</tr>
				<tr>
					<td>3</td>
					<td>30</td>
					<td>运营部</td>
					<td>
						<a href="javascript:void(0)" onclick="del(30)">删除</a>
						<a href="<%=request.getContextPath()%>/detail.jsp">修改</a>
						<a href="<%=request.getContextPath()%>/detail.jsp">详情</a>
					</td>
				</tr>
				<tr>
					<td>4</td>
					<td>40</td>
					<td>财务部</td>
					<td>
						<a href="javascript:void(0)" onclick="del(40)">删除</a>
						<a href="<%=request.getContextPath()%>/detail.jsp">修改</a>
						<a href="<%=request.getContextPath()%>/detail.jsp">详情</a>
					</td>
				</tr>--%>
			</tbody>
		</table>
		<hr >
		<a href="<%=request.getContextPath()%>/add.jsp">新增部门</a>
		<a href="<%=request.getContextPath()%>/dept/logout">退出</a>
	</body>
</html>