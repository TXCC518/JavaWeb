<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Login Page</title>
</head>
<body>
<h1>Login Page</h1>
<form action="<%= request.getContextPath()%>/user/login" method="post">
  username：<input type="text" name="username" ><br>
  password：<input type="password" name="password" ><br>
  <input type="checkbox" name="flag" value="1">十天内免登录<br>
  <input type="submit" value="Login">
</form>
</body>
</html>