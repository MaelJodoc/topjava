<%@ page import="java.util.List" %>
<%@ page import="ru.javawebinar.topjava.model.MealWithExceed" %>
<%--
  Created by IntelliJ IDEA.
  User: Ebozavreg
  Date: 04.03.2018
  Time: 13:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<ul>
    <% List<MealWithExceed> meals = (List<MealWithExceed>) request.getAttribute("meals");
        for (MealWithExceed m : meals) {
    %>
    <li>item<%=m.toString()%>
    </li>
    <%}%>
</ul>
</body>
</html>
