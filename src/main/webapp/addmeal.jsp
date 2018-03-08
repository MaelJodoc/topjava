<%--
  Created by IntelliJ IDEA.
  User: Ebozavreg
  Date: 08.03.2018
  Time: 12:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<form action="meals">
    <input type="hidden" name="action" value="add">

    <p>Time<input type="datetime-local" name="localTime"></p>

    <p>Description<input type="text" name="description"></p>

    <p>Calories<input type="text" name="calories"></p>

    <p><input type="submit"></p>
</form>
</body>
</html>
