<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Welkompagina</title>
</head>
<body>
		<p>${identificatie.emailAdres}</p>
		<h1>Pizzeria Luigi</h1>
		<h2>${boodschap}</h2>
		
		<c:url value='/pizzas' var='url' />	
		<a href='${url}'>pizza's</a>	
</body>
</html>