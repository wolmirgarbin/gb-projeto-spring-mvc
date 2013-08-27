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
			<strong>C�digo de acesso ao portal</strong>
			<br />
			<span>C�digo fornecido pelo criador do sistema para valida��o da requisi��o, se n�o informado n�o salva nenhum arquivo</span>
			<br />
			<span>Parametro obrigat�rio: chave=codigo-chave</span>
			<br />
			<br />
		</li>
		<li>
			<strong>CPF/CNPJ empresa</strong>
			<br />
			<span>Precisa estar cadastrada no banco de dados para poder salvar qualquer requisi��o, se n�o encontrar n�o grava.</span>
			<br />
			<span>Parametro obrigat�rio: identificacao=cpf-cnpj</span>
			<br />
			<br />
		</li>
		<li>
			<strong>Arquivos a gravar</strong>
			<br />
			<span>No max�mo 10 arquivos, xmls e pdf outras exten��es n�o s�o consideradas.</span>
			<br />
			<span>Parametro obrigat�rio: dfes=bytes dos arquivos</span>
			<br />
			<br />
		</li>
	</ul>
</body>
</html>