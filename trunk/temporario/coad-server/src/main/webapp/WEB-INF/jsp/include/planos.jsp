<%@ page contentType="text/html; charset=ISO-8859-1" language="java" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${ not empty mostrarPlanos && mostrarPlanos }">

<div class="container clearfix">
	<br />
	<h1 style="font-size: 13px;">O que é o portal DF-e</h1>
	
	<div class="fl" style="width: 500px;">
		<p class="paragrafo">
			Visando simplificar o armazenamento e disponibilidade da NF-e por um período de cinco anos, a partir da data de geração da nota, conforme a legislação exige, a VIASOFT desenvolveu o PORTAL DF-e (Portal de Documentos Fiscais Eletrônicos), através do qual oferece um serviço de armazenamento remoto com transferência automática das notas fiscais eletrônicas em Data Center totalmente seguro.
		</p>
		<p class="paragrafo">	
			Através do PORTAL DF-e VIASOFT, é possível transmitir de forma automática os arquivos XML, tanto das notas de Saída quanto de Entrada, para o banco de dados disponível no Data Center, permitindo a realização de consultas a qualquer momento e de qualquer lugar com acesso a internet. Além do armazenamento em Data Center, o PORTAL DF-e VIASOFT dispara um e-mail automático para o destinatário, com o arquivo XML anexado, de forma a contemplar as exigências da legislação.
		</p>
		<p class="paragrafo">
			Além de armazenar NF-e, o PORTAL DF-e VIASOFT permite armazenar outros documentos eletrônicos, como por exemplo, CT-e (Conhecimento de Transporte Eletrônico).
		</p>
		<p class="paragrafo">
			Para adquirir o PORTAL DF-e VIASOFT é simples! Os planos de mensalidade são baseados no número de documentos eletrônicos armazenados por mês, confira os planos da tabela ao lado:
		</p>
		<p class="paragrafo">
			Clique abaixo em "Contratar Portal" e preencha nosso formulário para que um executivo de negócios Viasoft entre em contato, ou ligue: 46 2101 7777.
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
					<td>Até 500 DF-e/mês</td><td align="right">R$ 95,00</td></tr>
				<tr style="border-bottom: 1px solid #F0F0F0; background: #F5F5F5;"><td>Até 1.000 DF-e/mês</td><td align="right">R$ 190,00</td>
				</tr>
				<tr style="border-bottom: 1px solid #F0F0F0;">
					<td>Até 2.000 DF-e/mês</td><td align="right">R$ 332,50</td>
				</tr>
				<tr style="border-bottom: 1px solid #F0F0F0; background: #F5F5F5;">
					<td>Até 3.000 DF-e/mês</td><td align="right">R$ 475,00</td>
				</tr>
				<tr style="border-bottom: 1px solid #F0F0F0;">
					<td>Até 4.000 DF-e/mês</td><td align="right">R$ 617,50</td>
				</tr>
				<tr style="border-bottom: 1px solid #F0F0F0; background: #F5F5F5;">
					<td>Até 5.000 DF-e/mês</td><td align="right">R$ 760,00</td>
				</tr>
				<tr style="border-bottom: 1px solid #F0F0F0;">
					<td>Até 6.000 DF-e/mês</td><td align="right">R$ 902,50</td>
				</tr>
				<tr style="border-bottom: 1px solid #F0F0F0; background: #F5F5F5;">
					<td>Até 7.000 DF-e/mês</td><td align="right">R$ 1.045,40</td>
				</tr>
				<tr style="border-bottom: 1px solid #F0F0F0;">
					<td>Até 8.000 DF-e/mês</td><td align="right">R$ 1.188,30</td>
				</tr>
				<tr style="border-bottom: 1px solid #F0F0F0; background: #F5F5F5;">
					<td>Até 9.000 DF-e/mês</td><td align="right">R$ 1.331,20</td>
				</tr>
				<tr style="border-bottom: 1px solid #F0F0F0;">
					<td>Até 10.000 DF-e/mês</td><td align="right">R$ 1.474,10</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>
<div class="container">
	<p class="paragrafo">
		Empresas com necessidade de armazenar mais de 10.000 documentos eletrônicos por mês deverão adquirir o plano "Até 10.000 DF-e/mês" e pagar mais R$100,00 de acréscimo na mensalidade para cada 1.000 DF-e armazenados de forma adicional.
	</p>
	<p class="paragrafo">
		A empresa que adquirir um plano poderá, a qualquer momento migrar para um novo plano. O PORTAL DF-e VIASOFT avisa, via e-mail, quando o limite de DF-e armazenados está alcançando o número limite disponível no plano contratado.
	</p>
	<br />
	<br />
</div>
</c:if>