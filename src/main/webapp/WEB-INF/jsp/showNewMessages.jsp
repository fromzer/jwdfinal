<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:bundle basename="pagecontent">
    <html>
    <head>
        <title>Users message</title>
        <c:import url="parts/common.jsp"/>
    </head>
    <c:import url="parts/navbar.jsp"/>
    <body>
    <div class="container">
        <h2>New message</h2>
        <table class="table table-striped">
            <tr>
                <th>Title</th>
                <th>Message</th>
                <th>Action</th>
            </tr>
            <c:forEach var="row" items="${newMessageList}">
                <tr>
                    <td>${row.topic}</td>
                    <td>${row.description}</td>
                    <td><a class="btn btn-primary"
                           href="/home?command=toMessageReplyForm&userId=${row.userId}&messageId=${row.id}"
                           role="button">Answer</a></td>
                </tr>
            </c:forEach>
        </table>
    </div>
    </body>
    </html>
</fmt:bundle>