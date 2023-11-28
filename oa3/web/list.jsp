<%@ page import="cn.txcc.bean.Dept" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>部门列表页面</title>
    <style>
        h1 {
            text-align: center;
        }

        table, th, td {
            margin: 50px auto;
            text-align: center;
            border: 1px solid #000;
            border-collapse: collapse;
        }

        th, td {
            padding: 10px 30px;
        }

        a {
            text-decoration: none;
        }
    </style>
</head>
<script type="text/javascript">
    function del(dno){
        if(confirm("亲，您确定要删除吗？")){
            document.location.href=`<%= request.getContextPath()%>/dept/remove?deptno=` + dno;
        }
    }
</script>
<body>
<h2>欢迎：<%= session.getAttribute("username")%></h2>
<a href="<%= request.getContextPath()%>/user/logout">【安全退出】</a>
<h1>部门列表</h1>
<hr>
<table>
    <tr>
        <th>序号</th>
        <th>部门编号</th>
        <th>部门名称</th>
        <th>部门地址</th>
        <th>操作</th>
    </tr>
    <%
        int i = 1;
        ArrayList<Dept> depts = (ArrayList<Dept>) request.getAttribute("depts");
        for(Dept dept: depts){
    %>
    <tr>
        <td><%= i++ %></td>
        <td><%= dept.getDeptno() %></td>
        <td><%= dept.getDname() %></td>
        <td><%= dept.getLoc() %></td>
        <td>
            <a href="javascript:void(0)" onclick="del(<%= dept.getDeptno()%>)">删除</a>
            <a href="<%= request.getContextPath()%>/dept/detail?path=query&deptno=<%= dept.getDeptno()%>">修改</a>
            <a href="<%= request.getContextPath()%>/dept/detail?path=detail&deptno=<%= dept.getDeptno()%>">详情</a>
        </td>
    </tr>
    <%
        }
    %>
</table>
<hr>
<a href="<%= request.getContextPath() %>/add.jsp">添加部门</a>
</body>
</html>
