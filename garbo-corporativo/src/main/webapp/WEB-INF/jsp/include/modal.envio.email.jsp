<%@ page contentType="text/html; charset=ISO-8859-1" language="java" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<div id="modalEnviarNotas">
	<span>* Pode enviar os documentos para o e-mail abaixo ou informar novos e-mails para enviar.</span>
	
	<input type="hidden" class="id-documentos">
	
	<div class="field" style="margin-bottom: 20px;">
		<label for="emailPrincipal">E-mail principal</label>
		<input type="checkbox" id="emailPrincipal" class="email-principal" checked="checked">
		<span></span>
	</div>
	
	<span>* Caso precisar enviar copias para outros e-mails utilizar os campos abaixo</span>
	
	<div class="field">
		<label>CC</label>
		<input class="g-maior email-cc" type="text" placeholder="nome@mail.com">
	</div>
	
	<div class="field">
		<label>CC</label>
		<input class="g-maior email-cc" type="text" placeholder="nome@mail.com">
	</div>
	
	<div class="field">
		<label>CC</label>
		<input class="g-maior email-cc" type="text" placeholder="nome@mail.com">
	</div>
	
	<div class="actions">
		<button type="button">
			<span class="ui-icon ui-icon-mail-closed"></span>
			<span class="btn-text">Enviar</span>
		</button>
		<div id="loaderEnvioEmail" class="loader"></div>
	</div>
</div>