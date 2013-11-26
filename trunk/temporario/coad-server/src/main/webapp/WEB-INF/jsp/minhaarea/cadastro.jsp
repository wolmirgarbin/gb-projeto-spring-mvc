<%@ page contentType="text/html; charset=ISO-8859-1" language="java" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>


<jsp:include page="../include/topo.minha.area.jsp" />

<div class="container">

	<c:choose>
		<c:when test="${ not empty alterarVisualizacao and alterarVisualizacao }">
			<div class="alerta radius-2">
				Para acessar esta tela precisa estar com uma empresa selecionada.
			</div>
		</c:when>
		<c:otherwise>
		
			<div class="adm-ajuda" style="margin-top: 10px;">
				<a href="javascript:void(0)" id="opemHelp">O que fazer nesta tela?</a>
				<ul id="opemHelpUl">
					<li>Informe o CPF ou CNPJ e clique em continuar</li>
					<li>Caso j� esteja cadastrado na base de dados pode permitir acesso a um usu�rio ou cadastr�-lo</li>
					<li>� mostrado na tela a lista de usu�rios que tem acesso a empresa, pode remover este acesso sempre que precisar.</li>
				</ul>
			</div>
		
			<c:if test="${ identificacao }">
				<div class="formulario">
					<h3>Informe o CPF ou CNPJ para cadastrar um usu�rio</h3>
					<div class="field">
						<label for="identificacao">CPF ou CNPJ</label>
						<input type="text" name="identificacao" id="identificacao" class="numeric" maxlength="14">
					</div>
					<div class="actions">
						<button type="button" onclick="window.location.href = '${applicationPath}cadastro/usuario/'+ document.getElementById('identificacao').value;">Continuar</button>
					</div>
				</div>
				<br>
				<br>
				<p>Usu�rios que podem acessar a empresa: <b>${ usuarioLogado.empresaBase.nome } (${ usuarioLogado.empresaBase.identificacao })</b></p>
				
				<div class="table-list">
					<table>
						<thead>
							<tr>
								<th>
									<span class="text">Nome</span>
								</th>
								<th width="200px">
									<span class="text">CPF / CNPJ</span>
								</th>
								<th width="200px">
									<span class="text">Permiss�o</span>
								</th>
								<th width="105px">
									<span class="text">Op��es</span>
								</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${ lsUsuarioEmpresa }" var="usuarioEmpresa">
								<tr class="line border">
									<td>
										<span class="text">${ usuarioEmpresa.usuario.pessoa.nome }</span>
									</td>
									<td>
										<span class="center">${ usuarioEmpresa.usuario.usuario }</span>
									</td>
									<td>
										<span class="center">${ usuarioEmpresa.usuario.role eq 'ROLE_ADMINISTRADOR' ? 'Acesso total' : 'Visualiza��o e download' } </span>
									</td>
									<td>
										<span class="center">
											<c:choose>
												<c:when test="${ usuarioEmpresa.usuario.usuario eq usuarioLogado.empresaBase.identificacao }">
													Usu�rio principal
												</c:when>
												<c:otherwise>
													<a href="${ applicationPath }cadastro/desvincular/${ usuarioEmpresa.usuario.id }">Remover acesso</a>
												</c:otherwise>
											</c:choose>
										</span>
									</td>
								</tr>
							</c:forEach>
							<c:if test="${ fn:length(lsUsuarioEmpresa) eq 0 }">
								<tr>
									<td colspan="3">Nenhum usu�rio vinculado a empresa</td>
								</tr>
							</c:if>
						</tbody>
					</table>
				</div>
			</c:if>
			
			
			<c:if test="${ not identificacao }">
				<c:choose>
					<c:when test="${ not empty usuario }">
						<!-- mostra esta parte apenas se tem usu�rio de acordo com o CPF ou CNPJ que foi informado -->
						<div class="alerta radius-2">
							Tem um usu�rio cadastrado no portal com este ${ fn:length(usuario.pessoa.identificacao) == 11 ? 'CPF' : 'CNPJ' }
						</div>
						<br><br>
						
						<span>Nome <b>${ usuario.pessoa.nome }</b></span><br>
						<span>Nome Fantasia <b>${ usuario.pessoa.fantasia }</b></span><br>
						<span>Identifica��o <b>${ usuario.pessoa.identificacao }</b></span><br>
						<span>E-mail <b>${ usuario.pessoa.emailPrincipal }</b></span><br>
						<br>
						<p>Deseja liberar acesso a este usu�rio para a empresa: <b>${ usuarioLogado.empresaBase.nome } (${ usuarioLogado.empresaBase.identificacao })</b>?</p>
						<br>
						<form action="${ applicationPath }cadastro/vincular" method="post">
							<input type="hidden" name="usuario" value="${ usuario.id }">
							<button type="submit">
								<span>Liberar acesso</span>
							</button>
						</form>
					</c:when>
					<c:otherwise>
						<p>Usuario n�o est� cadastrado, informe os campos abaixo para continuar.</p>
						<div class="formulario">
							<h3>Cadastro de usu�rio</h3>
							<form action="${ applicationPath }cadastro/usuario-save" method="post">
								<div class="field">
									<label for="identificacao">CPF / CNPJ</label>
									<input type="text" name="identificacao" id="identificacao" class="numeric" maxlength="14" readonly="readonly" value="${ identificacaoCampo }">
								</div>
								<div class="field">
									<label for="email">E-mail *</label>
									<input type="text" name="email" id="email">
								</div>
								<div class="field">
									<label for="nome">Nome *</label>
									<input type="text" name="nome" id="nome" maxlength="60">
								</div>
								<div class="field">
									<label for="fantasia">Fantasia *</label>
									<input type="text" name="fantasia" id="fantasia" maxlength="60">
								</div>
								<div class="actions">
									<button type="submit">Salvar</button>
								</div>
							</form>
						</div>
					</c:otherwise>
				</c:choose>
			</c:if>
		
		</c:otherwise>
	</c:choose>
</div>

<jsp:include page="../include/rodape.jsp" />