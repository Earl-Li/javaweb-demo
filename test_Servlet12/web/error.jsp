<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <title>出错了</title>
</head>
<body>
    <%--<h3>欢迎<%=session.getAttribute("user")%></h3>--%>
    <h3>欢迎${user}</h3>
    出错了!
    <hr>
    <%--<a href="<%=request.getContextPath()%>/dept/list">返回</a>--%>
    <a href="dept/list">返回</a>
</body>
</html>