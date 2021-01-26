<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:bundle basename="pagecontent">
    <html>
    <head>
        <title>Users</title>
        <c:import url="parts/common.jsp"/>
    </head>
    <body>
    <div class="container">
        <c:import url="parts/navbar.jsp"/>
        <h2><fmt:message key="user.page.list"/></h2>
        <table class="table table-striped">
            <thead>
            <tr>
                <th><fmt:message key="user.page.edit.login"/></th>
                <th><fmt:message key="user.page.edit.firstName"/></th>
                <th><fmt:message key="user.page.edit.lastName"/></th>
                <th>@<fmt:message key="user.page.edit.email"/></th>
                <th><fmt:message key="user.page.edit.role"/></th>
                <th><fmt:message key="user.page.actions"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="row" items="${showAllUsers}">
                <tr>
                    <td>${row.login}</td>
                    <td>${row.firstName}</td>
                    <td>${row.lastName}</td>
                    <td>${row.email}</td>
                    <td>${row.role}</td>
                    <td>
                        <a class="btn btn-primary" href="/admin?command=editUser&userId=${row.id}"
                           role="button"><fmt:message key="page.button.edit"/></a>
                        <a class="btn btn-primary" href="/admin?command=deleteUser&userId=${row.id}"
                           role="button"><fmt:message key="page.button.delete"/></a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    </body>
    </html>
</fmt:bundle>