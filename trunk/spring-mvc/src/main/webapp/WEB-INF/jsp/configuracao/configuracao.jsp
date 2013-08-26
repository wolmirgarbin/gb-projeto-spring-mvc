<%@ page contentType="text/html; charset=ISO-8859-1" language="java" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="../include/topo.minha.area.jsp" />

<div class="container">
	<div class="formulario">
		<form action="${ applicationPath }minha-area/configuracao" method="post">
			<h3>Configuração Portal</h3>
			
			<div class="separa-config"> * E-mail para notificações ao administrador ( apenas recebe notifições do portal DF-e )</div>
			<div class="field">
				<label for="identificacao">E-mail para notificações</label>
				<input class="g-xmaior mail" type="text" id="emailNotificacao" maxlength="200" name="emailNotificacao" value="${ config.emailNotificacao }">
			</div>
			
			<div class="separa-config"> * E-mail para leitura das notas de entrada ( o portal efetuará a varedura de e-mail em busca de documentos fiscais )</div>
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
				<input class="g-xmaior" type="text" id="loadSenha" maxlength="100" name="loadSenha" value="${ config.loadSenha }">
			</div>
			<div class="field">
				<label for="loadUsuario">Usuário e-mail leitura</label>
				<input class="g-xmaior" type="text" id="loadUsuario" maxlength="100" name="loadUsuario" value="${ config.loadUsuario }">
			</div>
			<div class="field">
				<label for="loadDebug">Usar debug leitura</label>
				<select name="loadDebug">
					<option value="N" ${ config.loadDebug == 'N' ? 'selected' : ''  }>Não</option>
					<option value="S" ${ config.loadDebug == 'S' ? 'selected' : ''  }>Sim</option>
				</select>
			</div>
			
			
			
			<div class="actions">
				<button id="pesquisarFiltroHome" type="submit">
					<span class="btn-text">Salvar</span>
				</button>
			</div>
		</form>
	</div>
	<%-- <br>
	<div class="formulario">
		<form action="${ applicationPath }minha-area/configuracao/endereco" method="post">
			<h3>Dados da Empresa</h3>
			<span>* Para não mostrar o endereço na capa, deixar o campo em branco.</span>
			
			<div class="field">
				<label for="nome">Nome</label>
				<input class="g-xmaior" type="text" id="nome" maxlength="80" name="nome" value="${ empresa.nome }">
			</div>
			<div class="field">
				<label for="identificacao">CPF/CNPJ</label>
				<input class="g-xmaior" type="text" id="identificacao" maxlength="20" name="identificacao" value="${ empresa.identificacao }">
			</div>
			<div class="field">
				<label for="rua">Rua</label>
				<input class="g-xmaior" type="text" id="rua" maxlength="60" name="rua" value="${ empresa.rua }">
			</div>
			<div class="field">
				<label for="bairro">Bairro</label>
				<input class="g-xmaior" type="text" id="bairro" maxlength="60" name="bairro" value="${ empresa.bairro }">
			</div>
			<div class="field">
				<label for="cidade">Cidade</label>
				<input class="g-xmaior" type="text" id="cidade" maxlength="100" name="cidade" value="${ empresa.cidade }">
			</div>
			<div class="field">
				<label for="uf">UF</label>
				<input class="g-xmaior" type="text" id="uf" maxlength="2" name="uf" value="${ empresa.uf }">
			</div>
			<div class="field">
				<label for="cep">Cep</label>
				<input class="g-xmaior" type="text" id="cep" maxlength="10" name="cep" value="${ empresa.cep }">
			</div>
			
			<div class="actions">
				<button id="pesquisarFiltroHome" type="submit">
					<span class="btn-text">Salvar</span>
				</button>
			</div>
		</form>
	</div> --%>
</div>

<jsp:include page="../include/rodape.jsp" />