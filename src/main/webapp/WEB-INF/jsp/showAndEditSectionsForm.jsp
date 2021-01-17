<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:bundle basename="pagecontent">
    <html>
    <head>
        <title>Edit section</title>
        <c:import url="parts/common.jsp"/>
    </head>
    <body>
        <div class="container">
            <c:import url="parts/navbar.jsp"/>
            <h2><fmt:message key="section.views.title"/></h2>
            <a type="button" href="/home?command=toCreateSectionForm&confId=${confId}"><fmt:message key="page.button.create"/></a>
            <div class="list-group">
                <c:if test="${empty showSections}">
                    <p align="center"><fmt:message key="section.message.empty"/></p>
                </c:if>
                <c:forEach var="row" items="${showSections}">
                    <a href="/home?command=editSection&sectionId=${row.id}&confId=${confId}" class="list-group-item list-group-item-action"
                       aria-current="true">
                        <div class="d-flex w-100 justify-content-between">
                            <h5 class="mb-1">${row.title}</h5>
                        </div>
                        <div class="size"><p class="mb-1">${row.description}</p></div>
                        <a href="/home?command=deleteSection&sectionId=${row.id}&confId=${confId}" class="btn btn-primary"><fmt:message
                                key="page.button.delete"/></a>
                    </a>
                </c:forEach>
            </div>
        </div>
    </body>
    </html>
</fmt:bundle>