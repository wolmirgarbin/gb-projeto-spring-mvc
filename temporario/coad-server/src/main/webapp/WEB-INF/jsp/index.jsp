<%@ page contentType="text/html; charset=ISO-8859-1" language="java" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<jsp:include page="include/head.jsp" />

<div class="topo-site" style="${ (not empty mostrarPlanos and mostrarPlanos) ? 'height:135px' : '' }">
	<div class="top-sup-container">
		<div class="container">
			<div class="top-sup-logo-cliente">
				<a href="${ applicationPath }">
					<img alt="Logo Cliente" src="${ applicationPath }resources/img/dfe.png">
				</a>
			</div>
			<div class="top-sup-login radius-2">
				<a href="http://www.viasoft.com.br/" target="_blank">
					<img alt="Logo Cliente" src="${ applicationPath }resources/img/logo.png">
				</a>
			</div>
		</div>
	</div>
	
	<c:if test="${ not empty isCapa && isCapa }">
		<div class="top-inf-container bt shadow">
			<div class="container clearfix">
				<div class="logo-agro">
					<a href="${ applicationProperties.urlAgro }" target="_blank"><img alt="Agrosoft" src="${ applicationPath }resources/img/dfes-logo.png"></a>
				</div>
				<div class="caixa-login ${ not empty cnpj ? 'amarelo' : '' }">
					<form action="${ applicationPath }" method="post">
						<span class="cai-label">Se já está cadastrado informe seu <b>CPF/CNPJ</b> e <b>senha</b></span>
						<div class="field blank">
							<input type="text" name="usuario" placeholder="CPF/CNPJ" value="${ not empty cnpj ? cnpj : '' }" maxlength="14" class="numeric">
						</div>
						<div class="field blank">
							<input type="password" name="senha" placeholder="Senha">
						</div>
						<div class="actions">
							<button type="submit">
								<span class="btn-text">Acessar</span>
							</button>
						</div>
					</form>
				</div>
				<div class="separa-caixas">
					<span class="cai-label">OU</span>
				</div>
				<div class="caixa-primeiro-ac">
					<form action="${ applicationPath }usuario/enviar-senha" method="post">
						<span class="cai-label">Caso seja seu primeiro acesso ou tenha esquecido sua senha, informe seu <b>CPF/CNPJ</b> e clique em continuar</span>
						<div class="field blank">
							<input type="text" name="usuario" placeholder="CPF/CNPJ" maxlength="14" class="numeric">
						</div>
						<div class="actions">
							<button type="submit">
								<span class="btn-text">Continuar</span>
							</button>
						</div>
					</form>
				</div>
			</div>
		</c:if>
	</div>
</div>

<c:if test="${ not empty isCapa && isCapa }">
	<div class="container download-direto clearfix">
		<div class="titulo">
			Download de Documentos Fiscais
			<div class="line-compl"></div>
		</div>
		<form action="${ applicationPath }download/direto" method="post">
			<div class="passos-capa">
				<span class="etapas">1&deg; Passo - Informe a chave de acesso</span>
				<div class="separador"></div>
				<div class="field radius-2">
					<div class="custom-field">
						<span>NF-e / CT-e</span>
						<input type="text" name="codigo" placeholder="Chave do documento" maxlength="44" class="numeric input-codigo" autocomplete="off">
					</div>
				</div>
			</div>
			<div class="passos-capa">
				<span class="etapas">2&deg; Passo - Informe o texto na caixa ao lado</span>
				<div class="separador"></div>
				<div class="field radius-2">
					<img alt="Captcha" src="${ applicationPath }img-captcha">
					<input type="text" name="answer" placeholder="Digite" maxlength="10" class="input-captcha" autocomplete="off">
				</div>
			</div>
			<div class="passos-capa u">
				<span class="etapas">3&deg; Passo - Download</span>
				<div class="separador"></div>
				<div class="field">
					<button type="submit">
						<span class="ui-icon ui-icon-arrowthickstop-1-s"></span>
						<span class="btn-text">Download</span>
					</button>
				</div>
			</div>
		</form>
	</div>
</c:if>

<jsp:include page="error/acesso.restrito.jsp" />

<jsp:include page="error/pagina.nao.encontrada.jsp" />

<jsp:include page="include/planos.jsp" />

<jsp:include page="include/html.carrossel.jsp" />

<jsp:include page="include/rodape.jsp" />