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
        <form action="/home?command=addConference" method="post">
            <div class="container">
                <h1><fmt:message key="conference.edit.title"/></h1>
                <p><fmt:message key="conference.edit.title.helpMessage"/></p>
                <hr>
                <div class="mb-3">
                    <label for="confTitle"><b><fmt:message key="edit.label.title"/></b></label>
                    <input type="text" class="form-control <c:if test="${not empty validateResult}">is-invalid</c:if>"
                           placeholder="<fmt:message key="edit.label.title.placeholder"/>" name="confTitle"
                           value="<c:if test="${entity != null}">${entity.title}</c:if>"
                           id="confTitle" required>
                    <div class="invalid-feedback">
                        <c:forEach var="violation" items="${validateResult}">
                            <c:if test="${violation.field eq 'title'}">
                                <c:out value="${violation.message}"/>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
                <br/>
                <div class="mb-3">
                    <label for="confDescription" class="form-label"><fmt:message
                            key="edit.label.description"/></label>
                    <textarea class="form-control <c:if test="${not empty validateResult}">is-invalid</c:if>"
                              id="confDescription" name="confDescription" rows="10"
                              placeholder="<fmt:message key="edit.label.description.placeholder"/>"
                              required><c:if test="${entity != null}">${entity.description}</c:if></textarea>
                    <div class="invalid-feedback">
                        <c:forEach var="violation" items="${validateResult}">
                            <c:if test="${violation.field eq 'description'}">
                                <c:out value="${violation.message}"/>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
                <div class="mb-3">
                    <label for="dateStart"><b><fmt:message key="conference.edit.label.dateStart"/></b></label>
                    <input type="date" class="form-control <c:if test="${not empty validateResult}">is-invalid</c:if>"
                           name="dateStart" id="dateStart"
                           value="<c:if test="${entity != null}">${entity.dateStart}</c:if>"
                           required>
                    <div class="invalid-feedback">
                        <c:forEach var="violation" items="${validateResult}">
                            <c:if test="${violation.field eq 'dateStart'}">
                                <c:out value="${violation.message}"/>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
                <br/>
                <div class="mb-3">
                    <label for="dateEnd"><b><fmt:message key="conference.edit.label.dateEnd"/></b></label>
                    <input type="date" class="form-control <c:if test="${not empty validateResult}">is-invalid</c:if>"
                           value="<c:if test="${entity != null}">${entity.dateEnd}</c:if>" name="dateEnd" id="dateEnd"
                           required>
                    <div class="invalid-feedback">
                        <c:forEach var="violation" items="${validateResult}">
                            <c:if test="${violation.field eq 'dateEnd'}">
                                <c:out value="${violation.message}"/>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
                <hr>
                <button type="submit" class="btn btn-primary"><fmt:message key="page.button.create"/></button>
                <a class="btn btn-primary" href="/home?command=toEditConferencesPage&confId=${row.id}"
                   role="button"><fmt:message key="page.button.cancel"/></a>
            </div>
        </form>
    </div>
    </body>
    </html>
</fmt:bundle>