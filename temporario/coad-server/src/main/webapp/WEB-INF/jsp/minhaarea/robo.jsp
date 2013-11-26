<%@ page contentType="text/html; charset=ISO-8859-1" language="java" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>

<jsp:include page="../include/topo.minha.area.jsp" />

<div class="container">

	<div class="adm-ajuda" style="margin-top: 10px;">
		<a href="javascript:void(0)" id="opemHelp">O que fazer nesta tela?</a>
		<ul id="opemHelpUl">
			<li>Efetuar o download do robo</li>
			<li>Visualizar o guia de instala��o e v�deo demonstrativo</li>
		</ul>
	</div>
	
	<h3>1) O que � o ROB� DF-e</h3>
	<p align="justify">
		O Sistema DF-e integra-se de maneira f�cil com qualquer software, consumindo apenas informa��es que estejam contidas no XML da nota. Uma das formas
		de entrada de notas fiscais no Sistema � atrav�s de web service, e � neste ponto que entra o ROB� DF-e. Ele � um software que roda vasculhando uma 
		pasta onde o Sistema Gerencial grava as notas, e sempre que o ROB� encontra um novo arquivo, ele envia para o Portal DF-e automaticamente. 
	</p>
	<br>
	<h3>2) Baixando o ROB� DF-e</h3>
	<p align="justify">
		� muito f�cil baixar e instalar o ROB�. Basta clicar em download e executar o arquivo jnlp (para que isso seja poss�vel precisa ter o Java 7 - <a href="http://www.java.com/pt_BR/download/" target="_blank">baixar/instalar agora</a> - ou superior instalado). 
		Ap�s isso o sistema ser� instalado automaticamente na m�quina. 
	</p>
	<br>
	<button type="button" onclick="window.location.href='${ applicationPath }app-robo/launch.jnlp'">
		<span class="ui-icon ui-icon-arrowthickstop-1-s"></span>
		<span class="btn-text">Download do ROB�</span>
	</button>
	<br>
	<h3>3) Configurando o ROB� DF-e</h3>
	<p align="justify">O ROB� DF-e tem apenas uma tela, e n�o requer nenhuma instala��o complexa. Ap�s a instala��o siga os tr�s passos abaixo:</p>
	<p align="justify">- Passo 1: No campo "CPF / CNPJ" informe todos os CPFs ou CNPJs das Empresas (Matriz ou Filial) que est�o cadastradas no portal DF-e separado-os por v�rgula, por exemplo: (12345678912,12345678912345);</p>
	<p align="justify">- Passo 2: No campo "Diret�rio de leitura" informe o diret�rio principal onde ficar�o as notas;</p>
	<p align="justify">- Passo 3: Estando preenchidos os campos acima, clique em Salvar Configura��es.</p>
	<p align="justify">Sempre que o Sistema for aberto, utilizar� estas configura��es para ler e enviar as notas, n�o precisando de nenhuma outra configura��o adicional.</p>
</div>

<jsp:include page="../include/rodape.jsp" />