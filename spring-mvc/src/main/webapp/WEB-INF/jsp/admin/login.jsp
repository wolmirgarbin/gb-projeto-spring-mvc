<%@ page contentType="text/html; charset=ISO-8859-1" language="java" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<jsp:include page="../include/head.jsp" />

<div class="page-login clearfix radius-2">
	<div class="fl lg-lvia">
		<img alt="Viasoft" src="${ applicationPath }resources/img/logo.png">
	</div>
	<div class="fr">
		<form action="${ applicationPath }admin" method="post">
			<span class="cai-label">Acesso restrito ao administrador</span>
			<div class="field blank">
				<input type="text" name="usuario" placeholder="Informe o usuário" maxlength="20" >
			</div>
			<div class="field blank">
				<input type="password" name="senha" placeholder="Informe a senha">
			</div>
			<div class="actions">
				<button type="submit">
					<span class="btn-text">Acessar</span>
				</button>
			</div>
		</form>
	</div>
</div>

<jsp:include page="../include/rodape.jsp" />