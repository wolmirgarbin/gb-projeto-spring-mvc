<%@ page contentType="text/html; charset=ISO-8859-1" language="java" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<jsp:include page="../include/head.jsp" />

<jsp:include page="topo-adm.jsp" />

<div class="container">

	<div class="adm-localizacao">
		<a href="${ applicationPath }admin/home">Voltar para tela inicial</a> / Relatório
	</div>

	<div class="adm-ajuda">
		<a href="javascript:void(0)" id="opemHelp">O que fazer nesta tela?</a>
		<ul id="opemHelpUl">
			<li>Pode visualizar o resultado do carregamento mensal por empresa e um totalizador por contratante</li>
		</ul>
	</div>
	
	<jsp:include page="include.cab.contratante.jsp" />
	
	<br>
	<form action="${ applicationPath }admin/relatorio" method="get">
		<div class="field">
			<span>Contratante </span><input type="text" name="contratante" value="${ contratante.id }" readonly="readonly" class="g-mini">
			&nbsp;|&nbsp;
			<span>Mês </span><input type="text" class="numeric g-mini" name="mes" maxlength="2" value="${ mes }">
			<span> / Ano </span><input type="text" class="numeric g-mini" name="ano" maxlength="4" value="${ ano }">
			&nbsp;
			<button type="submit" style="padding: 6px 10px;">Filtrar</button>
		</div>
	</form>
	<br><br>
	<span>Relatório de utilização de pacotes referente ao més ${ mes } de ${ ano }</span>
	<div class="table-list">
		<table>
			<thead>
				<tr>
					<th>
						<span class="text">Empresa</span>
					</th>
					<th width="160px">
						<span class="text">CPF / CNPJ</span>
					</th>
					<th width="160px">
						<span class="text">Local</span>
					</th>
					<th width="160px">
						<span class="text">Usado</span>
					</th>
				</tr>
			</thead>
			<tbody>
				<c:set var="soma" value="0"/>
				<c:forEach items="${ lsEmpresaTO }" var="empresaTO">
					<tr class="line border">
						<td><span class="text">${ empresaTO.nome }</span></td>
						<td><span class="center">${ empresaTO.identificacao }</span></td>
						<td><span class="text">${ empresaTO.cidade } - ${ empresaTO.uf }</span></td>
						<td><span class="center">${ empresaTO.quantidade }</span></td>
					</tr>
					<c:set var="soma" value="${ empresaTO.quantidade + soma }"/>	
				</c:forEach>
				<tr class="line">
					<td></td><td></td><td><span class="text">Soma</span></td><td><span class="center">${ soma } de ${ contratante.pacote }</span></td>
				</tr>
			</tbody>
		</table>
	</div>
	
</div>

<jsp:include page="../include/rodape.jsp" />