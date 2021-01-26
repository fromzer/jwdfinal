<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="functionTags" prefix="fnc" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:bundle basename="pagecontent">
    <html>
    <head>
        <title><fmt:message key="registration.page.title"/></title>
        <c:import url="parts/common.jsp"/>
    </head>
    <body>
    <div class="container">
        <c:import url="parts/navbar.jsp"/>
        <div class="form-container">
            <form id="web-form" action="/home?command=addUser" method="post">
                <h3><fmt:message key="registration.page.title"/></h3>
                <h4><fmt:message key="registration.page.title.message"/></h4>
                <fieldset>
                    <input class="<c:if test="${not empty validateResult}">is-invalid</c:if>"
                           value="<c:if test="${entity != null}">${entity.login}</c:if>" placeholder="<fmt:message key="user.page.edit.login.placeholder"/>"
                           type="text" name="login" id="login" tabindex="1" required autofocus>
                    <div class="invalid-feedback">
                        <fnc:violationTag violationList="${validateResult}" fieldName="login"/>
                    </div>
                </fieldset>
                <fieldset>
                    <input class="<c:if test="${not empty validateResult}">is-invalid</c:if>"
                           value="<c:if test="${entity != null}">${entity.firstName}</c:if>"
                           placeholder="<fmt:message key="user.page.edit.firstName.placeholder"/>"
                           type="text" name="firstName" id="firstName" tabindex="2" required>
                    <div class="invalid-feedback">
                        <fnc:violationTag violationList="${validateResult}" fieldName="firstName"/>
                    </div>
                </fieldset>
                <fieldset>
                    <input class="<c:if test="${not empty validateResult}">is-invalid</c:if>"
                           value="<c:if test="${entity != null}">${entity.lastName}</c:if>" placeholder="<fmt:message key="user.page.edit.lastName.placeholder"/>"
                           type="text" name="lastName" id="lastName" tabindex="3" required>
                    <div class="invalid-feedback">
                        <fnc:violationTag violationList="${validateResult}" fieldName="lastName"/>
                    </div>
                </fieldset>
                <fieldset>
                    <input class="<c:if test="${not empty validateResult}">is-invalid</c:if>"
                           placeholder="<fmt:message key="user.page.edit.password.placeholder"/>"
                           type="password" name="psw" id="psw" tabindex="4" required>
                    <div class="invalid-feedback">
                        <fnc:violationTag violationList="${validateResult}" fieldName="password"/>
                    </div>
                </fieldset>
                <fieldset>
                    <input class="<c:if test="${not empty validateResult}">is-invalid</c:if>"
                           placeholder="<fmt:message key="user.page.edit.passwordRepeat.placeholder"/>" type="password" name="psw-repeat" id="psw-repeat"
                           tabindex="5"
                           required>
                    <div class="invalid-feedback">
                        <fnc:violationTag violationList="${validateResult}" fieldName="passwordRepeat"/>
                    </div>
                </fieldset>
                <fieldset>
                    <input class="<c:if test="${not empty validateResult}">is-invalid</c:if>"
                           value="<c:if test="${entity != null}">${entity.email}</c:if>" placeholder="<fmt:message key="user.page.edit.email.placeholder"/>"
                           type="email" name="email" id="email" tabindex="3" required>
                    <div class="invalid-feedback">
                        <fnc:violationTag violationList="${validateResult}" fieldName="email"/>
                    </div>
                </fieldset>
                <fieldset>
                    <button name="submit" type="submit" id="web-form-submit" data-submit="...Sending"><fmt:message key="page.button.register"/></button>
                </fieldset>
                <p class="center"><fmt:message key="page.inscription.message.signIn"/> <a href="/home?command=loginPage"><fmt:message key="page.button.signIn"/></a></p>
            </form>
        </div>
    </div>
    </body>
    </html>
</fmt:bundle>