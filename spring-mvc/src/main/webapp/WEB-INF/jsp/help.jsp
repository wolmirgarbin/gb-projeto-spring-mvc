<%@ page contentType="text/html; charset=ISO-8859-1" language="java" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<html>
<head><title>Help WS</title></head>
<body>
	<h2>Help WS</h2>
	<p>Parametros necessarios para enviar arquivos no sistema DFE</p>
	<ul>
		<li>
			<strong>Código de acesso ao portal</strong>
			<br />
			<span>Código fornecido pelo criador do sistema para validação da requisição, se não informado não salva nenhum arquivo</span>
			<br />
			<span>Parametro obrigatório: chave=codigo-chave</span>
			<br />
			<br />
		</li>
		<li>
			<strong>CPF/CNPJ empresa</strong>
			<br />
			<span>Precisa estar cadastrada no banco de dados para poder salvar qualquer requisição, se não encontrar não grava.</span>
			<br />
			<span>Parametro obrigatório: identificacao=cpf-cnpj</span>
			<br />
			<br />
		</li>
		<li>
			<strong>Arquivos a gravar</strong>
			<br />
			<span>No maxímo 10 arquivos, xmls e pdf outras extenções não são consideradas.</span>
			<br />
			<span>Parametro obrigatório: dfes=bytes dos arquivos</span>
			<br />
			<br />
		</li>
	</ul>
</body>
</html>