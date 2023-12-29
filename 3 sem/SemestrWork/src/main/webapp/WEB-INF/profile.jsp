<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tournament</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
</head>
<body>

<%@include file="./part/navbar.jsp" %>

<div class="container">
    <c:if test="${empty user}">
        <h2>Something goes wrong.</h2>
    </c:if>
    <c:if test="${not empty user}">
        <div class="container">
        <h2>Id: ${user.id}</h2>
        <h3>Name: ${user.username}</h3>
        <h3>Email: ${user.email}</h3>
        <h3>Role: ${user.role.getValue()}</h3>
    </c:if>
</div>

<script src="https://code.jquery.com/jquery-3.6.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
