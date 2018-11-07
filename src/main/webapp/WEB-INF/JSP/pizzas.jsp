<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%@ taglib prefix='spring' uri='http://www.springframework.org/tags' %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Pizza Luigi</title>
</head>
<body>
	<c:if test='${not empty param.boodschap}'>
		<div class='boodschap'>${param.boodschap}</div>
	</c:if>
	<h1>Pizza's</h1>
	<ul>
		<c:forEach var='pizza' items='${pizzas }'>	
			<li>
<!-- 				tegen hacking -->
				<c:out value='${pizza.naam}' />
				<spring:url value='/pizzas/{id}' var='url'>
					<spring:param name='id' value='${pizza.id}' />
				</spring:url>
				<a href='${url}'>Detail</a>
			</li>
		</c:forEach>	
	</ul>
	<c:url value='/pizzas/prijzen' var='url'/>
	<a href='${url}'>Prijzen</a>
	
	<c:url value='/pizzas/toevoegen' var='url'/>
	<a href='${url}'>Toevoegen</a>
</body>
</html>