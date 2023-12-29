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
    <table class="table" id="tournament_table">
        <thead>
        <tr>
            <th>Stock id</th>
            <th>Name</th>
            <th>Count</th>
            <th>Price</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="stock" items="${stock_list}">
            <tr>
                <td>${stock.id}</td>
                <td>${stock.name}</td>
                <td>${stock.count}</td>
                <td>${stock.price}</td>
                <td><a id="stock_order" data-target="${stock.id}" class="btn btn-primary stock_order">Order</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<script>
    $('.stock_order').on('click', function () {
        const id = $(this).data('target');
        $.ajax({
            type: "POST",
            url: 'http://localhost:8080/stock',
            data: {
                stock_id: id
            },
            success: function (){
                location.reload()
            }
        })
    });
</script>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>