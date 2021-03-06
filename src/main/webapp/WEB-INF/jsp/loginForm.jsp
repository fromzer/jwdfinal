<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="functionTags" prefix="fnc" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:bundle basename="pagecontent">
    <html>
    <head>
        <title><fmt:message key="login.page.title"/></title>
        <c:import url="parts/common.jsp"/>
    </head>
    <body>
    <div class="container">
        <c:import url="parts/navbar.jsp"/>
        <div class="form-container">
            <form id="web-form" action="/home?command=login" method="post">
                <input type="hidden" name="authAction" value="login">
                <h3><fmt:message key="login.page.login"/></h3>
                <h4><fmt:message key="login.page.message"/></h4>
                <h6><c:if test="${flag eq 'true'}"><fmt:message key="login.page.message.error"/></c:if></h6>
                <fieldset>
                    <input class="<c:if test="${not empty validateResult}">is-invalid</c:if>" placeholder="<fmt:message key="login.page.login.placeholder"/>" name="userName" id="userName" type="text" tabindex="1" value="<c:if test="${login != null}">${login}</c:if>" required
                           autofocus>
                    <div class="invalid-feedback">
                        <fnc:violationTag violationList="${validateResult}" fieldName="login"/>
                    </div>
                </fieldset>
                <fieldset>
                    <input class="<c:if test="${not empty validateResult}">is-invalid</c:if>" placeholder="<fmt:message key="login.page.password.placeholder"/>" name="inputPassword" id="inputPassword" type="password"
                           tabindex="2" required>
                    <div class="invalid-feedback">
                        <fnc:violationTag violationList="${validateResult}" fieldName="password"/>
                    </div>
                </fieldset>
                <fieldset>
                    <button name="submit" type="submit" id="web-form-submit" data-submit="...Sending"><fmt:message
                            key="login.page.login"/></button>
                </fieldset>
                <p class="center"><fmt:message key="page.inscription.register"/> <a href="/home?command=registration"><fmt:message
                        key="login.page.registration"/></a></p>
            </form>
        </div>
    </div>
    </body>
    </html>
</fmt:bundle>