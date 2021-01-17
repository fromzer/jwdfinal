<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:bundle basename="pagecontent">
    <html>
    <head>
        <title>Error page</title>
        <c:import url="parts/common.jsp"/>
    </head>
    <body>
    <div class="container">
        <c:import url="parts/navbar.jsp"/>
        <div class="row">
            <div class="col-8">
                <div class="alert alert-warning" role="alert">
                    <h4 class="alert-heading">Sorry</h4>
                    <hr>
                    <p align="center">Что-то пошло не так =)</p>
                    <hr>
                    <c:if test="${ not empty error }">
                        <p class="mb-0"><c:out value="message:: ${ error.toString() }"/></p>
                    </c:if>
                </div>
            </div>
            <div class="col-2">
                <p class="center"><a href="/home?command=main">Back to main</a></p>
            </div>
        </div>
    </div>
    </body>
    </html>
</fmt:bundle>