<%@ page contentType="text/html; charset=ISO-8859-1" language="java" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<jsp:include page="../include/head.jsp" />

<jsp:include page="topo-adm.jsp" />

<div class="container">

	<div class="adm-localizacao">
		<a href="${ applicationPath }admin/home">Voltar para tela inicial</a> / Cadastro de contratantes
	</div>

	<div class="adm-ajuda">
		<a href="javascript:void(0)" id="opemHelp">O que fazer nesta tela?</a>
		<ul id="opemHelpUl">
			<li>Cadastrar contratantes</li>
		</ul>
	</div>
</div>

<div class="page-empresas">

	<div class="formulario" style="margin: 10px auto;">
	
		<h3>Cadastro de contratantes do portal DF-e</h3>
		
		<form action="${ applicationPath }admin/contratante-save" method="post">
			<div class="field">
				<label for="id">Código no portal</label>
				<input type="text" name="id" id="id" readonly="readonly" value="${ contratante.id }" class="g-mini">
			</div>
			<div class="field">
				<label for="nome">Nome</label>
				<input type="text" name="nome" id="nome" value="${ contratante.nome }" maxlength="100">
			</div>
			<div class="field">
				<label for="email">E-mail principal</label>
				<input type="text" name="email" id="email" value="${ contratante.email }" maxlength="100">
				<span>* Será enviado a senha do usuário e demais notificações para este e-mail</span>
			</div>
			<div class="field">
				<label for="email">Fone</label>
				(<input type="text" name="foneDDD" id="foneDDD" value="${ contratante.foneDDD }" maxlength="3" class="g-mini numeric">) <input type="text" name="foneNumero" id="foneNumero" value="${ contratante.foneNumero }" maxlength="9" class="numeric">
				
			</div>
			<div class="field">
				<label for="pacote">QTD pacote</label>
				<input type="text" name="pacote" id="pacote" value="${ contratante.pacote }" maxlength="100" class="numeric g-menor">
			</div>
			<div class="field">
				<label for="complemento">Complemento</label>
				<input type="text" name="complemento" id="complemento" value="${ contratante.complemento }" maxlength="100">
			</div>
			
			<div class="actions">
				<button type="submit">Salvar</button>
				<button type="reset">Limpar</button>
				<c:if test="${ not empty contratante }">
					<a href="${ applicationPath }admin/empresas?contratante=${ contratante.id }">Cadastrar empresas</a>
				</c:if>
			</div>
		</form>
	</div>	
</div>

<jsp:include page="../include/rodape.jsp" />