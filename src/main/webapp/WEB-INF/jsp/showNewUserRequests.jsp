<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:bundle basename="pagecontent">
    <html>
    <head>
        <title><fmt:message key="request.page.title.new"/></title>
        <c:import url="parts/common.jsp"/>
    </head>
    <body>
    <div class="container">
        <c:import url="parts/navbar.jsp"/>
        <h2><fmt:message key="request.page.title"/></h2>
        <table class="table table-striped">
            <thead>
            <tr>
                <th><fmt:message key="request.table.user"/></th>
                <th><fmt:message key="request.table.conference"/></th>
                <th><fmt:message key="request.table.section"/></th>
                <th><fmt:message key="request.table.dateStart"/></th>
                <th><fmt:message key="request.table.dateEnd"/></th>
                <th><fmt:message key="request.table.state"/></th>
                <th><fmt:message key="request.table.actions"/></th>
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
                    <form action="/admin?command=changeStateRequest&userSectionId=${row.userSection.id}&sectionId=${row.section.id}&userId=${row.user.id}" method="post">
                        <td>
                            <select id="states" name="states">
                                <option value="1"><fmt:message key="request.state.considered"/></option>
                                <option value="2"><fmt:message key="request.state.confirm"/></option>
                                <option value="3"><fmt:message key="request.state.reject"/></option>
                            </select>
                        </td>
                        <td>
                            <button name="submit" class="btn btn-primary" type="submit" id="web-form-submit" data-submit="...Sending"><fmt:message key="page.button.update"/></button>
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