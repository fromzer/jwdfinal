<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<%@ taglib uri="functionTags" prefix="fnc" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:bundle basename="pagecontent">
    <html>
    <head>
        <title><fmt:message key="section.views.title"/></title>
        <c:import url="parts/common.jsp"/>
    </head>
    <body>
    <div class="container">
        <c:import url="parts/navbar.jsp"/>
        <div class="row justify-content-center">
            <div class="col-md-8 mt-3 left">
                <h2><fmt:message key="section.views.title"/></h2>
                <a href="/home?command=main"><fmt:message key="page.inscription.back"/></a>
                <c:forEach var="row" items="${showSections}">
                    <div class="card mb-4">
                        <div class="card-body">
                            <h2 class="card-title">${row.title}</h2>
                            <p class="card-text">${row.description}</p>
                            <c:if test="${fn:contains(registerSection, row.id)}">
                                <a>
                                    <input type="submit" class="btn btn-success"
                                           value="<fmt:message key="page.button.attached"/>" align="right" disabled/>
                                </a>
                            </c:if>
                            <c:if test="${!fn:contains(registerSection, row.id) && currentUser!=null}">
                                <a href="/user?command=addRequest&confId=${row.conferenceId}&sectionId=${row.id}">
                                    <input type="submit" class="btn btn-primary"
                                           value="<fmt:message key="page.button.join"/>" align="right"/>
                                </a>
                            </c:if>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
    </body>
    </html>
</fmt:bundle>