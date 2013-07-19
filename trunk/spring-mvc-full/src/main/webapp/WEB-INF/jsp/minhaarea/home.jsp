<%@ page contentType="text/html; charset=ISO-8859-1" language="java" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<jsp:include page="../include/topo.minha.area.jsp" />

<c:set value="${ roleDoAdministrador eq usuarioLogado.role }" var="isAdm"/>

<div class="container">
	
	<c:if test="${ not empty quantidadeUsuarioSemEmail && quantidadeUsuarioSemEmail > 0 }">
		<div class="alerta radius-2">
			Tem ${ quantidadeUsuarioSemEmail } pessoa${ quantidadeUsuarioSemEmail > 1 ? 's' : '' } sem e-mail cadastrado, <a href="${ applicationPath }minha-area/usuarios/ajustar-email">cadastrar e-mails agora</a>.
		</div>
	</c:if>

	<div class="formulario for-minha-area clearfix">
	
		<input type="hidden" value="${ recebidos ? 'recebidos' : 'enviados' }" id="tipoBusca">
	
		<h3>Filtrar arquivos ${ recebidos ? 'recebidos' : 'enviados' } por:</h3>
		<div class="field">
			<label for="dataInicial">Data de emissão</label>
			<input class="date" type="text" id="dataInicial" maxlength="10">
			<input class="date" type="text" id="dataFinal" maxlength="10">
		</div>
		<c:if test="${ isAdm }">
			<div class="field">
				<label for="situacao">Número</label>
				<input class="g-menor numeric" type="text" id="situacao" maxlength="8">
			</div>
			<div class="field">
				<label for="nome">Nome Destinatário</label>
				<input class="g-maior" type="text" id="nome" maxlength="100">
			</div>
			<div class="field">
				<label for="identificacao">CPF / CNPJ</label>
				<input class="g-maior numeric" type="text" id="identificacao" maxlength="14">
			</div>
		</c:if>
		<div class="actions action-tela-home">
			<button id="pesquisarFiltroHome" type="button">
				<span class="ui-icon ui-icon-search"></span>
				<span class="btn-text">Pesquisar</span>
			</button>
		</div>
	</div>
	<div class="table-list">
		<table id="table-list">
			<thead>
				<tr>
					<th width="320px">
						<span class="ui-icon black ui-icon-more show-all"></span>
						<span class="text" style="float: left; width: 93%;">Chave de acesso</span>
					</th>
					<th width="80px">
						<span class="text">Emissão</span>
					</th>
					<th width="70px">
						<span class="text">Número</span>
					</th>
					<th>
						<span class="text">${ recebidos ? 'Emissor' : 'Destinatário' }</span>
					</th>
					<th width="105px">
						<span class="text">CPF / CNPJ</span>
					</th>
					<th>
						<span class="text">${ recebidos ? 'Meu e-mail' : 'E-mail cliente' }</span>
					</th>
					<!-- <th width="26px" align="left"><input type="checkbox"></th> -->
				</tr>
			</thead>
			<tbody></tbody>
		</table>
		<div class="clearfix">
			<!-- <a class="radius-2 carregar" id="carregarMaisRegistrosHome" href="javascript:void(0)">Mostrar próximos 10 registros de acordo com os filtros</a>
			<a class="radius-2 downloadAll" id="downloadAll" href="javascript:void(0)">Fazer download dos arquivos selecionados</a> -->
		</div>
	</div>
</div>

<jsp:include page="../include/rodape.jsp" />