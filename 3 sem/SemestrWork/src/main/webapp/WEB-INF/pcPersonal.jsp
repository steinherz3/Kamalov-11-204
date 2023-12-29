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
    <c:if test="${empty pc}">
        <h2>Sorry, but PC with this id could not be founded.</h2>
    </c:if>
    <c:if test="${not empty pc}">
        <div class="container">
        <h2>PC ${pc.id}</h2>
        <h3>Working from: ${pc.startWorkingTime}</h3>
        <h3>Working to: ${pc.endWorkingTime}</h3>
        <c:if test="${not empty user}">
            <div class="container">
                <h2>Search PC on time</h2>
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
                <a class="btn btn-primary" id="create_booking">Create booking</a>
            </div>
            </div>
            <table class="table" id="booking_table">
            <thead>
            <tr>
                <th>From</th>
                <th>To</th>
                <th>Status</th>
                <th>Delete</th>
            </tr>
            </thead>
            <tbody>
        </c:if>
        <c:forEach var="booking" items="${booking_list}">
            <tr>
                <td>${booking.timeFrom}</td>
                <td>${booking.timeTo}</td>
                <td>${booking.status.getValue()}</td>
                <td><a id="delete_booking" data-target="${booking.id}" class="btn btn-primary delete_booking">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
        </table>
    </c:if>
</div>

<script>
    $('#create_booking').on('click', function () {
        $.ajax({
            type: "POST",
            url: 'http://localhost:8080/pc/detail',
            data: {
                date: $('#date').val(),
                from: $('#from').val(),
                to: $('#to').val(),
                pc_id: ${pc.id}
            },
            success: function () {
                location.reload()
            }
        })
    });
</script>

<script>
    $('.delete_booking').on('click', function () {
        const id = $(this).data('target');
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/booking/delete',
            data: {
                booking_id: id
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
