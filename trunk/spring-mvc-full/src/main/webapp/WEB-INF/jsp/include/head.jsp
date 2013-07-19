<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html; charset=ISO-8859-1" language="java" pageEncoding="ISO-8859-1" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
	<title>${ not empty title ? title : 'Portal DFE viasoft' }</title>
	<link rel="stylesheet" href="${ applicationPath }resources/ui-theme-agro/jquery-ui-1.10.3.custom.min.css" />
	<link rel="stylesheet" href="${ applicationPath }resources/css/all-page.css" type="text/css">

	<script type="text/javascript"> var applicationPath = '${ applicationPath }'; </script>

	<link rel="shortcut icon" href="${ applicationPath }resources/img/favicon.ico" type="image/x-icon" />
</head>
<body class="${ browser }"><div class="tudo">


<div class="validator" id="messageValidator">
	<div class="container">
		<div class="validatorMessage bt shadow radius-2">
			<div class="mes-title ${ not empty mensagemSucesso and mensagemSucesso ? 'sucesso' : '' }">Mensagem</div>
			<div class="mes-message">
				<c:if test="${ not empty mensagem }">
					<span>${ mensagem }</span>
				</c:if>
				<form:errors path="usuario.usuario"/>
				<c:if test="${ empty validSenha or validSenha }">
					<form:errors path="usuario.senha"/>
				</c:if>
			</div>
		</div>
	</div>
</div>