<%@ page contentType="text/html; charset=ISO-8859-1" language="java" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${ not empty mostrarPlanos && mostrarPlanos }">

<div class="container clearfix">
	<br />
	<h1 style="font-size: 13px;">O que � o portal DF-e</h1>
	
	<div class="fl" style="width: 500px;">
		<p class="paragrafo">
			Visando simplificar o armazenamento e disponibilidade da NF-e por um per�odo de cinco anos, a partir da data de gera��o da nota, conforme a legisla��o exige, a VIASOFT desenvolveu o PORTAL DF-e (Portal de Documentos Fiscais Eletr�nicos), atrav�s do qual oferece um servi�o de armazenamento remoto com transfer�ncia autom�tica das notas fiscais eletr�nicas em Data Center totalmente seguro.
		</p>
		<p class="paragrafo">	
			Atrav�s do PORTAL DF-e VIASOFT, � poss�vel transmitir de forma autom�tica os arquivos XML, tanto das notas de Sa�da quanto de Entrada, para o banco de dados dispon�vel no Data Center, permitindo a realiza��o de consultas a qualquer momento e de qualquer lugar com acesso a internet. Al�m do armazenamento em Data Center, o PORTAL DF-e VIASOFT dispara um e-mail autom�tico para o destinat�rio, com o arquivo XML anexado, de forma a contemplar as exig�ncias da legisla��o.
		</p>
		<p class="paragrafo">
			Al�m de armazenar NF-e, o PORTAL DF-e VIASOFT permite armazenar outros documentos eletr�nicos, como por exemplo, CT-e (Conhecimento de Transporte Eletr�nico).
		</p>
		<p class="paragrafo">
			Para adquirir o PORTAL DF-e VIASOFT � simples! Os planos de mensalidade s�o baseados no n�mero de documentos eletr�nicos armazenados por m�s, confira os planos da tabela ao lado:
		</p>
		<p class="paragrafo">
			Clique abaixo em "Contratar Portal" e preencha nosso formul�rio para que um executivo de neg�cios Viasoft entre em contato, ou ligue: 46 2101 7777.
		</p>
		<br />
		<button type="submit" onclick="document.location.href='http://www.viasoft.com.br/atendimento/demo/?sl=8';" style="display: block; margin: 0px auto;">
			<span class="btn-text">Contratar Portal</span>				
		</button>
		<br />
		<br />
	</div>
	<div class="table-list radius-2 fr" style="font-size: 12px; font-weight: bold; border: 1px solid #F0F0F0; padding: 15px; width: 420px;">
		<table>
			<thead>
				<tr>
					<th width="300px">
						<span class="text" style="text-transform: none;">PLANO DE ARMAZENAMENTO DE DF-e</span>
					</th>
					<th>
						<span class="text" style="border-right: none;">Mensalidade</span>
					</th>
				</tr>
			</thead>
			<tbody>
				<tr style="border-bottom: 1px solid #F0F0F0;">
					<td>At� 500 DF-e/m�s</td><td align="right">R$ 95,00</td></tr>
				<tr style="border-bottom: 1px solid #F0F0F0; background: #F5F5F5;"><td>At� 1.000 DF-e/m�s</td><td align="right">R$ 190,00</td>
				</tr>
				<tr style="border-bottom: 1px solid #F0F0F0;">
					<td>At� 2.000 DF-e/m�s</td><td align="right">R$ 332,50</td>
				</tr>
				<tr style="border-bottom: 1px solid #F0F0F0; background: #F5F5F5;">
					<td>At� 3.000 DF-e/m�s</td><td align="right">R$ 475,00</td>
				</tr>
				<tr style="border-bottom: 1px solid #F0F0F0;">
					<td>At� 4.000 DF-e/m�s</td><td align="right">R$ 617,50</td>
				</tr>
				<tr style="border-bottom: 1px solid #F0F0F0; background: #F5F5F5;">
					<td>At� 5.000 DF-e/m�s</td><td align="right">R$ 760,00</td>
				</tr>
				<tr style="border-bottom: 1px solid #F0F0F0;">
					<td>At� 6.000 DF-e/m�s</td><td align="right">R$ 902,50</td>
				</tr>
				<tr style="border-bottom: 1px solid #F0F0F0; background: #F5F5F5;">
					<td>At� 7.000 DF-e/m�s</td><td align="right">R$ 1.045,40</td>
				</tr>
				<tr style="border-bottom: 1px solid #F0F0F0;">
					<td>At� 8.000 DF-e/m�s</td><td align="right">R$ 1.188,30</td>
				</tr>
				<tr style="border-bottom: 1px solid #F0F0F0; background: #F5F5F5;">
					<td>At� 9.000 DF-e/m�s</td><td align="right">R$ 1.331,20</td>
				</tr>
				<tr style="border-bottom: 1px solid #F0F0F0;">
					<td>At� 10.000 DF-e/m�s</td><td align="right">R$ 1.474,10</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>
<div class="container">
	<p class="paragrafo">
		Empresas com necessidade de armazenar mais de 10.000 documentos eletr�nicos por m�s dever�o adquirir o plano "At� 10.000 DF-e/m�s" e pagar mais R$100,00 de acr�scimo na mensalidade para cada 1.000 DF-e armazenados de forma adicional.
	</p>
	<p class="paragrafo">
		A empresa que adquirir um plano poder�, a qualquer momento migrar para um novo plano. O PORTAL DF-e VIASOFT avisa, via e-mail, quando o limite de DF-e armazenados est� alcan�ando o n�mero limite dispon�vel no plano contratado.
	</p>
	<br />
	<br />
</div>
</c:if>