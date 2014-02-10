<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div id="messageValidator" class="validator">
	<div class="validatorMessage bt shadow radius-2">
		<div class="mes-title ${ not empty messageSucess and messageSucess ? 'sucesso' : '' }">Mensagem</div>
		<div class="mes-message">
			<c:if test="${ not empty message }">
				<span>${ message }</span>
			</c:if>
			<form:errors path="usuario.usuario"/>
			<c:if test="${ empty validSenha or validSenha }">
				<form:errors path="usuario.senha"/>
			</c:if>
		</div>
	</div>
</div>