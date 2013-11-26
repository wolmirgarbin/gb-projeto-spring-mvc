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
					<img alt="Logo Cliente" src="${ applicationPath }resources/img/dfe.png">
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

<div class="cont-menu-linha">
	<div class="info-empresa container">
		<div class="usuario">
			<div class="fr sair">
				<span>Logado com:</span> <b>${ not empty usuarioLogado ? usuarioLogado.usuario : '' }</b> <span>(</span> <a href="${ applicationPath }usuario/logout">Sair</a> <span>)</span>
			</div>
			
			<div class="empresa radius-2 fl" id="menuEmpresas">
				<c:if test="${ not empty usuarioLogado.empresaBase }">
					<span class="name fl" title="${ usuarioLogado.empresaBase.nome }">${ garbo:ajustaTexto(usuarioLogado.empresaBase.nome, 20) }</span>
				</c:if>
				<c:if test="${ empty usuarioLogado.empresaBase }">
					<span class="name fl" title="${ usuarioLogado.pessoa.nome }">${ garbo:ajustaTexto(usuarioLogado.pessoa.nome, 20) }</span>
				</c:if>
				
				<c:if test="${ fn:length(usuarioLogado.lsUsuarioEmpresa) > 0 }">
					<ul class="menu radius-2">
						<li class="item-minhas">
							<a class="item" href="${ applicationPath }usuario/ver-minhas-notas">Minhas notas</a>
						</li>
						<c:forEach items="${ usuarioLogado.lsUsuarioEmpresa }" var="usuEmp">
							<li>
								<a class="item" href="${ applicationPath }usuario/alterar-empresa/${ usuEmp.empresa.id }">
									${ usuEmp.empresa.nome }
								</a>
								<span class="local">${ usuEmp.empresa.cidade } - ${ usuEmp.empresa.uf }</span>
							</li>
						</c:forEach>
					</ul>
				</c:if>
			</div>
			
			<span class="info-identi">Visualizando documentos relacionados com: </span><b>${ not empty usuarioLogado.empresaBase ? usuarioLogado.empresaBase.identificacao : usuarioLogado.usuario }</b>
		</div>
	</div>
	
	<div class="container cont-menu">
		<div class="menu fl">
			<c:if test="${ roleDoAdministrador eq usuarioLogado.role or roleDoSupervisor eq usuarioLogado.role }">
				<a class="m-item enviados ${ menuPage eq 'enviados' ? 'active' : '' }" href="${ applicationPath }minha-area">
					<span class="menu-icon menu-send"></span>
					Enviados
				</a>
				<a class="m-item recebidos ${ menuPage eq 'recebidos' ? 'active' : '' }" href="${ applicationPath }minha-area/recebidos">
					<span class="menu-icon menu-received"></span>
					Recebidos
				</a>
				<a class="m-item recebidos ${ menuPage eq 'outras-notas' ? 'active' : '' }" href="${ applicationPath }minha-area/outras-notas">
					<span class="menu-icon menu-other"></span>
					Outros Doc.
				</a>
				<a class="m-item email ${ menuPage eq 'email' ? 'active' : '' }" href="${ applicationPath }minha-area/usuarios/all">
					<span class="menu-icon menu-message"></span>
					E-mail / Conta
				</a>
				<a class="m-item configuracao ${ menuPage eq 'configuracao' ? 'active' : '' }" href="${ applicationPath }minha-area/configuracao">
					<span class="menu-icon menu-config"></span>
					Configuração
				</a>
				<c:if test="${ not empty usuarioLogado.contratante }">
					<a class="m-item usuarios ${ menuPage eq 'usuarios' ? 'active' : '' }" href="${ applicationPath }cadastro/usuario">
						<span class="menu-icon menu-user"></span>
						Usuários
					</a>
				</c:if>
				<a class="m-item robo ${ menuPage eq 'robo' ? 'active' : '' }" href="${ applicationPath }minha-area/robo">
					<span class="menu-icon menu-robo"></span>
					Robô
				</a>
				<a class="m-item chave ${ menuPage eq 'senhas' ? 'active' : '' }" href="${ applicationPath }minha-area/senhas">
					<span class="menu-icon menu-chave"></span>
					Senha
				</a>
			</c:if>
			<c:if test="${ roleDoAdministrador != usuarioLogado.role and roleDoSupervisor != usuarioLogado.role }">
				<c:if test="${ not empty usuarioLogado.empresaBase }">
					<a class="m-item enviados ${ menuPage eq 'enviados' ? 'active' : '' }" href="${ applicationPath }minha-area">
						<span class="menu-icon menu-send"></span>
						Enviados
					</a>
				</c:if>
				<a class="m-item recebidos ${ menuPage eq 'recebidos' ? 'active' : '' }" href="${ applicationPath }minha-area/recebidos">
					<span class="menu-icon menu-received"></span>
					Recebidos
				</a>
				<a class="m-item recebidos ${ menuPage eq 'outras-notas' ? 'active' : '' }" href="${ applicationPath }minha-area/outras-notas">
					<span class="menu-icon menu-other"></span>
					Outros Doc.
				</a>
				<a class="m-item chave ${ menuPage eq 'senhas' ? 'active' : '' }" href="${ applicationPath }minha-area/senhas">
					<span class="menu-icon menu-chave"></span>
					Senha
				</a>
			</c:if>
		</div>
	</div>
</div>