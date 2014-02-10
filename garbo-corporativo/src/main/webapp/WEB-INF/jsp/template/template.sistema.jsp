<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<tiles:insertAttribute name="head"/>
</head>
<body class="${ browser }">
<tiles:insertAttribute name="mensagens"/>
<div class="tudo">
	<header>
		<tiles:insertAttribute name="topo"/>
	</header>
	<div class="container clearfix">
		<div class="lat-user fl">
			<tiles:insertAttribute name="menu"/>
		</div>
		<div class="formulario fr">
			<tiles:insertAttribute name="formulario"/>
		</div>
	</div>
	<footer>
		<tiles:insertAttribute name="rodape"/>
	</footer>
</div>
<tiles:insertAttribute name="javascript"/>
</body></html>