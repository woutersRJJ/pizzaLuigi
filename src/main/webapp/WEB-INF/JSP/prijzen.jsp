<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%@ taglib prefix='spring' uri='http://www.springframework.org/tags' %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Prijzen</title>
</head>
<body>
	<h1>Prijzen</h1>
	<c:forEach var='prijs' items='${prijzen}'>
		<c:url value='/pizzas' var='url'>
			<c:param name='prijs' value='${prijs}' />
		</c:url>
		<li>
			<a href='${url}'>${prijs}</a>
		</li>
	</c:forEach>
	
	<c:if test='${not empty pizzas }'>
		<h2>${prijs}</h2>
		<ul>
			<c:forEach var='pizza' items='${pizzas}'>
				<spring:url var='url' value='/pizzas/{id}'>
					<spring:param name='id' value='${pizza.id}'/>
				</spring:url>
				<li>
					<a href='${url}'>${pizza.naam}</a>
				</li>
			</c:forEach>
		</ul>
	</c:if>
</body>
</html>