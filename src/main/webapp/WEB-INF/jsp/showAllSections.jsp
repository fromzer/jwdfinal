<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:bundle basename="pagecontent">
    <html>
    <head>
        <title>Sections page</title>
        <c:import url="parts/common.jsp"/>
    </head>
    <body>
    <div class="container">
        <c:import url="parts/navbar.jsp"/>
        <h2>List of Conferences</h2>


        <div class="list-group">
            <c:forEach var="row" items="${showSections}">
                <a class="list-group-item list-group-item-action"
                   aria-current="true">
                    <div class="d-flex w-100 justify-content-between">
                        <h5 class="mb-1">${row.title}</h5>
                    </div>
                    <div class="size"><p class="mb-1">${row.description}</p></div>
                    <c:if test="${fn:contains(registerSection, row.id)}">
                        <a>
                            <input type="submit" class="btn btn-primary" value="attached" align="right" disabled/>
                        </a>

                    </c:if>
                    <c:if test="${!fn:contains(registerSection, row.id) && currentUser!=null}">
                        <a href="/home?command=addRequest&confId=${row.conferenceId}&sectionId=${row.id}">
                            <input type="submit" class="btn btn-primary" value="join" align="right"/>
                        </a>
                    </c:if>
                </a>
            </c:forEach>
        </div>
    </div>
    </body>
    </html>
</fmt:bundle>