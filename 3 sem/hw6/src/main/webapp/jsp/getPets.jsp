<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" language="java" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>All Pets</title>
    <style>
        table {
  background: #012B39;
  border-radius: 1.25em;
  border-collapse: collapse;
  margin: 1em;
}
h1 {
    color: #245460;
    font-size: 2.85em;
    font-weight: 600;
    padding: 0.5em 1em;
    text-align: left;
}
th {
  border-bottom: 1px solid #364043;
  color: #E2B842;
  font-size: 1.85em;
  font-weight: 600;
  padding: 0.5em 1em;
  text-align: left;
}
td {
  color: #fff;
  font-size: 1em;
  font-weight: 400;
  padding: 0.65em 1em;
}
tbody tr {
  transition: background 0.25s ease;
  font-size: 2em;
}
tbody tr:hover {
  background: #014055;
}
    </style>
</head>
<body background="https://mobimg.b-cdn.net/v3/fetch/e2/e2835ecbfa81891b747fec6a6e1456e7.jpeg">
    <table id="petsTable">
        <h1>Pets List</h1>
        <thead>
            <tr>
                <th>Id</th>
                <th>Pet Name</th>
                <th>Age</th>
                <th>Height</th>
                <th>Color</th>
                <th>Sex</th>
                <th>Account Name</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${petList}" var="pet">
                          <tr>
                            <td>${pet.id}</td>
                            <td>${pet.name}</td>
                            <td>${pet.age}</td>
                            <td>${pet.height}</td>
                            <td>${pet.color}</td>
                            <td>${pet.sex}</td>
                            <td>${pet.account.name}</td>
                          </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>