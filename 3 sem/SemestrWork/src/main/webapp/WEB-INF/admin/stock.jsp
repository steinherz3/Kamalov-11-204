<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Stock</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
</head>
<body>
<%@include file="./part/navbar.jsp"%>

<div class="container">
    <h2>Create Stock</h2>
    <div class="form-group">
        <label for="id">Id:</label>
        <input type="number" class="form-control" id="id" name="id" required>
    </div>
    <div class="form-group">
        <label for="name">Name:</label>
        <input type="text" class="form-control" id="name" name="name" required>
    </div>
    <div class="form-group">
        <label for="count">Count:</label>
        <input type="number" class="form-control" id="count" name="count" required>
    </div>
    <div class="form-group">
        <label for="price">Price:</label>
        <input type="number" class="form-control" id="price" name="price" required>
    </div>
    <a class="btn btn-primary" id="stock_create">Create Stock</a>
    <a class="btn btn-primary" id="stock_update">Update Stock</a>
    <a class="btn btn-primary" id="stock_delete">Delete Stock</a>
</div>

<script>
    $('#stock_create').on('click', function () {
        $.ajax({
            type: "POST",
            url: 'http://localhost:8080/admin/stock',
            data: {
                name: $('#name').val(),
                count: $('#count').val(),
                price: $('#price').val()
            },
            success: function (){
                location.reload()
            }
        })
    });
</script>

<script>
    $('#stock_update').on('click', function () {
        $.ajax({
            type: "POST",
            url: 'http://localhost:8080/admin/stock/update',
            data: {
                stock_id: $('#id').val(),
                name: $('#name').val(),
                count: $('#count').val(),
                price: $('#price').val()
            },
            success: function (){
                location.reload()
            }
        })
    });
</script>

<script>
    $('#stock_delete').on('click', function () {
        $.ajax({
            type: "POST",
            url: 'http://localhost:8080/admin/stock/delete',
            data: {
                stock_id: $('#id').val(),
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