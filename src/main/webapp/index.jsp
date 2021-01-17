<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:bundle basename="pagecontent">
    <html>
    <head><title>Index</title></head>
    <body>
    <jsp:forward page="/home?command=main"></jsp:forward>
    </body>
    </html>
</fmt:bundle>