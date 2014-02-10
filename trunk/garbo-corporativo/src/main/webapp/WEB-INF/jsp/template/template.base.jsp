<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
<tiles:insertAttribute name="head"/>
</head>
<body class="${ browser }">
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
<div class="tudo">
	<header>
		<tiles:insertAttribute name="topo"/>
	</header>
	<tiles:insertAttribute name="conteudo"/>
	<footer>
		<tiles:insertAttribute name="rodape"/>
	</footer>
</div>
<tiles:insertAttribute name="javascript"/>
</body></html>