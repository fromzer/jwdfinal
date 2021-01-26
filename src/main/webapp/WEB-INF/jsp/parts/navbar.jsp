<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:bundle basename="pagecontent">
    <nav class="navbar navbar-expand-sm bg-dark navbar-dark">
        <div class="container-fluid">
            <div class="navbar-header">
                <span class="navbar-brand"><fmt:message key="page.menu.title"/></span>
            </div>
            <div class="collapse navbar-collapse" id="myNavbar">
                <ul class="nav navbar-nav mr-auto">
                    <li><a href="/home?command=main"><fmt:message key="page.menu.home"/></a></li>
                    <c:if test="${currentUser!=null}">
                        <li><a href="/user?command=allUserRequests"><fmt:message key="page.menu.request"/></a></li>
                        <li><a href="/user?command=profile"><fmt:message key="page.menu.profile"/></a></li>
                        <li><a href="/user?command=toWriteMessagePage"><fmt:message key="page.menu.writeMessage"/></a>
                        </li>
                    </c:if>
                    <c:if test="${currentUser!=null && currentUser.role == 'ADMIN'}">
                        <li class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#"><fmt:message
                                    key="page.menu.environment"/><span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li><a href="/admin?command=showAllUsers"><fmt:message
                                        key="page.menu.environment.users"/></a></li>
                                <li><a href="/admin?command=toEditConferencesPage"><fmt:message
                                        key="page.menu.environment.conf"/></a></li>
                                <li><a href="/admin?command=showNewRequests"><fmt:message
                                        key="page.menu.environment.requests"/></a></li>
                                <li><a href="/admin?command=messages"><fmt:message
                                        key="page.menu.environment.messages"/></a></li>
                            </ul>
                        </li>
                    </c:if>
                </ul>
                <ul class="nav navbar-nav navbar-right mr-3">
                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">Lang<span class="caret"/></a>
                        <ul class="dropdown-menu lang" aria-labelledby="navbarDropdown">
                            <li><a href="/home?command=setLocale&locale=en_US">EN</a></li>
                            <li><a href="/home?command=setLocale&locale=ru_RU">RU</a></li>
                            <li><a href="/home?command=setLocale&locale=ru_BY">BY</a></li>
                        </ul>
                    </li>
                    <li>
                        <a href="#" class="disabled">
                            <c:if test="${currentUser!=null}"><fmt:message
                                    key="page.menu.authMessage"/>${currentUser.firstName}</c:if>
                            <c:if test="${currentUser==null}"><fmt:message key="page.menu.authInMessage"/></c:if>
                        </a>
                    </li>
                    <li>
                        <c:if test="${currentUser!=null}">
                        <a href="/user?command=logout"><span class="glyphicon glyphicon-log-out"></span>
                                <fmt:message key="page.menu.logout"/>
                            </c:if>
                            <c:if test="${currentUser==null}">
                            <a href="/home?command=loginPage"><span class="glyphicon glyphicon-log-in"></span>
                                <fmt:message key="page.menu.login"/>
                                </c:if>
                            </a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</fmt:bundle>