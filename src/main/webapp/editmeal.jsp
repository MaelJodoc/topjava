<%@ page import="ru.javawebinar.topjava.model.Meal" %>
<%@ page import="java.time.LocalDateTime" %>
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
<%
    Meal meal = (Meal) request.getAttribute("meal");
    long id = (long) request.getAttribute("id");
    String description = meal.getDescription();
    int calories = meal.getCalories();
    LocalDateTime dateTime = meal.getDateTime();
%>

<form action="meals">
    <input type="hidden" name="action" value="update">
    <input type="hidden" name="id" value=<%=id%>>

    <p>Time<input type="datetime-local" name="localTime" value=<%=dateTime%>></p>

    <p>Description<input type="text" name="description" value=<%=description%>></p>

    <p>Calories<input type="text" name="calories" value=<%=calories%>></p>

    <p><input type="submit"></p>
</form>

</body>
</html>
