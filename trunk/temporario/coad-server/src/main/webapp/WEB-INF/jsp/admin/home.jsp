<%@ page contentType="text/html; charset=ISO-8859-1" language="java" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<jsp:include page="../include/head.jsp" />

<jsp:include page="topo-adm.jsp" />

<div class="container">

	<div class="adm-ajuda">
		<a href="javascript:void(0)" id="opemHelp">O que fazer nesta tela?</a>
		<ul id="opemHelpUl">
			<li>Cada contratante é a pessoa/empresa que contratou o serviço de armazenamento do DF-e</li>
			<li>Para novas contratações clique em 'Adicionar novo contratante' e preencha o cadastro</li>
			<li>Para editar um contratante escolha a opção dentro do grid 'Editar' ou 'Empresas'</li>
		</ul>
	</div>
	
	<a href="${applicationPath}admin/contratante">Adicionar novo contratante</a>
	<div class="table-list">
		<table>
			<thead>
				<tr>
					<th width="80px">
						<span class="text">Código</span>
					</th>
					<th>
						<span class="text">Nome</span>
					</th>
					<th width="200px">
						<span class="text">E-mail</span>
					</th>
					<th width="110px">
						<span class="text">Fone</span>
					</th>
					<th width="60px">
						<span class="text">Pacote</span>
					</th>
					<th width="200px">
						<span class="text">OPÇÕES</span>
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ lsContratante }" var="contratante">
					<tr class="line border">
						<td><span class="center">${ contratante.id }</span></td>
						<td><span>${ contratante.nome }</span></td>
						<td><span class="center">${ contratante.email }</span></td>
						<td><span class="center">(${ contratante.foneDDD }) ${ contratante.foneNumero }</span></td>
						<td><span class="center">${ contratante.pacote }</span></td>
						<td>
							<span class="center">
								<a href="${ applicationPath }admin/contratante?id=${ contratante.id }">Editar</a>
								|
								<a href="${ applicationPath }admin/empresas?contratante=${ contratante.id }">Empresas</a>
								|
								<a href="${ applicationPath }admin/relatorio?contratante=${ contratante.id }">Relatório</a>
							</span>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
</div>

<jsp:include page="../include/rodape.jsp" />