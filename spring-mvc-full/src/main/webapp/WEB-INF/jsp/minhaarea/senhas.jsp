<%@ page contentType="text/html; charset=ISO-8859-1" language="java" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>

<jsp:include page="../include/topo.minha.area.jsp" />

<div class="container">

	<c:if test="${ not empty usuarioLogado && usuarioLogado.mudouSenha eq 'N' }">
		<div class="alerta radius-2">Sua senha foi gerada automáticamente pelo sistema, recomendamos que altere sua senha.</div>
	</c:if>

	<div class="formulario">
		<h3>Alterar senha</h3>
		<form action="${ applicationPath }minha-area/senhas" method="post">
			<div class="field">
				<label for="senhaAtual">Senha atual</label>
				<input type="password" name="senhaAtual" id="senhaAtual">
				<form:errors path="atualizaSenhaTO.senhaAtual" cssClass="erros"/>
			</div>
			<div class="field">
				<label for="senhaNova">Nova senha</label>	
				<input type="password" name="senhaNova" id="senhaNova">
				<form:errors path="atualizaSenhaTO.senhaNova" cssClass="erros"/>
			</div>
			<div class="field">
				<label for="senhaConfirma">Confirmação de senha</label>
				<input type="password" name="senhaConfirma" id="senhaConfirma">
				<form:errors path="atualizaSenhaTO.senhaConfirma" cssClass="erros"/>
			</div>
			<div class="actions">
				<button type="submit">Salvar</button>
			</div>
		</form>
	</div>
</div>

<jsp:include page="../include/rodape.jsp" />