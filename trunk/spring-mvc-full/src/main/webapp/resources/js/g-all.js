var $dataTableHome = undefined;
var $dataTableUsuarios = undefined;

$(document).ready(function(){
	
	// customizar data
	$.datepicker.regional['pt-BR'] = {
            closeText: 			'Fechar',
            prevText: 			'&#x3c;Anterior',
            nextText: 			'Pr&oacute;ximo&#x3e;',
            currentText: 		'Hoje',
            monthNames: 		['Janeiro','Fevereiro','Mar&ccedil;o','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
            monthNamesShort:	['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez'],
            dayNames: 			['Domingo','Segunda-feira','Ter&ccedil;a-feira','Quarta-feira','Quinta-feira','Sexta-feira','Sabado'],
            dayNamesShort: 		['Dom','Seg','Ter','Qua','Qui','Sex','Sab'],
            dayNamesMin: 		['Dom','Seg','Ter','Qua','Qui','Sex','Sab'],
            weekHeader: 		'Sm',
            dateFormat: 		'dd/mm/yy',
            firstDay: 			0,
            isRTL: 				false,
            showMonthAfterYear: false,
            yearSuffix: 		'',
            showOn: 			"button",
    		buttonImage: 		applicationPath +"resources/ui-theme-agro/images/calendar.png",
    		buttonImageOnly: 	true};
    $.datepicker.setDefaults($.datepicker.regional['pt-BR']);
    
	$(".date").datepicker();
	
	
	// numeros
	$(".numeric").setMask("numeric");
	$(".site").setMask("site");
	$(".mail").setMask("email");
	
	
	// table home
	$dataTableHome = $("#table-list");
	if( $dataTableHome.size() > 0 ) {
		atualizaLista(true);
		
		// evento do btn de pesquisar
		$("#pesquisarFiltroHome").click(function(){
			// validações
			
			atualizaLista(true);
		});
		
		// evento do btn de mais resultados
		$("#carregarMaisRegistrosHome").click(function(){
			atualizaLista(false);
		});
	}
	
	
	
	// tabela de usuarios
	$dataTableUsuarios = $("#table-usuarios");
	if( $dataTableUsuarios.size() > 0 ) {
		$dataTableUsuarios.gmTableUsuario();
		
		$("#pesquisarUsuarios").click(function(){
			atualizaListaUsuarios(true);
		});
		$("#carregarMaisRegistrosUsuario").click(function(){
			atualizaListaUsuarios(false);
		});
	}
});


// TABELA DA HOME
function atualizaLista(clear) {
	if( clear ) {
		// limpa a tabela para nova pesquisa
		$dataTableHome.find("tbody").html("");
	}
	
	// cria os filtros
	var filter = {};
	filter.apartirDe = $dataTableHome.find(".line").size();
	filter.dataInicio = $("#dataInicial").val();
	filter.dataFim = $("#dataFinal").val();
	filter.situacao = $("#situacao").val();
	filter.nome = $("#nome").val();
	filter.identificacao = $("#identificacao").val();
	filter.tipoBusca = $("#tipoBusca").val();
	
	$.ajax({
		type: "GET",
		url: applicationPath +"json/nfe-cliente",
		data: filter,
		dataType: "json",
		traditional:true,
		cache:true,
		contentType:"application/x-www-form-urlencoded; charset=UTF-8",
		timeout:45000
	}).done(function(json) {
		$dataTableHome.find("tbody").append(template(json));
		$dataTableHome.gmTable();
	}).fail(function(){
		
	});
}

// template utilizado para a lista de NFes da home
function template(json) {
	var html = "";
	if( json.notas.length == 0 ) {
		
		$("#carregarMaisRegistrosHome").hide();
		
		html += "<tr class='line border'>";
		html += 	"<td colspan='6'>Não foram encontrados registros de acordo com a pesquisa</td>";
		html += "</tr>";
	} else {
		
		$("#carregarMaisRegistrosHome").show();
		
		for( i in json.notas ) {
			var nota = json.notas[i];
			html += "<tr class='line border'>";
			html += 	"<td>";
			html +=			"<span class='ui-icon black ui-icon-more expand-row'></span>";
			html +=			"<span class='text'>"+ nota.numero +"</span>";
			html += 	"</td>";
			html += 	"<td><span class='text center'>"+ nota.dtEmissao +"</span></td>";
			html += 	"<td><span class='text center'>"+ nota.situacao +"</span></td>";
			html += 	"<td><span class='text'>"+ nota.destinatario +"</span></td>";
			html += 	"<td><span class='text center'>"+ nota.identificacao +"</span></td>";
			html += 	"<td><span class='text'>"+ nota.email +"</span></td>";
			// html += 	"<td><input type='checkbox' value='"+ nota.id +"'></td>";
			html += "</tr>";
			html += "<tr class='show-line border'>";
			html += 	"<td colspan='6'>";
			html += 		"<div class='parte'>";
			html += 			"<span class='title'>Download NF-e</span>";
			html += 			"<span class='value'><a href='javascript:void(0)' data-id='"+ nota.idNfe +"' class='d-xml chama-download'></a></span>";
			html += 		"</div>";
			
			// caso não tenha a danf não mostra a opção para download
			if( nota.idDanf != null ) {
				html += 		"<div class='parte'>";
				html += 			"<span class='title'>Download Danf</span>";
				html += 			"<span class='value'><a href='javascript:void(0)' data-id='"+ nota.idDanf +"' class='d-pdf chama-download'></a></span>";
				html += 		"</div>";
			}
			
			// caso não tenha email não mostra a opção de reenviar
			if( nota.email != '-' ) {
				html += 		"<div class='parte'>";
				html += 			"<span class='title'>Reenviar e-mail</span>";
				html += 			"<span class='value'><a href='javascript:void(0)' data-id='"+ nota.id +"' title='Enviar para: "+ nota.email +"' class='d-email send-mail'></a></span>";
				html += 		"</div>";
			}
			
			html += 	"</td>";
			html += "</tr>";
		}
	}
	return html;
}



// TABELA DE USUARIOS
function atualizaListaUsuarios(clear) {
	
	if( clear ) {
		$dataTableUsuarios.find("tbody").html("");
	}
	
	// cria os filtros
	var filter = {};
	filter.apartirDe = $dataTableUsuarios.find(".line").size();
	filter.nome = $("#nome").val();
	filter.identificacao = $("#identificacao").val();
	
	
	var $ajaxUsuario = $.ajax({
		type: "GET",
		url: applicationPath +"json/list-usuarios",
		data: filter,
		dataType: "json",
		traditional:true,
		cache:true,
		contentType:"application/x-www-form-urlencoded; charset=UTF-8",
		timeout:45000
	}).done(function(json) {
		$dataTableUsuarios.find("tbody").append(templateUsuario(json));
		$dataTableUsuarios.gmTableUsuario();
		
		if( json.countPessoas > 0 && $dataTableUsuarios.find(".line").size() < json.countPessoas ){
			$("#carregarMaisRegistrosUsuario").show();
		} else {
			$("#carregarMaisRegistrosUsuario").hide();
		}
	}).fail(function(){
		
	});
}

function templateUsuario(json) {
	var html = "";
	
	if( json.usuarios.length == 0 ) {
		$("#carregarMaisRegistrosUsuario").hide();
		html += "<tr class='line border'>";
		html += 	"<td style='text-align: left;' colspan='3' alig>Não foram encontrados registros de acordo com a pesquisa</td>";
		html += "</tr>";
	} else {
		$("#carregarMaisRegistrosUsuario").show();
		for( i in json.usuarios ) {
			var usuario = json.usuarios[i];
			html += "<tr class='line border"+ (usuario.email == null || usuario.email == "" ? ' active' : '' ) +"'>";
			html += 	"<td class='nome'><span>"+ usuario.nome +"</span></td>";
			html += 	"<td><span>"+ usuario.identificacao +"</span></td>";
			html += 	"<td><input class='save-email' type='text' data-id='"+ usuario.id +"' value='"+ (usuario.email == null ? '' : usuario.email ) +"'></td>";
			html += "</tr>";
		}
	}
	return html;
}