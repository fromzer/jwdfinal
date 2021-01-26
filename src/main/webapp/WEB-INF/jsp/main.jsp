<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:bundle basename="pagecontent">
    <!doctype html>
    <html lang="en">
    <head>
        <title><fmt:message key="main.title"/></title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <c:import url="parts/common.jsp"/>
    </head>
    <body>
    <div class="container">
        <c:import url="parts/navbar.jsp"/>
        <div>
            <form action="/home?command=main">
                <input type="hidden" name="currentPage" value="1">
                <div class="form-group col-md-4 small-height">
                    <label for="records"><fmt:message key="main.paginator.select"/></label>
                    <select class="form-control" id="records" name="recordsPerPage" onchange="this.form.submit()">
                        <option value="5">5</option>
                        <option value="10">10</option>
                        <option value="15">15</option>
                    </select>
                </div>
            </form>
        </div>
        </br>
        <div class="row justify-content-center">
            <div class="col-md-8 mt-3 left">
                <c:forEach var="conference" items="${showConferences}">
                    <div class="card mb-4">
                        <div class="card-body">
                            <h2 class="card-title">${conference.title}</h2>
                            <p class="card-text text-muted h6"><fmt:message
                                    key="main.conference.dateStart"/> ${conference.dateStart} | <fmt:message
                                    key="main.conference.dateEnd"/> ${conference.dateEnd} </p>
                            <p class="card-text">${conference.description}</p>
                            <a href="/home?command=sections&confId=${conference.id}"
                               class="btn btn-primary"><fmt:message key="main.conference.readMore"/>
                                &rarr;</a>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
        <div>
            <nav aria-label="Navigation for conferences">
                <ul class="pagination justify-content-center">
                    <c:if test="${currentPage != 1}">
                        <li class="page-item"><a class="page-link"
                                                 href="/home?command=main&recordsPerPage=${recordsPerPage}&currentPage=${currentPage-1}"><fmt:message
                                key="main.paginator.previous"/></a>
                        </li>
                    </c:if>
                    <c:forEach begin="1" end="${numberOfPages}" var="i">
                        <c:choose>
                            <c:when test="${currentPage eq i}">
                                <li class="page-item active"><a class="page-link">
                                        ${i} <span class="sr-only">(current)</span></a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li class="page-item"><a class="page-link"
                                                         href="/home?command=main&recordsPerPage=${recordsPerPage}&currentPage=${i}">${i}</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    <c:if test="${currentPage lt numberOfPages}">
                        <li class="page-item"><a class="page-link"
                                                 href="/home?command=main&recordsPerPage=${recordsPerPage}&currentPage=${currentPage+1}"><fmt:message
                                key="main.paginator.next"/></a>
                        </li>
                    </c:if>
                </ul>
            </nav>
        </div>
    </div>
    </body>
    </html>
</fmt:bundle>