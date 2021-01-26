<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:bundle basename="pagecontent">
    <html>
    <head>
        <title><fmt:message key="request.page.title"/></title>
        <c:import url="parts/common.jsp"/>
    </head>
    <body>
    <div class="container">
        <c:import url="parts/navbar.jsp"/>
        <h2><fmt:message key="request.page.title"/></h2>
        <table class="table table-striped">
            <thead>
            <tr>
                <th><fmt:message key="request.table.conference"/></th>
                <th><fmt:message key="request.table.section"/></th>
                <th><fmt:message key="request.table.dateStart"/></th>
                <th><fmt:message key="request.table.dateEnd"/></th>
                <th><fmt:message key="request.table.state"/></th>
                <th><fmt:message key="request.table.actions"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="row" items="${showAllUserRequests}">
                <tr>
                    <td>${row.conference.title}</td>
                    <td>${row.section.title}</td>
                    <td>${row.conference.dateStart}</td>
                    <td>${row.conference.dateEnd}</td>
                    <td>${row.userSection.state}</td>
                    <td>
                        <a class="btn btn-primary" href="/user?command=deleteRequest&sectionId=${row.section.id}" role="button"><fmt:message key="page.button.cancel"/></a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    </body>
    </html>
</fmt:bundle>