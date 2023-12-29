<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Tournament</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
</head>
<body>
<%@include file="./part/navbar.jsp"%>

<div class="container">
    <h2>Create Tournament</h2>
    <div class="form-group">
        <label for="id">Id:</label>
        <input type="number" class="form-control" id="id" name="id" required>
    </div>
    <div class="form-group">
        <label for="name">Name:</label>
        <input type="text" class="form-control" id="name" name="name" required>
    </div>
    <div class="form-group">
        <label for="description">Description:</label>
        <input type="text" class="form-control" id="description" name="description" required>
    </div>
    <div class="form-group">
        <label for="start_time">Time:</label>
        <input type="datetime-local" class="form-control" id="start_time" name="start_time" required>
    </div>
    <a class="btn btn-primary" id="tournament_create">Create Tournament</a>
    <a class="btn btn-primary" id="tournament_update">Update Tournament</a>
    <a class="btn btn-primary" id="tournament_delete">Delete Tournament</a>
</div>

<script>
    $('#tournament_create').on('click', function () {
        $.ajax({
            type: "POST",
            url: 'http://localhost:8080/admin/tournament',
            data: {
                name: $('#name').val(),
                description: $('#description').val(),
                start_time: $('#start_time').val()
            },
            success: function (){
                location.reload()
            }
        })
    });
</script>

<script>
    $('#tournament_update').on('click', function () {
        $.ajax({
            type: "POST",
            url: 'http://localhost:8080/admin/tournament/update',
            data: {
                tournament_id: $('#id').val(),
                name: $('#name').val(),
                description: $('#description').val(),
                start_time: $('#start_time').val()
            },
            success: function (){
                location.reload()
            }
        })
    });
</script>

<script>
    $('#tournament_delete').on('click', function () {
        $.ajax({
            type: "POST",
            url: 'http://localhost:8080/admin/tournament/delete',
            data: {
                tournament_id: $('#id').val(),
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