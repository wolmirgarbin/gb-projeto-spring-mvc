<%@ page contentType="text/html; charset=ISO-8859-1" language="java" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="head.jsp" />

<div class="topo-site min">
	<div class="top-sup-container">
		<div class="container">
			<div class="top-sup-logo-cliente">
				<c:if test="${ not empty empresa }">
					<a href="${ empresa.config.site }" target="_blank">
						<img alt="Logo Cliente" src="${ empresa.config.logo }">
					</a>
				</c:if>
				<c:if test="${ empty empresa }">
					<a href="#"><img alt="Logo Cliente" src=""></a>
				</c:if>
			</div>
		</div>
	</div>
</div>
<div class="container cont-menu">
	<div class="menu fl">
		<c:if test="${ roleDoAdministrador eq usuarioLogado.role }">
			<a class="m-item enviados ${ menuPage eq 'home' ? 'active' : '' }" href="${ applicationPath }minha-area">Enviados</a>
			<a class="m-item recebidos ${ menuPage eq 'recebidos' ? 'active' : '' }" href="${ applicationPath }minha-area/recebidos">Recebidos</a>
			<a class="m-item usuarios ${ menuPage eq 'usuarios' ? 'active' : '' }" href="${ applicationPath }minha-area/usuarios/all">E-mail / Conta</a>
			<a class="m-item configuracao ${ menuPage eq 'configuracao' ? 'active' : '' }" href="${ applicationPath }minha-area/configuracao">Configuração</a>
			<a class="m-item chave ${ menuPage eq 'senhas' ? 'active' : '' }" href="${ applicationPath }minha-area/senhas">Senha</a>
		</c:if>
		<c:if test="${ roleDoAdministrador != usuarioLogado.role }">
			<a class="m-item recebidos ${ menuPage eq 'home' ? 'active' : '' }" href="${ applicationPath }minha-area">Recebidos</a>
			<a class="m-item chave ${ menuPage eq 'senhas' ? 'active' : '' }" href="${ applicationPath }minha-area/senhas">Senha</a>
		</c:if>
	</div>
	<div class="usuario fr">${ not empty usuarioLogado ? usuarioLogado.usuario : '' } ( <a href="${ applicationPath }usuario/logout">Sair do sistema</a> )</div>
</div>
