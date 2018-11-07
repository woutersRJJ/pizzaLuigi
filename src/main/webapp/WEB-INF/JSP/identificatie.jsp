<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ taglib prefix='spring' uri='http://www.springframework.org/tags'%>
<%@ taglib prefix='form' uri='http://www.springframework.org/tags/form'%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
</head>
<body>
	<h1>Identificatie</h1>
	
	<c:url value='/identificatie' var='url' />
	<form:form action='${url}' modelAttribute='identificatie' method='post'
		id='identificatieForm'>
		<form:label path='emailAdres'>Email adres:
			<form:errors path='emailAdres' />
		</form:label>
		<form:input path='emailAdres' autofocus='autofocus'
			required='required' />
		<input type='submit' value='OK' id='okKnop'>
	</form:form>
</body>
</html>