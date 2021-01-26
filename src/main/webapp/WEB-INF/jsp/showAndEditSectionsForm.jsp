<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:bundle basename="pagecontent">
    <html>
    <head>
        <title><fmt:message key="section.edit.title"/></title>
        <c:import url="parts/common.jsp"/>
    </head>
    <body>
    <div class="container">
        <c:import url="parts/navbar.jsp"/>

        <div class="row justify-content-center">
            <div class="col-md-8 mt-3 left">
                <h2><fmt:message key="section.views.title"/></h2>
                <a type="button" href="/admin?command=toCreateSectionForm&confId=${confId}"><fmt:message
                        key="page.button.create"/></a>
                <c:if test="${empty showSections}">
                    <p align="center"><fmt:message key="section.message.empty"/></p>
                </c:if>
                <c:forEach var="row" items="${showSections}">
                    <div class="card mb-4">
                        <div class="card-body">
                            <h2 class="card-title">${row.title}</h2>
                            <p class="card-text">${row.description}</p>
                            <a href="/admin?command=editSection&sectionId=${row.id}&confId=${confId}"
                               class="btn btn-warning"
                               aria-current="true"><fmt:message
                                    key="page.button.edit"/></a>
                            <a href="/admin?command=deleteSection&sectionId=${row.id}&confId=${confId}"
                               class="btn btn-danger"><fmt:message
                                    key="page.button.delete"/></a>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
    </body>
    </html>
</fmt:bundle>