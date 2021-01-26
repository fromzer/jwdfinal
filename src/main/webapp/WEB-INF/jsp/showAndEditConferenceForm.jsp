<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:bundle basename="pagecontent">
    <html>
    <head>
        <title><fmt:message key="conference.edit.title"/></title>
        <c:import url="parts/common.jsp"/>
    </head>
    <body>
    <div class="container">
        <c:import url="parts/navbar.jsp"/>
        <div class="row justify-content-center">
            <div class="col-md-8 mt-3 left">
                <h4><fmt:message key="page.view.conference.listTitle"/></h4>
                <a href="/admin?command=toCreateConferencePage" class="btn btn-primary"><fmt:message
                        key="page.button.create"/> </a>
                <c:forEach var="conference" items="${showConferences}">
                    <div class="card mb-4">
                        <div class="card-body">
                            <h2 class="card-title">${conference.title}</h2>
                            <p class="card-text text-muted h6"><fmt:message key="main.conference.dateStart"/> ${conference.dateStart} | <fmt:message key="main.conference.dateEnd"/> ${conference.dateEnd} </p>
                            <p class="card-text">${conference.description}</p>
                            <a href="/admin?command=toEditSections&confId=${conference.id}" class="btn btn-primary"><fmt:message
                                    key="section.views.title"/></a>
                            <a href="/admin?command=editConference&confId=${conference.id}" class="btn btn-warning"><fmt:message
                                    key="page.button.edit"/></a>
                            <a href="/admin?command=deleteConference&confId=${conference.id}" class="btn btn-danger"><fmt:message
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