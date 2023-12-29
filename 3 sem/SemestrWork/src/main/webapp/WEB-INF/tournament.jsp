<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tournament List</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
</head>
<body>
<%@include file="./part/navbar.jsp" %>
<div class="container">
    <div class="container">
        <form action="<c:url value="/tournament"/>" method="get">
            <h2>Search Tournament on time</h2>
            <div class="form-group">
                <label for="from">Time from:</label>
                <input type="datetime-local" class="form-control" id="from" name="from" required>
            </div>
            <div class="form-group">
                <label for="to">Time to:</label>
                <input type="datetime-local" class="form-control" id="to" name="to" required>
            </div>
            <button type="submit" class="btn btn-primary" id="pc_find">Get results</button>
        </form>
    </div>
    <table class="table" id="tournament_table">
        <thead>
        <tr>
            <th>Tournament id</th>
            <th>Name</th>
            <th>Start time</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="tournament" items="${tournament_list}">
            <tr>
                <td>${tournament.id}</td>
                <td>${tournament.name}</td>
                <td>${tournament.startTime}</td>
                <td><a href="/tournament/detail?tournament_id=${tournament.id}" class="btn btn-primary">View Details</a></td>
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