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
			<li>Visualizar o guia de instalação e vídeo demonstrativo</li>
		</ul>
	</div>
	
	<h3>1) O que é o ROBÔ DF-e</h3>
	<p align="justify">
		O Sistema DF-e integra-se de maneira fácil com qualquer software, consumindo apenas informações que estejam contidas no XML da nota. Uma das formas
		de entrada de notas fiscais no Sistema é através de web service, e é neste ponto que entra o ROBÔ DF-e. Ele é um software que roda vasculhando uma 
		pasta onde o Sistema Gerencial grava as notas, e sempre que o ROBÔ encontra um novo arquivo, ele envia para o Portal DF-e automaticamente. 
	</p>
	<br>
	<h3>2) Baixando o ROBÔ DF-e</h3>
	<p align="justify">
		É muito fácil baixar e instalar o ROBÔ. Basta clicar em download e executar o arquivo jnlp (para que isso seja possível precisa ter o Java 7 - <a href="http://www.java.com/pt_BR/download/" target="_blank">baixar/instalar agora</a> - ou superior instalado). 
		Após isso o sistema será instalado automaticamente na máquina. 
	</p>
	<br>
	<button type="button" onclick="window.location.href='${ applicationPath }app-robo/launch.jnlp'">
		<span class="ui-icon ui-icon-arrowthickstop-1-s"></span>
		<span class="btn-text">Download do ROBÔ</span>
	</button>
	<br>
	<h3>3) Configurando o ROBÔ DF-e</h3>
	<p align="justify">O ROBÔ DF-e tem apenas uma tela, e não requer nenhuma instalação complexa. Após a instalação siga os três passos abaixo:</p>
	<p align="justify">- Passo 1: No campo "CPF / CNPJ" informe todos os CPFs ou CNPJs das Empresas (Matriz ou Filial) que estão cadastradas no portal DF-e separado-os por vírgula, por exemplo: (12345678912,12345678912345);</p>
	<p align="justify">- Passo 2: No campo "Diretório de leitura" informe o diretório principal onde ficarão as notas;</p>
	<p align="justify">- Passo 3: Estando preenchidos os campos acima, clique em Salvar Configurações.</p>
	<p align="justify">Sempre que o Sistema for aberto, utilizará estas configurações para ler e enviar as notas, não precisando de nenhuma outra configuração adicional.</p>
</div>

<jsp:include page="../include/rodape.jsp" />