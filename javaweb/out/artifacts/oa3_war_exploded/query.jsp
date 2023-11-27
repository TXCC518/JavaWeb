<%@ page import="cn.txcc.bean.Dept" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang='en'>
<head>
    <meta charset='UTF-8'>
    <title>修改部门</title>
</head>
<body>
<form action='<%= request.getContextPath()%>/dept/query' method='post'>
    <%
        Dept dept = (Dept) request.getAttribute("dept");
    %>
    部门编号：<input type='text' name='deptno' readonly value='<%= dept.getDeptno()%>'><br>
    部门名称：<input type='text' name='dname' value="<%= dept.getDname()%>"><br>
    部门地址：<input type='text' name='loc' value="<%= dept.getLoc()%>"><br>
    <input type='submit' value='修改'>
</form>
</body>
</html>