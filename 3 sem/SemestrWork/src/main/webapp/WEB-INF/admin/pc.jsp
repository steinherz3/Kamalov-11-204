<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>PC</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
</head>
<body>
<%@include file="./part/navbar.jsp"%>

<div class="container">
        <h2>Create PC</h2>
        <div class="form-group">
            <label for="id">Id:</label>
            <input type="number" class="form-control" id="id" name="id" required>
        </div>
        <div class="form-group">
            <label for="from">Time from:</label>
            <input type="time" class="form-control" id="from" name="from" required>
        </div>
        <div class="form-group">
            <label for="to">Time to:</label>
            <input type="time" class="form-control" id="to" name="to" required>
        </div>
        <a class="btn btn-primary" id="pc_create">Create PC</a>
        <a class="btn btn-primary" id="pc_update">Update PC</a>
        <a class="btn btn-primary" id="pc_delete">Delete PC</a>
</div>

<script>
    $('#pc_create').on('click', function () {
        $.ajax({
            type: "POST",
            url: 'http://localhost:8080/admin/pc',
            data: {
                from: $('#from').val(),
                to: $('#to').val()
            },
            success: function (){
                location.reload()
            }
        })
    });
</script>

<script>
    $('#pc_update').on('click', function () {
        $.ajax({
            type: "POST",
            url: 'http://localhost:8080/admin/pc/update',
            data: {
                pc_id: $('#id').val(),
                from: $('#from').val(),
                to: $('#to').val()
            },
            success: function (){
                location.reload()
            }
        })
    });
</script>

<script>
    $('#pc_delete').on('click', function () {
        $.ajax({
            type: "POST",
            url: 'http://localhost:8080/admin/pc/delete',
            data: {
                pc_id: $('#id').val(),
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