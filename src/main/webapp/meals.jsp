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
    <link rel="stylesheet" href="css/meals.css">
</head>
<body>
<ul>
    <% List<MealWithExceed> meals = (List<MealWithExceed>) request.getAttribute("meals");
        for (MealWithExceed m : meals) {
            String cssClass = m.isExceed() ? "red" : "green";
            String deleteHref = "meals?action=delete&id=" + m.getId();
            String editHref = "meals?action=showUpdateForm&id=" + m.getId()
                    + "&dateTime=" + m.getDateTime()
                    + "&calories=" + m.getCalories()
                    + "&description=" + m.getDescription();
    %>
    <li class=<%=cssClass%>>
        item<%=m.toString()%>
        <a href=<%=deleteHref%>>delete</a>
        <a href=<%=editHref%>>edit</a>
    </li>
    <%}%>
</ul>
<br>
<a href="addmeal.jsp">add</a>
</body>
</html>
