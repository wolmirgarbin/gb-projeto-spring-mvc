<%@ page contentType="text/html; charset=ISO-8859-1" language="java" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="container rod-line">
	<div class="fl">
		Desenvolvido por <b>AGRO VIASOFT</b>
		<br><br><br>
		&copy; 2013 - Todos os direitos reservados
	</div>
	<c:if test="${ not isCapa }">
		<div class="fr">
			<a href="${ applicationProperties.urlAgro }"><img alt="agronegocios" src="${ applicationPath }resources/img/logo/agronegocios.png"></a>
		</div>
	</c:if>
</div>
</div>

<%-- Incluir os arquivos de java script --%>
<script type="text/javascript" src="${ applicationPath }resources/js/jquery-1.10.1.min.js"></script>
<script type="text/javascript" src="${ applicationPath }resources/js/gm-browser.js"></script>

<%-- componentes ui --%>
<script src="${ applicationPath }resources/ui-theme-agro/jquery-ui-1.10.3.custom.min.js"></script>
<script src="${ applicationPath }resources/ui-theme-agro/components/jquery.ui.datepicker.min.js"></script>

<%-- pages --%>
<script type="text/javascript" src="${ applicationPath }resources/js/gm-meio.mask.js"></script>
<script type="text/javascript" src="${ applicationPath }resources/js/gm-messages.js"></script>
<script type="text/javascript" src="${ applicationPath }resources/js/gm-table.js"></script>

<script type="text/javascript" src="${ applicationPath }resources/js/g-all.js"></script>

</body></html>