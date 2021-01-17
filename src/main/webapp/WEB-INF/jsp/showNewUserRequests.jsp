<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:bundle basename="pagecontent">
    <html>
    <head>
        <title>New user requests</title>
        <c:import url="parts/common.jsp"/>
    </head>
    <body>
    <div class="container">
        <c:import url="parts/navbar.jsp"/>
        <h2>New requests</h2>
        <table class="table table-striped">
            <thead>
            <tr>
                <th>User</th>
                <th>Conference</th>
                <th>Section</th>
                <th>Date start</th>
                <th>Date end</th>
                <th>State</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="row" items="${showNewRequests}">
                <tr>
                    <td>${row.user.login}</td>
                    <td>${row.conference.title}</td>
                    <td>${row.section.title}</td>
                    <td>${row.conference.dateStart}</td>
                    <td>${row.conference.dateEnd}</td>
                    <form action="/home?command=changeStateRequest&userSectionId=${row.userSection.id}&sectionId=${row.section.id}&userId=${row.user.id}" method="post">
                        <td>
                            <select id="states" name="states">
                                <option value="1">Considered</option>
                                <option value="2">Confirm</option>
                                <option value="3">Reject</option>
                            </select>
                        </td>
                        <td>
                            <button name="submit" type="submit" id="web-form-submit" data-submit="...Sending">Update</button>
                        </td>
                    </form>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    </body>
    </html>
</fmt:bundle>