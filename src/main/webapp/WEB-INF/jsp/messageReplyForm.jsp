<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="functionTags" prefix="fnc" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:bundle basename="pagecontent">
    <html>
    <head>
        <title><fmt:message key="message.page.reply.title"/></title>
        <c:import url="parts/common.jsp"/>
    </head>
    <body>
    <div class="container">
        <c:import url="parts/navbar.jsp"/>
        <div class="form-container">
            <form id="web-form" method="post"
                  action="/admin?command=sendMessage&messageId=${messageId}&userId=${user.id}">
                <h3><fmt:message key="messages.page.answer"/></h3>
                <fieldset>
                    <input type="text" class="<c:if test="${not empty validateResult}">is-invalid</c:if>"
                           placeholder="<fmt:message key="message.label.email"/>" value="${user.email}" name="emailForm"
                           id="emailForm"
                           disabled>
                    <div class="invalid-feedback">
                        <fnc:violationTag violationList="${validateResult}" fieldName="email"/>
                    </div>
                </fieldset>
                <fieldset>
                    <input type="text" tabindex="1" class="<c:if test="${not empty validateResult}">is-invalid</c:if>"
                           id="topicForm"
                           value="<c:if test="${entity != null}">${entity.topic}</c:if>" name="topicForm"
                           placeholder="<fmt:message key="message.topic.placeholder"/>" required tabindex="2"/>
                    <div class="invalid-feedback">
                        <fnc:violationTag violationList="${validateResult}" fieldName="topic"/>
                    </div>
                </fieldset>
                <fieldset>
                            <textarea class="form-control <c:if test="${not empty validateResult}">is-invalid</c:if>"
                                      id="textMessageForm" name="textMessageForm" rows="7"
                                      placeholder="<fmt:message key="message.description.placeholder"/>" required><c:if
                                    test="${entity != null}">${entity.description}</c:if></textarea>
                    <div class="invalid-feedback">
                        <fnc:violationTag violationList="${validateResult}" fieldName="message"/>
                    </div>
                </fieldset>
                <fieldset>
                    <button name="submit" type="submit" id="web-form-submit" data-submit="...Sending"><fmt:message
                            key="page.button.send"/></button>
                </fieldset>
            </form>
        </div>
    </div>
    </body>
    </html>
</fmt:bundle>