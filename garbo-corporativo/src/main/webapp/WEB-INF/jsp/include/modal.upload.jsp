<%@ page contentType="text/html; charset=ISO-8859-1" language="java" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${ not empty usuarioLogado.empresaBase }">
	<div id="modalUploadNotas">
		<form action="${ applicationPath }upload-files" method="POST" enctype="multipart/form-data">
		
			<span>* Com esta opção pode enviar os arquivos diretamente de sua maquina para dentro do portal DF-e.</span>
			<br><br>
			<span>* <b style="color: #990000;">Maximo 6 arquivos por vez.</b></span>
			
			<div class="field" style="margin: 25px 0px;">
				<label for="arquivosDfe">Selecione os arquivos</label>
				<input class="g-xmaior" type="file" multiple="multiple" id="arquivosDfe" name="dfes">
			</div>
			
			<div class="actions">
				<button type="submit">
					<span class="ui-icon ui-icon-arrowthickstop-1-n"></span>
					<span class="btn-text">Enviar</span>
				</button>
			</div>
		</form>
	</div>
</c:if>