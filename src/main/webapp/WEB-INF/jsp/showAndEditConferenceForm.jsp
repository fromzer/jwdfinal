<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:bundle basename="pagecontent">
    <html>
    <head>
        <title>Edit Conferences</title>
        <c:import url="parts/common.jsp"/>
    </head>
    <body>
    <div class="container">
        <c:import url="parts/navbar.jsp"/>
        <h4><fmt:message key="page.view.conference.listTitle"/></h4>
        <a href="/home?command=toCreateConferencePage" class="btn btn-primary"><fmt:message
                key="page.button.create"/> </a>
        <div class="list-group">
            <c:forEach var="row" items="${showConferences}">
                <a href="/home?command=toEditSections&confId=${row.id}" class="list-group-item list-group-item-action"
                   aria-current="true">
                    <div class="d-flex w-100 justify-content-between">
                        <h5 class="mb-1">${row.title}</h5>
                    </div>
                    <p class="mb-1">${row.description}</p>
                    <div>
                        <small><p>Date start: ${row.dateStart}</p></small>
                        <small><p>Date end: ${row.dateEnd}</p></small>
                    </div>
                    <a href="/home?command=editConference&confId=${row.id}" class="btn btn-primary"><fmt:message
                            key="page.button.edit"/></a>
                    <a href="/home?command=deleteConference&confId=${row.id}" class="btn btn-primary"><fmt:message
                            key="page.button.delete"/></a>
                </a>
            </c:forEach>
        </div>
    </div>
    </body>
    </html>
</fmt:bundle>