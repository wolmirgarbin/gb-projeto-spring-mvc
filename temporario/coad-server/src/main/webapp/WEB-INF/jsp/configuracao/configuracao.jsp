<%@ page contentType="text/html; charset=ISO-8859-1" language="java" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="../include/topo.minha.area.jsp" />

<div class="container">
	
	<c:choose>
		<c:when test="${ not empty alterarVisualizacao and alterarVisualizacao }">
			<div class="alerta radius-2">
				Para acessar esta tela precisa estar com uma empresa selecionada.
			</div>
		</c:when>
		<c:otherwise>
		
			<div class="informacao-conf">
				<h2>Importante!</h2>
				<p>Para enviar as notas por e-mail precisa ser feito um redirecionamento do e-mail que recebe as notas de sua empresa
				para o e-mail <b>${ applicationProperties.loadEmail }</b>, ap�s este processo finalizado o sistema far� o carregamento 
				automaticamente para dentro do portal.</p>
			</div>
		
			<div class="formulario">
				<form action="${ applicationPath }minha-area/configuracao-fuso" method="post">
					<h3>Fuso Hor�rio</h3>
					
					<div class="separa-config"> * Informe o fuso hor�rio com base no hor�rio de Brasilia</div>
					<div class="field">
						<label>Fuso Hor�rio</label> <b>${ empresa.nome }</b>
					</div>
					<div class="field">
						<label for="fusoHorario">Fuso Hor�rio</label>
						<select name="fusoHorario" style="width: 480px;">
							<c:forEach items="${ lsFusoHorario }" var="utc">
								<option value="${ utc.valor }" ${ empresa.utc eq utc.valor ? 'selected' : '' }>UTC${ utc.valor }  - ${ utc.descricao }</option>
							</c:forEach>
						</select>
					</div>
					<div class="actions">
						<button id="pesquisarFiltroHome" type="submit">
							<span class="btn-text">Salvar</span>
						</button>
					</div>
				</form>
			</div>
			
			<div class="formulario">
				<form action="${ applicationPath }minha-area/configuracao-conf" method="post">
					<h3>Notifica��es</h3>
					
					<c:if test="${ not empty usuarioLogado.contratante }">
						<div class="separa-config"> * E-mail para notifica��es ao administrador ( apenas recebe notifica��es do portal DF-e e � utilizado por todas as empresas )</div>
						<div class="field">
							<label for="identificacao">E-mail para notifica��es</label>
							<input class="g-xmaior mail" type="text" id="emailNotificacao" maxlength="200" name="emailNotificacao" value="${ usuarioLogado.contratante.email }">
						</div>
					</c:if>
					
					<%-- <div class="separa-config"> * E-mail para leitura das notas de entrada ( o portal efetuar� a varedura de e-mail em busca de documentos fiscais )</div>
					<div class="field">
						<label for="loadHost">Host do e-mail leitura</label>
						<input class="g-xmaior" type="text" id="loadHost" maxlength="100" name="loadHost" value="${ config.loadHost }">
					</div>
					<div class="field">
						<label for="loadEmail">E-mail para leitura</label>
						<input class="g-xmaior" type="text" id="loadEmail" maxlength="250" name="loadEmail" value="${ config.loadEmail }">
					</div>
					<div class="field">
						<label for="loadSenha">Senha e-mail leitura</label>
						<input class="g-xmaior" type="password" id="loadSenha" maxlength="100" name="loadSenha" value="${ config.loadSenha }">
						<input type="checkbox" id="ocultarSenhaEmailLeitura"> <span>Mostrar senha</span>
					</div>
					<div class="field">
						<label for="loadUsuario">Usu�rio e-mail leitura</label>
						<input class="g-xmaior" type="text" id="loadUsuario" maxlength="100" name="loadUsuario" value="${ config.loadUsuario }">
					</div>
					<div class="field">
						<label for="loadAutenticted">Autenticar</label>
						<select name="loadAutenticted" style="width: 95px;">
							<option value="N" ${ config.loadAutenticted == 'N' ? 'selected' : ''  }>N�o</option>
							<option value="S" ${ config.loadAutenticted == 'S' ? 'selected' : ''  }>Sim</option>
						</select>
					</div>
					<div class="field">
						<label for="loadPort">Porta de leitura</label>
						<input class="g-menor numeric" type="text" id="loadPort" maxlength="100" name="loadPort" value="${ config.loadPort }">
					</div>
					<div class="field">
						<label for="loadProtocolo">Porta de leitura</label>
						<select name="loadProtocolo" style="width: 95px;">
							<c:forEach items="${ lsTiposProtocolo }" var="item">
								<option value="${ item.name() }" ${ config.loadProtocolo == item.name() ? 'selected' : ''  }>${ item.name() }</option>
							</c:forEach>
						</select>
					</div> --%>
					
					<div class="actions">
						<button id="pesquisarFiltroHome" type="submit">
							<span class="btn-text">Salvar</span>
						</button>
					</div>
				</form>
			</div>
			
		</c:otherwise>
	</c:choose>
</div>

<jsp:include page="../include/rodape.jsp" />