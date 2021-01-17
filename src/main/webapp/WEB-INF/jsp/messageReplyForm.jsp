<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="functionTags" prefix="fnc" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:bundle basename="pagecontent">
    <html>
    <head>
        <title>Message reply form</title>
        <c:import url="parts/common.jsp"/>
    </head>
    <body>
    <div class="container">
        <c:import url="parts/navbar.jsp"/>
        <h4 align="center"><fmt:message key="message.page.title"/></h4>
        <div class="d-flex flex-column center" align="center">
            <form name="LoginForm" method="post"
                  action="/user?command=sendMessage&messageId=${messageId}&userId=${user.id}">
                <div class="mb-3">
                    <label for="emailForm" class="form-label"><fmt:message key="message.label.email"/></label>
                    <input type="text" class="form-control" id="emailForm" value="${user.email}" name="emailForm"
                           disabled/>
                </div>
                <div class="mb-3">
                    <label for="topicForm" class="form-label"><fmt:message key="message.label.topic"/></label>
                    <input type="text" class="form-control <c:if test="${not empty validateResult}">is-invalid</c:if>"
                           id="topicForm"
                           value="<c:if test="${entity != null}">${entity.topic}</c:if>" name="topicForm"
                           placeholder="<fmt:message key="message.topic.placeholder"/>" required/>
                    <div class="invalid-feedback">
                        <fnc:violationTag violationList="${validateResult}" fieldName="topic"/>
                    </div>
                </div>
                <div class="mb-3">
                    <label for="textMessageForm" class="form-label"><fmt:message
                            key="message.label.description"/></label>
                    <textarea class="form-control <c:if test="${not empty validateResult}">is-invalid</c:if>"
                              id="textMessageForm" name="textMessageForm" rows="7"
                              placeholder="<fmt:message key="message.description.placeholder"/>" required><c:if
                            test="${entity != null}">${entity.description}</c:if></textarea>
                    <div class="invalid-feedback">
                        <fnc:violationTag violationList="${validateResult}" fieldName="message"/>
                    </div>
                </div>
                </br>
                <input type="submit" class="btn btn-primary" value="<fmt:message key="page.button.send"/>"/>
                </br>
            </form>
        </div>
    </div>
    </body>
    </html>
</fmt:bundle>