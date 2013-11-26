<%@ page contentType="text/html; charset=ISO-8859-1" language="java" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="include/head.jsp" />

<style>
body { overflow: hidden; }
</style>

<jsp:include page="include/html.carrossel.jsp" />

<%-- Incluir os arquivos de java script --%>
<script type="text/javascript" src="${ applicationPath }resources/js/jquery-1.10.1.min.js"></script>
<script type="text/javascript" src="${ applicationPath }resources/js/gm-browser.js"></script>

<!-- carrosel capa -->
<script type="text/javascript" src="${ applicationPath }resources/js/infinitecarousel/jquery.infinitecarousel3.js"></script>
<script type="text/javascript">
$(function(){
	$('#carousel').infiniteCarousel({
		imagePath: applicationPath +'resources/js/infinitecarousel/images/',
		transitionSpeed: 450,
		displayTime: 4500
	});
});
</script>

</body></html>