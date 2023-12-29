<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PC List</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
</head>
<body>
<%@include file="./part/navbar.jsp" %>
<div class="container">
    <div class="container">
        <form action="<c:url value="/pc"/>" method="get">
            <h2>Search PC on time</h2>
            <div class="form-group">
                <label for="from">Time from:</label>
                <input type="time" class="form-control" id="from" name="from" required>
            </div>
            <div class="form-group">
                <label for="to">Time to:</label>
                <input type="time" class="form-control" id="to" name="to" required>
            </div>
            <div class="form-group">
                <label for="date">Date:</label>
                <input type="date" class="form-control" id="date" name="date" required>
            </div>
            <button type="submit" class="btn btn-primary" id="pc_find">Get results</button>
        </form>
    </div>
    <table class="table" id="pc_table">
        <thead>
        <tr>
            <th>PC id</th>
            <th>Start time</th>
            <th>End time</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="pc" items="${pc_list}">
            <tr>
                <td>${pc.id}</td>
                <td>${pc.startWorkingTime}</td>
                <td>${pc.endWorkingTime}</td>
                <td><a href="/pc/detail?pc_id=${pc.id}" class="btn btn-primary">View Details</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>