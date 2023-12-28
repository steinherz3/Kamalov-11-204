<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Remove your Pet!</title>
    <style>
        .headLine{
            font-size: 50px; 
            font-family: sans-serif; 
            font-weight: 800; 
            color: brown;
        }
        .select-css{
            display: block; 
font-size: 18px; 
font-family: sans-serif; 
font-weight: 800; 
color: #444; 
line-height: 1.3; 
padding: .6em 1.4em .5em .8em; width: 25%; 
max-width: 100%; 
box-sizing: border-box; 
margin: 0; 
border: 1px solid #aaa;
 box-shadow: 0 1px 0 1px rgba(0,0,0,.04); 
border-radius: .5em;
 -moz-appearance: none;
 -webkit-appearance: none;
 appearance: none;
 background-color: #fff; 
background-image: url('data:image/svg+xml;charset=US-ASCII,%3Csvg%20xmlns%3D%22http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg%22%20width%3D%22292.4%22%20height%3D%22292.4%22%3E%3Cpath%20fill%3D%22%23007CB2%22%20d%3D%22M287%2069.4a17.6%2017.6%200%200%200-13-5.4H18.4c-5%200-9.3%201.8-12.9%205.4A17.6%2017.6%200%200%200%200%2082.2c0%205%201.8%209.3%205.4%2012.9l128%20127.9c3.6%203.6%207.8%205.4%2012.8%205.4s9.2-1.8%2012.8-5.4L287%2095c3.5-3.5%205.4-7.8%205.4-12.8%200-5-1.9-9.2-5.5-12.8z%22%2F%3E%3C%2Fsvg%3E'), linear-gradient(to bottom, #ffffff 0%,#e5e5e5 100%); 
background-repeat: no-repeat, repeat;
background-position: right .7em top 50%, 0 0;
background-size: .65em auto, 100%; 
} 
 .select-css::-ms-expand { display: none; } 
 .select-css:hover { border-color: #888; } 
 .select-css:focus { border-color: #aaa; 
 box-shadow: 0 0 1px 3px rgba(59, 153, 252, .7);
 box-shadow: 0 0 0 3px -moz-mac-focusring; 
color: #222;
 outline: none; 
        }
        .select-css option { font-weight:normal; } 
 *[dir="rtl"] .select-css, :root:lang(ar) .select-css, :root:lang(iw) .select-css { 
background-position: left .7em top 50%, 0 0; 
padding: .6em .8em .5em 1.4em; 
}
.form-style-5{
        	max-width: 500px;
        	padding: 10px 20px;
        	background: #f4f7f8;
        	margin: 10px auto;
        	padding: 20px;
        	background: #f4f7f8;
        	border-radius: 8px;
        	font-family: Georgia, "Times New Roman", Times, serif;
        }
        .form-style-5 fieldset{
        	border: none;
        }
        .form-style-5 legend {
        	font-size: 1.4em;
        	margin-bottom: 10px;
        }
        .form-style-5 label {
        	display: block;
        	margin-bottom: 8px;
        }
        .form-style-5 input[type="text"],
        .form-style-5 input[type="date"],
        .form-style-5 input[type="datetime"],
        .form-style-5 input[type="email"],
        .form-style-5 input[type="number"],
        .form-style-5 input[type="search"],
        .form-style-5 input[type="time"],
        .form-style-5 input[type="url"],
        .form-style-5 textarea,
        .form-style-5 select {
        	font-family: Georgia, "Times New Roman", Times, serif;
        	background: rgba(255,255,255,.1);
        	border: none;
        	border-radius: 4px;
        	font-size: 15px;
        	margin: 0;
        	outline: 0;
        	padding: 10px;
        	width: 100%;
        	box-sizing: border-box;
        	-webkit-box-sizing: border-box;
        	-moz-box-sizing: border-box;
        	background-color: #e8eeef;
        	color:#8a97a0;
        	-webkit-box-shadow: 0 1px 0 rgba(0,0,0,0.03) inset;
        	box-shadow: 0 1px 0 rgba(0,0,0,0.03) inset;
        	margin-bottom: 30px;
        }
        .form-style-5 input[type="text"]:focus,
        .form-style-5 input[type="date"]:focus,
        .form-style-5 input[type="datetime"]:focus,
        .form-style-5 input[type="email"]:focus,
        .form-style-5 input[type="number"]:focus,
        .form-style-5 input[type="search"]:focus,
        .form-style-5 input[type="time"]:focus,
        .form-style-5 input[type="url"]:focus,
        .form-style-5 textarea:focus,
        .form-style-5 select:focus{
        	background: #d2d9dd;
        }
        .form-style-5 select{
        	-webkit-appearance: menulist-button;
        	height:35px;
        }
        .form-style-5 .number {
        	background: #1abc9c;
        	color: #fff;
        	height: 30px;
        	width: 30px;
        	display: inline-block;
        	font-size: 0.8em;
        	margin-right: 4px;
        	line-height: 30px;
        	text-align: center;
        	text-shadow: 0 1px 0 rgba(255,255,255,0.2);
        	border-radius: 15px 15px 15px 0px;
        }

        .form-style-5 input[type="submit"],
        .form-style-5 input[type="button"]
        {
        	position: relative;
        	display: block;
        	padding: 19px 39px 18px 39px;
        	color: #FFF;
        	margin: 0 auto;
        	background: #1abc9c;
        	font-size: 18px;
        	text-align: center;
        	font-style: normal;
        	width: 100%;
        	border: 1px solid #16a085;
        	border-width: 1px 1px 3px;
        	margin-bottom: 10px;
        }
        .form-style-5 input[type="submit"]:hover,
        .form-style-5 input[type="button"]:hover
        {
        	background: #109177;
        }
    </style>
</head>
<body>
    <body background="https://centroveterinariostolz.com/wp-content/uploads/2020/07/sintomas-pulgas-en-gatos-1536x1024.jpg">
        <label for="petName" class="headLine">Choose your Pet, that should be updated</label>
        <select form="removeForm" name="petId" class="select-css" id="petName" name="petName" required>
            <option disabled selected>Choose Pet</option>
            <c:forEach items="${pets}" var="pet">
                <option class="select-css" value="${pet.id}">${pet.name}</option>
            </c:forEach>
        </select>
    
        <form id="removeForm" class="form-style-5" method="post" action="/removePet">
            
            <label for="removeButton">Delete Pet</label>
            <input id="removeButton" type="submit" value="Remove">
        </form>
</body>
</html>