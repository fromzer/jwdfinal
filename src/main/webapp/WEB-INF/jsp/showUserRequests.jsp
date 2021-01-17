<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:bundle basename="pagecontent">
    <html>
    <head>
        <title>Requests list</title>
        <c:import url="parts/common.jsp"/>
    </head>
    <body>
    <div class="container">
        <c:import url="parts/navbar.jsp"/>
        <h2>List Users</h2>
        <table class="table table-striped">
            <thead>
            <tr>
                <th>Conference</th>
                <th>Section</th>
                <th>Date start</th>
                <th>Date end</th>
                <th>State</th>
                <th>Actions</th>
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
                        <a class="btn btn-primary" href="/home?command=deleteRequest&sectionId=${row.section.id}" role="button">Cancel</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    </body>
    </html>
</fmt:bundle>