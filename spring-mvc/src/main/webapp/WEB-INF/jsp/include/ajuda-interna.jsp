<%@ page contentType="text/html; charset=ISO-8859-1" language="java" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${ not empty mostrarOrientacao and mostrarOrientacao }">

	<link rel="stylesheet" href="${ applicationPath }resources/css/joyride-2.1.css" type="text/css">
	
	<c:set var="mostraCompleto" value="${ roleDoAdministrador eq usuarioLogado.role or roleDoSupervisor eq usuarioLogado.role }"/>
	
	<ol id="joyRideTipContent">
		<c:if test="${ mostraCompleto }">
			<li data-class="enviados" data-text="Mais" class="custom">
				<h4>Enviados</h4>
				<p>Todos os documentos eletrônicos enviados(NF-e, DANFE, CT-e).</p>
			</li>
			<li data-class="recebidos" data-button="Mais">
				<h4>Recebidos</h4>
				<p>Todos os documentos eletrônicos recebidos(NF-e, DANFE, CT-e).</p>
			</li>
			<li data-class="usuarios" data-button="Mais">
				<h4>E-mail / Conta</h4>
				<p>O sistema permite informar e-mail de clientes que não possuem nenhum e-mail informado.</p>
			</li>
			<li data-class="configuracao" data-button="Mais">
				<h4>Configuração</h4>
				<p>Nesta opção pode cadastrar um e-mail para notificação e outro para busca de documentos fiscais.</p>
			</li>
			<li data-class="chave" data-button="Mais">
				<h4>Senha</h4>
				<p>Permite alterar a senha do usuário logado no sistema!</p>
			</li>
			<li data-class="button-send-dfe" data-button="Fechar">
				<h4>Upload</h4>
				<p>Pode enviar notas para dentro do sistema por está opção!</p>
			</li>
		</c:if>
		<c:if test="${ not mostraCompleto }">
			<li data-class="recebidos" data-button="Mais">
				<h4>Recebidos</h4>
				<p>Todos os documentos eletrônicos recebidos(NF-e, DANFE, CT-e).</p>
			</li>
			<li data-class="chave" data-button="Fechar">
				<h4>Senha</h4>
				<p>Permite alterar a senha do usuário logado no sistema!</p>
			</li>
		</c:if>
	</ol>
	
	<script type="text/javascript" src="${ applicationPath }resources/js/modernizr.mq.js"></script>
	<script type="text/javascript" src="${ applicationPath }resources/js/jquery.joyride-2.1.js"></script>

	<script type="text/javascript">
		$(window).load(function() {
			$('#joyRideTipContent').joyride({
				autoStart : true,
				/* postStepCallback : function(index, tip) {
					if (index == 2) {
						$(this).joyride('set_li', false, 1);
					}
				}, */
				modal : true,
				expose : true
			});
		});
	</script>
</c:if>