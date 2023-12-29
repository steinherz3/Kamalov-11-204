<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Navbar</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    <style>
        .right_box{
            position: absolute;
            right: 10px;
        }
    </style>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="#">Navbar</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
            aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link" data-target="/home">Home</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" data-target="/pc">PC</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" data-target="/tournament">Tournaments</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" data-target="/stock">Stock</a>
            </li>
        </ul>
        <div class="right_box">
            <c:if test="${empty user}">
                <ul class="nav justify-content-end">
                    <li class="btn-group mx-auto" role="group" aria-label="Basic outlined example">
                        <a class="btn btn-primary" href="<c:url value="/login"/>" role="button">Sign In</a>
                        <a class="btn btn-dark" href="<c:url value="/register"/>" role="button">Sign Up</a>
                    </li>
                </ul>
            </c:if>
            <c:if test="${not empty user}">
                <div class="dropdown">
                    <button class="btn btn-primary dropdown-toggle" type="button" data-bs-toggle="dropdown"
                            id="dropdownMenuButton" aria-haspopup="true" aria-expanded="false">
                            ${user.getUsername()}
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                             class="bi bi-person-circle" viewBox="0 0 16 16">
                            <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"></path>
                            <path fill-rule="evenodd"
                                  d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"></path>
                        </svg>
                    </button>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" href="<c:url value="/profile"/>">Profile</a></li>
                        <li><a class="dropdown-item" href="<c:url value="/edit"/>">Edit</a></li>
                        <c:if test="${user.getRole().getValue() == 'admin'}">
                            <li><a class="dropdown-item" href="<c:url value="/admin"/>">Redact</a></li>
                        </c:if>
                        <li>
                            <hr class="dropdown-divider">
                        </li>
                        <li>
                            <form action="<c:url value="/home"/>" method="post">
                                <button class="dropdown-item btn-primary" name="logout" value="out">Log out</button>
                            </form>
                        </li>
                    </ul>
                </div>
            </c:if>
        </div>
    </div>
</nav>

<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
<script>
    $('.nav-link').on('click', function (event) {
        event.preventDefault();
        const url = $(this).data('target');
        location.replace(url)
    });
</script>
</body>
</html>