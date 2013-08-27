<%@ page contentType="text/html; charset=ISO-8859-1" language="java" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<c:if test="${ not empty acessoRestrito and acessoRestrito }">
<div class="container clearfix error-separ">
	<div class="cont-imagem aces"></div>
	<div class="error-dir">
		<h1>Acesso restrito</h1>
		<span>Para acessar está pagina precisa estar logado no sistema e possuir permissão de acesso.</span>
		<a href="${ applicationPath }">Pagina inicial</a>
	</div>
</div>
</c:if>