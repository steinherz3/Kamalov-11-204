<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login Page</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
</head>
<body>
<%@include file="./part/navbar.jsp"%>

<div class="container">
    <h2>Search PC on time</h2>
    <form action="<c:url value="/test"/>" method="post">
        <div class="form-group">
            <label for="date">Date:</label>
            <input type="date" class="form-control" id="date" name="date" required>
        </div>
        <div class="form-group">
            <label for="from">Time from:</label>
            <input type="time" class="form-control" id="from" name="from" required>
        </div>
        <div class="form-group">
            <label for="to">Time to:</label>
            <input type="time" class="form-control" id="to" name="to" required>
        </div>
        <button type="submit" class="btn btn-primary">Отправить</button>
    </form>
</div>

<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
</body>
</html>