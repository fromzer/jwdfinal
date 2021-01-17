<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:bundle basename="pagecontent">
    <html>
    <head>
        <title>Registration</title>
        <c:import url="parts/common.jsp"/>
    </head>
    <body>
    <div class="container">
        <c:import url="parts/navbar.jsp"/>
        <div class="form-container">
            <form id="web-form" action="/home?command=addUser" method="post">
                <h3>Registration</h3>
                <h4>Please fill in this form to create an account.</h4>
                <fieldset>
                    <input class="<c:if test="${not empty validateResult}">is-invalid</c:if>"
                           value="<c:if test="${entity != null}">${entity.login}</c:if>" placeholder="Your Login"
                           type="text" name="login" id="login" tabindex="1" required autofocus>
                    <div class="invalid-feedback">
                        <c:forEach var="violation" items="${validateResult}">
                            <c:if test="${violation.field eq 'login'}">
                                <c:out value="${violation.message}"/>
                            </c:if>
                        </c:forEach>
                    </div>
                </fieldset>
                <fieldset>
                    <input class="<c:if test="${not empty validateResult}">is-invalid</c:if>"
                           value="<c:if test="${entity != null}">${entity.firstName}</c:if>"
                           placeholder="Your First Name"
                           type="text" name="firstName" id="firstName" tabindex="2" required>
                    <div class="invalid-feedback">
                        <c:forEach var="violation" items="${validateResult}">
                            <c:if test="${violation.field eq 'firstName'}">
                                <c:out value="${violation.message}"/>
                            </c:if>
                        </c:forEach>
                    </div>
                </fieldset>
                <fieldset>
                    <input class="<c:if test="${not empty validateResult}">is-invalid</c:if>"
                           value="<c:if test="${entity != null}">${entity.lastName}</c:if>" placeholder="Your Last Name"
                           type="text" name="lastName" id="lastName" tabindex="3" required>
                    <div class="invalid-feedback">
                        <c:forEach var="violation" items="${validateResult}">
                            <c:if test="${violation.field eq 'lastName'}">
                                <c:out value="${violation.message}"/>
                            </c:if>
                        </c:forEach>
                    </div>
                </fieldset>
                <fieldset>
                    <input class="<c:if test="${not empty validateResult}">is-invalid</c:if>"
                           placeholder="Your Password"
                           type="password" name="psw" id="psw" tabindex="4" required>
                    <div class="invalid-feedback">
                        <c:forEach var="violation" items="${validateResult}">
                            <c:if test="${violation.field eq 'password'}">
                                <c:out value="${violation.message}"/>
                            </c:if>
                        </c:forEach>
                    </div>
                </fieldset>
                <fieldset>
                    <input class="<c:if test="${not empty validateResult}">is-invalid</c:if>"
                           placeholder="Repeat Your Password" type="password" name="psw-repeat" id="psw-repeat"
                           tabindex="5"
                           required>
                    <div class="invalid-feedback">
                        <c:forEach var="violation" items="${validateResult}">
                            <c:if test="${violation.field eq 'passwordRepeat'}">
                                <c:out value="${violation.message}"/>
                            </c:if>
                        </c:forEach>
                    </div>
                </fieldset>
                <fieldset>
                    <input class="<c:if test="${not empty validateResult}">is-invalid</c:if>"
                           value="<c:if test="${entity != null}">${entity.email}</c:if>" placeholder="Your Email"
                           type="email" name="email" id="email" tabindex="3" required>
                    <div class="invalid-feedback">
                        <c:forEach var="violation" items="${validateResult}">
                            <c:if test="${violation.field eq 'email'}">
                                <c:out value="${violation.message}"/>
                            </c:if>
                        </c:forEach>
                    </div>
                </fieldset>
                <fieldset>
                    <button name="submit" type="submit" id="web-form-submit" data-submit="...Sending">Register</button>
                </fieldset>
                <p class="center">Already have an account? <a href="/home?command=loginPage">Sign In</a></p>
            </form>
        </div>
    </div>
    </body>
    </html>
</fmt:bundle>