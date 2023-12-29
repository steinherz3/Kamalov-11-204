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
    <c:if test="${empty tournament}">
        <h2>Sorry, but Tournament with this id could not be founded.</h2>
    </c:if>
    <c:if test="${not empty tournament}">
        <div class="container">
        <h2>PC ${tournament.id}</h2>
        <h3>Name: ${tournament.name}</h3>
        <h3>Description ${tournament.description}</h3>
        <h3>Start on: ${tournament.startTime}</h3>
        <c:if test="${not empty user}">
            <div class="container">
                <a class="btn btn-primary" id="register">Register on tournament</a>
            </div>
            </div>
        </c:if>
        <table class="table" id="user_table">
            <thead>
            <tr>
                <th>Username</th>
                <c:if test="${user.role.getValue()=='admin'}">
                <th>Action</th>
                </c:if>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="user" items="${user_list}">
                <tr>
                    <td>${user.username}</td>
                    <c:if test="${user.role.getValue()=='admin'}">
                    <td><a id="delete_from_tournament" data-target="${user.id}" class="btn btn-primary delete_booking">Delete</a></td>
                    </c:if>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>

<script>
    $('#register').on('click', function () {
        $.ajax({
            type: "POST",
            url: 'http://localhost:8080/tournament/detail',
            data: {
                tournament_id: ${tournament.id}
            },
            success: function (){
                location.reload()
            }
        })
    });
</script>

<script>
    $('.delete_from_tournament').on('click', function () {
        const id = $(this).data('target');
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/tournament/delete',
            data: {
                user_id: id,
                tournament_id: ${tournament.id}
            },
            success: function (){
                location.reload()
            }
        })
    });
</script>

<script src="https://code.jquery.com/jquery-3.6.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
