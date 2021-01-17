<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:bundle basename="pagecontent">
    <html>
    <head>
        <title>Edit user</title>
        <c:import url="parts/common.jsp"/>
    </head>
    <body>
    <div class="container">
        <c:import url="parts/navbar.jsp"/>
        <div class="form-container">
            <form id="web-form" action="/home?command=updateUserInfo" method="post">
                <h3>Update user info</h3>
                <fieldset>
                    <label for="login"><b>Login</b></label>
                    <input placeholder="Your Login"
                           type="text" name="login" id="login" value="${userData.login}"
                           disabled tabindex="1" required>
                </fieldset>
                <fieldset>
                    <label for="firstName"><b>First name</b></label>
                    <input class="<c:if test="${not empty validateResult}">is-invalid</c:if>"
                           placeholder="Your First Name" type="text" name="firstName" id="firstName"
                           value="${entity ne null?entity.firstName:userData.firstName}" tabindex="2" required
                           autofocus>
                    <div class="invalid-feedback">
                        <c:forEach var="violation" items="${validateResult}">
                            <c:if test="${violation.field eq 'firstName'}">
                                <c:out value="${violation.message}"/>
                            </c:if>
                        </c:forEach>
                    </div>
                </fieldset>
                <fieldset>
                    <label for="lastName"><b>Last name</b></label>
                    <input class="<c:if test="${not empty validateResult}">is-invalid</c:if>"
                           placeholder="Your Last Name" type="text" name="lastName" id="lastName" tabindex="3"
                           value="${entity ne null?entity.lastName:userData.lastName}" required>
                    <div class="invalid-feedback">
                        <c:forEach var="violation" items="${validateResult}">
                            <c:if test="${violation.field eq 'lastName'}">
                                <c:out value="${violation.message}"/>
                            </c:if>
                        </c:forEach>
                    </div>
                </fieldset>
                <fieldset>
                    <label for="psw"><b>Password change</b></label>
                    <input class="<c:if test="${not empty validateResult}">is-invalid</c:if>"
                           placeholder="Your Password" type="password" name="psw" id="psw" tabindex="4">
                    <div class="invalid-feedback">
                        <c:forEach var="violation" items="${validateResult}">
                            <c:if test="${violation.field eq 'password'}">
                                <c:out value="${violation.message}"/>
                            </c:if>
                        </c:forEach>
                    </div>
                </fieldset>
                <fieldset>
                    <label for="pswRepeat"><b>Repeat Password</b></label>
                    <input class="<c:if test="${not empty validateResult}">is-invalid</c:if>"
                           placeholder="Repeat Your Password" type="password" name="pswRepeat" id="pswRepeat"
                           tabindex="5">
                    <div class="invalid-feedback">
                        <c:forEach var="violation" items="${validateResult}">
                            <c:if test="${violation.field eq 'passwordRepeat'}">
                                <c:out value="${violation.message}"/>
                            </c:if>
                        </c:forEach>
                    </div>
                </fieldset>
                <fieldset>
                    <label for="email"><b>Email</b></label>
                    <input class="<c:if test="${not empty validateResult}">is-invalid</c:if>" placeholder="Your Email"
                           type="email" name="email" id="email" tabindex="3"
                           value="${entity ne null?entity.email:userData.email}" required>
                    <div class="invalid-feedback">
                        <c:forEach var="violation" items="${validateResult}">
                            <c:if test="${violation.field eq 'email'}">
                                <c:out value="${violation.message}"/>
                            </c:if>
                        </c:forEach>
                    </div>
                </fieldset>
                <fieldset>
                    <button name="submit" type="submit" id="web-form-submit" data-submit="...Sending">Update</button>
                </fieldset>
            </form>
        </div>
    </div>
    </body>
    </html>
</fmt:bundle>