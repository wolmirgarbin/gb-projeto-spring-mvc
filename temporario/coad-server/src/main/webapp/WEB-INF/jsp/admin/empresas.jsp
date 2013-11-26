<%@ page contentType="text/html; charset=ISO-8859-1" language="java" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<jsp:include page="../include/head.jsp" />

<jsp:include page="topo-adm.jsp" />

<div class="container">

	<div class="adm-localizacao">
		<a href="${ applicationPath }admin/home">Voltar para tela inicial</a> / Cadastro de contratantes / Cadastro de empresa por contratante
	</div>

	<div class="adm-ajuda">
		<a href="javascript:void(0)" id="opemHelp">O que fazer nesta tela?</a>
		<ul id="opemHelpUl">
			<li>Cadastrar empresas</li>
		</ul>
	</div>
	
</div>

<div class="page-empresas">	

	<div class="formulario" style="margin: 10px auto;">
	
		<jsp:include page="include.cab.contratante.jsp" />
	
		<h3>Manutenção de empresas ou filial</h3>
		
		<form action="${ applicationPath }admin/empresas-save" method="post">
			<div class="field">
				<label for="id">Código no portal</label>
				<input type="text" name="id" id="id" readonly="readonly" value="${ empresa.id }">
				<input type="hidden" name="contratante" value="${ contratante.id }">
			</div>
			<div class="field">
				<label for="nome">Nome empresa</label>
				<input type="text" name="nome" id="nome" value="${ empresa.nome }">
			</div>
			<div class="field">
				<label for="identificacao">CPF / CNPJ</label>
				<input type="text" name="identificacao" id="identificacao" class="numeric" value="${ empresa.identificacao }" maxlength="14" ${ not empty empresa ? 'readonly' : '' }>
				<span>* Serão carregadas apenas as notas que contiverem este CPF / CNPJ</span>
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
				<label for="fusoHorario">Fuso horário</label>
				<select name="utc" style="width: 480px;">
					<c:forEach items="${ lsFusoHorario }" var="utc">
						<option value="${ utc.valor }" ${ empresa.utc eq utc.valor ? 'selected' : '' }>UTC${ utc.valor }  - ${ utc.descricao }</option>
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
			
			<div class="actions">
				<button type="submit">Salvar</button>
				<button type="button" onclick="document.location.href = '${ applicationPath }admin/empresas?contratante=${ contratante.id }'">Novo</button>
				<button type="button" onclick="document.location.href = '${ applicationPath }admin/home'">Cancelar</button>
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
					<th>
						<span class="text">Cidade</span>
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
						<td><span>${ emp.cidade }</span></td>
						<td><span class="center">${ emp.identificacao }</span></td>
						<td><span class="center">${ emp.ativa }</span></td>
						<td><span class="center"><a href="${ applicationPath }admin/empresas?contratante=${ contratante.id }&id=${ emp.id }">Editar</a></span></td>
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