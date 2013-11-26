<%@ page contentType="text/html; charset=ISO-8859-1" language="java" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="container rod-line">
	<div class="fl">
		Desenvolvido por <b>VIASOFT Softwares Empresariais</b> &copy; 2013 - Todos os direitos reservados
	</div>
	<div class="fr">
		<a href="${ applicationProperties.urlAgro }" target="_blanck"><img alt="logo" width="130" height="26" src="${ applicationPath }resources/img/via.png"></a>
	</div>
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
<script type="text/javascript" src="${ applicationPath }resources/js/gm-modal.js"></script>
<script type="text/javascript" src="${ applicationPath }resources/js/gm-table.js"></script><!-- dependencia do gm-modal -->
<script type="text/javascript" src="${ applicationPath }resources/js/g-pluginTipsy.js"></script>

<script type="text/javascript" src="${ applicationPath }resources/js/g-all.js"></script>

<c:if test="${ isCapa or ( not empty mostrarPlanos and mostrarPlanos ) }">
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
</c:if>

<div id="gmModal">
	<div class="container-modal">
		<div class="conteudo-interno radius-2">
			<div class="mod-top">
				<span class="mod-titulo">Titulo</span>
				<a href="javascript:void(0)" class="mod-close"></a>
			</div>
			<div class="mod-html">
				<jsp:include page="modal.upload.jsp" />
				<jsp:include page="modal.envio.email.jsp" />
			</div>
		</div>
	</div>
	<div class="background-transparente"></div>
</div>

<jsp:include page="ajuda-interna.jsp" />

<c:set value="${ isCapa or ( not empty mostrarPlanos and mostrarPlanos ) }" var="capa"/> 
<c:if test="${ capa }">
	<script type="text/javascript">
	    var _gaq = _gaq || [];
	    _gaq.push(['_setAccount', 'UA-31944505-1']);
	    _gaq.push(['_trackPageview']);
	    (function () {
	        var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
	        ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
	        var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
	    })();
	</script>
</c:if>

<c:if test="${ not capa }">
	<script>
	  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
	  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
	  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
	  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');
	
	  ga('create', 'UA-43246748-1', 'viasoft.com.br');
	  ga('send', 'pageview');
	
	</script>
</c:if>

</body></html>