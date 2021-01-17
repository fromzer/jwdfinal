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
        <form action="/home?command=addSection&confId=${confId}" method="post">
            <div class="container">
                <h4><fmt:message key="section.edit.title"/></h4>
                <p><fmt:message key="section.edit.title.helpMessage"/></p>
                <hr>
                <div class="mb-3">
                    <label for="confName"><b><fmt:message key="section.edit.title.conference"/></b></label>
                    <input type="text" class="form-control"
                           placeholder="${confTitle}" name="confName"
                           id="confName" disabled>
                </div>
                <br/>
                <div class="mb-3">
                    <label for="sectionTitle"><b><fmt:message key="edit.label.title"/></b></label>
                    <input type="text" class="form-control <c:if test="${not empty validateResult}">is-invalid</c:if>"
                           placeholder="<fmt:message key="edit.label.title.placeholder"/>" name="sectionTitle"
                           value="${entity != null?entity.title:section.title}" name="topicForm"
                           id="sectionTitle" required>
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
                    <label for="sectionDescription" class="form-label"><fmt:message
                            key="edit.label.description"/></label>
                    <textarea class="form-control <c:if test="${not empty validateResult}">is-invalid</c:if>"
                              id="sectionDescription" name="sectionDescription" rows="10"
                              placeholder="<fmt:message key="edit.label.description.placeholder"/>"
                              name="topicForm"
                              required>${entity != null?entity.description:section.description}</textarea>
                    <div class="invalid-feedback">
                        <c:forEach var="violation" items="${validateResult}">
                            <c:if test="${violation.field eq 'description'}">
                                <c:out value="${violation.message}"/>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
                <hr>
                <button type="submit" class="btn btn-primary"><fmt:message key="page.button.create"/></button>
                <a class="btn btn-primary" href="/home?command=toEditSections&confId=${confId}" role="button">Cancel</a>
            </div>
        </form>
    </div>
    </body>
    </html>
</fmt:bundle>