<%@ page contentType="text/html; charset=ISO-8859-1" language="java" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<jsp:include page="../include/topo.minha.area.jsp" />

<c:set value="${ roleDoAdministrador eq usuarioLogado.role }" var="isAdm"/>
<c:set value="${ roleDoSupervisor eq usuarioLogado.role }" var="isSup"/>

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
		<c:if test="${ isAdm || isSup }">
			<div class="field">
				<label for="situacao">Número</label>
				<input class="g-menor numeric" type="text" id="situacao" maxlength="8">
			</div>
			<div class="field">
				<label for="nome">Nome ${ recebidos ? 'Emissor' : 'Destinatário' }</label>
				<input class="g-maior" type="text" id="nome" maxlength="100">
			</div>
			<div class="field">
				<label for="identificacao">CPF / CNPJ</label>
				<input class="g-maior numeric" type="text" id="identificacao" maxlength="14">
			</div>
			<div class="field" style="width: 600px;">
				<label for="chave">Chave de acesso</label>
				<input class="g-xmaior numeric" type="text" id="chave" maxlength="60">
			</div>
		</c:if>
		<div class="clear" style="height: 10px;"></div>
		<div class="barra-campo">
			<label for="qtdRegistros">Quantidade máxima de</label>
			<select id="qtdRegistros" style="width: 70px;">
				<option value="10">10</option>
				<option value="25">25</option>
				<option value="50">50</option>
				<option value="100">100</option>
			</select>
			<span> registros por página</span>
		</div>
		
		<div class="actions action-tela-home">
			<button id="pesquisarFiltroHome" type="button">
				<span class="ui-icon ui-icon-search"></span>
				<span class="btn-text">Pesquisar</span>
			</button>
		</div>
	</div>
	<div class="table-list">
		
		<div class="all-download" id="barraDownload">
			<div class="container">
				<div class="internal line">	
					* Download máximo de 100 documentos por tipo.
				</div>
				<div class="internal clearfix">
				    <div class="fl">
				        <span class="texto">Quais arquivos precisa?</span>
				        <div class="boxcheck">
				            <input type="checkbox" class="check" id="doc-xml" checked="checked">
				            <label for="doc-xml" class="label">NF-e / CT-e</label>
				        </div>
						<div class="boxcheck">
				            <input type="checkbox" class="check" id="doc-danfe">
				            <label for="doc-danfe" class="label">DANFE</label>
				        </div>
				        <div class="boxcheck">
				            <input type="checkbox" class="check" id="doc-eventos">
				            <label for="doc-eventos" class="label">Eventos</label>
				        </div>
				    </div>
		   			
		   			<div class="fr">
						<!-- <a href="javascript:void(0)" type="button" style="float: right;">
							<span class="ui-icon ui-icon-search"></span>
							<span class="btn-text">Enviar</span>
						</a> -->
						<a href="javascript:void(0)" class="fl command radius-2" id="commandDownload">
							<span class="fl ui-icon ui-icon-arrowthickstop-1-s"></span>
							<span class="fl btn-text">Download</span>
						</a>
						<div class="seta"></div>
					</div>
				</div>
			</div>
		</div>
	
		<table id="table-list">
			<thead>
				<tr>
					<th width="320px">
						<span class="ui-icon black ui-icon-more show-all"></span>
						<span class="text" style="float: left; width: 93%;">Chave de acesso</span>
					</th>
					<th width="66px">
						<span class="text">Emissão</span>
					</th>
					<th width="106px">
						<span class="text">Carregamento</span>
					</th>
					<th width="56px">
						<span class="text">Status</span>
					</th>
					<th width="60px">
						<span class="text">Número</span>
					</th>
					<th>
						<span class="text">${ recebidos ? 'Emissor' : 'Destinatário' }</span>
					</th>
					<th width="105px">
						<span class="text">CPF / CNPJ</span>
					</th>
					<th width="26px" align="left"><input type="checkbox" id="checkAll"></th>
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