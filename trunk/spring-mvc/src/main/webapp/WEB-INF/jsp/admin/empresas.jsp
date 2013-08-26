<%@ page contentType="text/html; charset=ISO-8859-1" language="java" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<jsp:include page="../include/head.jsp" />

<div class="page-empresas">	

	<div class="formulario" style="margin: 10px auto;">
	
		<br>
		<a href="${ applicationPath }admin/logout">Sair do sistema</a>
		<br><br>
	
		<h3>Manutenção de empresas do portal DF-e</h3>
		
		<form action="${ applicationPath }admin/action/save" method="post">
			<div class="field">
				<label for="id">Código no portal</label>
				<input type="text" name="id" id="id" readonly="readonly" value="${ empresa.id }">
			</div>
			<div class="field">
				<label for="nome">Nome empresa</label>
				<input type="text" name="nome" id="nome" value="${ empresa.nome }">
			</div>
			<div class="field">
				<label for="email">E-mail principal</label>
				<input type="text" name="email" id="email" value="${ empresa.config.emailNotificacao }">
				<span>* Será enviado a senha do usuário e demais notificações para este e-mail</span>
			</div>
			<div class="field">
				<label for="identificacao">CPF / CNPJ</label>
				<input type="text" name="identificacao" id="identificacao" class="numeric" value="${ empresa.identificacao }" maxlength="14" ${ not empty empresa ? 'readonly' : '' }>
				<span>* As serão carregadas apenas as notas que contiverem este CPF / CNPJ</span>
			</div>
			<div class="field">
				<label for="cidade">Cidade</label>
				<input type="text" name="cidade" id="cidade" value="${ empresa.cidade }" maxlength="100"> / <input type="text" name="uf" id="uf" class="g-mini" value="${ empresa.uf }" maxlength="2">
			</div>
			<div class="field">
				<label for="tipo">É empresa matriz</label>
				<select id="tipo" name="tipo" onchange="alterarTipo()">
					<option value="M" ${ empresa.tipo eq 'M' ? 'selected' : '' }>Matriz</option>
					<option value="F" ${ empresa.tipo eq 'F' ? 'selected' : '' }>Filial</option>
				</select>
				<label for="matriz">Código matriz</label>
				<select id="matriz" name="matriz">
					<option value="">Selecione</option>
					<c:forEach items="${ lsEmpresa }" var="emp">
						<c:if test="${ empresa.id ne emp.id and emp.tipo eq 'M' }">
							<option value="${ emp.id }" ${ empresa.empresaMatriz.id eq emp.id ? 'selected' : '' }>${ emp.nome } - ${ emp.identificacao }</option>
						</c:if>
					</c:forEach>
				</select>
			</div>
			<div class="field">
				<label for="ativa">Ativa</label>
				<select id="ativa" name="ativa">
					<option value="S" ${ empresa.ativa eq 'S' ? 'selected' : '' }>Sim</option>
					<option value="N" ${ empresa.ativa eq 'N' ? 'selected' : '' }>Não</option>
				</select>
			</div>
			
			<div class="field">
				<label for="qtdMes">Quantidade (Pacote / Mês)</label>
				<input type="text" name="qtdMes" id="qtdMes" class="numeric" value="${ empresa.qtdMes }">
				<span>* Quantidade de notas armazenadas por mês para o cliente, caso seja enviado mais será armazenado normalmente.</span>
			</div>
			
			<div class="actions">
				<button type="submit">Salvar</button>
				<button type="reset">Limpar</button>
				<button type="button" onclick="document.location.href = '${ applicationPath }admin/home'">Novo</button>
			</div>
		</form>
	</div>
	
	
	<div class="table-list" style="margin: 10px auto;">
		<table>
			<thead>
				<tr>
					<th width="80px">
						<span class="text">Código</span>
					</th>
					<th>
						<span class="text">Nome</span>
					</th>
					<th width="110px">
						<span class="text">CPF / CNPJ</span>
					</th>
					<th width="50px">
						<span class="text">Ativa</span>
					</th>
					<th width="100px">
						<span class="text">OPÇÕES</span>
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ lsEmpresa }" var="emp">
					<tr class="line border">
						<td><span class="center">${ emp.id }</span></td>
						<td><span>${ emp.nome }</span></td>
						<td><span class="center">${ emp.identificacao }</span></td>
						<td><span class="center">${ emp.ativa }</span></td>
						<td><span class="center"><a href="${ applicationPath }admin/home?id=${ emp.id }">Editar</a></span></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

<script type="text/javascript">
	function alterarTipo() {
		var tipo = document.getElementById("tipo").value;
		if( tipo == 'M' ) {
			document.getElementById("matriz").disabled = true;
			document.getElementById("matriz").value = "";
		} else {
			document.getElementById("matriz").disabled = false;
		}
	}
	alterarTipo();
</script>

<jsp:include page="../include/rodape.jsp" />