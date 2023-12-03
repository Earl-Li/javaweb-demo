<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>出错了</title>
</head>
<body>
    <h3>欢迎<%=session.getAttribute("user")%></h3>
    出错了!
    <hr>
    <a href="<%=request.getContextPath()%>/dept/list">返回</a>
</body>
</html>