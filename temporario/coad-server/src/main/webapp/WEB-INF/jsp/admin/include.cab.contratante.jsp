<%@ page contentType="text/html; charset=ISO-8859-1" language="java" pageEncoding="ISO-8859-1" %>

<div class="cabecalho">
	<h3>Contratante</h3>
	<span>${ contratante.nome }</span>
	<span>${ contratante.email }</span>
	<span>(${ contratante.foneDDD }) ${ contratante.foneNumero }</span>
	<span>${ contratante.complemento }</span>
	<span>Pacote: <b>${ contratante.pacote }</b></span>
</div>