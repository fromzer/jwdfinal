<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="functionTags" prefix="fnc" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:bundle basename="pagecontent">
    <html>
    <head>
        <title><fmt:message key="user.page.edit.title"/></title>
        <c:import url="parts/common.jsp"/>
    </head>
    <body>
    <div class="container">
        <c:import url="parts/navbar.jsp"/>
        <div class="form-container">
            <form id="web-form" action="/user?command=updateUserInfo" method="post">
                <h3><fmt:message key="user.page.edit.title.update"/></h3>
                <fieldset>
                    <label for="login"><b><fmt:message key="user.page.edit.login"/></b></label>
                    <input placeholder="<fmt:message key="user.page.edit.login.placeholder"/>"
                           type="text" name="login" id="login" value="${userData.login}"
                           disabled tabindex="1" required>
                </fieldset>
                <fieldset>
                    <label for="firstName"><b><fmt:message key="user.page.edit.firstName"/></b></label>
                    <input class="<c:if test="${not empty validateResult}">is-invalid</c:if>"
                           placeholder="<fmt:message key="user.page.edit.firstName.placeholder"/>" type="text" name="firstName" id="firstName"
                           value="${entity ne null?entity.firstName:userData.firstName}" tabindex="2" required
                           autofocus>
                    <div class="invalid-feedback">
                        <fnc:violationTag violationList="${validateResult}" fieldName="firstName"/>
                    </div>
                </fieldset>
                <fieldset>
                    <label for="lastName"><b><fmt:message key="user.page.edit.lastName"/></b></label>
                    <input class="<c:if test="${not empty validateResult}">is-invalid</c:if>"
                           placeholder="<fmt:message key="user.page.edit.lastName.placeholder"/>" type="text" name="lastName" id="lastName" tabindex="3"
                           value="${entity ne null?entity.lastName:userData.lastName}" required>
                    <div class="invalid-feedback">
                        <fnc:violationTag violationList="${validateResult}" fieldName="lastName"/>
                    </div>
                </fieldset>
                <fieldset>
                    <label for="psw"><b><fmt:message key="user.page.edit.password"/></b></label>
                    <input class="<c:if test="${not empty validateResult}">is-invalid</c:if>"
                           placeholder="<fmt:message key="user.page.edit.password.placeholder"/>" type="password" name="psw" id="psw" tabindex="4">
                    <div class="invalid-feedback">
                        <fnc:violationTag violationList="${validateResult}" fieldName="password"/>
                    </div>
                </fieldset>
                <fieldset>
                    <label for="pswRepeat"><b><fmt:message key="user.page.edit.passwordRepeat"/></b></label>
                    <input class="<c:if test="${not empty validateResult}">is-invalid</c:if>"
                           placeholder="<fmt:message key="user.page.edit.passwordRepeat.placeholder"/>" type="password" name="pswRepeat" id="pswRepeat"
                           tabindex="5">
                    <div class="invalid-feedback">
                        <fnc:violationTag violationList="${validateResult}" fieldName="passwordRepeat"/>
                    </div>
                </fieldset>
                <fieldset>
                    <label for="email"><b><fmt:message key="user.page.edit.email"/></b></label>
                    <input class="<c:if test="${not empty validateResult}">is-invalid</c:if>" placeholder="<fmt:message key="user.page.edit.email.placeholder"/>"
                           type="email" name="email" id="email" tabindex="3"
                           value="${entity ne null?entity.email:userData.email}" required>
                    <div class="invalid-feedback">
                        <fnc:violationTag violationList="${validateResult}" fieldName="email"/>
                    </div>
                </fieldset>
                <fieldset>
                    <button name="submit" type="submit" id="web-form-submit" data-submit="...Sending"><fmt:message key="page.button.update"/></button>
                </fieldset>
            </form>
        </div>
    </div>
    </body>
    </html>
</fmt:bundle>