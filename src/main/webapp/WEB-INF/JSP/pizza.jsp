<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Pizza</title>
</head>
<body>
	<c:if test='${empty pizza}'>
		<h2>Pizza niet gevonden</h2>
	</c:if>
	<c:if test='${not empty pizza}'>
		<h2>Pizza ${pizza.naam}</h2>
		<ul>
			<li>${pizza.prijs} ${inDollar} DOLLAR</li>
			<li>${pizza.pikant}</li>
		</ul>
	</c:if>
</body>
</html>