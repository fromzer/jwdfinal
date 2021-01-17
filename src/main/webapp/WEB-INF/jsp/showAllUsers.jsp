<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Users</title>
    <c:import url="parts/common.jsp"/>
</head>
<body>
    <div class="container">
        <c:import url="parts/navbar.jsp"/>
        <h2>List Users</h2>
        <table class="table table-striped">
            <thead>
            <tr>
                <th>Login</th>
                <th>First name</th>
                <th>Last name</th>
                <th>@Email</th>
                <th>Role</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="row" items="${showAllUsers}">
                <tr>
                    <td>${row.login}</td>
                    <td>${row.firstName}</td>
                    <td>${row.lastName}</td>
                    <td>${row.email}</td>
                    <td>${row.role}</td>
                    <td>
                        <a class="btn btn-primary" href="/home?command=editUser&userId=${row.id}" role="button">Edit</a>
                        <a class="btn btn-primary" href="/home?command=deleteUser&userId=${row.id}" role="button">Delete</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
