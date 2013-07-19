<%@ page contentType="text/html; charset=ISO-8859-1" language="java" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<c:if test="${ not empty paginaNaoEncontrada and paginaNaoEncontrada }">
<div class="container clearfix error-separ">
	<div class="cont-imagem aces"></div>
	<div class="error-dir">
		<h1>Página não encontrada</h1>
		<span>A url chamada não foi encontrada, verifique se digitou corretamente ou se ela realmente existe.</span>
		<a href="${ applicationPath }">Pagina inicial</a>
	</div>
</div>
</c:if>