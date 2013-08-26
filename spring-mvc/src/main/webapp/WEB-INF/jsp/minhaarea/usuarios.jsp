<%@ page contentType="text/html; charset=ISO-8859-1" language="java" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>

<jsp:include page="../include/topo.minha.area.jsp" />

<div class="container">

	<c:if test="${ linkVerTodos }">
		<div class="alerta radius-2">Informe os e-mails dos usuários listados</div>
	</c:if>

	<c:if test="${ not linkVerTodos }">
		<div class="formulario for-minha-area clearfix">
			<h3>Filtrar usuários</h3>
			<div class="field">
				<label for="nome">Nome do usuário</label>
				<input type="text" name="nome" id="nome" maxlength="100">
			</div>
			<div class="field">
				<label for="identificacao">CPF / CNPJ</label>
				<input type="text" name="identificacao" id="identificacao" maxlength="14" class="numeric">
			</div>
			<div class="actions action-tela-home">
				<button type="button" id="pesquisarUsuarios">
					<span class="ui-icon ui-icon-search"></span>
					<span class="btn-text">Pesquisar</span>
				</button>
			</div>
		</div>
	</c:if>
	
	<div class="table-list">
	
		<c:if test="${ not empty mostrarLink and mostrarLink }">
			<c:if test="${ linkVerTodos }">
				<a class="topo-a" style="${ quantidadeUsuarioSemEmail > 0 ? '' : 'display: none;' }" href="${ applicationPath }minha-area/usuarios/all">Mostrar todos os usuários</a>
			</c:if>
			<c:if test="${ not linkVerTodos }">
				<a class="topo-a mostrar-apenas-email" style="${ quantidadeUsuarioSemEmail > 0 ? '' : 'display: none;' }" href="${ applicationPath }minha-area/usuarios/ajustar-email">Mostrar apenas os que não possuem e-mail</a>
			</c:if>
		</c:if>
		
		<table id="table-usuarios">
			<thead>
				<tr>
					<th width="380px"><span class="text">Nome</span></th>
					<th width="200px"><span class="text">CPF / CNPJ</span></th>
					<th><span class="text">E-mail</span></th>
				</tr>
			</thead>
			<tbody class="custom-usuarios">
				<c:forEach var="pessoaEmpresa" items="${ listPessoas }">
					<tr class="line border ${ empty pessoaEmpresa.pessoa.emailPrincipal ? 'active' : '' }">
						<td class="nome"><span>${ pessoaEmpresa.pessoa.nome }</span></td>
						<td><span>${ pessoaEmpresa.pessoa.identificacao }</span></td>
						<td><input class="save-email" type="text" data-id="${ pessoaEmpresa.pessoa.id }" value="${ not empty pessoaEmpresa.pessoa.emailPrincipal ? pessoaEmpresa.pessoa.emailPrincipal : '' }"></td>
					</tr>
				</c:forEach>
				<c:if test="${ empty listPessoas }">
					<tr class="line border">
						<td class="nome"><span>Não existem pessoas sem e-mail no portal</span></td>
					</tr>
				</c:if>
			</tbody>
		</table>
		
		<c:if test="${ not empty mostrarLink and mostrarLink and not linkVerTodos }">
			<a id="carregarMaisRegistrosUsuario" class="carregar" style="${ countPessoas > fn:length(listPessoas) ? '' : 'display: none;'}" href="javascript:void(0)">+ Mostrar mais registros</a>
		</c:if>
	</div>
</div>

<jsp:include page="../include/rodape.jsp" />