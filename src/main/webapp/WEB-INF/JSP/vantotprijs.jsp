<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix='spring' uri='http://www.springframework.org/tags'%>
<%@taglib prefix='form' uri='http://www.springframework.org/tags/form'%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>pagina met prijsformulier</title>
</head>
<body>
	<c:url value='/pizzas' var='url' />
	<form:form action='${url}' modelAttribute='vanTotPrijsForm' method='get'>
		<form:label path='van'>van:
			<form:errors path='van'/>
		</form:label>
		<form:input path='van' autofocus='autofocus' type='number' required='required' />
		<form:label path='tot'>tot:
			<form:errors path='tot'/>
		</form:label>
		<form:input path='tot' type='number' required='required' />
		<input type='submit' value='Zoeken'>
		<form:errors cssClass='fout'/>
	</form:form>
	<c:if test='${not empty pizzas}'>
		<ul>
			<c:forEach var='pizza' items='${pizzas}'>
				<spring:url value='/pizzas/{id}' var='url'>
					<spring:param name='id' value='${pizza.id}' />
				</spring:url>
				<li>
					<a href='${url}'>${pizza.naam}</a>
				</li>
			</c:forEach>
		</ul>
	</c:if>	
</body>
</html>