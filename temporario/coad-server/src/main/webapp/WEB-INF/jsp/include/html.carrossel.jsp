<%@ page contentType="text/html; charset=ISO-8859-1" language="java" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<div class="container">
	<ul id="carousel">
		<c:if test="${ not empty mostrarPlanos && mostrarPlanos }">
			<li>
				<a href="http://www.viasoft.com.br">
					<img width="980" height="160" alt="Viasoft" src="${ applicationPath }resources/img/slider/banner_interno.jpg" />
				</a>
			</li>
			<li>
				<a href="http://www.viasoft.com.br">
					<img width="980" height="160" alt="Viasoft" src="${ applicationPath }resources/img/slider/banner_interno.jpg" />
				</a>
			</li>
		</c:if>
		<c:if test="${ not empty isCapa && isCapa }">
			<li>
				<a href="${ applicationPath }planos">
					<img width="980" height="160" alt="Você está armazenando as NF-e como a legislação exige" src="${ applicationPath }resources/img/slider/banner_df_01.jpg" />
				</a>
			</li>
			<li>
				<a href="${ applicationPath }planos">
					<img width="980" height="160" alt="Simplifique com o portal DF-e" src="${ applicationPath }resources/img/slider/banner_df_02.jpg" />
				</a>
			</li>
			<li>
				<a href="${ applicationPath }planos">
					<img width="980" height="160" alt="Conheça os beneficios e planos do portal DF-e viasoft" src="${ applicationPath }resources/img/slider/banner_df_03.jpg" />
				</a>
			</li>
		</c:if>
	</ul>
</div>