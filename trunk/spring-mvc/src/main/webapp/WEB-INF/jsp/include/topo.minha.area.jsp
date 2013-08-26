<%@ page contentType="text/html; charset=ISO-8859-1" language="java" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.garbosystem.com.br/jsp/jstl/core" prefix="garbo" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<jsp:include page="head.jsp" />

<div class="topo-site min">
	<div class="top-sup-container">
		<div class="container">
			<div class="top-sup-logo-cliente">
				<a href="http://www.viasoft.com.br/" target="_blank">
					<img alt="Logo Cliente" src="http://www.viasoft.com.br/img/logo.png">
				</a>
			</div>
			<c:if test="${ not empty usuarioLogado.empresaBase }">
				<div class="upload-sistema radius-2">
					<a class="button-send-dfe" href="javascript:void(0)" id="opemModalUpload">
						<span class="span">Envie seus arquivos</span>
					</a>
				</div>
			</c:if>
		</div>
	</div>
</div>
<div class="container cont-menu">
	<div class="menu fl">
		<c:if test="${ roleDoAdministrador eq usuarioLogado.role or roleDoSupervisor eq usuarioLogado.role }">
			<a class="m-item enviados ${ menuPage eq 'home' ? 'active' : '' }" href="${ applicationPath }minha-area">Enviados</a>
			<a class="m-item recebidos ${ menuPage eq 'recebidos' ? 'active' : '' }" href="${ applicationPath }minha-area/recebidos">Recebidos</a>
			<a class="m-item usuarios ${ menuPage eq 'usuarios' ? 'active' : '' }" href="${ applicationPath }minha-area/usuarios/all">E-mail / Conta</a>
			<a class="m-item configuracao ${ menuPage eq 'configuracao' ? 'active' : '' }" href="${ applicationPath }minha-area/configuracao">Configuração</a>
			<a class="m-item chave ${ menuPage eq 'senhas' ? 'active' : '' }" href="${ applicationPath }minha-area/senhas">Senha</a>
		</c:if>
		<c:if test="${ roleDoAdministrador != usuarioLogado.role and roleDoSupervisor != usuarioLogado.role }">
			<a class="m-item recebidos ${ menuPage eq 'home' ? 'active' : '' }" href="${ applicationPath }minha-area">Recebidos</a>
			<a class="m-item chave ${ menuPage eq 'senhas' ? 'active' : '' }" href="${ applicationPath }minha-area/senhas">Senha</a>
		</c:if>
	</div>
	<div class="usuario fr">
		<div class="fr">
			${ not empty usuarioLogado ? usuarioLogado.usuario : '' } ( <a href="${ applicationPath }usuario/logout">Sair</a> )
		</div>
		<div class="clear"></div>
		<c:if test="${ ( roleDoAdministrador eq usuarioLogado.role or roleDoSupervisor eq usuarioLogado.role ) and fn:length(usuarioLogado.lsUsuarioEmpresa) > 0 }">
			<div class="empresa radius-2" id="menuEmpresas">
				<span class="name fl" title="${ usuarioLogado.empresaBase.nome }">${ garbo:ajustaTexto(usuarioLogado.empresaBase.nome, 14) }</span>
				<span class="ui-icon ui-icon-triangle-1-s"></span>
				
				<ul class="menu radius-2">
					<c:forEach items="${ usuarioLogado.lsUsuarioEmpresa }" var="usuEmp">
						<li>
							<a class="item" href="${ applicationPath }usuario/alterar-empresa/${ usuEmp.empresa.id }">
								${ usuEmp.empresa.nome }
							</a>
							<span class="local">${ usuEmp.empresa.cidade } - ${ usuEmp.empresa.uf }</span>
						</li>
					</c:forEach>
				</ul>
			</div>
		</c:if>
	</div>
</div>
