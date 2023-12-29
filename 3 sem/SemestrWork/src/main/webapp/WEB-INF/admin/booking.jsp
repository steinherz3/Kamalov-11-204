<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>PC</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
</head>
<body>

<%@include file="./part/navbar.jsp" %>

<div class="container">
    <table class="table" id="booking_table">
        <thead>
        <tr>
            <th>User id</th>
            <th>PC id</th>
            <th>From</th>
            <th>To</th>
            <th>Status</th>
            <th>Confirm</th>
            <th>Deny</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="booking" items="${booking_list}">
            <tr>
                <td>${booking.userId}</td>
                <td>${booking.pcId}</td>
                <td>${booking.timeFrom}</td>
                <td>${booking.timeTo}</td>
                <td>${booking.status.getValue()}</td>
                <td><a id="confirm_booking" data-target="${booking.id}"
                       class="btn btn-primary confirm_booking">Confirm</a>
                <td><a id="deny_booking" data-target="${booking.id}" class="btn btn-primary deny_booking">Deny</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<script>
    $('.confirm_booking').on('click', function () {
        const id = $(this).data('target');
        $.ajax({
            type: "POST",
            url: 'http://localhost:8080/admin/booking',
            data: {
                booking_id: id,
                status: 'ACCEPTED'
            },
            success: function () {
                location.reload()
            }
        })
    });
</script>

<script>
    $('.deny_booking').on('click', function () {
        const id = $(this).data('target');
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/admin/booking',
            data: {
                booking_id: id,
                status: 'DENIED'
            },
            success: function () {
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
