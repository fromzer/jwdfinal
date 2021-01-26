<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:bundle basename="pagecontent">
    <html>
    <head>
        <title><fmt:message key="messages.page.title"/></title>
        <c:import url="parts/common.jsp"/>
    </head>
    <c:import url="parts/navbar.jsp"/>
    <body>
    <div class="container">
        <h2><fmt:message key="messages.page.newMessages"/></h2>
        <table class="table table-striped">
            <tr>
                <th><fmt:message key="edit.label.title"/></th>
                <th><fmt:message key="edit.label.message"/></th>
                <th><fmt:message key="edit.label.action"/></th>
            </tr>
            <c:forEach var="row" items="${newMessageList}">
                <tr>
                    <td>${row.topic}</td>
                    <td>${row.description}</td>
                    <td><a class="btn btn-primary"
                           href="/admin?command=toMessageReplyForm&userId=${row.userId}&messageId=${row.id}"
                           role="button"><fmt:message key="page.button.answer"/></a></td>
                </tr>
            </c:forEach>
        </table>
    </div>
    </body>
    </html>
</fmt:bundle>